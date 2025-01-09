package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedContainers;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCPacketOpenHiredMenu {

    private final int entityId;

    public ExtendedCPacketOpenHiredMenu(int entityId) {
        this.entityId = entityId;
    }

    public static ExtendedCPacketOpenHiredMenu decode(PacketBuffer buf) {
        return new ExtendedCPacketOpenHiredMenu(buf.readInt());
    }

    public static void encode(ExtendedCPacketOpenHiredMenu msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public int getId() {
        return this.entityId;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public static void handle(ExtendedCPacketOpenHiredMenu msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(msg.entityId);

            // Prepare PacketBuffer with initialization data
            PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
            ExtendedHiredUnitContainer.writeContainerInitData(initData, msg.entityId);

            ExtendedHirableEntity hirableEntity = null;
            if (entity instanceof ExtendedHirableEntity) hirableEntity = (ExtendedHirableEntity) entity;

            if (entity instanceof NPCEntity) {
                NPCEntity npcEntity = (NPCEntity) entity;
                if (npcEntity instanceof ExtendedHirableEntity) {
                    ((ExtendedHirableEntity) npcEntity).setInventoryOpen(true);
                }
            }

            // Open GUI on client side
            assert hirableEntity != null;
            NetworkHooks.openGui(Objects.requireNonNull(context.get().getSender()),
                    new SimpleNamedContainerProvider(
                            (windowId, playerInventory, player) ->
                                    ExtendedContainers.HIRED_UNIT_CONTAINER.get().create(windowId, playerInventory, initData),
                            hirableEntity.getHiredUnitName()
                    ),
                    buf -> ExtendedHiredUnitContainer.writeContainerInitData(buf, msg.entityId)
            );

            context.get().setPacketHandled(true);
        });
    }

}
