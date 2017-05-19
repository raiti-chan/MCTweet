package com.raitichan.MCTweet.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * <br>Created by Raiti-chan on 2017/05/18.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MCTweetGuiHandler implements IGuiHandler {
	/**
	 * Returns a Server side Container to be displayed to the user.
	 *
	 * @param ID     The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world  The current world
	 * @param x      X Position
	 * @param y      Y Position
	 * @param z      Z Position
	 * @return A GuiScreen/Container to be displayed to the user, null if none.
	 */
	@Override
	public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	/**
	 * Returns a Container to be displayed to the user. On the client side, this
	 * needs to return a instance of GuiScreen On the server side, this needs to
	 * return a instance of Container
	 *
	 * @param ID     The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world  The current world
	 * @param x      X Position
	 * @param y      Y Position
	 * @param z      Z Position
	 * @return A GuiScreen/Container to be displayed to the user, null if none.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		switch (ID) {
			case GuiPinInput.ID:
				return new GuiPinInput();
		}
		
		return null;
	}
}
