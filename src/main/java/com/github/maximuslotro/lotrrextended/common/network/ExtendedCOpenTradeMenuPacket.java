package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedIMerchantEntity;
import lotr.common.entity.npc.ExtendedITraderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCOpenTradeMenuPacket {

    private int traderId;

    public ExtendedCOpenTradeMenuPacket(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedCOpenTradeMenuPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedCOpenTradeMenuPacket decode(PacketBuffer buf) {
        return new ExtendedCOpenTradeMenuPacket(buf.readInt());
    }

    public static void handle(ExtendedCOpenTradeMenuPacket packet, Supplier<NetworkEvent.Context> context) {
    	context.get().setPacketHandled(true);
    	Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.traderId);
    	if (!(entity instanceof ExtendedITraderEntity)) {
    		ExtendedLog.error("Recieved a trader eneity not of type ExtendedTraderEntity from {}", context.get().getSender().getName().getString());
    		context.get().setPacketHandled(true);
    		return;
    	}

    	ExtendedIMerchantEntity trader = (ExtendedIMerchantEntity) entity;
    	if(trader.isInteractingWithPlayer() && trader.getPlayerInteractingWith().get()!=context.get().getSender().getUUID()) {
    		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_busy"), Util.NIL_UUID);
    		return;
    	}

    	// Ensure a modified client does not send trade request, bypassing client trade request check
    	if (!trader.canTradeWithPlayer(context.get().getSender())) {
    		if(trader.isFriendly(context.get().getSender())) {
        		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), Util.NIL_UUID);
    		}else{
        		context.get().getSender().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), Util.NIL_UUID);
    		}
    		return;
    	}

    	ContainerType<ExtendedTradeContainer> containerType = ExtendedContainerTypes.TRADING.get();
    	PacketBuffer initData = new PacketBuffer(Unpooled.buffer());

    	int[] boughtPrices = new int[9];
    	int[] boughtQuantities = new int[9];
    	int[] boughtOriginalQuantities = new int[9];
    	int[] soldPrices = new int[9];
    	int[] soldQuantities = new int[9];
    	int[] soldOriginalQuantities = new int[9];
    	List<ItemStack> displayItems = new ArrayList<>();

    	boughtPrices = trader.getTraderData().getBoughtPrices();
    	boughtQuantities = trader.getTraderData().getBoughtQuantities();
    	boughtOriginalQuantities = trader.getTraderData().getBoughtOriginalQuantities();
    	soldPrices = trader.getTraderData().getSoldPrices();
    	soldQuantities = trader.getTraderData().getSoldQuantities();
    	soldOriginalQuantities = trader.getTraderData().getSoldOriginalQuantities();

    	for (int i = 0; i < 18; i++) {
    		ItemStack stack = trader.getTraderData().getDisplayInventory().getItem(i);
    		displayItems.add(stack);
    	}

    	ExtendedTradeContainer.writeContainerInitData(initData, packet.traderId, boughtPrices, boughtQuantities,
    			boughtOriginalQuantities, soldPrices, soldQuantities, soldOriginalQuantities, displayItems);
    	int[] finalBoughtPrices = boughtPrices;
    	int[] finalBoughtQuantities = boughtQuantities;
    	int[] finalBoughtOriginalQuantities = boughtOriginalQuantities;
    	int[] finalSoldPrices = soldPrices;
    	int[] finalSoldQuantities = soldQuantities;
    	int[] finalSoldOriginalQuantities = soldOriginalQuantities;

    	NetworkHooks.openGui(context.get().getSender(), new SimpleNamedContainerProvider((i, inv, p) -> containerType.create(i, inv, initData), ExtendedTradeContainer.CONTAINER_TITLE), 
    		buf -> ExtendedTradeContainer.writeContainerInitData(buf, packet.traderId, finalBoughtPrices, finalBoughtQuantities,
                finalBoughtOriginalQuantities, finalSoldPrices, finalSoldQuantities, finalSoldOriginalQuantities, displayItems));
    }

}
