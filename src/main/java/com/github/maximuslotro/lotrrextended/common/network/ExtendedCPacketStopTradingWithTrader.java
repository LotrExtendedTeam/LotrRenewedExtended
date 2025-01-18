package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author RichardB122
 *
 * This is a dual-use packet for both handling opening the Coin Exchange menu and for telling the
 * server that the trading main menu has been closed so that the trader can move again. The boolean
 * shouldOpen is used to distinguish this.
 */
public class ExtendedCPacketStopTradingWithTrader {

    private int traderId;


    public ExtendedCPacketStopTradingWithTrader() {}

    public ExtendedCPacketStopTradingWithTrader(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedCPacketStopTradingWithTrader packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedCPacketStopTradingWithTrader decode(PacketBuffer buf) {
        ExtendedCPacketStopTradingWithTrader packet = new ExtendedCPacketStopTradingWithTrader();
        packet.traderId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCPacketStopTradingWithTrader packet, Supplier<NetworkEvent.Context> context) {
    	World level = Objects.requireNonNull(context.get().getSender()).level;
        if (level.getEntity(packet.traderId) != null && level.getEntity(packet.traderId) instanceof NPCEntity) {
            NPCEntity entity = (NPCEntity) level.getEntity(packet.traderId);
            if (!(entity instanceof ExtendedTraderEntity)) {
                return;
            }

            ExtendedTraderEntity trader = (ExtendedTraderEntity) entity;
            trader.removePlayerFromInteracting(context.get().getSender());
        }
        context.get().setPacketHandled(true);
    }

}
