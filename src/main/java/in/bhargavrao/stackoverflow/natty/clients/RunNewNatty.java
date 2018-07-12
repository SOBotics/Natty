package in.bhargavrao.stackoverflow.natty.clients;

import in.bhargavrao.stackoverflow.natty.roomdata.*;
import in.bhargavrao.stackoverflow.natty.services.PropertyService;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;
import org.sobotics.chatexchange.chat.StackExchangeClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhargav.h on 28-Dec-16.
 */
public class RunNewNatty {

    public static void main(String[] args) {

        StackExchangeClient client;

        PropertyService service = new PropertyService();

        client = new StackExchangeClient(service.getEmail(), service.getPassword());

        boolean isOnServer = service.getLocation() != null && service.getLocation().equals("server");

        List<BotRoom> rooms = new ArrayList<>();
        if (!isOnServer){
            rooms.add(new SOBoticsWorkshopChatRoom());
        }
        else  {
            rooms.add(new SOBoticsChatRoom());
        	rooms.add(new HeadquartersChatRoom());
        	rooms.add(new RPublicChatRoom());
        	rooms.add(new GMTsChatRoom());
            rooms.add(new SeboticsRoom());
            rooms.add(new RaidersRoom());
        }

        RunnerService runner = new RunnerService(client,rooms);
        runner.start();
        runner.run();

        StatusUtils.startupDate = Instant.now();
        System.out.println("LOADED  - "+Instant.now());

    }
}
