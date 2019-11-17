

import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class FakeWorldProvider extends WorldProvider {

	@Override
	public String getDimensionName() {
		return "";
	}

	@Override
	public String getInternalNameSuffix() {
		return "";
	}
	
	@Override
	public BlockPos getSpawnCoordinate() {
		return new BlockPos(0, 64, 0);
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new FakeChunkProvider();
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		return true;
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return true;
	}
	
	@Override
	public int getAverageGroundLevel() {
		return 63;
	}
	
	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}
	
	@Override
	public boolean isSkyColored() {
		return false;
	}
	
	@Override
	public boolean doesWaterVaporize() {
		return false;
	}

}
