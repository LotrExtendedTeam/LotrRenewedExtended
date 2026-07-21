package com.github.maximuslotro.lotrrextended.client.gui.screen.settings;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedBasicAbstractScreen;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.client.gui.widget.button.LeftRightButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class AbstractExtendedOptionsScreen extends ExtendedBasicAbstractScreen{
	private final SettingsPage currentPage;
	public static int option = 0;
	public AbstractExtendedOptionsScreen(ITextComponent pTitle, SettingsPage pCurrentPage) {
		super(pTitle, 200, 256);
		this.currentPage = pCurrentPage;
	}

	public boolean hasCheatsEnabled() {
		boolean hasCheatsEnabled = false;
		if (this.minecraft.getSingleplayerServer() != null) {
		    hasCheatsEnabled = this.minecraft.getSingleplayerServer().isSingleplayerOwner(this.minecraft.player.getGameProfile()) 
		            && this.minecraft.getSingleplayerServer().getWorldData().getAllowCommands()
		            || this.minecraft.getSingleplayerServer().getPlayerList().isAllowCheatsForAllPlayers();
		} else {
			hasCheatsEnabled = this.minecraft.player.hasPermissions(2);
		}
		return hasCheatsEnabled;
	}
	@Override
	public void init() {
		super.init();
		// Return button
		this.addButton(new LeftRightButton(Math.max(0, this.leftPos - 140), this.topPos + (this.imageHeight + 20) / 4, 100, 20, 
			true, new TranslationTextComponent("gui.lotr.menu.return"), (b) -> {
				this.minecraft.setScreen(new MiddleEarthMasterMenuScreen());
			}));
		// Client options button
		this.addButton(new ExtendedColorChangingButton(Math.max(0, this.leftPos - 140), this.topPos + 20 + (this.imageHeight + 20) / 4, 100, 20, 
			new TranslationTextComponent("gui.lotrextended.settings.button"), 
			button -> {
				this.minecraft.setScreen(new ExtendedClientOptionsScreen());
			}, 0.13f, 0.92f, 0.71f)  {
			@Override
			public boolean setColor(){
				return AbstractExtendedOptionsScreen.this.currentPage==SettingsPage.CLIENT;
			}
		});
		if (hasCheatsEnabled()) {
			this.addButton(new ExtendedColorChangingButton(Math.max(0, this.leftPos - 140), this.topPos + 40 + (this.imageHeight + 20) / 4, 100, 20, 
				new TranslationTextComponent("gui.lotrextended.serversettings.button"), 
				button -> {
					this.minecraft.setScreen(new ExtendedServerOptionsScreen());
				}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return AbstractExtendedOptionsScreen.this.currentPage==SettingsPage.SERVER;
				}
			});
		}else {
			this.addButton(new ExtendedColorChangingButton(Math.max(0, this.leftPos - 140), this.topPos + 40 + (this.imageHeight + 20) / 4, 100, 20, 
				new TranslationTextComponent("gui.lotrextended.serversettings.button"), 
				button -> {}, 0.88f, 0.33f, 0.33f) {
				@Override
				public boolean setColor(){
					return true;
				}
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					AbstractExtendedOptionsScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.serversettings.no_perm"), 150), pMouseX, pMouseY);
				}
			});
		}
		this.addButton(new ExtendedColorChangingButton(Math.max(0, this.leftPos - 140), this.topPos + 60 + (this.imageHeight + 20) / 4, 100, 20, 
			new TranslationTextComponent("Wiki Stuff"), 
			button -> {
				this.minecraft.setScreen(new ExtendedWikiGenScreen());
			}, 0.13f, 0.92f, 0.71f)  {
			@Override
			public boolean setColor(){
				return AbstractExtendedOptionsScreen.this.currentPage==SettingsPage.WIKI;
			}
		});
	}
	public enum SettingsPage {
		CLIENT, SERVER, WIKI
	}
}
