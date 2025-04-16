package com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;

public class ExtendedSingleJigsawPiece extends SingleJigsawPiece {
	public static final Codec<ExtendedSingleJigsawPiece> CODEC = RecordCodecBuilder.create((p_236833_0_) -> {
		return p_236833_0_.group(templateCodec(), processorsCodec(), projectionCodec(), waterloggingCodec()).apply(p_236833_0_, ExtendedSingleJigsawPiece::new);
	});

	protected final boolean apply_waterlogging;
	
	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Boolean> waterloggingCodec() {
		return Codec.BOOL.optionalFieldOf("apply_waterlogging", true).forGetter((p_236845_0_) -> {
			return p_236845_0_.apply_waterlogging;
		});
	}

	protected ExtendedSingleJigsawPiece(Either<ResourceLocation, Template> p_i242007_1_, Supplier<StructureProcessorList> p_i242007_2_, JigsawPattern.PlacementBehaviour p_i242007_3_, boolean apply_waterlogging) {
		super(p_i242007_1_, p_i242007_2_, p_i242007_3_);
		this.apply_waterlogging = apply_waterlogging;
	}

	@Override
	protected PlacementSettings getSettings(Rotation pRotation, MutableBoundingBox pBoundingBox, boolean p_230379_3_) {
		PlacementSettings placementsettings = super.getSettings(pRotation, pBoundingBox, p_230379_3_);
		placementsettings.keepLiquids = apply_waterlogging;
		return placementsettings;
	}

	@Override
	public IJigsawDeserializer<?> getType() {
		return ExtendedStructuresV5.SINGLE_EXTENDED;
	}

	@Override
	public String toString() {
		return "ExtendedSingle[" + this.template + "]";
	}

	public boolean isApply_waterlogging() {
		return apply_waterlogging;
	}
}
