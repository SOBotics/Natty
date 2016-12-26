package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class ContainsQMFilter implements Filter {
    private Post post;
    private double value;

    public ContainsQMFilter(Post post) {
        this.post = post;
        value = 1;
    }

    @Override
    public boolean filter() {
        return !CheckUtils.checkIfEndsWithQm(post) && CheckUtils.checkIfBodyContainsQm(post);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Contains ?";
    }
}
