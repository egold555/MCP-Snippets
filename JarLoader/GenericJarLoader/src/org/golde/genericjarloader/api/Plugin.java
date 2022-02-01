package org.golde.genericjarloader.api;

public abstract class Plugin {

	private PluginDescriptionFile descriptionFile;
	
	public void onEnable() {};
	public void onDisable() {}
	
	
	public final void setDescriptionFile(PluginDescriptionFile descriptionFile) {
		if(this.descriptionFile != null) {
			throw new PluginException("Can't set the description file. Its already set!");
		}
		this.descriptionFile = descriptionFile;
	}
	
	//Can't use lombok to create a final getter smh
	public final PluginDescriptionFile getDescriptionFile() {
		return descriptionFile;
	}
	
}
