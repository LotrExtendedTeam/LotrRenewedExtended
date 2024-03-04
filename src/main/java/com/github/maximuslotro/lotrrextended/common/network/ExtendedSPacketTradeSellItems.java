package com.github.maximuslotro.lotrrextended.common.network;

import lotr.common.inv.ExtendedTradeContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class ExtendedSPacketTradeSellItems {
	public ExtendedSPacketTradeSellItems() {}

	public static void encode(ExtendedSPacketTradeSellItems packet, PacketBuffer buf) {
	}

	public static ExtendedSPacketTradeSellItems decode(PacketBuffer buf) {
		ExtendedSPacketTradeSellItems packet = new ExtendedSPacketTradeSellItems();
		return packet;
	}

	public static void handle(ExtendedSPacketTradeSellItems packet, Supplier<Context> context) {
		ServerPlayerEntity player = ((Context) context.get()).getSender();
	    if (player.containerMenu instanceof ExtendedTradeContainer) {
			ExtendedTradeContainer container = (ExtendedTradeContainer) player.containerMenu;
			container.handleSellItems();
		}
		context.get().setPacketHandled(true);
	}
}
