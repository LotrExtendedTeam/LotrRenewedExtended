package com.github.maximuslotro.lotrrextended.common.network;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;

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

public class ExtendedCPacketHireUnit {

    private int captainId;

    public ExtendedCPacketHireUnit() {}

    public ExtendedCPacketHireUnit(int captainId) {
       this.captainId = captainId;
    }

    public static void encode(ExtendedCPacketHireUnit packet, PacketBuffer buf) {
        buf.writeInt(packet.captainId);
    }

    public static ExtendedCPacketHireUnit decode(PacketBuffer buf) {
        ExtendedCPacketHireUnit packet = new ExtendedCPacketHireUnit();
        packet.captainId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCPacketHireUnit packet, Supplier<NetworkEvent.Context> context) {
        Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.captainId);
        if (!(entity instanceof ExtendedCaptainEntity)) {
            ExtendedLog.error("Received a captain entity not of type ExtendedCaptainEntity from %s", Objects.requireNonNull(context.get().getSender()).getName().getString());
            context.get().setPacketHandled(true);
            return;
        }

        ExtendedCaptainEntity captain = (ExtendedCaptainEntity) entity;
        ServerPlayerEntity player = Objects.requireNonNull(context.get().getSender());

        // verify the player has enough alignment
        if (!captain.canHire(player)) {
            player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.captain_alignment_too_low"), Util.NIL_UUID);
            context.get().setPacketHandled(true);
            return;
        }

        // verify the player has enough money
        int cost = captain.getCost("BREE_GUARD", player);
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
            ExtendedCriteriaTriggers.UNIT_HIRED_TRIGGER.trigger((ServerPlayerEntity)player, entity.getType(), newEntity.getType(), cost);
        }

        context.get().setPacketHandled(true);
    }
}
