package in.bhargavrao.stackoverflow.natty.commands.reserved;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;

public abstract class ReservedCommand extends Command {

    public ReservedCommand(Message message, String commandName) {
        super(message, commandName);
    }

    public boolean authenticate() throws UnAuthorizedException {
        boolean valid = message.getUser().isModerator() || message.getUser().isRoomOwner();
        System.out.println("Comes here");
        if (!valid){
            throw new UnAuthorizedException("Trying to access a non moderator command", message);
        }

        return CommandUtils.checkForCommand(message.getPlainContent(), commandName);
    }

}
