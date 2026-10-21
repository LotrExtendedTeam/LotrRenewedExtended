package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCRequestConquestDataPacket {
	private ResourceLocation faction;

	public ExtendedCRequestConquestDataPacket(ResourceLocation pFaction) {
		this.faction = pFaction;
	}

	public static void encode(ExtendedCRequestConquestDataPacket packet, PacketBuffer buf) {
		buf.writeResourceLocation(packet.faction);
	}

	public static ExtendedCRequestConquestDataPacket decode(PacketBuffer buf) {
		return new ExtendedCRequestConquestDataPacket(buf.readResourceLocation());
	}

	public static void handle(ExtendedCRequestConquestDataPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		context.get().enqueueWork(() -> {
			ExtendedLevelData.serverInstance().getConquestData().sendConquestDataToClient(packet.faction, context.get().getSender());
        });
	}

}
