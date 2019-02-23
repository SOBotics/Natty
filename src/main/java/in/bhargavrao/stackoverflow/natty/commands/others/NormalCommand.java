package in.bhargavrao.stackoverflow.natty.commands.others;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import org.sobotics.chatexchange.chat.Message;

public abstract class NormalCommand extends Command {

    public NormalCommand(Message message, String commandName) {
        super(message, commandName);
    }

}
