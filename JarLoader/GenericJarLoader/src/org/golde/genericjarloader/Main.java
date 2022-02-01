package org.golde.genericjarloader;

import java.io.File;

public class Main {

	
	/*
	 * Test Code:
	 * 
	 * Load all plugins in the res/plugins folder
	 * 
	 * Wait 3 seconds
	 * 
	 * Unload all plugins in the res/plugins folder
	 *
	 * Exit Program
	 */
	
	public static void main(String[] args) throws InterruptedException {

		//create a new plugin loader
		PluginLoader loader = new PluginLoader();

		//Plugins folder
		File pluginsFolder = new File("res/plugins");

		//Loop over every jar file and load it
		//TODO: If it doesn't end in .jar its not a plugin
		for(File f : pluginsFolder.listFiles()) {

			loader.load(f);

		}

		//Sleep for 3 seconds
		Thread.sleep(3000);

		
		//Loop over every jar file and unload it
		//TODO: If it doesn't end in .jar its not a plugin
		for(File f : pluginsFolder.listFiles()) {

			loader.unload(f);

		}

	}

}
