package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveWhitelist implements SpecialCommand {

    private Message message;

    public RemoveWhitelist(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message.getPlainContent(),"rmwhitelist");
    }

    @Override
    public void execute(Room room) {
        String filename = FilePathUtils.whitelistFile;
        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        room.replyTo(message.getId(), CommandUtils.checkAndRemoveMessage(filename,data));
    }

    @Override
    public String description() {
        return "Removes the given statement from whitelist  ";
    }

    @Override
    public String name() {
        return "rmwhitelist";
    }
}
