package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.stat.LOTRStats;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author RichardB122
 */
public class ExtendedCTalkWithTraderPacket {

    private int traderId;

    public ExtendedCTalkWithTraderPacket() {}

    public ExtendedCTalkWithTraderPacket(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedCTalkWithTraderPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedCTalkWithTraderPacket decode(PacketBuffer buf) {
        ExtendedCTalkWithTraderPacket packet = new ExtendedCTalkWithTraderPacket();
        packet.traderId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCTalkWithTraderPacket packet, Supplier<NetworkEvent.Context> context) {
        Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.traderId);
        if (!(entity instanceof NPCEntity)) {
            return;
        }

        NPCEntity npc = (NPCEntity) entity;
        if (npc.sendNormalSpeechTo(context.get().getSender())) {
            int talkingToTime = LOTRUtil.secondsToTicks(LOTRConfig.COMMON.getRandomNPCTalkToPlayerDuration(new Random()));
            npc.setTalkingToEntity(context.get().getSender(), talkingToTime);
            Objects.requireNonNull(context.get().getSender()).awardStat(LOTRStats.TALK_TO_NPC);
        }

        context.get().setPacketHandled(true);
    }

}
