package in.bhargavrao.stackoverflow.natty.commands.replies;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

public abstract class ReplyCommand extends Command {

    ReplyCommand(Message message, String commandName) {
        super(message, commandName);
    }


    public boolean validate() {
        return CommandUtils.checkForCommand(message.getContent(), commandName);
    }

    String getAnswerIdFromMessage(Room room, PingMessageEvent event, String siteurl) {
        long repliedTo = event.getParentMessageId();
        Message repliedToMessage = room.getMessage(repliedTo);
        return getPostIdFromMessage(repliedToMessage.getPlainContent().trim(), siteurl);
    }

    String getPostIdFromMessage(String message, String siteurl) {
        message = message.split("//"+siteurl+"/a/")[1];
        return message.substring(0,message.indexOf(")"));
    }
}
