package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import org.sobotics.chatexchange.chat.Message;

public class ReservedCommand extends NormalCommand{


    public ReservedCommand(Message message, String commandName) {
        super(message, commandName);
    }

    public boolean validate() throws UnAuthorizedException {
        boolean valid = message.getUser().isModerator() || message.getUser().isRoomOwner();
        if (!valid){
            throw new UnAuthorizedException("Trying to access a non moderator command", message);
        }

        return super.validate() && valid;
    }
}
