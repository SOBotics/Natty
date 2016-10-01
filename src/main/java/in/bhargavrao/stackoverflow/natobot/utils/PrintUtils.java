package in.bhargavrao.stackoverflow.natobot.utils;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class PrintUtils {

    public static String printDescription(){
        return " [ [NATOBot](//stackapps.com/questions/7049) ] ";
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
        return  "     help          - Returns the information regarding the chatbot\n" +
                "     commands      - Returns the list of commands associated with this bot\n" +
                "     alive         - Returns a test reply to inform that the bot is alive\n" +
                "     check         - Checks the sanity of a given post\n" +
                "     addsalute     - Adds a given statement to the list of salutations\n" +
                "     whitelist     - Adds a given statement to the list of whitelisted words\n" +
                "     blacklist     - Adds a given statement to the list of blacklisted words\n" +
                "     iswhitelisted - Checks if the given statement is whitelisted\n" +
                "     isblacklisted - Checks if the given statement is blacklisted\n" +
                "     opt-in        - Notifies the user of ALL THE POSTS in NATO\n" +
                "     opt-out       - Unlists the user from notifications";

    }
}
