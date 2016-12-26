package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.entities.Post;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowAllNewValidator implements Validator {

    @Override
    public boolean validate(Post post) {
        return post.getAnswerCreationDate().isBefore(post.getQuestionCreationDate().plusSeconds(2592000));
    }

}
