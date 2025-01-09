package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitEquipmentContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedContainers;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCPacketOpenHiredEquipmentMenu {
    private final int entityId;

    public ExtendedCPacketOpenHiredEquipmentMenu(int entityId) {
        this.entityId = entityId;
    }

    public static ExtendedCPacketOpenHiredEquipmentMenu decode(PacketBuffer buf) {
        return new ExtendedCPacketOpenHiredEquipmentMenu(buf.readInt());
    }

    public static void encode(ExtendedCPacketOpenHiredEquipmentMenu msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public static void handle(ExtendedCPacketOpenHiredEquipmentMenu msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // fill in equipment array list
            final List<ItemStack> equipment = new ArrayList<>(6);
            Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(msg.entityId);
            Inventory inventory = null;
            ItemStack[] baseGear = ExtendedHiredUnitHelper.getBaseGear(entity);
            if (entity instanceof ExtendedHiredBreeGuard) {
                inventory = ((ExtendedHiredBreeGuard) entity).inventory;
            }
            for (int i = 9; i < 15; i++) {
                if (inventory != null) {
                    ItemStack item = inventory.getItem(i);
                    if (baseGear[i - 9] != null) {
                        if (msg.areItemStacksExactlyEqual(baseGear[i - 9], item)) {
                            item = ItemStack.EMPTY;
                        }
                    }

                    equipment.add(item);
                } else {
                    equipment.add(ItemStack.EMPTY);
                }
            }

            // Prepare PacketBuffer with initialization data
            PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
            ExtendedHiredUnitEquipmentContainer.writeContainerInitData(initData, msg.entityId, equipment);

            if (entity instanceof NPCEntity) {
                NPCEntity npcEntity = (NPCEntity) entity;
                if (npcEntity instanceof ExtendedHiredBreeGuard) {
                    ((ExtendedHiredBreeGuard) npcEntity).setEquipmentOpen(true);
                }
            }

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

    private boolean areItemStacksExactlyEqual(ItemStack stack1, ItemStack stack2) {
        // first check for direct equality
        if (stack1 == stack2) {
            return true;
        }

        // if either of the stacks is empty, they're not equal
        

        // check item type
        // check stack size
        if (stack1.isEmpty() || stack2.isEmpty() || !stack1.getItem().equals(stack2.getItem()) || (stack1.getCount() != stack2.getCount())) {
            return false;
        }

        // check NBT tags for exact equality
        CompoundNBT nbt1 = stack1.getTag();
        CompoundNBT nbt2 = stack2.getTag();
        return nbt1 == null ? nbt2 == null : nbt1.equals(nbt2);
    }
}
