package org.golde.genericjarloader.example;

import org.golde.genericjarloader.api.Plugin;

/**
 * Exampleplugin that prints when it was enabled and disabled.
 * @author Eric Golde
 *
 */
public class ExamplePlugin extends Plugin {

	@Override
	public void onEnable() {
		System.out.println("[ExamplePlugin] I was enabled!");
	}
	
	@Override
	public void onDisable() {
		System.out.println("[ExamplePlugin] I was disabled!");
	}
	
}
