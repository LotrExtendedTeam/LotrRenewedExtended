package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSNpcMarriageInfoPacket {
	private ExtendedNpcMarriageHolder holder;
	private PacketBuffer buffer;

	public ExtendedSNpcMarriageInfoPacket(PacketBuffer buffer) {
		this.buffer = buffer;
	}

	public ExtendedSNpcMarriageInfoPacket(ExtendedNpcMarriageHolder holder) {
		this.holder = holder;
	}

	public static void encode(ExtendedSNpcMarriageInfoPacket packet, PacketBuffer buf) {
		packet.holder.createDataPacketToClient(buf);
	}

	public static ExtendedSNpcMarriageInfoPacket decode(PacketBuffer buf) {
		return new ExtendedSNpcMarriageInfoPacket(buf);
	}

	public static void handle(ExtendedSNpcMarriageInfoPacket packet, Supplier<NetworkEvent.Context> context) {
		ExtendedNpcMarriageHolder.readDataPacketFromServer(packet.buffer, LOTRMod.PROXY.getClientWorld());
		context.get().setPacketHandled(true);
	}
}
