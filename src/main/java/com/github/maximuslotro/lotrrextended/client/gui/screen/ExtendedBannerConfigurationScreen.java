package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedBannerTextFieldPermissionWidget;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedSimpleButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTextureOffsetButton;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileClient;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCBannerSaveInfoPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenChangedScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenScreenPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.fac.Faction;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedBannerConfigurationScreen extends ExtendedAbstractWindowedScreen {
	private static final ResourceLocation guiTexture = new ResourceLocation("lotrextended:textures/gui/banner_gui.png");
	private static final TranslationTextComponent TITLE = new TranslationTextComponent("gui.lotrextended.banner_configuration.title");
	private List<ExtendedBannerPermissions> globalPerms;
	private List<ExtendedBannerPermissions> factionPerms;
	private float alignmentLevel;
	private List<ProtectionEntry> whitelist;
	private boolean canSelfProtect;
	// 0 Faction, 1 Player, 2 None, 3 Server
	private int protectionType;
	private boolean needsUpdate = false;
	private Button protectionTypeButton;
	private Button update;
	/** Alignment entry field */
	private TextFieldWidget alignmentField;
	/** Custom message entry field */
	private TextFieldWidget messageField;
	private float previousAlignment;
	private boolean showDefaultPerms = false;
	private boolean showFactionPerms = false;
	private List<Button> globalPermButtons = new ArrayList<>();
	private List<Button> factionPermButtons = new ArrayList<>();
	private BlockPos configurePos;
	private int shownWhiteListButtonCount = 0;
	private List<ExtendedBannerTextFieldPermissionWidget> whitelistFields = new ArrayList<>();
	private int whiteListOffset = 0;
	private Button createFellowship;
	private Button createPlayer;
	private Button factionPermButton;
	private boolean justCreatedWhitelistEntry = false;
	private boolean updateWhitelist= false;
	private boolean isEnabled;
	private boolean isClientOP;
	private String protMessage;
	private ResourceLocation flagLoc = null;
	private TranslationTextComponent factionMessage = null;
	private String playerName;
	private Button playerInfo;

	public ExtendedBannerConfigurationScreen(ExtendedSBannerOpenScreenPacket serverData) {
		super(TITLE, 200, 192);
		this.globalPerms = serverData.globalPerms;
		this.factionPerms = serverData.factionPerms;
		this.alignmentLevel = Math.round(serverData.alignmentLevel * 10) / 10f;
		this.previousAlignment = alignmentLevel;
		this.whitelist = serverData.whitelist;
		this.canSelfProtect = serverData.canSelfProtect;
		this.protectionType = serverData.protectionType;
		this.configurePos = serverData.configurePos;
		this.isEnabled = serverData.enabled;
		this.shownWhiteListButtonCount = calcWhiteListSize();
		updateWhitelist = true;
		this.isClientOP = serverData.isClientOP;
		this.protMessage = serverData.protMessage;
		this.playerName = serverData.bannerPlacer;
	}

	public void updateFromPacket(ExtendedSBannerOpenChangedScreenPacket serverData) {
		needsUpdate = false;
		this.globalPerms = serverData.globalPerms;
		this.factionPerms = serverData.factionPerms;
		this.alignmentLevel = Math.round(serverData.alignmentLevel * 10) / 10f;
		this.previousAlignment = alignmentLevel;
		this.whitelist = serverData.whitelist;
		this.canSelfProtect = serverData.canSelfProtect;
		this.protectionType = serverData.protectionType;
		this.configurePos = serverData.configurePos;
		this.isEnabled = serverData.enabled;
		this.protMessage = serverData.protMessage;
		handleAlignmentUpdate();
		this.shownWhiteListButtonCount = calcWhiteListSize();
		whiteListOffset = 0;
		justCreatedWhitelistEntry = false;
		updateWhitelist = true;
		this.playerName = serverData.bannerPlacer;
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
		playerInfo = this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 184, this.topPos + 6, 80, 232, 10, 10, new StringTextComponent("PlayerName"), button -> {}, guiTexture){
			@Override
			public int hoverOffset() {
				return 10;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.player.tooltip", playerName), 200), pMouseX, pMouseY);
			}
		});	
		// General stuff
		this.addButton(protectionTypeButton = new ExtendedSimpleButton(this.leftPos + 30, this.topPos + 20, 140, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"), button -> {
			rotatePermissionType();
			needsUpdate = true;
		}));
		protectionTypeButton.setMessage(
			protectionType==0 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"):
				protectionType==1 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_whitelist"): 
					new TranslationTextComponent("gui.lotrextended.banner_configuration.type_custom"));
		this.addButton(update = new ExtendedSimpleButton(this.leftPos + 85, this.topPos + this.imageHeight-25, 110, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update"), button -> {
			needsUpdate = false;
			whitelistFields.forEach((textButton) -> textButton.setFocus(false));
			if(isEnabled) {
				ExtendedPacketHandler.sendToServer(new ExtendedCBannerSaveInfoPacket(this.globalPerms, this.factionPerms, this.alignmentLevel, this.whitelist, this.canSelfProtect, this.protectionType, this.configurePos, protMessage));
			}
		}) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update.disc"), 200), pMouseX, pMouseY);
			}
		});
		update.active = false;
		this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 62, this.topPos + this.imageHeight-25, 80, 192, 20, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection"), button -> {
			canSelfProtect = !canSelfProtect;
			needsUpdate = true;
		}, guiTexture){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.canSelfProtect;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				List<IReorderingProcessor> lines = font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection.setting")
					.append(new TranslationTextComponent(ExtendedBannerConfigurationScreen.this.canSelfProtect ? "gui.lotrextended.base.on" : "gui.lotrextended.base.off")).withStyle(ExtendedBannerConfigurationScreen.this.canSelfProtect ? TextFormatting.GREEN : TextFormatting.RED)
					.append(new StringTextComponent("\n")).append(new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection.disc").withStyle(TextFormatting.RED)), 200);
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, lines, pMouseX, pMouseY);
			}
		});
		this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 7, this.topPos + this.imageHeight-25, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms.global"), button -> showDefaultPerms = !showDefaultPerms, guiTexture){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.showDefaultPerms;
			}
			@Override
			public boolean shouldDoDifferentOffset() {
				return !globalPerms.isEmpty();
			}
			@Override
			public int additionalOffset() {
				return 10;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				List<IReorderingProcessor> lines = font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms.global").withStyle(TextFormatting.YELLOW)
					.append(new StringTextComponent("\n")).append(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms.global.disc").withStyle(TextFormatting.RED)), 200);
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, lines, pMouseX, pMouseY);
			}
		});
		for(ExtendedBannerPermissions perm : ExtendedBannerPermissions.values()) {
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
			ExtendedTextureOffsetButton permButton = new PermissionButton(leftLoc, upLoc, 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, -1, button -> {
				ExtendedBannerPermissions tweaking = ((PermissionButton)button).perm;
				if(globalPerms.contains(tweaking)) {
					globalPerms.remove(tweaking);
				}else {
					if(tweaking.equals(ExtendedBannerPermissions.EVERYTHING)) {
						globalPerms.removeIf((bannerPerm) -> !bannerPerm.equals(ExtendedBannerPermissions.BANNER));
					}else if(!tweaking.equals(ExtendedBannerPermissions.EVERYTHING) && !tweaking.equals(ExtendedBannerPermissions.BANNER)){
						globalPerms.remove(ExtendedBannerPermissions.EVERYTHING);
					}
					globalPerms.add(tweaking);
				}
				needsUpdate = true;
			}){
				@Override
				public boolean shouldOffset() {
					return ExtendedBannerConfigurationScreen.this.globalPerms.contains((this.perm));
				}
				@Override
				public int hoverOffset() {
					return 10;
				}
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)+".disc").withStyle(perm==ExtendedBannerPermissions.BANNER ? TextFormatting.RED : (perm==ExtendedBannerPermissions.EVERYTHING ? TextFormatting.YELLOW : TextFormatting.WHITE)), 200), pMouseX, pMouseY);
				}
			};
			permButton.visible = showDefaultPerms;
			globalPermButtons.add(permButton);
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
		this.addButton(factionPermButton = new ExtendedTextureOffsetButton(this.leftPos + 145, this.topPos + 80, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), button -> showFactionPerms = !showFactionPerms, guiTexture){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.showFactionPerms;
			}
			@Override
			public boolean shouldDoDifferentOffset() {
				return !factionPerms.isEmpty();
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
		for(ExtendedBannerPermissions perm : ExtendedBannerPermissions.values()) {
			if(perm==ExtendedBannerPermissions.BANNER) {
				continue;
			}
			int leftLoc = this.leftPos + 43 + (perm.ordinal() * 15);

			int upLoc = this.topPos + 100;
			ExtendedTextureOffsetButton permButton = new PermissionButton(leftLoc, upLoc, 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, -1, button -> {
				ExtendedBannerPermissions tweaking = ((PermissionButton)button).perm;
				if(factionPerms.contains(tweaking)) {
					factionPerms.remove(tweaking);
					if(factionPerms.isEmpty()) {
						factionPerms.add(ExtendedBannerPermissions.EVERYTHING);
					}
				}else {
					if(tweaking.equals(ExtendedBannerPermissions.EVERYTHING)) {
						factionPerms.clear();
					}else if(!tweaking.equals(ExtendedBannerPermissions.EVERYTHING)){
						factionPerms.remove(ExtendedBannerPermissions.EVERYTHING);
					}
					factionPerms.add(tweaking);
				}
				needsUpdate = true;
			}){
				@Override
				public boolean shouldOffset() {
					return ExtendedBannerConfigurationScreen.this.factionPerms.contains((this.perm));
				}
				@Override
				public int hoverOffset() {
					return 10;
				}
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)+".disc").withStyle(perm==ExtendedBannerPermissions.BANNER ? TextFormatting.RED : (perm==ExtendedBannerPermissions.EVERYTHING ? TextFormatting.YELLOW : TextFormatting.WHITE)), 200), pMouseX, pMouseY);
				}
			};
			permButton.visible = showFactionPerms;
			factionPermButtons.add(permButton);
			this.addButton(permButton);
		}
		// Custom
		this.addButton(messageField = new TextFieldWidget(this.font, this.leftPos+33, this.topPos+81, 145, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.custom.textBox")) {
			@Override
			public void setFocus(boolean pIsFocused) {
				super.setFocus(pIsFocused);
				if(!pIsFocused) {
					ExtendedBannerConfigurationScreen.this.handleMessageUpdate();
				}
			}
		});
		this.messageField.setMaxLength(24);
		this.messageField.setBordered(false);
		if(this.protMessage.isEmpty()) {
			this.messageField.setValue("The Server");
		}else {
			this.messageField.setValue(this.protMessage);
		}
		this.messageField.setFocus(false);
		this.messageField.setCanLoseFocus(true);
		// Whitelist
		whitelistFields.clear();
		for(int i=0; i<4; i++) {
			ExtendedBannerTextFieldPermissionWidget textField =  new ExtendedBannerTextFieldPermissionWidget(this.font, this.leftPos+32, this.topPos+51 + (i*30), 97, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.textBox"), i) {
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

			IndexedButton permShowButton = new IndexedButton(this.leftPos + 29, this.topPos + 63 + (i*30), 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), i, button -> textField.togglePermVis()){
				@Override
				public boolean shouldOffset() {
					return ExtendedBannerConfigurationScreen.this.whitelistFields.get(this.id).showPerms;
				}
				@Override
				public boolean shouldDoDifferentOffset() {
					if(ExtendedBannerConfigurationScreen.this.whitelist.size()> convertWhitelistOffset(this.id)) {
						return !(ExtendedBannerConfigurationScreen.this.whitelist.get(convertWhitelistOffset(this.id)).getEntryPerms().size()==1 && ExtendedBannerConfigurationScreen.this.whitelist.get(convertWhitelistOffset(this.id)).getEntryPerms().contains(ExtendedBannerPermissions.EVERYTHING));
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
			for(ExtendedBannerPermissions perm : ExtendedBannerPermissions.values()) {
				int leftLoc = this.leftPos + 41;
				if(perm.ordinal()>0) {
					leftLoc += 12;
				}
				if(perm.ordinal()>1 ) {
					leftLoc += (perm.ordinal()-1) * 10;
				}
				PermissionButton permButton = new PermissionButton(leftLoc, this.topPos + 63 + (i*30), 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, i, button -> {
					if(whitelist.size()>convertWhitelistOffset(((PermissionButton)button).parentID)) {
						ExtendedBannerPermissions tweaking = ((PermissionButton)button).perm;
						int buttonIndex = convertWhitelistOffset(((PermissionButton)button).parentID);
						if(whitelist.get(buttonIndex).getEntryPerms().contains(tweaking)) {
							whitelist.get(buttonIndex).getEntryPerms().remove(tweaking);
						}else {
							if(tweaking.equals(ExtendedBannerPermissions.EVERYTHING)) {
								whitelist.get(buttonIndex).getEntryPerms().removeIf((bannerPerm) -> !bannerPerm.equals(ExtendedBannerPermissions.BANNER));
							}else if(!tweaking.equals(ExtendedBannerPermissions.EVERYTHING) && !tweaking.equals(ExtendedBannerPermissions.BANNER)){
								whitelist.get(buttonIndex).getEntryPerms().remove(ExtendedBannerPermissions.EVERYTHING);
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
						ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)+".disc").withStyle(perm==ExtendedBannerPermissions.BANNER ? TextFormatting.RED : (perm==ExtendedBannerPermissions.EVERYTHING ? TextFormatting.YELLOW : TextFormatting.WHITE)), 200), pMouseX, pMouseY);
					}
				};
				permButton.visible = textField.showPerms;
				textField.permButtons.add(permButton);
				this.addButton(permButton);
			}
			this.whitelistFields.add(textField);
			this.addButton(textField);
			updateWhitelist = true;
		}
		this.addButton(createPlayer = new ExtendedSimpleButton(this.leftPos + 29, this.topPos + 46, 64, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.add_player"), button -> {
			this.whitelist.add(new ProtectionEntry(new ExtendedBannerProfileClient("")).addPerm(ExtendedBannerPermissions.EVERYTHING));
			justCreatedWhitelistEntry=true;
			needsUpdate = true;
		}));
		this.addButton(createFellowship = new ExtendedSimpleButton(this.leftPos + 92, this.topPos + 46, 79, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.add_fellowship"), button -> {
			this.whitelist.add(new ProtectionEntry(new ExtendedBannerProfileClient("f/")).addPerm(ExtendedBannerPermissions.EVERYTHING));
			justCreatedWhitelistEntry=true;
			needsUpdate = true;
		}));
	}

	private void rotatePermissionType() {
		if(protectionType==0) {
			protectionType=1;
		}else if(protectionType==1 && !isClientOP) {
			protectionType=0;
		}else if(protectionType==1 && isClientOP) {
			protectionType=3;
		}else if(protectionType==3) {
			protectionType=0;
		}
	}

	private ResourceLocation getFlagLoc() {
		if(flagLoc==null) {
			if(this.minecraft==null || this.minecraft.level==null || this.minecraft.level.getBlockEntity(configurePos)==null) {
				return null;
			}else {
				TileEntity blockEntity = this.minecraft.level.getBlockEntity(configurePos);
				Block state = blockEntity.getBlockState().getBlock();
				// TODO make namespace dynamic (change when we move our banner blocks to our namespace)
				flagLoc = new ResourceLocation("lotrextended", "textures/block/banner/"+state.getRegistryName().getPath()+".png");
			}
		}
		return flagLoc;
	}

	private TranslationTextComponent getFactionMessage() {
		if(factionMessage==null) {
			if(this.minecraft==null || this.minecraft.level==null) {
				return null;
			}else {
				TileEntity blockEntity = this.minecraft.level.getBlockEntity(configurePos);
				if(blockEntity instanceof ExtendedMEBannerTileEntity) {
					Optional<Faction> resolvedFaction = ((ExtendedMEBannerTileEntity)blockEntity).getFactionProtcting().resolveFaction(this.minecraft.level);
					if(resolvedFaction.isPresent()) {
						factionMessage = new TranslationTextComponent("gui.lotrextended.banner_configuration.faction.aligned", resolvedFaction.get().getColoredDisplayName());
					}
				}
			}
		}
		return factionMessage;
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		this.minecraft.getTextureManager().bind(guiTexture);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.font.draw(matStack, title, leftPos + (this.imageWidth/2) - (font.width(title)/2), topPos + 7, 4210752);
		if(!isEnabled) {
			alignmentField.visible=false;
			factionPermButton.visible = false;
			messageField.visible=false;
			protectionTypeButton.visible=false;
			update.visible=false;
			whitelistFields.forEach((textField -> {textField.isVisible(false); textField.isPermVisible(false);}));
			globalPermButtons.forEach((permButton) -> permButton.visible=false);
			showFactionPerms=false;
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.disabled"), leftPos + 25, topPos + 64, 16711680);
			playerInfo.visible=false;
			return;
		}
		playerInfo.visible=true;
		if(protectionType==0) {
			//Faction
			alignmentField.visible=true;
			factionPermButton.visible = true;
			messageField.visible=false;
			createPlayer.visible = false;
			createFellowship.visible = false;
			whitelistFields.forEach((textField -> {textField.isVisible(false); textField.isPermVisible(false);}));
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.faction.requires"), leftPos + 61, topPos + 64, 4210752);
			if(this.getFactionMessage()!=null) {
				this.font.draw(matStack, this.getFactionMessage(), leftPos + (this.imageWidth/2) - (this.font.width(this.getFactionMessage())/2), topPos + 45, 4210752);
			}
			if(this.getFlagLoc()!=null) {
				this.minecraft.getTextureManager().bind(this.getFlagLoc());
				blit(matStack, this.leftPos-90, this.topPos + (this.imageHeight/2) - 74, 0, 0, 80, 148);
			}
			this.minecraft.getTextureManager().bind(guiTexture);
			blit(matStack, this.leftPos+60, this.topPos+76, 0, 192, 80, 17);
			// Permissions box
			if(this.showFactionPerms) {
				blit(matStack, this.leftPos+40, this.topPos+97, 0, 192, 79, 12);
				blit(matStack, this.leftPos+40, this.topPos+109, 0, 205, 79, 4);
				blit(matStack, this.leftPos+119, this.topPos+97, 38, 192, 42, 12);
				blit(matStack, this.leftPos+119, this.topPos+109, 38, 205, 42, 4);
			}
			alignmentField.render(matStack, mouseX, mouseY, tick);
		}else if(protectionType==1) {
			//Whitelist
			shownWhiteListButtonCount = calcWhiteListSize();
			alignmentField.visible=false;
			factionPermButton.visible = false;
			showFactionPerms=false;
			messageField.visible=false;
			this.minecraft.getTextureManager().bind(guiTexture);
			for(int i=0;i<shownWhiteListButtonCount; i++) {
				blit(matStack, this.leftPos+29, this.topPos+46 + (i*30), 0, 192, 80, 17);
				blit(matStack, this.leftPos+55, this.topPos+46 + (i*30), 1, 192, 79, 17);
				ExtendedBannerTextFieldPermissionWidget textField = whitelistFields.get(i);
				textField.setTextColor(whitelist.get(convertWhitelistOffset(i)).getProfile() instanceof ExtendedBannerProfileClient ? 14737632 : 65313);
				textField.isVisible(true);
				//textField.render(matStack, mouseX, mouseY, tick);
				textField.isPermVisible(textField.showPerms);
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
				whitelistFields.get(i).isVisible(false);
				whitelistFields.get(i).isPermVisible(false);
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
		}else {
			//Custom
			messageField.visible=true;
			alignmentField.visible=false;
			factionPermButton.visible = false;
			showFactionPerms=false;
			createPlayer.visible = false;
			createFellowship.visible = false;
			whitelistFields.forEach((textField -> {textField.isVisible(false); textField.isPermVisible(false);}));
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.custom.description"), leftPos + 31, topPos + 64, 4210752);
			this.minecraft.getTextureManager().bind(guiTexture);
			blit(matStack, this.leftPos+30, this.topPos+76, 0, 192, 79, 17);
			blit(matStack, this.leftPos+100, this.topPos+76, 1, 192, 79, 17);
			messageField.render(matStack, mouseX, mouseY, tick);
		}
		for(Button button : globalPermButtons) {
			button.visible = showDefaultPerms;
		}
		for(Button button : factionPermButtons) {
			button.visible = showFactionPerms;
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	@Override
	public void tick() {
		super.tick();
		alignmentField.tick();
		messageField.tick();
		protectionTypeButton.setMessage(
			protectionType==0 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"):
				protectionType==1 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_whitelist"): 
					new TranslationTextComponent("gui.lotrextended.banner_configuration.type_custom"));
		update.active = needsUpdate;
		whitelistFields.forEach(TextFieldWidget::tick);
		shownWhiteListButtonCount = calcWhiteListSize();
	}

	@Override
	public void onClose() {
		super.onClose();
		handleMessageUpdate();
		handleAlignmentUpdate();
		if(isEnabled) {
			ExtendedPacketHandler.sendToServer(new ExtendedCBannerSaveInfoPacket(this.globalPerms, this.factionPerms, this.alignmentLevel, this.whitelist, this.canSelfProtect, this.protectionType, this.configurePos, this.protMessage));
		}
	}
	public void handleMessageUpdate() {
		String newValue = messageField.getValue();
		if(newValue!=protMessage) {
			this.needsUpdate = true;
		}
		this.protMessage = newValue;
	}
	public void handleAlignmentUpdate() {
		String newValue = alignmentField.getValue();
		if(newValue.startsWith("+")) {
			newValue=newValue.substring(1);
		}
		newValue=newValue.trim();
		try {
			alignmentLevel = Float.parseFloat(newValue);
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

	public static class PermissionButton extends ExtendedTextureOffsetButton {
		public ExtendedBannerPermissions perm;
		public int parentID;
		public PermissionButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, ExtendedBannerPermissions perm, int id, IPressable pOnPress) {
			super(pX, pY, tX, tY, pWidth, pHeight, pMessage, pOnPress, guiTexture);
			this.perm = perm;
			this.parentID = id;
		}
	}

	public static class IndexedButton extends ExtendedTextureOffsetButton {
		public int id;
		public IndexedButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, int id, IPressable pOnPress) {
			super(pX, pY, tX, tY, pWidth, pHeight, pMessage, pOnPress, guiTexture);
			this.id = id;
		}
	}
}