package in.bhargavrao.stackoverflow.natty.services;

import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.MessagePostedEvent;

import java.util.function.Consumer;

public class NewMessageHandlerService {

    public Consumer<MessagePostedEvent> getMessagePostedEventConsumer(Room finalChatroom) {
        return event-> newMessage(finalChatroom, event, false);
    }


    private static void newMessage(Room room, MessagePostedEvent event, boolean b) {
        String message = event.getMessage().getPlainContent();
        int cp = Character.codePointAt(message, 0);
        if(message.trim().startsWith("@bots alive")){
            room.send("Whadya think?");
        }
        else if (cp == 128642 || (cp>=128644 && cp<=128650)){
            room.send("\uD83D\uDE83");
        }
    }

}
