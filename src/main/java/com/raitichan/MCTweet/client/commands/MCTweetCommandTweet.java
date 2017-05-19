package com.raitichan.MCTweet.client.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import com.raitichan.MCTweet.twitter.TwitterCore;
import twitter4j.TwitterException;

/**
 * <br>Created by Raiti-chan on 2017/05/19.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MCTweetCommandTweet extends MCTweetCommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName () {
		return "tweet";
	}
	
	/**
	 * Gets the usage string for the command.
	 *
	 * @param sender sender
	 */
	@Override
	public String getCommandUsage (ICommandSender sender) {
		return COMMAND_USAGE_BASE + "tweet";
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
		try {
			TwitterCore.getInstance().tweet(args[0]);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
