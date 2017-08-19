package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by bhargav.h on 12-Sep-16.
 */
public class AllowOnlyNCharsValidator implements Validator {

    private int len;

    public AllowOnlyNCharsValidator(int len) {
        this.len = len;
    }

    @Override
    public boolean validate(Post post) {
        return post.getBody().length()<len;
    }

}
