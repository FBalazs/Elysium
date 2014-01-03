package hd.elysium.item;

import hd.elysium.Elysium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDebug extends ElysianItem
{
	public ItemDebug(int defaultId, String unlocalizedName)
	{
		super(defaultId, unlocalizedName);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(world.isRemote)
		{
			player.addChatMessage("Dimension data");
			player.addChatMessage(" Id: "+world.provider.dimensionId);
			player.addChatMessage(" Name: "+world.provider.getDimensionName());
		}
		return stack;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		
		return false;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(world.isRemote)
		{
			player.addChatMessage("Block data");
			player.addChatMessage(" Id: "+world.getBlockId(x, y, z));
			player.addChatMessage(" Metadata: "+world.getBlockMetadata(x, y, z));
		}
        return true;
    }
}
