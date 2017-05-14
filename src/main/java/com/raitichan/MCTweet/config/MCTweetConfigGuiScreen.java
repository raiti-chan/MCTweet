package com.raitichan.MCTweet.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.raitichan.MCTweet.MCTweet;

/**
 * MCTweet config gui screen.
 * <br>Created by Raiti-chan on 2017/05/13.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MCTweetConfigGuiScreen extends GuiConfig {
	/**
	 * GuiConfig constructor that will use ConfigChangedEvent when editing is concluded. If a non-null value is passed for configID,
	 * the OnConfigChanged and PostConfigChanged events will be posted when the Done button is pressed if any configElements were changed
	 * (includes child screens). If configID is not defined, the events will be posted if the parent gui is null or if the parent gui
	 * is not an instance of GuiConfig.
	 *
	 * @param parentScreen           the parent GuiScreen object
	 */
	public MCTweetConfigGuiScreen (GuiScreen parentScreen) {
		super(parentScreen, getConfigElement(), MCTweet.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(MCTweetConfig.getINSTANCE().getConfiguration().toString()));
	}
	
	private static List<IConfigElement> getConfigElement() {
		List<IConfigElement> list = new ArrayList<>();
		
		list.add(new ConfigElement(MCTweetConfig.getINSTANCE().getConfiguration().getCategory(MCTweetConfig.ConfigGroup.API.groupName)));
		
		return list;
	}
}
