package in.bhargavrao.stackoverflow.natty.utils;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 29-Sep-16.
 */
public class ApiUtils {

    private static final String apiKey = "kmtAuIIqwIrwkXm1*p3qqA((";
    private static final String filter = "!40nvjHa_IL(lxIFT9";
    private static final String site = "stackoverflow";

    private static int quota=0;

    public static JsonObject getQuestionDetailsByIds(List<Integer> questionIdList) throws IOException {
        String questionIds = questionIdList.stream().map(String::valueOf).collect(Collectors.joining(";"));
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionIds;
        JsonObject questionJson = JsonUtils.get(questionIdUrl,"site",site,"pagesize",String.valueOf(questionIdList.size()),"key",apiKey);
        quota = questionJson.get("quota_remaining").getAsInt();
        return questionJson;
    }

    public static JsonObject getQuestionDetailsById(Integer questionId) throws IOException{
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionId;
        JsonObject questionJson = JsonUtils.get(questionIdUrl,"site",site,"key",apiKey);
        quota = questionJson.get("quota_remaining").getAsInt();
        return questionJson;
    }

    public static JsonObject getAnswerDetailsById(Integer answerId) throws IOException{
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerId;
        JsonObject answerJson = JsonUtils.get(answerIdUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","site",site,"key",apiKey,"sort","creation");
        quota = answerJson.get("quota_remaining").getAsInt();
        return answerJson;
    }

    public static JsonObject getAnswerDetailsByIds(List<Integer> answerIdList) throws IOException{
        String answerIds = answerIdList.stream().map(String::valueOf).collect(Collectors.joining(";"));
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerIds;
        JsonObject answerJson = JsonUtils.get(answerIdUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","site",site,"pagesize",String.valueOf(answerIdList.size()),"key",apiKey,"sort","creation");
        quota = answerJson.get("quota_remaining").getAsInt();
        return answerJson;
    }

    public static JsonObject getFirstPageOfAnswers(Instant fromTimestamp) throws IOException{
        String answersUrl = "https://api.stackexchange.com/2.2/answers";
        JsonObject answersJson = JsonUtils.get(answersUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","fromdate",String.valueOf(fromTimestamp.minusSeconds(1).getEpochSecond()),"site",site,"key",apiKey,"sort","creation");
        quota = answersJson.get("quota_remaining").getAsInt();
        return answersJson;
    }

    public static int getQuota(){
        return quota;
    }
}
