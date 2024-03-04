package com.github.maximuslotro.lotrrextended.common.network;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.stat.LOTRStats;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author RichardB122
 */
public class ExtendedSPacketTalkWithTrader {

    private int traderId;

    public ExtendedSPacketTalkWithTrader() {}

    public ExtendedSPacketTalkWithTrader(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedSPacketTalkWithTrader packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedSPacketTalkWithTrader decode(PacketBuffer buf) {
        ExtendedSPacketTalkWithTrader packet = new ExtendedSPacketTalkWithTrader();
        packet.traderId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedSPacketTalkWithTrader packet, Supplier<NetworkEvent.Context> context) {
        World level = Objects.requireNonNull(context.get().getSender()).level;
        Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.traderId);
        if (!(entity instanceof NPCEntity)) {
            return;
        }

        NPCEntity npc = (NPCEntity) entity;
        if (!level.isClientSide) {
            if (npc.sendNormalSpeechTo(context.get().getSender())) {
                int talkingToTime = LOTRUtil.secondsToTicks(LOTRConfig.COMMON.getRandomNPCTalkToPlayerDuration(new Random()));
                npc.setTalkingToEntity(context.get().getSender(), talkingToTime);
                Objects.requireNonNull(context.get().getSender()).awardStat(LOTRStats.TALK_TO_NPC);
            }
        }

        context.get().setPacketHandled(true);
    }

}
