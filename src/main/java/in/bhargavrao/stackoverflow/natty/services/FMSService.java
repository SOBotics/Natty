package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

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
                        "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>" +
                        "<body>" +
                        "<h2><a href='//"+np.getSiteUrl()+"/a/"+np.getAnswerID()+"'>"+np.getTitle()+"</a></h2>" +
                        "<p class='feedback'></p><br />"+
                        "<pre style='border:1px solid black;border-radius:5px'><code>" +np.getBody()+"</code></pre>" +
                        "<p>Posted by " +
                        "<a href='"+np.getSiteUrl()+"/users/"+ np.getAnswerer().getUserId() + "'>"
                          +np.getAnswerer().getUsername()+"</a> ("+ np.getAnswerer().getReputation()+") at "+
                           np.getAnswerCreationDate()+"</p>"+
                        "<p>Caught for</p>";

        for(String reason :report.getCaughtFor()){
            htmlString+= reason +"<br/>";
        }
        htmlString+="</body>" +
                "</html>";
        try {
            String filename = FMSPath + np.getAnswerID() + ".html";
            Files.write(Paths.get(filename), Arrays.asList(htmlString), StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return FMSUrl+"/"+np.getAnswerID()+".html";
    }

    public void storeFeedback(String postId,String chatUsername, String feedbackType, String sitename){

        String symbol = "";

        switch (feedbackType){
            case "t":
            case "tp": symbol = "<span class='fa fa-check' style='color:green'></span>"; break;
            case "f":
            case "fp": symbol = "<span class='fa fa-times' style='color:red'></span>"; break;
            case "n":
            case "ne": symbol = "<span class='fa fa-pencil' style='color:yellow'></span>"; break;
            case "tn": symbol = "<span class='fa fa-warning' style='color:orange'></span>"; break;
        }

        String feedbackString = "<p class='feedback'> <span title='"+chatUsername+"'>"+symbol+"</span>";
        String filename = FMSPath + postId + ".html";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            lines.set(0,String.join(feedbackString, lines.get(0).split("<p class='feedback'>")));
            Files.write(Paths.get(filename), lines, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
