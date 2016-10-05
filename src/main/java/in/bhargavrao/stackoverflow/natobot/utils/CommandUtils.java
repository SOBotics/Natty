package in.bhargavrao.stackoverflow.natobot.utils;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class CommandUtils {

    public static boolean checkForCommand(String message, String command){
        return message.split(" ")[1].toLowerCase().equals(command);
    }
    public static String extractData(String message){
        String parts[] = message.split(" ");
        return String.join(" ", Arrays.copyOfRange(parts,2,parts.length));
    }
    public static String checkAndRemoveMessage(String filename, String message){
        try{
            if(FileUtils.checkIfInFile(filename,message)){
                FileUtils.removeFromFile(filename,message);
                return "Done";
            }
            else {
                return ( "It's not there in the file");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return ("Failed");
        }

    }

}
