package hd.elysium.world;

import hd.elysium.Elysium;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class ElysiumWorldProvider extends WorldProvider
{
	@Override
	public void registerWorldChunkManager()
	{
		this.dimensionId = Elysium.dimensionId;
		this.worldChunkMgr = new ElysiumChunkManager(this.worldObj);
	}
	
	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ElysiumChunkProvider(this.worldObj, this.worldObj.getSeed(), true);
	}
	
	@Override
	public String getDimensionName()
	{
		return Elysium.NAME;
	}
}
