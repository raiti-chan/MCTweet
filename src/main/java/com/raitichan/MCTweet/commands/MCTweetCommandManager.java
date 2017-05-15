package com.raitichan.MCTweet.commands;

import javax.annotation.Nullable;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

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
	 * This class instance.
	 */
	public static final MCTweetCommandManager INSTANCE = new MCTweetCommandManager();
	
	/**
	 * Commands list.
	 */
	private Map<String, MCTweetCommandBase> commandMap = new HashMap<>();
	
	private Set<MCTweetCommandBase> commandSet = new HashSet<>();
	
	private MCTweetCommandManager () {
		this.registerCommand(new MCTweetCommandHelp());
	}
	
	
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
	 *  Get a list of options for when the user presses the TAB key
	 * @param server server
	 * @param sender sender
	 * @param args args
	 * @param pos pos
	 * @return list of options.
	 */
	@Override
	@NotNull
	public List<String> getTabCompletionOptions (MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return super.getTabCompletionOptions(server, sender, args, pos);
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
		if (args.length == 0) {
			sender.addChatMessage(new TextComponentTranslation(this.getCommandUsage(sender)));
			return;
		}
		MCTweetCommandBase command = commandMap.get(args[0]);
		if (command == null) throw new CommandNotFoundException(MCTweet.MOD_ID + ".command.notfound");
		String[] newArgs = new String[args.length - 1];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = args[1];
		}
		command.execute(server, sender, newArgs);
	}
	
	/**
	 * Register command
	 * @param command Commands to register
	 */
	private void registerCommand(@NotNull MCTweetCommandBase command) {
		this.commandMap.put(command.getCommandName(), command);
		this.commandSet.add(command);
		
		for (String alias : command.getCommandAliases()) {
			MCTweetCommandBase listedCommand = this.commandMap.get(command.getCommandName());
			if (listedCommand == null || !listedCommand.getCommandName().equals(alias))
				this.commandMap.put(alias, command);
		}
	}
	
	
	List<MCTweetCommandBase> getPossibleCommands (ICommandSender sender) {
		List<MCTweetCommandBase> list = new ArrayList<>();
		for (MCTweetCommandBase command : this.commandSet) {
			if (command.checkPermission(Minecraft.getMinecraft().getIntegratedServer(), sender)) {
				list.add(command);
			}
		}
		return list;
	}
	
	Map<String, MCTweetCommandBase> getCommandsMap () {
		return this.commandMap;
	}
	
	
}
