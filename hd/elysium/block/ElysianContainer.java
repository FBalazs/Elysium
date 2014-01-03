package hd.elysium.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class ElysianContainer extends ElysianBlock implements ITileEntityProvider
{
	public ElysianContainer(int defaultId, String unlocalizedName, Material material)
	{
		super(defaultId, unlocalizedName, material);
		this.isBlockContainer = true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta)
	{
		super.breakBlock(world, x, y, z, id, meta);
		world.removeBlockTileEntity(x, y, z);
	}
	
	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int blockId, int eventId)
	{
		super.onBlockEventReceived(world, x, y, z, blockId, eventId);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		return tile != null ? tile.receiveClientEvent(blockId, eventId) : false;
	}
}
