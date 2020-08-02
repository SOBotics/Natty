package in.bhargavrao.stackoverflow.natty.utils;

import java.util.ArrayList;
import java.util.List;

public class AutoFlagUtils {

    // Just a hacky way to achieve the stop autoflagging part. Will soon be refactored.

    public static boolean shouldAutoflag=true;
    public static List<String> autoFlagSites = new ArrayList<String>() {{
        add("stackoverflow");
    }};

}
