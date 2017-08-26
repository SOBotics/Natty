package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.model.autocomments.AutoComment;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.NattyService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.AutoCommentUtils;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;

import java.io.IOException;
import java.util.List;

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


            StorageService service = new FileStorageService();

            String word = CommandUtils.extractData(message.getPlainContent()).trim();

            // TO CHECK FOR OTHER OPTIONS

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

                // FOR USERS

                if(parts[1].equals("users")){
                    try {
                        room.replyTo(message.getId(), service.checkUsers(Integer.parseInt(parts[2]),"stackoverflow"));
                        // TODO: Implement for other sites.
                    } catch (NoSuchUserFoundException e) {
                        room.replyTo(message.getId(), "Sorry, you ain't *that* famous.");
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
        NattyService cc = new NattyService(sitename, siteurl);
        Post np = cc.checkPost(Integer.parseInt(word));

        if (np==null)
            return new String[]{"The post is either deleted or not retrievable from the IP.", ""};

        PostPrinter pp = new PostPrinter(np);
        pp.addQuesionLink();

        PostReport report = PostUtils.getNaaValue(np, sitename);

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
