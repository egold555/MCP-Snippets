
import clientname.gui.cosmeticview.fakeplayer.FakePlayer;
import clientname.gui.cosmeticview.fakeplayer.FakeWorld;
import clientname.utils.EntityUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
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

		player = new FakePlayer(mc, world);
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
			
			EntityUtils.drawEntityOnScreen(
					sr.getScaledWidth() - distanceToSide - 200,
					(int) ((sr.getScaledHeight() / 2) + (player.height * targetHeight)),
					targetHeight,
					rotate,
					180,
					player);
			
			EntityUtils.drawEntityOnScreen(
					sr.getScaledWidth() - distanceToSide - 300,
					(int) ((sr.getScaledHeight() / 2) + (player.height * targetHeight)),
					targetHeight,
					0,
					rotate,
					sheep);
			
			EntityUtils.drawEntityOnScreen(
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

}
