package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;
import java.util.List;
import java.util.ArrayList;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedClientsideSeparatedScreenHandler;

public class ExtendedSOpenCaptainPacket {

    private int captainId;
    private List<ExtendedHiredUnitProfile> profiles;
    
    public ExtendedSOpenCaptainPacket(int captainId, List<ExtendedHiredUnitProfile> profiles) {
       this.captainId = captainId;
       this.profiles = profiles;
    }

    public static void encode(ExtendedSOpenCaptainPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
        buf.writeInt(packet.profiles.size());
        for (ExtendedHiredUnitProfile profile : packet.profiles) {
        	ExtendedHiredUnitProfile.toNetwork(profile, buf);
        }
    }

    public static ExtendedSOpenCaptainPacket decode(PacketBuffer buf) {
        int captainId = buf.readInt();
        int profileCount = buf.readInt();
        List<ExtendedHiredUnitProfile> profiles = new ArrayList<>();
        for (int i = 0; i < profileCount; i++) {
            profiles.add(ExtendedHiredUnitProfile.fromNetwork(buf));
        }
        return new ExtendedSOpenCaptainPacket(captainId, profiles);
    }

    public static void handle(ExtendedSOpenCaptainPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ExtendedClientsideSeparatedScreenHandler.openCaptainGui(packet));

    }

	public int getCaptainId() {
		return captainId;
	}

	public List<ExtendedHiredUnitProfile> getProfiles() {
		return profiles;
	}
}
