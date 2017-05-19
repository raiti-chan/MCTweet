package com.raitichan.MCTweet.client.commands;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.raitichan.MCTweet.MCTweet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * <br>Created by Raiti-chan on 2017/05/15.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class MCTweetCommandHelp extends MCTweetCommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName () {
		return "help";
	}
	
	/**
	 * Gets the usage string for the command.
	 *
	 * @param sender sender
	 */
	@Override
	public String getCommandUsage (ICommandSender sender) {
		return COMMAND_USAGE_BASE + "help";
	}
	
	@NotNull
	@Override
	public List<String> getTabCompletionOptions (MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		if (args.length == 1) {
			Set<String> set = MCTweetCommandManager.INSTANCE.getCommandsMap().keySet();
			return CommandBase.getListOfStringsMatchingLastWord(args, (String[]) set.toArray(new String[set.size()]));
		} else {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Callback for when the command is executed
	 *
	 * @param server server
	 * @param sender sender
	 * @param args   args
	 */
	@Override
	public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		List<MCTweetCommandBase> possibleCommands = MCTweetCommandManager.INSTANCE.getPossibleCommands(sender);
		
		int j = (possibleCommands.size() - 1) / 7;
		int k;
		
		try {
			k = args.length == 0 ? 0 : CommandBase.parseInt(args[0], 1, j + 1) - 1;
		} catch (NumberInvalidException e) {
			Map<String, MCTweetCommandBase> map = MCTweetCommandManager.INSTANCE.getCommandsMap();
			MCTweetCommandBase commandBase = map.get(args[0]);
			
			if (commandBase != null) {
				throw new WrongUsageException(commandBase.getCommandUsage(sender));
			}
			
			if (MathHelper.parseIntWithDefault(args[0], -1) != -1) {
				throw e;
			}
			
			throw new CommandNotFoundException(MCTweet.MOD_ID + ".command.notfound");
		}
		
		int l = Math.min((k + 1) * 7, possibleCommands.size());
		TextComponentTranslation textComponents = new TextComponentTranslation("commands.help.header", k + 1, j + 1);
		textComponents.getStyle().setColor(TextFormatting.DARK_GREEN);
		sender.addChatMessage(textComponents);
		
		for (int i1 = k * 7; i1 < l; ++i1) {
			MCTweetCommandBase commandBase = possibleCommands.get(i1);
			TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(commandBase.getCommandUsage(sender));
			textcomponenttranslation.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + commandBase.getCommandName() + " "));
			sender.addChatMessage(textcomponenttranslation);
		}
		
		if (k == 0 && sender instanceof EntityPlayer) {
			TextComponentTranslation textComponents1 = new TextComponentTranslation("commands.help.footer");
			textComponents1.getStyle().setColor(TextFormatting.GREEN);
			sender.addChatMessage(textComponents1);
		}
		
	}
}
