package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.model.Post;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public interface Validator {
    boolean validate(Post post);
    String descriptor();
}
