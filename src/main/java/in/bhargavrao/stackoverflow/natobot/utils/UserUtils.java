package in.bhargavrao.stackoverflow.natobot.utils;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natobot.entities.NatoBotUser;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by bhargav.h on 18-Sep-16.
 */
public class UserUtils {

    public static List<NatoBotUser> getUsersOptedIn(String tagname, long roomId){
        List <NatoBotUser> natoBotUsers = new ArrayList<>();

        String filename = ".\\src\\main\\resources\\lib\\OptedInUsersList.txt";
        try {
            List<String> lines = FileUtils.readFile(filename);
            for(String e:lines){
                String pieces[] = e.split(",");
                if(pieces[1].equals(tagname) && Long.valueOf(pieces[3]).equals(Long.valueOf(roomId))){
                    NatoBotUser natoBotUser = new NatoBotUser();
                    natoBotUser.setUsername(pieces[2].replace("\"",""));
                    natoBotUser.setUserId(Integer.parseInt(pieces[0]));
                    natoBotUsers.add(natoBotUser);
                }
            }
        }
        catch (IOException e){
            System.out.println("file not found");
        }
        return natoBotUsers;
    }

    public static List<NatoBotUser> pingUserIfApplicable(NatoPost np, long roomId)
    {
        List<NatoBotUser> pingList = new ArrayList<>();
        for(String tag:np.getTags()){
            for(NatoBotUser natoBotUser :getUsersOptedIn(tag,roomId)) {
                pingList.add(natoBotUser);
            }
        }
        return pingList;
    }

    public static boolean checkIfUserInRoom(Room room, int userId){
        User user = room.getUser(userId);
        return user.isCurrentlyInRoom();
    }

}
