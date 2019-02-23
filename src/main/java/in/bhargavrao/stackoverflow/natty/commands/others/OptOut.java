package in.bhargavrao.stackoverflow.natty.commands.others;

import in.bhargavrao.stackoverflow.natty.model.OptedInUser;
import in.bhargavrao.stackoverflow.natty.model.SOUser;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.apache.commons.lang3.StringUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.User;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class OptOut extends NormalCommand {

    private Message message;

    public OptOut(Message message) {
        super(message, "opt-out");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
    	User user = message.getUser();
        long userId = user.getId();
        String userName = user.getName();
        int reputation = user.getReputation();
        StorageService service = new FileStorageService();
        String data = CommandUtils.extractData(message.getPlainContent()).trim();

        String pieces[] = data.split(" ");

        if(pieces.length>=2){
            String tag = pieces[0];
            String postType = pieces[1];
            boolean whenInRoom = true;
            if(pieces.length==3 && pieces[2].equals("always")){
                whenInRoom = false;
            }

            if(!tag.equals("all")){
                tag = StringUtils.substringBetween(message.getPlainContent(),"[","]");
            }
            if(postType.equals("all") || postType.equals("naa")){
                OptedInUser optedInUser = new OptedInUser();
                optedInUser.setPostType(postType);
                optedInUser.setRoomId(room.getRoomId());
                optedInUser.setUser(new SOUser(userName,userId,reputation,null));
                optedInUser.setTagname(tag);
                optedInUser.setWhenInRoom(whenInRoom);


                service.removeOptedInUser(optedInUser);
            }
            else {
                room.replyTo(message.getId(), "Type of post can be naa or all");
            }
        }
        else if(pieces[0].equals("everything")){
            service.removeAllOptIn(userId);
        }
        else if(pieces.length==1){
            room.replyTo(message.getId(), "Please specify the type of post.");
        }
    }

    @Override
    public String description() {
        return "Unnotifies the user. ";
    }

}
