package com.github.maximuslotro.lotrrextended.common.network;

import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class ExtendedCPacketExchangeCoins {
	private int side; // 0 is left, 1 is right

	public ExtendedCPacketExchangeCoins() {}

	public ExtendedCPacketExchangeCoins(int side) {
		this.side = side;
	}

	public static void encode(ExtendedCPacketExchangeCoins packet, PacketBuffer buf) {
		buf.writeInt(packet.side);
	}

	public static ExtendedCPacketExchangeCoins decode(PacketBuffer buf) {
		ExtendedCPacketExchangeCoins packet = new ExtendedCPacketExchangeCoins();
		packet.side = buf.readInt();
		return packet;
	}

	public static void handle(ExtendedCPacketExchangeCoins packet, Supplier<Context> context) {
		ServerPlayerEntity player = ((Context) context.get()).getSender();
	    if (player.containerMenu instanceof ExtendedCoinExchangeContainer) {
			ExtendedCoinExchangeContainer container = (ExtendedCoinExchangeContainer) player.containerMenu;
			container.handleExchange(packet.side);
		}
		context.get().setPacketHandled(true);
	}
}
