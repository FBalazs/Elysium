package hd.elysium.tile;

import hd.elysium.Elysium;
import hd.elysium.world.ElysiumTeleporter;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPortal extends TileEntity
{
	public static int ticksBetweenChecks;
	
	public int ticksSinceLastCheck;
	public EntityPlayerMP player;
	public int ticksBeforeTp;
	
	public int deg, mdeg;
	public float tex, mtex;
	public float rad, mrad;
	
	public TileEntityPortal()
	{
		this.player = null;
		this.ticksBeforeTp = 20;
		this.deg = 0;
		this.mdeg = 5;
		this.tex = 0F;
		this.mtex = 0.05F;
		this.rad = 0.75F;
		this.mrad = 0.025F;
		this.ticksSinceLastCheck = ticksBetweenChecks;
	}
	
	public boolean canStay()
	{
		for(int i = this.xCoord-2; i <= this.xCoord+2; i++)
			for(int k = this.zCoord-2; k <= this.zCoord+2; k++)
				if(this.worldObj.getBlockId(i, this.yCoord-2, k) != Block.blockNetherQuartz.blockID
					|| this.worldObj.getBlockMetadata(i, this.yCoord-2, k) != 0
					|| this.worldObj.getBlockId(i, this.yCoord-8, k) != Block.blockNetherQuartz.blockID
					|| this.worldObj.getBlockMetadata(i, this.yCoord-8, k) != 0)
					return false;
		for(int i = this.xCoord-1; i <= this.xCoord+1; i++)
			for(int k = this.zCoord-1; k <= this.zCoord+1; k++)
				if(this.worldObj.getBlockId(i, this.yCoord-1, k) != Block.blockNetherQuartz.blockID
					|| this.worldObj.getBlockMetadata(i, this.yCoord-1, k) != 1
					|| this.worldObj.getBlockId(i, this.yCoord-3, k) != Block.blockNetherQuartz.blockID
					|| this.worldObj.getBlockMetadata(i, this.yCoord-3, k) != 2
					|| this.worldObj.getBlockId(i, this.yCoord-4, k) != Block.blockNetherQuartz.blockID
					|| this.worldObj.getBlockMetadata(i, this.yCoord-4, k) != 2
					|| this.worldObj.getBlockId(i, this.yCoord-5, k) != Block.blockGold.blockID
					|| this.worldObj.getBlockId(i, this.yCoord-6, k) != Block.blockNetherQuartz.blockID
					|| this.worldObj.getBlockMetadata(i, this.yCoord-6, k) != 2
					|| this.worldObj.getBlockId(i, this.yCoord-7, k) != Block.blockNetherQuartz.blockID
					|| this.worldObj.getBlockMetadata(i, this.yCoord-7, k) != 2)
					return false;
		return true;
	}
	
	@Override
	public void updateEntity()
	{
		if(!this.worldObj.isRemote)
		{
			this.ticksSinceLastCheck++;
			if(this.ticksSinceLastCheck >= ticksBetweenChecks)
			{
				if(!this.canStay())
				{
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Block.dragonEgg.blockID);
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord+0.5D, this.yCoord+0.5D, this.zCoord+0.5D, new ItemStack(Elysium.itemCristal)));
					this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 2F, true);
				}
				this.ticksSinceLastCheck = 0;
			}
			
			if(this.player != null)
			{
				if((int)Math.floor(this.player.posX) == this.xCoord
					&& (int)Math.floor(this.player.posY) == this.yCoord
					&& (int)Math.floor(this.player.posZ) == this.zCoord)
				{
					if(this.ticksBeforeTp > 0)
					{
						//System.out.println(this.ticksSinceLastCheck+" "+this.ticksBeforeTp);
						this.ticksBeforeTp--;
					}
					else
					{
						if(this.player.dimension == Elysium.dimensionId)
							this.player.mcServer.getConfigurationManager().transferPlayerToDimension(this.player, 0, new ElysiumTeleporter(this.player.mcServer.worldServerForDimension(0)));
						else
							this.player.mcServer.getConfigurationManager().transferPlayerToDimension(this.player, Elysium.dimensionId, new ElysiumTeleporter(this.player.mcServer.worldServerForDimension(Elysium.dimensionId)));
						this.ticksBeforeTp = 20;
						this.player = null;
					}
				}
				else
				{
					this.player = null;
					this.ticksBeforeTp = 20;
				}
			}
		}
		else
		{
			this.deg += this.mdeg;
			if(this.deg >= 360)
				this.deg -= 360;
			
			this.tex += this.mtex;
			if(this.tex >= 1F)
				this.tex -= 1F;
			
			this.rad += this.mrad;
			if(this.rad >= 1.25F || this.rad <= 0.75F)
				this.mrad *= -1F;
		}
	}
}
