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

    private static void removeCoinsByDenomination(PlayerEntity player, int denomination, int amount) {
        System.out.println("removeCoinsByDenomination(player, " + denomination + ", " + amount + ")");
        while (amount > 0) {
            if (amount > 64) {
                int slot = player.inventory.findSlotMatchingItem(new ItemStack(itemFromDenomination(denomination), amount));
                player.inventory.removeItem(slot, 64);
                amount -= 64;
            } else {
                int slot = player.inventory.findSlotMatchingItem(new ItemStack(itemFromDenomination(denomination), amount));
                if (slot > -1) {
                    player.inventory.removeItem(slot, amount);
                } else {
                    System.out.println("Slot matching " + new ItemStack(itemFromDenomination(denomination), amount) + " return -1. amount = " + amount);
                }
                amount -= amount;
            }
        }
    }

    // TODO: brought in from my LOTR: Companions mod could probably be cleaned up
    public static boolean removeCoins(PlayerEntity player, PlayerInventory inventory, int amount) {
        if (CoinUtils.totalValueInPlayerInventory(inventory) < amount) return false;

        HashMap<Integer, Integer> denominations = totalOfEachDenomination(inventory);
        denominations.forEach((key, value) -> System.out.println(key + " : " + value));

        // attempt to pay directly from a single denomination first

        // TODO: Turn all this into a loop
        // remove ones and be done
        if (denominations.get(1) >= amount) {
            removeCoinsByDenomination(player, 1, amount);
            return true;
        }

        if (denominations.get(10) * 10 >= amount) {
            int coinsToConsume = 0;
            while (coinsToConsume * 10 < amount) {
                coinsToConsume++;
            }

            // determine change in ones
            int change = (coinsToConsume * 10) - amount;
            givePlayerCoins(player, 1, change);

            removeCoinsByDenomination(player, 10, coinsToConsume); // consume coins
            return true;
        }

        if (denominations.get(100) * 100 >= amount) {
            int coinsToConsume = 0;
            while (coinsToConsume * 100 < amount) {
                coinsToConsume++;
            }

            // determine change
            int change = (coinsToConsume * 100) - amount;
            if (change >= 10) {
                int changeInTens = change / 10;
                int changeInOnes = change % 10;

                givePlayerCoins(player, 10, changeInTens);
                if (changeInOnes > 0) givePlayerCoins(player, 1, changeInOnes);
            } else {
                givePlayerCoins(player, 1, change);
            }

            removeCoinsByDenomination(player, 100, coinsToConsume); // consume coins
            return true;
        } else {
            // TODO: This is a lot better than the old way but it doesn't handle the situation if the only way to pay is with
            //  all their denominations together

            System.out.println("Will have to pay with coins from multiple denominations");
        }

        return true;

        /*
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
        return false;*/
    }

}