package com.github.maximuslotro.lotrrextended.common.datagen.world;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedBiomeInformation;

import lotr.common.init.ExtendedBiomes;
import net.minecraft.data.DataGenerator;

public class ExtendedBiomeInformationGenerator extends ExtendedBiomeInformationProvider{

	public ExtendedBiomeInformationGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeBiomeInformation(Consumer<ExtendedBiomeInformation> pConsumer) {
		make(ExtendedBiomes.BARROW_DOWNS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.BARROW_DOWNS_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.FANGORN_WASTELAND.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.GORGOROTH_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.MORDOR_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.MORGUL_VALE_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.NURN_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_CLIFFS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_VALLEY.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.URUK_HIGHLANDS.getInitialisedBiome()).save(pConsumer);
	}
}