package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.clients.RunNewNatty;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class Status implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public Status(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"status");
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
