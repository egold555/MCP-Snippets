
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;

public class FakeNetworkManager extends NetworkManager {

	//Not sure if I need to override anything in here. It seems to work fine without overriding packets being sent
	
	public FakeNetworkManager(EnumPacketDirection packetDirection) {
		super(packetDirection);
	}

}
