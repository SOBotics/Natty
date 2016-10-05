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
        if(post.getBodyMarkdown().contains(" "))
            return post.getBodyMarkdown().trim().toLowerCase().substring(0,post.getBodyMarkdown().indexOf(' ')).matches("(how|what|where|why|same|can|did)");
        return false;
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
