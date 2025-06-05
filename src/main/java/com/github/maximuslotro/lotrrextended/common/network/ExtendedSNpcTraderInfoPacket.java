package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSNpcTraderInfoPacket {
	private ExtendedNpcTraderInfoHolder holder;
	private PacketBuffer buffer;

	public ExtendedSNpcTraderInfoPacket(PacketBuffer buffer) {
		this.buffer = buffer;
	}

	public ExtendedSNpcTraderInfoPacket(ExtendedNpcTraderInfoHolder holder) {
		this.holder = holder;
	}

	public static void encode(ExtendedSNpcTraderInfoPacket packet, PacketBuffer buf) {
		packet.holder.createDataPacketToClient(buf);
	}

	public static ExtendedSNpcTraderInfoPacket decode(PacketBuffer buf) {
		return new ExtendedSNpcTraderInfoPacket(buf);
	}

	public static void handle(ExtendedSNpcTraderInfoPacket packet, Supplier<NetworkEvent.Context> context) {
		World world = LOTRMod.PROXY.getClientWorld();
		ExtendedNpcTraderInfoHolder.readDataPacketFromServer(packet.buffer, world);
		context.get().setPacketHandled(true);
	}
}
