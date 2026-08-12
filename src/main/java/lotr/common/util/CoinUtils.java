package lotr.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCoinType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCoinItem;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CoinUtils {

	private CoinUtils() {}

	public static ExtendedCoinType getValueFromItem(Item item) {
		if (item instanceof ExtendedCoinItem) {
			return ((ExtendedCoinItem)item).type;
		}
		return ExtendedCoinType.ZERO;
	}

	private static Item getItemFromValue(ExtendedCoinType denomination) {
		if (denomination == ExtendedCoinType.ONE) {
			return ExtendedItems.SILVER_COIN_ONE.get();
		} else if (denomination == ExtendedCoinType.TEN) {
			return ExtendedItems.SILVER_COIN_TEN.get();
		} else if (denomination == ExtendedCoinType.HUNDRED) {
			return ExtendedItems.SILVER_COIN_HUNDRED.get();
		}

		return null;
	}

	public static int value(ItemStack stack) {
		if (!isValidCoin(stack)) {
			return 0;
		}
		return getValueFromItem(stack.getItem()).getValue() * stack.getCount();
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
	private static HashMap<ExtendedCoinType, Integer> totalOfEachDenomination(PlayerInventory inventory) {
		HashMap<ExtendedCoinType, Integer> denominations = new HashMap<>();
		denominations.put(ExtendedCoinType.ONE, 0);
		denominations.put(ExtendedCoinType.TEN, 0);
		denominations.put(ExtendedCoinType.HUNDRED, 0);

		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);
			if (CoinUtils.isValidCoin(stack)) {
				denominations.merge(CoinUtils.getValueFromItem(stack.getItem()), stack.getCount(), Integer::sum);
			}
		}

		return denominations;
	}

	private static void givePlayerCoins(PlayerEntity player, ExtendedCoinType denomination, int amount) {
		List<ItemStack> stacks = new ArrayList<>();
		while (amount > 0) {
			if (amount > 64) {
				stacks.add(new ItemStack(CoinUtils.getItemFromValue(denomination), 64));
				amount -= 64;
			} else {
				stacks.add(new ItemStack(CoinUtils.getItemFromValue(denomination), amount));
				amount = 0;
			}
		}

		for (ItemStack stack : stacks) {
			if (!player.inventory.add(stack)) {
				if(player instanceof ServerPlayerEntity) {
					((ServerPlayerEntity)player).refreshContainer(player.inventoryMenu);
				}
				ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack);
				player.level.addFreshEntity(itemEntity);
			}
		}
	}

	private static boolean removeCoinsByDenomination(PlayerEntity player, ExtendedCoinType denomination, int amount) {
		while (amount > 0) {
			int stackSize = Math.min(amount, 64); // determine the stack size to remove
			ItemStack itemStack = new ItemStack(getItemFromValue(denomination), 1); //Stack of one
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
				if(player instanceof ServerPlayerEntity) {
					((ServerPlayerEntity)player).refreshContainer(player.inventoryMenu);
				}
			} else {
				return false; // error case
			}
		}

		return true;
	}

	public static boolean removeCoins(PlayerEntity player, PlayerInventory inventory, int amount) {
		if (CoinUtils.totalValueInPlayerInventory(inventory) < amount) return false;

		HashMap<ExtendedCoinType, Integer> denominations = totalOfEachDenomination(inventory);

		// attempt to pay directly from a single denomination first
		ExtendedCoinType[] denominationOrder = new ExtendedCoinType[] {ExtendedCoinType.ONE, ExtendedCoinType.TEN, ExtendedCoinType.HUNDRED};
		for (ExtendedCoinType denomination : denominationOrder) {
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

					givePlayerCoins(player, ExtendedCoinType.TEN, changeInTens);
					if (changeInOnes > 0) {
						givePlayerCoins(player, ExtendedCoinType.ONE, changeInOnes);
					}
				} else {
					givePlayerCoins(player, ExtendedCoinType.ONE, change);
				}

				removeCoinsByDenomination(player, denomination, coinsToConsume); // consume coins
				return true;
			}
		}

		int amountLeft = amount;
		for (ExtendedCoinType denomination : denominationOrder) {
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

					givePlayerCoins(player, ExtendedCoinType.TEN, changeInTens);
					if (changeInOnes > 0) {
						givePlayerCoins(player, ExtendedCoinType.ONE, changeInOnes);
					}
				} else {
					givePlayerCoins(player, ExtendedCoinType.ONE, change);
				}

				removeCoinsByDenomination(player, denomination, coinsToConsume); // consume coins
			}
		}

		return true;
	}
}