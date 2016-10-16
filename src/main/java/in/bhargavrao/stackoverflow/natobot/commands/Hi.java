package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Hi implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Hi(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }


    @Override
    public boolean validate() {
        return (" "+message+" ").toLowerCase().contains(" hi ");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(event.getMessage().getId(), "Hi");
    }
}
