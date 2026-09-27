package com.github.maximuslotro.lotrrextended.client.gui.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedAbstractWindowedScreen;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedSimpleButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTextureOffsetButton;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileClient;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCBannerSaveInfoPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerScreenPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.fac.Faction;
import net.minecraft.block.Block;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedBannerConfigurationScreen extends ExtendedAbstractWindowedScreen {
	protected static final ResourceLocation GUI_TEXTURE = new ResourceLocation("lotrextended:textures/gui/banner_gui.png");
	private static final TranslationTextComponent TITLE = new TranslationTextComponent("gui.lotrextended.banner_configuration.title");
	protected final static int SIZE = 512;
	private ExtendedBannerConfigData serverData;
	private ExtendedBannerConfigData editableData;
	private boolean dataChanged = false;
	private Button protectionType;
	private Button syncChanges;
	private TextFieldWidget alignmentField;
	private TextFieldWidget messageField;
	private boolean showDefaultPerms = false;
	private List<PermissionButton> globalPermButtons = new ArrayList<>();
	private boolean showFactionPerms = false;
	private List<PermissionButton> factionPermButtons = new ArrayList<>();
	private BlockPos configurePos;
	private Button createFellowship;
	private Button createPlayer;
	private Button factionPermButton;
	private boolean isEnabled;
	private boolean isClientOP;
	private ResourceLocation flagLoc = null;
	private TranslationTextComponent factionMessage = null;
	private String playerName;
	private Button playerInfo;
	private ExtendedProtectionList protectionWhitelist;


	public ExtendedBannerConfigurationScreen(ExtendedSBannerScreenPacket serverPacket) {
		super(new StringTextComponent("BANNERCONFIGURATION"), 200, 192);
		this.serverData = new ExtendedBannerConfigData(serverPacket);
		this.editableData = new ExtendedBannerConfigData(serverPacket);
		this.configurePos = serverPacket.configurePos;
		this.isEnabled = serverPacket.enabled;
		this.isClientOP = serverPacket.isClientOP;
		this.playerName = serverPacket.bannerPlacer;
	}

	public void updateFromPacket(ExtendedSBannerScreenPacket serverPacket) {
		this.serverData = new ExtendedBannerConfigData(serverPacket);
		this.editableData = new ExtendedBannerConfigData(serverPacket);
		handleAlignmentUpdate();
		this.protectionWhitelist.updateProtections(editableData.whitelist, protectionWhitelist.getScrollAmount());
		this.configurePos = serverPacket.configurePos;
		this.isEnabled = serverPacket.enabled;
		this.playerName = serverPacket.bannerPlacer;
	}

	public void checkIfModified() {
		this.dataChanged = this.editableData.differsFrom(this.serverData);
	}

	public BlockPos getConfigurePos() {
		return configurePos;
	}

	@Override
	public void init() {
		super.init();
		playerInfo = this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 184, this.topPos + 6, 80, 232, 10, 10, new StringTextComponent("PlayerName"), button -> {}, 
			GUI_TEXTURE, SIZE, SIZE){
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
		this.addButton(protectionType = new ExtendedSimpleButton(this.leftPos + 30, this.topPos + 20, 140, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"), button -> {
			rotatePermissionType();
		}));
		protectionType.setMessage(
				editableData.protectionType==0 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"):
					editableData.protectionType==1 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_whitelist"): 
						new TranslationTextComponent("gui.lotrextended.banner_configuration.type_custom"));
		this.addButton(syncChanges = new ExtendedSimpleButton(this.leftPos + 85, this.topPos + 167, 110, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update"), button -> {
			if(isEnabled) {
				sendPacket();
			}
		}) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.send_update.disc"), 200), pMouseX, pMouseY);
			}
		});
		syncChanges.active = false;
		this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 62, this.topPos + 167, 80, 192, 20, 20, new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection"), button -> {
			editableData.canSelfProtect = !editableData.canSelfProtect;
		}, GUI_TEXTURE, SIZE, SIZE){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.editableData.canSelfProtect;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				List<IReorderingProcessor> lines = font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection.setting")
						.append(new TranslationTextComponent(ExtendedBannerConfigurationScreen.this.editableData.canSelfProtect ? "gui.lotrextended.base.on" : "gui.lotrextended.base.off")).withStyle(ExtendedBannerConfigurationScreen.this.editableData.canSelfProtect ? TextFormatting.GREEN : TextFormatting.RED)
						.append(new StringTextComponent("\n")).append(new TranslationTextComponent("gui.lotrextended.banner_configuration.self_protection.disc").withStyle(TextFormatting.RED)), 200);
				ExtendedBannerConfigurationScreen.this.renderTooltip(pPoseStack, lines, pMouseX, pMouseY);
			}
		});
		this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 7, this.topPos + 167, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms.global"), 
			button -> showDefaultPerms = !showDefaultPerms, GUI_TEXTURE, SIZE, SIZE){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.showDefaultPerms;
			}
			@Override
			public boolean shouldDoDifferentOffset() {
				return !editableData.globalPerms.isEmpty();
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
			int upLoc = this.topPos + ((perm.ordinal()==0||perm.ordinal()>4) ? 177 : 167);
			PermissionButton permButton = new PermissionButton(leftLoc, upLoc, 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, button -> {
				ExtendedBannerPermissions tweaking = ((PermissionButton)button).perm;
				if(editableData.globalPerms.contains(tweaking)) {
					editableData.globalPerms.remove(tweaking);
				}else {
					// Handle adding permissions
					if(tweaking.equals(ExtendedBannerPermissions.EVERYTHING)) {
						// If everything is allowed, clear everything but banner (which isn't covered by everything
						editableData.globalPerms.removeIf((bannerPerm) -> !bannerPerm.equals(ExtendedBannerPermissions.BANNER));
					}else if(!tweaking.equals(ExtendedBannerPermissions.EVERYTHING) && !tweaking.equals(ExtendedBannerPermissions.BANNER)){
						// If anything except everything and banner, clear everything
						editableData.globalPerms.remove(ExtendedBannerPermissions.EVERYTHING);
					}
					editableData.globalPerms.add(tweaking);
				}
			}){
				@Override
				public boolean shouldOffset() {
					return ExtendedBannerConfigurationScreen.this.editableData.globalPerms.contains((this.perm));
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
		this.alignmentField.setTextColor(65313);
		this.alignmentField.setMaxLength(12);
		this.alignmentField.setBordered(false);
		this.alignmentField.setValue("+"+editableData.alignmentLevel);
		this.alignmentField.setFocus(false);
		this.alignmentField.setCanLoseFocus(true);
		this.addButton(factionPermButton = new ExtendedTextureOffsetButton(this.leftPos + 145, this.topPos + 80, 190, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms"), 
			button -> showFactionPerms = !showFactionPerms, GUI_TEXTURE, SIZE, SIZE){
			@Override
			public boolean shouldOffset() {
				return ExtendedBannerConfigurationScreen.this.showFactionPerms;
			}
			@Override
			public boolean shouldDoDifferentOffset() {
				return !editableData.factionPerms.isEmpty();
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
			PermissionButton permButton = new PermissionButton(leftLoc, upLoc, 100 + perm.ordinal() * 10, 192, 10, 10, new TranslationTextComponent("gui.lotrextended.banner_configuration.perms."+perm.toString().toLowerCase(Locale.ENGLISH)), perm, button -> {
				ExtendedBannerPermissions tweaking = ((PermissionButton)button).perm;
				if(editableData.factionPerms.contains(tweaking)) {
					editableData.factionPerms.remove(tweaking);
					if(editableData.factionPerms.isEmpty()) {
						editableData.factionPerms.add(ExtendedBannerPermissions.EVERYTHING);
					}
				}else {
					// Handle adding permissions
					if(tweaking.equals(ExtendedBannerPermissions.EVERYTHING)) {
						// If everything is allowed, clear everything
						editableData.factionPerms.clear();
					}else if(!tweaking.equals(ExtendedBannerPermissions.EVERYTHING)){
						// If anything except everything and banner, clear everything
						editableData.factionPerms.remove(ExtendedBannerPermissions.EVERYTHING);
					}
					editableData.factionPerms.add(tweaking);
				}
			}){
				@Override
				public boolean shouldOffset() {
					return ExtendedBannerConfigurationScreen.this.editableData.factionPerms.contains((this.perm));
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
		this.addButton(messageField = new TextFieldWidget(this.font, this.leftPos+33, this.topPos+81, 145, 17, new TranslationTextComponent("gui.lotrextended.banner_configuration.custom.textBox")));
		this.messageField.setMaxLength(24);
		this.messageField.setBordered(false);
		if(this.editableData.protMessage.isEmpty()) {
			this.messageField.setValue("The Server");
		}else {
			this.messageField.setValue(this.editableData.protMessage);
		}
		this.messageField.setTextColor(65313);
		this.messageField.setFocus(false);
		this.messageField.setCanLoseFocus(true);
		this.messageField.setResponder((newValue) -> {
			this.editableData.protMessage = newValue;
			messageField.setTextColor(serverData.protMessage.equals(editableData.protMessage) ? 65313 : 14737632);
		});
		// Whitelist
		this.protectionWhitelist = new ExtendedProtectionList(this, this.minecraft, this.height, leftPos + 27, 166, topPos + 45, topPos + 45 + 119, 36);
		this.children.add(this.protectionWhitelist);
		this.addButton(createPlayer = new ExtendedSimpleButton(this.leftPos + 4, this.topPos + 45, 20, 20, new TranslationTextComponent("+P"), button -> {
			ProtectionEntry newProt = new ProtectionEntry(new ExtendedBannerProfileClient("")).addPerm(ExtendedBannerPermissions.EVERYTHING);
			newProt.setClientID(UUID.randomUUID());
			this.editableData.whitelist.add(newProt);
			protectionWhitelist.addProtection(newProt);
		}, (Button button, MatrixStack stack, int mouseX, int mouseY) -> {
			ExtendedBannerConfigurationScreen.this.renderTooltip(stack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.add_player"), 200), mouseX, mouseY);
		}));
		this.addButton(createFellowship = new ExtendedSimpleButton(this.leftPos + 4, this.topPos + 66, 20, 20, new TranslationTextComponent("+F"), button -> {
			ProtectionEntry newProt = new ProtectionEntry(new ExtendedBannerProfileClient("f/")).addPerm(ExtendedBannerPermissions.EVERYTHING);
			newProt.setClientID(UUID.randomUUID());
			this.editableData.whitelist.add(newProt);
			protectionWhitelist.addProtection(newProt);
		}, (Button button, MatrixStack stack, int mouseX, int mouseY) -> {
			ExtendedBannerConfigurationScreen.this.renderTooltip(stack, font.split(new TranslationTextComponent("gui.lotrextended.banner_configuration.whitelist.add_fellowship"), 200), mouseX, mouseY);
		}));
		this.protectionWhitelist.updateProtections(editableData.whitelist, protectionWhitelist.getScrollAmount());
	}

	private void rotatePermissionType() {
		if(editableData.protectionType==0) {
			editableData.protectionType=1;
		}else if(editableData.protectionType==1 && !isClientOP) {
			editableData.protectionType=0;
		}else if(editableData.protectionType==1 && isClientOP) {
			editableData.protectionType=3;
		}else if(editableData.protectionType==3) {
			editableData.protectionType=0;
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

	public ExtendedBannerConfigData getEditableData() {
		return editableData;
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		this.minecraft.getTextureManager().bind(GUI_TEXTURE);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, SIZE, SIZE);
		TranslationTextComponent title = new TranslationTextComponent("gui.lotrextended.banner_configuration.title");
		this.font.draw(matStack, title, leftPos + (this.imageWidth/2) - (font.width(title)/2), topPos + 7, 4210752);
		if(!isEnabled) {
			alignmentField.visible=false;
			factionPermButton.visible = false;
			messageField.visible=false;
			protectionType.visible=false;
			syncChanges.visible=false;
			globalPermButtons.forEach((permButton) -> permButton.visible=false);
			showFactionPerms=false;
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.disabled"), leftPos + 25, topPos + 64, 16711680);
			playerInfo.visible=false;
			return;
		}
		playerInfo.visible=true;
		if(editableData.protectionType==0) {
			//Faction
			alignmentField.visible=true;
			factionPermButton.visible = true;
			messageField.visible=false;
			createPlayer.visible = false;
			createFellowship.visible = false;
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.faction.requires"), leftPos + 61, topPos + 64, 4210752);
			if(this.getFactionMessage()!=null) {
				this.font.draw(matStack, this.getFactionMessage(), leftPos + (this.imageWidth/2) - (this.font.width(this.getFactionMessage())/2), topPos + 47, 4210752);
			}
			if(this.getFlagLoc()!=null) {
				this.minecraft.getTextureManager().bind(this.getFlagLoc());
				blit(matStack, this.leftPos-90, this.topPos + (this.imageHeight/2) - 74, 0, 0, 80, 148);
			}
			this.minecraft.getTextureManager().bind(GUI_TEXTURE);
			blit(matStack, this.leftPos+60, this.topPos+76, 0, 192, 80, 17, SIZE, SIZE);
			// Permissions box
			if(this.showFactionPerms) {
				blit(matStack, this.leftPos+40, this.topPos+97, 0, 192, 79, 12, SIZE, SIZE);
				blit(matStack, this.leftPos+40, this.topPos+109, 0, 205, 79, 4, SIZE, SIZE);
				blit(matStack, this.leftPos+119, this.topPos+97, 38, 192, 42, 12, SIZE, SIZE);
				blit(matStack, this.leftPos+119, this.topPos+109, 38, 205, 42, 4, SIZE, SIZE);
			}
			alignmentField.render(matStack, mouseX, mouseY, tick);
		}else if(editableData.protectionType==1) {
			//Whitelist
			this.protectionWhitelist.render(matStack, mouseX, mouseY, tick);
			alignmentField.visible=false;
			factionPermButton.visible = false;
			showFactionPerms=false;
			messageField.visible=false;
			createPlayer.visible = true;
			createFellowship.visible = true;
		}else {
			//Custom
			messageField.visible=true;
			alignmentField.visible=false;
			factionPermButton.visible = false;
			showFactionPerms=false;
			createPlayer.visible = false;
			createFellowship.visible = false;
			this.font.draw(matStack, new TranslationTextComponent("gui.lotrextended.banner_configuration.custom.description"), leftPos + 31, topPos + 64, 4210752);
			this.minecraft.getTextureManager().bind(GUI_TEXTURE);
			blit(matStack, this.leftPos+30, this.topPos+76, 0, 192, 79, 17, SIZE, SIZE);
			blit(matStack, this.leftPos+100, this.topPos+76, 1, 192, 79, 17, SIZE, SIZE);
			messageField.render(matStack, mouseX, mouseY, tick);
		}
		for(PermissionButton button : globalPermButtons) {
			button.visible = showDefaultPerms;
		}
		for(PermissionButton button : factionPermButtons) {
			button.visible = showFactionPerms;
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	@Override
	public void tick() {
		super.tick();
		alignmentField.tick();
		messageField.tick();
		protectionType.setMessage(
				editableData.protectionType==0 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_faction"):
					editableData.protectionType==1 ? new TranslationTextComponent("gui.lotrextended.banner_configuration.type_whitelist"): 
						new TranslationTextComponent("gui.lotrextended.banner_configuration.type_custom"));
		checkIfModified();
		syncChanges.active = dataChanged;
		protectionWhitelist.tick();
		boolean toMany = editableData.whitelist.size()>128;
		createFellowship.active = !toMany;
		createPlayer.active = !toMany;
	}

	@Override
	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		return super.mouseClicked(pMouseX, pMouseY, pButton) || this.protectionWhitelist.mouseClicked(pMouseX, pMouseY, pButton);
	}

	@Override
	public void onClose() {
		super.onClose();
		handleAlignmentUpdate();
		if(isEnabled) {
			sendPacket();
		}
	}

	public void sendPacket() {
		if(isEnabled) {
			ExtendedPacketHandler.sendToServer(new ExtendedCBannerSaveInfoPacket(editableData.globalPerms, editableData.factionPerms,
					editableData.alignmentLevel, editableData.whitelist, editableData.canSelfProtect, editableData.protectionType, this.configurePos, editableData.protMessage));
		}
	}

	public void handleAlignmentUpdate() {
		String newValue = alignmentField.getValue();
		if(newValue.startsWith("+")) {
			newValue=newValue.substring(1);
		}
		newValue=newValue.trim();
		float newLevel =1.0f;
		try {
			newLevel = Float.parseFloat(newValue);
		}catch(NumberFormatException e) {}
		newLevel = MathHelper.clamp(newLevel, 0f, 10000f);
		editableData.alignmentLevel = Math.round(newLevel * 10) / 10.f;
		alignmentField.setTextColor(Float.compare(serverData.alignmentLevel, editableData.alignmentLevel) == 0 ? 65313 : 14737632);
		alignmentField.setValue("+"+editableData.alignmentLevel);
	}

	public static class PermissionButton extends ExtendedTextureOffsetButton {
		public ExtendedBannerPermissions perm;
		public PermissionButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, ExtendedBannerPermissions perm, IPressable pOnPress) {
			super(pX, pY, tX, tY, pWidth, pHeight, pMessage, pOnPress, GUI_TEXTURE, SIZE, SIZE);
			this.perm = perm;
		}
	}
}