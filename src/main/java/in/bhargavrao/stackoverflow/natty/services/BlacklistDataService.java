package in.bhargavrao.stackoverflow.natty.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.StackExchangeClient;
import in.bhargavrao.stackoverflow.natty.roomdata.BotRoom;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;

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
    	Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(FilePathUtils.loginPropertiesFile));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        
        String apiKey = prop.getProperty("sentinel_apikey", "");
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
        
        Files.write(Paths.get(FilePathUtils.intelligentBlacklistFile), responseString.getBytes());
        
    }
    
    
    
    
}
