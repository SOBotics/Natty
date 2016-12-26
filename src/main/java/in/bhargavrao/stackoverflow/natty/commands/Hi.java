package in.bhargavrao.stackoverflow.natty.commands;

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
        if(event.getUserId()==1252759)
            room.replyTo(event.getMessage().getId(), "Hi Jon, Here are some scooby snacks.");
        if(event.getUserId()==4174897)
            room.replyTo(event.getMessage().getId(), "Plop.");
        else
            room.replyTo(event.getMessage().getId(), "Hi");

    }

    @Override
    public String description() {
        return "Hi, Test it out";
    }

    @Override
    public String name() {
        return "hi";
    }
}
