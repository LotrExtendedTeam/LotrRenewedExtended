package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

import lotr.common.inventory.ExtendedTradeContainer;

public class ExtendedCPacketTradeSellItems {
	public ExtendedCPacketTradeSellItems() {}

	public static void encode(ExtendedCPacketTradeSellItems packet, PacketBuffer buf) {
	}

	public static ExtendedCPacketTradeSellItems decode(PacketBuffer buf) {
		ExtendedCPacketTradeSellItems packet = new ExtendedCPacketTradeSellItems();
		return packet;
	}

	public static void handle(ExtendedCPacketTradeSellItems packet, Supplier<Context> context) {
		ServerPlayerEntity player = ((Context) context.get()).getSender();
	    if (player.containerMenu instanceof ExtendedTradeContainer) {
			ExtendedTradeContainer container = (ExtendedTradeContainer) player.containerMenu;
			container.handleSellItems();
		}
		context.get().setPacketHandled(true);
	}
}
