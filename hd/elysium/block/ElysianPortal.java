package hd.elysium.block;

import hd.elysium.Elysium;
import hd.elysium.tile.TileEntityPortal;
import hd.elysium.world.ElysiumTeleporter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ElysianPortal extends ElysianContainer
{
	public static int renderId;
	
	public ElysianPortal(int defaultId, String unlocalizedName, Material material)
	{
		super(defaultId, unlocalizedName, material);
		this.setBlockBounds(0.5F, 0F, 0.5F, 0.5F, 100F, 0.5F);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		// NOTE: the first statement also checks for server side (EntityPlayerMP is the server side version of EntityPlayer)
		if(entity instanceof EntityPlayerMP && entity.riddenByEntity == null && entity.ridingEntity == null)
		{
			TileEntityPortal tile = (TileEntityPortal)world.getBlockTileEntity(x, y, z);
			if(tile != null && tile.player == null)
				tile.player = (EntityPlayerMP)entity;
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return renderId;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityPortal();
	}
}
