package in.bhargavrao.stackoverflow.natty.exceptions;

import in.bhargavrao.stackoverflow.natty.model.Feedback;

/**
 * Created by bhargav.h on 18-Jun-18.
 */
public class PostNotStoredException extends Exception {

    private String postLink;

    public PostNotStoredException(String postLink){
        this.postLink = postLink;
    }

    @Override
    public String getMessage() {
        return "Post not detected by the system - "+postLink;
    }
}
