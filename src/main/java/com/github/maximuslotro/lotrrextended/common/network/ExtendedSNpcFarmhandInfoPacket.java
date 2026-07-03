package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.data.ExtendedNpcFarmhandInfoHolder;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSNpcFarmhandInfoPacket {
	private ExtendedNpcFarmhandInfoHolder holder;
	private PacketBuffer buffer;

	public ExtendedSNpcFarmhandInfoPacket(PacketBuffer buffer) {
		this.buffer = buffer;
	}

	public ExtendedSNpcFarmhandInfoPacket(ExtendedNpcFarmhandInfoHolder holder) {
		this.holder = holder;
	}

	public static void encode(ExtendedSNpcFarmhandInfoPacket packet, PacketBuffer buf) {
		packet.holder.createDataPacketToClient(buf);
	}

	public static ExtendedSNpcFarmhandInfoPacket decode(PacketBuffer buf) {
		return new ExtendedSNpcFarmhandInfoPacket(buf);
	}

	public static void handle(ExtendedSNpcFarmhandInfoPacket packet, Supplier<NetworkEvent.Context> context) {
		ExtendedNpcFarmhandInfoHolder.readDataPacketFromServer(packet.buffer, LOTRMod.PROXY.getClientWorld());
		context.get().setPacketHandled(true);
	}
}
