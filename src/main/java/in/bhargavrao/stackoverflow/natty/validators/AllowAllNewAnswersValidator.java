package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowAllNewAnswersValidator implements Validator {

    @Override
    public boolean validate(Post post) {
        return post.getAnswerCreationDate().isAfter(post.getQuestionCreationDate().plusSeconds(2592000));
    }

}
