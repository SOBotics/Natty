package in.bhargavrao.stackoverflow.natty.entities.autocomments;

public class AutoCommentNAAHighRep implements AutoComment {

	@Override
	public String getIdentifier() {
		return "NAAhighrep";
	}

	@Override
	public String getText() {
		return "This post doesn't look like an attempt to answer this question. Every post here is expected to be an explicit attempt to *answer* this question; if you have a critique or need a clarification of the question or another answer, you can [post a comment](//stackoverflow.com/help/privileges/comment) (like this one) directly below it. Please remove this answer and create either a comment or a new question. See: [Ask questions, get answers, no distractions](//stackoverflow.com/tour)";
	}

}
