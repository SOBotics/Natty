package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by bhargav.h on 12-Oct-16.
 */
public class LengthFilter implements Filter {
    private Post post;
    private double value;

    public LengthFilter(Post post) {
        this.post = post;
        value = 1;
    }

    @Override
    public boolean filter() {
        return true;//post.getBodyMarkdown().length()<500;
    }

    @Override
    public double getValue() {
        int length = post.getBodyMarkdown().length();
        if(length < 100){
            return 1.5;
        }
        else if(length < 250){
            return 1.0;
        }
        else if(length < 500){
            return 0.5;
        }
        else {
            return -0.5;
        }
    }

    @Override
    public String description() {
        return "Low Length";
    }
}
