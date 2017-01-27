package in.bhargavrao.stackoverflow.natty.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import com.google.gson.JsonObject;

import in.bhargavrao.stackoverflow.natty.utils.ApiUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;

/**
 * Created by bhargav.h on 29-Sep-16.
 */
public class ApiService {

    private String apiKey;
    private String autoflagKey;
    private String autoflagToken;
    private String site;

    private static int quota=0;

    public ApiService(String site){
        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(FilePathUtils.loginPropertiesFile));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        this.site = site;
        this.apiKey = prop.getProperty("apikey");
        this.autoflagKey = prop.getProperty("autoflagkey");
        this.autoflagToken = prop.getProperty("autoflagtoken");

    }

    public JsonObject getQuestionDetailsByIds(List<Integer> questionIdList) throws IOException {
        JsonObject questionJson = ApiUtils.getQuestionDetailsByIds(questionIdList,site,apiKey);
        quota = questionJson.get("quota_remaining").getAsInt();
        return questionJson;
    }

    public JsonObject getQuestionDetailsById(Integer questionId) throws IOException{
        JsonObject questionJson = ApiUtils.getQuestionDetailsById(questionId,site,apiKey);
        quota = questionJson.get("quota_remaining").getAsInt();
        return questionJson;
    }

    public JsonObject getAnswerDetailsById(Integer answerId) throws IOException{
        JsonObject answerJson = ApiUtils.getAnswerDetailsById(answerId,site,apiKey);
        quota = answerJson.get("quota_remaining").getAsInt();
        return answerJson;
    }

    public JsonObject getAnswerDetailsByIds(List<Integer> answerIdList) throws IOException{
        JsonObject answerJson = ApiUtils.getAnswerDetailsByIds(answerIdList,site,apiKey);
        quota = answerJson.get("quota_remaining").getAsInt();
        return answerJson;
    }

    public JsonObject getFirstPageOfAnswers(Instant fromTimestamp) throws IOException{
        JsonObject answersJson = ApiUtils.getFirstPageOfAnswers(fromTimestamp,site,apiKey);
        quota = answersJson.get("quota_remaining").getAsInt();
        return answersJson;
    }
    public JsonObject getAnswerFlagOptions(Integer answerId) throws IOException{
        JsonObject flagOptionsJson = ApiUtils.getAnswerFlagOptions(answerId,site,autoflagKey,autoflagToken);
        quota = flagOptionsJson.get("quota_remaining").getAsInt();
        return flagOptionsJson;
    }

    public JsonObject flagAnswer(Integer answerId, Integer flagType) throws IOException{
        JsonObject flaggedPost = ApiUtils.FlagAnswer(answerId,flagType,site,autoflagKey,autoflagToken);
        quota = flaggedPost.get("quota_remaining").getAsInt();
        return flaggedPost;
    }

    public JsonObject addComment(String comment, Integer postId) throws IOException{
        JsonObject flaggedPost = ApiUtils.addComment(comment,postId,site,autoflagKey,autoflagToken);
        quota = flaggedPost.get("quota_remaining").getAsInt();
        return flaggedPost;
    }

    public JsonObject deleteComment(Integer commentId) throws IOException{
        JsonObject flaggedPost = ApiUtils.deleteComment(commentId,site,autoflagKey,autoflagToken);
        quota = flaggedPost.get("quota_remaining").getAsInt();
        return flaggedPost;
    }

    public int getQuota(){
        return quota;
    }
}
