

import java.util.List;

import com.google.common.base.Predicate;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class FakeWorld extends World {

	public FakeWorld(WorldInfo info) {
		super(new FakeSaveHandler(), info, new FakeWorldProvider(), new Profiler(), true);
		this.provider.registerWorld(this);
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		return new FakeChunkProvider();
	}

	@Override
	protected int getRenderDistanceChunks() {
		return 0;
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
		return BiomeGenBase.plains;
	}

	@Override
	protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
		return false;
	}

	@Override
	public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
		return new BlockPos(pos.getX(), 63, pos.getZ());
	}

	@Override
	public boolean isAirBlock(BlockPos pos) {
		return pos.getY() > 63;
	}

	@Override
	public IBlockState getBlockState(BlockPos pos) {
		return pos.getY() > 63 ? Blocks.air.getDefaultState() : Blocks.grass.getDefaultState();
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
		return true;
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public boolean setBlockToAir(BlockPos pos) {
		return true;
	}

	@Override
	public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {

	}

	@Override
	public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType) {

	}

	@Override
	public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
		return this.isAirBlock(pos);
	}

	@Override
	public void notifyBlockOfStateChange(BlockPos pos, Block blockIn) {

	}

	@Override
	public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide) {

	}

	@Override
	public void markBlockForUpdate(BlockPos pos) {

	}

	@Override
	public void notifyLightSet(BlockPos pos) {

	}

	@Override
	public void notifyNeighborsRespectDebug(BlockPos pos, Block blockType) {

	}

	@Override
	public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax) {

	}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {

	}

	@Override
	public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2) {

	}

	@Override
	public void markTileEntityForRemoval(TileEntity tileEntityIn) {

	}

	@Override
	public boolean isBlockTickPending(BlockPos pos, Block blockType) {
		return false;
	}

	@Override
	public int getLight(BlockPos pos) {
		return 14;
	}

	@Override
	public int getLight(BlockPos pos, boolean checkNeighbors) {
		return 14;
	}

	@Override
	public int getLightFor(EnumSkyBlock type, BlockPos pos) {
		return 14;
	}

	@Override
	public int getLightFromNeighbors(BlockPos pos) {
		return 14;
	}

	@Override
	public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
		return 14;
	}

	@Override
	public boolean canBlockSeeSky(BlockPos pos) {
		return pos.getY() > 62;
	}

	@Override
	public BlockPos getHeight(BlockPos pos) {
		return new BlockPos(pos.getX(), 63, pos.getZ());
	}

	@Override
	public int getChunksLowestHorizon(int x, int z) {
		return 63;
	}

	@Override
	protected void updateBlocks() {

	}

	@Override
	public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue) {

	}

	@Override
	public float getLightBrightness(BlockPos pos) {
		return 1f;
	}

	@Override
	public float getSunBrightness(float p_72971_1_) {
		return 1f;
	}

	@Override
	public boolean isDaytime() {
		return true;
	}

	@Override
	public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {

	}

	@Override
	public void playSoundAtEntity(Entity entityIn, String name, float volume, float pitch) {

	}

	@Override
	public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch) {

	}

	@Override
	public void playSoundToNearExcept(EntityPlayer player, String name, float volume, float pitch) {

	}

	@Override
	public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175682_15_) {

	}

	@Override
	public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175688_14_) {

	}

	@Override
	public void playRecord(BlockPos pos, String name) {

	}

	@Override
	public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_) {
		return null;
	}

	@Override
	public MovingObjectPosition rayTraceBlocks(Vec3 start, Vec3 end, boolean stopOnLiquid) {
		return null;
	}

	@Override
	public MovingObjectPosition rayTraceBlocks(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
		return null;
	}

	@Override
	public boolean addWeatherEffect(Entity entityIn) {
		return false;
	}

	@Override
	public boolean spawnEntityInWorld(Entity entityIn) {
		return false;
	}

	@Override
	protected void onEntityAdded(Entity entityIn) {

	}

	@Override
	protected void onEntityRemoved(Entity entityIn) {

	}

	@Override
	public void removeEntity(Entity entityIn) {

	}

	@Override
	public void removePlayerEntityDangerously(Entity entityIn) {

	}

	@Override
	public int calculateSkylightSubtracted(float p_72967_1_) {
		return 6;
	}

	@Override
	public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {

	}

	@Override
	public void updateEntities() {

	}

	@Override
	public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
		if (forceUpdate) {
			++entityIn.ticksExisted;
		}
	}

	@Override
	public boolean checkNoEntityCollision(AxisAlignedBB bb) {
		return true;
	}

	@Override
	public boolean checkNoEntityCollision(AxisAlignedBB bb, Entity entityIn) {
		return true;
	}

	@Override
	public boolean checkBlockCollision(AxisAlignedBB bb) {
		return false;
	}

	@Override
	public boolean isAnyLiquid(AxisAlignedBB bb) {
		return false;
	}

	@Override
	public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity) {
		return false;
	}

	@Override
	public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
		return false;
	}

	@Override
	public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
		return false;
	}

	@Override
	public TileEntity getTileEntity(BlockPos pos) {
		return null;
	}

	@Override
	public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public String getDebugLoadedEntities() {
		return "";
	}

	@Override
	public String getProviderName() {
		return "";
	}

	@Override
	public void setTileEntity(BlockPos pos, TileEntity tileEntityIn) {

	}

	@Override
	public void removeTileEntity(BlockPos pos) {

	}

	@Override
	public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
		return true;
	}

	@Override
	public void tick() {

	}

	@Override
	protected void updateWeather() {

	}

	@Override
	public boolean canBlockFreezeWater(BlockPos pos) {
		return false;
	}

	@Override
	public boolean canBlockFreezeNoWater(BlockPos pos) {
		return false;
	}

	@Override
	public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj) {
		return false;
	}

	@Override
	public boolean canSnowAt(BlockPos pos, boolean checkLight) {
		return false;
	}

	@Override
	public boolean tickUpdates(boolean par1) {
		return false;
	}

	@Override
	public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
		return null;
	}

	@Override
	public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity) {
		return null;
	}

	@Override
	public void loadEntities(Collection<Entity> entityCollection) {

	}

	@Override
	public void unloadEntities(Collection<Entity> entityCollection) {

	}

	@Override
	public int countEntities(Class par1Class) {
		return 0;
	}

	@Override
	public int getStrongPower(BlockPos pos) {
		return 0;
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction) {
		return 0;
	}

	@Override
	public boolean isSidePowered(BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public int getRedstonePower(BlockPos pos, EnumFacing facing) {
		return 0;
	}

	@Override
	public boolean isBlockPowered(BlockPos pos) {
		return false;
	}

	@Override
	public int isBlockIndirectlyGettingPowered(BlockPos pos) {
		return 0;
	}

	@Override
	public void checkSessionLock() throws MinecraftException {
	}

	@Override
	public long getSeed() {
		return 1;
	}

	@Override
	public long getTotalWorldTime() {
		return 1;
	}

	@Override
	public long getWorldTime() {
		return 1;
	}

	@Override
	public void setWorldTime(long par1) {
	}

	@Override
	public BlockPos getSpawnPoint() {
		return new BlockPos(0, 64, 0);
	}

	@Override
	public void joinEntityInSurroundings(Entity par1Entity) {
	}

	@Override
	public boolean canSeeSky(BlockPos pos) {
		return pos.getY() > 62;
	}

	@Override
	public void setEntityState(Entity par1Entity, byte par2) {
	}

	@Override
	public float getThunderStrength(float delta) {
		return 0.0F;
	}

	@Override
	public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
	}

	@Override
	public void updateAllPlayersSleepingFlag() {
	}

	@Override
	public void setThunderStrength(float p_147442_1_) {
	}

	@Override
	public float getRainStrength(float par1) {
		return 0.0F;
	}

	@Override
	public void setRainStrength(float par1) {
	}

	@Override
	public boolean isThundering() {
		return false;
	}

	@Override
	public boolean isRaining() {
		return false;
	}

	@Override
	public boolean isBlockinHighHumidity(BlockPos pos) {
		return false;
	}

	@Override
	public void setItemData(String par1Str, WorldSavedData par2WorldSavedData) {
	}

	@Override
	public void playBroadcastSound(int p_175669_1_, BlockPos pos, int p_175669_3_) {
	}

	@Override
	public void playAuxSFX(int p_175718_1_, BlockPos pos, int p_175718_3_) {

	}

	@Override
	public void playAuxSFXAtEntity(EntityPlayer player, int sfxType, BlockPos pos, int p_180498_4_) {

	}

	@Override
	public int getHeight() {
		return 256;
	}

	@Override
	public int getActualHeight() {
		return 256;
	}

	@Override
	public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13nbtTagCompound) {
	}

	@Override
	public boolean addTileEntity(TileEntity tile) {
		return true;
	}

	@Override
	public void addTileEntities(Collection<TileEntity> tileEntityCollection) {
	}

	@Override
	public boolean isBlockFullCube(BlockPos pos) {
		return pos.getY() <= 63;
	}

	@Override
	public boolean isInsideBorder(WorldBorder worldBorderIn, Entity entityIn) {
		return true;
	}

	@Override
	public <T extends Entity> List<T> getEntities(Class<? extends T> entityType, Predicate<? super T> filter) {
		return new ArrayList<T>();
	}
	
	@Override
	public List<Entity> getEntitiesInAABBexcluding(Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate) {
		return new ArrayList<Entity>();
	}
	
	@Override
	public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> classEntity, AxisAlignedBB bb) {
		return new ArrayList<T>();
	}
	
	@Override
	public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, Predicate<? super T> filter) {
		return new ArrayList<T>();
	}
	
	@Override
	public List<Entity> getEntitiesWithinAABBExcludingEntity(Entity entityIn, AxisAlignedBB bb) {
		return new ArrayList<Entity>();
	}

	@Override
	public IChunkProvider getChunkProvider() {
		return new FakeChunkProvider();
	}

	@Override
	public Chunk getChunkFromChunkCoords(int par1, int par2) {
		return null;
	}

}
