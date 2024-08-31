package lotr.common.inv;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedContainers;
import lotr.common.init.ExtendedItems;

import lotr.common.util.CoinUtils;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSPacketUpdateTradeQuantities;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;

/**
 * @author RichardB122
 */
public class ExtendedTradeContainer extends Container {
    public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotr.trading");

    private boolean tradedYet = false;

    private World level;

    public NPCEntity traderNPC;

    public final ExtendedTraderEntity trader;

    private IInventory displayInventory = new Inventory(18);
    public Inventory sellInventory = new Inventory(9);

    private int[] soldPrices = new int[9];
    private int[] soldQuantities = new int[9];
    private int[] soldOriginalQuantities = new int[9];
    private int[] boughtPrices = new int[9];
    private int[] boughtQuantities = new int[9];
    private int[] boughtOriginalQuantities = new int[9];

    private ItemStack[] soldItems;

    private PlayerEntity player;

    public ExtendedTradeContainer(int containerId, PlayerInventory inventory, PacketBuffer extraData) {
        super(ExtendedContainers.TRADING.get(), containerId);

        CompoundNBT nbt = extraData.readAnySizeNbt();

        this.level = inventory.player.level;
        this.player = inventory.player;
        assert nbt != null;
        this.traderNPC = (NPCEntity) this.level.getEntity(nbt.getInt("entityId"));
        this.trader = (ExtendedTraderEntity) traderNPC;

        // read soldItems from NBT
        List<ItemStack> items = new ArrayList<>();
        ListNBT list = nbt.getList("soldItems", 10);
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT node = list.getCompound(i);
            if (node != null) {
                ItemStack item = ItemStack.of(node.getCompound("item"));
                items.add(item);
            }
        }
        soldItems = items.toArray(new ItemStack[items.size()]);

        if (!inventory.player.level.isClientSide) {
           List<ItemStack> tmpItems = new ArrayList<>();
           for (ExtendedTrade trade : trader.getSells()) {
               tmpItems.add(trade.getItem());
           }
           soldItems = tmpItems.toArray(new ItemStack[tmpItems.size()]);
           boughtPrices = trader.getBoughtPrices();
        }


        // setup trades
        if (trader.getDisplayInventory() != null) {
            displayInventory = trader.getDisplayInventory();
            soldPrices = nbt.getIntArray("sp");
            soldQuantities = nbt.getIntArray("sq");
            boughtPrices = nbt.getIntArray("bp");
            boughtQuantities = nbt.getIntArray("bq");
            boughtOriginalQuantities = nbt.getIntArray("boq");
            soldOriginalQuantities = nbt.getIntArray("soq");
        }

        // add buy slots
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(displayInventory, i, 8 + (i * 18), 40) {
                @Override
                public boolean mayPickup(PlayerEntity player) {
                    return true;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return true;
                }
            });
        }

        // add sell display slots
        for (int i = 0; i < 9; i++)
            addSlot(new Slot(displayInventory, i + 9, 8 + (i * 18), 92) {
                @Override
                public boolean mayPickup(PlayerEntity player) {
                    return false;
                }
            });

        // add actual sell slots
        for (int i = 0; i < 9; i++)
            addSlot(new Slot(sellInventory, i, 8 + (i * 18), 141));

        // add inventory slots
        int i;
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) // 95
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 188 + i * 18));
        }
        for (i = 0; i < 9; i++) { // 153
            addSlot(new Slot(inventory, i, 8 + i * 18, 246));
        }
    }
    public static void writeContainerInitData(PacketBuffer extraData, int entityId, int[] boughtPrices,
                                              int[] boughtQuantities, int[] boughtOriginalQuantities, int[] soldPrices,
                                              int[] soldQuantities, int[] soldOriginalQuantities, List<ItemStack> soldItems) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("entityId", entityId);
        nbt.putIntArray("bp", boughtPrices);
        nbt.putIntArray("bq", boughtQuantities);
        nbt.putIntArray("boq", boughtOriginalQuantities);
        nbt.putIntArray("sp", soldPrices);
        nbt.putIntArray("sq", soldQuantities);
        nbt.putIntArray("soq", soldOriginalQuantities);

        ListNBT listnbt = new ListNBT();
        for (ItemStack stack : soldItems) {
            CompoundNBT compoundnbt = new CompoundNBT();
            CompoundNBT itemTag = new CompoundNBT();
            itemTag = stack.save(itemTag);
            compoundnbt.put("item", itemTag);
            listnbt.add(compoundnbt);
        }
        nbt.put("soldItems", listnbt);

        extraData.writeNbt(nbt);
    }

    public int[] getBoughtPrices() {
        return boughtPrices;
    }

    public int[] getSoldPrices() {
        return soldPrices;
    }

    public int[] getSoldQuantities() {
        return soldQuantities;
    }

    public int[] getBoughtQuantities() {
        return boughtQuantities;
    }

    public int[] getBoughtOriginalQuantities() {
        return boughtOriginalQuantities;
    }

    public int[] getSoldOriginalQuantities() {
        return soldOriginalQuantities;
    }

    public int getSellValue() {
        int value = 0;

        // sum the counts for each Item
        HashMap<Item, Integer> counts = new HashMap<>();
        for (int i = 0; i < sellInventory.getContainerSize(); i++) {
            ItemStack stack = sellInventory.getItem(i);
            if (counts.containsKey(stack.getItem().getItem())) {
                int count = counts.get(stack.getItem()) + stack.getCount();
                counts.put(stack.getItem(), count);
            } else {
                counts.put(stack.getItem(), stack.getCount());
            }
        }

        // loop through all trade deals and determine value
        for (Item item : counts.keySet()) {
            for (int i = 0; i < 9; i++) {
                Item displayedItem = displayInventory.getItem(i + 9).getItem();
                if (!displayedItem.equals(item)) continue;

                int quantity = displayInventory.getItem(i + 9).getCount();
                if (quantity > 0) {
                    int totalTrades = counts.get(item) / quantity;
                    value += totalTrades * boughtPrices[i];
                }
            }
        }

        return value;
    }

    @Override
    public ItemStack clicked(int index, int p_184996_2_, ClickType clickType, PlayerEntity p_184996_4_) {
        // handle buy items
        if (index < 9 && !displayInventory.getItem(index).equals(ItemStack.EMPTY) && (clickType.equals(ClickType.PICKUP))) {
            boolean canPerformTrade =
                    CoinUtils.totalValueInPlayerInventory(player.inventory) >= soldPrices[index]
                            && soldQuantities[index] > 0;

            if (index < soldItems.length) {
                displayInventory.setItem(index, soldItems[index].copy());
                displayInventory.setChanged();
            }

            // note: always use ItemStack.isEmpty() and not .equals(ItemStack.EMPTY) - caused some really weird behavior
            // that took quite a while to debug
            boolean stackHasSpace = player.inventory.getCarried().isEmpty() ||
                    (player.inventory.getCarried().getItem().equals(soldItems[index].getItem())
                            && player.inventory.getCarried().getCount() + soldItems[index].getCount() <= soldItems[index].getMaxStackSize());

            if (canPerformTrade && stackHasSpace) {
                CoinUtils.removeCoins(player, player.inventory, soldPrices[index]);
                trader.playTradeSound();
                ItemStack itemForTrade = soldItems[index].copy();
                if (player.inventory.getCarried().equals(ItemStack.EMPTY)) {
                    player.inventory.setCarried(itemForTrade);
                } else {
                    player.inventory.setCarried(new ItemStack(soldItems[index].getItem(),
                            player.inventory.getCarried().getCount() + soldItems[index].getCount()));
                }
                soldQuantities[index] = soldQuantities[index] - 1;
                if (!level.isClientSide) {
                    trader.getSells().get(index).setQuantityLeft(trader.getSells().get(index).getQuantityLeft() - 1);
                }
                player.inventory.setChanged();

                if (!tradedYet) {
                    player.awardStat(ExtendedStats.TRADERS_TRADED);
                    if (!player.level.isClientSide()) {
                    	ExtendedCriteriaTriggers.TRAGER_TRADED_TRIGGER.trigger((ServerPlayerEntity)player, traderNPC.getType(), true, itemForTrade);
                    }
                    tradedYet = true;
                }
            }

            return player.inventory.getCarried();
        }

        return super.clicked(index, p_184996_2_, clickType, p_184996_4_);
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int slotId) {
        Slot slot = this.slots.get(slotId);
        ItemStack item = slot.getItem();

        if (slot.container.equals(displayInventory)) {
            if (slot.index < 9) {
            	// Fix "java.lang.ArithmeticException: / by zero" crash when shift-clicking sold-out slot
            	if(displayInventory.getItem(slot.index).getCount()<=0) {
            		return ItemStack.EMPTY;
            	}
                int tradesToPerform = 64 / displayInventory.getItem(slot.index).getCount();
                if (tradesToPerform > soldQuantities[slot.index]) tradesToPerform = soldQuantities[slot.index];
                if (tradesToPerform * soldPrices[slot.index] >= CoinUtils.totalValueInPlayerInventory(player.inventory)) {
                    tradesToPerform = CoinUtils.totalValueInPlayerInventory(player.inventory) / soldPrices[slot.index];
                }

                if (tradesToPerform == 0) return ItemStack.EMPTY;

                if (!tradedYet) {
                    player.awardStat(ExtendedStats.TRADERS_TRADED);
                    tradedYet = true;
                }

                // actually perform buy
                ItemStack stack = displayInventory.getItem(slot.index).copy();
                stack.setCount(tradesToPerform * displayInventory.getItem(slot.index).getCount());

                CoinUtils.removeCoins(player, player.inventory, tradesToPerform * soldPrices[slot.index]);
                if (!player.inventory.add(stack)) {
                    level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), stack));
                }
                player.inventory.setChanged();
                soldQuantities[slot.index] = soldQuantities[slot.index] - tradesToPerform;
                if (!level.isClientSide) {
                    trader.getSells().get(slot.index).setQuantityLeft(trader.getSells().get(slot.index).getQuantityLeft() - tradesToPerform);
                }
                return ItemStack.EMPTY;
            }
        }

        if (slot.container.equals(sellInventory)) {
            if (player.inventory.add(item)) {
                slot.set(ItemStack.EMPTY);
                slot.setChanged();
            }
            return ItemStack.EMPTY;
        }

        // any other slot was shift-clicked so we should attempt to move it to sell
        if (TradeUtils.isValidSellItem(item, trader.getBuys())) {
            if (sellInventory.canAddItem(item)) {
                ItemStack remaining = sellInventory.addItem(item);
                sellInventory.setChanged();

                if (remaining.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    slot.set(remaining);
                }
                slot.setChanged();
                player.inventory.setChanged();
            }
        }
        return ItemStack.EMPTY;
    }

    public void handleSellItems() {
        // sum the counts for each Item
        HashMap<Item, Integer> counts = new HashMap<>();
        for (int i = 0; i < sellInventory.getContainerSize(); i++) {
            ItemStack stack = sellInventory.getItem(i);
            if (counts.containsKey(stack.getItem().getItem())) {
                int count = counts.get(stack.getItem()) + stack.getCount();
                counts.put(stack.getItem(), count);
            } else {
                counts.put(stack.getItem(), stack.getCount());
            }
        }

        int coinsGiven = 0;

        List<ItemStack> soldItems = new ArrayList<>();
        // loop through all trade deals and determine value
        for (ExtendedTrade trade : trader.getBuys()) {
            if (counts.containsKey(trade.getItem().getItem())) {
                int count = counts.get(trade.getItem().getItem());
                int totalTrades = count / trade.getItem().getCount();
                if (totalTrades > trade.getQuantityLeft()) totalTrades = trade.getQuantityLeft();

                soldItems.add(new ItemStack(trade.getItem().getItem()));
                sellInventory.removeItemType(trade.getItem().getItem(), totalTrades * trade.getItem().getCount());
                coinsGiven += totalTrades * trade.getAmount();
                trade.setQuantityLeft(trade.getQuantityLeft() - totalTrades);
                sellInventory.setChanged();
            }
        }

        if (coinsGiven > 0 && !tradedYet) {
            player.awardStat(ExtendedStats.TRADERS_TRADED, 1);
            tradedYet = true;
        }

        // give in the largest currencies first
        List<ItemStack> itemsToGive = new ArrayList<>();
        if (coinsGiven / 100 > 0) {
            int hundreds = coinsGiven / 100;
            itemsToGive.add(new ItemStack(ExtendedItems.SILVER_COIN_HUNDRED.get(), hundreds));
            coinsGiven -= hundreds * 100;
        }
        if (coinsGiven / 10 > 0) {
            int tens = coinsGiven / 10;
            itemsToGive.add(new ItemStack(ExtendedItems.SILVER_COIN_TEN.get(), tens));
            coinsGiven -= tens * 10;
        }
        if (coinsGiven > 0) itemsToGive.add(new ItemStack(ExtendedItems.SILVER_COIN_ONE.get(), coinsGiven));

        for (ItemStack stack : itemsToGive) {
            if (!player.inventory.add(stack)) {
                level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), stack));
            }
        }
        player.inventory.setChanged();

        // if on the server side send a packet to the client that updates the quantities
        if (!player.level.isClientSide()) {
        	for(ItemStack itemSold : soldItems) {
        		ExtendedCriteriaTriggers.TRAGER_TRADED_TRIGGER.trigger((ServerPlayerEntity)player, traderNPC.getType(), false, itemSold);
        	}
        	ExtendedPacketHandler.sendToClient(new ExtendedSPacketUpdateTradeQuantities(trader.getSoldQuantities(), trader.getBoughtQuantities()), (ServerPlayerEntity) player);
        }
    }

    public void setSoldQuantities(int[] soldQuantities) {
        this.soldQuantities = soldQuantities;
    }

    public void setBoughtQuantities(int[] boughtQuantities) {
        this.boughtQuantities = boughtQuantities;
    }
}
