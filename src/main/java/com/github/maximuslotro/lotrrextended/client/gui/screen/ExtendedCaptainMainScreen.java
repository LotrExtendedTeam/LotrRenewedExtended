package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc.MixinNPCEntityInvoker;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedEmptyTextFieldWidget;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedImageButtonButton;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.network.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedICaptainEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.Faction;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class ExtendedCaptainMainScreen extends ExtendedAbstractWindowedScreen {
	private static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/captain_menu.png");
	private NPCEntity npcEntity;
	private final PlayerEntity player;
	private final List<ExtendedHiredUnitProfile> unitProfiles;
	private int currentProfileIndex = 0;
	private Button hireButton;
	private Button leftArrowButton;
	private Button rightArrowButton;
	private ExtendedEmptyTextFieldWidget squadTextField;
	private String squadName="";
	private MobEntity hiringEntity;

	public ExtendedCaptainMainScreen(NPCEntity entity, List<ExtendedHiredUnitProfile> profiles) {
		super(((MixinNPCEntityInvoker)entity).invokeNPCName(), 256, 160);
		Minecraft mc = Minecraft.getInstance();
		this.npcEntity = entity;
		this.player = mc.player;
		this.unitProfiles = profiles;
	}

	private ExtendedHiredUnitProfile getCurrentProfile() {
		if (unitProfiles.isEmpty()) return null;
		return unitProfiles.get(currentProfileIndex);
	}

	@Override
	public void init() {
		super.init();		
		hireButton = this.addButton(new ExtendedImageButtonButton(this.leftPos + 99, this.topPos + 132, 20, 20, 0, 160, 20, SCREEN_BACKGROUND, button -> hireFromNPC()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedHiredUnitProfile currentProfile = getCurrentProfile();
				if (currentProfile == null) {
					ExtendedCaptainMainScreen.this.renderTooltip(pPoseStack, font.split(new StringTextComponent("ERROR CAPTAIN PROFILE NOT FOUND!").withStyle(TextFormatting.RED), 200), pMouseX, pMouseY);
					return;
				}
				Faction fac = npcEntity.getFaction();
				AlignmentDataModule alignData = LOTRLevelData.sidedInstance(player.level).getData(player).getAlignmentData();
				if(currentProfile.isHiringRequiresPledge() && !alignData.isPledgedTo(fac)) {
					ExtendedCaptainMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hiring.hire_unit.not_pledged").withStyle(TextFormatting.RED), 200), pMouseX, pMouseY);
				}else if(currentProfile.getHiringAlignmentRequired()>alignData.getAlignment(fac)) {
					ExtendedCaptainMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hiring.hire_unit.not_alignment", currentProfile.getHiringAlignmentRequired()).withStyle(TextFormatting.RED), 200), pMouseX, pMouseY);
				}else if(currentProfile.getHiringCost()>CoinUtils.totalValueInInventory(player.inventory)) {
					ExtendedCaptainMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hiring.hire_unit.not_enough_money", currentProfile.getHiringCost()).withStyle(TextFormatting.RED), 200), pMouseX, pMouseY);
				}else {
					ExtendedCaptainMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hiring.hire_unit.amount", currentProfile.getHiringCost()), 200), pMouseX, pMouseY);
				}
			}
		});
		leftArrowButton = this.addButton(new Button(this.leftPos + 79 , this.topPos + 132, 20, 20, new StringTextComponent("<<"), button -> {
			switchToPrevious();
		}));
		rightArrowButton = this.addButton(new Button(this.leftPos + 119, this.topPos + 132, 20, 20, new StringTextComponent(">>"), button -> {
			switchToNext();
		}));
		if(unitProfiles.size() <= 1) {
			leftArrowButton.active=false;
			rightArrowButton.active=false;
		}
		squadTextField = this.addButton(new ExtendedEmptyTextFieldWidget(this.font, this.leftPos + 158, this.topPos + 132, 90, 20, 
				new TranslationTextComponent("gui.lotrextended.horn_select.squad.textfield"), new TranslationTextComponent("gui.lotrextended.hiring.squad_assign")));
		this.squadTextField.setMaxLength(20);
		this.squadTextField.setValue(squadName);
		this.squadTextField.setResponder((newValue) -> {
			this.squadName = newValue;
		});
		updateHireInformation();
	}

	@Override
	public void tick() {
		super.tick();
		ExtendedHiredUnitProfile currentProfile = getCurrentProfile();
		if (currentProfile == null) {
			hireButton.active=false;
			return;
		}
		squadTextField.tick();
		Faction fac = npcEntity.getFaction();
		AlignmentDataModule alignData = LOTRLevelData.sidedInstance(player.level).getData(player).getAlignmentData();
		if(currentProfile.isHiringRequiresPledge() && !alignData.isPledgedTo(fac)) {
			hireButton.active=false;
		}else if(currentProfile.getHiringAlignmentRequired()>alignData.getAlignment(fac)) {
			hireButton.active=false;
		}else if(currentProfile.getHiringCost()>CoinUtils.totalValueInInventory(player.inventory)) {
			hireButton.active=false;
		}else {
			hireButton.active=true;
		}
	}

	private void updateHireInformation() {
		ExtendedHiredUnitProfile currentProfile = getCurrentProfile();
		TextComponent unitName;
		if (currentProfile == null) {
			unitName = new StringTextComponent("ERROR NULL");
			hiringEntity = null;
		} else {
			unitName = currentProfile.stringsAreTranslated() ? new TranslationTextComponent(currentProfile.getEntityUnitTypeName()) : new StringTextComponent(currentProfile.getEntityUnitTypeName());
			Minecraft mc = getMinecraft();
			hiringEntity = (MobEntity) getCurrentProfile().getUnitEntityType().create(mc.level);
			try {
				hiringEntity.finalizeSpawn(null, null, SpawnReason.TRIGGERED, null, null);
			}catch(Exception e) {}
		}
		hireButton.setMessage(new TranslationTextComponent("gui.lotrextended.hiring.hire_unit", unitName));
	}

	private void switchToPrevious() {
		if (unitProfiles.size() <= 1) return;
		int newIndex = (currentProfileIndex - 1 + unitProfiles.size()) % unitProfiles.size();
		switchToProfile(newIndex);
	}

	private void switchToNext() {
		if (unitProfiles.size() <= 1) return;
		int newIndex = (currentProfileIndex + 1) % unitProfiles.size();
		switchToProfile(newIndex);
	}

	private void switchToProfile(int newIndex) {
		// Update the current profile index locally for immediate UI feedback
		this.currentProfileIndex = Math.max(0, Math.min(newIndex, unitProfiles.size() - 1));
		updateHireInformation();
	}

	private void hireFromNPC() {
		// Ensure the client does not disturb the server when a unit shouldn't actually be able to be hired by a player
		ExtendedHiredUnitProfile currentProfile = getCurrentProfile();
		if (currentProfile == null) return;

		if(npcEntity instanceof ExtendedICaptainEntity) {
			Faction fac = npcEntity.getFaction();
			AlignmentDataModule alignData = LOTRLevelData.sidedInstance(player.level).getData(player).getAlignmentData();
			if(currentProfile.isHiringRequiresPledge() && !alignData.isPledgedTo(fac)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_pledged"), Util.NIL_UUID);
				onClose();
			}else if(currentProfile.getHiringAlignmentRequired()>alignData.getAlignment(fac)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_alignment", currentProfile.getHiringAlignmentRequired()), Util.NIL_UUID);
				onClose();
			}else if(!player.isCreative() && currentProfile.getHiringCost()>CoinUtils.totalValueInInventory(player.inventory)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_enough_money", currentProfile.getHiringCost()), Util.NIL_UUID);
				onClose();
			}else {
				ExtendedPacketHandler.sendToServer(new ExtendedCUnitHirePacket(npcEntity.getId(), currentProfile.getId(), squadName));
			}
		}
	}

	@Override
	public void render(MatrixStack pMatrixStack, int mouseX, int mouseY, float tick) {
		renderBackground(pMatrixStack);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(SCREEN_BACKGROUND);
		this.blit(pMatrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

		this.font.draw(pMatrixStack, title, this.leftPos + ((this.imageWidth - font.width(title)) / 2), this.topPos + 4, 4210752);
		// Display unit type name and page indicator
		ExtendedHiredUnitProfile currentProfile = getCurrentProfile();
		if (currentProfile != null) {
			String unitTypeName = currentProfile.getEntityUnitTypeName();
			TextComponent unitTypeText = currentProfile.stringsAreTranslated() ? new TranslationTextComponent(unitTypeName) : new StringTextComponent(unitTypeName);
			this.font.draw(pMatrixStack, unitTypeText, this.leftPos + 128 - (font.width(unitTypeText) / 2), this.topPos + 37, 4210752);

			Faction fac = npcEntity.getFaction();
			AlignmentDataModule alignData = LOTRLevelData.sidedInstance(player.level).getData(player).getAlignmentData();
			TextComponent coinCost  = new StringTextComponent(currentProfile.getHiringCost() + "c");
			TextComponent alighmentNeeded  = new StringTextComponent("+"+currentProfile.getHiringAlignmentRequired());
			TextComponent pledgeRequired  = currentProfile.isHiringRequiresPledge() ? new TranslationTextComponent("gui.lotrextended.hiring.pledge_required") : new StringTextComponent("...");
			this.font.draw(pMatrixStack, coinCost, this.leftPos + 90, this.topPos + 53, currentProfile.getHiringCost()>CoinUtils.totalValueInInventory(player.inventory) ? 16736352 : 4210752);
			this.font.draw(pMatrixStack, alighmentNeeded, this.leftPos + 90, this.topPos + 74, currentProfile.getHiringAlignmentRequired()>alignData.getAlignment(fac) ? 16736352 : 4210752);
			this.font.draw(pMatrixStack, pledgeRequired, this.leftPos + 76, this.topPos + 92, currentProfile.isHiringRequiresPledge() && !alignData.isPledgedTo(fac) ? 16736352 : 4210752);

			if (unitProfiles.size() > 1) {
				String pageIndicator = "(" + (currentProfileIndex + 1) + "/" + unitProfiles.size() + ")";
				TextComponent pageText = new StringTextComponent(pageIndicator);
				this.font.draw(pMatrixStack, pageText, this.leftPos + 109 - (font.width(pageText) / 2), this.topPos + 123, 4210752);
			}
		}
		if(hiringEntity!=null) {
			InventoryScreen.renderEntityInInventory(leftPos + 39, topPos + 113, 25, leftPos + 39 - mouseX, topPos + 113 - mouseY, hiringEntity);
		}
		super.render(pMatrixStack, mouseX, mouseY, tick);
	}
}
