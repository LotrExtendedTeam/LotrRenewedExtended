package com.github.maximuslotro.lotrrextended.common.datagen.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedFluidTagGenerator extends FluidTagsProvider {

	public ExtendedFluidTagGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
    protected void addTags() {}

	@Override
	public String getName() {
		return "Extended Renewed Fluid Tags Generator";
	}
}
