package hd.elysium.block;

import java.util.Random;

import hd.elysium.Elysium;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;

public class ElysianBlock extends Block
{
	protected int idDropped;
	
	public ElysianBlock(int id, String unlocalizedName, Material material)
	{
		super(id, material);
		this.setUnlocalizedName(Elysium.ID+":"+unlocalizedName);
		this.setIdDropped(this.blockID);
	}
	
	public void setIdDropped(int id)
	{
		this.idDropped = id;
	}
	
	@Override
	public Block setTextureName(String texture)
	{
		this.textureName = Elysium.ID+":"+texture;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected String getTextureName()
	{
		return this.textureName == null ? this.getUnlocalizedName().substring(5) : this.textureName;
	}
	
	public void setHarvestLevel(String tool, int level)
	{
		MinecraftForge.setBlockHarvestLevel(this, tool, level);
	}
	
	public void setHarvestLevel(int meta, String tool, int level)
	{
		MinecraftForge.setBlockHarvestLevel(this, meta, tool, level);
	}
	
	@Override
	public int idDropped(int par1, Random rand, int par3)
	{
		return this.idDropped;
	}
	
	public void addNameTranslation(String lang, String str)
	{
		LanguageRegistry.instance().addStringLocalization(this.getUnlocalizedName()+".name", lang, str);
	}
	
	public void register()
	{
		GameRegistry.registerBlock(this, this.getUnlocalizedName());
		System.out.println("[ELYSIUM] Registered "+this.getUnlocalizedName()+" with id "+this.blockID);
	}
}
