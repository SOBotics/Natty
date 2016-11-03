package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.PrintUtils;

/**
 * Created by bhargav.h on 27-Oct-16.
 */
public class Halp implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Halp(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }


    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"halp");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(event.getMessage().getId(),"Plop! You need to use help");
    }
}
