package in.bhargavrao.stackoverflow.natty.commands.reserved;

import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Remind extends ReservedCommand {

    private Message message;

    public Remind(Message message) {
        super(message, "remind");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        StorageService service = new FileStorageService();
        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        if(!data.equals("")){
            room.replyTo(message.getId(), service.storeReminders(data));
        }
    }

    @Override
    public String description() {
        return "Adds a reminder to the list of requests";
    }

}
