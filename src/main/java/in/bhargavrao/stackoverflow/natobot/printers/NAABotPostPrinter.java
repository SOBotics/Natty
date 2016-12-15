package in.bhargavrao.stackoverflow.natobot.printers;

import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.NatoPostPrinter;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class NAABotPostPrinter implements PostPrinter {

    public final long roomId = 25312;

    @Override
    public String print(NatoReport report) {

        NatoPostPrinter napp = new NatoPostPrinter(report.getPost(),"[ [NAABot](http://chat.stackoverflow.com/transcript/message/34000821#34000821) ]").addMainTag().addDescription().addUserDetails().addMessage(" **"+report.getNaaValue()+"**");

        return  (napp.print());
    }
}
