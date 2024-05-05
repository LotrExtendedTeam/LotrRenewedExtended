package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.world.gen.structure.processor.GraniteWhiteWoolBeehiveThatchRemoverProcessor;
import com.github.maximuslotro.lotrrextended.world.gen.structure.processor.SinglePieceFoundationProcessor;
import com.github.maximuslotro.lotrrextended.world.gen.structure.processor.WoodPaletteProcessor;
import com.mojang.serialization.Codec;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessor;

public class ExtendedStructureProcessors {
	public static final IStructureProcessorType<WoodPaletteProcessor> WOOD_PALETTE_PROCESSOR = register("wood_palette_processor", WoodPaletteProcessor.CODEC);
	public static final IStructureProcessorType<SinglePieceFoundationProcessor> SINGLE_PIECE_FOUNDATIONS_PROCESSOR = register("single_piece_foundations_processor", SinglePieceFoundationProcessor.CODEC);
	public static final IStructureProcessorType<GraniteWhiteWoolBeehiveThatchRemoverProcessor> THATCH_POLISHED_GRANITE_BEEHIVE_WHITE_WOOL_PROCESSOR = register("thatch_polished_granite_white_wool_beehive_remover_processor", GraniteWhiteWoolBeehiveThatchRemoverProcessor.CODEC);
	public static void init() {}

	private static <P extends StructureProcessor> IStructureProcessorType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(LotrExtendedMod.MODID, name), () -> {
            return codec;
        });
    }
}
