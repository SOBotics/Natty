package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class RPublicPostPrinter implements in.bhargavrao.stackoverflow.natty.printers.PostPrinter {

    public final long roomId = 25312;

    @Override
    public String print(PostReport report) {

        PostPrinter rpp = new PostPrinter(report.getPost()).addMainTag().addDescription().addUserDetails();
        if(report.getNaaValue()>2.5){
            rpp.addMessage("  **Possible NAA**");
        }
        return  (rpp.print());
    }
}
