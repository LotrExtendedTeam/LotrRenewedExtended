package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.Util;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;
import java.util.List;
import java.util.ArrayList;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfileManager;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.util.ResourceLocation;

public class ExtendedCCaptainSwitchUnitPacket {

    private int captainId;
    private int newProfileIndex;
    
    public ExtendedCCaptainSwitchUnitPacket(int captainId, int newProfileIndex) {
       this.captainId = captainId;
       this.newProfileIndex = newProfileIndex;
    }

    public static void encode(ExtendedCCaptainSwitchUnitPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
        buf.writeInt(packet.newProfileIndex);
    }

    public static ExtendedCCaptainSwitchUnitPacket decode(PacketBuffer buf) {
        return new ExtendedCCaptainSwitchUnitPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(ExtendedCCaptainSwitchUnitPacket packet, Supplier<NetworkEvent.Context> context) {
    	Entity entity = context.get().getSender().level.getEntity(packet.captainId);
        if (!(entity instanceof ExtendedCaptainEntity)) {
            ExtendedLog.error("Received a captain entity not of type ExtendedCaptainEntity from %s", context.get().getSender().getName().getString());
            context.get().setPacketHandled(true);
            return;
        }
    	if (!((ExtendedCaptainEntity)entity).isFriendlyAndAlignedWithLevel(context.get().getSender(), (NPCEntity) entity, 0)) {
    		if(((ExtendedCaptainEntity)entity).isFriendly(context.get().getSender(), (NPCEntity) entity)) {
        		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.captain_alignment_too_low"), Util.NIL_UUID);
    		}else{
        		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.captain_unfrendly"), Util.NIL_UUID);
    		}
    		context.get().setPacketHandled(true);
    		return;
    	}
    	
    	// Get all hireable profiles for this captain
    	List<ResourceLocation> profileIds = ((ExtendedCaptainEntity)entity).getHireableProfiles();
    	
    	// Validate the new profile index
    	if (packet.newProfileIndex < 0 || packet.newProfileIndex >= profileIds.size()) {
    		ExtendedLog.error("Invalid profile index %d for captain with %d profiles", packet.newProfileIndex, profileIds.size());
    		context.get().setPacketHandled(true);
    		return;
    	}
    	
    	List<ExtendedServerHiredUnitProfile> serverProfiles = new ArrayList<>();
    	
    	for (ResourceLocation profileId : profileIds) {
    		ExtendedServerHiredUnitProfile unitProfile = ExtendedServerHiredUnitProfileManager.INSTANCE.get(profileId);
    		if(unitProfile != null && !unitProfile.isEmptyUnitType()) {
    			serverProfiles.add(unitProfile);
    		} else {
    			ExtendedLog.warn("Skipping invalid unit profile for captain entity: " + profileId);
    		}
    	}
    	
    	if(serverProfiles.isEmpty()) {
    		ExtendedLog.error("No valid unit profiles found for captain entity! " + entity.getClass().getName());
        	context.get().setPacketHandled(true);
        	return;
    	}
    	
    	// Send the updated packet with the new profile index
    	ExtendedSOpenCaptainPacket clientBoundPacket = new ExtendedSOpenCaptainPacket(packet.captainId, null, packet.newProfileIndex, serverProfiles);
		ExtendedPacketHandler.sendToClient(clientBoundPacket, context.get().getSender());
        context.get().setPacketHandled(true);
    }
}
