package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Hi implements SpecialCommand {

    private Message message;
    private long userId;

    public Hi(Message message, long userId) {
        this.message = message;
        this.userId = userId;
    }


    @Override
    public boolean validate() {
        return (" "+message+" ").toLowerCase().contains(" hi ");
    }

    @Override
    public void execute(Room room) {
        if(userId == 1252759)
            room.replyTo(message.getId(), "Hi Jon, Here are some scooby snacks.");
        if(userId == 4174897)
            room.replyTo(message.getId(), "Plop.");
        else
            room.replyTo(message.getId(), "Hi");

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
