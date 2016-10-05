package in.bhargavrao.stackoverflow.natobot.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.filters.*;

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

        JsonObject user = answer.get("owner").getAsJsonObject();

        np.setAnswerCreationDate(Instant.ofEpochSecond(answer.get("creation_date").getAsInt()));
        np.setAnswerID(answer.get("answer_id").getAsInt());
        np.setQuestionCreationDate(Instant.ofEpochSecond(question.get("creation_date").getAsInt()));
        np.setQuestionID(answer.get("question_id").getAsInt());
        np.setReputation(user.get("reputation").getAsInt());
        np.setTitle(JsonUtils.escapeHtmlEncoding(question.get("title").getAsString()));
        np.setMainTag(question.get("tags").getAsJsonArray().get(0).getAsString());
        np.setTags(StreamSupport.stream(question.get("tags").getAsJsonArray().spliterator(), false).map(JsonElement::getAsString).toArray(String[]::new));
        np.setBody(answer.get("body").getAsString());
        np.setBodyMarkdown(JsonUtils.escapeHtmlEncoding(answer.get("body_markdown").getAsString()));
        np.setUserName(JsonUtils.escapeHtmlEncoding(user.get("display_name").getAsString()));
        np.setUserType(user.get("user_type").getAsString());
        np.setUserID(user.get("user_id").getAsInt());

        return np;
    }

    public static List<Object> getNaaValue(NatoPost np, NatoPostPrinter pp) {
        Double f = 0.0;

        List<Filter> filters = new ArrayList<Filter>(){{
            add(new BlacklistedFilter(np));
            add(new ContainsQMFilter(np));
            add(new EndsWithQmFilter(np));
            add(new LinkOnlyAnswerFilter(np));
            add(new NoCodeBlockFilter(np));
            add(new NonEnglishFilter(np));
            add(new OneLineFilter(np));
            add(new SalutationsFilter(np));
            add(new StartsWithKeywordFilter(np));
            add(new UnregisteredUserFilter(np));
            add(new UserMentionedFilter(np));
            add(new WhitelistedFilter(np));
        }};

        for(Filter filter: filters){
            if(filter.filter()){
                f+=filter.getValue();
                pp.addMessage(" **"+filter.description()+"**; ");
            }
        }

        List<Object> returnValues =  new ArrayList<>();
        returnValues.add(f);
        returnValues.add(pp);
        return returnValues;
    }
}
