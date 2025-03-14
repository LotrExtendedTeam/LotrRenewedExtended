package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.client.hiredunits.ExtendedClientHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;

import lotr.common.util.client.ExtendedClientsideSeperatedScreenHandler;

public class ExtendedSOpenCaptainPacket {

    private int captainId;
    private ExtendedClientHiredUnitProfile clientProfile;
    // Only used on the server!!!!
    private ExtendedServerHiredUnitProfile serverProfile;
    
    public ExtendedSOpenCaptainPacket(int captainId, ExtendedClientHiredUnitProfile profile, ExtendedServerHiredUnitProfile serverProfile) {
       this.captainId = captainId;
       this.clientProfile = profile;
       this.serverProfile = serverProfile;
    }

    public static void encode(ExtendedSOpenCaptainPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
        ExtendedClientHiredUnitProfile.toNetwork(packet.serverProfile, buf);
    }

    public static ExtendedSOpenCaptainPacket decode(PacketBuffer buf) {
        return new ExtendedSOpenCaptainPacket(buf.readInt(), ExtendedClientHiredUnitProfile.fromNetwork(buf), null);
    }

    public static void handle(ExtendedSOpenCaptainPacket packet, Supplier<NetworkEvent.Context> context) {
        ExtendedClientsideSeperatedScreenHandler.openCaptainGui(packet);
        context.get().setPacketHandled(true);
    }

	public int getCaptainId() {
		return captainId;
	}

	public ExtendedClientHiredUnitProfile getClientProfile() {
		return clientProfile;
	}
}
