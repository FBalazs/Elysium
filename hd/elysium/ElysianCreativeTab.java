package hd.elysium;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ElysianCreativeTab extends CreativeTabs
{
	private ItemStack stack;
	
	public ElysianCreativeTab(String label)
	{
		super(label);
	}
	
	public void addNameTranslation(String lang, String str)
	{
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			LanguageRegistry.instance().addStringLocalization(this.getTranslatedTabLabel(), lang, str);
	}
	
	public void setItemStack(ItemStack stack)
	{
		this.stack = stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
	{
		return this.stack;
	}
}
