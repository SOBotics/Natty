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
 * Created by bhargav.h on 30-Jan-17.
 */
public class CleanerService {
    private Room room;
    private ScheduledExecutorService cleanerService;

    public CleanerService(Room room){
        this.room = room;
        this.cleanerService = Executors.newSingleThreadScheduledExecutor();
    }
    public void clean(){
        try
        {
            ApiService service = new ApiService("stackoverflow");
            JsonObject comments = service.getComments();
            if(comments.has("items")){
                for(JsonElement e : comments.get("items").getAsJsonArray()){
                    JsonObject comment = e.getAsJsonObject();
                    if(Instant.now().isAfter(Instant.ofEpochSecond(comment.get("creation_date").getAsInt()).plus(1, ChronoUnit.DAYS))){
                        room.send("Deleting [comment](" + comment.get("link").getAsString() + ")");
                        service.deleteComment(comment.get("comment_id").getAsInt());
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void start(){
        Runnable cleaner = () -> clean();
        cleanerService.scheduleAtFixedRate(cleaner, 0, 1, TimeUnit.DAYS);
    }

    public void stop(){
        cleanerService.shutdown();
    }
}
