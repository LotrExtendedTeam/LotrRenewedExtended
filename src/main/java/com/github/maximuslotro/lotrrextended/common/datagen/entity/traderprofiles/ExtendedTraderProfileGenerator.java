package com.github.maximuslotro.lotrrextended.common.datagen.entity.traderprofiles;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.init.ExtendedEntities;
import net.minecraft.data.DataGenerator;

public class ExtendedTraderProfileGenerator extends ExtendedTraderProfileProvider{

	public ExtendedTraderProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedTraderProfile> pConsumer) {
		make(ExtendedEntities.HOBBIT_BARTENDER.get(), 0)
		.save(pConsumer, ExtendedTraderTypes.HOBBIT_BARTENDER);
	}

}