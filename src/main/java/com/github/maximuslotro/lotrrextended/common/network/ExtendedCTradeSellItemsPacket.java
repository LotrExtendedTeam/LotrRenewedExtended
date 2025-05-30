package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCTradeSellItemsPacket {
	public ExtendedCTradeSellItemsPacket() {}

	public static void encode(ExtendedCTradeSellItemsPacket packet, PacketBuffer buf) {
	}

	public static ExtendedCTradeSellItemsPacket decode(PacketBuffer buf) {
        return new ExtendedCTradeSellItemsPacket();
	}

	public static void handle(ExtendedCTradeSellItemsPacket packet, Supplier<Context> context) {
		ServerPlayerEntity player = context.get().getSender();
	    if (player.containerMenu instanceof ExtendedTradeContainer) {
			ExtendedTradeContainer container = (ExtendedTradeContainer) player.containerMenu;
			container.handleSellItemsToTrader();
		}
		context.get().setPacketHandled(true);
	}
}
