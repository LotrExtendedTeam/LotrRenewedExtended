package com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

/**
 * An extension of {@link SingleJigsawPiece} that adds advanced placement constraints,
 * waterlogging control, blacklist handling, and flexible bounding box logic for structure generation.
 * <p>
 * This piece allows you to:
 * <ul>
 *     <li>Enable or disable waterlogging propagation through blocks.</li>
 *     <li>Control maximum placement distance and recursion depth.</li>
 *     <li>Ignore bounding box conflicts for forced placements.</li>
 *     <li>Restrict multiple buildings of the same type using a blacklist name.</li>
 *     <li>Snap placement height to water surface for smoother generation in oceans or rivers.</li>
 *     <li>Shift structures up or down for structure where the bottom block in not the ground block.</li>
 *     <li>Shift structures horizontally through the chunk generated in.</li>
 * </ul>
 * <p>
 * Used in procedural generation frameworks to gain fine-grained control over structure behavior
 * in modded Minecraft environments.
 * <p>
 * Related references:
 * <ul>
 *     <li>{@link net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece}</li>
 *     <li>{@link net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour}</li>
 *     <li>{@link ExtendedStructuresV5#SINGLE_EXTENDED}</li>
 * </ul>
 */
public class ExtendedSingleJigsawPiece extends SingleJigsawPiece {
	/**
	 * Codec for serializing and deserializing {@link ExtendedSingleJigsawPiece} from JSON or NBT for data-driven generation.
	 */
	public static final Codec<ExtendedSingleJigsawPiece> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(templateCodec(), processorsCodec(), projectionCodec(), waterloggingCodec(), maxPlaceDistanceCodec(), 
			maxDepthCodec(), ingnoreBBconflictsCodec(), blacklistNameCodec(), snapUpToWaterheightCodec(), 
			shiftStructureUpByCodec(), chunkCornerOffsetCodec(), foundationBlockCodec()).apply(instance, ExtendedSingleJigsawPiece::new);
	});
	/**
	 * If true, liquids will propagate through waterloggable blocks; if false, water will stay confined inside the designated blocks.
	 */
	protected final boolean apply_waterlogging;
	/**
	 * The maximum distance this piece can attempt to place blocks from its starting position.
	 */
	protected final int maxPlaceDistance;
	/**
	 * The maximum recursion depth allowed for structure placement.
	 */
	protected final int maxDepth;
	/**
	 * If true, bounding box conflicts will be ignored during placement.
	 */
	protected final boolean ingnoreBBconflicts;
	/**
	 * The blacklist name used to restrict multiple of the same type of buildings from spawning.
	 */
	protected final String blacklistName;
	/**
	 * If true, the piece will snap vertically to water height during placement.
	 */
	protected final boolean snapUpToWaterheight;
	/**
	 * The vertical offset (in blocks) to shift the structure upward during placement.
	 * A value of <code>0</code> will not apply any vertical offset.
	 */
	protected final int shiftStructureUpBy;

	/**
	 * The offset applied to chunk corner coordinates for centering structures. (value between 0 and 15)
	 */
	protected final int chunkCornerOffset;

	/**
	 * The blockstate to use when filling foundations beneath the structure.
	 */
	protected final BlockState foundation;

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Boolean> waterloggingCodec() {
		return Codec.BOOL.optionalFieldOf("apply_waterlogging", true).forGetter((piece) -> {
			return piece.apply_waterlogging;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> maxPlaceDistanceCodec() {
		return Codec.INT.optionalFieldOf("max_place_distance", 80).forGetter((piece) -> {
			return piece.maxPlaceDistance;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> maxDepthCodec() {
		return Codec.INT.optionalFieldOf("max_depth", 7).forGetter((piece) -> {
			return piece.maxDepth;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Boolean> ingnoreBBconflictsCodec() {
		return Codec.BOOL.optionalFieldOf("ignore_bb_checks", false).forGetter((piece) -> {
			return piece.ingnoreBBconflicts;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, String> blacklistNameCodec() {
		return Codec.STRING.optionalFieldOf("blacklist_name", "NOTSET").forGetter((piece) -> {
			return piece.blacklistName;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Boolean> snapUpToWaterheightCodec() {
		return Codec.BOOL.optionalFieldOf("snap_to_water_height", false).forGetter((piece) -> {
			return piece.snapUpToWaterheight;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> shiftStructureUpByCodec() {
		return Codec.INT.optionalFieldOf("shift_structure_up_by", 0).forGetter((piece) -> {
			return piece.shiftStructureUpBy;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> chunkCornerOffsetCodec() {
		return Codec.INT.optionalFieldOf("chunk_corner_offset", 7).forGetter((piece) -> {
			return piece.chunkCornerOffset;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, BlockState> foundationBlockCodec() {
		return BlockState.CODEC.optionalFieldOf("foundation_block", Blocks.AIR.defaultBlockState()).forGetter((piece) -> {
			return piece.foundation;
		});
	}
	/**
	 * Constructs an {@link ExtendedSingleJigsawPiece} with advanced configuration for structure placement.
	 *
	 * @param template             Either a {@link ResourceLocation} or a {@link Template} representing the structure.
	 * @param processors           The structure processor list supplier.
	 * @param placementType        The placement behavior for the jigsaw pattern.
	 * @param apply_waterlogging   Whether to enable waterlogging during placement.
	 * @param maxPlaceDistance     The maximum placement distance allowed.
	 * @param maxDepth             The maximum recursion depth.
	 * @param ingnoreBBconflicts   Whether to ignore bounding box conflicts.
	 * @param blacklist_name       The blacklist name for controlling placement conditions.
	 * @param snapUpToWaterheight  Whether to snap placement to water height.
	 * @param shiftStructureUpBy   Vertical offset from ground height to spawn at.
	 * @param chunkCornerOffset    Horizontal offset from chunk corner to spawn at.
	 * @param foundation           The block to use for a foundation
	 */
	protected ExtendedSingleJigsawPiece(Either<ResourceLocation, Template> template, Supplier<StructureProcessorList> processors, 
		JigsawPattern.PlacementBehaviour placementType, boolean apply_waterlogging, int maxPlaceDistance, int maxDepth, boolean ingnoreBBconflicts,
		String blacklist_name, boolean snapUpToWaterheight, int shiftStructureUpBy, int chunkCornerOffset, BlockState foundation) {
		super(template, processors, placementType);
		this.apply_waterlogging = apply_waterlogging;
		this.maxPlaceDistance = maxPlaceDistance;
		this.maxDepth = maxDepth;
		this.ingnoreBBconflicts = ingnoreBBconflicts;
		this.blacklistName = blacklist_name;
		this.snapUpToWaterheight = snapUpToWaterheight;
		this.shiftStructureUpBy = shiftStructureUpBy;
		this.chunkCornerOffset = chunkCornerOffset;
		this.foundation = foundation;
	}
	/**
	 * Retrieves placement settings with liquid retention set according to {@link #apply_waterlogging}.
	 *
	 * @param pRotation The rotation to apply.
	 * @param pBoundingBox The bounding box of placement.
	 * @param keepJigsawBlocks Whether to keep jigsaw blocks.
	 * @return The configured {@link PlacementSettings}.
	 */
	@Override
	protected PlacementSettings getSettings(Rotation pRotation, MutableBoundingBox pBoundingBox, boolean keepJigsawBlocks) {
		PlacementSettings placementsettings = super.getSettings(pRotation, pBoundingBox, keepJigsawBlocks);
		placementsettings.keepLiquids = apply_waterlogging;
		return placementsettings;
	}
	/**
	 * Returns a bounding box for this piece. If {@link #ingnoreBBconflicts} is true,
	 * it will return a 1x1x1 bounding box at the target position to bypass conflicts.
	 *
	 * @param pTemplateManager The template manager.
	 * @param pPos The target position.
	 * @param pRotation The rotation to apply.
	 * @return The computed {@link MutableBoundingBox}.
	 */
	@Override
	public MutableBoundingBox getBoundingBox(TemplateManager pTemplateManager, BlockPos pPos, Rotation pRotation) {
		if(ingnoreBBconflicts) {
			return new MutableBoundingBox(pPos.getX(), pPos.getY(), pPos.getZ(), pPos.getX(), pPos.getY(), pPos.getZ());
		}
		return super.getBoundingBox(pTemplateManager, pPos, pRotation);
	}
	/**
	 * Returns the deserializer type used for this piece.
	 *
	 * @return The {@link IJigsawDeserializer} type.
	 */
	@Override
	public IJigsawDeserializer<?> getType() {
		return ExtendedStructuresV5.SINGLE_EXTENDED;
	}
	/**
	 * Returns a string representation of this piece for debugging.
	 *
	 * @return A string describing the piece.
	 */
	@Override
	public String toString() {
		return "ExtendedSingle[" + this.template + " " + apply_waterlogging + " " + maxPlaceDistance + " "
			+ maxDepth + " " + ingnoreBBconflicts + " " + blacklistName + " " + snapUpToWaterheight + " "
			+ shiftStructureUpBy +" "+ chunkCornerOffset +"]";
	}
	/**
	 * @return Whether liquids propagate through waterloggable blocks.
	 */
	public boolean isApply_waterlogging() {
		return apply_waterlogging;
	}
	/**
	 * @return Whether the piece should snap to water height of y=62 during placement.
	 */
	public boolean shouldSnapToWaterHeight() {
		return snapUpToWaterheight;
	}
	/**
	 * @return The maximum placement distance.
	 */
	public int getMaxPlaceDistance() {
		return maxPlaceDistance;
	}
	/**
	 * @return The maximum recursion depth for placement.
	 */
	public int getMaxDepth() {
		return maxDepth;
	}
	/**
	 * @return Whether bounding box conflicts are ignored during placement.
	 */
	public boolean doesIngnoreBBconflicts() {
		return ingnoreBBconflicts;
	}
	/**
	 * @return The blacklist name used to restrict spawning multiple buildings of the same type.
	 */
	public String getBlacklistName() {
		return blacklistName;
	}
	/**
	 * @return Whether a custom blacklist name has been set.
	 */
	public boolean isBlacklistSet() {
		return !blacklistName.equals("NOTSET");
	}
	/**
	 * @return The number of blocks the structure should be shifted upward during placement.
	 */
	public int getVerticalShiftStructureBy() {
		return shiftStructureUpBy;
	}
	/**
     * Returns the offset applied to chunk corner coordinates for centering structures.
     *
     * @return The chunk corner offset.
     */
	public int getChunkCornerOffset() {
		return chunkCornerOffset;
	}
	/**
	 * @return Get the {@link BlockState} that should be used for foundations if need be.
	 */
	public BlockState getFoundationBlock() {
		return foundation;
	}
}