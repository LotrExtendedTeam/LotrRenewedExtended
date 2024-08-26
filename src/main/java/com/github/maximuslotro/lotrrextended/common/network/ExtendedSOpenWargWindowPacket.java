package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedWargInventoryScreen;
import com.github.maximuslotro.lotrrextended.common.inv.ExtendedWargInventoryContainer;

import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSOpenWargWindowPacket {
	private int containerId;
	private int size;
	private int entityId;

	public ExtendedSOpenWargWindowPacket() {
	}

	public ExtendedSOpenWargWindowPacket(int pContainerId, int pSize, int pEntityId) {
		this.containerId = pContainerId;
		this.size = pSize;
		this.entityId = pEntityId;
	}

	public static void encode(ExtendedSOpenWargWindowPacket packet, PacketBuffer buf) {
		buf.writeByte(packet.containerId);
		buf.writeVarInt(packet.size);
		buf.writeInt(packet.entityId);
	}

	public static ExtendedSOpenWargWindowPacket decode(PacketBuffer buf) {
		ExtendedSOpenWargWindowPacket packet = new ExtendedSOpenWargWindowPacket();
		packet.containerId = buf.readUnsignedByte();
		packet.size = buf.readVarInt();
		packet.entityId = buf.readInt();
		return packet;
	}

	public static void handle(ExtendedSOpenWargWindowPacket pPacket, Supplier<NetworkEvent.Context> context) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;
		Entity entity = mc.level.getEntity(pPacket.getEntityId());
		if (entity instanceof AbstractHorseEntity) {
			ClientPlayerEntity clientplayerentity = mc.player;
			WargEntity abstracthorseentity = (WargEntity)entity;
			Inventory inventory = new Inventory(pPacket.getSize());
			ExtendedWargInventoryContainer warginventorycontainer = new ExtendedWargInventoryContainer(pPacket.getContainerId(), clientplayerentity.inventory, inventory, abstracthorseentity);
			clientplayerentity.containerMenu = warginventorycontainer;
			mc.setScreen(new ExtendedWargInventoryScreen(warginventorycontainer, clientplayerentity.inventory, abstracthorseentity));
			context.get().setPacketHandled(true);
		}
	}

	public int getContainerId() {
		return containerId;
	}

	public int getSize() {
		return size;
	}

	public int getEntityId() {
		return entityId;
	}
}