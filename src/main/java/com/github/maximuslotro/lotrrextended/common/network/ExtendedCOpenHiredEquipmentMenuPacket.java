package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitEquipmentContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.init.ExtendedContainers;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCOpenHiredEquipmentMenuPacket {
    private final int entityId;

    public ExtendedCOpenHiredEquipmentMenuPacket(int entityId) {
        this.entityId = entityId;
    }

    public static ExtendedCOpenHiredEquipmentMenuPacket decode(PacketBuffer buf) {
        return new ExtendedCOpenHiredEquipmentMenuPacket(buf.readInt());
    }

    public static void encode(ExtendedCOpenHiredEquipmentMenuPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public static void handle(ExtendedCOpenHiredEquipmentMenuPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // fill in equipment array list
            final List<ItemStack> equipment = new ArrayList<>(20);
            Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(msg.entityId);
            if(!(entity instanceof ExtendedHirableEntity)) {
            	context.get().setPacketHandled(true);
            	return;
            }
            Inventory inventory = ((ExtendedHirableEntity) entity).getCustomInventory();
            for (int i = 9; i < 29; i++) {
            	equipment.add(inventory.getItem(i));
            }

            // Prepare PacketBuffer with initialization data
            PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
            ExtendedHiredUnitEquipmentContainer.writeContainerInitData(initData, msg.entityId, equipment);

            ((ExtendedHirableEntity) entity).setEquipmentOpen(true);

            // Open GUI on client side
            NetworkHooks.openGui(context.get().getSender(),
                    new SimpleNamedContainerProvider(
                            (windowId, playerInventory, player) ->
                                    ExtendedContainers.HIRED_UNIT_EQUIPMENT.get().create(windowId, playerInventory, initData),
                            ExtendedHiredUnitEquipmentContainer.CONTAINER_TITLE
                    ),
                    buf -> ExtendedHiredUnitEquipmentContainer.writeContainerInitData(buf, msg.entityId, equipment)
            );

            context.get().setPacketHandled(true);
        });
    }
}
