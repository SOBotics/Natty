package in.bhargavrao.stackoverflow.natty.filters;

import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.services.CheckerService;
import in.bhargavrao.stackoverflow.natty.services.PiledSymbolsCheckerService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Checks for piled symbols like ??? or !!!.
 * 
 * @note Currently, the value is set to 0.
 * */
public class PiledSymbolsFilter implements Filter {
	private Post post;
    private double value;
    private String symbols;
	private CheckerService<String> piledSymbolsChecker;
	
	public PiledSymbolsFilter(Post post) {
		this.post = post;
		this.value = 1;
		this.symbols = null;
		this.piledSymbolsChecker = new PiledSymbolsCheckerService();
	}
	
	
	@Override
	public boolean filter() {
		this.symbols = piledSymbolsChecker.check(this.post);
		return this.symbols != null && this.symbols.length() > 0;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public String description() {
		return "Piled symbols - "+this.symbols;
	}

}
