package com.raitichan.MCTweet.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import com.raitichan.MCTweet.MCTweet;
import org.jetbrains.annotations.NotNull;

/**
 * <br>Created by Raiti-chan on 2017/05/14.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MCTweetCommandManager extends CommandBase {
	
	/**
	 * Gets the name of the command
	 */
	@Override
	@NotNull
	public String getCommandName () {
		return "mctweet";
	}
	
	/**
	 * @return Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel () {
		return 0;
	}
	
	/**
	 * Gets the usage string for the command.
	 *
	 * @param sender message.
	 */
	@Override
	@NotNull
	public String getCommandUsage (@NotNull ICommandSender sender) {
		return MCTweet.MOD_ID + ".command.mctweet";
	}
	
	/**
	 * Callback for when the command is executed
	 *
	 * @param server server.
	 * @param sender sender.
	 * @param args   command parameter.
	 */
	@Override
	public void execute (@NotNull MinecraftServer server, @NotNull ICommandSender sender, @NotNull String[] args) throws CommandException {
		sender.addChatMessage(new TextComponentString("Test!"));
	}
}
