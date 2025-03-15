package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.inventory.ExtendedCoinExchangeContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCExchangeCoinsPacket {
	private int side; // 0 is left, 1 is right

	public ExtendedCExchangeCoinsPacket(int side) {
		this.side = side;
	}

	public static void encode(ExtendedCExchangeCoinsPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.side);
	}

	public static ExtendedCExchangeCoinsPacket decode(PacketBuffer buf) {
		return new ExtendedCExchangeCoinsPacket(buf.readInt());
	}

	public static void handle(ExtendedCExchangeCoinsPacket packet, Supplier<Context> context) {
		ServerPlayerEntity player = context.get().getSender();
	    if (player.containerMenu instanceof ExtendedCoinExchangeContainer) {
			ExtendedCoinExchangeContainer container = (ExtendedCoinExchangeContainer) player.containerMenu;
			container.handleExchange(packet.side);
		}
		context.get().setPacketHandled(true);
	}
}
