package com.github.maximuslotro.lotrrextended.common.utils;

import com.github.maximuslotro.lotrrextended.client.gui.banner.ExtendedBannerConfigurationScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedCaptainMainScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedEditStoneTextSignScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHoistOptionsScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHornSelectRadialScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHornSelectScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHornSelectTypeScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHornSquadScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedMapScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedTraderMainScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedUnitMainScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedTradeScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedWargInventoryScreen;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenCaptainPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenStoneTextSignPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenWargWindowPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedHoistTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;

import lotr.common.entity.npc.ExtendedICaptainEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.tileentity.TileEntity;

public class ExtendedClientsideSeparatedScreenHandler {

	public static void openHornMenu() {
		Minecraft.getInstance().setScreen(new ExtendedHornSelectScreen());
	}

	public static void openTraderMainMenuGui(NPCEntity entity, PlayerEntity player) {
		Minecraft.getInstance().setScreen(new ExtendedTraderMainScreen(entity, player));
	}

	public static void openUnitMainMenuGui(int entityId, boolean hireDataPresent) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity clientplayerentity = mc.player;
		Entity entity = mc.level.getEntity(entityId);
		if (clientplayerentity!=null && entity instanceof NPCEntity) {
			Minecraft.getInstance().setScreen(new ExtendedUnitMainScreen((NPCEntity)entity, clientplayerentity, hireDataPresent));
		}
	}

	public static void openCaptainGui(ExtendedSOpenCaptainPacket packet) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;
		Entity entity = mc.level.getEntity(packet.getCaptainId());		
		if (entity instanceof ExtendedICaptainEntity && entity instanceof NPCEntity) {
			Minecraft.getInstance().setScreen(new ExtendedCaptainMainScreen((NPCEntity) entity, packet.getProfiles()));
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
			WargEntity warg = (WargEntity)entity;
			Inventory inventory = new Inventory(pPacket.getSize());
			ExtendedWargInventoryContainer wargInventoryContainer = new ExtendedWargInventoryContainer(pPacket.getContainerId(), clientplayerentity.inventory, inventory, warg);
			clientplayerentity.containerMenu = wargInventoryContainer;
			mc.setScreen(new ExtendedWargInventoryScreen(wargInventoryContainer, clientplayerentity.inventory, warg));
		}
	}

	public static void openHornModeSelect() {
		if(ExtendedClientConfig.hornSelectScreenType.get()==0) {
			Minecraft.getInstance().setScreen(new ExtendedHornSelectTypeScreen());
		}else if(ExtendedClientConfig.hornSelectScreenType.get()==1) {
			Minecraft.getInstance().setScreen(new ExtendedHornSelectScreen());
		}else if(ExtendedClientConfig.hornSelectScreenType.get()==2) {
			Minecraft.getInstance().setScreen(new ExtendedHornSelectRadialScreen());
		}
	}

	public static void openHornSquadSelect(String currentSquadName) {
		Minecraft.getInstance().setScreen(new ExtendedHornSquadScreen(currentSquadName));
	}

	public static void handleBannerGUI(ExtendedSBannerScreenPacket packet) {
		Minecraft mc = Minecraft.getInstance();
		if(packet.openScreen) {
			mc.setScreen(new ExtendedBannerConfigurationScreen(packet));
		}else if(mc.screen instanceof ExtendedBannerConfigurationScreen && ((ExtendedBannerConfigurationScreen)mc.screen).getConfigurePos().equals(packet.configurePos)) {
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
		mc.setScreen(new ExtendedEditStoneTextSignScreen((ExtendedStoneTextSignTileEntity)tileentity));
	}

	public static void openHoistGUI(ExtendedHoistTileEntity te) {
		Minecraft mc = Minecraft.getInstance();
		mc.setScreen(new ExtendedHoistOptionsScreen(te));
	}

	public static void openConquestGUI() {
		Minecraft mc = Minecraft.getInstance();
    	mc.setScreen(new ExtendedMapScreen(true));
	}
}
