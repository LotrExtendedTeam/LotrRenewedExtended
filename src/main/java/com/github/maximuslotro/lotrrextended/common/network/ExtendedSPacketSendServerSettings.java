package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientServerValues;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSPacketSendServerSettings {
	private boolean makeMetalDoorsRequirePower;

	public ExtendedSPacketSendServerSettings() {}

	public void setMakeMetalDoorsRequirePower(boolean makeMetalDoorsRequirePower) {
		this.makeMetalDoorsRequirePower = makeMetalDoorsRequirePower;
	}

	public static void encode(ExtendedSPacketSendServerSettings packet, PacketBuffer buf) {
		buf.writeBoolean(packet.makeMetalDoorsRequirePower);
	}

	public static ExtendedSPacketSendServerSettings decode(PacketBuffer buf) {
		ExtendedSPacketSendServerSettings packet = new ExtendedSPacketSendServerSettings();
		packet.makeMetalDoorsRequirePower = buf.readBoolean();
		return packet;
	}

	public static void handle(ExtendedSPacketSendServerSettings packet, Supplier<NetworkEvent.Context> context) {
		ExtendedClientServerValues handler = ExtendedClientServerValues.INSTANCE;
		handler.forcePoweredMetalDoors = packet.makeMetalDoorsRequirePower;
		context.get().setPacketHandled(true);
	}
}
