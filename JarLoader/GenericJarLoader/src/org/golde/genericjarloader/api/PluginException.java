package org.golde.genericjarloader.api;

/**
 * Generic exception for when shit hits the fan loading and unloading plugins
 * @author Eric Golde
 *
 */
public class PluginException extends RuntimeException {

	private static final long serialVersionUID = -8161437637562938254L;

	public PluginException(String whatHappened) {
		super(whatHappened);
	}
	
	public PluginException(String whatHappened, Throwable cause) {
		super(whatHappened, cause);
	}
	
}
