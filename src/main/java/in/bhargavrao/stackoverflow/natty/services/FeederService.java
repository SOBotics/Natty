package in.bhargavrao.stackoverflow.natty.services;

import org.sobotics.chatexchange.chat.Room;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by bhargav.h on 28-Dec-16.
 */
public class FeederService {
    private String message;
    private Room room;
    private int nHrs;
    private ScheduledExecutorService feederService;

    public FeederService(String message, Room room, int nHrs) {
        this.message = message;
        this.room = room;
        this.nHrs = nHrs;
        feederService = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(){
        Runnable feeder = () -> room.send(message);
        feederService.scheduleAtFixedRate(feeder, 1, nHrs, TimeUnit.HOURS);
    }

    public void stop(){
        feederService.shutdown();
    }
}
