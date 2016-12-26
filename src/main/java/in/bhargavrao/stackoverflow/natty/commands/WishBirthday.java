package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class WishBirthday implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public WishBirthday(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"wishbd");
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
