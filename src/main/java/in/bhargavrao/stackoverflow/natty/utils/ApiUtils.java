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

    private static final String filter = "!40nvjHa_IL(lxIFT9";


    public static JsonObject getQuestionDetailsByIds(List<Integer> questionIdList, String site, String apiKey) throws IOException {
        String questionIds = questionIdList.stream().map(String::valueOf).collect(Collectors.joining(";"));
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionIds;
        return JsonUtils.get(questionIdUrl,"site",site,"pagesize",String.valueOf(questionIdList.size()),"key",apiKey);
    }

    public static JsonObject getQuestionDetailsById(Integer questionId, String site, String apiKey) throws IOException{
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionId;
        return JsonUtils.get(questionIdUrl,"site",site,"key",apiKey);
    }

    public static JsonObject getAnswerDetailsById(Integer answerId, String site, String apiKey) throws IOException{
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerId;
        return JsonUtils.get(answerIdUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","site",site,"key",apiKey,"sort","creation");
    }

    public static JsonObject getAnswerDetailsByIds(List<Integer> answerIdList, String site, String apiKey) throws IOException{
        String answerIds = answerIdList.stream().map(String::valueOf).collect(Collectors.joining(";"));
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerIds;
        return JsonUtils.get(answerIdUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","site",site,"pagesize",String.valueOf(answerIdList.size()),"key",apiKey,"sort","creation");
    }

    public static JsonObject getFirstPageOfAnswers(Instant fromTimestamp, String site, String apiKey) throws IOException{
        String answersUrl = "https://api.stackexchange.com/2.2/answers";
        return JsonUtils.get(answersUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","fromdate",String.valueOf(fromTimestamp.minusSeconds(1).getEpochSecond()),"site",site,"key",apiKey,"sort","creation");
    }

    public static JsonObject getAnswerFlagOptions(Integer answerId, String site, String apiKey, String token) throws IOException{
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerId+"/flags/options";
        return JsonUtils.get(answerIdUrl,"site",site,"key",apiKey,"access_token",token);
    }

    public static JsonObject FlagAnswer(Integer answerId, Integer flagType, String site, String apiKey, String token) throws IOException{
        String answerIdUrl = "https://api.stackexchange.com/2.2/answers/"+answerId+"/flags/add";
        return JsonUtils.post(answerIdUrl,"option_id",Integer.toString(flagType),"site",site,"key",apiKey,"access_token",token);
    }
}
