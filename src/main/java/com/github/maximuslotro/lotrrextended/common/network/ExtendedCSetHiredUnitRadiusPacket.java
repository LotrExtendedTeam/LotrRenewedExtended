package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCSetHiredUnitRadiusPacket {
	private final int entityId;
	private final int radius;

	public ExtendedCSetHiredUnitRadiusPacket(int entityId, int radius) {
		this.entityId = entityId;
		this.radius = radius;
	}

	public static ExtendedCSetHiredUnitRadiusPacket decode(PacketBuffer buf) {
		return new ExtendedCSetHiredUnitRadiusPacket(buf.readInt(), buf.readInt());
	}

	public static void encode(ExtendedCSetHiredUnitRadiusPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.radius);
	}

	public static void handle(ExtendedCSetHiredUnitRadiusPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity player = context.get().getSender();
			if (player != null && player.level instanceof ServerWorld) {
				Entity entity = player.level.getEntity(packet.entityId);
				if (entity instanceof NPCEntity && ((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty() && ((ExtendedINPCEntity)entity).getUnitInfo().getUnitOwner()==player) {
					((ExtendedINPCEntity)entity).getUnitInfo().setGuardRadius(packet.radius);
				}
			}
		});
		context.get().setPacketHandled(true);
	}
}
