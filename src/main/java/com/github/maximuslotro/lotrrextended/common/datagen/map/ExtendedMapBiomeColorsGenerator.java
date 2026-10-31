package com.github.maximuslotro.lotrrextended.common.datagen.map;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBiomes;

import net.minecraft.data.DataGenerator;

public class ExtendedMapBiomeColorsGenerator extends ExtendedMapBiomeColorsProvider {

	public ExtendedMapBiomeColorsGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId, 2);
	}

	@Override
	public void makeBiomes() {
		addBiome(ExtendedBiomes.BARROW_DOWNS, "7B8E52");
		addBiome(ExtendedBiomes.BARROW_DOWNS_RIVER, "677A72");
		addBiome(ExtendedBiomes.RIVENDELL, "7DA82D");
		addBiome(ExtendedBiomes.RIVENDELL_VALLEY, "86B72A");
		addBiome(ExtendedBiomes.RIVENDELL_HILLS, "C4C59E");
		addBiome(ExtendedBiomes.RIVENDELL_CLIFFS, "D8D5B1");
		addBiome(ExtendedBiomes.RIVENDELL_RIVER, "557CB5");
		addBiome(ExtendedBiomes.NURN_RIVER, "324659");
		addBiome(ExtendedBiomes.MORGUL_VALE_RIVER, "104622");
		addBiome(ExtendedBiomes.MORDOR_RIVER, "22303D");
		addBiome(ExtendedBiomes.URUK_HIGHLANDS, "7E935A");
		addBiome(ExtendedBiomes.FANGORN_WASTELAND, "647B45");
	}
}