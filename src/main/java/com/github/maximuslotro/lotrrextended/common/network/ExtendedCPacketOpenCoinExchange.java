package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.init.ExtendedContainers;
import lotr.common.inventory.ExtendedCoinExchangeContainer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @author RichardB122
 *
 * This is a dual-use packet for both handling opening the Coin Exchange menu and for telling the
 * server that the trading main menu has been closed so that the trader can move again. The boolean
 * shouldOpen is used to distinguish this.
 */
public class ExtendedCPacketOpenCoinExchange {

    private int traderId;

    public ExtendedCPacketOpenCoinExchange() {}

    public ExtendedCPacketOpenCoinExchange(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedCPacketOpenCoinExchange packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedCPacketOpenCoinExchange decode(PacketBuffer buf) {
        ExtendedCPacketOpenCoinExchange packet = new ExtendedCPacketOpenCoinExchange();
        packet.traderId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCPacketOpenCoinExchange packet, Supplier<NetworkEvent.Context> context) {
    	Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.traderId);
    	if (!(entity instanceof ExtendedTraderEntity)) {
    		ExtendedLog.error("Recieved a trader eneity not of type ExtendedTraderEntity from %s", context.get().getSender().getName().getString());
    		context.get().setPacketHandled(true);
    		return;
    	}

    	ExtendedTraderEntity trader = (ExtendedTraderEntity) entity;

    	// Ensure a modified client does not send trade request, bypassing client trade request check
    	if (!trader.canTradeWith(context.get().getSender())) {
    		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), Util.NIL_UUID);
    		context.get().setPacketHandled(true);
    		return;
    	}

    	ContainerType<ExtendedCoinExchangeContainer> containerType = ExtendedContainers.COIN_TRADING.get();
    	PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
    	ExtendedCoinExchangeContainer.writeContainerInitData(initData, packet.traderId);
    	NetworkHooks.openGui(context.get().getSender(), new SimpleNamedContainerProvider((i, inv, p) -> containerType.create(i, inv, initData), ExtendedCoinExchangeContainer.CONTAINER_TITLE), buf -> {
    		ExtendedCoinExchangeContainer.writeContainerInitData(buf, packet.traderId);
    	});
    	context.get().setPacketHandled(true);
    }

}
