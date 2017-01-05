package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Alive implements SpecialCommand {


    private Message message;

    public Alive(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"alive");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(), "Nope");
    }

    @Override
    public String description() {
        return "Returns a test reply to inform that the bot is alive";
    }

    @Override
    public String name() {
        return "alive";
    }
}
