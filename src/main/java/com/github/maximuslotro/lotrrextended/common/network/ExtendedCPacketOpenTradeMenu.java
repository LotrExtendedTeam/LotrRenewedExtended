package com.github.maximuslotro.lotrrextended.common.network;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.init.ExtendedContainers;
import lotr.common.inventory.ExtendedTradeContainer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

public class ExtendedCPacketOpenTradeMenu {

    private int traderId;

    public ExtendedCPacketOpenTradeMenu() {}

    public ExtendedCPacketOpenTradeMenu(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedCPacketOpenTradeMenu packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedCPacketOpenTradeMenu decode(PacketBuffer buf) {
        ExtendedCPacketOpenTradeMenu packet = new ExtendedCPacketOpenTradeMenu();
        packet.traderId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCPacketOpenTradeMenu packet, Supplier<NetworkEvent.Context> context) {
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

    	ContainerType<ExtendedTradeContainer> containerType = ExtendedContainers.TRADING.get();
    	PacketBuffer initData = new PacketBuffer(Unpooled.buffer());

    	int[] boughtPrices = new int[9];
    	int[] boughtQuantities = new int[9];
    	int[] boughtOriginalQuantities = new int[9];
    	int[] soldPrices = new int[9];
    	int[] soldQuantities = new int[9];
    	int[] soldOriginalQuantities = new int[9];
    	ArrayList<ItemStack> soldItems = new ArrayList<ItemStack>();

    	boughtPrices = trader.getBoughtPrices();
    	boughtQuantities = trader.getBoughtQuantities();
    	boughtOriginalQuantities = trader.getBoughtOriginalQuantities();
    	soldPrices = trader.getSoldPrices();
    	soldQuantities = trader.getSoldQuantities();
    	soldOriginalQuantities = trader.getSoldOriginalQuantities();

    	for (int i = 0; i < 9; i++) {
    		ItemStack stack = trader.getDisplayInventory().getItem(i);
    		soldItems.add(stack);
    	}

    	ExtendedTradeContainer.writeContainerInitData(initData, packet.traderId, boughtPrices, boughtQuantities,
    			boughtOriginalQuantities, soldPrices, soldQuantities, soldOriginalQuantities, soldItems);
    	int[] finalBoughtPrices = boughtPrices;
    	int[] finalBoughtQuantities = boughtQuantities;
    	int[] finalSoldPrices = soldPrices;
    	int[] finalSoldQuantities = soldQuantities;
    	int[] finalBoughtOriginalQuantities = boughtOriginalQuantities;
    	int[] finalSoldOriginalQuantities = soldOriginalQuantities;

    	NetworkHooks.openGui(context.get().getSender(), new SimpleNamedContainerProvider((i, inv, p) -> containerType.create(i, inv, initData), ExtendedTradeContainer.CONTAINER_TITLE), buf -> {
    		ExtendedTradeContainer.writeContainerInitData(buf, packet.traderId, finalBoughtPrices, finalBoughtQuantities,
    				finalBoughtOriginalQuantities, finalSoldPrices, finalSoldQuantities, finalSoldOriginalQuantities,
    				soldItems);
    	});

    	context.get().setPacketHandled(true);
    }

}
