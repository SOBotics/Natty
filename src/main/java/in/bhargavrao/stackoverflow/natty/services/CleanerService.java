package in.bhargavrao.stackoverflow.natty.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.FMSUtils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    
    public void clean() {
    	cleanAutoComments();
    	cleanOldLogfiles();
    }
    
    /**
     * Deletes old auto-comments
     * */
    private void cleanAutoComments(){
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
    
    /**
     * Deletes all FMS files that are older than 14 days
     * */
    private void cleanOldLogfiles() {
    	int keepLogsForDays = 14;
        File logsDir = new File(FMSUtils.FMSFilePath);
        
        for (File file : logsDir.listFiles()) {
        	long diff = new Date().getTime() - file.lastModified();

        	if (diff > keepLogsForDays * 24 * 60 * 60 * 1000) {
        		try {
        			file.delete();
        		} catch (SecurityException e) {
        			e.printStackTrace();
        		}
        	}
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
