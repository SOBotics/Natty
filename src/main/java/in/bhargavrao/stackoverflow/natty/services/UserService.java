package in.bhargavrao.stackoverflow.natty.services;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natty.model.OptedInUser;
import in.bhargavrao.stackoverflow.natty.model.Post;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bhargav.h on 18-Sep-16.
 */
public class UserService {

    Room room;

    public UserService (Room room){
        this.room = room;
    }

    public List<OptedInUser> pingUserIfApplicable(Post np)
    {
        StorageService service = new FileStorageService();
        List<OptedInUser> pingList = new ArrayList<>();
        for(String tag:np.getTags()){
            for(OptedInUser optedInUser :service.getOptedInUsers(tag,room.getRoomId())) {
                if(!(checkIfUserIsInList(pingList,optedInUser)))
                    pingList.add(optedInUser);
            }
        }
        return pingList;
    }

    private static boolean checkIfUserIsInList(List<OptedInUser> users, OptedInUser checkUser){
        if(users.size()==0) return false;
        for(OptedInUser user:users){
            if(user.getUser().getUserId() == checkUser.getUser().getUserId()){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfUserInRoom(int userId){
        User user = room.getUser(userId);
        return user.isCurrentlyInRoom();
    }

}
