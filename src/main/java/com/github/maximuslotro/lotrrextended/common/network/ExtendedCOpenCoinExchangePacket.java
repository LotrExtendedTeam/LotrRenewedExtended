package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedITraderEntity;
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
 * This is a  packet for handling opening the Coin Exchange menu.
 */
public class ExtendedCOpenCoinExchangePacket {

    private int traderId;

    public ExtendedCOpenCoinExchangePacket(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedCOpenCoinExchangePacket packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedCOpenCoinExchangePacket decode(PacketBuffer buf) {
    	return new ExtendedCOpenCoinExchangePacket(buf.readInt());
    }

    public static void handle(ExtendedCOpenCoinExchangePacket packet, Supplier<NetworkEvent.Context> context) {
    	context.get().setPacketHandled(true);
    	Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.traderId);
    	if (!(entity instanceof ExtendedITraderEntity)) {
    		ExtendedLog.error("Recieved a trader eneity not of type ExtendedTraderEntity from {}", context.get().getSender().getName().getString());
    		context.get().setPacketHandled(true);
    		return;
    	}

    	ExtendedITraderEntity trader = (ExtendedITraderEntity) entity;

    	// Ensure a modified client does not send trade request, bypassing client trade request check
    	if (!trader.canExchangeCoinsWithPlayer(context.get().getSender())) {
    		if(trader.isFriendly(context.get().getSender())) {
        		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), Util.NIL_UUID);
    		}else{
        		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), Util.NIL_UUID);
    		}
    		return;
    	}

    	ContainerType<ExtendedCoinExchangeContainer> containerType = ExtendedContainerTypes.COIN_TRADING.get();
    	PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
    	ExtendedCoinExchangeContainer.writeContainerInitData(initData, packet.traderId);
    	NetworkHooks.openGui(context.get().getSender(), new SimpleNamedContainerProvider((i, inv, p) -> containerType.create(i, inv, initData), ExtendedCoinExchangeContainer.CONTAINER_TITLE), buf -> ExtendedCoinExchangeContainer.writeContainerInitData(buf, packet.traderId));
    }
}
