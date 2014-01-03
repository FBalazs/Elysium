package hd.elysium.item;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hd.elysium.Elysium;
import net.minecraft.item.Item;

public class ElysianItem extends Item
{
	public ElysianItem(int id, String unlocalizedName)
	{
		super(id);
		this.setUnlocalizedName(Elysium.ID+":"+unlocalizedName);
	}
	
	@Override
	public Item setTextureName(String texture)
	{
		this.iconString = Elysium.ID+":"+texture;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected String getIconString()
	{
		return this.iconString == null ? this.getUnlocalizedName().substring(5) : this.iconString;
	}
	
	public void addNameTranslation(String lang, String str)
	{
		LanguageRegistry.instance().addStringLocalization(this.getUnlocalizedName()+".name", lang, str);
	}
}
