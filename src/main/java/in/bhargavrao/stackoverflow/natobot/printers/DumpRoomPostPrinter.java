package in.bhargavrao.stackoverflow.natobot.printers;

import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.NatoPostPrinter;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class DumpRoomPostPrinter implements PostPrinter {

    public final long roomId = 108192;

    @Override
    public String print(NatoReport report) {
        return  (new NatoPostPrinter(report.getPost()).addMainTag().addDescription().addReputation().addUserDetails().addBodyLength().addFirstLine().addMessage(" **"+report.getNaaValue()+"**;").print());
    }
}
