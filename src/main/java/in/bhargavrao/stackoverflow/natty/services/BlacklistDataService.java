package in.bhargavrao.stackoverflow.natty.services;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.StackExchangeClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BlacklistDataService {
	private StackExchangeClient client;
    private Room chatRoom;
    private ScheduledExecutorService executorService;


    public BlacklistDataService(Room chatroom) {
        this.client = client;
        this.chatRoom = chatroom;
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

        PropertyService propertyService = new PropertyService();
        
        String apiKey = propertyService.getSentinelApiKey();
        if (apiKey.length() == 0)
        	return;
        
        String urlString = "http://sentinel.erwaysoftware.com/api/blacklist_stats?key="+apiKey;
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
        con.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(  
                new InputStreamReader(con.getInputStream()));  
        String output;
        StringBuffer response = new StringBuffer();  
        
        while ((output = in.readLine()) != null) {  
        	response.append(output);  
        }  
        in.close();
        
        String responseString = response.toString();
        
        StorageService service = new FileStorageService();
        service.addIntelligentBlacklistJson(responseString);
        
    }
    
    
    
    
}
