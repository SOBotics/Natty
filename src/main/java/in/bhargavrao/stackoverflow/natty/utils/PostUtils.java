package in.bhargavrao.stackoverflow.natty.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import fr.tunaki.stackoverflow.chat.event.MessagePostedEvent;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.commands.Check;
import in.bhargavrao.stackoverflow.natty.exceptions.FeedbackInvalidatedException;
import in.bhargavrao.stackoverflow.natty.filters.*;
import in.bhargavrao.stackoverflow.natty.model.*;
import in.bhargavrao.stackoverflow.natty.model.autocomments.AutoComment;
import in.bhargavrao.stackoverflow.natty.services.ApiService;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.PropertyService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

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

    public static void handleFeedback(User user, String type, String answerId, String sitename, String siteurl) throws FeedbackInvalidatedException {
        StorageService service = new FileStorageService();
        String sentinel = service.getSentinelId(answerId, sitename);
        long postId = -1;
        if (sentinel!=null) {
            postId = Long.parseLong(sentinel.split(",")[1]);
        }
        if(postId!=-1) {
            long feedbackId = PostUtils.addFeedback(postId, user.getId(), user.getName(), type, sitename, siteurl);
        }
        FeedbackType previousFeedbackType = service.getFeedback(answerId, sitename);
        Feedback feedback = new Feedback(user.getName(), user.getId(), getFeedbackTypeFromFeedback(type));

        if(previousFeedbackType==null) {
            String loggedLine = service.retrieveReport(answerId, sitename);
            SavedReport report = getSavedReportFromLog(loggedLine);
            service.saveFeedback(feedback, report, sitename);
        }
        else if (previousFeedbackType.equals(feedback.getFeedbackType())){
            String loggedLine = service.retrieveFeedback(answerId, sitename);
            SavedReport report = getSavedReportFromLog(loggedLine.replace(previousFeedbackType.toString()+",",""));
            service.addFeedback(feedback, report, sitename);
        }
        else{
            String loggedLine = service.retrieveFeedback(answerId, sitename);
            String feedbackUserLog = service.retrieveFeedbackUserLog(answerId, sitename);
            String feedbackSplit[] = feedbackUserLog.split(",");
            Feedback previousFeedback = new Feedback(feedbackSplit[3], Long.parseLong(feedbackSplit[2]), previousFeedbackType);
            SavedReport report = getSavedReportFromLog(loggedLine.replace(previousFeedbackType.toString()+",",""));
            service.invalidateFeedback(feedback, report, sitename);
            throw new FeedbackInvalidatedException(previousFeedback, feedback);
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

        String htmlString="<!DOCTYPE html><html><head><title>"+np.getTitle()+
                "</title></head><link href='style.css' rel='stylesheet' ><body><pre style='border:1px solid black;border-radius:5px'><code>"
                +np.getBody()+"</code></pre><p>" + "<a href='"+np.getSiteUrl()+"/users/"+ np.getAnswerer().getUserId() + "'>"+np.getAnswerer().getUsername()+"</a>"+
                "</p><p>Caught for</p>";
        for(String i:report.getCaughtFor()){
            htmlString+=i+"<br/>";
        }
        htmlString+="<p><a href='"+np.getSiteUrl()+"/a/"+np.getAnswerID()+"'>Link to post</a></p></body></html>";
        try {
            FMSUtils.createNewFile("./../tomcat/webapps/ROOT/Natty/" + np.getAnswerID() + ".html", htmlString);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "http://51.254.218.90:8000/Natty/"+np.getAnswerID()+".html";
    }

    public static long addSentinel(PostReport report, String sitename, String siteurl){
    	//check if Natty is running on the server
        PropertyService service = new PropertyService();
        
        if (!service.getLocation().equals("server")) return -1; //return -1 if it's not running on server
        
        //continue...
        
        JsonObject post = new JsonObject();

        post.addProperty("title",report.getPost().getTitle());
        post.addProperty("body",report.getPost().getBody());
        post.addProperty("link","https://www."+siteurl+"/a/"+report.getPost().getAnswerID());
        post.addProperty("post_creation_date",report.getPost().getAnswerCreationDate().toString());
        post.addProperty("user_link","https://"+siteurl+"/users/"+report.getPost().getAnswerer().getUserId());
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

        String authorization = getSentinelAuth(sitename);

        JsonObject json = new JsonObject();

        json.add("post",post);
        json.add("reasons",reasons);
        json.addProperty("site","https://"+siteurl);
        json.addProperty("authorization",authorization);

        long sentinelPostId = SentinelUtils.post(json, sitename);
        StorageService storageService = new FileStorageService();
        storageService.storeSentinelData(report.getPost().getAnswerID(), sentinelPostId, sitename);
        return sentinelPostId;
    }

    @NotNull
    private static String getSentinelAuth(String sitename) {
//        if (sitename.equals("stackoverflow"))
//            return "112a5090460102f758711ae2c51c74f59555fb773f4192af122f2a4407904bce";
//        else if (sitename.equals("askubuntu"))
//            return "3eddced9a0db7e1e8293c256a2887ef4bfd6c6a259233b18d4df24e12285de8b";
        return "112a5090460102f758711ae2c51c74f59555fb773f4192af122f2a4407904bce";
    }

    public static long addFeedback(long post_id,long chat_id,String chat_username, String feedback_type, String sitename, String siteurl){

        JsonObject feedback = new JsonObject();

        feedback.addProperty("post_id",post_id);
        feedback.addProperty("chat_id",chat_id);
        feedback.addProperty("chat_username",chat_username);

        String authorization = getSentinelAuth(sitename);

        JsonObject json = new JsonObject();

        json.add("feedback",feedback);
        json.addProperty("feedback_type",feedback_type);
        json.addProperty("authorization",authorization);

        return SentinelUtils.feedback(json, sitename);
    }

    public static void store(Room room, PingMessageEvent event, String type, String sitename, String siteurl){
        long repliedTo = event.getParentMessageId();
        Message repliedToMessage = room.getMessage(repliedTo);
        String message = repliedToMessage.getPlainContent().trim();
        String linkToPost = getPostIdFromMessage(message, siteurl);
        StorageService service = new FileStorageService();
        if (type.equals("fp") && service.checkAutoFlag(Long.parseLong(linkToPost),sitename)){
            room.send("False positive feedback on Autoflag, please retract @BhargavRao");
        }
        try {
            handleFeedback(event.getMessage().getUser(), type, linkToPost, sitename, siteurl);
        } catch (FeedbackInvalidatedException e) {
            e.printStackTrace();
            room.send(e.getMessage());
        }
    }

    public static String getPostIdFromMessage(String message, String siteurl) {
        message = message.split("//"+siteurl+"/a/")[1];
        return message.substring(0,message.indexOf(")"));
    }


    public static void newMessage(Room room, MessagePostedEvent event, boolean b) {
        String message = event.getMessage().getPlainContent();
        int cp = Character.codePointAt(message, 0);
        if(message.trim().startsWith("@bots alive")){
            room.send("Whadya think?");
        }
        else if (cp == 128642 || (cp>=128644 && cp<=128650)){
            room.send("\uD83D\uDE83");
        }
    }

    public static void reply(Room room, PingMessageEvent event, String sitename, String siteurl, boolean isReply){
        Message message = event.getMessage();
		System.out.println(message.getContent());
        if (CheckUtils.checkIfUserIsBlacklisted(event.getUserId())){
            System.out.println("Blacklisted user");
            return;
        }
        if (CommandUtils.checkForCommand(message.getContent(),"tp") ||
                CommandUtils.checkForCommand(message.getContent(),"t")){
            store(room, event, "tp", sitename, siteurl);
        }
        if (CommandUtils.checkForCommand(message.getContent(),"ne") ||
                CommandUtils.checkForCommand(message.getContent(),"n")){
            store(room, event, "ne", sitename, siteurl);
        }
        if (CommandUtils.checkForCommand(message.getContent(),"fp") ||
                CommandUtils.checkForCommand(message.getContent(),"f")){
            store(room, event, "fp", sitename, siteurl);
        }
        if (CommandUtils.checkForCommand(message.getContent(),"delete") ||
                CommandUtils.checkForCommand(message.getContent(),"remove")){
            long repliedTo = event.getParentMessageId();
            room.delete(repliedTo);
        }
        if (CommandUtils.checkForCommand(message.getContent(),"why")){
            long repliedTo = event.getParentMessageId();
            Message repliedToMessage = room.getMessage(repliedTo);
            String linkToPost = getPostIdFromMessage(repliedToMessage.getPlainContent().trim(), siteurl);

            try {
                String returnParams[] = new Check(message, sitename, siteurl).getCheckData(linkToPost, 2);
                room.replyTo(message.getId(), returnParams[0]);
                if (!returnParams[1].equals(""))
                    room.send(returnParams[1]);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static SavedReport getReport(Post np, PostReport report){

        SavedReport savedReport = new SavedReport();

        savedReport.setAnswerId(np.getAnswerID());
        savedReport.setTimestamp(np.getAnswerCreationDate());
        savedReport.setNaaValue(report.getNaaValue());
        savedReport.setBodyLength(np.getBodyMarkdown().length());
        savedReport.setReputation(np.getAnswerer().getReputation());

        List<Reason> reasons = new ArrayList<>();

        for (String caughtFor: report.getCaughtFor()){

            Reason reason = new Reason();

            if (caughtFor.contains("-")){
                reason.setReasonName(caughtFor.split("-")[0].trim());
                reason.setSubReason(caughtFor.split("-")[1].trim());
            }
            else {
                reason.setReasonName(caughtFor);
            }
            reasons.add(reason);
        }
        savedReport.setReasons(reasons);
        return savedReport;
    }

    public static SavedReport getSavedReportFromLog(String logline){
        SavedReport report = new SavedReport();
        String parts[] = logline.split(",");
        if (parts.length!=6)
            return null;
        report.setAnswerId(Integer.valueOf(parts[0]));
        report.setTimestamp(Instant.parse(parts[1]));
        report.setNaaValue(Double.parseDouble(parts[2]));
        report.setBodyLength(Integer.parseInt(parts[3]));
        report.setReputation(Integer.parseInt(parts[4]));
        List<Reason> reasons = getReasons(parts[5]);
        report.setReasons(reasons);
        return report;
    }

    private static List<Reason> getReasons(String part) {
        List<Reason> reasons = new ArrayList<>();
        String reasonStrings[] = part.split(";");
        for (String reasonString: reasonStrings){
            Reason reason = new Reason();
            if (reasonString.contains("-")){
                reason.setReasonName(reasonString.split(" - ")[0]);
                reason.setSubReason(reasonString.split(" - ")[1]);
            }
            else {
                reason.setReasonName(reasonString);
            }
            reasons.add(reason);
        }
        return reasons;
    }

    public static String autoFlag(Post post, AutoComment comment, String sitename, String siteurl){
        if(sitename.equals("stackoverflow")) {
            ApiService apiService = new ApiService(sitename);
            try {
                JsonObject flagOptions = apiService.getAnswerFlagOptions(post.getAnswerID());
                JsonArray options = flagOptions.getAsJsonArray("items");
                for (JsonElement e : options) {
                    if (e.getAsJsonObject().get("title").getAsString().equals("not an answer")) {
                        JsonObject flaggedPost = apiService.flagAnswer(post.getAnswerID(), e.getAsJsonObject().get("option_id").getAsInt());

                        //If a comment was passed, post it
                        if (comment != null && comment.length() > 0) {
                            JsonObject commentJson = apiService.addComment(comment.getText(), post.getAnswerID());
                            Integer commentId = commentJson.get("items").getAsJsonArray().get(0).getAsJsonObject().get("comment_id").getAsInt();
                            return "Post Flagged Automatically - Added [comment](//stackoverflow.com/posts/comments/" + commentId + "): " + comment.getIdentifier();
                            //return "Post Flagged Automatically - would add comment: "+comment.getIdentifier();
                        }
                        StorageService service = new FileStorageService();
                        service.addAutoFlag(post.getAnswerID(), sitename);
                        return "Post Flagged Automatically";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Some Error Occured";
            }
        }
        return "Some Error Occured";
    }

    public static FeedbackType getFeedbackTypeFromFeedback(String feedback){
        switch (feedback){
            case "t": return FeedbackType.TRUE_POSITIVE;
            case "tp": return FeedbackType.TRUE_POSITIVE;
            case "f": return FeedbackType.FALSE_POSITIVE;
            case "fp": return FeedbackType.FALSE_POSITIVE;
            case "n": return FeedbackType.NEEDS_EDITING;
            case "ne": return FeedbackType.NEEDS_EDITING;
            case "tn": return FeedbackType.TRUE_NEGATIVE;
        }
        return null;
    }
}
