package hd.elysium.world;

import hd.elysium.Elysium;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class ElysiumTeleporter extends Teleporter
{
	private final WorldServer worldServer;
	
	public ElysiumTeleporter(WorldServer worldServer)
	{
		super(worldServer);
		this.worldServer = worldServer;
	}
	
	public int getPortalHeight(int x, int z)
	{
		for(int y = 0; y < 256; y++)
			if(this.worldServer.getBlockId(x, y, z) == Elysium.blockPortal.blockID)
				return y;
		return -1;
	}
	
	public boolean canPlacePortalAt(int x, int y, int z)
	{
		/*int p = 0;
		for(int i = x-2; i <= x+2; i++)
			for(int k = z-2; k <= z+2; k++)
			{
				Block block = Block.blocksList[this.worldServer.getBlockId(i, y-1, k)];
				if(block != null && !block.canBeReplacedByLeaves(this.worldServer, i, y-1, k))
					p++;
			}
		if(p/25F < 0.5F)
			return false;*/
		
		for(int i = x-2; i <= x+2; i++)
			for(int j = y; j <= y+10; j++)
				for(int k = z-2; k <= z+2; k++)
				{
					Block block = Block.blocksList[this.worldServer.getBlockId(i, j, k)];
					if(block != null && !block.canBeReplacedByLeaves(this.worldServer, i, j, k))
						return false;
				}
		return true;
	}
	
	public void placePortalAt(int x, int y, int z)
	{
		for(int i = x-2; i <= x+2; i++)
			for(int j = y-5; j <= y-1; j++)
				for(int k = z-2; k <= z+2; k++)
				{
					Block block = Block.blocksList[this.worldServer.getBlockId(i, j, k)];
					if(block == null || block.canBeReplacedByLeaves(this.worldServer, i, j, k))
						this.worldServer.setBlock(i, j, k, (int)this.worldServer.getBiomeGenForCoords(i, k).fillerBlock & 256);
				}
		
		for(int i = x-2; i <= x+2; i++)
			for(int k = z-2; k <= z+2; k++)
			{
				this.worldServer.setBlock(i, y, k, Block.blockNetherQuartz.blockID);
				this.worldServer.setBlock(i, y+6, k, Block.blockNetherQuartz.blockID);
			}
		for(int i = x-1; i <= x+1; i++)
			for(int k = z-1; k <= z+1; k++)
			{
				this.worldServer.setBlock(i, y+1, k, Block.blockNetherQuartz.blockID);
				this.worldServer.setBlockMetadataWithNotify(i, y+1, k, 2, 0);
				this.worldServer.setBlock(i, y+2, k, Block.blockNetherQuartz.blockID);
				this.worldServer.setBlockMetadataWithNotify(i, y+2, k, 2, 0);
				this.worldServer.setBlock(i, y+3, k, Block.blockGold.blockID);
				this.worldServer.setBlock(i, y+4, k, Block.blockNetherQuartz.blockID);
				this.worldServer.setBlockMetadataWithNotify(i, y+4, k, 2, 0);
				this.worldServer.setBlock(i, y+5, k, Block.blockNetherQuartz.blockID);
				this.worldServer.setBlockMetadataWithNotify(i, y+5, k, 2, 0);
				this.worldServer.setBlock(i, y+7, k, Block.blockNetherQuartz.blockID);
				this.worldServer.setBlockMetadataWithNotify(i, y+7, k, 1, 0);
			}
		
		this.worldServer.setBlock(x, y+8, z, Elysium.blockPortal.blockID);
	}
	
	@Override
	public void placeInPortal(Entity entity, double unused1, double unused2, double unused3, float unused4)
	{
		int x = (int)Math.floor(entity.posX);
		int z = (int)Math.floor(entity.posZ);
		int y = this.getPortalHeight(x, z);
		if(y == -1)
		{
			for(y = 32; y < 128 && !this.canPlacePortalAt(x, y, z); y++);
			this.placePortalAt(x, y, z);
		}
		double d = this.worldServer.rand.nextDouble()*Math.PI*2;
		entity.posX = x+Math.cos(d)*1.5;
		entity.posZ = z-Math.sin(d)*1.5;
		entity.posY = y+8;
	}
}
