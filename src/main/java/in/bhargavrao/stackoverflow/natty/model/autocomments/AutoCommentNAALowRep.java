package in.bhargavrao.stackoverflow.natty.entities.autocomments;

public class AutoCommentNAALowRep implements AutoComment {

	@Override
	public String getIdentifier() {
		return "NAAlowrep";
	}

	@Override
	public String getText() {
		return "This does not provide an answer to the question. You can [search for similar questions](//stackoverflow.com/search), or refer to the related and linked questions on the right-hand side of the page to find an answer. If you have a related but different question, [ask a new question](//stackoverflow.com/questions/ask), and include a link to this one to help provide context. See: [Ask questions, get answers, no distractions](//stackoverflow.com/tour)";
	}

}
