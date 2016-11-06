package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveWhitelist implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public RemoveWhitelist(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message,"rmwhitelist");
    }

    @Override
    public void execute(Room room) {
        String filename = FilePathUtils.whitelistFile;
        String data = CommandUtils.extractData(message).trim();
        room.replyTo(event.getMessage().getId(), CommandUtils.checkAndRemoveMessage(filename,data));
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
