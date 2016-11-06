package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Remind implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Remind(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"remind");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.featureRequests;
            String data = CommandUtils.extractData(message);
            if (FileUtils.checkIfInFile(filename, data)) {
                room.replyTo(event.getMessage().getId(), "Already present as FR");
            }
            else{
                if(data.trim().equals("")) {
                    room.replyTo(event.getMessage().getId(), "The code is made Tuna Proof™");
                }
                else {
                    FileUtils.appendToFile(filename, data);
                    room.replyTo(event.getMessage().getId(), "Added request Successfully");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occured, Try again");
        }
    }

    @Override
    public String description() {
        return "Adds a reminder to the list of requests";
    }

    @Override
    public String name() {
        return "remind";
    }
}
