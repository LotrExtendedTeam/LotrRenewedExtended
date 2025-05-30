package com.github.maximuslotro.lotrrextended.common.inventory.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSUpdateTradeQuantitiesPacket;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;

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

        // read displayItems from NBT
        List<ItemStack> displayItems = new ArrayList<>();
        ListNBT list = nbt.getList("displayItems", 10);
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT node = list.getCompound(i);
            if (node != null) {
                ItemStack item = ItemStack.of(node.getCompound("item"));
                displayItems.add(item);
            }
        }
        // Handle packet error
        if(displayItems.size()!=18) {
        	ExtendedLog.error(nbt.getAsString());
        	throw new IllegalArgumentException("Failed opening trade container with non 18 display inventory!");
        }
        // Add display items to display inventory
        for (int i = 0; i < 18; i++) {
        	displayInventory.setItem(i, displayItems.get(i));
        }
        // Strip first 9 items from display inventory for sellItems array?
        List<ItemStack> soldItemsTemp = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
        	if(!displayItems.get(i).isEmpty()) {
        		soldItemsTemp.add(displayItems.get(i));
        	}
        }
        soldItems = soldItemsTemp.toArray(new ItemStack[soldItemsTemp.size()]);

        // Load trade info from packet nbt
        soldPrices = nbt.getIntArray("sp");
        soldQuantities = nbt.getIntArray("sq");
        boughtPrices = nbt.getIntArray("bp");
        boughtQuantities = nbt.getIntArray("bq");
        boughtOriginalQuantities = nbt.getIntArray("boq");
        soldOriginalQuantities = nbt.getIntArray("soq");

        // add buy slots, player buys items from trader here
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(displayInventory, i, 8 + (i * 18), 40) {
                @Override
                public boolean mayPickup(PlayerEntity player) {
                    return true;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        }

        // add sell display slots, shows what player can sell to the trader
        for (int i = 0; i < 9; i++)
            addSlot(new Slot(displayInventory, i + 9, 8 + (i * 18), 92) {
                @Override
                public boolean mayPickup(PlayerEntity player) {
                    return false;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });

        // add actual sell slots, player places items to sell here
        for (int i = 0; i < 9; i++)
            addSlot(new Slot(sellInventory, i, 8 + (i * 18), 141));

        // add inventory slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) // 95
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 188 + i * 18));
        }
        for (int i = 0; i < 9; i++) { // 153
            addSlot(new Slot(inventory, i, 8 + i * 18, 246));
        }
    }
    public static void writeContainerInitData(PacketBuffer extraData, int entityId, int[] boughtPrices,
                                              int[] boughtQuantities, int[] boughtOriginalQuantities, int[] soldPrices,
                                              int[] soldQuantities, int[] soldOriginalQuantities, List<ItemStack> displayItems) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("entityId", entityId);
        nbt.putIntArray("bp", boughtPrices);
        nbt.putIntArray("bq", boughtQuantities);
        nbt.putIntArray("boq", boughtOriginalQuantities);
        nbt.putIntArray("sp", soldPrices);
        nbt.putIntArray("sq", soldQuantities);
        nbt.putIntArray("soq", soldOriginalQuantities);

        ListNBT listnbt = new ListNBT();
        for (ItemStack stack : displayItems) {
            CompoundNBT compoundnbt = new CompoundNBT();
            CompoundNBT itemTag = new CompoundNBT();
            itemTag = stack.save(itemTag);
            compoundnbt.put("item", itemTag);
            listnbt.add(compoundnbt);
        }
        nbt.put("displayItems", listnbt);

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
        if (index < 9 && !displayInventory.getItem(index).equals(ItemStack.EMPTY) && (clickType.equals(ClickType.PICKUP) || clickType.equals(ClickType.PICKUP_ALL))) {
        	if(trader.getTraderData().isTradesLocked()) {
        		return ItemStack.EMPTY;
        	}
            boolean canPerformTrade = CoinUtils.totalValueInPlayerInventory(player.inventory) >= soldPrices[index] && soldQuantities[index] > 0;

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
            	int price = soldPrices[index];
            	if(!CoinUtils.removeCoins(player, player.inventory, price)) {
            		return super.clicked(index, p_184996_2_, clickType, p_184996_4_);
            	}
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
                    trader.getTraderData().getSells().get(index).setQuantityLeft(trader.getTraderData().getSells().get(index).getQuantityLeft() - 1);
                }
                player.inventory.setChanged();

                if (!tradedYet) {
                    player.awardStat(ExtendedStats.TRADERS_TRADED);
                    tradedYet = true;
                }
                if (!player.level.isClientSide()) {
                	ExtendedCriteriaTriggers.TRAGER_TRADED_TRIGGER.trigger((ServerPlayerEntity)player, traderNPC.getType(), true, itemForTrade, price);
                	trader.getTraderData().incrementTradeSoldToPlayerCount(price);
                }
            }

            return player.inventory.getCarried();
        }

        // implement custom PICKUP_ALL logic based on deobfuscated MC code that
        // only evaluates items from the players inventory and not the other containers
        if (clickType == ClickType.PICKUP_ALL && index >= 0) {
            if (index < 27) {
                return ItemStack.EMPTY;
            }

            // Get the slot the player is trying to interact with
            Slot initialSlot = this.slots.get(index);
            ItemStack carriedItem = player.inventory.getCarried(); // Item the player is currently holding

            // Check if the player is holding something and the initial slot is either empty or not allowed to pick up from
            if (!carriedItem.isEmpty() && (initialSlot == null || !initialSlot.hasItem() || !initialSlot.mayPickup(player))) {
                // Decide whether to loop from the start or end of the inventory based on the button pressed
                int startIndex = p_184996_2_ == 0 ? 0 : this.slots.size() - 1;
                int direction = p_184996_2_ == 0 ? 1 : -1;

                // Perform two passes over the slots
                for (int pass = 0; pass < 2; ++pass) {
                    // Loop through the slots in the container
                    for (int i = startIndex; i >= 0 && i < this.slots.size() && carriedItem.getCount() < carriedItem.getMaxStackSize(); i += direction) {
                        Slot currentSlot = this.slots.get(i);
                        if (!currentSlot.container.equals(player.inventory)) {
                            continue;
                        }

                        // Check if the slot contains items, the items can be quick replaced, and the player is allowed to pick them up
                        if (currentSlot.hasItem() && canItemQuickReplace(currentSlot, carriedItem, true) && currentSlot.mayPickup(player) && this.canTakeItemForPickAll(carriedItem, currentSlot)) {
                            ItemStack slotItem = currentSlot.getItem(); // Get the item in the current slot

                            // On the second pass or if the slot is not full, allow item transfer
                            if (pass != 0 || slotItem.getCount() != slotItem.getMaxStackSize()) {
                                // Calculate how many items can be transferred from this slot
                                int transferCount = Math.min(carriedItem.getMaxStackSize() - carriedItem.getCount(), slotItem.getCount());

                                // Remove the items from the slot and add them to the carried stack
                                ItemStack removedStack = currentSlot.remove(transferCount);
                                carriedItem.grow(transferCount); // Increase the carried item stack count

                                // If the slot is now empty, set it to an empty stack
                                if (removedStack.isEmpty()) {
                                    currentSlot.set(ItemStack.EMPTY);
                                }

                                // Notify the slot that the item has been taken
                                currentSlot.onTake(player, removedStack);
                            }
                        }
                    }
                }
            }

            this.broadcastChanges(); // Notify the container that changes have occurred
            return carriedItem;
        }
        // TODO add better validation for allowable move types
        if (clickType == ClickType.QUICK_CRAFT || clickType == ClickType.SWAP || clickType == ClickType.THROW) {
        	return ItemStack.EMPTY;
        }
        // Allow clone as it only works in creative anyways
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
            	if(trader.getTraderData().isTradesLocked()) {
            		return ItemStack.EMPTY;
            	}
            	// Fix "java.lang.ArithmeticException: / by zero" crash when shift-clicking sold-out slot
            	if(displayInventory.getItem(slot.index).getCount()<=0) {
            		return ItemStack.EMPTY;
            	}

                while (player.inventory.getFreeSlot() > -1) {
                    boolean lastTradeThatCanBePerformed = false;

                    int tradesToPerform = displayInventory.getItem(slot.index).getMaxStackSize() / displayInventory.getItem(slot.index).getCount();
                    if (tradesToPerform > soldQuantities[slot.index]) {
                        tradesToPerform = soldQuantities[slot.index];
                        lastTradeThatCanBePerformed = true;
                    }
                    int priceToSellAt = soldPrices[slot.index];
                    if (tradesToPerform * priceToSellAt >= CoinUtils.totalValueInPlayerInventory(player.inventory)) {
                        tradesToPerform = CoinUtils.totalValueInPlayerInventory(player.inventory) / priceToSellAt;
                        lastTradeThatCanBePerformed = true;
                    }

                    if (tradesToPerform == 0) return ItemStack.EMPTY;

                    if (!tradedYet) {
                        player.awardStat(ExtendedStats.TRADERS_TRADED);
                        tradedYet = true;
                    }

                    // actually perform buy
                    ItemStack stack = displayInventory.getItem(slot.index).copy();
                    stack.setCount(tradesToPerform * displayInventory.getItem(slot.index).getCount());

                    CoinUtils.removeCoins(player, player.inventory, tradesToPerform * priceToSellAt);
                    player.inventory.getFreeSlot();
                    if (!player.inventory.add(stack)) {
                        level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), stack));
                    }
                    player.inventory.setChanged();
                    soldQuantities[slot.index] = soldQuantities[slot.index] - tradesToPerform;
                    if (!level.isClientSide) {
                        trader.getTraderData().getSells().get(slot.index).setQuantityLeft(trader.getTraderData().getSells().get(slot.index).getQuantityLeft() - tradesToPerform);
                        ExtendedCriteriaTriggers.TRAGER_TRADED_TRIGGER.trigger((ServerPlayerEntity)player, traderNPC.getType(), true, stack, tradesToPerform * priceToSellAt);
                    	trader.getTraderData().incrementTradeSoldToPlayerCount(tradesToPerform * priceToSellAt);
                    }
                    if (lastTradeThatCanBePerformed) break;
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
        if (!level.isClientSide && TradeUtils.isValidSellItem(item, trader.getTraderData().getBuys())) {
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

    /*
     * ONLY CALL SERVER SIDE!
     * */
    public void handleSellItemsToTrader() {
    	if(trader.getTraderData().isTradesLocked()) {
    		return;
    	}
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
        for (ExtendedTrade trade : trader.getTraderData().getBuys()) {
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

        if (!player.level.isClientSide()) {
        	for(ItemStack itemSold : soldItems) {
        		ExtendedCriteriaTriggers.TRAGER_TRADED_TRIGGER.trigger((ServerPlayerEntity)player, traderNPC.getType(), false, itemSold, coinsGiven);
        	}
        	trader.getTraderData().incrementTradeBoughtFromPlayerCount(coinsGiven);

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
        	ExtendedPacketHandler.sendToClient(new ExtendedSUpdateTradeQuantitiesPacket(trader.getTraderData().getSoldQuantities(), trader.getTraderData().getBoughtQuantities()), (ServerPlayerEntity) player);
        }
    }

    public void setSoldQuantities(int[] soldQuantities) {
        this.soldQuantities = soldQuantities;
    }

    public void setBoughtQuantities(int[] boughtQuantities) {
        this.boughtQuantities = boughtQuantities;
    }
}
