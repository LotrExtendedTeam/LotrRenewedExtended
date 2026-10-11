package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.entity.npc.ExtendedINPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCUnitDismissPacket {
	private final int entityId;

	public ExtendedCUnitDismissPacket(int entityId) {
		this.entityId = entityId;
	}

	public static ExtendedCUnitDismissPacket decode(PacketBuffer buf) {
		return new ExtendedCUnitDismissPacket(buf.readInt());
	}

	public static void encode(ExtendedCUnitDismissPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
	}

	public static void handle(ExtendedCUnitDismissPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity pPlayer = context.get().getSender();
			Entity entity = pPlayer.level.getEntity(msg.entityId);
			if(!(entity instanceof ExtendedINPCEntity) || !((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty()
				|| !pPlayer.getUUID().equals(((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID())) {
				return;
			}
			((ExtendedINPCEntity)entity).getUnitInfo().relieveUnitFromDuty(false);
		});
		context.get().setPacketHandled(true);
	}
}
