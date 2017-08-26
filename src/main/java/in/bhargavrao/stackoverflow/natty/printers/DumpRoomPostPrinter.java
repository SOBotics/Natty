package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class DumpRoomPostPrinter implements in.bhargavrao.stackoverflow.natty.printers.PostPrinter {

    public final long roomId = 108192;

    @Override
    public String print(PostReport report) {
        return  (new PostPrinter(report.getPost()).addMainTag().addDescription().addReputation().addUserDetails().addBodyLength().addFirstLine().addMessage(" **"+report.getNaaValue()+"**;").print());
    }
}
