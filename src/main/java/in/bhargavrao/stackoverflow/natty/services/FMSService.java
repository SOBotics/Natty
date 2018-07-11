package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class FMSService {

    private String FMSPath;
    private String FMSUrl;

    public FMSService() {
        PropertyService propertyService = new PropertyService();
        FMSPath = propertyService.getFMSPath();
        FMSUrl = propertyService.getFMSUrl();
    }

    public String storeReport(PostReport report){

        Post np = report.getPost();
        String htmlString=
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head><title>"+np.getTitle()+"</title></head>" +
                        "<link href='style.css' rel='stylesheet' >" +
                        "<body>" +
                        "<h2><a href='//"+np.getSiteUrl()+"/a/"+np.getAnswerID()+"'>"+np.getTitle()+"</a></h2><br />"+
                        "<pre style='border:1px solid black;border-radius:5px'><code>" +np.getBody()+"</code></pre>" +
                        "<p>Posted by " +
                        "<a href='"+np.getSiteUrl()+"/users/"+ np.getAnswerer().getUserId() + "'>"
                          +np.getAnswerer().getUsername()+"</a> ("+ np.getAnswerer().getReputation()+") at "+
                           np.getAnswerCreationDate()+"</p>"+
                        "<p>Caught for</p>";

        for(String reason :report.getCaughtFor()){
            htmlString+= reason +"<br/>";
        }
        htmlString+="</body></html>";
        try {
            String filename = FMSPath + np.getAnswerID() + ".html";
            Files.write(Paths.get(filename), Arrays.asList(htmlString), StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return FMSUrl+"/"+np.getAnswerID()+".html";
    }

    public String storeFeedback(){

        return "";
    }
}
