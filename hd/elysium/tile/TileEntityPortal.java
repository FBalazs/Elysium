package hd.elysium.tile;

import hd.elysium.Elysium;
import hd.elysium.world.ElysiumTeleporter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPortal extends TileEntity
{
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
	}
	
	@Override
	public void updateEntity()
	{
		if(this.player != null)
		{
			if((int)Math.floor(this.player.posX) == this.xCoord
				&& (int)Math.floor(this.player.posX) == this.xCoord
				&& (int)Math.floor(this.player.posX) == this.xCoord)
			{
				if(this.ticksBeforeTp > 0)
					this.ticksBeforeTp--;
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
