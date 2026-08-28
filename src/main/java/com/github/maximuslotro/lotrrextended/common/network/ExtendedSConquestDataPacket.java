package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSConquestDataPacket {
	private ResourceLocation faction;
	private int conquestScale;

	public ExtendedSConquestDataPacket(ResourceLocation pFaction, int pConquestScale) {
		this.faction = pFaction;
		this.conquestScale = pConquestScale;
	}

	public static void encode(ExtendedSConquestDataPacket packet, PacketBuffer buf) {
		buf.writeResourceLocation(packet.faction);
		buf.writeInt(packet.conquestScale);
	}

	public static ExtendedSConquestDataPacket decode(PacketBuffer buf) {
		ResourceLocation faction = buf.readResourceLocation();
		int conquestScale = buf.readInt();
		return new ExtendedSConquestDataPacket(faction, conquestScale);
	}

	public static void handle(ExtendedSConquestDataPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
	}
}