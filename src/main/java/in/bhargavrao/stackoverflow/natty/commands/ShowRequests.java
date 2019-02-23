package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.util.List;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class ShowRequests extends ReservedCommand {

    private Message message;

    public ShowRequests(Message message) {
        super(message, "showreqs");
        this.message = message;
    }

    @Override
    public void execute(Room room) {

        StorageService service = new FileStorageService();
        List<String> lines = service.retrieveReminders();

        if (lines == null) {
            room.replyTo(message.getId(), "Some Error Occured");
        }
        else {
            String requestString = "";
            int i = 0;
            for (String line : lines) {
                requestString += "    " + (i + 1) + ". " + line.trim() + "\n";
                i++;
            }

            if (lines.size() == 0)
                room.replyTo(message.getId(), "There are no requirements currently ");
            else {
                room.replyTo(message.getId(), "The list of requests stored  ");
                room.send(requestString);
            }
        }

    }

    @Override
    public String description() {
        return "Shows the list of requests";
    }

}
