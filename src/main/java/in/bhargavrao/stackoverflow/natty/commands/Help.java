package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Help implements Command {

    private Message message;

    public Help(Message message) {
        this.message = message;
    }


    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"help");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(),PrintUtils.printHelp());
    }

    @Override
    public String description() {
        return "Returns information regarding the chatbot";
    }

    @Override
    public String name() {
        return "help";
    }
}
