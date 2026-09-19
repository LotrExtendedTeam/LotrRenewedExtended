package com.github.maximuslotro.lotrrextended.common.network;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfileManager;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitCombatTypes;

import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.entity.npc.*;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder.UnitHiredInformation;
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
    private String squad;

    public ExtendedCHireUnitPacket(int captainId, ResourceLocation unitID, String squad) {
       this.captainId = captainId;
       this.unitProfileID = unitID;
       this.squad = squad;
    }

    public static void encode(ExtendedCHireUnitPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
        buf.writeResourceLocation(packet.unitProfileID);
        if(packet.squad!=null && !packet.squad.isEmpty()) {
        	buf.writeBoolean(true);
        	buf.writeUtf(packet.squad);
        }else {
        	buf.writeBoolean(false);
        }
    }

    public static ExtendedCHireUnitPacket decode(PacketBuffer buf) {
        return new ExtendedCHireUnitPacket(buf.readInt(), buf.readResourceLocation(), buf.readBoolean() ? buf.readUtf() : "");
    }

    public static void handle(ExtendedCHireUnitPacket packet, Supplier<NetworkEvent.Context> context) {
    	context.get().setPacketHandled(true);
        Entity entityCaptain = context.get().getSender().level.getEntity(packet.captainId);
        if (!(entityCaptain instanceof ExtendedICaptainEntity)) {
            ExtendedLog.error("Received a captain entity not of type ExtendedCaptainEntity from {}", Objects.requireNonNull(context.get().getSender()).getName().getString());
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
        Entity newEntity = unitProfile.getUnitEntityType().spawn((ServerWorld) entityCaptain.level, null, player, new BlockPos(entityCaptain.getX(), entityCaptain.getY(), entityCaptain.getZ()), SpawnReason.TRIGGERED, true, false);

        if(unitProfile.getUnitType()==ExtendedUnitCombatTypes.FARMHAND && !(newEntity instanceof ExtendedIFarmhand)) {
        	ExtendedLog.error("Unit {} is farmand type, but does not implement ExtendedIFarmhand! DEVELOPMENT BUG, pls report!");
        }
        if (newEntity != null && newEntity instanceof NPCEntity) {
        	UnitHiredInformation hireInfo = new UnitHiredInformation(unitProfile.getHiringAlignmentRequired(), unitProfile.isHiringRequiresPledge());
        	((ExtendedINPCEntity)newEntity).getUnitInfo().setNpcAsHired(player, unitProfile.getId(), hireInfo);
        	if(packet.squad!=null && !packet.squad.isEmpty()) {
        		((ExtendedINPCEntity)newEntity).getUnitInfo().setSquad(packet.squad);
        	}
        	if(!player.isCreative()) {
        		CoinUtils.removeCoins(player, player.inventory, unitProfile.getHiringCost());
        	}
            player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.unit_has_been_hired", new TranslationTextComponent(unitProfile.getEntityUnitTypeName()), unitProfile.getHiringCost()), Util.NIL_UUID);
            ExtendedCriteriaTriggers.UNIT_HIRED_TRIGGER.trigger(player, entityCaptain.getType(), newEntity.getType(), unitProfile.getHiringCost());
            LOTRLevelData.getSidedData(player).getFactionStatsData().getFactionStats(captainNpc.getFaction()).addHire();

        }
    }
}
