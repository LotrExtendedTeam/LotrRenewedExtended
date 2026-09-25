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
		textField =  new TextFieldWidget(minecraft.font, 0, 0, 97, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.textBox")) {
			@Override
			public void setFocus(boolean pIsFocused) {
				super.setFocus(pIsFocused);
			}
		};
		textField.setBordered(false);
		textField.setValue("");
		textField.setFocus(false);
		textField.setCanLoseFocus(true);

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
				ExtendedBannerPermissions tweaking = ((PermissionButton)button).perm;
				if(protection.getEntryPerms().contains(tweaking)) {
					protection.getEntryPerms().remove(tweaking);
				}else {
					if(tweaking.equals(ExtendedBannerPermissions.EVERYTHING)) {
						protection.getEntryPerms().removeIf((bannerPerm) -> !bannerPerm.equals(ExtendedBannerPermissions.BANNER));
					}else if(!tweaking.equals(ExtendedBannerPermissions.EVERYTHING) && !tweaking.equals(ExtendedBannerPermissions.BANNER)){
						protection.getEntryPerms().remove(ExtendedBannerPermissions.EVERYTHING);
					}
					protection.getEntryPerms().add(tweaking);
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
		this.children = ImmutableList.of(this.permShowButton, this.deleteButton, textField);
		this.children.addAll(permButtons);


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
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTicks) {
		textField.setTextColor(protection.getProfile() instanceof ExtendedBannerProfileClient ? 14737632 : 65313);
		permButtons.forEach((button) -> button.visible = showPerms);
	}

}
