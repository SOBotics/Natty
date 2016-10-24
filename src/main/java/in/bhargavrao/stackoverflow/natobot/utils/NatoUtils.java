package in.bhargavrao.stackoverflow.natobot.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.entities.SOUser;
import in.bhargavrao.stackoverflow.natobot.filters.*;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by bhargav.h on 29-Sep-16.
 */
public class NatoUtils {

    public static NatoPost getNatoPost(JsonObject answer, JsonObject question){


        NatoPost np = new NatoPost();

        JsonObject answererJSON = answer.get("owner").getAsJsonObject();
        JsonObject askerJSON = question.get("owner").getAsJsonObject();

        np.setAnswerCreationDate(Instant.ofEpochSecond(answer.get("creation_date").getAsInt()));
        np.setAnswerID(answer.get("answer_id").getAsInt());
        np.setQuestionCreationDate(Instant.ofEpochSecond(question.get("creation_date").getAsInt()));
        np.setQuestionID(answer.get("question_id").getAsInt());
        np.setTitle(JsonUtils.escapeHtmlEncoding(question.get("title").getAsString()));
        np.setMainTag(question.get("tags").getAsJsonArray().get(0).getAsString());
        np.setTags(StreamSupport.stream(question.get("tags").getAsJsonArray().spliterator(), false).map(JsonElement::getAsString).toArray(String[]::new));
        np.setBody(answer.get("body").getAsString());
        np.setBodyMarkdown(JsonUtils.escapeHtmlEncoding(answer.get("body_markdown").getAsString()));

        SOUser answerer = new SOUser();
        SOUser asker = new SOUser();

        try{
            answerer.setReputation(answererJSON.get("reputation").getAsLong());
            answerer.setUsername(JsonUtils.escapeHtmlEncoding(answererJSON.get("display_name").getAsString()));
            answerer.setUserType(answererJSON.get("user_type").getAsString());
            answerer.setUserId(answererJSON.get("user_id").getAsInt());

            asker.setReputation(askerJSON.get("reputation").getAsLong());
            asker.setUsername(JsonUtils.escapeHtmlEncoding(askerJSON.get("display_name").getAsString()));
            asker.setUserType(askerJSON.get("user_type").getAsString());
            asker.setUserId(askerJSON.get("user_id").getAsInt());
        }
        catch (Exception e){
            System.out.println("ASKER"+askerJSON);
            System.out.println("ANSWERER"+answererJSON);
            e.printStackTrace();
        }

        np.setAsker(asker);
        np.setAnswerer(answerer);

        return np;

    }


    public static NatoReport getNaaValue(NatoPost np) {
        Double f = 0.0;

        List<Filter> filters = new ArrayList<Filter>(){{
            add(new BlacklistedFilter(np));
            add(new ContainsQMFilter(np));
            add(new EndsWithQmFilter(np));
            add(new LengthFilter(np));
            add(new LinkOnlyAnswerFilter(np));
            add(new NoCodeBlockFilter(np));
            add(new NonEnglishFilter(np));
            add(new OneLineFilter(np));
            add(new ReputationFilter(np));
            add(new SalutationsFilter(np));
            add(new SelfAnswerFilter(np));
            add(new StartsWithKeywordFilter(np));
            add(new UnregisteredUserFilter(np));
            add(new UserMentionedFilter(np));
            add(new VeryLongWordFilter(np));
            add(new WhitelistedFilter(np));
        }};

        List<String> caughtFor = new ArrayList<>();
        List<Double> caughtNaa = new ArrayList<>();

        for(Filter filter: filters){
            if(filter.filter()){
                f+=filter.getValue();
                caughtFor.add(filter.description());
                caughtNaa.add(filter.getValue());
            }
        }

        return new NatoReport(np,f,caughtFor,caughtNaa);
    }

    public static String addFMS(NatoReport report){

        NatoPost np = report.getPost();

        String htmlString="<!DOCTYPE html><html><head><title>"+np.getTitle()+"</title></head><link href='style.css' rel='stylesheet' ><body><pre style='border:1px solid black;border-radius:5px'><code>"+np.getBody()+"</code></pre><p>Caught for</p>";
        for(String i:report.getCaughtFor()){
            htmlString+=i+"<br/>";
        }
        htmlString+="<p><a href='//stackoverflow.com/a/"+np.getAnswerID()+"'>Link to post</a></p></body></html>";
        try {
            FileUtils.createNewFile("./../tomcat/webapps/ROOT/NATO/" + np.getAnswerID() + ".html", htmlString);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "http://51.254.218.90:8000/NATO/"+np.getAnswerID()+".html";
    }

}
