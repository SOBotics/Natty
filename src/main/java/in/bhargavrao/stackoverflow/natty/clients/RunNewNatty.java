package in.bhargavrao.stackoverflow.natty.clients;

import fr.tunaki.stackoverflow.chat.StackExchangeClient;
import in.bhargavrao.stackoverflow.natty.roomdata.*;
import in.bhargavrao.stackoverflow.natty.services.PropertyService;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;

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
        rooms.add(new SOBoticsChatRoom());
        if (isOnServer) {
        	rooms.add(new HeadquartersChatRoom());
        	rooms.add(new RPublicChatRoom());
        	rooms.add(new GMTsChatRoom());
            rooms.add(new TemporaryRoom());
        }

        RunnerService runner = new RunnerService(client,rooms);
        runner.start();
        runner.run();

        StatusUtils.startupDate = Instant.now();
        System.out.println("LOADED  - "+Instant.now());

    }
}
