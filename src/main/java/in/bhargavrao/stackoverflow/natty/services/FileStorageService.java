package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;
import in.bhargavrao.stackoverflow.natty.utils.JsonUtils;
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
    public String listWord(String word, ListType type) {

        String filename = getFilename(type);

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
    public String unListWord(String word, ListType type) {

        String filename = getFilename(type);

        try{
            if(FileUtils.checkIfInFile(filename,word)){
                FileUtils.removeFromFile(filename,word);
                return "Done";
            }
            else {
                return ("It's not there in the file");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return ("Some Error Occured");
        }

    }

    @Override
    public String addCheckUsers(Integer userid, String message, String sitename) {
        try {
            FileUtils.appendToFile(checkUsers,userid+","+sitename+","+ JsonUtils.escapeHtmlEncoding(message));
            return "Added user successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occured";
        }
    }


    @Override
    public String checkUsers(Integer userid, String sitename) throws NoSuchUserFoundException {

        String filename = checkUsers;

        try {
            for(String line: FileUtils.readFile(filename)){
                String users[] = line.split(",");
                if(users[0].equals(Integer.toString(userid))  && users[1].equals(sitename) ){
                    return users[2];
                }
            }
        } catch (IOException e) {
            return "Some Error Occured.";
        }

        throw new NoSuchUserFoundException();
    }

    @Override
    public String storeReminders(String reminder) {
        try {
            String filename = featureRequests;
            if (FileUtils.checkIfInFile(filename, reminder)) {
                return  "Already present as FR";
            }
            else{
                FileUtils.appendToFile(filename, reminder);
                return  "Added request successfully";
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return "Some Error Occured";
        }
    }

    @Override
    public String saveFeedback(Feedback feedback) {
        return null;
    }

    @Override
    public String storeReport(PostReport report) {
        return null;
    }


    private String getFilename(ListType type) {
        String filename = null;
        switch (type){

            case BLACKLIST: filename = blacklistFile; break;
            case WHITELIST: filename = whitelistFile; break;
            case SALUTE: filename = salutationsFile; break;
        }
        return filename;
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
