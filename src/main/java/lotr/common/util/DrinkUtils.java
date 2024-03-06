package lotr.common.util;

import java.util.Random;

import lotr.common.item.VesselDrinkItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DrinkUtils {
	public static ItemStack createDrink(Random rand, ItemStack drink) {
		drink.setCount(16);
		VesselDrinkItem.setPotency(drink, VesselDrinkItem.Potency.randomForNPC(rand));
		return drink;
	}

	public static ItemStack drinkOfPotency(Item item, VesselDrinkItem.Potency potency) {
		ItemStack drink = new ItemStack(item);
		drink.setCount(1);
		VesselDrinkItem.setPotency(drink, potency);
		return drink;
	}
}
