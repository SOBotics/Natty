package in.bhargavrao.stackoverflow.natobot.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.commands.*;
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

    public void mention(Room room, PingMessageEvent event, boolean isReply){

        List<SpecialCommand> commands = new ArrayList<SpecialCommand>(){{
            add(new AddCheckUser(event));
            add(new AddSalute(event));
            add(new Alive(event));
            add(new Blacklist(event));
            add(new Check(event));
            add(new Commands(event));
            add(new Help(event));
            add(new Help(event));
            add(new Hi(event));
            add(new IsBlacklisted(event));
            add(new IsWhitelisted(event));
            add(new OptIn(event));
            add(new OptOut(event));
            add(new Quota(event));
            add(new Remind(event));
            add(new RemoveBlackList(event));
            add(new RemoveRequest(event));
            add(new RemoveWhitelist(    event));
            add(new Say(event));
            add(new ShowRequests(event));
            add(new Status(event));
            add(new Whitelist(event));
            add(new WishBirthday(event));
        }};

        for(SpecialCommand command: commands){
            if(command.validate()){
                command.execute(room);
            }
        }
        System.out.println(event.getMessage().getContent());
    }

    public int runOnce(Room room,  List<Validator> validators, NatoBot cc, int naaValueLimit){
        int numOfAnswers = 0;
        try{
            List<NatoPost> natoAnswers = cc.getNatoAnswers(new AllowAllNatoValidator());
            System.out.println(natoAnswers);
            for (NatoPost np : natoAnswers) {
                boolean sent = false;

                NatoPostPrinter pp = new NatoPostPrinter(np).addMainTag().addQuesionLink().addBodyLength().addReputation();

                List<OptedInUser> pingUsersList = UserUtils.pingUserIfApplicable(np,room.getRoomId());

                List<Object> returnValues = NatoUtils.getNaaValue(np, pp);
                Double naaValue = (Double) returnValues.get(0);
                pp = (NatoPostPrinter) returnValues.get(1);

                boolean validate = true;

                for (Validator validator : validators){
                    validate = validate && validator.validate(np);
                }

                if (validate){
                    pp.addFirstLine();
                    pp.addMessage(" **"+naaValue+"**;");

                    for (OptedInUser user : pingUsersList) {
                        if (!user.isWhenInRoom() || (user.isWhenInRoom() && UserUtils.checkIfUserInRoom(room, user.getUser().getUserId()))) {
                            pp.addMessage(" @"+user.getUser().getUsername().replace(" ",""));
                        }
                        if(user.getPostType().equals("all")){
                            numOfAnswers++;
                            room.send(pp.print());
                            sent = true;
                        }
                    }

                    if (!sent && naaValue>naaValueLimit)
                        room.send(pp.print());
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return numOfAnswers;
    }
}
