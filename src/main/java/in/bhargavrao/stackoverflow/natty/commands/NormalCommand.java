package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;

public class NormalCommand {

    protected Message message;
    protected String commandName;

    public NormalCommand(Message message, String commandName) {
        this.message = message;
        this.commandName = commandName;
    }

    public boolean validate() throws UnAuthorizedException {
        return CommandUtils.checkForCommand(message.getPlainContent(), commandName);
    }

    public String getName() {
        return commandName;
    }
}
