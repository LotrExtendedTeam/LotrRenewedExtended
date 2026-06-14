package com.github.maximuslotro.lotrrextended.common.network;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfileManager;

import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.entity.npc.*;
import lotr.common.util.CoinUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ExtendedCHireUnitPacket {

    private int captainId;
    private ResourceLocation unitProfileID;

    public ExtendedCHireUnitPacket(int captainId, ResourceLocation unitID) {
       this.captainId = captainId;
       this.unitProfileID = unitID;
    }

    public static void encode(ExtendedCHireUnitPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
        buf.writeResourceLocation(packet.unitProfileID);
    }

    public static ExtendedCHireUnitPacket decode(PacketBuffer buf) {
        return new ExtendedCHireUnitPacket(buf.readInt(), buf.readResourceLocation());
    }

    public static void handle(ExtendedCHireUnitPacket packet, Supplier<NetworkEvent.Context> context) {
    	context.get().setPacketHandled(true);
        Entity entityCaptain = context.get().getSender().level.getEntity(packet.captainId);
        if (!(entityCaptain instanceof ExtendedCaptainEntity)) {
            ExtendedLog.error("Received a captain entity not of type ExtendedCaptainEntity from %s", Objects.requireNonNull(context.get().getSender()).getName().getString());
            return;
        }

        NPCEntity captainNpc = (NPCEntity)entityCaptain;
        ServerPlayerEntity player = context.get().getSender();

        ExtendedServerHiredUnitProfile unitProfile = ExtendedServerHiredUnitProfileManager.INSTANCE.get(packet.unitProfileID);
        LOTRPlayerData playerData = LOTRLevelData.getSidedData(player);

        if(unitProfile.isEmptyUnitType()) {
        	player.sendMessage(new TranslationTextComponent("ERROR: TRIED HIRING UNKNOWN UNIT TYPE"), Util.NIL_UUID);
        	return;
        }

        // verify the player has enough alignment
        if(unitProfile.getHiringAlignmentRequired() > playerData.getAlignmentData().getAlignment(captainNpc.getFaction())) {
        	player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_alignment", unitProfile.getHiringAlignmentRequired()), Util.NIL_UUID);
            return;
        }
        
        // verify the player has pledge if needed
        if(unitProfile.isHiringRequiresPledge() && !playerData.getAlignmentData().isPledgedTo(captainNpc.getFaction())) {
        	player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_pledged"), Util.NIL_UUID);
            return;
        }
 
        // verify the player has enough coins
        if (!player.isCreative() && unitProfile.getHiringCost() > CoinUtils.totalValueInInventory(player.inventory)) {
            player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_enough_money", unitProfile.getHiringCost()), Util.NIL_UUID);
            return;
        }

        // spawn the actual NPC
        Entity newEntity = unitProfile.getUnitEntityType().spawn((ServerWorld) entityCaptain.level, null, player, new BlockPos(entityCaptain.getX(), entityCaptain.getY(), entityCaptain.getZ()), SpawnReason.NATURAL, false, false);

        if (newEntity != null && newEntity instanceof NPCEntity) {
        	((ExtendedNPCEntity)newEntity).getUnitInfo().setNpcAsHired(player, unitProfile.getId());
        	if(!player.isCreative()) {
        		CoinUtils.removeCoins(player, player.inventory, unitProfile.getHiringCost());
        	}
            player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.unit_has_been_hired", new TranslationTextComponent(unitProfile.getEntityUnitTypeName()), unitProfile.getHiringCost()), Util.NIL_UUID);
            ExtendedCriteriaTriggers.UNIT_HIRED_TRIGGER.trigger(player, entityCaptain.getType(), newEntity.getType(), unitProfile.getHiringCost());
        }
    }
}
