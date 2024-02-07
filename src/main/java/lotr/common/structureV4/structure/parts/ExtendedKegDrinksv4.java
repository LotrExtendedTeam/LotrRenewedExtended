package lotr.common.structureV4.structure.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselDrinkItem.Potency;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

//TODO move to Json driven pools
public class ExtendedKegDrinksv4 {
	public static List<RegistryObject<Item>> shireDrinksPool = new ArrayList<RegistryObject<Item>>(){{ add(LOTRItems.ALE); add(LOTRItems.CIDER);add(LOTRItems.PERRY);add(LOTRItems.CHERRY_LIQUEUR);add(LOTRItems.APPLE_JUICE);}};

	public static ItemStack SetDrink(Random rand, List<RegistryObject<Item>> pool) {
		ItemStack drink = new ItemStack((pool.get(rand.nextInt(pool.size()))).get());
		drink.setCount(16);
		VesselDrinkItem.setPotency(drink, genRandomPotency(rand));
		return drink;
	}

	public static Potency genRandomPotency(Random rand) {
		switch(rand.nextInt(4)) {
			case 3:
				return Potency.STRONG;
			case 2:
				return Potency.MODERATE;
			case 1:
				return Potency.LIGHT;
			default:
				return Potency.WEAK;
		}
	}
}
