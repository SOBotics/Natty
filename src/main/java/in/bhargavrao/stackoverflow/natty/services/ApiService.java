package in.bhargavrao.stackoverflow.natty.services;

import com.google.gson.JsonObject;
import in.bhargavrao.stackoverflow.natty.utils.JsonUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 29-Sep-16.
 */
public class ApiService {

    private String apiKey;
    private String autoflagKey;
    private String autoflagToken;
    private String userId;
    private String site;

    private static int quota=0;
    private static final String filter = "!40nvjHa_IL(lxIFT9";

    public ApiService(String site){

        PropertyService service = new PropertyService();

        this.site = site;
        this.apiKey = service.getApiKey();
        this.autoflagKey = service.getAutoFlagKey();
        this.autoflagToken = service.getAutoFlagToken();
        this.userId = service.getUserId();

    }

    public JsonObject getQuestionDetailsByIds(List<Integer> questionIdList) throws IOException {
        JsonObject questionJson = getQuestionDetailsByIds(questionIdList,site,apiKey);
        JsonUtils.handleBackoff(questionJson);
        quota = questionJson.get("quota_remaining").getAsInt();
        return questionJson;
    }

    public JsonObject getQuestionDetailsById(Integer questionId) throws IOException{
        JsonObject questionJson = getQuestionDetailsById(questionId,site,apiKey);
        JsonUtils.handleBackoff(questionJson);
        quota = questionJson.get("quota_remaining").getAsInt();
        return questionJson;
    }

    public JsonObject getAnswerDetailsById(Integer answerId) throws IOException{
        JsonObject answerJson = getAnswerDetailsById(answerId,site,apiKey);
        JsonUtils.handleBackoff(answerJson);
        quota = answerJson.get("quota_remaining").getAsInt();
        return answerJson;
    }

    public JsonObject getAnswerDetailsByIds(List<Integer> answerIdList) throws IOException{
        JsonObject answerJson = getAnswerDetailsByIds(answerIdList,site,apiKey);
        JsonUtils.handleBackoff(answerJson);
        quota = answerJson.get("quota_remaining").getAsInt();
        return answerJson;
    }

    public JsonObject getFirstPageOfAnswers(Instant fromTimestamp) throws IOException{
        JsonObject answersJson = getFirstPageOfAnswers(fromTimestamp,site,apiKey);
        JsonUtils.handleBackoff(answersJson);
        quota = answersJson.get("quota_remaining").getAsInt();
        return answersJson;
    }
    public JsonObject getAnswerFlagOptions(Integer answerId) throws IOException{
        JsonObject flagOptionsJson = getAnswerFlagOptions(answerId,site,autoflagKey,autoflagToken);
        JsonUtils.handleBackoff(flagOptionsJson);
        quota = flagOptionsJson.get("quota_remaining").getAsInt();
        return flagOptionsJson;
    }

    public JsonObject flagAnswer(Integer answerId, Integer flagType) throws IOException{
        JsonObject flaggedPost = FlagAnswer(answerId,flagType,site,autoflagKey,autoflagToken);
        JsonUtils.handleBackoff(flaggedPost);
        quota = flaggedPost.get("quota_remaining").getAsInt();
        return flaggedPost;
    }

    public JsonObject addComment(String comment, Integer postId) throws IOException{
        JsonObject addedComment = addComment(comment,postId,site,autoflagKey,autoflagToken);
        JsonUtils.handleBackoff(addedComment);
        quota = addedComment.get("quota_remaining").getAsInt();
        return addedComment;
    }

    public JsonObject deleteComment(Integer commentId) throws IOException{
        JsonObject deletedComment  = deleteComment(commentId,site,autoflagKey,autoflagToken);
        JsonUtils.handleBackoff(deletedComment);
        quota = deletedComment.get("quota_remaining").getAsInt();
        return deletedComment;
    }

    public JsonObject getComments() throws IOException{
        JsonObject commentList = getComments(Integer.parseInt(userId),site,apiKey);
        JsonUtils.handleBackoff(commentList);
        quota = commentList.get("quota_remaining").getAsInt();
        return commentList;
    }

    public JsonObject getMentions(Instant fromTimestamp) throws IOException{
        JsonObject mentionsList = getMentions(fromTimestamp, Integer.parseInt(userId),site,apiKey);
        JsonUtils.handleBackoff(mentionsList);
        quota = mentionsList.get("quota_remaining").getAsInt();
        return mentionsList;
    }

    public int getQuota(){
        return quota;
    }

    private JsonObject getQuestionDetailsByIds(List<Integer> questionIdList, String site, String apiKey) throws IOException {
        String questionIds = questionIdList.stream().map(String::valueOf).collect(Collectors.joining(";"));
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionIds;
        return JsonUtils.get(questionIdUrl,"site",site,"pagesize",String.valueOf(questionIdList.size()),"key",apiKey);
    }

    private JsonObject getQuestionDetailsById(Integer questionId, String site, String apiKey) throws IOException{
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionId;
        return JsonUtils.get(questionIdUrl,"site",site,"key",apiKey);
    }

    private JsonObject getAnswerDetailsById(Integer answerId, String site, String apiKey) throws IOException{
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerId;
        return JsonUtils.get(answerIdUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","site",site,"key",apiKey,"sort","creation");
    }

    private JsonObject getAnswerDetailsByIds(List<Integer> answerIdList, String site, String apiKey) throws IOException{
        String answerIds = answerIdList.stream().map(String::valueOf).collect(Collectors.joining(";"));
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerIds;
        return JsonUtils.get(answerIdUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","site",site,"pagesize",String.valueOf(answerIdList.size()),"key",apiKey,"sort","creation");
    }

    private JsonObject getFirstPageOfAnswers(Instant fromTimestamp, String site, String apiKey) throws IOException{
        String answersUrl = "https://api.stackexchange.com/2.2/answers";
        return JsonUtils.get(answersUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","fromdate",String.valueOf(fromTimestamp.minusSeconds(1).getEpochSecond()),"site",site,"key",apiKey,"sort","creation");
    }

    private JsonObject getAnswerFlagOptions(Integer answerId, String site, String apiKey, String token) throws IOException{
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerId+"/flags/options";
        return JsonUtils.get(answerIdUrl,"site",site,"key",apiKey,"access_token",token);
    }

    private JsonObject FlagAnswer(Integer answerId, Integer flagType, String site, String apiKey, String token) throws IOException{
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerId+"/flags/add";
        return JsonUtils.post(answerIdUrl,"option_id",Integer.toString(flagType),"site",site,"key",apiKey,"access_token",token);
    }

    private JsonObject addComment(String comment, Integer postID, String site, String apiKey, String token) throws IOException{
        String commentsUrl = "https://api.stackexchange.com/2.2/posts/"+postID+"/comments/add";
        return JsonUtils.post(commentsUrl,"body",comment,"site",site,"key",apiKey,"access_token",token);
    }

    private JsonObject deleteComment(Integer commentID, String site, String apiKey, String token) throws IOException{
        String commentsUrl = "https://api.stackexchange.com/2.2/comments/"+commentID+"/delete";
        return JsonUtils.post(commentsUrl,"site",site,"key",apiKey,"access_token",token);
    }

    private JsonObject getComments(Integer userId, String site, String apiKey) throws IOException{
        String commentsUrl = "https://api.stackexchange.com/2.2/users/"+userId+"/comments";
        return JsonUtils.get(commentsUrl,"site",site,"key",apiKey, "filter","!9YdnSOQH3");
    }

    private JsonObject getMentions(Instant fromTimestamp, Integer userId, String site, String apiKey) throws IOException{
        String mentionUrl = "https://api.stackexchange.com/2.2/users/"+userId+"/mentioned";
        return JsonUtils.get(mentionUrl,"site",site,"key",apiKey,"fromdate",String.valueOf(fromTimestamp.minusSeconds(1).getEpochSecond()),"filter","!bZA*iTYWJS8yRg");
    }


}
