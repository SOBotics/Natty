package in.bhargavrao.stackoverflow.natty.entities.autocomments;

public class AutoCommentNAALowRep implements AutoComment {

	@Override
	public String getIdentifier() {
		return "NAAlowrep";
	}

	@Override
	public String getText() {
		return "This does not provide an answer to the question. Please note that [Stack Overflow doesn't work like a discussion forum](//stackoverflow.com/tour) and, although you do not have enough reputation to comment, [avoid posting comments as answers](http://meta.stackexchange.com/q/214173/347985). You can [search for similar questions](//stackoverflow.com/search), or refer to the related linked questions, to find an answer. If you have a related but different question, [ask a new question](//stackoverflow.com/questions/ask), possibly referencing this one to help provide context.";
	}

}
