package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class OptIn implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public OptIn(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"opt-in");
    }

    @Override
    public void execute(Room room) {
        long userId = event.getUserId();
        String userName = event.getUserName();
        String filename = ".\\src\\main\\resources\\lib\\OptedInUsersList.txt";
        String tag = StringUtils.substringBetween(message,"[","]");
        try
        {
            String optMessage = userId+","+tag+",\""+userName+"\""+","+room.getRoomId();
            if(FileUtils.checkIfInFile(filename,optMessage)){
                room.replyTo(event.getMessage().getId(), "You've already been added.");
            }
            else {
                FileUtils.appendToFile(filename, optMessage);
                System.out.println("Added user");
                room.replyTo(event.getMessage().getId(), "You've been added.");
            }
        }
        catch(IOException e)
        {
            System.out.println("File not found");
        }
    }
}
