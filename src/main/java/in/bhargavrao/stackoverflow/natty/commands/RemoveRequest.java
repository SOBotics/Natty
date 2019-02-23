package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.util.Arrays;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveRequest extends ReservedCommand implements Command {

    private Message message;

    public RemoveRequest(Message message) {
        super(message, "rmreq");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        StorageService service = new FileStorageService();
        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        int linenumbers[] = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();
        room.replyTo(message.getId(),(linenumbers.length==1) ? (service.deleteReminder(linenumbers[0])): service.deleteReminders(linenumbers));
    }

    @Override
    public String description() {
        return "Removes the request from the list of requests";
    }

}
