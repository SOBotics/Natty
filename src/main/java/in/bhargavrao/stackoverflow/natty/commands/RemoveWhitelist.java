package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveWhitelist extends NormalCommand implements Command {

    private Message message;

    public RemoveWhitelist(Message message) {
        super(message, "rmwhitelist");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        String data = CommandUtils.extractData(message.getPlainContent());
        StorageService service = new FileStorageService();
        room.replyTo(message.getId(),service.unListWord(data, ListType.WHITELIST));
    }

    @Override
    public String description() {
        return "Removes the given statement from whitelist  ";
    }

}
