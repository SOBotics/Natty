package in.bhargavrao.stackoverflow.natty.entities.autocomments;

public class AutoCommentNAAHighRep implements AutoComment {

	@Override
	public String getIdentifier() {
		return "NAAhighrep";
	}

	@Override
	public String getText() {
		return "NAA for >=50 rep";
	}

}
