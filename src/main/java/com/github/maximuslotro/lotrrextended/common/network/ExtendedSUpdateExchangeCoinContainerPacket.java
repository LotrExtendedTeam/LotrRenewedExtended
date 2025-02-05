package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.inventory.ExtendedCoinExchangeContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedSUpdateExchangeCoinContainerPacket {
	private int exchangedSide; // 0 is left, 1 is right
	private boolean exchanged;

	public ExtendedSUpdateExchangeCoinContainerPacket() {}

	public ExtendedSUpdateExchangeCoinContainerPacket(boolean exchanged, int exchangedSide) {
		this.exchanged = exchanged;
		this.exchangedSide = exchangedSide;
	}

	public static void encode(ExtendedSUpdateExchangeCoinContainerPacket packet, PacketBuffer buf) {
		buf.writeBoolean(packet.exchanged);
		buf.writeInt(packet.exchangedSide);
	}

	public static ExtendedSUpdateExchangeCoinContainerPacket decode(PacketBuffer buf) {
		ExtendedSUpdateExchangeCoinContainerPacket packet = new ExtendedSUpdateExchangeCoinContainerPacket();
		packet.exchanged = buf.readBoolean();
		packet.exchangedSide = buf.readInt();
		return packet;
	}

	public static void handle(ExtendedSUpdateExchangeCoinContainerPacket packet, Supplier<Context> context) {
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
