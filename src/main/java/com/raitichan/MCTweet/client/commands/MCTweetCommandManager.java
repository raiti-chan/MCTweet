package com.raitichan.MCTweet.client.commands;

import javax.annotation.Nullable;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.raitichan.MCTweet.MCTweet;
import org.jetbrains.annotations.NotNull;

/**
 * <br>Created by Raiti-chan on 2017/05/14.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class MCTweetCommandManager extends CommandBase {
	
	static final GuiHandlerForCommand GUI_HANDLER = new GuiHandlerForCommand();
	
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
		MinecraftForge.EVENT_BUS.register(GUI_HANDLER);
		this.registerCommand(new MCTweetCommandHelp());
		this.registerCommand(new MCTweetCommandLogin());
		this.registerCommand(new MCTweetCommandTweet());
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
		return MCTweetCommandBase.COMMAND_USAGE_BASE + "help";
	}
	
	
	/**
	 * Get a list of options for when the user presses the TAB key
	 *
	 * @param server server
	 * @param sender sender
	 * @param args   args
	 * @param pos    pos
	 * @return list of options.
	 */
	@Override
	@NotNull
	public List<String> getTabCompletionOptions (MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		if (args.length == 1) {
			//該当コマンドの検索
			List<String> list = new ArrayList<>();
			
			for (Map.Entry<String, MCTweetCommandBase> entry : this.commandMap.entrySet()) {
				if (CommandBase.doesStringStartWith(args[0], entry.getKey()) && entry.getValue().checkPermission(server, sender)) {
					list.add(entry.getKey());
				}
			}
			return list;
		} else {
			if (args.length > 1) {
				MCTweetCommandBase commandBase = this.commandMap.get(args[0]);
				if (commandBase == null || !commandBase.checkPermission(server, sender)) return Collections.emptyList();
				String[] newArgs = new String[args.length - 1];
				for (int i = 0; i < newArgs.length; i++) {
					newArgs[i] = args[1];
				}
				return commandBase.getTabCompletionOptions(server, sender, newArgs, pos);
			}
		}
		return Collections.emptyList();
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
		if (command.checkPermission(server, sender)) command.execute(server, sender, newArgs);
		else {
			TextComponentTranslation textComponents = new TextComponentTranslation("commands.generic.permission");
			textComponents.getStyle().setColor(TextFormatting.RED);
			sender.addChatMessage(textComponents);
		}
	}
	
	/**
	 * Register command
	 *
	 * @param command Commands to register
	 */
	private void registerCommand (@NotNull MCTweetCommandBase command) {
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
	
	//==================================================================================================================
	@SuppressWarnings("WeakerAccess")
	public static class GuiHandlerForCommand {
		
		private int guiId = -1;
		
		private EntityPlayer player = null;
		
		private BlockPos pos = null;
		
		private World world = null;
		
		@SuppressWarnings("unused")
		@SubscribeEvent
		public void tickEndEvent (TickEvent.ClientTickEvent event) {
			if (event.phase == TickEvent.Phase.START || guiId == -1) return;
			player.openGui(MCTweet.instance, guiId, world, pos.getX(), pos.getY(), pos.getZ());
			guiId = -1;
			player = null;
			pos = null;
			world = null;
		}
		
		public void openGuiFromCommand (EntityPlayer player, int guiId, World world, BlockPos pos) {
			this.player = player;
			this.guiId = guiId;
			this.world = world;
			this.pos = pos;
		}
		
		public void openGuiFromCommand (EntityPlayer player, int guiId, World world, int x, int y, int z) {
			this.openGuiFromCommand(player, guiId, world, new BlockPos(x, y, z));
		}
		
		public void openGuiFromCommand (EntityPlayer player, int guiId, World world, double x, double y, double z) {
			this.openGuiFromCommand(player, guiId, world, (int) x, (int) y, (int) z);
		}
		
		
	}
	
}
