package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.entities.Post;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class RPublicValidator implements Validator {

    @Override
    public boolean validate(Post post) {
        return new AllowAboveNRepValidator(10000).validate(post) &&
               new AllowOnlyTagValidator("r").validate(post);
    }

}
