package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;

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
        return CommandUtils.checkForCommand(message,"wishBD");
    }

    @Override
    public void execute(Room room) {
        System.out.println("Comes here");
        room.send("http://images.all-free-download.com/images/graphicthumb/best_happy_birthday_design_elements_vector_set_524006.jpg");
    }
}
