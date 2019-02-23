package in.bhargavrao.stackoverflow.natty.commands.others;

import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Blacklist extends NormalCommand {

    private Message message;

    public Blacklist(Message message) {
        super(message, "blacklist");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        String data = CommandUtils.extractData(message.getPlainContent());
        StorageService service = new FileStorageService();
        room.replyTo(message.getId(),service.listWord(data, ListType.BLACKLIST));
    }

    @Override
    public String description() {
        return "Adds a given statement to the list of blacklisted words";
    }

}
