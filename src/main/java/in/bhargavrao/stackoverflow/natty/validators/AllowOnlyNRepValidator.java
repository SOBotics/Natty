package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.entities.Post;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowOnlyNRepValidator implements Validator {

    private int rep;

    public AllowOnlyNRepValidator(int rep) {
        this.rep = rep;
    }

    @Override
    public boolean validate(Post post) {
        return post.getAnswerer().getReputation() <= rep;
    }

}
