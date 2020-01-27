package <your package here>;

import org.lwjgl.opengl.GL11;

//change these to your client packages...
import clientname.module.ModuleDraggable;
import clientname.render.draghud.util.ScreenPosition;
import clientname.utils.UrlTextureUtil;
import clientname.utils.UrlTextureUtil.ResourceLocationCallback;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

public class ModUrlImageTest extends ModDraggable {

	private ResourceLocation img = null;
	private boolean hasTriedToDownload = false;
	
	@Override
	public int getHeight() {
		return 100;
	}

	@Override
	public int getWidth() {
		return 100;
	}

	@Override
	public void render(ScreenPosition pos) {
		
		if(img == null && !hasTriedToDownload) {
			hasTriedToDownload = true;
			UrlTextureUtil.downloadAndSetTexture("https://flif.info/example-images/fish.png", new ResourceLocationCallback() {
				
				@Override
				public void onTextureLoaded(ResourceLocation rl) {
					img = rl;
				}
			});
		}
		
		if(img != null) {
			GL11.glPushMatrix();
			mc.getTextureManager().bindTexture(img);
			Gui.drawModalRectWithCustomSizedTexture(pos.getAbsoluteX(), pos.getAbsoluteY(), 1, 1, 100, 100, 100, 100);
			GL11.glPopMatrix();
		}
		
	}

}
