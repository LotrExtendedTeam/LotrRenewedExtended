package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSPacketNpcMarriageInfo {
	private ExtendedNpcMarriageHolder holder;
	private PacketBuffer buffer;

	public ExtendedSPacketNpcMarriageInfo(PacketBuffer buffer) {
		this.buffer = buffer;
	}

	public ExtendedSPacketNpcMarriageInfo(ExtendedNpcMarriageHolder holder) {
		this.holder = holder;
	}

	public static void encode(ExtendedSPacketNpcMarriageInfo packet, PacketBuffer buf) {
		packet.holder.createDataPacketToClient(buf);
	}

	public static ExtendedSPacketNpcMarriageInfo decode(PacketBuffer buf) {
		return new ExtendedSPacketNpcMarriageInfo(buf);
	}

	public static void handle(ExtendedSPacketNpcMarriageInfo packet, Supplier<NetworkEvent.Context> context) {
		World world = LOTRMod.PROXY.getClientWorld();
		ExtendedNpcMarriageHolder.readDataPacketFromServer(packet.buffer, world);
		context.get().setPacketHandled(true);
	}
}
