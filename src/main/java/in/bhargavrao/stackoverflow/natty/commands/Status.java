package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.clients.RunNewNatty;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class Status implements SpecialCommand {

    private Message message;

    public Status(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"status");
    }

    @Override
    public void execute(Room room) {
        room.send("Some issue with that, Will be fixed soon");
    }

    @Override
    public String description() {
        return "Returns the status of the bot";
    }

    @Override
    public String name() {
        return "status";
    }
}
