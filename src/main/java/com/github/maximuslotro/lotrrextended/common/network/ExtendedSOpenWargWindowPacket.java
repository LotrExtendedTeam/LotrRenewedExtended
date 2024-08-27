package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.util.client.ExtendedClientsideSeperatedScreenHandler;
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
		ExtendedClientsideSeperatedScreenHandler.handle(pPacket);
		context.get().setPacketHandled(true);
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