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
public class IsWhitelisted implements SpecialCommand {

    private Message message;

    public IsWhitelisted(Message message) {
        this.message = message;
    }
    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"iswhitelisted");
    }

    @Override
    public void execute(Room room) {
        String word = CommandUtils.extractData(message.getPlainContent());
        StorageService service = new FileStorageService();
        room.replyTo(message.getId(), service.checkListWord(word, ListType.WHITELIST)?"The word is whitelisted":"The word is not whitelisted");
    }

    @Override
    public String description() {
        return "Checks if the given statement is whitelisted";
    }

    @Override
    public String name() {
        return "iswhitelisted";
    }
}
