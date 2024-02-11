package lotr.common.util;

import java.util.Random;

import lotr.common.item.VesselDrinkItem;
import net.minecraft.item.ItemStack;

public class DrinkUtils {
	public static ItemStack createDrink(Random rand, ItemStack drink) {
		drink.setCount(16);
		VesselDrinkItem.setPotency(drink, VesselDrinkItem.Potency.randomForNPC(rand));
		return drink;
	}
}
