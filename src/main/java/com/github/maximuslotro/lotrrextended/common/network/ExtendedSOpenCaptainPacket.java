package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;
import java.util.List;
import java.util.ArrayList;

import com.github.maximuslotro.lotrrextended.client.hiredunits.ExtendedClientHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;

public class ExtendedSOpenCaptainPacket {

    private int captainId;
    private List<ExtendedClientHiredUnitProfile> clientProfiles;
    // Only used on the server!!!!
    private List<ExtendedServerHiredUnitProfile> serverProfiles;
    
    public ExtendedSOpenCaptainPacket(int captainId, List<ExtendedClientHiredUnitProfile> profiles, List<ExtendedServerHiredUnitProfile> serverProfiles) {
       this.captainId = captainId;
       this.clientProfiles = profiles;
       this.serverProfiles = serverProfiles;
    }

    public static void encode(ExtendedSOpenCaptainPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
        buf.writeInt(packet.serverProfiles.size());
        for (ExtendedServerHiredUnitProfile profile : packet.serverProfiles) {
            ExtendedClientHiredUnitProfile.toNetwork(profile, buf);
        }
    }

    public static ExtendedSOpenCaptainPacket decode(PacketBuffer buf) {
        int captainId = buf.readInt();
        int profileCount = buf.readInt();
        List<ExtendedClientHiredUnitProfile> profiles = new ArrayList<>();
        for (int i = 0; i < profileCount; i++) {
            profiles.add(ExtendedClientHiredUnitProfile.fromNetwork(buf));
        }
        return new ExtendedSOpenCaptainPacket(captainId, profiles, null);
    }

    public static void handle(ExtendedSOpenCaptainPacket packet, Supplier<NetworkEvent.Context> context) {
        ExtendedClientsideSeparatedScreenHandler.openCaptainGui(packet);
        context.get().setPacketHandled(true);
    }

	public int getCaptainId() {
		return captainId;
	}

	public List<ExtendedClientHiredUnitProfile> getClientProfiles() {
		return clientProfiles;
	}
}
