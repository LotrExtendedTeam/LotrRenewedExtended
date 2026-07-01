package com.github.maximuslotro.lotrrextended.common.inventory.container;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

public abstract class ExtendedHiredUnitContainer extends Container {

	private int entityId;
	private NPCEntity companion;

	public ExtendedHiredUnitContainer(ContainerType<?> pMenuType, int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		super(pMenuType, pContainerId);

		// read in packet data
		this.entityId = extraData.readInt();
		this.companion = (NPCEntity) pPlayerInventory.player.level.getEntity(entityId);
	}

	public int getEntityId() {
		return entityId;
	}

	public NPCEntity getCompanion() {
		return companion;
	}

	public static void writeContainerInitData(PacketBuffer extraData, int entityId) {
		extraData.writeInt(entityId);
	}
}
