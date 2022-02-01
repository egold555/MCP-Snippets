# Jar Loader
Very crude example of loading jars into memory and calling onEnable and onDisable like spigot / forge.

This is going to need some modifications to work with mods and a client, but it should be a good starting point.

## Folders
GenericJarLoader - The actual API and the class that loads the jar

GenericJarLoaderExample - Example plugin that prints hello world.

lib - libraries for both projects. You **dont need to** use these, I used them for making my life easier. If you were to include this in a client, you could get rid of lombok, and replace GSON with org.json which is included with Minecraft by default.