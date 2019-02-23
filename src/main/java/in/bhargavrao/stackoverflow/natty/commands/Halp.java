package in.bhargavrao.stackoverflow.natty.commands;

import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 27-Oct-16.
 */
public class Halp extends HiddenCommand {

    private Message message;

    public Halp(Message message) {
        super(message, "halp");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(),"Plop! You need to use help");
    }


}
