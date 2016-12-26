package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;

/**
 * Created by bhargav.h on 27-Oct-16.
 */
public class HeadquartersPostPrinter implements in.bhargavrao.stackoverflow.natty.printers.PostPrinter {

    public final long roomId = 126814;

    @Override
    public String print(PostReport report) {
        return (new PostPrinter(report.getPost()).addMainTag().addDescription().addUserDetails().addMessage(" **"+report.getNaaValue()+"**;").print());
    }
}
