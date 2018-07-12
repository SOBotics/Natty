package in.bhargavrao.stackoverflow.natty.commands;

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
public class OptIn implements SpecialCommand {

    private Message message;

    public OptIn(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"opt-in");
    }

    @Override
    public void execute(Room room) {
    	User user = message.getUser();
        long userId = user.getId();
        String userName = user.getName();
        int reputation = user.getReputation();

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

                StorageService service = new FileStorageService();
                service.addOptedInUser(optedInUser);

            }
            else {
                room.replyTo(message.getId(), "Type of post can be naa or all");
            }
        }
        else if(pieces.length==1){
            room.replyTo(message.getId(), "Please specify the type of post.");
        }
    }

    @Override
    public String description() {
        return "Notifies the user of the requested posts, see the wiki for more details";
    }

    @Override
    public String name() {
        return "opt-in";
    }
}
