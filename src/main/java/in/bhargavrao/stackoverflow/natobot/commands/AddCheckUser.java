package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;
import in.bhargavrao.stackoverflow.natobot.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class AddCheckUser implements SpecialCommand {
    private PingMessageEvent event;
    private String message;

    public AddCheckUser(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"addcheckuser");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.checkUsers;
            String data = CommandUtils.extractData(message);
            String parts[] = data.split(" ");
            if (StringUtils.isNumeric(parts[0])){
                String snark = String.join(" ", Arrays.copyOfRange(parts,1,parts.length));
                FileUtils.appendToFile(filename,parts[0]+","+ JsonUtils.escapeHtmlEncoding(snark));
                room.replyTo(event.getMessage().getId(),"SOUser Added");
            }
            else{
                room.replyTo(event.getMessage().getId(), "Must be SOUser ID");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occured, Try again");
        }
    }
}
