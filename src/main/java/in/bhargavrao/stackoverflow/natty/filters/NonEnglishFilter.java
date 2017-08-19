package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;


/**
 * Created by bhargav.h on 04-Oct-16.
 */
public class NonEnglishFilter implements Filter {
    private Post post;
    private double value;
    private String language;

    public NonEnglishFilter(Post post) {
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
