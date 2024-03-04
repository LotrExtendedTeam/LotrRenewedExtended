package lotr.common.util;

import lotr.common.init.ExtendedItems;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CoinUtils {

    private CoinUtils() {}

    public static int valueOfItem(Item item) {
        if (item.equals(ExtendedItems.SILVER_COIN_ONE.get())) {
            return 1;
        } else if (item.equals(ExtendedItems.SILVER_COIN_TEN.get())) {
            return 10;
        } else if (item.equals(ExtendedItems.SILVER_COIN_HUNDRED.get())) {
            return 100;
        }
        return 0;
    }

    public static int value(ItemStack stack) {
        if (!isValidCoin(stack)) return 0;
        return valueOfItem(stack.getItem()) * stack.getCount();
    }

    public static boolean isValidCoin(ItemStack stack) {
        return stack.getItem() == ExtendedItems.SILVER_COIN_ONE.get() ||
                stack.getItem() == ExtendedItems.SILVER_COIN_TEN.get() ||
                stack.getItem() == ExtendedItems.SILVER_COIN_HUNDRED.get();
    }

    public static int totalValueInPlayerInventory(PlayerInventory inv) {
        int totalValue = 0;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            totalValue += value(stack);
        }

        return totalValue;
    }

    // TODO: brought in from my LOTR: Companions mod could probably be cleaned up
    public static boolean removeCoins(PlayerEntity player, PlayerInventory inventory, int amount) {
        int coinsRemoved = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if (!CoinUtils.isValidCoin(itemStack)) {
                continue;
            }

            int currency = CoinUtils.valueOfItem(itemStack.getItem());
            int value = itemStack.getCount() * currency;

            if (coinsRemoved + value >= amount) {
                // check if change has to be mode
                int amountLeft = amount - coinsRemoved;
                int newDifference = value - amountLeft;

                if (newDifference == 0) {
                    inventory.setItem(i, ItemStack.EMPTY);
                    return true;
                }

                if (newDifference % currency == 0) {
                    // no change has to be made - simply reduce it
                    int newCount = newDifference / currency;
                    if (newCount > 64) {
                        itemStack.setCount(64);
                        List<ItemStack> stacks = new ArrayList<>();
                        int remainder = newCount - 64;
                        while (remainder > 64) {
                            stacks.add(new ItemStack(itemStack.getItem(), 64));
                            remainder -= 64;
                        }
                        if (remainder > 0) {
                            stacks.add(new ItemStack(itemStack.getItem(), remainder));
                        }
                        for (ItemStack stack : stacks) {
                            if (!inventory.add(stack)) {
                                ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack);
                                player.level.addFreshEntity(itemEntity);
                            }
                        }
                    } else {
                        itemStack.setCount(newCount);
                    }
                    return true;
                } else {
                    int currencyOfChange = 1;
                    Item item = ExtendedItems.SILVER_COIN_ONE.get();
                    if (newDifference % 100 == 0) {
                        currencyOfChange = 100;
                        item = ExtendedItems.SILVER_COIN_HUNDRED.get();
                    } else if (newDifference % 10 == 0) {
                        currencyOfChange = 10;
                        item = ExtendedItems.SILVER_COIN_TEN.get();
                    }
                    int newAmount = newDifference / currencyOfChange;
                    if (newAmount > 64) {
                        ItemStack newItemStack = new ItemStack(item, 64);
                        inventory.setItem(i, newItemStack);
                        List<ItemStack> stacks = new ArrayList<>();

                        int remainder = newAmount - 64;
                        while (remainder > 64) {
                            stacks.add(new ItemStack(item, 64));
                            remainder -= 64;
                        }

                        if (remainder > 0) {
                            stacks.add(new ItemStack(item, remainder));
                        }

                        for (ItemStack stack : stacks) {
                            if (!inventory.add(stack)) {
                                ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack);
                                player.level.addFreshEntity(itemEntity);
                            }
                        }
                    } else {
                        ItemStack newItemStack = new ItemStack(item, newAmount);
                        inventory.setItem(i, newItemStack);
                    }
                    return true;
                }
            } else {
                // remove all coins
                coinsRemoved += value;
                inventory.setItem(i, ItemStack.EMPTY);
            }
        }

        return false;
    }

}