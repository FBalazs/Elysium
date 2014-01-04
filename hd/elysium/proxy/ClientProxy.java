package hd.elysium.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import hd.elysium.Elysium;
import hd.elysium.block.ElysianPortal;
import hd.elysium.render.RenderPortal;
import hd.elysium.tile.TileEntityPortal;

public class ClientProxy extends CommonProxy
{
	public void registerRenderers()
	{
		ElysianPortal.renderId = RenderingRegistry.getNextAvailableRenderId();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPortal.class, new RenderPortal(Elysium.config.get("performance", "beamPoligons", 32).getInt()));
	}
	
	@Override
	public void init()
	{
		System.out.println("[ELYSIUM] Initializing ClientProxy...");
		this.registerRenderers();
		System.out.println("[ELYSIUM] Initialized ClientProxy!");
	}
}
