package lotr.common.util;

import lotr.common.init.ExtendedItems;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // total the amount of each denomination they have
    private static HashMap<Integer, Integer> totalOfEachDenomination(PlayerInventory inventory) {
        HashMap<Integer, Integer> denominations = new HashMap<>();
        denominations.put(1, 0);
        denominations.put(10, 0);
        denominations.put(100, 0);

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (CoinUtils.isValidCoin(stack)) {
                if (CoinUtils.valueOfItem(stack.getItem()) == 1) {
                   denominations.merge(1, stack.getCount(), Integer::sum);
                }
                if (CoinUtils.valueOfItem(stack.getItem()) == 10) {
                    denominations.merge(10, stack.getCount(), Integer::sum);
                }
                if (CoinUtils.valueOfItem(stack.getItem()) == 100) {
                    denominations.merge(100, stack.getCount(), Integer::sum);

                }
            }
        }

        return denominations;
    }

    private static Item itemFromDenomination(int denomination) {
        if (denomination == 1) {
            return ExtendedItems.SILVER_COIN_ONE.get();
        } else if (denomination == 10) {
            return ExtendedItems.SILVER_COIN_TEN.get();
        } else if (denomination == 100) {
            return ExtendedItems.SILVER_COIN_HUNDRED.get();
        }

        return null;
    }

    private static void givePlayerCoins(PlayerEntity player, int denomination, int amount) {
        List<ItemStack> stacks = new ArrayList<>();
        while (amount > 0) {
            if (amount > 64) {
                stacks.add(new ItemStack(CoinUtils.itemFromDenomination(denomination), 64));
                amount -= 64;
            } else {
                stacks.add(new ItemStack(CoinUtils.itemFromDenomination(denomination), amount));
                amount = 0;
            }
        }

        for (ItemStack stack : stacks) {
            if (!player.inventory.add(stack)) {
                ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack);
                player.level.addFreshEntity(itemEntity);
            }
        }
    }

    private static boolean removeCoinsByDenomination(PlayerEntity player, int denomination, int amount) {
        while (amount > 0) {
            int stackSize = Math.min(amount, 64); // determine the stack size to remove
            int slot = player.inventory.findSlotMatchingItem(new ItemStack(itemFromDenomination(denomination), stackSize));
            if (slot > -1) {
                player.inventory.removeItem(slot, stackSize);
                amount -= stackSize; // reduce the amount by the stack size
            } else {
                return false; // error case
            }
        }

        return true;
    }

    public static boolean removeCoins(PlayerEntity player, PlayerInventory inventory, int amount) {
        if (CoinUtils.totalValueInPlayerInventory(inventory) < amount) return false;

        HashMap<Integer, Integer> denominations = totalOfEachDenomination(inventory);

        // attempt to pay directly from a single denomination first
        Integer[] denominationOrder = new Integer[] {1, 10, 100};
        for (int denomination : denominationOrder) {
            int quantity = denominations.get(denomination);

            if (denomination * quantity >= amount) {
                int coinsToConsume = 0;
                while (coinsToConsume * denomination < amount) {
                    coinsToConsume++;
                }

                // determine change
                int change = (coinsToConsume * denomination) - amount;
                if (change >= 10) {
                    int changeInTens = change / 10;
                    int changeInOnes = change % 10;

                    givePlayerCoins(player, denomination / 10, changeInTens);
                    if (changeInOnes > 0) givePlayerCoins(player, 1, changeInOnes);
                } else {
                    givePlayerCoins(player, 1, change);
                }

                removeCoinsByDenomination(player, denomination, coinsToConsume); // consume coins
                return true;
            }
        }

        // TODO: Implement this
        System.out.println("Will have to pay with coins from multiple denominations");


        return true;
    }

}