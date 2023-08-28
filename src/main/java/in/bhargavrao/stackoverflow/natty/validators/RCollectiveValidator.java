package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by MDoubleDash on 28-Aug-23.
 */
public class RCollectiveValidator implements Validator {

    @Override
    public boolean validate(Post post) {
        return new AllowOnlyTagValidator("r").validate(post) &&
               new AllowAllNewAnswersValidator().validate(post);
    }

    @Override
    public String descriptor() {
        return "Not a new answer on an old question tagged R.";
    }

}