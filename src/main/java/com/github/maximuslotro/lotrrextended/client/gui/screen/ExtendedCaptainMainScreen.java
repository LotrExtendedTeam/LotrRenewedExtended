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

public class ExtendedCaptainMainScreen extends ExtendedBasicAbstractScreen {

	private NPCEntity npcEntity;
	private final PlayerEntity player;
	private final ExtendedClientHiredUnitProfile unitprofile;
	private Button hireButton;

	public ExtendedCaptainMainScreen(NPCEntity entity, PlayerEntity player, ExtendedClientHiredUnitProfile profile) {
		super(new StringTextComponent("Captain Menu"), 176, 256);

		this.npcEntity = entity;
		this.player = player;
		this.unitprofile = profile;
	}

	@Override
	public void init() {
		super.init();
		this.addButton(new Button(this.leftPos + (this.imageWidth - 125) / 2, this.topPos + 115, 125, 20, new TranslationTextComponent("gui.lotrextended.trading.talk_button"), button -> talkWithNPC()));
		// 114 was 57
		// +5 was +63/then 32
		TextComponent start = new TranslationTextComponent("gui.lotrextended.hiring.hire");
		if(unitprofile.stringsAreTranslated()) {
			start.append(new TranslationTextComponent(unitprofile.getEntityUnitTypeName()));
		}else {
			start.append(new StringTextComponent(unitprofile.getEntityUnitTypeName()));
		}
		hireButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 125) / 2, this.topPos + 92, 125, 20, start, button -> hireFromNPC()));
	}

	private void talkWithNPC() {
		ExtendedPacketHandler.sendToServer(new ExtendedCTalkWithTraderPacket(npcEntity.getId()));
		closeConnection();
	}

	private void hireFromNPC() {
		// Ensure the client does not disturb the server when a unit shouldn't actually be able to be hired by a player
		if(npcEntity instanceof ExtendedCaptainEntity) {
			Faction fac = npcEntity.getFaction();
			AlignmentDataModule alignData = LOTRLevelData.sidedInstance(player.level).getData(player).getAlignmentData();
			if(unitprofile.isHiringRequiresPledge() && !alignData.isPledgedTo(fac)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_pledged"), Util.NIL_UUID);
				closeConnection();
			}else if(unitprofile.getHiringAlignmentRequired()>alignData.getAlignment(fac)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_alignment", unitprofile.getHiringAlignmentRequired()), Util.NIL_UUID);
				closeConnection();
			}else if(unitprofile.getHiringCost()>CoinUtils.totalValueInInventory(player.inventory)) {
				player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_enough_money", unitprofile.getHiringCost()), Util.NIL_UUID);
				closeConnection();
			}else {
				ExtendedPacketHandler.sendToServer(new ExtendedCHireUnitPacket(npcEntity.getId(), unitprofile.getId()));
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
		super.render(matStack, mouseX, mouseY, tick);
	}
}
