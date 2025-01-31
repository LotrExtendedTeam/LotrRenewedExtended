package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientServerValues;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSSendServerSettingsPacket {
	private boolean makeMetalDoorsRequirePower;

	public ExtendedSSendServerSettingsPacket() {}

	public void setMakeMetalDoorsRequirePower(boolean makeMetalDoorsRequirePower) {
		this.makeMetalDoorsRequirePower = makeMetalDoorsRequirePower;
	}

	public static void encode(ExtendedSSendServerSettingsPacket packet, PacketBuffer buf) {
		buf.writeBoolean(packet.makeMetalDoorsRequirePower);
	}

	public static ExtendedSSendServerSettingsPacket decode(PacketBuffer buf) {
		ExtendedSSendServerSettingsPacket packet = new ExtendedSSendServerSettingsPacket();
		packet.makeMetalDoorsRequirePower = buf.readBoolean();
		return packet;
	}

	public static void handle(ExtendedSSendServerSettingsPacket packet, Supplier<NetworkEvent.Context> context) {
		ExtendedClientServerValues handler = ExtendedClientServerValues.INSTANCE;
		handler.forcePoweredMetalDoors = packet.makeMetalDoorsRequirePower;
		context.get().setPacketHandled(true);
	}
}
