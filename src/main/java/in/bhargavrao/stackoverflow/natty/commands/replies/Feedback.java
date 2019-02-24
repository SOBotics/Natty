package in.bhargavrao.stackoverflow.natty.commands.replies;

import in.bhargavrao.stackoverflow.natty.exceptions.FeedbackInvalidatedException;
import in.bhargavrao.stackoverflow.natty.exceptions.PostNotStoredException;
import in.bhargavrao.stackoverflow.natty.services.FeedbackHandlerService;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

public abstract class Feedback extends ReplyCommand {

    private String commandAlias;
    private PingMessageEvent event;
    private String sitename;
    private String siteurl;

    public Feedback(Message message, String commandName, String commandAlias, PingMessageEvent event,
                    String sitename, String siteurl) {
        super(message, commandName);
        this.commandAlias = commandAlias;
        this.event = event;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }

    @Override
    public boolean validate() {
        return  CommandUtils.checkForCommand(message.getContent(),commandName) ||
                CommandUtils.checkForCommand(message.getContent(),commandAlias);
    }

    @Override
    public void execute(Room room) {
        store(room, event, getName(), sitename, siteurl);
    }

    private void store(Room room, PingMessageEvent event, String type, String sitename, String siteurl){
        long repliedTo = event.getParentMessageId();
        Message repliedToMessage = room.getMessage(repliedTo);
        String message = repliedToMessage.getPlainContent().trim();
        String linkToPost = getPostIdFromMessage(message, siteurl);
        StorageService service = new FileStorageService();
        if (!type.equals("tp") && service.checkAutoFlag(Long.parseLong(linkToPost),sitename)){
            room.send("False positive feedback on Autoflag, please retract @Bhargav or @Petter");
        }
        try {
            new FeedbackHandlerService(sitename, siteurl).handleFeedback(event.getMessage().getUser(), type, linkToPost);
        } catch (FeedbackInvalidatedException | PostNotStoredException e) {
            e.printStackTrace();
            room.send(e.getMessage());
        }
    }
}
