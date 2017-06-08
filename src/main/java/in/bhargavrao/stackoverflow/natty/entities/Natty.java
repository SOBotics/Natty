package in.bhargavrao.stackoverflow.natty.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import in.bhargavrao.stackoverflow.natty.services.ApiService;
import in.bhargavrao.stackoverflow.natty.utils.*;
import in.bhargavrao.stackoverflow.natty.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



/**
 * Created by bhargav.h on 10-Sep-16.
 */
public class Natty {


    private Instant previousAnswerTimestamp;
    private ApiService apiService;
    private String siteName;
    private String siteUrl;

    public Natty(String sitename, String siteurl) {
        previousAnswerTimestamp = Instant.now().minusSeconds(60);
        apiService = new ApiService(sitename);
        this.siteName = sitename;
        this.siteUrl = siteurl;
    }

    public List<Post> getPosts(Validator validator) throws IOException{
        ArrayList<Post> posts = new ArrayList<>();

        JsonObject answersJson = apiService.getFirstPageOfAnswers(previousAnswerTimestamp);
        if (answersJson.has("items")) {
            JsonArray answers = answersJson.get("items").getAsJsonArray();

            List<Integer> questionIdList = StreamSupport.stream(answers.spliterator(),false).map(x -> x.getAsJsonObject().get("question_id").getAsInt()).collect(Collectors.toList());

            JsonObject questionsJson = apiService.getQuestionDetailsByIds(questionIdList);

            if(questionsJson.has("items")){
                JsonArray questions = questionsJson.get("items").getAsJsonArray();

                Map<Integer,JsonObject> questionMap = new HashMap<>();
                for(JsonElement j: questions){
                    Integer questionId = j.getAsJsonObject().get("question_id").getAsInt();
                    questionMap.put(questionId,j.getAsJsonObject());
                }

                for(JsonElement j: answers) {
                    JsonObject answer = j.getAsJsonObject();
                    Integer questionId = answer.get("question_id").getAsInt();
                    if(questionMap.containsKey(questionId))
                    {
                        Post np = PostUtils.getPost(answer, questionMap.get(questionId));
                        np.setSiteName(siteName);
                        np.setSiteUrl(siteUrl);
                        Instant answerCreationDate = np.getAnswerCreationDate();
                        if (previousAnswerTimestamp.isAfter(answerCreationDate) ||
                                previousAnswerTimestamp.equals(answerCreationDate)) {
                            continue;
                        }
                        if (validator.validate(np)) {
                            previousAnswerTimestamp = answerCreationDate;
                            posts.add(np);
                        }
                    }
                    else{
                        System.out.println("MISSING KEY "+questionId);
                    }
                }
            }
        }
        return posts;
    }

    public Post checkPost(int answerId) throws IOException{
        JsonObject answerApiJson = apiService.getAnswerDetailsById(answerId);
        if(answerApiJson.has("items")) {
            JsonObject answer = answerApiJson.getAsJsonArray("items").get(0).getAsJsonObject();
            int questionId = answer.get("question_id").getAsInt();
            JsonObject questionApiJson = apiService.getQuestionDetailsById(questionId);
            if(questionApiJson.has("items")){
                JsonObject question = questionApiJson.getAsJsonArray("items").get(0).getAsJsonObject();
                Post np =  PostUtils.getPost(answer,question);
                np.setSiteName(siteName);
                np.setSiteUrl(siteUrl);
                return  np;
            }
        }
        return null;
    }
}
