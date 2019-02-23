package in.bhargavrao.stackoverflow.natty.commands;

import org.sobotics.chatexchange.chat.Message;

public class ReservedCommand {

    private Message message;

    public ReservedCommand(Message message) {
        this.message = message;
    }

    public boolean validate()
    {
        return message.getUser().isModerator() || message.getUser().isRoomOwner();
    }
}
