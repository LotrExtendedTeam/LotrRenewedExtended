package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCPacketSetHiredUnitStationary {

    private final int entityId;

    public ExtendedCPacketSetHiredUnitStationary (int entityId) {
        this.entityId = entityId;
    }

    public static ExtendedCPacketSetHiredUnitStationary decode(PacketBuffer buf) {
        return new ExtendedCPacketSetHiredUnitStationary(buf.readInt());
    }

    public static void encode(ExtendedCPacketSetHiredUnitStationary msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public static void handle(ExtendedCPacketSetHiredUnitStationary  msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (msg != null) {
                context.get().enqueueWork(() -> {
                    ServerPlayerEntity player = context.get().getSender();
                    if (player != null && player.level instanceof ServerWorld) {
                        Entity entity = player.level.getEntity(msg.getEntityId());

                        if (ExtendedHiredUnitHelper.isEntityHiredUnit(entity)) {
                            ExtendedHirableEntity unit = ExtendedHiredUnitHelper.getExtendedHirableEntity(entity);
                            unit.setStationary(!unit.isStationary());
                        }
                    }
                });
            }
        });
        context.get().setPacketHandled(true);
    }
}
