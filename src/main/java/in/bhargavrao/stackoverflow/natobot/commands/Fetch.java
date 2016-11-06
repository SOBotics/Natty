package in.bhargavrao.stackoverflow.natobot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.printers.SoBoticsPostPrinter;
import in.bhargavrao.stackoverflow.natobot.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Fetch implements SpecialCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(Fetch.class);
    private PingMessageEvent event;
    private String message;

    public Fetch(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message,"fetch");
    }

    @Override
    public String description() {
        return "Returns a list of posts that need feedback, See the wiki for more details";
    }

    @Override
    public void execute(Room room) {

        String data = CommandUtils.extractData(message).trim();
        String filename = FilePathUtils.outputReportLogFile;

        try{
            List<String> lines = FileUtils.readFile(filename);
            String returnString = String.join("; ", lines);

            if(data.split(" ")[0].equals("posts") && lines.size()<10 && lines.size()!=0){
                ArrayList<NatoPost> natoAnswers = new ArrayList<>();

                JsonObject answersJson = ApiUtils.getAnswerDetailsByIds(lines.stream().map(Integer::parseInt).collect(Collectors.toList()));
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
                                natoAnswers.add(np);
                            }
                            else{
                                System.out.println("MISSING KEY "+questionId);
                            }
                        }
                    }
                }

                for(NatoPost post: natoAnswers){
                    NatoReport report = NatoUtils.getNaaValue(post);
                    room.send(new SoBoticsPostPrinter().print(report));
                }
            }
            else if(data.split(" ")[0].equals("links") && lines.size()!=0) {

                if (lines.size() == 0)
                    room.replyTo(event.getMessage().getId(), "All reports have been tended to");
                else {
                    String links = "";
                    for(String line: lines) {
                        links += "["+line.trim()+"](//stackoverflow.com/a/"+line.trim()+"); ";
                    }
                    room.replyTo(event.getMessage().getId(), links);
                }
            }
            else if(data.split(" ")[0].equals("FMS") && lines.size()!=0) {

                String links = "";
                for(String line: lines) {
                    links += "["+line.trim()+"](http://51.254.218.90:8000/NATO/"+line.trim()+".html); ";
                }
                room.replyTo(event.getMessage().getId(), links);
            }
            else if(data.split(" ")[0].equals("amount") && lines.size()!=0) {
                room.replyTo(event.getMessage().getId(), Integer.toString(lines.size()));
            }
            else {

                if (lines.size() == 0)
                    room.replyTo(event.getMessage().getId(), "All reports have been tended to");
                else
                    room.replyTo(event.getMessage().getId(), returnString);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public String name() {
        return "fetch";
    }
}
