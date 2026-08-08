package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood.WoodPaletteProcessor;
import com.mojang.serialization.Codec;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessor;

/**
 * Registers and holds custom {@link IStructureProcessorType} instances used
 * for various structure processing tasks in the mod.
 */
public class ExtendedStructureProcessors {
	/**
     * Processor for wood palette replacement during structure placement.
     */
	public static final IStructureProcessorType<WoodPaletteProcessor> WOOD_PALETTE_PROCESSOR = register("wood_palette_processor", WoodPaletteProcessor.CODEC);
	/**
     * Processor for weathering bricks.
     */
	public static final IStructureProcessorType<BrickWeathererProcessor> LOTR_WEATHERER_PROCESSOR = register("lotr_brick_weatherer_processor", BrickWeathererProcessor.CODEC);
	/**
     * Processor for protecting the air inside structures
     */
	public static final IStructureProcessorType<ReplaceAirWithGrassVoidsProcessor> GRASS_VOID_AIR_PROCESSOR = register("air_replacer_grass_void_processor", ReplaceAirWithGrassVoidsProcessor.CODEC);
	/**
     * Processor for processing armor stand loot-tables
     */
	public static final IStructureProcessorType<ExtendedArmorStandProcessor> ARMOR_STAND_PROCESSOR = register("armor_stand_processor", ExtendedArmorStandProcessor.CODEC);
	/**
     * Processor for processing block replacements
     */
	public static final IStructureProcessorType<ExtendedBlockReplacementProcessor> BLOCK_REPLACEMENT_PROCESSOR = register("block_replacement", ExtendedBlockReplacementProcessor.CODEC);

	/**
     * Processor that removes thatch, polished granite, beehives, and white wool blocks
     * from structures during generation.
     * <p>
     * Marked TODO to simplify in the future or remove.
     */	
	public static final IStructureProcessorType<GraniteWhiteWoolBeehiveThatchRemoverProcessor> THATCH_POLISHED_GRANITE_BEEHIVE_WHITE_WOOL_PROCESSOR = register("thatch_polished_granite_white_wool_beehive_remover_processor", GraniteWhiteWoolBeehiveThatchRemoverProcessor.CODEC);
     /**
      * Initialization method to trigger class loading and registration.
      * Typically called during mod setup.
      */
	public static void init() {}
	/**
     * Helper method to register a structure processor type.
     *
     * @param <P>   The processor type extending {@link StructureProcessor}.
     * @param name  The unique name of the processor to register.
     * @param codec The codec used for serialization/deserialization of the processor.
     * @return The registered {@link IStructureProcessorType} instance.
     */
	private static <P extends StructureProcessor> IStructureProcessorType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(LotrExtendedMod.MODID, name), () -> codec);
    }
}
