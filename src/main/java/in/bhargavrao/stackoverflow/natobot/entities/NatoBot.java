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

    public JsonObject getQuestionDetailsByIds(String questionIds) throws IOException{
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

    public NatoPost getNatoPost(JsonObject answer, JsonObject question){

        NatoPost np = new NatoPost();

        // Answer Data

        Integer questionId = answer.get("question_id").getAsInt();
        Integer answerId = answer.get("answer_id").getAsInt();
        Integer answerCreationDate = answer.get("creation_date").getAsInt();
        String answerBody = answer.get("body").getAsString();
        String answerBodyMarkdown = Parser.unescapeEntities(JsonUtils.sanitizeChatMessage(answer.get("body_markdown").getAsString()), false).trim();

        // User Data

        JsonObject user = answer.get("owner").getAsJsonObject();
        Integer reputation = user.get("reputation").getAsInt();
        String username = Parser.unescapeEntities(JsonUtils.sanitizeChatMessage(user.get("display_name").getAsString()), false).trim();
        String usertype = user.get("user_type").getAsString();
        Integer userId = user.get("user_id").getAsInt();

        // Question Data

        String mainTag = question.get("tags").getAsJsonArray().get(0).getAsString();
        String tags[] = StreamSupport.stream(question.get("tags").getAsJsonArray().spliterator(), false).map(JsonElement::getAsString).toArray(String[]::new);
        Integer questionCreationDate = question.get("creation_date").getAsInt();
        String questionTitle = Parser.unescapeEntities(JsonUtils.sanitizeChatMessage(question.get("title").getAsString()), false).trim();

        np.setAnswerCreationDate(Instant.ofEpochSecond(answerCreationDate));
        np.setAnswerID(answerId);
        np.setQuestionCreationDate(Instant.ofEpochSecond(questionCreationDate));
        np.setQuestionID(questionId);
        np.setReputation(reputation);
        np.setTitle(questionTitle);
        np.setMainTag(mainTag);
        np.setTags(tags);
        np.setBody(answerBody);
        np.setBodyMarkdown(answerBodyMarkdown);
        np.setUserName(username);
        np.setUserType(usertype);
        np.setUserID(userId);

        return np;

    }

    public List<NatoPost> getNatoAnswers(Validator validator) throws IOException{
        ArrayList<NatoPost> natoAnswers = new ArrayList<>();

        JsonObject answersJson = getFirstPageOfAnswers();
        if (answersJson.has("items")) {
            JsonArray answers = answersJson.get("items").getAsJsonArray();

            String questionIdList = StreamSupport.stream(answers.spliterator(),false).map(x -> x.getAsJsonObject().get("question_id").getAsString()).collect(Collectors.joining(";"));

            JsonObject questionsJson = getQuestionDetailsByIds(questionIdList);

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
