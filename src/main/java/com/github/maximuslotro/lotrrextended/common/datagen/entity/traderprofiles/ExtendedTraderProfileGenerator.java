package com.github.maximuslotro.lotrrextended.common.datagen.entity.traderprofiles;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.init.ExtendedEntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;

public class ExtendedTraderProfileGenerator extends ExtendedTraderProfileProvider{

	public ExtendedTraderProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedTraderProfile> pConsumer) {
		make(ExtendedEntities.HOBBIT_BARTENDER.get(), 0)
		.addSBuy(Items.ACACIA_BOAT, 4).addSBuy(Items.ACACIA_BOAT, 4, 3, 3).addMBuy(3, Items.ACACIA_FENCE, Items.ACACIA_SAPLING)
		.addSSell(Items.BEETROOT_SEEDS, 3).addMSell(5, Items.BLUE_BANNER, Items.GLASS_BOTTLE)
		.save(pConsumer, ExtendedTraderTypes.HOBBIT_BARTENDER);
	}

}