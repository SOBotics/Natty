package in.bhargavrao.stackoverflow.natty.commands.others;

import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Alive extends NormalCommand {


    private Message message;

    public Alive(Message message) {

        super(message, "alive");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(), "Nope");
    }

    @Override
    public String description() {
        return "Returns a test reply to inform that the bot is alive";
    }

}
