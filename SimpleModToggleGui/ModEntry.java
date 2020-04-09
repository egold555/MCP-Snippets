package clientname.gui.options;

import org.apache.commons.lang3.StringUtils;

import clientname.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;

public class ModEntry implements GuiListExtended.IGuiListEntry, Comparable<ModEntry> {

	private final GuiCheckBox checkbox;
	private final String name;
	private final Mod mod;
	private final GuiModToggle gui;
	
	public ModEntry(GuiModToggle inGui, Mod mod) {
		this.mod = mod;
		name = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(mod.getClass().getSimpleName().replace("Mod", "").replaceAll("\\d+", "")), " ");
		checkbox = new GuiCheckBox(0, 0, 0, mod.isEnabled());
		this.gui = inGui;
	}
	
	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
		this.checkbox.xPosition = x + 200;
		this.checkbox.yPosition = y;
		this.checkbox.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
		this.mod.setEnabled(this.checkbox.isChecked());
		gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, name, x, y + 4, -1);
	}

	@Override
	public boolean mousePressed(int slotIndex, int x, int y, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
		return this.checkbox.mousePressed(Minecraft.getMinecraft(), x, y);
	}

	@Override
	public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
		this.checkbox.mouseReleased(x, y);
	}
	
	@Override
	public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
		
	}

	@Override
	public int compareTo(ModEntry o) {
		return this.name.compareTo(o.name);
	}

}
