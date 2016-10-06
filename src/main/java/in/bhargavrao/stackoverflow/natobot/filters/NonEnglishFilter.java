package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;


/**
 * Created by bhargav.h on 04-Oct-16.
 */
public class NonEnglishFilter implements Filter {
    private NatoPost post;
    private double value;
    private String language;

    public NonEnglishFilter(NatoPost post) {
        this.post = post;
        value = 2;
        language = null;

    }

    @Override
    public boolean filter() {
        String language = CheckUtils.checkIfNonEnglish(post);
        if(language!=null && !language.equalsIgnoreCase("en")){
            this.language = language;
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
        return "Non English Post - "+language;
    }
}
