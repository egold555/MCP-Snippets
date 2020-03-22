import com.mojang.authlib.GameProfile;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings.GameType;

public class FakeNetworkPlayerInfo extends NetworkPlayerInfo {

	public FakeNetworkPlayerInfo(GameProfile gp) {
		super(gp);
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getGameProfile().getName());
	}
	
	@Override
	public GameType getGameType() {
		return GameType.CREATIVE;
	}
	
	@Override
	public int getResponseTime() {
		return 0;
	}
	
	//slim, default
	@Override
	public String getSkinType() {
		return "default";
	}
	
	@Override
	public ScorePlayerTeam getPlayerTeam() {
		return null;
	}

}
