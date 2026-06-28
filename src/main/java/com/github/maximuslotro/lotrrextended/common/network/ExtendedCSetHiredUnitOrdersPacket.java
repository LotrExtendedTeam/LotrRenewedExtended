package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCSetHiredUnitOrdersPacket {

	private final int entityId;
	private final int orderIndex;

	public ExtendedCSetHiredUnitOrdersPacket(int entityId, ExtendedUnitOrders order) {
		this.entityId = entityId;
		this.orderIndex = order.getUnchangingIndex();
	}

	public ExtendedCSetHiredUnitOrdersPacket(int entityId, int pOrderIndex) {
		this.entityId = entityId;
		this.orderIndex = pOrderIndex;
	}

	public static ExtendedCSetHiredUnitOrdersPacket decode(PacketBuffer buf) {
		return new ExtendedCSetHiredUnitOrdersPacket(buf.readInt(), buf.readInt());
	}

	public static void encode(ExtendedCSetHiredUnitOrdersPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.orderIndex);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public static void handle(ExtendedCSetHiredUnitOrdersPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity player = context.get().getSender();
			if (player != null && player.level instanceof ServerWorld) {
				Entity entity = player.level.getEntity(packet.getEntityId());

				if (entity instanceof NPCEntity && ((ExtendedNPCEntity)entity).getUnitInfo().isNpcActiveDuty() && ((ExtendedNPCEntity)entity).getUnitInfo().getUnitOwner()==player) {
					((ExtendedNPCEntity)entity).getUnitInfo().setOrders(ExtendedUnitOrders.getByUnchangingIndex(packet.getOrderIndex()));
				}
			}
		});
		context.get().setPacketHandled(true);
	}
}
