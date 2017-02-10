package in.bhargavrao.stackoverflow.natty.clients;

import fr.tunaki.stackoverflow.chat.StackExchangeClient;
import in.bhargavrao.stackoverflow.natty.roomdata.*;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by bhargav.h on 28-Dec-16.
 */
public class RunNewNatty {

    public static void main(String[] args) {

        StackExchangeClient client;

        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(FilePathUtils.loginPropertiesFile));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        client = new StackExchangeClient(prop.getProperty("email"), prop.getProperty("password"));

        boolean isOnServer = prop.getProperty("location") != null && prop.getProperty("location").equals("server");

        List<BotRoom> rooms = new ArrayList<>();
        rooms.add(new SOBoticsChatRoom());
        if (isOnServer) {
        	rooms.add(new HeadquartersChatRoom());
        	rooms.add(new RPublicChatRoom());
        	rooms.add(new GMTsChatRoom());
        }

        RunnerService runner = new RunnerService(client,rooms);
        runner.start();
        runner.run();

        StatusUtils.startupDate = Instant.now();
        System.out.println("LOADED  - "+Instant.now());


    }

}
