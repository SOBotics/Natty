package in.bhargavrao.stackoverflow.natobot.printers;

import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.NatoPostPrinter;

/**
 * Created by bhargav.h on 27-Oct-16.
 */
public class HeadquartersPostPrinter implements PostPrinter {

    public final long roomId = 126814;

    @Override
    public String print(NatoReport report) {
        return (new NatoPostPrinter(report.getPost()).addMainTag().addDescription().addUserDetails().addMessage(" **"+report.getNaaValue()+"**;").print());
    }
}
