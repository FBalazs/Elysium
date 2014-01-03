package hd.elysium.world.biome;

import hd.elysium.Elysium;
import net.minecraft.world.biome.BiomeGenBase;

public class ElysianBiome extends BiomeGenBase
{
	public ElysianBiome(int id, String name, float minHeight, float maxHeight)
	{
		super(id, true);
		this.setBiomeName(name);
		this.setMinMaxHeight(minHeight, maxHeight);
		this.topBlock = (byte)Elysium.blockGrass.blockID;
		this.fillerBlock = (byte)Elysium.blockDirt.blockID;
		this.temperature = 0.7F;
		this.rainfall = 0.05F;
		this.waterColorMultiplier = 0*65536 + 255*256 + 255;
		
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}
