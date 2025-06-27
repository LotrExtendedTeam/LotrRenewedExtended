package com.github.maximuslotro.lotrrextended.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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
    	ExtendedServerHiredUnitProfile unitProfile = ExtendedServerHiredUnitProfileManager.INSTANCE.get(((ExtendedCaptainEntity)entity).getHireableProfile());

    	if(unitProfile==null) {
    		ExtendedLog.error("Tried to handle a null unit profile for "+entity.getClass().getName() +". PLEASE REPORT THIS!");
        	context.get().setPacketHandled(true);
        	return;
    	}
        if(unitProfile.isEmptyUnitType()) {
        	ExtendedLog.error("Tried loading an unknown unit profile for captain entity! "+((ExtendedCaptainEntity)entity).getHireableProfile());
        	context.get().setPacketHandled(true);
        	return;
        }
    	ExtendedSOpenCaptainPacket clientBoundPacket = new ExtendedSOpenCaptainPacket(packet.captainId, null, unitProfile);
		ExtendedPacketHandler.sendToClient(clientBoundPacket, context.get().getSender());
        context.get().setPacketHandled(true);
    }
}
