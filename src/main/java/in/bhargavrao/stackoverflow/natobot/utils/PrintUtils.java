package in.bhargavrao.stackoverflow.natobot.utils;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class PrintUtils {

    public static String printDescription(){
        return " [ [NATOBot](https://git.io/vi0YX) ] ";
    }
    public static String printHelp(){
        return "I'm a bot that returns posts from the new answers to old questions page. "+wikiLink();
    }
    private static String wikiLink(){
        return "The guide and the wiki for the project [are present here](https://git.io/viiT1).";
    }
    public static String printCommandHeader(){
        return "The list of commands are as follows: "+wikiLink();
    }
    public static String printCommands(){
        return  "     help     - Returns the information regarding the chatbot\n" +
                "     commands - Returns the list of commands associated with this bot";

    }
}
