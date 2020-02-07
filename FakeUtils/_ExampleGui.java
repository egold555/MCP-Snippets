package clientname.gui.test;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import clientname.gui.test.fakeplayer.FakePlayer;
import clientname.gui.test.fakeplayer.FakeWorld;
import clientname.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class ExampleGui extends GuiScreen {

	private World world;
	private FakePlayer player;
	private EntitySheep sheep;
	private EntityRabbit rabbit;

	@Override
	public void initGui() {

		world = new FakeWorld(new WorldInfo(new NBTTagCompound()));

		player = new FakePlayer(mc, world, new GameProfile(UUID.randomUUID(), "FakePlayer"));
		player.setAllSkinLayersEnabled();
		player.setArrowCountInEntity(4);

		sheep = new EntitySheep(world);
		sheep.setFleeceColor(EnumDyeColor.YELLOW);
		sheep.setFire(1);

		rabbit = new EntityRabbit(world);
		rabbit.setRabbitType(3);

		mc.getRenderManager().cacheActiveRenderInfo(world, mc.fontRendererObj, player, player, mc.gameSettings, 0.0F);

	}

	int rotate = 0;

	@Override
	public void drawScreen(int mouseX2, int mouseY2, float partialTicks) {

		drawBackground(0);

		mc.thePlayer = player;
		world.updateEntity(player);



		if (mc.getRenderManager().worldObj == null || mc.getRenderManager().playerRenderer == null) {
			mc.getRenderManager().cacheActiveRenderInfo(world, mc.fontRendererObj, player, player, mc.gameSettings, 0.0F);
		}
		if ((world != null) && (player != null)) {
			mc.thePlayer = player;
			ScaledResolution sr = new ScaledResolution(mc);

			int distanceToSide = ((mc.currentScreen.width / 2)) / 2;
			float targetHeight = (float) (sr.getScaledHeight_double() / 5.0F) / 1.8F;

			drawEntityOnScreen(
					sr.getScaledWidth() - distanceToSide - 200,
					(int) ((sr.getScaledHeight() / 2) + (player.height * targetHeight)),
					targetHeight,
					rotate,
					180,
					player);

			drawEntityOnScreen(
					sr.getScaledWidth() - distanceToSide - 300,
					(int) ((sr.getScaledHeight() / 2) + (player.height * targetHeight)),
					targetHeight,
					0,
					rotate,
					sheep);

			drawEntityOnScreen(
					sr.getScaledWidth() - distanceToSide - 10,
					(int) ((sr.getScaledHeight() / 2) + (player.height * targetHeight)),
					rotate,
					rotate,
					180,
					rabbit);


			rotate+=5;
			if(rotate<= -360 || rotate >= 360) {
				rotate = 0;
			}

		}

		super.drawScreen(mouseX2, mouseY2, partialTicks);

		//get rid of the player after were done rendering it. We don't want to confuse the singleplayer gods
		mc.thePlayer = null;
	}

	public static void drawEntityOnScreen(int posX, int posY, float scale, float yawRotate, float pitchRotate, EntityLivingBase ent) {
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.translate(posX, posY, 50.0F);
		GlStateManager.scale(-scale, scale, scale);
		GlStateManager.rotate(pitchRotate, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(yawRotate, 0.0F, 1.0F, 0.0F);
		float f2 = ent.renderYawOffset;
		float f3 = ent.rotationYaw;
		float f4 = ent.rotationPitch;
		float f5 = ent.prevRotationYawHead;
		float f6 = ent.rotationYawHead;
		RenderHelper.enableStandardItemLighting();
		ent.renderYawOffset = (float) Math.atan(yawRotate / 40.0F);
		ent.rotationYaw = (float) Math.atan(yawRotate / 40.0F);
		ent.rotationPitch = -((float) Math.atan(0 / 40.0F)) * 20.0F;
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		try {
			RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
			rendermanager.setPlayerViewY(180.0F);
			rendermanager.setRenderShadow(false);
			rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, true);
			rendermanager.setRenderShadow(true);
		} 
		finally {
			ent.renderYawOffset = f2;
			ent.rotationYaw = f3;
			ent.rotationPitch = f4;
			ent.prevRotationYawHead = f5;
			ent.rotationYawHead = f6;
			GlStateManager.popMatrix();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();
			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.disableTexture2D();
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.translate(0.0F, 0.0F, 20.0F);
		}
	}

}
