package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class IsBlacklisted implements SpecialCommand {

    private Message message;

    public IsBlacklisted(Message message) {
        this.message = message;
    }
    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"isblacklisted");
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

    @Override
    public String name() {
        return "isblacklisted";
    }
}
