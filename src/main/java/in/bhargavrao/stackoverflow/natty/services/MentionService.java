package in.bhargavrao.stackoverflow.natty.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.tunaki.stackoverflow.chat.Room;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by bhargav.h on 11-Feb-17.
 */
public class MentionService {
    private Room room;
    private ScheduledExecutorService mentionService;
    private Instant previousTimeStamp;

    public MentionService(Room room){
        this.room = room;
        this.mentionService = Executors.newSingleThreadScheduledExecutor();
        this.previousTimeStamp = Instant.now().minusSeconds(300);
    }
    public void mention(){
        try
        {
            ApiService service = new ApiService("stackoverflow");
            JsonObject comments = service.getMentions(previousTimeStamp);
            previousTimeStamp = Instant.now();
            if(comments.has("items")){
                for(JsonElement e : comments.get("items").getAsJsonArray()){
                    JsonObject comment = e.getAsJsonObject();
                    room.send("***New reply to an auto-comment***");
                    room.send(comment.get("link").getAsString());
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void start(){
        Runnable mentioner = () -> mention();
        mentionService.scheduleAtFixedRate(mentioner, 0, 5, TimeUnit.MINUTES);
    }

    public void stop(){
        mentionService.shutdown();
    }

}
