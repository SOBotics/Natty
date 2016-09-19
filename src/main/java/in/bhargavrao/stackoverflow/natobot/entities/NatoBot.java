package in.bhargavrao.stackoverflow.natobot.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import in.bhargavrao.stackoverflow.natobot.utils.JsonUtils;
import in.bhargavrao.stackoverflow.natobot.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.parser.Parser;

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

    private String apiKey = "kmtAuIIqwIrwkXm1*p3qqA((";
    private String filter = "!40nvjHa_IL(lxIFT9";
    private String site = "stackoverflow";
    private Instant previousAnswerTimestamp;

    public NatoBot() {
        previousAnswerTimestamp = Instant.now().minusSeconds(60);
    }

    public JsonObject getQuestionDetailsByIds(List<Integer> questionIdList) throws IOException{
        String questionIds = questionIdList.stream().map(String::valueOf).collect(Collectors.joining(";"));
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionIds;
        JsonObject questionJson = JsonUtils.get(questionIdUrl,"site",site,"key",apiKey);
        return questionJson;
    }

    public JsonObject getQuestionDetailsById(Integer questionId) throws IOException{
        String questionIdUrl = "https://api.stackexchange.com/2.2/questions/"+questionId;
        JsonObject questionJson = JsonUtils.get(questionIdUrl,"site",site,"key",apiKey);
        return questionJson;
    }

    public JsonObject getFirstPageOfAnswers() throws IOException{
        String answersUrl = "https://api.stackexchange.com/2.2/answers";
        JsonObject answersJson = JsonUtils.get(answersUrl,"order","asc","sort","creation","filter",filter,"page","1","pagesize","100","fromdate",String.valueOf(previousAnswerTimestamp.minusSeconds(1).getEpochSecond()),"site",site,"key",apiKey,"sort","creation");
        return answersJson;
    }


    private String escapeHtmlEncoding(String message) {
        return Parser.unescapeEntities(JsonUtils.sanitizeChatMessage(message), false).trim();
    }

    public NatoPost getNatoPost(JsonObject answer, JsonObject question){

        NatoPost np = new NatoPost();

        JsonObject user = answer.get("owner").getAsJsonObject();

        np.setAnswerCreationDate(Instant.ofEpochSecond(answer.get("creation_date").getAsInt()));
        np.setAnswerID(answer.get("answer_id").getAsInt());
        np.setQuestionCreationDate(Instant.ofEpochSecond(question.get("creation_date").getAsInt()));
        np.setQuestionID(answer.get("question_id").getAsInt());
        np.setReputation(user.get("reputation").getAsInt());
        np.setTitle(escapeHtmlEncoding(question.get("title").getAsString()));
        np.setMainTag(question.get("tags").getAsJsonArray().get(0).getAsString());
        np.setTags(StreamSupport.stream(question.get("tags").getAsJsonArray().spliterator(), false).map(JsonElement::getAsString).toArray(String[]::new));
        np.setBody(answer.get("body").getAsString());
        np.setBodyMarkdown(escapeHtmlEncoding(answer.get("body_markdown").getAsString()));
        np.setUserName(escapeHtmlEncoding(user.get("display_name").getAsString()));
        np.setUserType(user.get("user_type").getAsString());
        np.setUserID(user.get("user_id").getAsInt());

        return np;

    }

    public List<NatoPost> getNatoAnswers(Validator validator) throws IOException{
        ArrayList<NatoPost> natoAnswers = new ArrayList<>();

        JsonObject answersJson = getFirstPageOfAnswers();
        JsonUtils.handleBackoff(LOGGER, answersJson);
        if (answersJson.has("items")) {
            JsonArray answers = answersJson.get("items").getAsJsonArray();

            List<Integer> questionIdList = StreamSupport.stream(answers.spliterator(),false).map(x -> x.getAsJsonObject().get("question_id").getAsInt()).collect(Collectors.toList());

            JsonObject questionsJson = getQuestionDetailsByIds(questionIdList);
            JsonUtils.handleBackoff(LOGGER, questionsJson);

            if(questionsJson.has("items")){
                JsonArray questions = questionsJson.get("items").getAsJsonArray();

                Map<Integer,JsonObject> questionMap = new HashMap<>();
                for(JsonElement j: questions){
                    Integer questionId = j.getAsJsonObject().get("question_id").getAsInt();
                    questionMap.put(questionId,j.getAsJsonObject());
                }

                for(JsonElement j: answers){
                    JsonObject answer = j.getAsJsonObject();
                    Integer questionId = answer.get("question_id").getAsInt();
                    NatoPost np = getNatoPost(answer, questionMap.get(questionId));
                    np.setQuota(questionsJson.get("quota_remaining").getAsInt());
                    Instant answerCreationDate = np.getAnswerCreationDate();
                    if(previousAnswerTimestamp.isAfter(answerCreationDate) ||
                            previousAnswerTimestamp.equals(answerCreationDate)){
                        continue;
                    }
                    if(validator.validate(np)){
                        previousAnswerTimestamp = answerCreationDate;
                        natoAnswers.add(np);
                    }
                }
            }
        }
        return natoAnswers;
    }

}
