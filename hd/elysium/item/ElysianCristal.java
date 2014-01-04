package hd.elysium.item;

import hd.elysium.Elysium;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ElysianCristal extends ElysianItem
{
	public ElysianCristal(int id, String unlocalizedName)
	{
		super(id, unlocalizedName);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(!world.isRemote && world.getBlockId(x, y, z) == Block.dragonEgg.blockID)
		{
			world.createExplosion(player, x+0.5D, y+0.5D, z+0.5D, 2F, true);
			world.setBlock(x, y, z, Elysium.blockPortal.blockID);
			if(!player.capabilities.isCreativeMode)
				stack.stackSize--;
			return true;
		}
        return false;
    }
}
