package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;
import java.util.List;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.entities.autocomments.*;
import in.bhargavrao.stackoverflow.natty.entities.Natty;
import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.AutoCommentUtils;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Check implements SpecialCommand {

    private Message message;
    private String sitename;
    private String siteurl;

    public Check(Message message, String sitename, String siteurl) {
        this.message = message;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"check");
    }

    @Override
    public void execute(Room room) {
        try {

            String filename = FilePathUtils.checkUsers;
            String word = CommandUtils.extractData(message.getPlainContent()).trim();
            Integer returnValue = 0;
            
            if(word.contains(" ")){
                String parts[] = word.split(" ");
                if(parts[0].toLowerCase().equals("value")){
                    returnValue = 1;
                    word = parts[1];
                }
                else if (parts[0].toLowerCase().equals("explain")){
                    returnValue = 2;
                    word = parts[1];
                }
            }
            if(word.contains("/"))
            {            	
                String parts[]= word.split("//")[1].split("/");
                if(parts[1].equals("users")){
                    for(String line: FileUtils.readFile(filename)){
                        String users[] = line.split(",");
                        if(parts[2].equals(users[0])){
                            room.replyTo(message.getId(), users[1]);
                        }
                    }
                }
                else {
                    if (!word.contains(siteurl))
                    {
                        room.replyTo(message.getId(), "Wrong site");
                        return;
                    }
                    word = CommandUtils.getAnswerId(word);
                }
            }

            String returnParams[] = getCheckData(word, returnValue);

            room.replyTo(message.getId(), returnParams[0]);
            if (!returnParams[1].equals(""))
                room.send(returnParams[1]);

        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(message.getId(), "Error occured, Try again");
        }
    }

    public String[] getCheckData(String word, Integer returnValue) throws IOException {
        String[] returnParams;
        Natty cc = new Natty(sitename, siteurl);
        Post np = cc.checkPost(Integer.parseInt(word));
        PostPrinter pp = new PostPrinter(np);
        pp.addQuesionLink();

        PostReport report = PostUtils.getNaaValue(np);

        Double found = report.getNaaValue();
        List<String> caughtFilters = report.getCaughtFor();
        List<Double> caughtFiltersValues = report.getCaughtForValues();

        pp.addMessage(" **"+found+"**;");

        AutoComment comment = AutoCommentUtils.commentForPostReport(report);
        if (comment.length() > 0) pp.addMessage(" **Proposed comment: "+comment.getIdentifier()+"**;");

        String returnString;
        String additionalData = "";

        if(returnValue==1) {
            returnString =  "The NAA Value is " + found;
        }
        else if(returnValue==2) {
            returnString =  "The NAA Value is " + found + ". The explanation for the filters is:";
            String explanation = "";
            for(int i=0;i<caughtFilters.size();i++){
                explanation+="    "+caughtFiltersValues.get(i)+" - "+caughtFilters.get(i)+"\n";
            }
            additionalData = explanation;
        }
        else {
            returnString = pp.print();
        }

        returnParams = new String[]{returnString, additionalData};
        return returnParams;
    }

    @Override
    public String description() {
        return "Checks the sanity of a given post";
    }

    @Override
    public String name() {
        return "check";
    }
}
