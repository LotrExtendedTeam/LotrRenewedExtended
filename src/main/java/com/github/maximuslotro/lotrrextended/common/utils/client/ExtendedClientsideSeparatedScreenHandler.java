package com.github.maximuslotro.lotrrextended.common.utils.client;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedBannerConfigurationScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedCaptainMainScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedExitStoneTextSignScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedTraderMainScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedTradeScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedWargInventoryScreen;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenChangedScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenCaptainPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenStoneTextSignPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenWargWindowPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.tileentity.TileEntity;

public class ExtendedClientsideSeparatedScreenHandler {

	public static void openTraderMainMenuGui(NPCEntity entity, PlayerEntity player) {
		Minecraft.getInstance().setScreen(new ExtendedTraderMainScreen(entity, (ExtendedTraderEntity) entity, player));
	}

	public static void openCaptainGui(ExtendedSOpenCaptainPacket packet) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;
		Entity entity = mc.level.getEntity(packet.getCaptainId());		
		if (entity instanceof ExtendedCaptainEntity && entity instanceof NPCEntity) {
			Minecraft.getInstance().setScreen(new ExtendedCaptainMainScreen((NPCEntity) entity, player, packet.getClientProfiles()));
		}
	}

	public static void closeTraderGui(int traderId) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.screen instanceof ExtendedTradeScreen && ((ExtendedTradeScreen)mc.screen).container.traderNPC.getId()==traderId) {
			mc.screen.onClose();
		}
	}

	public static void handleWargScreenOpen(ExtendedSOpenWargWindowPacket pPacket) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity clientplayerentity = mc.player;
		Entity entity = mc.level.getEntity(pPacket.getEntityId());
		if (clientplayerentity!=null && entity instanceof WargEntity) {
			WargEntity abstractHorseEntity = (WargEntity)entity;
			Inventory inventory = new Inventory(pPacket.getSize());
			ExtendedWargInventoryContainer wargInventoryContainer = new ExtendedWargInventoryContainer(pPacket.getContainerId(), clientplayerentity.inventory, inventory, abstractHorseEntity);
			clientplayerentity.containerMenu = wargInventoryContainer;
			mc.setScreen(new ExtendedWargInventoryScreen(wargInventoryContainer, clientplayerentity.inventory, abstractHorseEntity));
		}
	}

	public static void openBannerGUI(ExtendedSBannerOpenScreenPacket packet) {
		Minecraft.getInstance().setScreen(new ExtendedBannerConfigurationScreen(packet));
	}

	public static void handleBannerGUIUpdate(ExtendedSBannerOpenChangedScreenPacket packet) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.screen instanceof ExtendedBannerConfigurationScreen && ((ExtendedBannerConfigurationScreen)mc.screen).getConfigurePos().equals(packet.configurePos)) {
			((ExtendedBannerConfigurationScreen)mc.screen).updateFromPacket(packet);
		}
	}

	public static void openCarvingGUI(ExtendedSOpenStoneTextSignPacket packet) {
		Minecraft mc = Minecraft.getInstance();
    	TileEntity tileentity = mc.level.getBlockEntity(packet.getPos());
        if (!(tileentity instanceof ExtendedStoneTextSignTileEntity)) {
           tileentity = new ExtendedStoneTextSignTileEntity();
           tileentity.setLevelAndPosition(mc.level, packet.getPos());
        }
    	mc.setScreen(new ExtendedExitStoneTextSignScreen((ExtendedStoneTextSignTileEntity)tileentity));
	}
}
