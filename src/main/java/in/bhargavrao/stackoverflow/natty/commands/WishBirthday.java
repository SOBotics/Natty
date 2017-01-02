package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class WishBirthday implements SpecialCommand {

    private Message message;

    public WishBirthday(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"wishbd");
    }

    @Override
    public void execute(Room room) {
        room.send("http://images.all-free-download.com/images/graphicthumb/best_happy_birthday_design_elements_vector_set_524006.jpg");
    }

    @Override
    public String description() {
        return "Wishes the user a very happy birthday";
    }

    @Override
    public String name() {
        return "wishBD";
    }
}
