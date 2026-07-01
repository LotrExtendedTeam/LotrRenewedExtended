package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
			ServerPlayerEntity pPlayer = context.get().getSender();
			Entity entity = pPlayer.level.getEntity(msg.entityId);
			if(!(entity instanceof ExtendedINPCEntity) 
				|| !((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty()
				|| !pPlayer.getUUID().equals(((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID())) {
				return;
			}
			ExtendedNpcHiredUnitInfoHolder info = ((ExtendedINPCEntity)entity).getUnitInfo();
			final List<ItemStack> equipment = new ArrayList<>(20);
			Inventory inventory = info.getInternalUnitInventory();
			for (int i = 9; i < 29; i++) {
				equipment.add(inventory.getItem(i));
			}
			PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
			ExtendedHiredUnitEquipmentContainer.writeContainerInitData(initData, msg.entityId, info.getUnitProfile(), equipment);
			info.isInteracting = true;
			NetworkHooks.openGui(context.get().getSender(),
				new SimpleNamedContainerProvider((windowId, playerInventory, player) -> 
				ExtendedContainerTypes.HIRED_UNIT_EQUIPMENT.get().create(windowId, playerInventory, initData),
				ExtendedHiredUnitEquipmentContainer.CONTAINER_TITLE),
				buf -> ExtendedHiredUnitEquipmentContainer.writeContainerInitData(buf, msg.entityId, info.getUnitProfile(), equipment)
				);
		});
		context.get().setPacketHandled(true);
	}
}
