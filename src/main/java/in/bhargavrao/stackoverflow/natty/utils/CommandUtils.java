package in.bhargavrao.stackoverflow.natty.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class CommandUtils {

    public static boolean checkForCommand(String message, String command){
    	String username = "";
    	
    	Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(FilePathUtils.loginPropertiesFile));
            username = prop.getProperty("username").substring(0,3).toLowerCase();
        }
        catch (IOException e){
            e.printStackTrace();
            username = "nat";
        }
    	
        return message.split(" ")[0].toLowerCase().startsWith("@"+username) && message.split(" ")[1].toLowerCase().equals(command);
    }
    public static String extractData(String message){
        String parts[] = message.split(" ");
        return String.join(" ", Arrays.copyOfRange(parts,2,parts.length));
    }
    public static String getAnswerId(String word){
        String parts[]= word.split("//")[1].split("/");
        if(parts[1].equals("a") || parts[1].equals("answers")){
            word = parts[2];
        }
        else if (parts[1].equals("q") || parts[1].equals("questions")){
            if (parts[4].contains("#"))
            {
                word = parts[4].split("#")[1];
            }
        }
        return word;
    }
    public static String getCommentId(String word){
        String parts[]= word.split("//")[1].split("/");
        if(parts[1].equals("posts") && parts[2].equals("comments")){
            word = parts[3];
        }
        else if (parts[3].contains("#comment") ){
            if (parts[3].contains("#"))
            {
                String commentAnchor = parts[3].split("#")[1];
                word = commentAnchor.substring(7,commentAnchor.indexOf("_"));
            }
        }
        else if (parts.length==5 && parts[4].contains("#comment") ){
            if (parts[4].contains("#"))
            {
                String commentAnchor = parts[4].split("#")[1];
                word = commentAnchor.substring(7,commentAnchor.indexOf("_"));
            }
        }
        return word;
    }

}
