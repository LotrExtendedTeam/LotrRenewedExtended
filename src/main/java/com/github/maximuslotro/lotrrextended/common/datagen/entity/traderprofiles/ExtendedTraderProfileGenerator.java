package com.github.maximuslotro.lotrrextended.common.datagen.entity.traderprofiles;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ExtendedTraderProfileGenerator extends ExtendedTraderProfileProvider{

	public ExtendedTraderProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedTraderProfile> pConsumer) {
		//TODO add orange juice, venison, mushroom pie
		make(ExtendedEntities.HOBBIT_BARTENDER.get(), 0)
		.addSBuy(Items.BEEF, 3)
		.addSBuy(Items.PORKCHOP, 3)
		.addSBuy(Items.MUTTON, 3)
		.addSBuy(Items.CHICKEN, 3)
		.addSBuy(Items.RABBIT, 3)
		.addSBuy(ExtendedItems.RAW_DUCK, 3)
		.addSBuy(LOTRItems.CLAY_PLATE, 1)
		.addSBuy(LOTRItems.CLAY_MUG, 1)
		.addSBuy(LOTRItems.PIPEWEED_LEAF, 1)
		.addSBuy(new ItemStack(Items.POTATO, 2), 1)
		.addSBuy(Items.COD, 2)
		.addSBuy(Items.SALMON, 2)
		.addSBuy(Items.WATER_BUCKET, 4)
		.addSBuy(new ItemStack(Items.COAL, 2), 1)
		.addSBuy(LOTRItems.SALT, 10)
		.addSBuy(Items.BUCKET, 3)
		.addSBuy(LOTRItems.FOUR_LEAF_CLOVER, 40)
		.addSSell(Items.COOKED_BEEF, 7)
		.addSSell(Items.COOKED_PORKCHOP, 7)
		.addSSell(Items.COOKED_MUTTON, 7)
		.addSSell(Items.COOKED_RABBIT, 7)
		.addSSell(Items.COOKED_CHICKEN, 6)
		.addSSell(ExtendedItems.DUCK, 6)
		.addSSell(Items.BREAD, 5)
		.addSSell(new ItemStack(LOTRItems.PIPEWEED.get(), 6), 6)
		.addSSell(LOTRItems.SMOKING_PIPE, 15)
		.addSSell(LOTRItems.WOODEN_MUG, 2)
		.addSSell(LOTRItems.CERAMIC_MUG, 2)
		.addSSell(new ItemStack(Items.BAKED_POTATO, 2), 7)
		.addSSell(Items.COOKED_COD, 6)
		.addSSell(Items.COOKED_SALMON, 6)
		.addSSell(LOTRItems.CHOCOLATE_DRINK, 4)
		.addSSell(LOTRItems.APPLE_JUICE, 6)
		.addSSell(LOTRItems.GAMMON, 7)
		.addSSell(Items.RABBIT_STEW, 10)
		.addSSell(Items.MUSHROOM_STEW, 10)
		.addSSell(ExtendedItems.HOBBIT_PANCAKE, 10)
		.addSSell(ExtendedItems.HOBBIT_PANCAKE_SYRUP, 13)
		.addSSell(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER, 15)
		.addSSell(LOTRItems.FINE_PLATE, 2)
		.addSSell(LOTRItems.STONEWARE_PLATE, 2)
		.addSSell(ExtendedItems.HOBBIT_MARRIAGE_RING, 14)
		.addSSell(LOTRItems.HOBBIT_OVEN, 40)
		.addSSell(LOTRItems.HOBBIT_CRAFTING_TABLE, 40)
		.addDrinkSell(LOTRItems.ALE, 6, 8, 10)
		.addDrinkSell(LOTRItems.CIDER, 6, 8, 10)
		.addDrinkSell(LOTRItems.PERRY, 6, 8, 10)
		.save(pConsumer, ExtendedTraderTypes.HOBBIT_BARTENDER);
		//TODO add corn, corn stalk, leak
		make(ExtendedEntities.HOBBIT_FARMER.get(), 0)
		.addSBuy(new ItemStack(Items.BONE_MEAL, 6), 1)
		.addSBuy(Items.STONE_HOE, 1)
		.addSBuy(LOTRItems.BRONZE_HOE, 6)
		.addSBuy(Items.IRON_HOE, 8)
		.addSBuy(Items.BUCKET, 3)
		.addSBuy(Items.WATER_BUCKET, 4)
		.addSSell(Items.HAY_BLOCK, 12)
		.addSSell(Items.LEAD, 8)
		.addSSell(LOTRItems.LETTUCE, 3)
		.addSSell(Items.MILK_BUCKET, 8)
		.addSSell(LOTRItems.PIPEWEED_LEAF, 4)
		.addSSell(LOTRItems.PIPEWEED_SEEDS, 3)
		.addSSell(Items.POTATO, 2)
		.addSSell(Items.WHEAT_SEEDS, 1)
		.addSSell(Items.WHEAT, 2)
		.addMSell(2, Items.WHITE_WOOL,Items.BLACK_WOOL,
			Items.BLUE_WOOL, Items.BROWN_WOOL,
			Items.CYAN_WOOL, Items.GRAY_WOOL,
			Items.GREEN_WOOL, Items.LIGHT_BLUE_WOOL,
			Items.LIGHT_GRAY_WOOL, Items.LIME_WOOL,
			Items.MAGENTA_WOOL, Items.ORANGE_WOOL,
			Items.PINK_WOOL, Items.PURPLE_WOOL,
			Items.RED_WOOL, Items.YELLOW_WOOL)
		.addSSell(ExtendedItems.BRANDING_IRON, 25)
		.addSSell(ExtendedItems.BUTTER, 10)
		
		.save(pConsumer, ExtendedTraderTypes.HOBBIT_FARMER);
	}

}