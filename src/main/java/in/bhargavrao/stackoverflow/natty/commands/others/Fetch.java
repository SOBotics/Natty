package in.bhargavrao.stackoverflow.natty.commands.others;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import in.bhargavrao.stackoverflow.natty.services.ApiService;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.PropertyService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.SentinelUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Fetch extends NormalCommand {

    private Message message;
    private String sitename;
    private String siteurl;

    public Fetch(Message message, String sitename, String siteurl) {
        super(message, "fetch");
        this.message = message;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }


    @Override
    public String description() {
        return "Returns a list of posts that need feedback, See the wiki for more details";
    }

    @Override
    public void execute(Room room) {

        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        StorageService service = new FileStorageService();
        PropertyService propertyService = new PropertyService();
        try{
            List<String> lines = service.getReports(sitename);
            String returnString = String.join("; ", lines);


            if (lines.size() == 0) {
                room.send("All reports have been tended to");
                return;
            }

            String[] splits = data.split(" ");
            String option;
            try {
                option = splits[0].toLowerCase();
            }
            catch (ArrayIndexOutOfBoundsException e){
                room.send(returnString);
                return;
            }

            int stopValue = lines.size();
            if(splits.length==2&&CheckUtils.checkIfInteger(splits[1])){
                stopValue = Integer.parseInt(splits[1]);
            }


            switch (option) {
                case "links": {

                    StringBuilder links = new StringBuilder();
                    int i = 0;
                    for (String line : lines) {
                        links.append("[").append(line.trim()).append("](//").append(siteurl).append("/a/").append(line.trim()).append("); ");
                        i++;
                        if (i == stopValue)
                            break;
                    }
                    room.send(links.toString());
                    break;
                }
                case "fms": {

                    StringBuilder links = new StringBuilder();

                    propertyService.getFMSUrl();
                    int i = 0;
                    for (String line : lines) {

                        String postId = line.trim();
                        String sentinelId = service.getSentinelId(postId, sitename);
                        if (sentinelId==null || sentinelId.equals("-1")) {
                            links.append("[").append(line.trim()).append("](").append(propertyService.getFMSUrl()).append("/").append(line.trim()).append(".html); ");
                        }
                        else {
                            links.append(postId).append("; ");
                        }
                        i++;
                        if (i == stopValue)
                            break;
                    }
                    room.send(links.toString());
                    break;
                }
                case "deleted": {
                    if (lines.size() > 100 && stopValue > 100) {
                        room.send("There are more than 100 requests. Hence fetching deleted posts from the first 100 only");
                        lines = lines.subList(0, 100);
                    }

                    ApiService apiService = new ApiService(siteurl);
                    List<Integer> answerIds = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
                    JsonObject answersJson = apiService.getAnswerDetailsByIds(answerIds);

                    if (answersJson.has("items")) {
                        JsonArray answers = answersJson.get("items").getAsJsonArray();
                        for (JsonElement element : answers) {
                            JsonObject answer = element.getAsJsonObject();
                            String answerId = answer.get("answer_id").getAsString();
                            int index = lines.indexOf(answerId);
                            if (index != -1) {
                                lines.remove(index);
                            }
                        }
                    }
                    StringBuilder links = new StringBuilder();
                    int i = 0;
                    for (String line : lines) {
                        links.append("[").append(line.trim()).append("](//").append(siteurl).append("/a/").append(line.trim()).append("); ");
                        i++;
                        if (i == stopValue)
                            break;
                    }

                    if (links.toString().trim().equals(""))
                        links = new StringBuilder("There are no deleted posts");
                    room.send(links.toString());
                    break;
                }
                case "nondeleted": {
                    if (lines.size() > 100 && stopValue > 100) {
                        room.send("There are more than 100 requests. Hence fetching non deleted posts from the first 100 only");
                        lines = lines.subList(0, 100);
                    }
                    ApiService apiService = new ApiService(siteurl);
                    List<Integer> answerIds = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
                    JsonObject answersJson = apiService.getAnswerDetailsByIds(answerIds);
                    StringBuilder links = new StringBuilder();

                    int i = 0;
                    if (answersJson.has("items")) {
                        JsonArray answers = answersJson.get("items").getAsJsonArray();
                        for (JsonElement element : answers) {
                            JsonObject answer = element.getAsJsonObject();
                            String answerId = answer.get("answer_id").getAsString();
                            links.append("[").append(answerId).append("](//").append(siteurl).append("/a/").append(answerId).append("); ");
                            i++;
                            if (i == stopValue)
                                break;
                        }
                    }

                    if (links.toString().trim().equals(""))
                        links = new StringBuilder("There are no non deleted posts");
                    room.send(links.toString());
                    break;
                }
                case "sentinel": {
                    StringBuilder links = new StringBuilder();
                    int i = 0;
                    for (String line : lines) {
                        String postId = line.trim();
                        String sentinelId = service.getSentinelId(postId, sitename);
                        if (sentinelId==null || sentinelId.equals("-1")) {
                            links.append(postId).append("; ");
                        } else {
                            sentinelId = sentinelId.replace(postId + ",", "");
                            links.append("[").append(postId).append("](").append(SentinelUtils.getSentinelMainUrl(sitename)).append("/posts/").append(sentinelId).append("); ");
                        }
                        i++;
                        if (i == stopValue)
                            break;
                    }
                    room.send(links.toString());
                    break;
                }
                case "amount":
                case "count":
                case "number":
                    room.send(Integer.toString(lines.size()));
                    break;
                default:
                    room.send(returnString);
                    break;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}
