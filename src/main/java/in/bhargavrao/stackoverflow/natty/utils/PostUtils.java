package in.bhargavrao.stackoverflow.natty.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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


    public static PostReport getNaaValue(Post np, String sitename) {
        Double f = 0.0;

        List<Filter> filters = getFiltersForSite(np, sitename);

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

    @NotNull
    private static List<Filter> getFiltersForSite(Post np, String sitename) {
        ArrayList<Filter> filters = new ArrayList<Filter>() {{
            add(new BlacklistedFilter(np));
            add(new ContainsQMFilter(np));
            add(new EndsWithQmFilter(np));
            add(new LengthFilter(np));
            add(new LinkOnlyAnswerFilter(np));
            add(new NoCodeBlockFilter(np));
            add(new NonEnglishFilter(np));
            add(new OneLineFilter(np));
            add(new PiledSymbolsFilter(np));
            add(new SalutationsFilter(np));
            add(new StartsWithKeywordFilter(np));
            add(new UnformattedCodeFilter(np));
            add(new UserMentionedFilter(np));
            add(new VeryLongWordFilter(np));
            add(new WhitelistedFilter(np));
            add(new NewBlacklistedFilter(np));
        }};


        if(sitename.equals("testBody")){
            return filters;
        }

        filters.add(new ReputationFilter(np));
        filters.add(new SelfAnswerFilter(np));
        filters.add(new UnregisteredUserFilter(np));

        return filters;
    }

    public static long addSentinel(PostReport report, String sitename, String siteurl){
    	//check if Natty is running on the server
        PropertyService service = new PropertyService();
        
        if (!service.getLocation().equals("server")) return -1; //return -1 if it's not running on server
        
        //continue...
        
        JsonObject post = new JsonObject();

        post.addProperty("title",report.getPost().getTitle());
        post.addProperty("body",report.getPost().getBody());
        post.addProperty("link","https://"+siteurl+"/a/"+report.getPost().getAnswerID());
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
            if(reason.startsWith("Intelli"))
                reason = "IntelliBL";
            reasons.add(reason);
        }

        String authorization = SentinelUtils.getSentinelAuth(sitename);

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


    public static String autoFlag(Post post, AutoComment comment, String sitename, String siteurl){
        ApiService apiService = new ApiService(sitename);
        PropertyService propertyService = new PropertyService();

        if(propertyService.getLocation().equals("server")) {
            try {
                JsonObject flagOptions = apiService.getAnswerFlagOptions(post.getAnswerID());
                JsonArray options = flagOptions.getAsJsonArray("items");
                for (JsonElement e : options) {
                    if (e.getAsJsonObject().get("title").getAsString().equalsIgnoreCase("not an answer")) {
                        JsonObject flaggedPost = apiService.flagAnswer(post.getAnswerID(), e.getAsJsonObject().get("option_id").getAsInt());

                        StorageService service = new FileStorageService();
                        service.addAutoFlag(post.getAnswerID(), sitename);

                        //If a comment was passed, post it
                        if (comment != null && comment.length() > 0) {
                            JsonObject commentJson = apiService.addComment(comment.getText(), post.getAnswerID());
                            Integer commentId = commentJson.get("items").getAsJsonArray().get(0).getAsJsonObject().get("comment_id").getAsInt();
                            return "Post Flagged Automatically - Added [comment](//stackoverflow.com/posts/comments/" + commentId + "): " + comment.getIdentifier();
                        }
                        return "Post Flagged Automatically";
                    }
                }
                return "Flagging as not an answer is not possible";
            } catch (IOException e) {
                e.printStackTrace();
                return "Some Error Occurred";
            }
        }
        return "Post not flagged. Not on Server";
    }
}
