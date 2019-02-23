package in.bhargavrao.stackoverflow.natty.commands;

import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class WishBirthday extends HiddenCommand {

    private Message message;

    public WishBirthday(Message message) {
        super(message, "wishbd");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        room.send("http://images.all-free-download.com/images/graphicthumb/best_happy_birthday_design_elements_vector_set_524006.jpg");
    }

}
