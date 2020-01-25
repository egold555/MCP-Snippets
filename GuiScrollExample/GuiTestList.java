package clientname.gui.tst.scroll;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiTestList extends GuiScreen {

	private ScrollListTest scrollerThingy;
	
	@Override
	public void initGui() {
		
		scrollerThingy = new ScrollListTest(mc, this);
		this.buttonList.clear();

	}
	
	@Override
	public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.scrollerThingy.handleMouseInput();
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton != 0 || !this.scrollerThingy.mouseClicked(mouseX, mouseY, mouseButton))
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (state != 0 || !this.scrollerThingy.mouseReleased(mouseX, mouseY, state))
        {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        //this.drawDefaultBackground();
        this.scrollerThingy.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Scroll Test (Like controls)", this.width / 2, 8, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
}
