package com.github.maximuslotro.lotrrextended.common.network;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.init.ExtendedEntities;
import lotr.common.util.CoinUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ExtendedCHireUnitPacket {

    private int captainId;

    public ExtendedCHireUnitPacket() {}

    public ExtendedCHireUnitPacket(int captainId) {
       this.captainId = captainId;
    }

    public static void encode(ExtendedCHireUnitPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
    }

    public static ExtendedCHireUnitPacket decode(PacketBuffer buf) {
        ExtendedCHireUnitPacket packet = new ExtendedCHireUnitPacket();
        packet.captainId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCHireUnitPacket packet, Supplier<NetworkEvent.Context> context) {
        Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.captainId);
        if (!(entity instanceof ExtendedCaptainEntity)) {
            ExtendedLog.error("Received a captain entity not of type ExtendedCaptainEntity from %s", Objects.requireNonNull(context.get().getSender()).getName().getString());
            context.get().setPacketHandled(true);
            return;
        }

        ExtendedCaptainEntity captain = (ExtendedCaptainEntity) entity;
        ServerPlayerEntity player = Objects.requireNonNull(context.get().getSender());

        // verify the player has enough alignment
        if (!captain.canHire(ExtendedHiredUnitTypes.BREE_GUARD, player)) {
            player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.captain_alignment_too_low"), Util.NIL_UUID);
            context.get().setPacketHandled(true);
            return;
        }

        // verify the player has enough money
        int cost = captain.getCost(ExtendedHiredUnitTypes.BREE_GUARD, player);
        if (CoinUtils.totalValueInInventory(player.inventory) < cost) {
            player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.not_enough_money", cost), Util.NIL_UUID);
            context.get().setPacketHandled(true);
            return;
        }

        // spawn the actual bree-guard
        ExtendedHiredBreeGuard newEntity = (ExtendedHiredBreeGuard) ExtendedEntities.HIRED_BREE_GUARD.get().spawn(
                (ServerWorld) entity.level, null,
                player, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                SpawnReason.NATURAL, false, false
        );

        if (newEntity != null) {
            newEntity.tame(player);
            CoinUtils.removeCoins(player, player.inventory, cost);
            player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.unit_has_been_hired", "Bree-land Guard", cost), Util.NIL_UUID);
            ExtendedCriteriaTriggers.UNIT_HIRED_TRIGGER.trigger(player, entity.getType(), newEntity.getType(), cost);
        }

        context.get().setPacketHandled(true);
    }
}
