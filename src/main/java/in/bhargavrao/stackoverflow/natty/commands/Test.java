package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.mocks.MockBodyPost;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;

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
        String bodyMarkdown = CommandUtils.extractData(message.getPlainContent()).trim();

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

    @Override
    public String description() {
        return "Tests a mock body if it would be caught for any filters";
    }

    @Override
    public String name() {
        return "test";
    }
}
