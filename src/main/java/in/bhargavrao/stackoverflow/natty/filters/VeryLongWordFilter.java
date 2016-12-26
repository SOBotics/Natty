package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class VeryLongWordFilter implements Filter {
    private Post post;
    private double value;

    public VeryLongWordFilter(Post post) {
        this.post = post;
        value = 1.5;
    }

    @Override
    public boolean filter() {
        String listedWord = CheckUtils.checkForLongWords(post);
        if(listedWord!=null){
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
        return "Contains Very Long Word";
    }
}
