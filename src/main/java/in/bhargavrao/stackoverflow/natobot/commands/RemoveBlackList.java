package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;

import java.io.IOException;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveBlackList implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public RemoveBlackList(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message,"rmblacklist");
    }

    @Override
    public void execute(Room room) {

        String filename = FilePathUtils.blacklistFile;
        String data = CommandUtils.extractData(message).trim();
        room.replyTo(event.getMessage().getId(), CommandUtils.checkAndRemoveMessage(filename,data));

    }
}
