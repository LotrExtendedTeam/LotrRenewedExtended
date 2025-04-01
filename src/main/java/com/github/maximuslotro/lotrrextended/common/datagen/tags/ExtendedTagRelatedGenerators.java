package com.github.maximuslotro.lotrrextended.common.datagen.tags;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedTagRelatedGenerators {
	public static void gatherData(DataGenerator generator, ExistingFileHelper existingFileHelper, String modid) {
		ExtendedBlockTagGenerator blocktags = new ExtendedBlockTagGenerator(generator, modid, existingFileHelper);
		generator.addProvider(blocktags);
		generator.addProvider(new ExtendedItemTagGenerator(generator, blocktags, modid, existingFileHelper));
		generator.addProvider(new ExtendedFluidTagGenerator(generator, modid, existingFileHelper));
	}
}
