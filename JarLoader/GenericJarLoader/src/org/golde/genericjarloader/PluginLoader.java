package org.golde.genericjarloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.golde.genericjarloader.api.Plugin;
import org.golde.genericjarloader.api.PluginDescriptionFile;
import org.golde.genericjarloader.api.PluginException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public class PluginLoader {

	//JSON instance for the plugin.json file
	private static final Gson GSON = new GsonBuilder().serializeNulls().create();

	//Map from FIle to Plugin instance.
	//Really not the best way to do it but it works for this example
	private HashMap<File, Plugin> map = new HashMap<File, Plugin>();

	/**
	 * Try to load a plugin
	 * @param file path to jar file
	 * @return 
	 */
	public Plugin load(File file) {

		try {
			
			//Make sure the plugin isn't loaded
			//if it is, throw an exception
			if(map.containsKey(file)) {
				throw new PluginException("Plugin already loaded.");
			}

			//Gets the plugin.json file out of the jar file
			PluginDescriptionFile pluginDescriptionFile = getPluginDescriptionFile(file);

			//get the class loader
			ClassLoader loader = URLClassLoader.newInstance( new URL[] { file.toURI().toURL() }, getClass().getClassLoader() );
			
			//load the main class specified in the plugin.json file
			Class<?> clazz = Class.forName(pluginDescriptionFile.getMain(), true, loader);
			
			//Get an instance of the main class that should be ran
			Class<? extends Plugin> instanceClass = clazz.asSubclass(Plugin.class);

			//Get the constructor of the instance class
			Constructor<? extends Plugin> instanceClassConstructor = (Constructor<? extends Plugin>) instanceClass.getConstructor();
			
			//Create a new instance of it
			Plugin plugin = instanceClassConstructor.newInstance();
			
			//set the description file, so we can read it from the plugin instance.
			plugin.setDescriptionFile(pluginDescriptionFile);

			//add the plugin to the map of enabled plugins
			map.put(file, plugin);
			
			//let us know it loaded
			System.out.println("Loaded '" + plugin.getDescriptionFile().getName() + " v" + plugin.getDescriptionFile().getVersion() + "'");

			//call onEnable
			plugin.onEnable();
			
			//return the instance
			return plugin;
		}
		catch(MalformedURLException e) {
			throw new PluginException("Failed to convert the file path to a URL.", e);
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new PluginException("Failed to create a new instance of the plugin.", e);
		}
	}

	/**
	 * Unload a plugin
	 * @param file
	 */
	public void unload(File file) {

		//Make sure we can't unload the plugin twice
		if(!map.containsKey(file)) {
			throw new PluginException("Can't unload a Plugin that wasn't loaded in the first place.");
		}

		//get the instance
		Plugin plugin = map.get(file);
		
		//call on disable
		plugin.onDisable();
		
		//remove the file from the map so it can be enabled again
		map.remove(file);
		
		//Let us know it was unloaded
		System.out.println("Unloaded '" + plugin.getDescriptionFile().getName() + " v" + plugin.getDescriptionFile().getVersion() + "'");
	
		//TODO: Garbage collect?
	}

	
	/**
	 * Reload a plugin
	 * @param file
	 */
	public void reload(File file) {
		unload(file);
		load(file);
	}

	/**
	 * Get a plugins description file from the jar
	 * @param file the jar file
	 * @return a object with everything in the plugin description
	 */
	private PluginDescriptionFile getPluginDescriptionFile(File file) {

		try {
			
			//Create a zip file
			ZipFile zipFile = new ZipFile(file);

			//Be able to loop over every file
			//TODO: Only really need the top level tbh
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			//Break the loop when this isn't null
			PluginDescriptionFile pluginJson = null;

			//Go over every file in the zip file until we find the plugin.json file
			//TODO: Really only need the top level entries tbh
			while(entries.hasMoreElements() && pluginJson == null){
				ZipEntry entry = entries.nextElement();

				if(!entry.isDirectory() && entry.getName().equals("plugin.json")) {
					InputStream stream = zipFile.getInputStream(entry);
					try {
						//try to initalize it to a object
						pluginJson = GSON.fromJson(new InputStreamReader(stream), PluginDescriptionFile.class);
					}
					catch(JsonParseException jsonParseException) {
						throw new PluginException("Failed to parse JSON:", jsonParseException);
					}
				}
			}

			//if we don't find it, throw an excepiton
			if(pluginJson == null) {
				zipFile.close();
				throw new PluginException("Failed to find plugin.json in the root of the jar.");
			}

			//close the zip
			zipFile.close();
			
			//return the object
			return pluginJson;
		}
		catch(IOException e) {
			throw new PluginException("Failed to open the jar as a zip:", e);
		}

		
	}

}
