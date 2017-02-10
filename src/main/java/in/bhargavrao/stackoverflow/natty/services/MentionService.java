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

    public MentionService(Room room){
        this.room = room;
        this.mentionService = Executors.newSingleThreadScheduledExecutor();
    }
    public void clean(){
        try
        {
            ApiService service = new ApiService("stackoverflow");
            JsonObject comments = service.getMentions();
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
        Runnable cleaner = () -> clean();
        mentionService.scheduleAtFixedRate(cleaner, 0, 1, TimeUnit.DAYS);
    }

    public void stop(){
        mentionService.shutdown();
    }

}
