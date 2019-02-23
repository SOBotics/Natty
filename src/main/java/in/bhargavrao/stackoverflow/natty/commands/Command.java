package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public abstract class Command {

    protected Message message;
    protected String commandName;

    public Command(Message message, String commandName) {
        this.message = message;
        this.commandName = commandName;
    }

    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(), commandName);
    }

    public boolean authenticate() throws UnAuthorizedException{
        return true;
    }

    public abstract void execute(Room room);

    public abstract String description();

    public String getName() {
        return commandName;
    }
}
