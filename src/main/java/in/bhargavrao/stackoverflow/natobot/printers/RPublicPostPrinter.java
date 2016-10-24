package in.bhargavrao.stackoverflow.natobot.printers;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.NatoPostPrinter;

import java.util.List;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class RPublicPostPrinter implements PostPrinter {

    public final long roomId = 25312;

    @Override
    public String print(NatoReport report) {

        NatoPostPrinter rpp = new NatoPostPrinter(report.getPost()).addMainTag().addDescription().addUserDetails();
        if(report.getNaaValue()>2.5){
            rpp.addMessage("  **Possible NAA**");
        }
        return  (rpp.print());
    }
}
