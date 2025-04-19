package com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ExtendedSingleJigsawPiece extends SingleJigsawPiece {
	public static final Codec<ExtendedSingleJigsawPiece> CODEC = RecordCodecBuilder.create((p_236833_0_) -> {
		return p_236833_0_.group(templateCodec(), processorsCodec(), projectionCodec(), waterloggingCodec(), maxPlaceDistanceCodec(), 
			maxDepthCodec(), ingnoreBBconflictsCodec(), blacklistNameCodec()).apply(p_236833_0_, ExtendedSingleJigsawPiece::new);
	});

	protected final boolean apply_waterlogging;
	protected final int maxPlaceDistance;
	protected final int maxDepth;
	protected final boolean ingnoreBBconflicts;
	protected final String blacklistName;

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Boolean> waterloggingCodec() {
		return Codec.BOOL.optionalFieldOf("apply_waterlogging", true).forGetter((p_236845_0_) -> {
			return p_236845_0_.apply_waterlogging;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> maxPlaceDistanceCodec() {
		return Codec.INT.optionalFieldOf("max_place_distance", 80).forGetter((p_236845_0_) -> {
			return p_236845_0_.maxPlaceDistance;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> maxDepthCodec() {
		return Codec.INT.optionalFieldOf("max_depth", 7).forGetter((p_236845_0_) -> {
			return p_236845_0_.maxDepth;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Boolean> ingnoreBBconflictsCodec() {
		return Codec.BOOL.optionalFieldOf("ignore_bb_checks", false).forGetter((p_236845_0_) -> {
			return p_236845_0_.ingnoreBBconflicts;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, String> blacklistNameCodec() {
		return Codec.STRING.optionalFieldOf("blacklist_name", "NOTSET").forGetter((p_236845_0_) -> {
			return p_236845_0_.blacklistName;
		});
	}

	protected ExtendedSingleJigsawPiece(Either<ResourceLocation, Template> p_i242007_1_, Supplier<StructureProcessorList> p_i242007_2_, 
		JigsawPattern.PlacementBehaviour p_i242007_3_, boolean apply_waterlogging, int maxPlaceDistance, int maxDepth, boolean ingnoreBBconflicts,
		String blacklist_name) {
		super(p_i242007_1_, p_i242007_2_, p_i242007_3_);
		this.apply_waterlogging = apply_waterlogging;
		this.maxPlaceDistance = maxPlaceDistance;
		this.maxDepth = maxDepth;
		this.ingnoreBBconflicts = ingnoreBBconflicts;
		this.blacklistName = blacklist_name;
	}

	@Override
	protected PlacementSettings getSettings(Rotation pRotation, MutableBoundingBox pBoundingBox, boolean p_230379_3_) {
		PlacementSettings placementsettings = super.getSettings(pRotation, pBoundingBox, p_230379_3_);
		placementsettings.keepLiquids = apply_waterlogging;
		return placementsettings;
	}

	@Override
	public MutableBoundingBox getBoundingBox(TemplateManager pTemplateManager, BlockPos pPos, Rotation pRotation) {
		if(ingnoreBBconflicts) {
			return new MutableBoundingBox(pPos.getX(), pPos.getY(), pPos.getZ(), pPos.getX(), pPos.getY(), pPos.getZ());
		}
		return super.getBoundingBox(pTemplateManager, pPos, pRotation);
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

	public int getMaxPlaceDistance() {
		return maxPlaceDistance;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public boolean doesIngnoreBBconflicts() {
		return ingnoreBBconflicts;
	}

	public String getBlacklistName() {
		return blacklistName;
	}

	public boolean isBlacklistSet() {
		return !blacklistName.equals("NOTSET");
	}
}