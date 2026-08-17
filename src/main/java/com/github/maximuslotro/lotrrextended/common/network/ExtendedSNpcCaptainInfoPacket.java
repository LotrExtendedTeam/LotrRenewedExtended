package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.data.ExtendedNpcCaptainInfoHolder;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSNpcCaptainInfoPacket {
	private ExtendedNpcCaptainInfoHolder holder;
	private PacketBuffer buffer;

	public ExtendedSNpcCaptainInfoPacket(PacketBuffer buffer) {
		this.buffer = buffer;
	}

	public ExtendedSNpcCaptainInfoPacket(ExtendedNpcCaptainInfoHolder holder) {
		this.holder = holder;
	}

	public static void encode(ExtendedSNpcCaptainInfoPacket packet, PacketBuffer buf) {
		packet.holder.createDataPacketToClient(buf);
	}

	public static ExtendedSNpcCaptainInfoPacket decode(PacketBuffer buf) {
		return new ExtendedSNpcCaptainInfoPacket(buf);
	}

	public static void handle(ExtendedSNpcCaptainInfoPacket packet, Supplier<NetworkEvent.Context> context) {
		ExtendedNpcCaptainInfoHolder.readDataPacketFromServer(packet.buffer, LOTRMod.PROXY.getClientWorld());
		context.get().setPacketHandled(true);
	}
}
