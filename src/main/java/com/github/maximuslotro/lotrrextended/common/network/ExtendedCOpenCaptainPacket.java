package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;
import java.util.List;
import java.util.ArrayList;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfileManager;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedCOpenCaptainPacket {

    private int captainId;

    public ExtendedCOpenCaptainPacket(int captainId) {
       this.captainId = captainId;
    }

    public static void encode(ExtendedCOpenCaptainPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
    }

    public static ExtendedCOpenCaptainPacket decode(PacketBuffer buf) {
        return new ExtendedCOpenCaptainPacket(buf.readInt());
    }

    public static void handle(ExtendedCOpenCaptainPacket packet, Supplier<NetworkEvent.Context> context) {
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
    	
    	List<ExtendedServerHiredUnitProfile> serverProfiles = new ArrayList<>();
    	
    	for (ResourceLocation profileId : profileIds) {
    		ExtendedServerHiredUnitProfile unitProfile = ExtendedServerHiredUnitProfileManager.INSTANCE.get(profileId);
    		if(unitProfile != null && !unitProfile.isEmptyUnitType()) {
    			serverProfiles.add(unitProfile);
    		}
    	}
   
    	if(serverProfiles.isEmpty()) {
    		ExtendedLog.error("No valid unit profiles found for captain entity! " + entity.getClass().getName());
        	context.get().setPacketHandled(true);
        	return;
    	}
    	// The client profiles list will be null here - it gets populated during packet encoding from serverProfiles
    	ExtendedSOpenCaptainPacket clientBoundPacket = new ExtendedSOpenCaptainPacket(packet.captainId, null, serverProfiles);
		ExtendedPacketHandler.sendToClient(clientBoundPacket, context.get().getSender());
        context.get().setPacketHandled(true);
    }
}
