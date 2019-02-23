package in.bhargavrao.stackoverflow.natty.commands.hidden;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import org.sobotics.chatexchange.chat.Message;

public abstract class HiddenCommand extends Command {

    public HiddenCommand(Message message, String commandName) {
        super(message, commandName);
    }

    public String description()  {
        return "";
    }
}
