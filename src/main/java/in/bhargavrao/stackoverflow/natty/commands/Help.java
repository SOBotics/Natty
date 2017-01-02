package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Help implements SpecialCommand {

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
