package com.raitichan.MCTweet;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.raitichan.MCTweet.client.commands.MCTweetCommandManager;
import com.raitichan.MCTweet.client.gui.MCTweetGuiHandler;
import com.raitichan.MCTweet.config.MCTweetConfig;
import com.raitichan.MCTweet.twitter.TwitterCore;

/**
 * This mod makes it possible to Tweet from Minecraft.
 * This class is Mod's main class.
 * <br>Created by Raiti-chan on 2017/05/13.
 *
 * @author Raiti-chan
 * @version 1.0.0α
 * @since 1.0.0α
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = MCTweet.MOD_ID, name = MCTweet.MOD_NAME, version = MCTweet.MOD_VERSION, acceptedMinecraftVersions = MCTweet.MCVERSION, guiFactory = "com.raitichan.MCTweet.config.MCTweetConfigGuiFactory")
public class MCTweet {
	
	/**
	 * This mod's id.
	 */
	public static final String MOD_ID = "mctweet";
	
	/**
	 * This mod's name.
	 */
	public static final String MOD_NAME = "MCTweet";
	
	/**
	 * This mod's version.
	 */
	public static final String MOD_VERSION = "1.10.2-1.0.0a";
	
	/**
	 * Available Minecraft version.
	 */
	public static final String MCVERSION = "[1.10.2]";
	
	/**
	 * This mod's metadata.
	 */
	@Mod.Metadata
	public static ModMetadata metadata;
	
	/**
	 * MOD Instance.
	 */
	@Mod.Instance
	public static MCTweet instance;
	
	
	/**
	 * PreInit.
	 * @param event FMLEvent.
	 */
	@Mod.EventHandler
	@SideOnly(Side.CLIENT)
	public void preInit (FMLPreInitializationEvent event) {
		writeMetadata();//MODメタデータの書き込み
		MCTweetConfig.getINSTANCE().init(event.getSuggestedConfigurationFile());//Configの初期化
		ClientCommandHandler.instance.registerCommand(MCTweetCommandManager.INSTANCE);// コマンドの登録
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new MCTweetGuiHandler());
	}
	
	/**
	 * Init.
	 * @param event FMLEvent.
	 */
	@Mod.EventHandler
	public void init (FMLInitializationEvent event) {
	
	}
	
	/**
	 * Post init.
	 * @param event FMLEvent.
	 */
	@Mod.EventHandler
	public void  postInit (FMLPostInitializationEvent event) {
		TwitterCore.getInstance().getAccessToken();
	}
	
	
	private static void writeMetadata () {
		metadata.modId = MOD_ID;
		metadata.name = MOD_NAME;
		metadata.version = MOD_VERSION;
		metadata.authorList.add("Raiti-chan");
		metadata.description = "This mod makes it possible to Tweet from Minecraft.";
		
		metadata.autogenerated = false;
	}
}
