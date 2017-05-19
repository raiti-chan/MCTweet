package com.raitichan.MCTweet.client.commands;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.raitichan.MCTweet.client.gui.GuiPinInput;
import com.raitichan.MCTweet.twitter.TwitterCore;
import twitter4j.TwitterException;

/**
 * <br>Created by Raiti-chan on 2017/05/17.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class MCTweetCommandLogin extends MCTweetCommandBase {
	
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName () {
		return "login";
	}
	
	/**
	 * Gets the usage string for the command.
	 *
	 * @param sender sender
	 */
	@Override
	public String getCommandUsage (ICommandSender sender) {
		return COMMAND_USAGE_BASE + "login";
	}
	
	/**
	 * Callback for when the command is executed
	 *
	 * @param server server
	 * @param sender sender
	 * @param args args
	 */
	@Override
	public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			//player.openGui(MCTweet.instance, GuiPinInput.ID, player.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);
			try {
				Desktop.getDesktop().browse(new URI(TwitterCore.getInstance().getRequestToken()));
			} catch (IOException | URISyntaxException | TwitterException e) {
				e.printStackTrace();
			}
			MCTweetCommandManager.GUI_HANDLER.openGuiFromCommand(player, GuiPinInput.ID, player.getEntityWorld(), player.posX, player.posY, player.posZ);
		}
	}
}
