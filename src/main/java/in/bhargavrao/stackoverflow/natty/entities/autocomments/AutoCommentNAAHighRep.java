package in.bhargavrao.stackoverflow.natty.entities.autocomments;

public class AutoCommentNAAHighRep implements AutoComment {

	@Override
	public String getIdentifier() {
		return "NAAhighrep";
	}

	@Override
	public String getText() {
		return "This post isn't an actual attempt at answering the question. Please note [Stack Overflow doesn't work like a discussion forum](http://stackoverflow.com/tour), it is a Q&A site where every post is either a question or an answer to a question. Posts can also have [comments](http://stackoverflow.com/help/privileges/comment) - small sentences like this one - that can be used to critique or request clarification from an author. This should be either a comment or [a new question](http://stackoverflow.com/questions/ask).";
	}

}
