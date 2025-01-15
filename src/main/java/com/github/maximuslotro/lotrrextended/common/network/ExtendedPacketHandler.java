package com.github.maximuslotro.lotrrextended.common.network;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ExtendedPacketHandler {

	private static final String PROTOCOL_VERSION = "7";

	private static SimpleChannel CHANNEL;

	public static void register() {
		CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("lotr", "extended"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
		int id = 0;
		// Packets starting with S are sent from the server to the client (originate at the server)
		// Packets starting with C are sent from the client to the server (originate at the client)
		// This naming scheme is shown in {@Link net.minecraft.network.ProtocolType}
		CHANNEL.registerMessage(id++, ExtendedCPacketSelectHorn.class, ExtendedCPacketSelectHorn::encode, ExtendedCPacketSelectHorn::decode, ExtendedCPacketSelectHorn::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketExchangeCoins.class, ExtendedCPacketExchangeCoins::encode, ExtendedCPacketExchangeCoins::decode, ExtendedCPacketExchangeCoins::handle);
		CHANNEL.registerMessage(id++, ExtendedSPacketUpdateExchangeCoinContainer.class, ExtendedSPacketUpdateExchangeCoinContainer::encode, ExtendedSPacketUpdateExchangeCoinContainer::decode, ExtendedSPacketUpdateExchangeCoinContainer::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketOpenCoinExchange.class, ExtendedCPacketOpenCoinExchange::encode, ExtendedCPacketOpenCoinExchange::decode, ExtendedCPacketOpenCoinExchange::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketTalkWithTrader.class, ExtendedCPacketTalkWithTrader::encode, ExtendedCPacketTalkWithTrader::decode, ExtendedCPacketTalkWithTrader::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketOpenTradeMenu.class, ExtendedCPacketOpenTradeMenu::encode, ExtendedCPacketOpenTradeMenu::decode, ExtendedCPacketOpenTradeMenu::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketTradeSellItems.class, ExtendedCPacketTradeSellItems::encode, ExtendedCPacketTradeSellItems::decode, ExtendedCPacketTradeSellItems::handle);
		CHANNEL.registerMessage(id++, ExtendedSPacketUpdateTradeQuantities.class, ExtendedSPacketUpdateTradeQuantities::encode, ExtendedSPacketUpdateTradeQuantities::decode, ExtendedSPacketUpdateTradeQuantities::handle);
		CHANNEL.registerMessage(id++, ExtendedSPacketNpcMarriageInfo.class, ExtendedSPacketNpcMarriageInfo::encode, ExtendedSPacketNpcMarriageInfo::decode, ExtendedSPacketNpcMarriageInfo::handle);
		CHANNEL.registerMessage(id++, ExtendedSPacketSendServerSettings.class, ExtendedSPacketSendServerSettings::encode, ExtendedSPacketSendServerSettings::decode, ExtendedSPacketSendServerSettings::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketStopInteractingWithInteractable.class, ExtendedCPacketStopInteractingWithInteractable::encode, ExtendedCPacketStopInteractingWithInteractable::decode, ExtendedCPacketStopInteractingWithInteractable::handle);
		CHANNEL.registerMessage(id++, ExtendedSOpenWargWindowPacket.class, ExtendedSOpenWargWindowPacket::encode, ExtendedSOpenWargWindowPacket::decode, ExtendedSOpenWargWindowPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSBannerOpenScreenPacket.class, ExtendedSBannerOpenScreenPacket::encode, ExtendedSBannerOpenScreenPacket::decode, ExtendedSBannerOpenScreenPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCBannerSaveInfoPacket.class, ExtendedCBannerSaveInfoPacket::encode, ExtendedCBannerSaveInfoPacket::decode, ExtendedCBannerSaveInfoPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSBannerOpenChangedScreenPacket.class, ExtendedSBannerOpenChangedScreenPacket::encode, ExtendedSBannerOpenChangedScreenPacket::decode, ExtendedSBannerOpenChangedScreenPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketOpenHiredMenu.class, ExtendedCPacketOpenHiredMenu::encode, ExtendedCPacketOpenHiredMenu::decode, ExtendedCPacketOpenHiredMenu::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketOpenHiredEquipmentMenu.class, ExtendedCPacketOpenHiredEquipmentMenu::encode, ExtendedCPacketOpenHiredEquipmentMenu::decode, ExtendedCPacketOpenHiredEquipmentMenu::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketSetHiredUnitStationary.class, ExtendedCPacketSetHiredUnitStationary::encode, ExtendedCPacketSetHiredUnitStationary::decode, ExtendedCPacketSetHiredUnitStationary::handle);
		CHANNEL.registerMessage(id++, ExtendedCPacketHireUnit.class, ExtendedCPacketHireUnit::encode, ExtendedCPacketHireUnit::decode, ExtendedCPacketHireUnit::handle);
		if (id >= 255) {
			ExtendedLog.error("Max made a Derp mistake. He needs to make a new network channel (Max number of packets reached %d). ()_()", id);
		}
	}

	public static void sendToClient(Object msg, ServerPlayerEntity player) {
		CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
	}

	public static void sendToAllClients(Object msg) {
		CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
	}

	public static void sendToServer(Object msg) {
	  CHANNEL.send(PacketDistributor.SERVER.noArg(), msg);
	}

	public static void sendToAllTrackingEntity(Object msg, Entity entity) {
		CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
	}
}
