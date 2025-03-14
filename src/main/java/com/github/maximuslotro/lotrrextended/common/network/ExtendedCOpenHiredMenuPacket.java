package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.init.ExtendedContainers;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCOpenHiredMenuPacket {

    private final int entityId;

    public ExtendedCOpenHiredMenuPacket(int entityId) {
        this.entityId = entityId;
    }

    public static ExtendedCOpenHiredMenuPacket decode(PacketBuffer buf) {
        return new ExtendedCOpenHiredMenuPacket(buf.readInt());
    }

    public static void encode(ExtendedCOpenHiredMenuPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public int getId() {
        return this.entityId;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public static void handle(ExtendedCOpenHiredMenuPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(msg.entityId);

            ExtendedHirableEntity unit = ((ExtendedHirableEntity) entity);
            // Prepare PacketBuffer with initialization data
            PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
            ExtendedHiredUnitContainer.writeContainerInitData(initData, msg.entityId, unit.getUnitProfile(), unit.getOrders());

            if(!(entity instanceof ExtendedHirableEntity)) {
            	context.get().setPacketHandled(true);
            	return;
            }
            ((ExtendedHirableEntity) entity).setInventoryOpen(true);

            // Open GUI on client side
            NetworkHooks.openGui(Objects.requireNonNull(context.get().getSender()),
                    new SimpleNamedContainerProvider(
                            (windowId, playerInventory, player) ->
                                    ExtendedContainers.HIRED_UNIT_CONTAINER.get().create(windowId, playerInventory, initData),
                                    ((ExtendedHirableEntity) entity).getHiredUnitName()
                    ),
                    buf -> ExtendedHiredUnitContainer.writeContainerInitData(buf, msg.entityId, unit.getUnitProfile(), unit.getOrders())
            );

            context.get().setPacketHandled(true);
        });
    }

}
