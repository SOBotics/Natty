package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class NAABotPostPrinter implements in.bhargavrao.stackoverflow.natty.printers.PostPrinter {

    public final long roomId = 25312;

    @Override
    public String print(PostReport report) {

        PostPrinter napp = new PostPrinter(report.getPost(),"[ [NAABot](http://chat.stackoverflow.com/transcript/message/34000821#34000821) ]").addMainTag().addDescription().addUserDetails().addMessage(" **"+report.getNaaValue()+"**");

        return  (napp.print());
    }
}
