package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.model.Post;

public interface CheckerService<T> {

     T check(Post post);
}
