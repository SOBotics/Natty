package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class SelfAnswerFilter implements Filter {
    private Post post;
    private double value;

    public SelfAnswerFilter(Post post) {
        this.post = post;
        value = -3;
    }

    @Override
    public boolean filter() {
        return post.getAnswerer().getUserId()==post.getAsker().getUserId();
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Self Answer";
    }
}
