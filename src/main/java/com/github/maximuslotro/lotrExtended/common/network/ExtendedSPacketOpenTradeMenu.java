package com.github.maximuslotro.lotrExtended.common.network;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.init.ExtendedContainers;
import lotr.common.inv.ExtendedTradeContainer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

public class ExtendedSPacketOpenTradeMenu {

    private int traderId;

    public ExtendedSPacketOpenTradeMenu() {}

    public ExtendedSPacketOpenTradeMenu(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedSPacketOpenTradeMenu packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedSPacketOpenTradeMenu decode(PacketBuffer buf) {
        ExtendedSPacketOpenTradeMenu packet = new ExtendedSPacketOpenTradeMenu();
        packet.traderId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedSPacketOpenTradeMenu packet, Supplier<NetworkEvent.Context> context) {
        ContainerType<ExtendedTradeContainer> containerType = ExtendedContainers.TRADING.get();
        PacketBuffer initData = new PacketBuffer(Unpooled.buffer());

        int[] boughtPrices = new int[9];
        int[] boughtQuantities = new int[9];
        int[] boughtOriginalQuantities = new int[9];
        int[] soldPrices = new int[9];
        int[] soldQuantities = new int[9];
        int[] soldOriginalQuantities = new int[9];
        ArrayList<ItemStack> soldItems = new ArrayList<ItemStack>();

        Entity entity = Objects.requireNonNull(context.get().getSender()).level.getEntity(packet.traderId);
        if (entity instanceof ExtendedTraderEntity) {
            ExtendedTraderEntity trader = (ExtendedTraderEntity) entity;
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
