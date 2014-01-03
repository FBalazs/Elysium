package hd.elysium;

import java.io.File;

import hd.elysium.block.ElysianBlock;
import hd.elysium.block.ElysianGrass;
import hd.elysium.block.ElysianPortal;
import hd.elysium.item.ItemDebug;
import hd.elysium.item.ElysianItem;
import hd.elysium.proxy.CommonProxy;
import hd.elysium.tile.TileEntityPortal;
import hd.elysium.world.ElysiumWorldProvider;
import hd.elysium.world.biome.ElysianBiome;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Elysium.ID, version = Elysium.VERSION, name = Elysium.NAME, dependencies="required-after:Forge@[9.11.1.953,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Elysium
{
	public static final String ID = "hdelysium";
	public static final String VERSION = "1.0.0";
	public static final String NAME = "Elysium";
	
	@Instance(ID)
	public static Elysium instance;
	@SidedProxy(serverSide = "hd.elysium.proxy.CommonProxy", clientSide = "hd.elysium.proxy.ClientProxy")
	public static CommonProxy proxy;
	public static Configuration config;
	
	public static int dimensionId;
	
	public static ElysianCreativeTab tabBlocks,
									tabItems;
	
	public static ElysianItem itemDebug;
	
	public static ElysianBlock blockDirt,
								blockGrass,
								blockPalestone,
								blockPortal;
	
	public static BiomeGenBase biomeOcean,
								biomePlain,
								biomeHills;
	
	public void addItems()
	{
		tabItems = new ElysianCreativeTab(ID+".items");
		tabItems.addNameTranslation("en_US", "Elysium Items");
		
		itemDebug = new ItemDebug(config.getItem("debug", DefaultProperties.itemDebugId).getInt(), "debug");
			itemDebug.setCreativeTab(tabItems);
			itemDebug.addNameTranslation("en_US", "Debug Item");
		
		tabItems.setItemStack(new ItemStack(Item.appleGold));
	}
	
	public void addBlocks()
	{
		tabBlocks = new ElysianCreativeTab(ID+".blocks");
		tabBlocks.addNameTranslation("en_US", "Elysium Blocks");
		
		blockDirt = new ElysianBlock(config.getTerrainBlock("block", "dirt", DefaultProperties.blockDirtId, null).getInt(), "dirt", Material.ground);
			blockDirt.setHardness(0.5F);
			blockDirt.setHarvestLevel("shovel", 0);
			blockDirt.setStepSound(Block.soundGravelFootstep);
			blockDirt.setCreativeTab(tabBlocks);
			blockDirt.addNameTranslation("en_US", "Elysian Dirt");
		blockDirt.register();
		
		blockGrass = new ElysianGrass(config.getTerrainBlock("block", "grass", DefaultProperties.blockGrassId, null).getInt(), "grass", Material.grass);
			blockGrass.setHardness(0.6F);
			blockGrass.setHarvestLevel("shovel", 0);
			blockGrass.setStepSound(Block.soundGrassFootstep);
			blockGrass.setIdDropped(blockDirt.blockID);
			blockGrass.setCreativeTab(tabBlocks);
			blockGrass.addNameTranslation("en_US", "Elysian Grass");
		blockGrass.register();
		
		blockPalestone = new ElysianBlock(config.getTerrainBlock("block", "palestone", DefaultProperties.blockPalestoneId, null).getInt(), "palestone", Material.rock);
			blockPalestone.setHardness(2F);
			blockPalestone.setResistance(12.5F);
			blockPalestone.setHarvestLevel("pickaxe", 1);
			blockPalestone.setStepSound(Block.soundStoneFootstep);
			blockPalestone.setCreativeTab(tabBlocks);
			blockPalestone.addNameTranslation("en_US", "Palestone");
		blockPalestone.register();
		
		blockPortal = new ElysianPortal(config.getBlock("portal", DefaultProperties.blockPortalId).getInt(), "portal", Material.rock);
			blockPortal.setHardness(0.8F);
			blockPortal.setStepSound(Block.soundStoneFootstep);
			blockPortal.setHarvestLevel("pickaxe", 1);
			blockPortal.setCreativeTab(tabBlocks);
		blockPortal.register();
		
		tabBlocks.setItemStack(new ItemStack(blockDirt));
	}
	
	public void registerTileEntities()
	{
		registerTile(TileEntityPortal.class);
	}
	
	public static void registerTile(Class<? extends TileEntity> tileClass)
	{
		GameRegistry.registerTileEntity(tileClass, tileClass.getName());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("[ELYSIUM] Pre initialization started!");
		config = new Configuration(new File(event.getModConfigurationDirectory(), "/elysium/config.cfg"));
		config.load();
		
		dimensionId = config.get("dimension", "id", DefaultProperties.dimensionId).getInt();
		this.addItems();
		this.addBlocks();
		this.registerTileEntities();
		
		biomeOcean = new ElysianBiome(config.get("biomeIds", "ocean", 200).getInt(), "Elysium Ocean", -0.5F, 0F);
		biomePlain = new ElysianBiome(config.get("biomeIds", "plain", 201).getInt(), "Elysium Plain", 0.1F, 0.25F);
		biomeHills = new ElysianBiome(config.get("biomeIds", "hills", 202).getInt(), "Elysium Hills", 0.25F, 1.5F);
		
		DimensionManager.registerProviderType(dimensionId, ElysiumWorldProvider.class, false);
		DimensionManager.registerDimension(dimensionId, dimensionId);
		
		proxy.init();
		
		config.save();
		System.out.println("[ELYSIUM] Pre initialization ended!");
	}
}
