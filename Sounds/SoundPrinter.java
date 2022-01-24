package YOUR_PACKAGE;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.util.ResourceLocation;

//Created by Eric Golde 1/23/2022
public class SoundPrinter {

	//This method should be called from the net.minecraft.client.audio.SoundHandler class
	//Place method at the end of the "onResourceManagerReload" function.
	//Make sure to remove this method before exporting your client.
	public static void print(SoundRegistry registry) {

		//our list of sounds
		List<String> soundsRaw = new ArrayList<String>();

		//Turn resource locations into a list of strings
		for(ResourceLocation key : registry.getKeys()) {
			soundsRaw.add(key.getResourcePath());
		}

		//sort alphbetically
		Collections.sort(soundsRaw);

		//file put in ".minecraft", or "<mcp project>/jars"
		try {
			PrintWriter pw = new PrintWriter("Sounds.java");
			pw.println("package YOUR_PACKAGE;");
			pw.println();
			pw.println("import net.minecraft.util.ResourceLocation;");
			pw.println();
			pw.println("//Created by Eric Golde: https://github.com/egold555/MCP-Snippets/tree/master/Sounds");
			pw.println("public class Sounds {");
			pw.println();
			for(String s : soundsRaw) {
				final String javaVariableName = s.toUpperCase().replace(".", "_");
				pw.println("	public static final ResourceLocation " + javaVariableName + " = new ResourceLocation(\"" + s + "\");");
			}
			pw.println();
			pw.println("}");
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
