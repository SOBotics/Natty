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
public class AddSalute implements SpecialCommand {
    private PingMessageEvent event;
    private String message;

    public AddSalute(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"addsalute");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.salutationsFile;
            String data = CommandUtils.extractData(message);
            if (FileUtils.checkIfInFile(filename, data))
                room.replyTo(event.getMessage().getId(), "Already added as Salute");
            FileUtils.appendToFile(filename,data);
            room.replyTo(event.getMessage().getId(),"Added Salute Successfully");
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occured, Try again");
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
