package com.raitichan.MCTweet.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.raitichan.MCTweet.MCTweet;
import com.raitichan.MCTweet.twitter.TwitterCore;
import org.lwjgl.input.Keyboard;
import twitter4j.TwitterException;

/**
 * <br>Created by Raiti-chan on 2017/05/18.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class GuiPinInput extends GuiScreen {
	
	public static final int ID = 0;
	
	private GuiTextField pinField;
	
	private GuiButton okButton;
	
	private GuiButton cancelButton;
	
	@Override
	public void updateScreen () {
		this.pinField.updateCursorCounter();
	}
	
	@Override
	public void initGui () {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.okButton = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format(MCTweet.MOD_ID + ".gui.ok")));
		this.cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel")));
		this.pinField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
		this.pinField.setMaxStringLength(16);
		this.pinField.setFocused(true);
		
	}
	
	@Override
	public void onGuiClosed () {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		switch (button.id) {
			case 0:
				try {
					TwitterCore.getInstance().getAccessTokenForPin(this.pinField.getText());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				this.mc.displayGuiScreen(null);
				break;
			case 1:
				this.mc.displayGuiScreen(null);
				break;
			case 2:
				break;
		}
		
	}
	
	@Override
	protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.pinField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped (char typedChar, int keyCode) throws IOException {
		this.pinField.textboxKeyTyped(typedChar, keyCode);
	}
	
	@Override
	public void drawScreen (int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, I18n.format(MCTweet.MOD_ID + ".gui.pin_input.pin"), this.width / 2, 20, 16777215);
		this.pinField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
}
