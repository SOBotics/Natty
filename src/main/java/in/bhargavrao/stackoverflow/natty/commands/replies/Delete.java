package in.bhargavrao.stackoverflow.natty.commands.replies;

import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

/**
 * Created by bhargav.h on 22-Feb-19.
 */
public class Delete extends ReplyCommand {

    private Message message;
    private PingMessageEvent event;

    public Delete(Message message, PingMessageEvent pingMessageEvent) {
        super(message, "delete");
        this.message = message;
        this.event = pingMessageEvent;
    }

    public boolean authenticate() throws UnAuthorizedException {
        boolean valid = message.getUser().isModerator() || message.getUser().isRoomOwner();
        if (!valid){
            throw new UnAuthorizedException("Trying to access a non moderator command", message);
        }
        return true;
    }

    @Override
    public void execute(Room room) {
        long repliedTo = event.getParentMessageId();
        room.delete(repliedTo);
    }

    @Override
    public String description() {
        return "";
    }

}
