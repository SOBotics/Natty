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
public class OptOut implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public OptOut(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"opt-out");
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
            if(FileUtils.checkIfInFile(filename,optMessage)) {
                FileUtils.removeFromFile(filename, optMessage);
                System.out.println("Remove user");
                room.replyTo(event.getMessage().getId(), "You've been removed.");
            }
            else{
                room.replyTo(event.getMessage().getId(), "You've already been removed.");
                room.replyTo(event.getMessage().getId(), "Or did you not opt-in only? o_O");
            }
        }
        catch(IOException e)
        {
            System.out.println("File not found");
        }
    }
}
