package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.services.CheckerService;
import in.bhargavrao.stackoverflow.natty.services.NonEnglishCheckerService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;


/**
 * Created by bhargav.h on 04-Oct-16.
 */
public class NonEnglishFilter implements Filter {
    private Post post;
    private double value;
    private String language;
    private CheckerService<String> nonEnglishCheckerService;

    public NonEnglishFilter(Post post) {
        this.post = post;
        value = 2;
        language = null;
        nonEnglishCheckerService = new NonEnglishCheckerService();
    }

    @Override
    public boolean filter() {

        String language = nonEnglishCheckerService.check(post);
        if(language!=null && !language.equalsIgnoreCase("en")){
            this.language = language;
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
        return "Non English Post - "+language;
    }
}
