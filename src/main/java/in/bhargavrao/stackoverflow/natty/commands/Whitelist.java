package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Whitelist implements SpecialCommand {

    private Message message;

    public Whitelist(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"whitelist");
    }

    @Override
    public void execute(Room room) {
        String data = CommandUtils.extractData(message.getPlainContent());
        StorageService service = new FileStorageService();
        room.replyTo(message.getId(),service.ListWord(data, ListType.WHITELIST));
    }

    @Override
    public String description() {
        return "Adds a given statement to the list of whitelisted words";
    }

    @Override
    public String name() {
        return "whitelist";
    }
}
