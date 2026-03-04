package com.github.maximuslotro.lotrrextended.common.network;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ExtendedPacketHandler {

	private static final String PROTOCOL_VERSION = "9";

	private static SimpleChannel CHANNEL;

	public static void register() {
		CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("lotrextended", "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
		int id = 0;
		// Packets starting with S are sent from the server to the client (originate at the server)
		// Packets starting with C are sent from the client to the server (originate at the client)
		// This naming scheme is shown in {@see net.minecraft.network.ProtocolType}
		CHANNEL.registerMessage(id++, ExtendedCSelectHornPacket.class, ExtendedCSelectHornPacket::encode, ExtendedCSelectHornPacket::decode, ExtendedCSelectHornPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCExchangeCoinsPacket.class, ExtendedCExchangeCoinsPacket::encode, ExtendedCExchangeCoinsPacket::decode, ExtendedCExchangeCoinsPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCOpenCoinExchangePacket.class, ExtendedCOpenCoinExchangePacket::encode, ExtendedCOpenCoinExchangePacket::decode, ExtendedCOpenCoinExchangePacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCTalkWithTraderPacket.class, ExtendedCTalkWithTraderPacket::encode, ExtendedCTalkWithTraderPacket::decode, ExtendedCTalkWithTraderPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCOpenTradeMenuPacket.class, ExtendedCOpenTradeMenuPacket::encode, ExtendedCOpenTradeMenuPacket::decode, ExtendedCOpenTradeMenuPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCTradeSellItemsPacket.class, ExtendedCTradeSellItemsPacket::encode, ExtendedCTradeSellItemsPacket::decode, ExtendedCTradeSellItemsPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSUpdateTradeQuantitiesPacket.class, ExtendedSUpdateTradeQuantitiesPacket::encode, ExtendedSUpdateTradeQuantitiesPacket::decode, ExtendedSUpdateTradeQuantitiesPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSNpcMarriageInfoPacket.class, ExtendedSNpcMarriageInfoPacket::encode, ExtendedSNpcMarriageInfoPacket::decode, ExtendedSNpcMarriageInfoPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSSendServerSettingsPacket.class, ExtendedSSendServerSettingsPacket::encode, ExtendedSSendServerSettingsPacket::decode, ExtendedSSendServerSettingsPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCStopInteractingWithInteractablePacket.class, ExtendedCStopInteractingWithInteractablePacket::encode, ExtendedCStopInteractingWithInteractablePacket::decode, ExtendedCStopInteractingWithInteractablePacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSOpenWargWindowPacket.class, ExtendedSOpenWargWindowPacket::encode, ExtendedSOpenWargWindowPacket::decode, ExtendedSOpenWargWindowPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSBannerOpenScreenPacket.class, ExtendedSBannerOpenScreenPacket::encode, ExtendedSBannerOpenScreenPacket::decode, ExtendedSBannerOpenScreenPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCBannerSaveInfoPacket.class, ExtendedCBannerSaveInfoPacket::encode, ExtendedCBannerSaveInfoPacket::decode, ExtendedCBannerSaveInfoPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSBannerOpenChangedScreenPacket.class, ExtendedSBannerOpenChangedScreenPacket::encode, ExtendedSBannerOpenChangedScreenPacket::decode, ExtendedSBannerOpenChangedScreenPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCOpenHiredMenuPacket.class, ExtendedCOpenHiredMenuPacket::encode, ExtendedCOpenHiredMenuPacket::decode, ExtendedCOpenHiredMenuPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCOpenHiredEquipmentMenuPacket.class, ExtendedCOpenHiredEquipmentMenuPacket::encode, ExtendedCOpenHiredEquipmentMenuPacket::decode, ExtendedCOpenHiredEquipmentMenuPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCSetHiredUnitOrdersPacket.class, ExtendedCSetHiredUnitOrdersPacket::encode, ExtendedCSetHiredUnitOrdersPacket::decode, ExtendedCSetHiredUnitOrdersPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCHireUnitPacket.class, ExtendedCHireUnitPacket::encode, ExtendedCHireUnitPacket::decode, ExtendedCHireUnitPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedDualTogglePacket.class, ExtendedDualTogglePacket::encode, ExtendedDualTogglePacket::decode, ExtendedDualTogglePacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCOpenCaptainPacket.class, ExtendedCOpenCaptainPacket::encode, ExtendedCOpenCaptainPacket::decode, ExtendedCOpenCaptainPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSOpenCaptainPacket.class, ExtendedSOpenCaptainPacket::encode, ExtendedSOpenCaptainPacket::decode, ExtendedSOpenCaptainPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSNpcTraderInfoPacket.class, ExtendedSNpcTraderInfoPacket::encode, ExtendedSNpcTraderInfoPacket::decode, ExtendedSNpcTraderInfoPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSTradeRefreshPacket.class, ExtendedSTradeRefreshPacket::encode, ExtendedSTradeRefreshPacket::decode, ExtendedSTradeRefreshPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCUpdateStoneTextSignPacket.class, ExtendedCUpdateStoneTextSignPacket::encode, ExtendedCUpdateStoneTextSignPacket::decode, ExtendedCUpdateStoneTextSignPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSOpenStoneTextSignPacket.class, ExtendedSOpenStoneTextSignPacket::encode, ExtendedSOpenStoneTextSignPacket::decode, ExtendedSOpenStoneTextSignPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedSNpcUnitInfoPacket.class, ExtendedSNpcUnitInfoPacket::encode, ExtendedSNpcUnitInfoPacket::decode, ExtendedSNpcUnitInfoPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCUpdateHoistPacket.class, ExtendedCUpdateHoistPacket::encode, ExtendedCUpdateHoistPacket::decode, ExtendedCUpdateHoistPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCRenameItemPacket.class, ExtendedCRenameItemPacket::encode, ExtendedCRenameItemPacket::decode, ExtendedCRenameItemPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCAnvilButtonPacket.class, ExtendedCAnvilButtonPacket::encode, ExtendedCAnvilButtonPacket::decode, ExtendedCAnvilButtonPacket::handle);
		CHANNEL.registerMessage(id++, ExtendedCServerSettingsPacket.class, ExtendedCServerSettingsPacket::encode, ExtendedCServerSettingsPacket::decode, ExtendedCServerSettingsPacket::handle);

		if (id >= 255) {
			// Since IndexedMessageCodec.MessageHandler -> indicies.put((short)(index & 0xff), this);
			// And 0xFF is 255, so using one byte for index, max messages for one channel is 255
			ExtendedLog.error("Max made a Derp mistake. He needs to make a new network channel. ()_()", id);
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
