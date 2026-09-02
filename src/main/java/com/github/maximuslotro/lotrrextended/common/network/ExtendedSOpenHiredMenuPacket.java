package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSOpenHiredMenuPacket {
	private final int entityId;
	private final boolean hireInfo;

	public ExtendedSOpenHiredMenuPacket(int entityId, boolean hireInfoPresent) {
		this.entityId = entityId;
		hireInfo = hireInfoPresent;
	}

	public static ExtendedSOpenHiredMenuPacket decode(PacketBuffer buf) {
		return new ExtendedSOpenHiredMenuPacket(buf.readInt(), buf.readBoolean());
	}

	public static void encode(ExtendedSOpenHiredMenuPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeBoolean(msg.hireInfo);
	}

	public static void handle(ExtendedSOpenHiredMenuPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ExtendedClientsideSeparatedScreenHandler.openUnitMainMenuGui(msg.entityId, msg.hireInfo);
		});
		context.get().setPacketHandled(true);
	}
}
