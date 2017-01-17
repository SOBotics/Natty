package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class OneLineFilter implements Filter {
    private Post post;
    private double value;

    public OneLineFilter(Post post) {
        this.post = post;
        value = 0.5;
    }

    @Override
    public boolean filter() {
        return CheckUtils.checkIfOneLine(post);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "One Line only";
    }
}
