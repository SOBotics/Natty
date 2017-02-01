package in.bhargavrao.stackoverflow.natty.filters;

import java.io.InputStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;

import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;

public class NewBlacklistedFilter implements Filter {

	private Post post;
    private double value;
    private double maxValue;
    private String listedWord;

    public NewBlacklistedFilter(Post post) {
        this.post = post;
        value = 0;
        maxValue = 4.0;
        listedWord = null;
    }
	
	@Override
	public boolean filter() {
		//the usual check first
		String listedWord = CheckUtils.checkForBlackListedWords(post);
        if(listedWord!=null){
            this.listedWord = listedWord;
            
            //calculate the value
            String jsonString;
            try {
            	InputStream is = JsonParser.class.getResourceAsStream(FilePathUtils.intelligentBlacklistFile);
            	jsonString = is.toString();
            } catch (Throwable e) {
            	e.printStackTrace();
            	return false;
            }
            
            JsonParser parser = new JsonParser();
            
            JsonObject list = (JsonObject) parser.parse(jsonString);
            JsonArray array = list.getAsJsonArray();
            
            for(JsonElement item : array) {
            	JsonObject object = item.getAsJsonObject();
            	
            	String phrase = object.get("phrase").getAsString();
            	if (phrase != null && phrase.equals(this.listedWord)) {
            		JsonObject feedbacks = object.getAsJsonObject("feedback_counts");
            		JsonObject tpObj = (JsonObject) feedbacks.get("tp");
            		JsonObject fpObj = (JsonObject) feedbacks.get("fp");
            		JsonObject tnObj = (JsonObject) feedbacks.get("tn");
            		JsonObject neObj = (JsonObject) feedbacks.get("ne");
            		
            		if (tpObj != null && fpObj != null && tnObj != null && neObj != null ) {
            			double tp = tpObj.getAsDouble();
            			double fp = fpObj.getAsDouble();
            			double tn = tnObj.getAsDouble();
            			double ne = neObj.getAsDouble();
            			
            			double totalPosts = tp+fp+tn+ne;
                		
                		//Score calculation: ((1−fps/postsMatchingThePhrase)^maxScore) * maxScore
                		this.value = Math.pow((1 - (fp / totalPosts)), this.maxValue) * this.maxValue;
            		}
            		
            		
            		break;
            	}
            }
            
            return true;
        }
        return false;
	}

	@Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Contains Blacklisted Word - "+listedWord;
    }

}
