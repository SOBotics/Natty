package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class GMTsValidator implements Validator {

    @Override
    public boolean validate(Post post) {
        return new AllowAllNewAnswersValidator().validate(post) &&
               new AllowOnlyTagValidator("r").validate(post);
    }

    @Override
    public String descriptor() {
        return "Not a new post on an old question tagged R.";
    }

}
