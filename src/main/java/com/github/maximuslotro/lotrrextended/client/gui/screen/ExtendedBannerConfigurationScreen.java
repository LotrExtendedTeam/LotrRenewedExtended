package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedTextFieldWidget;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileClient;
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
import net.minecraft.util.text.TextFormatting;
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
	private float previousAlignment;
	private boolean showDefaultPerms = false;
	private List<Button> defaultPermButtons = new ArrayList<>();
	private BlockPos configurePos;
	int shownWhiteListButtonCount = 0;
	private List<ExtendedTextFieldWidget> whitelistFields = new ArrayList<>();
	private int whiteListOffset = 0;
	private Button createFellowship;
	private Button createPlayer;
	private boolean justCreatedWhitelistEntry = false;
	private boolean updateWhitelist= false;
	private boolean isEnabled;

	public ExtendedBannerConfigurationScreen(ExtendedSBannerOpenScreenPacket serverData) {
		super(new StringTextComponent("BANNERCONFIGURATION"), 200, 192);
		this.defaultPerms = serverData.defaultPerms;
		this.alignmentLevel = Math.round(serverData.alignmentLevel * 10) / 10f;
		this.previousAlignment = alignmentLevel;
		this.whitelist = serverData.whitelist;
		this.canSelfProtect = true; //serverData.canSelfProtect;
		this.protectionType = serverData.protectionType;
		this.configurePos = serverData.configurePos;
		this.isEnabled = serverData.enabled;
		this.shownWhiteListButtonCount = calcWhiteListSize();
		updateWhitelist = true;
	}

	public void updatefromPacket(ExtendedSBannerOpenChangedScreenPacket serverData) {
		needsUpdate = false;
		this.defaultPerms = serverData.defaultPerms;
		this.alignmentLevel = Math.round(serverData.alignmentLevel * 10) / 10f;
		this.previousAlignment = alignmentLevel;
		this.whitelist = serverData.whitelist;
		this.canSelfProtect = true; //serverData.canSelfProtect;
		this.protectionType = serverData.protectionType;
		this.configurePos = serverData.configurePos;
		this.isEnabled = serverData.enabled;
		handleAlignmentUpdate();
		this.shownWhiteListButtonCount = calcWhiteListSize();
		whiteListOffset = 0;
		justCreatedWhitelistEntry = false;
		updateWhitelist = true;
	}

	public BlockPos getConfigurePos() {
		return configurePos;
	}

	public int convertWhitelistOffset(int unNormalizedIndex) {
		return unNormalizedIndex+whiteListOffset;
	}

	public int calcWhiteListSize() {
		return Math.min(whitelist.size(), 4);
	}

	@Override
	public void init() {
		super.init();
		// General stuff
		this.addButton(protectionTypeButton = new SimpleButton(this.leftPos + 30, this.topPos + 20, 140, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"), button -> {
			protectionType = protectionType==0 ? 1 : 0;
			needsUpdate = true;
		}));
		protectionTypeButton.setMessage(protectionType==0 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"):new TranslationTextComponent("gui.lotrextended.banner_configuration.type_whitelist"));
		this.addButton(update = new SimpleButton(this.leftPos +this.imageWidth -115, this.topPos + this.imageHeight-25, 110, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update"), button -> {
			needsUpdate = false;
			whitelistFields.forEach((textButton) -> textButton.setFocus(false));
			if(isEnabled) {
				ExtendedPacketHandler.sendToServer(new ExtendedCBannerSaveInfoPacket(this.defaultPerms, this.alignmentLevel, this.whitelist, this.canSelfProtect, this.protectionType, this.configurePos));
			}
		}) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update.disc"), 200), pMouseX, pMouseY);
			}
		});
		update.active = false;
//		this.addButton(new TextureOffsetButton(this.leftPos +this.imageWidth -140, this.topPos + this.imageHeight-25, 80, 192, 20, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection"), button -> {
//			canSelfProtect = !canSelfProtect;
//			needsUpdate = true;
//		}){
//			@Override
//			public boolean shouldOffset() {
//				return !ExtendedBannerConfigurationScreen.this.canSelfProtect;
//			}
//			@Override
//			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
//				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection"+(ExtendedBannerConfigurationScreen.this.canSelfProtect ? ".on" : ".off")), 200), pMouseX, pMouseY);
//			}
//		});
		this.addButton(new TextureOffsetButton(this.leftPos + 7, this.topPos + this.imageHeight-25, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), button -> {
			showDefaultPerms = !showDefaultPerms;
		}){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.showDefaultPerms;
			}
			@Override
			public boolean shouldDoDiffrentOffset() {
				return !defaultPerms.isEmpty();
			}
			@Override
			public int additionalOffset() {
				return 10;
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
			TextureOffsetButton permButton = new PermissionButton(leftLoc, upLoc, 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, -1, button -> {
				BannerPermission tweaking = ((PermissionButton)button).perm;
				if(defaultPerms.contains(tweaking)) {
					defaultPerms.remove(tweaking);
				}else {
					if(tweaking.equals(BannerPermission.EVERYTHING)) {
						defaultPerms.removeIf((bannerPerm) -> !bannerPerm.equals(BannerPermission.BANNER));
					}else if(!tweaking.equals(BannerPermission.EVERYTHING) && !tweaking.equals(BannerPermission.BANNER)){
						defaultPerms.remove(BannerPermission.EVERYTHING);
					}
					defaultPerms.add(tweaking);
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
					ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)+".disc").withStyle(perm==BannerPermission.BANNER ? TextFormatting.RED : TextFormatting.WHITE), 200), pMouseX, pMouseY);
				}
			};
			permButton.visible = showDefaultPerms;
			defaultPermButtons.add(permButton);
			this.addButton(permButton);
		}
		// Faction
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
		// Whitelist
		for(int i=0; i<4; i++) {
			ExtendedTextFieldWidget textField =  new ExtendedTextFieldWidget(this.font, this.leftPos+32, this.topPos+51 + (i*30), 97, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.textBox"), i) {
				@Override
				public void setFocus(boolean pIsFocused) {
					super.setFocus(pIsFocused);
					if(!pIsFocused) {
						ExtendedBannerConfigurationScreen.this.handleWhitelistUpdate(this.id);
					}
				}
			};
			textField.setBordered(false);
			textField.setValue("");
			textField.setFocus(false);
			textField.setCanLoseFocus(true);

			IndexedButton permShowButton = new IndexedButton(this.leftPos + 29, this.topPos + 63 + (i*30), 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), i, button -> {
				textField.togglePermVis();
			}){
				@Override
				public boolean shouldOffset() {
					return ExtendedBannerConfigurationScreen.this.whitelistFields.get(this.id).showPerms;
				}
				@Override
				public boolean shouldDoDiffrentOffset() {
					if(ExtendedBannerConfigurationScreen.this.whitelist.size()> convertWhitelistOffset(this.id)) {
						return !(ExtendedBannerConfigurationScreen.this.whitelist.get(convertWhitelistOffset(this.id)).getEntryPerms().size()==1 && ExtendedBannerConfigurationScreen.this.whitelist.get(convertWhitelistOffset(this.id)).getEntryPerms().contains(BannerPermission.EVERYTHING));
					}
					return false;
				}
				@Override
				public int additionalOffset() {
					return 10;
				}
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms.disc"), 200), pMouseX, pMouseY);
				}
			};
			textField.permShowButton = permShowButton;
			this.addButton(permShowButton);
			IndexedButton deleteButton = new IndexedButton(this.leftPos + 136, this.topPos + 50 + (i*30), 190, 232, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.delete"), i, button -> {
				if(ExtendedBannerConfigurationScreen.this.whitelist.size()> convertWhitelistOffset(((IndexedButton)button).id)) {
					ExtendedBannerConfigurationScreen.this.whitelist.remove(convertWhitelistOffset(((IndexedButton)button).id));
					needsUpdate = true;
				}
			}){
				@Override
				public int hoverOffset() {
					return 10;
				}
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.delete.disc"), 200), pMouseX, pMouseY);
				}
			};
			textField.deleteButton = deleteButton;
			this.addButton(deleteButton);
			for(BannerPermission perm : BannerPermission.values()) {
				int leftLoc = this.leftPos + 41;
				if(perm.ordinal()>0) {
					leftLoc += 12;
				}
				if(perm.ordinal()>1 ) {
					leftLoc += (perm.ordinal()-1) * 10;
				}
				PermissionButton permButton = new PermissionButton(leftLoc, this.topPos + 63 + (i*30), 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, i, button -> {
					if(whitelist.size()>convertWhitelistOffset(((PermissionButton)button).parentID)) {
						BannerPermission tweaking = ((PermissionButton)button).perm;
						int buttonIndex = convertWhitelistOffset(((PermissionButton)button).parentID);
						if(whitelist.get(buttonIndex).getEntryPerms().contains(tweaking)) {
							whitelist.get(buttonIndex).getEntryPerms().remove(tweaking);
						}else {
							if(tweaking.equals(BannerPermission.EVERYTHING)) {
								whitelist.get(buttonIndex).getEntryPerms().removeIf((bannerPerm) -> !bannerPerm.equals(BannerPermission.BANNER));
							}else if(!tweaking.equals(BannerPermission.EVERYTHING) && !tweaking.equals(BannerPermission.BANNER)){
								whitelist.get(buttonIndex).getEntryPerms().remove(BannerPermission.EVERYTHING);
							}
							whitelist.get(buttonIndex).getEntryPerms().add(tweaking);
						}
						needsUpdate = true;
					}
				}){
					@Override
					public boolean shouldOffset() {
						if(ExtendedBannerConfigurationScreen.this.whitelist.size()> convertWhitelistOffset(this.parentID)) {
							return ExtendedBannerConfigurationScreen.this.whitelist.get(convertWhitelistOffset(this.parentID)).getEntryPerms().contains((this.perm));
						}
						return false;
					}
					@Override
					public int hoverOffset() {
						return 10;
					}
					@Override
					public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
						ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)+".disc").withStyle(perm==BannerPermission.BANNER ? TextFormatting.RED : TextFormatting.WHITE), 200), pMouseX, pMouseY);
					}
				};
				permButton.visible = textField.showPerms;
				textField.permButtons.add(permButton);
				this.addButton(permButton);
			}
			this.whitelistFields.add(textField);
			this.addButton(textField);
		}
		this.addButton(createPlayer = new SimpleButton(this.leftPos + 29, this.topPos + 46, 64, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.add_player"), button -> {
			this.whitelist.add(new ProtectionEntry(new ExtendedBannerProfileClient("")).addPerm(BannerPermission.EVERYTHING));
			justCreatedWhitelistEntry=true;
			needsUpdate = true;
		}));
		this.addButton(createFellowship = new SimpleButton(this.leftPos + 92, this.topPos + 46, 79, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.add_fellowship"), button -> {
			this.whitelist.add(new ProtectionEntry(new ExtendedBannerProfileClient("f/")).addPerm(BannerPermission.EVERYTHING));
			justCreatedWhitelistEntry=true;
			needsUpdate = true;
		}));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		this.minecraft.getTextureManager().bind(guiTexture);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		TranslationTextComponent title = new TranslationTextComponent("gui.lotrextended.banner_configuration.title");
		this.font.draw(matStack, title, leftPos + (this.imageWidth/2) - (font.width(title)/2), topPos + 7, 4210752);
		if(!isEnabled) {
			alignmentField.visible=false;
			protectionTypeButton.visible=false;
			update.visible=false;
			whitelistFields.forEach((textField -> {textField.isVisable(false); textField.isPermVisable(false);}));
			defaultPermButtons.forEach((permButton) -> permButton.visible=false);
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.disabled"), leftPos + 25, topPos + 64, 16711680);
			return;
		}
		if(protectionType==0) {
			alignmentField.visible=true;
			createPlayer.visible = false;
			createFellowship.visible = false;
			whitelistFields.forEach((textField -> {textField.isVisable(false); textField.isPermVisable(false);}));
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.faction.requires"), leftPos + 61, topPos + 64, 4210752);
			this.minecraft.getTextureManager().bind(guiTexture);
			blit(matStack, this.leftPos+60, this.topPos+76, 0, 192, 80, 17);
			alignmentField.render(matStack, mouseX, mouseY, tick);
		}else {
			shownWhiteListButtonCount = calcWhiteListSize();
			alignmentField.visible=false;
			this.minecraft.getTextureManager().bind(guiTexture);
			for(int i=0;i<shownWhiteListButtonCount; i++) {
				blit(matStack, this.leftPos+29, this.topPos+46 + (i*30), 0, 192, 80, 17);
				blit(matStack, this.leftPos+55, this.topPos+46 + (i*30), 1, 192, 79, 17);
				ExtendedTextFieldWidget textField = whitelistFields.get(i);
				textField.setTextColor(whitelist.get(convertWhitelistOffset(i)).getProfile() instanceof ExtendedBannerProfileClient ? 14737632 : 65313);
				textField.isVisable(true);
				//textField.render(matStack, mouseX, mouseY, tick);
				textField.isPermVisable(textField.showPerms);
				if(this.updateWhitelist) {
					textField.setValue(whitelist.get(convertWhitelistOffset(i)).getProfile().getDisplayName());
				}
				if(justCreatedWhitelistEntry &&  i+1==shownWhiteListButtonCount) {
					textField.setValue(whitelist.get(convertWhitelistOffset(i)).getProfile().getDisplayName());
					textField.setEditable(true);
					justCreatedWhitelistEntry = false;
				}
			}
			updateWhitelist = false;
			for(int i=shownWhiteListButtonCount;i<4; i++) {
				whitelistFields.get(i).isVisable(false);
				whitelistFields.get(i).isPermVisable(false);
			}
			if(shownWhiteListButtonCount<4) {
				createPlayer.visible = true;
				createFellowship.visible = true;
				createPlayer.y = this.topPos+46 + (shownWhiteListButtonCount*30);
				createFellowship.y = this.topPos+46 + (shownWhiteListButtonCount*30);
			}else {
				createPlayer.visible = false;
				createFellowship.visible = false;
			}
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
		whitelistFields.forEach((textField) -> textField.tick());
		shownWhiteListButtonCount = calcWhiteListSize();
	}

	@Override
	public void onClose() {
		super.onClose();
		if(isEnabled) {
			ExtendedPacketHandler.sendToServer(new ExtendedCBannerSaveInfoPacket(this.defaultPerms, this.alignmentLevel, this.whitelist, this.canSelfProtect, this.protectionType, this.configurePos));
		}
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

	public void handleWhitelistUpdate(int id) {
		Minecraft mc =Minecraft.getInstance();
		if(whitelist.size()>convertWhitelistOffset(id) && whitelistFields.size()==4) {
			if(!whitelist.get(convertWhitelistOffset(id)).getProfile().getDisplayName().equals(this.whitelistFields.get(id).getValue())) {
				if(whitelist.get(convertWhitelistOffset(id)).getProfile() instanceof ExtendedBannerProfileClient) {
					((ExtendedBannerProfileClient)whitelist.get(convertWhitelistOffset(id)).getProfile()).setName(this.whitelistFields.get(id).getValue());
				}
				else {
					String newName = this.whitelistFields.get(id).getValue();
					ProtectionEntry entity = new ProtectionEntry(new ExtendedBannerProfileClient(newName));
					entity.setPerms(whitelist.get(convertWhitelistOffset(id)).getEntryPerms());
					whitelist.set(convertWhitelistOffset(id), entity);
				}
				this.needsUpdate = true;
			}
		}
	}

	public static class PermissionButton extends TextureOffsetButton {
		public BannerPermission perm;
		public int parentID;
		public PermissionButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, BannerPermission perm, int id, IPressable pOnPress) {
			super(pX, pY, tX, tY, pWidth, pHeight, pMessage, pOnPress);
			this.perm = perm;
			this.parentID = id;
		}
	}

	public static class IndexedButton extends TextureOffsetButton {
		public int id;
		public IndexedButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, int id, IPressable pOnPress) {
			super(pX, pY, tX, tY, pWidth, pHeight, pMessage, pOnPress);
			this.id = id;
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
			int amount = shouldOffset() ? this.height + hoverOffset() + (this.isHovered() ? hoverOffset() : 0): (this.isHovered() ? hoverOffset() : 0);
			if(shouldDoDiffrentOffset()){
				amount = this.height + additionalOffset() + (this.shouldOffset() ? additionalOffset() : 0);
			}
			this.blit(pMatrixStack, this.x, this.y, xLoc, yLoc+amount, this.width, this.height);
			this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);

		}

		public int hoverOffset() {
			return 0;
		}

		public boolean shouldOffset() {
			return false;
		}

		public boolean shouldDoDiffrentOffset() {
			return false;
		}

		public int additionalOffset() {
			return 0;
		}
	}

	public static class SimpleButton extends Button {
		public SimpleButton(int pX, int pY, int pWidth, int pHeight, ITextComponent pMessage, IPressable pOnPress) {
			super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
		}

		@SuppressWarnings("deprecation")
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