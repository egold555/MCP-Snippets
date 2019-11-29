
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class FakePlayer extends EntityPlayerSP {

	public FakePlayer(Minecraft mc, World world) {
		this(mc, world, mc.getSession().getProfile());
	}
	
	public FakePlayer(Minecraft mc, World world, GameProfile gp) {
		super(mc, world, new NetHandlerPlayClient(mc, mc.currentScreen, new FakeNetworkManager(EnumPacketDirection.CLIENTBOUND), gp) {
			@Override
			public NetworkPlayerInfo getPlayerInfo(String p_175104_1_) {
				return new NetworkPlayerInfo(gp);
			}
			@Override
			public NetworkPlayerInfo getPlayerInfo(UUID p_175102_1_) {
				return new NetworkPlayerInfo(gp);
			}
		}, null);

		this.dimension = 0;
		this.movementInput = new MovementInput();
	}
	
	public void setAllSkinLayersEnabled() {
		for (EnumPlayerModelParts part : EnumPlayerModelParts.values()) {
			this.mc.gameSettings.switchModelPartEnabled(part);
		}
	}
	
	@Override
	public float getEyeHeight() {
		return 1.82F;
	}
}
