package in.bhargavrao.stackoverflow.natty.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by bhargav.h on 05-Oct-16.
 */
public class FilePathUtils {
    public static String blacklistFile = "./data/BlackListedWords.txt";
    public static String whitelistFile = "./data/WhiteListedWords.txt";
    public static String salutationsFile = "./data/Salutations.txt";
    public static String optedUsersFile = "./data/OptedInUsersList.txt";
    public static String featureRequests = "./data/FeatureRequests.txt";
    public static String checkUsers = "./data/CheckUsers.txt";
    public static String blacklistedUsers = "./data/BlackListedUsers.txt";


    public static String vowpalDataLogFile = "./logs/vowpalData.txt";
    public static String outputErrorLogFile = "./logs/error.txt";

    public static String loginPropertiesFile = "./properties/login.properties";

    public static String outputCSVLogFileName = "output.csv";
    public static String outputReportLogFileName = "reports.txt";
    public static String outputCompleteLogFileName = "fullReports.txt";
    public static String outputSentinelIdLogFileName = "sentinelReports.txt";


    public static String getOutputCSVLogFile(String sitename){
        return getPath(sitename)+outputCSVLogFileName;
    }
    public static String getOutputReportLogFile(String sitename){
        return getPath(sitename)+outputReportLogFileName;
    }
    public static String getOutputCompleteLogFile(String sitename){
        return getPath(sitename)+outputCompleteLogFileName;
    }
    public static String getOutputSentinelIdLogFile(String sitename){
        return getPath(sitename)+outputSentinelIdLogFileName;
    }

    @NotNull
    private static String getPath(String sitename) {
        String path = "./";
        switch (sitename){
            case "stackoverflow": path+="logs/"; break;
            case "askubuntu"    : path+="aulogs/"; break;
        }
        return path;
    }


}
