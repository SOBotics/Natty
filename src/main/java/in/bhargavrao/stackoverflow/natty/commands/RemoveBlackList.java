package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveBlackList implements SpecialCommand {

    private Message message;

    public RemoveBlackList(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"rmblacklist");
    }

    @Override
    public void execute(Room room) {

        String filename = FilePathUtils.blacklistFile;
        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        room.replyTo(message.getId(), CommandUtils.checkAndRemoveMessage(filename,data));

    }

    @Override
    public String description() {
        return "Removes the given statement from blacklist";
    }

    @Override
    public String name() {
        return "rmblacklist";
    }
}
