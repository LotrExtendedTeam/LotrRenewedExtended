package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSDataRequestPacket {

	private int type;

	public static ExtendedSDataRequestPacket requestModListPacket() {
		return new ExtendedSDataRequestPacket(0);
	}

	private ExtendedSDataRequestPacket(int pType) {
		this.type = pType;
	}

	public static void encode(ExtendedSDataRequestPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.type);
	}

	public static ExtendedSDataRequestPacket decode(PacketBuffer buf) {
		return new ExtendedSDataRequestPacket(buf.readInt());
	}

	public static void handle(ExtendedSDataRequestPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		if(packet.type==0) {
			ExtendedPacketHandler.sendToServer(new ExtendedCModListPacket());
		}
	}
}