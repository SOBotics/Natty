package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class LinkOnlyAnswerFilter implements Filter {
    private Post post;
    private double value;

    public LinkOnlyAnswerFilter(Post post) {
        this.post = post;
        value = 2;
    }

    @Override
    public boolean filter() {
        return CheckUtils.checkIfLinkOnlyAnswer(post,40) && !CheckUtils.checkIfCodeBlock(post);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Possible Link Only";
    }
}
