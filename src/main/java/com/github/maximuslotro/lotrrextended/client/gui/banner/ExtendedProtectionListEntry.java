package com.github.maximuslotro.lotrrextended.client.gui.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.maximuslotro.lotrrextended.client.gui.banner.ExtendedBannerConfigurationScreen.PermissionButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTextureOffsetButton;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileClient;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

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
	public List<PermissionButton> permButtons = new ArrayList<>();
	public ExtendedTextureOffsetButton permShowButton;
	public ExtendedTextureOffsetButton deleteButton;
	public TextFieldWidget textField;
	public boolean showPerms = false;
	protected ProtectionEntry protection;
	ExtendedBannerConfigurationScreen parentScreen;

	public ExtendedProtectionListEntry(Minecraft pMinecraft, ExtendedBannerConfigurationScreen pParentScreen, ProtectionEntry pProtectionEntry) {
		this.minecraft = pMinecraft;
		this.protection = pProtectionEntry;
		this.parentScreen = pParentScreen;
		this.showPerms = !(protection.getEntryPerms().size() == 1 && protection.getEntryPerms().contains(ExtendedBannerPermissions.EVERYTHING));
		textField = new TextFieldWidget(minecraft.font, 0, 0, 135, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.textBox"));
		textField.setBordered(false);
		textField.setValue(protection.getProfile().getDisplayName());
		textField.setFocus(false);
		textField.setMaxLength(24);
		textField.setResponder((newValue) -> {
			if(protection.getProfile() instanceof ExtendedBannerProfileClient) {
				((ExtendedBannerProfileClient)protection.getProfile()).setName(newValue);
			}
			else {
				ExtendedBannerProfileClient newProfile = new ExtendedBannerProfileClient(newValue);
				protection.updateProfile(newProfile);
			}
		});

		permShowButton = new ExtendedTextureOffsetButton(0, 0, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), 
			button -> showPerms=!showPerms, ExtendedBannerConfigurationScreen.GUI_TEXTURE, ExtendedBannerConfigurationScreen.SIZE, ExtendedBannerConfigurationScreen.SIZE){
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
		deleteButton = new ExtendedTextureOffsetButton(0, 0, 190, 232, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.delete"), 
			button -> {isRemoved = true; }, ExtendedBannerConfigurationScreen.GUI_TEXTURE, ExtendedBannerConfigurationScreen.SIZE , ExtendedBannerConfigurationScreen.SIZE){
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
					if(protection.getEntryPerms().isEmpty()) {
						protection.getEntryPerms().add(ExtendedBannerPermissions.EVERYTHING);
					}
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
					//TODO fix tooltip clipping like how social interactions menu does it
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

	public boolean isRemoved() {
		return isRemoved;
	}

	@Override
	public List<? extends IGuiEventListener> children() {
		return children;
	}

	public void tick() {
		textField.tick();
		textField.setTextColor(protection.getProfile() instanceof ExtendedBannerProfileClient ? 14737632 : 65313);
		permButtons.forEach((button) -> button.visible = showPerms);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTicks) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(ExtendedBannerConfigurationScreen.GUI_TEXTURE);
		AbstractGui.blit(pMatrixStack, pLeft-1, pTop-2, 200, 0, 158, 32, ExtendedBannerConfigurationScreen.SIZE, ExtendedBannerConfigurationScreen.SIZE);
		textField.x = pLeft + 3;
		textField.y = pTop + 5;
		textField.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		permShowButton.x = pLeft + 1;
		permShowButton.y = pTop + 18;
		permShowButton.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		deleteButton.x = pLeft + 142;
		deleteButton.y = pTop + 3;
		deleteButton.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		if(showPerms) {
			permButtons.forEach((button) -> {
				int leftLoc = 13;
				if(button.perm.ordinal()>0) {
					leftLoc += 13;
				}
				if(button.perm.ordinal()>1) {
					leftLoc += (button.perm.ordinal()-1) * 10;
				}
				button.x = pLeft + leftLoc;
				button.y = pTop + 19;
				button.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);});
		}
	}
}
