package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.apache.commons.lang3.StringUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

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
        StorageService service = new FileStorageService();
        String data = CommandUtils.extractData(message.getPlainContent());
        String parts[] = data.split(" ");
        if (StringUtils.isNumeric(parts[0])){
            String snark = data.replace(parts[0],"").trim();
            room.replyTo(message.getId(),service.addCheckUsers(Integer.parseInt(parts[0]),snark, "stackoverflow"));
            // TODO: Need to update for other sites
        }
        else{
            room.replyTo(message.getId(), "Must be SOUser ID");
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
