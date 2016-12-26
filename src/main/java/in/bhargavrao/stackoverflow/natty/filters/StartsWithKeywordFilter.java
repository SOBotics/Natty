package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.entities.Post;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class StartsWithKeywordFilter implements Filter {
    private Post post;
    private double value;

    public StartsWithKeywordFilter(Post post) {
        this.post = post;
        value = 1.5;
    }

    @Override
    public boolean filter() {
        if(post.getBodyMarkdown().contains(" "))
            return post.getBodyMarkdown().trim().toLowerCase().substring(0,post.getBodyMarkdown().indexOf(' ')).matches("(what|where|why|same|can|did)");
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
