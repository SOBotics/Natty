package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Say implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Say(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"say");
    }

    @Override
    public void execute(Room room) {
        room.send(CommandUtils.extractData(message));
    }
}
