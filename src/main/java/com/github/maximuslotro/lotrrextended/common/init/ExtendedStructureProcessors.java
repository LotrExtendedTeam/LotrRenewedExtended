package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.world.gen.structure.processor.WoodPaletteProcessor;
import com.mojang.serialization.Codec;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessor;

public class ExtendedStructureProcessors {
	public static final IStructureProcessorType<WoodPaletteProcessor> WOOD_PALETTE_PROCESSOR = register("wood_palette_processor", WoodPaletteProcessor.CODEC);

	public static void init() {}

	private static <P extends StructureProcessor> IStructureProcessorType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(LotrExtendedMod.MODID, name), () -> {
            return codec;
        });
    }
}
