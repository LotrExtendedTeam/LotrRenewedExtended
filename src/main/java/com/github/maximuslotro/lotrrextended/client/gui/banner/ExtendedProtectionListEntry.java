package com.github.maximuslotro.lotrrextended.client.gui.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.maximuslotro.lotrrextended.client.gui.banner.ExtendedBannerConfigurationScreen.IndexedButton;
import com.github.maximuslotro.lotrrextended.client.gui.banner.ExtendedBannerConfigurationScreen.PermissionButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTextureOffsetButton;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileClient;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedProtectionListEntry extends AbstractOptionList.Entry<ExtendedProtectionListEntry> {
	private final Minecraft minecraft;
	private final List<IGuiEventListener> children;
	private boolean isRemoved;
	public List<ExtendedTextureOffsetButton> permButtons = new ArrayList<>();
	public ExtendedTextureOffsetButton permShowButton;
	public ExtendedTextureOffsetButton deleteButton;
	public TextFieldWidget textField;
	public boolean showPerms = false;
	private ProtectionEntry protection;

	public ExtendedProtectionListEntry(Minecraft pMinecraft, ExtendedBannerConfigurationScreen pParentScreen, ProtectionEntry pProtectionEntry) {
		this.minecraft = pMinecraft;
		this.protection = pProtectionEntry;
		textField = new TextFieldWidget(minecraft.font, 0, 0, 97, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.textBox"));
		textField.setBordered(false);
		textField.setValue("");
		textField.setFocus(false);
		textField.setCanLoseFocus(true);
		
		this.textField.setResponder((newValue) -> {
			if(protection.getProfile() instanceof ExtendedBannerProfileClient) {
				((ExtendedBannerProfileClient)protection.getProfile()).setName(newValue);
			}
			else {
				ExtendedBannerProfileClient newProfile = new ExtendedBannerProfileClient(newValue);
				protection.updateProfile(newProfile);
			}
		});

		permShowButton = new IndexedButton(3, 2, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), button -> showPerms=!showPerms){
			@Override
			public boolean shouldOffset() {
				return showPerms;
			}
			@Override
			public boolean shouldDoDifferentOffset() {
				return !(protection.getEntryPerms().size()==1 && protection.getEntryPerms().contains(ExtendedBannerPermissions.EVERYTHING));
			}
			@Override
			public int additionalOffset() {
				return 10;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				pParentScreen.renderTooltip(pPoseStack, minecraft.font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms.disc"), 200), pMouseX, pMouseY);
			}
		};
		deleteButton = new IndexedButton(120, 0, 190, 232, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.delete"), button -> {
			isRemoved = true;
		}){
			@Override
			public int hoverOffset() {
				return 10;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				pParentScreen.renderTooltip(pPoseStack, minecraft.font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.delete.disc"), 200), pMouseX, pMouseY);
			}
		};
		for(ExtendedBannerPermissions perm : ExtendedBannerPermissions.values()) {
			int leftLoc = 3;
			if(perm.ordinal()>0) {
				leftLoc += 12;
			}
			if(perm.ordinal()>1 ) {
				leftLoc += (perm.ordinal()-1) * 10;
			}
			PermissionButton permButton = new PermissionButton(leftLoc, 2, 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, button -> {
				ExtendedBannerPermissions permission = ((PermissionButton)button).perm;
				if(protection.getEntryPerms().contains(permission)) {
					protection.getEntryPerms().remove(permission);
				}else {
					// Handle adding permissions
					if(permission.equals(ExtendedBannerPermissions.EVERYTHING)) {
						// If everything is allowed, clear everything but banner (which isn't covered by everything
						protection.getEntryPerms().removeIf((bannerPerm) -> !bannerPerm.equals(ExtendedBannerPermissions.BANNER));
					}else if(!permission.equals(ExtendedBannerPermissions.EVERYTHING) && !permission.equals(ExtendedBannerPermissions.BANNER)){
						// If anything except everything and banner, clear everything
						protection.getEntryPerms().remove(ExtendedBannerPermissions.EVERYTHING);
					}
					protection.getEntryPerms().add(permission);
				}
			}){
				@Override
				public boolean shouldOffset() {
					return protection.getEntryPerms().contains((this.perm));

				}
				@Override
				public int hoverOffset() {
					return 10;
				}
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					pParentScreen.renderTooltip(pPoseStack, minecraft.font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)+".disc").withStyle(perm==ExtendedBannerPermissions.BANNER ? TextFormatting.RED : (perm==ExtendedBannerPermissions.EVERYTHING ? TextFormatting.YELLOW : TextFormatting.WHITE)), 200), pMouseX, pMouseY);
				}
			};
			permButton.visible = showPerms;
			permButtons.add(permButton);
		}
		this.children = new ImmutableList.Builder<IGuiEventListener>().add(this.permShowButton).add(this.deleteButton).add(this.textField).addAll(this.permButtons).build();
	}

	public void setRemoved(boolean pRemoved) {
		this.isRemoved = pRemoved;
	}

	@Override
	public List<? extends IGuiEventListener> children() {
		return children;
	}

	public void tick() {
		textField.tick();
		textField.setTextColor(protection.getProfile() instanceof ExtendedBannerProfileClient ? 14737632 : 65313);
		if(isRemoved) {
			textField.setTextColor(16711680);
		}
		permButtons.forEach((button) -> button.visible = showPerms);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTicks) {
		this.minecraft.getTextureManager().bind(ExtendedBannerConfigurationScreen.guiTexture);
	    AbstractGui.blit(pMatrixStack, 0, 0, 0, 192, 80, 17, 200, 192);
	    AbstractGui.blit(pMatrixStack, 20, 0, 1, 192, 79, 17, 200, 192);
	}
}
