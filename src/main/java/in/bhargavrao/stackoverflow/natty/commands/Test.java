package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.mocks.MockBodyPost;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.util.List;

/**
 * Created by bhargav.h on 27-Aug-17.
 */
public class Test implements SpecialCommand {

    private Message message;

    public Test(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"test");
    }

    @Override
    public void execute(Room room) {

        System.out.println("BODY:["+message.getContent()+"]");
        System.out.println("PLAIN:["+message.getPlainContent()+"]");

        String bodyMarkdown = getBM(message.getContent());

        System.out.println("BM:["+bodyMarkdown+"]");

        Post mockPost = new MockBodyPost(bodyMarkdown);

        PostReport report = PostUtils.getNaaValue(mockPost, "testBody");
        List<String> caughtFilters = report.getCaughtFor();
        List<Double> caughtFiltersValues = report.getCaughtForValues();

        String returnString =  "The NAA Value is " + report.getNaaValue() + ". The explanation for the filters is:";
        String explanation = "";
        for(int i=0;i<caughtFilters.size();i++){
            explanation+="    "+caughtFiltersValues.get(i)+" - "+caughtFilters.get(i)+"\n";
        }

        room.replyTo(message.getId(), returnString);
        room.send(explanation);
    }

    private String getBM(String content) {

        if (content.contains("<div class='partial'>") && content.contains("<br>")){
            content = content.replace("<br>","\n");
            content = content.substring(21, content.length()-6);
        }
        return CommandUtils.extractData(content);
    }

    @Override
    public String description() {
        return "Tests a mock body if it would be caught for any filters";
    }

    @Override
    public String name() {
        return "test";
    }
}
