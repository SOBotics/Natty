package in.bhargavrao.stackoverflow.natty.entities.autocomments;

public class AutoCommentLinkOnly implements AutoComment {

	@Override
	public String getIdentifier() {
		return "link-only";
	}

	@Override
	public String getText() {
		return "A link to a solution is welcome, but please ensure your answer is useful without it: [add context around the link](//meta.stackexchange.com/a/8259) so your fellow users will have some idea what it is and why it’s there, then quote the most relevant part of the page you're linking to in case the target page is unavailable. [Answers that are little more than a link may be deleted.](//stackoverflow.com/help/deleted-answers)";
	}
	
}
