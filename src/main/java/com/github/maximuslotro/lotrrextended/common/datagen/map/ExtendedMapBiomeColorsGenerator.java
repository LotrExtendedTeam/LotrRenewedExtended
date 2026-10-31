package com.github.maximuslotro.lotrrextended.common.datagen.map;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBiomePointers;

import net.minecraft.data.DataGenerator;

public class ExtendedMapBiomeColorsGenerator extends ExtendedMapBiomeColorsProvider {

	public ExtendedMapBiomeColorsGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId, 2);
	}

	@Override
	public void makeBiomes() {
		addBiome(ExtendedBiomePointers.BARROW_DOWNS, "7B8E52");
		addBiome(ExtendedBiomePointers.BARROW_DOWNS_RIVER, "677A72");
		addBiome(ExtendedBiomePointers.RIVENDELL, "7DA82D");
		addBiome(ExtendedBiomePointers.RIVENDELL_VALLEY, "86B72A");
		addBiome(ExtendedBiomePointers.RIVENDELL_HILLS, "C4C59E");
		addBiome(ExtendedBiomePointers.RIVENDELL_CLIFFS, "D8D5B1");
		addBiome(ExtendedBiomePointers.RIVENDELL_RIVER, "557CB5");
		addBiome(ExtendedBiomePointers.NURN_RIVER, "324659");
		addBiome(ExtendedBiomePointers.MORGUL_VALE_RIVER, "104622");
		addBiome(ExtendedBiomePointers.MORDOR_RIVER, "22303D");
		addBiome(ExtendedBiomePointers.URUK_HIGHLANDS, "7E935A");
		addBiome(ExtendedBiomePointers.FANGORN_WASTELAND, "647B45");
	}
}