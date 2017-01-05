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
public class AddSalute implements SpecialCommand {

    private Message message;

    public AddSalute(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"addsalute");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.salutationsFile;
            String data = CommandUtils.extractData(message.getPlainContent());
            if (FileUtils.checkIfInFile(filename, data))
                room.replyTo(message.getId(), "Already added as Salute");
            FileUtils.appendToFile(filename,data);
            room.replyTo(message.getId(),"Added Salute successfully");
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(message.getId(), "Error occured, Try again");
        }
    }

    @Override
    public String description() {
        return "Adds a given statement to the list of salutations";
    }

    @Override
    public String name() {
        return "addsalute";
    }
}
