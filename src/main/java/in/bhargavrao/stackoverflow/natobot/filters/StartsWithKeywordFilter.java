package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class StartsWithKeywordFilter implements Filter {
    private NatoPost post;
    private double value;

    public StartsWithKeywordFilter(NatoPost post) {
        this.post = post;
        value = 1;
    }

    @Override
    public boolean filter() {
        return post.getBodyMarkdown().trim().matches("(How|What|Where|Why|Same|Can|Did).*");
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Starts with Keyword";
    }
}
