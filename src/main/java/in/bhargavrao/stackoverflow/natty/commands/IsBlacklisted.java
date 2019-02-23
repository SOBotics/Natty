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
public class IsBlacklisted extends NormalCommand {

    private Message message;

    public IsBlacklisted(Message message) {
        super(message, "isblacklisted");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        String word = CommandUtils.extractData(message.getPlainContent());
        StorageService service = new FileStorageService();
        room.replyTo(message.getId(), service.checkListWord(word, ListType.BLACKLIST)?"The word is blacklisted":"The word is not blacklisted");
    }

    @Override
    public String description() {
        return "Checks if the given statement is blacklisted";
    }

}
