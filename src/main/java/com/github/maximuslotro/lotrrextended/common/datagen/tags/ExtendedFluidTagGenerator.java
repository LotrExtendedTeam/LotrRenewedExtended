package com.github.maximuslotro.lotrrextended.common.datagen.tags;

import java.nio.file.Path;

import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedFluidTagGenerator extends ExtendedTagsProvider<Fluid> {

	@SuppressWarnings("deprecation")
	public ExtendedFluidTagGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, Registry.FLUID, modid, existingFileHelper);
	}

	@Override
    protected void addTags() {}

	@Override
	protected Path getPath(ResourceLocation pId) {
		return this.generator.getOutputFolder().resolve("data/" + pId.getNamespace() + "/tags/fluids/" + pId.getPath() + ".json");
	}
	@Override
	public String getName() {
		return "Extended Renewed Fluid Tags Generator";
	}
}
