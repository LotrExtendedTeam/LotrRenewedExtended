package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCSetHiredUnitDataPacket {
	private final int entityId;
	private final int orderIndex;
	private final int radius;

	public ExtendedCSetHiredUnitDataPacket(int entityId, ExtendedUnitOrders order, int radius) {
		this(entityId, order.getUnchangingIndex(), radius);
	}

	public ExtendedCSetHiredUnitDataPacket(int entityId, int pOrderIndex, int radius) {
		this.entityId = entityId;
		this.orderIndex = pOrderIndex;
		this.radius = radius;
	}

	public static ExtendedCSetHiredUnitDataPacket decode(PacketBuffer buf) {
		return new ExtendedCSetHiredUnitDataPacket(buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static void encode(ExtendedCSetHiredUnitDataPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.orderIndex);
		buf.writeInt(msg.radius);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public static void handle(ExtendedCSetHiredUnitDataPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity player = context.get().getSender();
			if (player != null && player.level instanceof ServerWorld) {
				Entity entity = player.level.getEntity(packet.getEntityId());
				if (entity instanceof NPCEntity && ((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty() && ((ExtendedINPCEntity)entity).getUnitInfo().getUnitOwner()==player) {
					((ExtendedINPCEntity)entity).getUnitInfo().setGuardRadius(packet.radius);
					((ExtendedINPCEntity)entity).getUnitInfo().trySetOrders(ExtendedUnitOrders.getByUnchangingIndex(packet.getOrderIndex()));
				}
			}
		});
		context.get().setPacketHandled(true);
	}
}
