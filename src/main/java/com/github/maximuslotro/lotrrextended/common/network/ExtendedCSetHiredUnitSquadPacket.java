package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCSetHiredUnitSquadPacket {
	private final int entityId;
	private final String squad;

	public ExtendedCSetHiredUnitSquadPacket(int entityId, String squad) {
		this.entityId = entityId;
		this.squad = squad;
	}
	public static void encode(ExtendedCSetHiredUnitSquadPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		if(msg.squad!=null && !msg.squad.isEmpty()) {
			buf.writeBoolean(true);
			buf.writeUtf(msg.squad);
		}else {
			buf.writeBoolean(false);
		}
	}

	public static ExtendedCSetHiredUnitSquadPacket decode(PacketBuffer buf) {
		return new ExtendedCSetHiredUnitSquadPacket(buf.readInt(), buf.readBoolean() ? buf.readUtf() : "");
	}

	public static void handle(ExtendedCSetHiredUnitSquadPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity player = context.get().getSender();
			if (player != null && player.level instanceof ServerWorld) {
				Entity entity = player.level.getEntity(packet.entityId);
				if (entity instanceof NPCEntity && ((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty() && ((ExtendedINPCEntity)entity).getUnitInfo().getUnitOwner()==player) {
					if(packet.squad!=null && !packet.squad.isEmpty()) {
		        		((ExtendedINPCEntity)entity).getUnitInfo().setSquad(packet.squad);
		        	}
				}
			}
		});
		context.get().setPacketHandled(true);
	}
}
