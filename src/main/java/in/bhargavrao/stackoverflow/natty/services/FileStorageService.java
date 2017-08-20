package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public class FileStorageService implements StorageService {

    private String blacklistFile = "./data/BlackListedWords.txt";
    private String whitelistFile = "./data/WhiteListedWords.txt";
    private String salutationsFile = "./data/Salutations.txt";
    private String optedUsersFile = "./data/OptedInUsersList.txt";
    private String featureRequests = "./data/FeatureRequests.txt";
    private String checkUsers = "./data/CheckUsers.txt";
    private String blacklistedUsers = "./data/BlackListedUsers.txt";

    private String outputErrorLogFile = "./logs/error.txt";

    private String loginPropertiesFile = "./properties/login.properties";

    private String outputCSVLogFileName = "output.csv";
    private String outputReportLogFileName = "reports.txt";
    private String outputCompleteLogFileName = "fullReports.txt";
    private String outputSentinelIdLogFileName = "sentinelReports.txt";



    @Override
    public String ListWord(String word, ListType type) {

        String filename = null;
        switch (type){

            case BLACKLIST: filename = blacklistFile; break;
            case WHITELIST: filename = whitelistFile; break;
            case SALUTE: filename = salutationsFile; break;
        }

        try {
            if (FileUtils.checkIfInFile(filename, word)) {
                return "Already Blacklisted";
            }
            else{
                FileUtils.appendToFile(filename,word);
                return  "Added blacklist successfully";
            }
        } catch (IOException e) {
            return "Some error occurred";
        }
    }

    @Override
    public String UnListWord(String word, ListType type) {
        return null;
    }

    @Override
    public String SaveFeedback(Feedback feedback) {
        return null;
    }

    @Override
    public String StoreReport(PostReport report) {
        return null;
    }




    @NotNull
    private String getPath(String sitename) {
        String path = "./";
        switch (sitename){
            case "stackoverflow": path+="logs/"; break;
            case "askubuntu"    : path+="aulogs/"; break;
        }
        return path;
    }
}
