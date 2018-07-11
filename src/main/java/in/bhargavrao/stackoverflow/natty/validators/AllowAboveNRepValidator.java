package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowAboveNRepValidator implements Validator {

    private int rep;

    public AllowAboveNRepValidator(int rep) {
        this.rep = rep;
    }

    @Override
    public boolean validate(Post post) {
        return post.getAnswerer().getReputation() >= rep;
    }

    @Override
    public String descriptor() {
        return "User not above reputation "+rep;
    }
}
