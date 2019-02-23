package in.bhargavrao.stackoverflow.natty.exceptions;

import org.sobotics.chatexchange.chat.Message;

/**
 * Created by bhargav.h on 22-Aug-17.
 */
public class UnAuthorizedException extends Exception {

    private Message chatMessage;

    public UnAuthorizedException(String message, Message chatMessage) {
        super(message);
        this.chatMessage = chatMessage;
    }

    public Message getChatMessage() {
        return chatMessage;
    }
}
