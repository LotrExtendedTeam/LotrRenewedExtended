package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.maximuslotro.lotrrextended.common.enums.BannerPermission;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCBannerSaveInfoPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenChangedScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenScreenPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedBannerConfigurationScreen extends ExtendedBasicAbstractScreen {
	private static final ResourceLocation guiTexture = new ResourceLocation("lotrextended:textures/gui/banner_gui.png");
	private List<BannerPermission> defaultPerms;
	private float alignmentLevel;
	private List<ProtectionEntry> whitelist;
	private boolean canSelfProtect;
	private int protectionType;
	private boolean needsUpdate = false;
	private Button protectionTypeButton;
	private Button update;
	/** Alignment entry field */
	private TextFieldWidget alignmentField;
	/** is the text that appears when you press the chat key and the input box appears pre-filled */
	private float previousAlignment;;
	private boolean showDefaultPerms = false;
	private List<Button> defaultPermButtons = new ArrayList<>();
	private BlockPos configurePos;

	public ExtendedBannerConfigurationScreen(ExtendedSBannerOpenScreenPacket serverData) {
		super(new StringTextComponent("BANNERCONFIGURATION"), 200, 192);
		this.defaultPerms = serverData.defaultPerms;
		this.alignmentLevel = Math.round(serverData.alignmentLevel * 10) / 10f;
		this.previousAlignment = alignmentLevel;
		this.whitelist = serverData.whitelist;
		this.canSelfProtect = serverData.canSelfProtect;
		this.protectionType = serverData.protectionType;
		this.configurePos = serverData.configurePos;
	}

	public void updatefromPacket(ExtendedSBannerOpenChangedScreenPacket serverData) {
		needsUpdate = false;
		this.defaultPerms = serverData.defaultPerms;
		this.alignmentLevel = Math.round(serverData.alignmentLevel * 10) / 10f;
		this.previousAlignment = alignmentLevel;
		this.whitelist = serverData.whitelist;
		this.canSelfProtect = serverData.canSelfProtect;
		this.protectionType = serverData.protectionType;
		handleAlignmentUpdate();
	}

	public BlockPos getConfigurePos() {
		return configurePos;
	}

	@Override
	public void init() {
		super.init();
		this.addButton(alignmentField = new TextFieldWidget(this.font, this.leftPos+63, this.topPos+81, 80, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.faction.textBox")) {
			@Override   
			public void setFocus(boolean pIsFocused) {
				super.setFocus(pIsFocused);
				if(!pIsFocused) {
					ExtendedBannerConfigurationScreen.this.handleAlignmentUpdate();
				}
			}
		});
		this.alignmentField.setMaxLength(12);
		this.alignmentField.setBordered(false);
		this.alignmentField.setValue("+"+alignmentLevel);
		this.alignmentField.setFocus(false);
		this.alignmentField.setCanLoseFocus(true);
		this.alignmentField.setTextColorUneditable(65313);
		this.addButton(protectionTypeButton = new SimpleButton(this.leftPos + 30, this.topPos + 20, 140, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"), button -> {
			protectionType = protectionType==0 ? 1 : 0;
			needsUpdate = true;
		}));
		protectionTypeButton.setMessage(protectionType==0 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"):new TranslationTextComponent("gui.lotrextended.banner_configuration.type_whitelist"));
		this.addButton(update = new SimpleButton(this.leftPos +this.imageWidth -115, this.topPos + this.imageHeight-25, 110, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update"), button -> {
			needsUpdate = false;
			ExtendedPacketHandler.sendToServer(new ExtendedCBannerSaveInfoPacket(this.defaultPerms, this.alignmentLevel, this.whitelist, this.canSelfProtect, this.protectionType, this.configurePos));
		}) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update.disc"), 200), pMouseX, pMouseY);
			}
		});
		update.active = false;
		this.addButton(new TextureOffsetButton(this.leftPos +this.imageWidth -140, this.topPos + this.imageHeight-25, 80, 192, 20, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection"), button -> {
			canSelfProtect = !canSelfProtect;
			needsUpdate = true;
		}){
			@Override
			public boolean shouldOffset() {
				return !ExtendedBannerConfigurationScreen.this.canSelfProtect;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection"+(ExtendedBannerConfigurationScreen.this.canSelfProtect ? ".on" : ".off")), 200), pMouseX, pMouseY);
			}
		});
		this.addButton(new TextureOffsetButton(this.leftPos + 7, this.topPos + this.imageHeight-25, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), button -> {
			showDefaultPerms = !showDefaultPerms;
		}){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.showDefaultPerms;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms.disc"), 200), pMouseX, pMouseY);
			}
		});
		for(BannerPermission perm : BannerPermission.values()) {
			int leftLoc = this.leftPos + 7;
			if(perm.ordinal()>0) {
				leftLoc += 12;
			}
			if(perm.ordinal()>1 && perm.ordinal()!=5) {
				if(perm.ordinal()>5) {
					leftLoc += (perm.ordinal()-5) * 10;
				}else {
					leftLoc += (perm.ordinal()-1) * 10;
				}
			}
			int upLoc = this.topPos + ((perm.ordinal()==0||perm.ordinal()>4) ? this.imageHeight-15 : this.imageHeight-25);
			TextureOffsetButton permButton = new PermissionButton(leftLoc, upLoc, 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, button -> {
				if(defaultPerms.contains(((PermissionButton)button).perm)) {
					defaultPerms.remove(((PermissionButton)button).perm);
				}else {
					defaultPerms.add(((PermissionButton)button).perm);
				}
				needsUpdate = true;
			}){
				@Override
				public boolean shouldOffset() {
					return ExtendedBannerConfigurationScreen.this.defaultPerms.contains((this.perm));
				}
				@Override
				public int hoverOffset() {
					return 10;
				}
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)+".disc"), 200), pMouseX, pMouseY);
				}
			};
			defaultPermButtons.add(permButton);
		}
		for(Button button : defaultPermButtons) {
			this.addButton(button);
		}
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		this.minecraft.getTextureManager().bind(guiTexture);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		TranslationTextComponent title = new TranslationTextComponent("gui.lotrextended.banner_configuration.title");
		this.font.draw(matStack, title, leftPos + (this.imageWidth/2) - (font.width(title)/2), topPos + 7, 4210752);
		if(protectionType==0) {
			alignmentField.visible=true;
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.faction.requires"), leftPos + 61, topPos + 64, 4210752);
			this.minecraft.getTextureManager().bind(guiTexture);
			blit(matStack, this.leftPos+60, this.topPos+76, 0, 192, 80, 17);
			alignmentField.render(matStack, mouseX, mouseY, tick);
		}else {
			alignmentField.visible=false;
		}
		for(Button button : defaultPermButtons) {
			button.visible = showDefaultPerms;
		}
		super.render(matStack, mouseX, mouseY, tick);
		for(Widget button : this.buttons) {
			if(button.isHovered()) {
				button.renderToolTip(matStack, mouseX, mouseY);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		alignmentField.tick();
		protectionTypeButton.setMessage(protectionType==0 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"):new TranslationTextComponent("gui.lotrextended.banner_configuration.type_whitelist"));
		update.active = needsUpdate;
	}

	public void handleAlignmentUpdate() {
		String newValue = alignmentField.getValue();
		if(newValue.startsWith("+")) {
			newValue=newValue.substring(1);
		}
		newValue=newValue.trim();
		try {
			alignmentLevel = Float.valueOf(newValue);
		}catch(NumberFormatException e) {
			alignmentLevel=1.0f;
		}
		alignmentLevel = Math.round(alignmentLevel * 10) / 10.f;

		this.alignmentField.setValue("+"+alignmentLevel);
		if(this.previousAlignment!=this.alignmentLevel) {
			this.needsUpdate = true;
			previousAlignment = alignmentLevel;
		}
	}

	public static class PermissionButton extends TextureOffsetButton {
		public BannerPermission perm;
		public PermissionButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, BannerPermission perm, IPressable pOnPress) {
			super(pX, pY, tX, tY, pWidth, pHeight, pMessage, pOnPress);
			this.perm = perm;
		}
	}

	public static class TextureOffsetButton extends SimpleButton {
		private int xLoc;
		private int yLoc;
		public TextureOffsetButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, IPressable pOnPress) {
			super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
			xLoc = tX;
			yLoc = tY;
		}

		@Override
		public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.getTextureManager().bind(guiTexture);
			int amount = (shouldOffset()? this.height+ hoverOffset() + (this.isHovered() ? hoverOffset() : 0): (this.isHovered() ? hoverOffset() : 0));
			this.blit(pMatrixStack, this.x, this.y, xLoc, yLoc+amount, this.width, this.height);
			this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);

		}
		public int hoverOffset() {
			return 0;
		}

		public boolean shouldOffset() {
			return false;
		}
	}

	public static class SimpleButton extends Button {
		public SimpleButton(int pX, int pY, int pWidth, int pHeight, ITextComponent pMessage, IPressable pOnPress) {
			super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
		}

		@Override
		public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontrenderer = minecraft.font;
			minecraft.getTextureManager().bind(WIDGETS_LOCATION);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
			int i = this.getYImage(this.isHovered());
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			this.blit(pMatrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
			this.blit(pMatrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
			this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);
			int j = getFGColor();
			drawCenteredString(pMatrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
		}
	}
}