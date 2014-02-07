package com.forgetutorials.mod.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.forgetutorials.lib.utilities.ForgeRegistryUtilities;
import com.forgetutorials.mod.core.proxy.CommonProxy;
import com.forgetutorials.mod.lib.ModInfo;
import com.forgetutorials.mod.lib.Tabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION, dependencies = "required-after:ForgeTutorialsAPI")
@NetworkMod(serverSideRequired = false, clientSideRequired = true)
public class ForgeTutorialsMod implements IGuiHandler {

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs tabs = new Tabs();

	public static final int metaDimID = 8;
	public static int metaBiomeID = 108;


	public static ForgeRegistryUtilities registry = new ForgeRegistryUtilities("metatechcraft", ModInfo.MOD_ID);

	@Instance("ForgeTutorialsMod")
	public static ForgeTutorialsMod instance;

	@EventHandler
	public void load(FMLInitializationEvent event) {
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(">> ForgeTutorialsMod: preInit");

		NetworkRegistry.instance().registerGuiHandler(this, ForgeTutorialsMod.instance);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(">> ForgeTutorialsMod: init");
		ForgeTutorialsMod.proxy.initizeRendering();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(">> ForgeTutorialsMod: postInit");
		ForgeTutorialsMod.proxy.registerTileEntities();
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}