package com.raitichan.MCTweet.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import com.raitichan.MCTweet.MCTweet;

/**
 * <br>Created by Raiti-chan on 2017/05/15.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MCTweetCommandHelp extends MCTweetCommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName () {
		return "Help";
	}
	
	/**
	 * Gets the usage string for the command.
	 *
	 * @param sender sender
	 */
	@Override
	public String getCommandUsage (ICommandSender sender) {
		return MCTweet.MOD_ID + ".command.help";
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
	
	}
}
