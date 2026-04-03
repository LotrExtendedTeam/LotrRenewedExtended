package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.FMLHandshakeMessages.S2CConfigData;

public class ExtendedSSyncServerSettingsPacket {
	private final String fileName;
	private final byte[] fileData;

	public ExtendedSSyncServerSettingsPacket(final String configFileName, final byte[] configFileData) {
		this.fileName = configFileName;
		this.fileData = configFileData;
	}

	public static void encode(ExtendedSSyncServerSettingsPacket packet, PacketBuffer buf) {
		buf.writeUtf(packet.fileName);
		buf.writeByteArray(packet.fileData);
	}

	public static ExtendedSSyncServerSettingsPacket decode(PacketBuffer buf) {
		return new ExtendedSSyncServerSettingsPacket(buf.readUtf(32767), buf.readByteArray());
	}

	public static void handle(ExtendedSSyncServerSettingsPacket packet, Supplier<NetworkEvent.Context> context) {
		ConfigTracker.INSTANCE.receiveSyncedConfig(new S2CConfigData(packet.fileName, packet.fileData), context);
		ExtendedLog.debug("Received extended config sync from server");
		context.get().setPacketHandled(true);
	}
}
