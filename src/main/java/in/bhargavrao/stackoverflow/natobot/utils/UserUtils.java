package in.bhargavrao.stackoverflow.natobot.utils;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natobot.entities.NatoBotUser;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.OptedInUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by bhargav.h on 18-Sep-16.
 */
public class UserUtils {

    public static List<OptedInUser> getUsersOptedIn(String tagname, long roomId){
        List <OptedInUser> optedInUsers = new ArrayList<>();

        String filename = ".\\src\\main\\resources\\lib\\OptedInUsersList.txt";
        try {
            List<String> lines = FileUtils.readFile(filename);
            for(String e:lines){
                String pieces[] = e.split(",");
                if((pieces[1].equals(tagname)||pieces[1].equals("all")) && Long.valueOf(pieces[3]).equals(Long.valueOf(roomId))){

                    OptedInUser optedInUser = new OptedInUser();

                    NatoBotUser natoBotUser = new NatoBotUser();
                    natoBotUser.setUsername(pieces[2].replace("\"",""));
                    natoBotUser.setUserId(Integer.parseInt(pieces[0]));

                    optedInUser.setUser(natoBotUser);
                    optedInUser.setTagname(pieces[1]);
                    optedInUser.setPostType(pieces[4]);
                    optedInUser.setRoomId(Long.valueOf(pieces[3]));
                    optedInUser.setWhenInRoom(Boolean.parseBoolean(pieces[5]));


                    optedInUsers.add(optedInUser);
                }
            }
        }
        catch (IOException e){
            System.out.println("file not found");
        }
        return optedInUsers;
    }

    public static List<OptedInUser> pingUserIfApplicable(NatoPost np, long roomId)
    {
        List<OptedInUser> pingList = new ArrayList<>();
        for(String tag:np.getTags()){
            for(OptedInUser optedInUser :getUsersOptedIn(tag,roomId)) {
                if(!(checkIfUserIsInList(pingList,optedInUser)))
                    pingList.add(optedInUser);
            }
        }
        return pingList;
    }

    public static boolean checkIfUserIsInList(List<OptedInUser> users, OptedInUser checkUser){
        if(users.size()==0) return false;
        for(OptedInUser user:users){
            if(user.getUser().getUserId() == checkUser.getUser().getUserId()){
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfUserInRoom(Room room, int userId){
        User user = room.getUser(userId);
        return user.isCurrentlyInRoom();
    }

}
