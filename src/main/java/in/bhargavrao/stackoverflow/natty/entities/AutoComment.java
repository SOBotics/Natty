package in.bhargavrao.stackoverflow.natty.entities;

import in.bhargavrao.stackoverflow.natty.entities.AutoCommentType;

/**
 * This represents a comment, that is automatically posted by Natty.
 * */
public class AutoComment {
	
	/**
	 * A short code to identify the comment. For example: "NAA" or "link-only"
	 * */
	public String identifier;
	
	/**
	 * The text that will be posted
	 * */
	public String text;
	
	/**
	 * Initializes a comment for an AutoCommentType
	 * */
	public AutoComment(AutoCommentType type) {
		switch(type) {
		case NAA:
			this.identifier = "NAA";
			this.text = "This post isn't an actual attempt at answering the question. Please note [Stack Overflow doesn't work like a discussion forum](http://stackoverflow.com/tour), it is a Q&A site where every post is either a question or an answer to a question. Posts can also have [comments](http://stackoverflow.com/help/privileges/comment) - small sentences like this one - that can be used to critique or request clarification from an author. This should be either a comment or [a new question](http://stackoverflow.com/questions/ask).";
			break;
		case LINK_ONLY:
			this.identifier = "link-only";
			this.text = "A link to a potential solution is always welcome, but please [add context around the link](http://meta.stackexchange.com/a/8259) so your fellow users will have some idea what it is and why it’s there. Always quote the most relevant part of an important link, in case the target site is unreachable or goes permanently offline. Take into account that being barely more than a link to an external site is a possible reason as to [Why and how are some answers deleted?](http://stackoverflow.com/help/deleted-answers).";
			break;
		case UNDEFINED:
			this.identifier = "undefined";
			this.text = "";
		}
	}
	
	/**
	 * Returns the length of the comment's text property.
	 * */
	public int length() {
		return text.length();
	}
}
