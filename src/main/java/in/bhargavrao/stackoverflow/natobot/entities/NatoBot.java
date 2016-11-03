package in.bhargavrao.stackoverflow.natobot.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.commands.*;
import in.bhargavrao.stackoverflow.natobot.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natobot.utils.*;
import in.bhargavrao.stackoverflow.natobot.validators.AllowAllNatoValidator;
import in.bhargavrao.stackoverflow.natobot.validators.Validator;
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
public class NatoBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(NatoBot.class);

    private Instant previousAnswerTimestamp;

    public NatoBot() {
        previousAnswerTimestamp = Instant.now().minusSeconds(60);
    }

    public List<NatoPost> getNatoAnswers(Validator validator) throws IOException{
        ArrayList<NatoPost> natoAnswers = new ArrayList<>();

        JsonObject answersJson = ApiUtils.getFirstPageOfAnswers(previousAnswerTimestamp);
        JsonUtils.handleBackoff(LOGGER, answersJson);
        if (answersJson.has("items")) {
            JsonArray answers = answersJson.get("items").getAsJsonArray();

            List<Integer> questionIdList = StreamSupport.stream(answers.spliterator(),false).map(x -> x.getAsJsonObject().get("question_id").getAsInt()).collect(Collectors.toList());

            JsonObject questionsJson = ApiUtils.getQuestionDetailsByIds(questionIdList);
            JsonUtils.handleBackoff(LOGGER, questionsJson);

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
                        NatoPost np = NatoUtils.getNatoPost(answer, questionMap.get(questionId));
                        Instant answerCreationDate = np.getAnswerCreationDate();
                        if (previousAnswerTimestamp.isAfter(answerCreationDate) ||
                                previousAnswerTimestamp.equals(answerCreationDate)) {
                            continue;
                        }
                        if (validator.validate(np)) {
                            previousAnswerTimestamp = answerCreationDate;
                            natoAnswers.add(np);
                        }
                    }
                    else{
                        System.out.println("MISSING KEY "+questionId);
                    }
                }
            }
        }
        return natoAnswers;
    }

    public NatoPost checkNatoPost(int answerId) throws IOException{
        JsonObject answerApiJson = ApiUtils.getAnswerDetailsById(answerId);
        JsonUtils.handleBackoff(LOGGER,answerApiJson);
        if(answerApiJson.has("items")) {
            JsonObject answer = answerApiJson.getAsJsonArray("items").get(0).getAsJsonObject();
            int questionId = answer.get("question_id").getAsInt();
            JsonObject questionApiJson = ApiUtils.getQuestionDetailsById(questionId);
            JsonUtils.handleBackoff(LOGGER,questionApiJson);
            if(questionApiJson.has("items")){
                JsonObject question = questionApiJson.getAsJsonArray("items").get(0).getAsJsonObject();
                return NatoUtils.getNatoPost(answer,question);
            }
        }
        return null;
    }
}
