package in.bhargavrao.stackoverflow.natty.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.entities.SOUser;
import in.bhargavrao.stackoverflow.natty.entities.autocomments.*;
import in.bhargavrao.stackoverflow.natty.filters.BlacklistedFilter;
import in.bhargavrao.stackoverflow.natty.filters.ContainsQMFilter;
import in.bhargavrao.stackoverflow.natty.filters.EndsWithQmFilter;
import in.bhargavrao.stackoverflow.natty.filters.Filter;
import in.bhargavrao.stackoverflow.natty.filters.LengthFilter;
import in.bhargavrao.stackoverflow.natty.filters.LinkOnlyAnswerFilter;
import in.bhargavrao.stackoverflow.natty.filters.NoCodeBlockFilter;
import in.bhargavrao.stackoverflow.natty.filters.NonEnglishFilter;
import in.bhargavrao.stackoverflow.natty.filters.OneLineFilter;
import in.bhargavrao.stackoverflow.natty.filters.PiledSymbolsFilter;
import in.bhargavrao.stackoverflow.natty.filters.ReputationFilter;
import in.bhargavrao.stackoverflow.natty.filters.SalutationsFilter;
import in.bhargavrao.stackoverflow.natty.filters.SelfAnswerFilter;
import in.bhargavrao.stackoverflow.natty.filters.StartsWithKeywordFilter;
import in.bhargavrao.stackoverflow.natty.filters.UnformattedCodeFilter;
import in.bhargavrao.stackoverflow.natty.filters.UnregisteredUserFilter;
import in.bhargavrao.stackoverflow.natty.filters.UserMentionedFilter;
import in.bhargavrao.stackoverflow.natty.filters.VeryLongWordFilter;
import in.bhargavrao.stackoverflow.natty.filters.WhitelistedFilter;
import in.bhargavrao.stackoverflow.natty.services.ApiService;

/**
 * Created by bhargav.h on 29-Sep-16.
 */
public class PostUtils {

    public static Post getPost(JsonObject answer, JsonObject question){

        Post np = new Post();

        JsonObject answererJSON = answer.get("owner").getAsJsonObject();
        JsonObject askerJSON = question.get("owner").getAsJsonObject();

        np.setAnswerCreationDate(Instant.ofEpochSecond(answer.get("creation_date").getAsInt()));
        np.setAnswerID(answer.get("answer_id").getAsInt());
        np.setQuestionCreationDate(Instant.ofEpochSecond(question.get("creation_date").getAsInt()));
        np.setQuestionID(answer.get("question_id").getAsInt());
        np.setTitle(JsonUtils.escapeHtmlEncoding(question.get("title").getAsString()));
        np.setMainTag(question.get("tags").getAsJsonArray().get(0).getAsString());
        np.setTags(StreamSupport.stream(question.get("tags").getAsJsonArray().spliterator(), false).map(JsonElement::getAsString).toArray(String[]::new));
        np.setBody(answer.get("body").getAsString());
        np.setBodyMarkdown(JsonUtils.escapeHtmlEncoding(answer.get("body_markdown").getAsString()));

        SOUser answerer = new SOUser();
        SOUser asker = new SOUser();

        try{
            answerer.setReputation(answererJSON.get("reputation").getAsLong());
            answerer.setUsername(JsonUtils.escapeHtmlEncoding(answererJSON.get("display_name").getAsString()));
            answerer.setUserType(answererJSON.get("user_type").getAsString());
            answerer.setUserId(answererJSON.get("user_id").getAsInt());

            asker.setUsername(JsonUtils.escapeHtmlEncoding(askerJSON.get("display_name").getAsString()));
            asker.setUserType(askerJSON.get("user_type").getAsString());

            if(askerJSON.get("user_type").getAsString().equals("does_not_exist")){
                asker.setReputation(0);
                asker.setUserId(-1);
            }
            else {
                asker.setReputation(askerJSON.get("reputation").getAsLong());
                asker.setUserId(askerJSON.get("user_id").getAsInt());
            }
        }
        catch (Exception e){
            System.out.println("ASKER"+askerJSON);
            System.out.println("ANSWERER"+answererJSON);
            e.printStackTrace();
        }

        np.setAsker(asker);
        np.setAnswerer(answerer);

        return np;

    }

    public static void handleFeedback(User user, String type, String linkToPost) {
        String filename = FilePathUtils.outputCSVLogFile;
        try {

            String sentinel = FileUtils.readLineFromFileStartswith(FilePathUtils.outputSentinelIdLogFile,linkToPost);

            long postId = Long.parseLong(sentinel.split(",")[1]);
            if(postId!=-1) {
                long feedbackId = PostUtils.addFeedback(postId, user.getId(), user.getName(), type);
            }
            String loggedLine = FileUtils.readLineFromFileStartswith(FilePathUtils.outputCompleteLogFile,linkToPost);
            String loggedAsTp = FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"tp,"+linkToPost);
            String loggedAsTn = FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"tn,"+linkToPost);
            String loggedAsFp = FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"fp,"+linkToPost);
            String loggedAsNe = FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"ne,"+linkToPost);

            if((loggedAsTp==null||loggedAsTn==null||loggedAsFp==null||loggedAsNe==null)&&loggedLine!=null) {
                FileUtils.appendToFile(filename, type + "," +loggedLine);
                FileUtils.removeFromFile(FilePathUtils.outputReportLogFile,linkToPost);
                FileUtils.removeFromFileStartswith(FilePathUtils.outputCompleteLogFile,linkToPost);
            }
            else if(loggedAsTp!=null){
                FileUtils.removeFromFile(filename,loggedAsTp);
                FileUtils.appendToFile(filename,loggedAsTp.replace("tp,",type+","));
            }
            else if(loggedAsFp!=null){
                FileUtils.removeFromFile(filename,loggedAsFp);
                FileUtils.appendToFile(filename,loggedAsFp.replace("fp,",type+","));
            }
            else if(loggedAsNe!=null){
                FileUtils.removeFromFile(filename,loggedAsNe);
                FileUtils.appendToFile(filename,loggedAsNe.replace("ne,",type+","));
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
    }


    public static PostReport getNaaValue(Post np) {
        Double f = 0.0;

        List<Filter> filters = new ArrayList<Filter>(){{
            add(new BlacklistedFilter(np));
            add(new ContainsQMFilter(np));
            add(new EndsWithQmFilter(np));
            add(new LengthFilter(np));
            add(new LinkOnlyAnswerFilter(np));
            add(new NoCodeBlockFilter(np));
            add(new NonEnglishFilter(np));
            add(new OneLineFilter(np));
            add(new PiledSymbolsFilter(np));
            add(new ReputationFilter(np));
            add(new SalutationsFilter(np));
            add(new SelfAnswerFilter(np));
            add(new StartsWithKeywordFilter(np));
            add(new UnformattedCodeFilter(np));
            add(new UnregisteredUserFilter(np));
            add(new UserMentionedFilter(np));
            add(new VeryLongWordFilter(np));
            add(new WhitelistedFilter(np));
        }};

        List<String> caughtFor = new ArrayList<>();
        List<Double> caughtNaa = new ArrayList<>();

        for(Filter filter: filters){
            if(filter.filter()){
                f+=filter.getValue();
                caughtFor.add(filter.description());
                caughtNaa.add(filter.getValue());
            }
        }

        return new PostReport(np,f,caughtFor,caughtNaa);
    }

    public static String addFMS(PostReport report){

        Post np = report.getPost();

        String htmlString="<!DOCTYPE html><html><head><title>"+np.getTitle()+"</title></head><link href='style.css' rel='stylesheet' ><body><pre style='border:1px solid black;border-radius:5px'><code>"+np.getBody()+"</code></pre><p>Caught for</p>";
        for(String i:report.getCaughtFor()){
            htmlString+=i+"<br/>";
        }
        htmlString+="<p><a href='//stackoverflow.com/a/"+np.getAnswerID()+"'>Link to post</a></p></body></html>";
        try {
            FileUtils.createNewFile("./../tomcat/webapps/ROOT/Natty/" + np.getAnswerID() + ".html", htmlString);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "http://51.254.218.90:8000/Natty/"+np.getAnswerID()+".html";
    }

    public static long addSentinel(PostReport report){
    	//check if Natty is running on the server
    	Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(FilePathUtils.loginPropertiesFile));
        }
        catch (IOException e){
            e.printStackTrace();
            return -1;
        }
        
        if (!prop.getProperty("location").equals("server")) return -1; //return -1 if it's not running on server
        
        //continue...
        
        JsonObject post = new JsonObject();

        post.addProperty("title",report.getPost().getTitle());
        post.addProperty("body",report.getPost().getBody());
        post.addProperty("link","http://www.stackoverflow.com/a/"+report.getPost().getAnswerID());
        post.addProperty("post_creation_date",report.getPost().getAnswerCreationDate().toString());
        post.addProperty("user_link","http://stackoverflow.com/users/"+report.getPost().getAnswerer().getUserId());
        post.addProperty("username",report.getPost().getAnswerer().getUsername());
        post.addProperty("user_reputation",report.getPost().getAnswerer().getReputation());
        post.addProperty("nato_score",report.getNaaValue());
        post.addProperty("answer_id", report.getPost().getAnswerID());


        JsonArray reasons = new JsonArray();
        for(String reason: report.getCaughtFor()){
            if(reason.startsWith("Non English"))
                reason = "Non English Post";
            if(reason.startsWith("User @"))
                reason = "User Mentioned";
            if(reason.startsWith("Piled symbols"))
                reason = "Piled symbols";
            reasons.add(reason);
        }

        String authorization = "112a5090460102f758711ae2c51c74f59555fb773f4192af122f2a4407904bce";

        JsonObject json = new JsonObject();

        json.add("post",post);
        json.add("reasons",reasons);
        json.addProperty("authorization",authorization);

        long sentinelPostId = SentinelUtils.post(json);

        try{
           FileUtils.appendToFile(FilePathUtils.outputSentinelIdLogFile,report.getPost().getAnswerID()+","+sentinelPostId);
        }
        catch (IOException e){
           e.printStackTrace();
        }

        return sentinelPostId;
    }

    public static long addFeedback(long post_id,long chat_id,String chat_username, String feedback_type){

        JsonObject feedback = new JsonObject();

        feedback.addProperty("post_id",post_id);
        feedback.addProperty("chat_id",chat_id);
        feedback.addProperty("chat_username",chat_username);


        String authorization = "112a5090460102f758711ae2c51c74f59555fb773f4192af122f2a4407904bce";

        JsonObject json = new JsonObject();

        json.add("feedback",feedback);
        json.addProperty("feedback_type",feedback_type);
        json.addProperty("authorization",authorization);

        return SentinelUtils.feedback(json);
    }

    public static void store(Room room, PingMessageEvent event, String type){
        long repliedTo = event.getParentMessageId();
        Message repliedToMessage = room.getMessage(repliedTo);
        String message = repliedToMessage.getPlainContent().trim();
        String linkToPost = getPostIdFromMessage(message);
        handleFeedback(event.getMessage().getUser(), type, linkToPost);
    }

    public static String getPostIdFromMessage(String message) {
        message = message.split("//stackoverflow.com/a/")[1];
        return message.substring(0,message.indexOf(")"));
    }


    public static void reply(Room room, PingMessageEvent event, boolean isReply){
        Message message = event.getMessage();
		System.out.println(message.getContent());
        if (CheckUtils.checkIfUserIsBlacklisted(event.getUserId())){
            System.out.println("Blacklisted user");
            return;
        }
        if (CommandUtils.checkForCommand(message.getContent(),"tp") ||
                CommandUtils.checkForCommand(message.getContent(),"t")){
            store(room, event, "tp");
        }
        if (CommandUtils.checkForCommand(message.getContent(),"ne") ||
                CommandUtils.checkForCommand(message.getContent(),"n")){
            store(room, event, "ne");
        }
        if (CommandUtils.checkForCommand(message.getContent(),"fp") ||
                CommandUtils.checkForCommand(message.getContent(),"f")){
            store(room, event, "fp");
        }
    }

    
    public static String autoFlag(Post post) {
    	return autoFlag(post, null);
    }
    
    
    public static String autoFlag(Post post, AutoComment comment){
        ApiService apiService = new ApiService("stackoverflow");
        try{
            JsonObject flagOptions = apiService.getAnswerFlagOptions(post.getAnswerID());
            JsonArray options = flagOptions.getAsJsonArray("items");
            for(JsonElement e: options){
                if(e.getAsJsonObject().get("title").getAsString().equals("not an answer")){
                    JsonObject flaggedPost = apiService.flagAnswer(post.getAnswerID(),e.getAsJsonObject().get("option_id").getAsInt());
                    
                    //If a comment was passed, post it
                    if (comment != null && comment.length() > 0) {
                        JsonObject commentJson = apiService.addComment(comment.getText(),post.getAnswerID());
                        Integer commentId = commentJson.get("items").getAsJsonArray().get(0).getAsJsonObject().get("comment_id").getAsInt();
                        return "Post Flagged Automatically - Added [comment](//stackoverflow.com/posts/comments/"+commentId+"): "+comment.getIdentifier();
                        //return "Post Flagged Automatically - would add comment: "+comment.getIdentifier();
                    }
                    
                    return "Post Flagged Automatically";
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return "Some Error Occured";
        }
        return "Some Error Occured";
    }
}
