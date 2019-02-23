package in.bhargavrao.stackoverflow.natty.commands.hidden;

import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Hi extends HiddenCommand {

    private Message message;
    private long userId;

    public Hi(Message message, long userId) {
        super(message, "hi");
        this.message = message;
        this.userId = userId;
    }

    @Override
    public boolean validate() {
        return (" "+message.getPlainContent()+" ").toLowerCase().contains(" hi ");
    }

    @Override
    public void execute(Room room) {
        if(userId == 1252759)
            room.replyTo(message.getId(), "Hi Jon, Here are some scooby snacks.");
        else if(userId == 4174897)
            room.replyTo(message.getId(), "Plop.");
        else
            room.replyTo(message.getId(), "Hi");

    }

}
