package in.bhargavrao.stackoverflow.natty.commands.replies;

import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 22-Feb-19.
 */
public class Where extends ReplyCommand {

    private Message message;

    public Where(Message message) {
        super(message, "where");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        room.send("there");
    }

    @Override
    public String description() {
        return "";
    }

}
