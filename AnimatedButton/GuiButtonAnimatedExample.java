package clientname.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonAnimatedExample extends GuiButton {

	//default constructors
	public GuiButtonAnimatedExample(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	public GuiButtonAnimatedExample(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
	}
	
	//this stores the height of the animated rectangle we are drawing
	int animatedHeight = 0;
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            
			//I am drawing the button as a rectangle, so I am not using any textures.
			
            /*
             * If we are hovered, we increese animatedHeight by one every frame.
             * Once we reach a greater height then the buttons height, we set it to the buttons height
             * 
             * When we are not hovering, we decreese the animatedHeight variable until its at 0
             * if it goes greater then 0, we set it to 0.
             */
            if(this.hovered) {
            	animatedHeight++;
            	if(animatedHeight > this.height) {
            		animatedHeight = height;
            	}
            }
            else {
            	animatedHeight--;
            	if(animatedHeight < 0) {
            		animatedHeight = 0;
            	}
            }
                  
            //normal rectangle
            this.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0xFFFF0000);
            
            //animated one
            this.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.animatedHeight, 0xFF00FF00);
            
            //default stuff from GuiButton.java
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }
            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }

}
