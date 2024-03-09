package com.github.maximuslotro.lotrrextended.common.network;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedContainers;
import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author RichardB122
 *
 * This is a dual-use packet for both handling opening the Coin Exchange menu and for telling the
 * server that the trading main menu has been closed so that the trader can move again. The boolean
 * shouldOpen is used to dinstingush this.
 */
public class ExtendedSPacketOpenCoinExchange {

    private int traderId;

    private boolean shouldOpen;

    public ExtendedSPacketOpenCoinExchange() {}

    public ExtendedSPacketOpenCoinExchange(int traderId, boolean shouldOpen) {
        this.traderId = traderId;
        this.shouldOpen = shouldOpen;
    }

    public static void encode(ExtendedSPacketOpenCoinExchange packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
        buf.writeBoolean(packet.shouldOpen);
    }

    public static ExtendedSPacketOpenCoinExchange decode(PacketBuffer buf) {
        ExtendedSPacketOpenCoinExchange packet = new ExtendedSPacketOpenCoinExchange();
        packet.traderId = buf.readInt();
        packet.shouldOpen = buf.readBoolean();
        return packet;
    }

    public static void handle(ExtendedSPacketOpenCoinExchange packet, Supplier<NetworkEvent.Context> context) {
        if (packet.shouldOpen) {
            ContainerType<ExtendedCoinExchangeContainer> containerType = ExtendedContainers.COIN_TRADING.get();
            PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
            ExtendedCoinExchangeContainer.writeContainerInitData(initData, packet.traderId);
            NetworkHooks.openGui(context.get().getSender(), new SimpleNamedContainerProvider((i, inv, p) -> containerType.create(i, inv, initData), ExtendedCoinExchangeContainer.CONTAINER_TITLE), buf -> {
                ExtendedCoinExchangeContainer.writeContainerInitData(buf, packet.traderId);
            });
        } else {
            World level = Objects.requireNonNull(context.get().getSender()).level;
            if (level.getEntity(packet.traderId) != null && level.getEntity(packet.traderId) instanceof NPCEntity) {
                NPCEntity entity = (NPCEntity) level.getEntity(packet.traderId);
                if (!(entity instanceof ExtendedTraderEntity)) {
                    return;
                }

                ExtendedTraderEntity trader = (ExtendedTraderEntity) entity;
                trader.removePlayerFromTrading(context.get().getSender());
            }
        }


        context.get().setPacketHandled(true);
    }

}
