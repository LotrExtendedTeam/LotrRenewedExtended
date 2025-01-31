package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.inventory.ExtendedTradeContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedSUpdateTradeQuantitiesPacket {

	private int[] soldQuantities = new int[9];
	private int[] boughtQuantities = new int[9];

	public ExtendedSUpdateTradeQuantitiesPacket() {}

	public ExtendedSUpdateTradeQuantitiesPacket(int[] soldQuantities, int[] boughtQuantities) {
		this.boughtQuantities = boughtQuantities;
		this.soldQuantities = soldQuantities;
	}

	public static void encode(ExtendedSUpdateTradeQuantitiesPacket packet, PacketBuffer buf) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putIntArray("bq", packet.boughtQuantities);
		nbt.putIntArray("sq", packet.soldQuantities);
		buf.writeNbt(nbt);
	}

	public static ExtendedSUpdateTradeQuantitiesPacket decode(PacketBuffer buf) {
		ExtendedSUpdateTradeQuantitiesPacket packet = new ExtendedSUpdateTradeQuantitiesPacket();
		CompoundNBT nbt = buf.readAnySizeNbt();
		packet.boughtQuantities = nbt.getIntArray("bq");
		packet.soldQuantities = nbt.getIntArray("sq");
		return packet;
	}

	public static void handle(ExtendedSUpdateTradeQuantitiesPacket packet, Supplier<Context> context) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;
		assert player != null;
		if (player.containerMenu instanceof ExtendedTradeContainer) {
			ExtendedTradeContainer container = (ExtendedTradeContainer) player.containerMenu;
			container.setSoldQuantities(packet.soldQuantities);
			container.setBoughtQuantities(packet.boughtQuantities);
		}

		context.get().setPacketHandled(true);
	}
}
