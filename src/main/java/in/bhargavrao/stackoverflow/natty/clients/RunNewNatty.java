package in.bhargavrao.stackoverflow.natty.clients;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.StackExchangeClient;
import fr.tunaki.stackoverflow.chat.event.EventType;
import in.bhargavrao.stackoverflow.natty.commandlists.GMTsCommandsList;
import in.bhargavrao.stackoverflow.natty.commandlists.HeadquartersCommandsList;
import in.bhargavrao.stackoverflow.natty.commandlists.RPublicCommandsList;
import in.bhargavrao.stackoverflow.natty.commandlists.SoBoticsCommandsList;
import in.bhargavrao.stackoverflow.natty.entities.Natty;
import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.printers.GmtsPostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.HeadquartersPostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.RPublicPostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.SoBoticsPostPrinter;
import in.bhargavrao.stackoverflow.natty.roomdata.*;
import in.bhargavrao.stackoverflow.natty.services.FeederService;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.services.StatsService;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllNewAnswersValidator;
import in.bhargavrao.stackoverflow.natty.validators.AllowOnlyTagValidator;
import in.bhargavrao.stackoverflow.natty.validators.RPublicValidator;

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

        System.out.println("STARTING - "+Instant.now());

        List<BotRoom> rooms = new ArrayList<>();
        rooms.add(new SOBoticsChatRoom());
        rooms.add(new HeadquartersChatRoom());
        rooms.add(new RPublicChatRoom());
        rooms.add(new GMTsChatRoom());

        RunnerService runner = new RunnerService(client,rooms);
        runner.start();
        runner.run();

        System.out.println("LOADED  - "+Instant.now());


    }

}
