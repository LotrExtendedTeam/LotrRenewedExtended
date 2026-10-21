package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Random;
import java.util.function.Supplier;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.stat.LOTRStats;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCTalkWithNPCPacket {

    private int traderId;

    public ExtendedCTalkWithNPCPacket(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedCTalkWithNPCPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedCTalkWithNPCPacket decode(PacketBuffer buf) {
    	return new ExtendedCTalkWithNPCPacket(buf.readInt());
    }

    public static void handle(ExtendedCTalkWithNPCPacket packet, Supplier<NetworkEvent.Context> context) {
    	context.get().enqueueWork(()->{
            Entity entity = context.get().getSender().level.getEntity(packet.traderId);
    		if (!(entity instanceof NPCEntity)) {
                return;
            }
            NPCEntity npc = (NPCEntity) entity;
            if (npc.sendNormalSpeechTo(context.get().getSender())) {
                int talkingToTime = LOTRUtil.secondsToTicks(LOTRConfig.COMMON.getRandomNPCTalkToPlayerDuration(new Random()));
                npc.setTalkingToEntity(context.get().getSender(), talkingToTime);
                context.get().getSender().awardStat(LOTRStats.TALK_TO_NPC);
            }
    	});
        context.get().setPacketHandled(true);
    }

}
