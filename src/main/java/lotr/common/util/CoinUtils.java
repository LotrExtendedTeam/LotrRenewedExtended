package lotr.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.enums.CoinType;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import lotr.common.init.ExtendedItems;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CoinUtils {

    private CoinUtils() {}

    public static CoinType valueOfItem(Item item) {
        if (item.equals(ExtendedItems.SILVER_COIN_ONE.get())) {
            return CoinType.ONE;
        } else if (item.equals(ExtendedItems.SILVER_COIN_TEN.get())) {
            return CoinType.TEN;
        } else if (item.equals(ExtendedItems.SILVER_COIN_HUNDRED.get())) {
            return CoinType.HUNDRED;
        }
        return CoinType.ZERO;
    }

    public static int value(ItemStack stack) {
        if (!isValidCoin(stack)) {
        	return 0;
        }
        return valueOfItem(stack.getItem()).getValue() * stack.getCount();
    }

    public static boolean isValidCoin(ItemStack stack) {
        return ExtendedItemStackUtils.isSameItemStackItemAndTag(stack, ExtendedItems.SILVER_COIN_ONE.get()) ||
        	ExtendedItemStackUtils.isSameItemStackItemAndTag(stack, ExtendedItems.SILVER_COIN_TEN.get()) ||
        	ExtendedItemStackUtils.isSameItemStackItemAndTag(stack, ExtendedItems.SILVER_COIN_HUNDRED.get());
    }

    public static int totalValueInPlayerInventory(PlayerInventory inv) {
        int totalValue = 0;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            totalValue += value(stack);
        }

        return totalValue;
    }

    /**
     * Gets the value of all the coins in the inventory
     * This is called on both the server and client
     * @param inventory An instance of IInventory to check
     * @return The total coin value of the inventory
     * */
    public static int totalValueInInventory(IInventory inventory) {
        int totalValue = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            totalValue += value(stack);
        }

        return totalValue;
    }

    // total the amount of each denomination they have
    private static HashMap<CoinType, Integer> totalOfEachDenomination(PlayerInventory inventory) {
        HashMap<CoinType, Integer> denominations = new HashMap<>();
        denominations.put(CoinType.ONE, 0);
        denominations.put(CoinType.TEN, 0);
        denominations.put(CoinType.HUNDRED, 0);

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (CoinUtils.isValidCoin(stack)) {
            	denominations.merge(CoinUtils.valueOfItem(stack.getItem()), stack.getCount(), Integer::sum);
            }
        }

        return denominations;
    }

    private static Item itemFromDenomination(CoinType denomination) {
        if (denomination == CoinType.ONE) {
            return ExtendedItems.SILVER_COIN_ONE.get();
        } else if (denomination == CoinType.TEN) {
            return ExtendedItems.SILVER_COIN_TEN.get();
        } else if (denomination == CoinType.HUNDRED) {
            return ExtendedItems.SILVER_COIN_HUNDRED.get();
        }

        return null;
    }

    private static void givePlayerCoins(PlayerEntity player, CoinType denomination, int amount) {
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

    private static boolean removeCoinsByDenomination(PlayerEntity player, CoinType denomination, int amount) {
        while (amount > 0) {
            int stackSize = Math.min(amount, 64); // determine the stack size to remove
            ItemStack itemStack = new ItemStack(itemFromDenomination(denomination), 1); //Stack of one
            int slotToRemoveFrom = -1;

            for(int i = 0; i < player.inventory.getContainerSize(); ++i) {
                if (!player.inventory.getItem(i).isEmpty() && ExtendedItemStackUtils.isSameItemStackItemAndTag(itemStack, player.inventory.getItem(i))) {
                    slotToRemoveFrom = i;
                    break;
                }
            }

            if (slotToRemoveFrom > -1) {
            	// Get the removed item from the player inventory, with the min size possible of either the count and stackSize
                ItemStack removedStack = player.inventory.removeItem(slotToRemoveFrom, stackSize);
                amount -= removedStack.getCount(); // reduce the amount by the size of the removed stack
            } else {
                return false; // error case
            }
        }

        return true;
    }

    public static boolean removeCoins(PlayerEntity player, PlayerInventory inventory, int amount) {
        if (CoinUtils.totalValueInPlayerInventory(inventory) < amount) return false;

        HashMap<CoinType, Integer> denominations = totalOfEachDenomination(inventory);

        // attempt to pay directly from a single denomination first
        CoinType[] denominationOrder = new CoinType[] {CoinType.ONE, CoinType.TEN, CoinType.HUNDRED};
        for (CoinType denomination : denominationOrder) {
            int quantity = denominations.get(denomination);

            if (denomination.getValue() * quantity >= amount) {
                int coinsToConsume = 0;
                while (coinsToConsume * denomination.getValue() < amount) {
                    coinsToConsume++;
                }

                // determine change
                int change = (coinsToConsume * denomination.getValue()) - amount;
                if (change >= 10) {
                    int changeInTens = change / 10;
                    int changeInOnes = change % 10;

                    givePlayerCoins(player, CoinType.TEN, changeInTens);
                    if (changeInOnes > 0) {
                    	givePlayerCoins(player, CoinType.ONE, changeInOnes);
                    }
                } else {
                    givePlayerCoins(player, CoinType.ONE, change);
                }

                removeCoinsByDenomination(player, denomination, coinsToConsume); // consume coins
                return true;
            }
        }

        int amountLeft = amount;
        for (CoinType denomination : denominationOrder) {
            if (amountLeft == 0) break;
            int quantity = denominations.get(denomination);

            if (denomination.getValue() * quantity <= amountLeft) {
                removeCoinsByDenomination(player, denomination, quantity);
                amountLeft -= (denomination.getValue() * quantity);
            } else {
                // attempt to make change
                int coinsToConsume = 0;
                while (coinsToConsume * denomination.getValue() < amount) {
                    coinsToConsume++;
                }

                // determine change
                int change = (coinsToConsume * denomination.getValue()) - amount;
                if (change >= 10) {
                    int changeInTens = change / 10;
                    int changeInOnes = change % 10;

                    givePlayerCoins(player, CoinType.TEN, changeInTens);
                    if (changeInOnes > 0) {
                    	givePlayerCoins(player, CoinType.ONE, changeInOnes);
                    }
                } else {
                    givePlayerCoins(player, CoinType.ONE, change);
                }

                removeCoinsByDenomination(player, denomination, coinsToConsume); // consume coins
            }
        }

        return true;
    }
}