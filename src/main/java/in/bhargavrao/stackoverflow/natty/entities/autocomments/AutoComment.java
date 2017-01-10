package in.bhargavrao.stackoverflow.natty.entities.autocomments;

/**
 * This represents a comment, that is automatically posted by Natty.
 * */
public interface AutoComment {
	/**
	 * @returns A short code to identify the comment. For example: "NAA" or "link-only"
	 * */
	String getIdentifier();
	
	/**
	 * @returns The text that will be posted
	 * */
	String getText();
}
