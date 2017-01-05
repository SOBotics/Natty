package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Remind implements SpecialCommand {

    private Message message;

    public Remind(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"remind");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.featureRequests;
            String data = CommandUtils.extractData(message.getPlainContent());
            if (FileUtils.checkIfInFile(filename, data)) {
                room.replyTo(message.getId(), "Already present as FR");
            }
            else{
                if(data.trim().equals("")) {
                    room.replyTo(message.getId(), "The code is made Tuna Proof™");
                }
                else {
                    FileUtils.appendToFile(filename, data);
                    room.replyTo(message.getId(), "Added request successfully");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(message.getId(), "Error occured, Try again");
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
