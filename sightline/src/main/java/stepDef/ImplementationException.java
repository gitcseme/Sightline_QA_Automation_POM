package stepDef;

@SuppressWarnings("serial")
public class ImplementationException extends Exception {
	
	public ImplementationException() {
		super("Not yet implemented");
	}

	public ImplementationException(String contextName) {
		super(String.format("Context '%s' not yet implemented",contextName));
	}

}
