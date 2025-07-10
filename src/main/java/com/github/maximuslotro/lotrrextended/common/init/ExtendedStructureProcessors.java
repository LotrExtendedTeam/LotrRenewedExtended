package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.BrickWeathererProcessor;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.GraniteWhiteWoolBeehiveThatchRemoverProcessor;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.SinglePieceFoundationProcessor;
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
     * Legacy processor for weathering Arnor bricks.
     * <p>
     * Kept for legacy support of 1.5.2 structures.
     * Scheduled for removal by December 31st, 2025.
     * 
     * Looks like it was being used as a processor until 1.7.4
     * Maybe remove by July 31st, 2026?
     * @deprecated Scheduled for removal; retained only for legacy structure compatibility.
     */
	public static final IStructureProcessorType<BrickWeathererProcessor> ARNOR_WEATHERER_PROCESSOR = register("arnor_weatherer_processor", BrickWeathererProcessor.CODEC);
	/**
     * Processor for weathering bricks.
     */
	public static final IStructureProcessorType<BrickWeathererProcessor> LOTR_WEATHERER_PROCESSOR = register("lotr_brick_weatherer_processor", BrickWeathererProcessor.CODEC);
	/**
     * Processor that handles foundation placement for single-piece structures.
     * <p>
     * Currently marked as possibly unused.
     */
	public static final IStructureProcessorType<SinglePieceFoundationProcessor> SINGLE_PIECE_FOUNDATIONS_PROCESSOR = register("single_piece_foundations_processor", SinglePieceFoundationProcessor.CODEC);
	/**
     * Processor that removes thatch, polished granite, beehives, and white wool blocks
     * from structures during generation.
     * <p>
     * Marked TODO to simplify in the future.
     */	public static final IStructureProcessorType<GraniteWhiteWoolBeehiveThatchRemoverProcessor> THATCH_POLISHED_GRANITE_BEEHIVE_WHITE_WOOL_PROCESSOR = register("thatch_polished_granite_white_wool_beehive_remover_processor", GraniteWhiteWoolBeehiveThatchRemoverProcessor.CODEC);
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
