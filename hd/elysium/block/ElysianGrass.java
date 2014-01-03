package hd.elysium.block;

import hd.elysium.Elysium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class ElysianGrass extends ElysianBlock
{
	@SideOnly(Side.CLIENT)
	private Icon iconTop;
	
	public ElysianGrass(int id, String unlocalizedName, Material material)
	{
		super(id, unlocalizedName, material);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		switch(side)
		{
			case 0:
				return Elysium.blockDirt.getIcon(side, meta);
			case 1:
				return this.iconTop;
			default:
				return this.blockIcon;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(this.getTextureName()+"_side");
		this.iconTop = iconRegister.registerIcon(this.getTextureName()+"_top");
	}
}
