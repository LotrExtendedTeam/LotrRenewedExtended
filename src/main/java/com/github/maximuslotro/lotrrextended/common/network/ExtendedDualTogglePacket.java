package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedNetworkToggles;

import lotr.common.LOTRMod;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedDualTogglePacket {

	private boolean value;
	private ExtendedNetworkToggles type;

	public ExtendedDualTogglePacket(ExtendedNetworkToggles packetType, boolean value) {
		this.type = packetType;
		this.value = value;
	}

	public static void encode(ExtendedDualTogglePacket packet, PacketBuffer buf) {
		buf.writeInt(packet.getType().ordinal());
		buf.writeBoolean(packet.getValue());
	}

	public static ExtendedDualTogglePacket decode(PacketBuffer buf) {
		return new ExtendedDualTogglePacket(ExtendedNetworkToggles.byId(buf.readInt()), buf.readBoolean());
	}

	public static void handle(ExtendedDualTogglePacket packet, Supplier<Context> context) {
		LOTRLevelData dataLevel = context.get().getDirection()==NetworkDirection.PLAY_TO_CLIENT ? LOTRLevelData.clientInstance() : LOTRLevelData.serverInstance();
		LOTRPlayerData dataPlayer = dataLevel.getData(context.get().getDirection()==NetworkDirection.PLAY_TO_CLIENT ? LOTRMod.PROXY.getClientPlayer() : context.get().getSender());
		ExtendedNetworkToggles type = packet.getType();
		if(type==ExtendedNetworkToggles.FRIENDLY_FIRE) {
			dataPlayer.getAlignmentData().setFriendlyFireEnabled(packet.getValue());
		}else if(type==ExtendedNetworkToggles.UNITS_FRIENDLY_FIRE) {
			((ExtendedLOTRPlayerData)dataPlayer).getExtendedMiscData().setHurtHiredUnits(packet.getValue());
		}else if(type==ExtendedNetworkToggles.MAP_LOCATION) {
			dataPlayer.getMiscData().setShowMapLocation(packet.getValue());
		}else if(type==ExtendedNetworkToggles.MAP_MARKERS) {
			dataPlayer.getMapMarkerData().setShowMarkers(packet.getValue());
		}
		context.get().setPacketHandled(true);
	}

	public boolean getValue() {
		return this.value;
	}

	public ExtendedNetworkToggles getType() {
		return this.type;
	}
}
