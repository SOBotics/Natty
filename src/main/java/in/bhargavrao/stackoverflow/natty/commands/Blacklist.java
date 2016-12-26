package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;

import java.io.IOException;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Blacklist implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Blacklist(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"blacklist");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.blacklistFile;
            String data = CommandUtils.extractData(message);
            if (FileUtils.checkIfInFile(filename, data)) {
                room.replyTo(event.getMessage().getId(), "Already Blacklisted");
            }
            else{
                FileUtils.appendToFile(filename,data);
                room.replyTo(event.getMessage().getId(), "Added blacklist successfully");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occured, Try again");
        }
    }

    @Override
    public String description() {
        return "Adds a given statement to the list of blacklisted words";
    }

    @Override
    public String name() {
        return "blacklist";
    }
}
