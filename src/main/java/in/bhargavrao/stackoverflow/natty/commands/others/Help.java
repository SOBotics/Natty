package in.bhargavrao.stackoverflow.natty.commands.others;

import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Help extends NormalCommand {

    private Message message;

    public Help(Message message) {
        super(message, "help");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(),PrintUtils.printHelp());
    }

    @Override
    public String description() {
        return "Returns information regarding the chatbot";
    }

}
