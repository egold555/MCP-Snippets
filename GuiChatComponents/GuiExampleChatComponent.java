package clientname.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GuiExampleChatComponent extends GuiScreen {

	private IChatComponent[] message;
	private int messageLengthTimesFontHeight;

	private GuiButton refreshButton;

	public GuiExampleChatComponent()
	{

		message = new IChatComponent[] {
				new ChatComponentText("Hi,"),
				new ChatComponentText(""),
				
				new ChatComponentText("Cover over the text in [ ] to see what they do!"),
				new ChatComponentText(""),
				
				new ChatComponentText(EnumChatFormatting.GOLD + "Hover:"),
				new ChatComponentText(""),
				
				//Acts like your hovering a item
				new ChatComponentText(EnumChatFormatting.GOLD + "[Item]").setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText("{id:stone,Count:1}")))),
				new ChatComponentText(""),
				
				//Shows generic text
				new ChatComponentText(EnumChatFormatting.GOLD + "[Text]").setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Hello World, I am text")))),
				new ChatComponentText(""),
				
				//Shows a entity
				new ChatComponentText(EnumChatFormatting.GOLD + "[Entity]").setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText("{id:f84c6a79-0a4e-45e0-879b-cd49ebd4c4e2,name:Herobrine}")))),
				new ChatComponentText(""),
				
				//Shows a achievement
				new ChatComponentText(EnumChatFormatting.GOLD + "[Achievement]").setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText("stat.jump")))),
				new ChatComponentText(""),
				
				new ChatComponentText(EnumChatFormatting.BLUE + "Click:"),
				new ChatComponentText(""),
				
				//Opens a url to my website
				new ChatComponentText(EnumChatFormatting.BLUE + "[Open Url]").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://eric.golde.org/"))),
				new ChatComponentText(""),
				
				//Opens a file, in this case, a txt document that comes with windows
				new ChatComponentText(EnumChatFormatting.BLUE + "[Open File]").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, "C:\\Windows\\System32\\WindowsCodecsRaw.txt"))),
				new ChatComponentText(""),
				
				//Send a chat message into the non existant gui chat window. 
				new ChatComponentText(EnumChatFormatting.BLUE + "[Crash]").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/say hi"))),
				
				new ChatComponentText(""),
				//Send a chat message into the non existant gui chat window. 
				new ChatComponentText(EnumChatFormatting.BLUE + "[Nothing]").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/say hi"))),

				new ChatComponentText(""),
				//Send a chat message into the non existant gui chat window. 
				new ChatComponentText(EnumChatFormatting.BLUE + "[Console Error 1]").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "1"))),
				
				new ChatComponentText(""),
				//Send a chat message into the non existant gui chat window. 
				new ChatComponentText(EnumChatFormatting.BLUE + "[Console Error 2]").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.TWITCH_USER_INFO, "loltyler1"))),
		};

	}

	@Override
	public void initGui()
	{
		this.buttonList.clear();

		this.messageLengthTimesFontHeight = this.message.length * this.fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
	
		int i = this.height / 2 - this.messageLengthTimesFontHeight / 2;

		//draw the chat components
		for (IChatComponent s : this.message)
		{
			this.drawCenteredString(this.fontRendererObj, s.getFormattedText(), this.width / 2, i, 16777215);
			i += this.fontRendererObj.FONT_HEIGHT;

			
		}

		handleComponentHover(findChatComponent(mouseX, mouseY), mouseX, mouseY);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	
	private IChatComponent findChatComponentLine(int mouseY)
	{
		int i = this.height / 2 - this.messageLengthTimesFontHeight / 2;

		for (IChatComponent s : this.message)
		{
			int yTop = i;
			int yBottom = i + this.fontRendererObj.FONT_HEIGHT;
			if (mouseY >= yTop && mouseY < yBottom) {
				return s;
			}
			i += this.fontRendererObj.FONT_HEIGHT;
		}
		
		return null;
	}
	
	private IChatComponent findChatComponent(int mouseX, int mouseY) {
		
		IChatComponent s = findChatComponentLine(mouseY);
		
		if (s == null || !(s instanceof ChatComponentText)) {
			return null;
		}
		
		int stringWidth = this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)s).getChatComponentText_TextValue(), false));
		int xLeft = this.width / 2 - stringWidth / 2;
		int xRight = this.width / 2 + stringWidth / 2;
		if (mouseX >= xLeft && mouseX < xRight) {
			return s;
		}
		
		return null;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0)
		{
			IChatComponent ichatcomponent = findChatComponent(mouseX, mouseY);

			if (this.handleComponentClick(ichatcomponent))
			{
				return;
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

}