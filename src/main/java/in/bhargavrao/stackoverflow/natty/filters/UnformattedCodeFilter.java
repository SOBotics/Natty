package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class UnformattedCodeFilter implements Filter {
    private Post post;
    private double value;

    public UnformattedCodeFilter(Post post) {
        this.post = post;
        value = -0.5;
    }

    @Override
    public boolean filter() {
        return CheckUtils.checkIfUnformatted(post);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Unformatted Code";
    }
}
