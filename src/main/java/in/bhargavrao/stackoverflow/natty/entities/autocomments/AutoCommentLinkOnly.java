package in.bhargavrao.stackoverflow.natty.entities.autocomments;

public class AutoCommentLinkOnly implements AutoComment {

	@Override
	public String getIdentifier() {
		return "link-only";
	}

	@Override
	public String getText() {
		return "A link to a potential solution is always welcome, but please [add context around the link](http://meta.stackexchange.com/a/8259) so your fellow users will have some idea what it is and why it’s there. Always quote the most relevant part of an important link, in case the target site is unreachable or goes permanently offline. Take into account that being barely more than a link to an external site is a possible reason as to [Why and how are some answers deleted?](http://stackoverflow.com/help/deleted-answers).";
	}
	
}
