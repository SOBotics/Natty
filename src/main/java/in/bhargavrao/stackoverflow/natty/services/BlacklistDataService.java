package in.bhargavrao.stackoverflow.natty.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.StackExchangeClient;
import in.bhargavrao.stackoverflow.natty.roomdata.BotRoom;

public class BlacklistDataService {
	private StackExchangeClient client;
    private List<BotRoom> rooms;
    private List<Room> chatRooms;
    private ScheduledExecutorService executorService;


    public BlacklistDataService(StackExchangeClient client, List<BotRoom> rooms) {
        this.client = client;
        this.rooms = rooms;
        chatRooms = new ArrayList<>();
    }
    
    public void start() {
    	executorService = Executors.newSingleThreadScheduledExecutor();
    	executorService.scheduleAtFixedRate(()->secureExecute(), 0, 12, TimeUnit.HOURS);
    }
    
    
    private void secureExecute() {
    	try {
    		execute();
    	} catch (Throwable e) {
    		e.printStackTrace();
    	}
    }
    
    private void execute() throws Throwable {
    	
    }
    
    
    
    
}
