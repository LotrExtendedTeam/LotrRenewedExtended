package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

import lotr.common.inventory.ExtendedCoinExchangeContainer;

public class ExtendedSPacketUpdateExchangeCoinContainer {
	private int exchangedSide; // 0 is left, 1 is right
	private boolean exchanged;

	public ExtendedSPacketUpdateExchangeCoinContainer() {}

	public ExtendedSPacketUpdateExchangeCoinContainer(boolean exchanged, int exchangedSide) {
		this.exchanged = exchanged;
		this.exchangedSide = exchangedSide;
	}

	public static void encode(ExtendedSPacketUpdateExchangeCoinContainer packet, PacketBuffer buf) {
		buf.writeBoolean(packet.exchanged);
		buf.writeInt(packet.exchangedSide);
	}

	public static ExtendedSPacketUpdateExchangeCoinContainer decode(PacketBuffer buf) {
		ExtendedSPacketUpdateExchangeCoinContainer packet = new ExtendedSPacketUpdateExchangeCoinContainer();
		packet.exchanged = buf.readBoolean();
		packet.exchangedSide = buf.readInt();
		return packet;
	}

	public static void handle(ExtendedSPacketUpdateExchangeCoinContainer packet, Supplier<Context> context) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;
		assert player != null;
		if (player.containerMenu instanceof ExtendedCoinExchangeContainer) {
			ExtendedCoinExchangeContainer container = (ExtendedCoinExchangeContainer) player.containerMenu;
			container.sideExchanged = packet.exchangedSide;
			container.exchanged = packet.exchanged;
		}

		context.get().setPacketHandled(true);
	}
}
