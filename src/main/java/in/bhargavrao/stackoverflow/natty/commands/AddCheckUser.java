package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;
import in.bhargavrao.stackoverflow.natty.utils.JsonUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class AddCheckUser implements SpecialCommand {

    private Message message;

    public AddCheckUser(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"addcheckuser");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.checkUsers;
            String data = CommandUtils.extractData(message.getPlainContent());
            String parts[] = data.split(" ");
            if (StringUtils.isNumeric(parts[0])){
                String snark = String.join(" ", Arrays.copyOfRange(parts,1,parts.length));
                FileUtils.appendToFile(filename,parts[0]+","+ JsonUtils.escapeHtmlEncoding(snark));
                room.replyTo(message.getId(),"SOUser Added");
            }
            else{
                room.replyTo(message.getId(), "Must be SOUser ID");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(message.getId(), "Error occured, Try again");
        }
    }

    @Override
    public String description() {
        return "Adds a new user to the special users list";
    }

    @Override
    public String name() {
        return "addcheckuser";
    }
}
