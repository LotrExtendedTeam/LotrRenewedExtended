package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSNpcUnitInfoPacket {
	private ExtendedNpcHiredUnitInfoHolder holder;
	private PacketBuffer buffer;

	public ExtendedSNpcUnitInfoPacket(PacketBuffer buffer) {
		this.buffer = buffer;
	}

	public ExtendedSNpcUnitInfoPacket(ExtendedNpcHiredUnitInfoHolder holder) {
		this.holder = holder;
	}

	public static void encode(ExtendedSNpcUnitInfoPacket packet, PacketBuffer buf) {
		packet.holder.createDataPacketToClient(buf);
	}

	public static ExtendedSNpcUnitInfoPacket decode(PacketBuffer buf) {
		return new ExtendedSNpcUnitInfoPacket(buf);
	}

	public static void handle(ExtendedSNpcUnitInfoPacket packet, Supplier<NetworkEvent.Context> context) {
		World world = LOTRMod.PROXY.getClientWorld();
		ExtendedNpcHiredUnitInfoHolder.readDataPacketFromServer(packet.buffer, world);
		context.get().setPacketHandled(true);
	}
}
