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
        if (cp == 128642 || (cp>=128644 && cp<=128650)){
            room.send("[\uD83D\uDE83](https://www.youtube.com/watch?v=C5seQxAqZtQ)");
        }
        if(message.trim().contains("programmers") && event.getUserId()==7481043){
            room.send("Sigh, he's getting philosophical again. Is there anyone here?");
        }
        if(message.trim().contains("Punishments include such things as flashbacks") && event.getUserId()==7481043){
            room.send("Calm down, nothing will happen.");
        }
    }
}
