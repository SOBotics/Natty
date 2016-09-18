package in.bhargavrao.stackoverflow.natobot.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by bhargav.h on 18-Sep-16.
 */
public class UserUtils {

    public static List<String> getUsersOptedIn(String tagname){
        List <String> users = new ArrayList<>();

        String filename = ".\\src\\main\\resources\\lib\\OptedInUsersList.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for(String e:lines){
                String pieces[] = e.split(",");
                if(pieces[1].equals(tagname)){
                    //System.out.println("pieces : "+pieces[0]+" "+pieces[1]+" "+pieces[2]);
                    users.add(pieces[2]);
                }
            }
        }
        catch (IOException e){
            System.out.println("file not found");
        }
        return users;
    }
}
