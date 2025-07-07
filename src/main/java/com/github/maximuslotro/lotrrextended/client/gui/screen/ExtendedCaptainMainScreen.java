package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.client.hiredunits.ExtendedClientHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.network.*;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.Faction;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class ExtendedCaptainMainScreen extends ExtendedBasicAbstractScreen {

	private NPCEntity npcEntity;
	private final PlayerEntity player;
	private final List<ExtendedClientHiredUnitProfile> unitProfiles;
	private int currentProfileIndex;
	private Button hireButton;
	private Button leftArrowButton;
	private Button rightArrowButton;

	public ExtendedCaptainMainScreen(NPCEntity entity, PlayerEntity player, List<ExtendedClientHiredUnitProfile> profiles) {
		super(new StringTextComponent("Captain Menu"), 176, 256);

		this.npcEntity = entity;
		this.player = player;
		this.unitProfiles = profiles;
		this.currentProfileIndex = 0;
	}
	
	private ExtendedClientHiredUnitProfile getCurrentProfile() {
		if (unitProfiles.isEmpty()) return null;
		return unitProfiles.get(currentProfileIndex);
	}

	@Override
	public void init() {
		super.init();		
		this.addButton(new Button(this.leftPos + (this.imageWidth - 125) / 2, this.topPos + 115, 125, 20, new TranslationTextComponent("gui.lotrextended.trading.talk_button"), button -> talkWithNPC()));
		// only show if there are multiple profiles
		if (unitProfiles.size() > 1) {
			leftArrowButton = this.addButton(new Button(this.leftPos -13 , this.topPos + 92, 25, 20, new StringTextComponent("<<"), button -> {
				switchToPrevious();
			}));
			rightArrowButton = this.addButton(new Button(this.leftPos + this.imageWidth - 13, this.topPos + 92, 25, 20, new StringTextComponent(">>"), button -> {
				switchToNext();
			}));
		} 
		updateHireButton();
	}
	
	private void updateHireButton() {
		ExtendedClientHiredUnitProfile currentProfile = getCurrentProfile();
		if (currentProfile == null) return;
		
		TextComponent start = new TranslationTextComponent("gui.lotrextended.hiring.hire");
		if(currentProfile.stringsAreTranslated()) {
			start.append(new TranslationTextComponent(currentProfile.getEntityUnitTypeName()));
		} else {
			start.append(new StringTextComponent(currentProfile.getEntityUnitTypeName()));
		}
		
		if (hireButton != null) {
			this.buttons.remove(hireButton);
			this.children.remove(hireButton);
		}
		hireButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 125) / 2, this.topPos + 92, 125, 20, start, button -> hireFromNPC()));
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
		updateHireButton();
	}

	private void talkWithNPC() {
		ExtendedPacketHandler.sendToServer(new ExtendedCTalkWithTraderPacket(npcEntity.getId()));
		closeConnection();
	}

	private void hireFromNPC() {
		// Ensure the client does not disturb the server when a unit shouldn't actually be able to be hired by a player
		ExtendedClientHiredUnitProfile currentProfile = getCurrentProfile();
		if (currentProfile == null) return;
		
		if(npcEntity instanceof ExtendedCaptainEntity) {
			Faction fac = npcEntity.getFaction();
			AlignmentDataModule alignData = LOTRLevelData.sidedInstance(player.level).getData(player).getAlignmentData();
			if(currentProfile.isHiringRequiresPledge() && !alignData.isPledgedTo(fac)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_pledged"), Util.NIL_UUID);
				closeConnection();
			}else if(currentProfile.getHiringAlignmentRequired()>alignData.getAlignment(fac)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_alignment", currentProfile.getHiringAlignmentRequired()), Util.NIL_UUID);
				closeConnection();
			}else if(currentProfile.getHiringCost()>CoinUtils.totalValueInInventory(player.inventory)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_enough_money", currentProfile.getHiringCost()), Util.NIL_UUID);
				closeConnection();
			}else {
				ExtendedPacketHandler.sendToServer(new ExtendedCHireUnitPacket(npcEntity.getId(), currentProfile.getId()));
			}
		}
	}

	private void closeConnection() {
		Minecraft mc = Minecraft.getInstance();
		if(mc.player != null) {
			mc.player.closeContainer();
		}
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new StringTextComponent(npcEntity.getName().getString());
		this.font.draw(matStack, title, this.leftPos + ((this.imageWidth - font.width(title)) / 2), this.topPos + 67, 16777215);
		
		// Display unit type name and page indicator
		ExtendedClientHiredUnitProfile currentProfile = getCurrentProfile();
		if (currentProfile != null) {
			// Show unit type name
			String unitTypeName = currentProfile.getEntityUnitTypeName();
			TextComponent unitTypeText = currentProfile.stringsAreTranslated() ? 
				new TranslationTextComponent(unitTypeName) : new StringTextComponent(unitTypeName);
			this.font.draw(matStack, unitTypeText, this.leftPos + ((this.imageWidth - font.width(unitTypeText)) / 2), this.topPos + 80, 11184810);
			
			// Show page indicator if multiple profiles
			if (unitProfiles.size() > 1) {
				String pageIndicator = "(" + (currentProfileIndex + 1) + "/" + unitProfiles.size() + ")";
				TextComponent pageText = new StringTextComponent(pageIndicator);
				this.font.draw(matStack, pageText, this.leftPos + ((this.imageWidth - font.width(pageText)) / 2), this.topPos + 35, 11184810);
			}
		}
		
		super.render(matStack, mouseX, mouseY, tick);
	}
}
