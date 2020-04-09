package clientname.gui.options;

import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;

public class GuiModToggle extends GuiScreen {

	private ScrollListModToggle scrollPanel;

	@Override
	public void initGui() {
		scrollPanel = new ScrollListModToggle(mc, this);
		this.buttonList.clear();
		
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.scrollPanel.handleMouseInput();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.scrollPanel.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		this.scrollPanel.mouseReleased(mouseX, mouseY, state);
		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.scrollPanel.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRendererObj, "Mod Options", this.width / 2, 8, 16777215);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
