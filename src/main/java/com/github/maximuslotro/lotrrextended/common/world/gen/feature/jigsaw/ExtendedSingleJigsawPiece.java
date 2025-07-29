package com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
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
 *     <li>Add foundations under the structure.</li>
 *     <li>Disable piece from being spawned to low in the world.</li>
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
	public static final List<String> flag_keys = new ArrayList<String>() {{
	    add("reset_max_depth");
	    add("ignore_bb_checks");
	    add("disable_waterlogging");
	    add("shouldnt_protect_y_level");
	    add("snap_to_water_height");
	    add("disable_spawn_above_ground");
	}};
	/**
	 * Codec for serializing and deserializing {@link ExtendedSingleJigsawPiece} from JSON or NBT for data-driven generation.
	 */
	public static final Codec<ExtendedSingleJigsawPiece> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(templateCodec(), processorsCodec(), projectionCodec(), maxPlaceDistanceCodec(), 
			maxDepthCodec(), blacklistNameCodec(), shiftStructureUpByCodec(), chunkCornerOffsetCodec(), foundationBlockCodec(),
			protectableYLevelCodec(), perPieceFlagsCodec()).apply(instance, ExtendedSingleJigsawPiece::new);
	});
	/**
	 * The maximum distance this piece can attempt to place blocks from its starting position.
	 */
	protected final int maxPlaceDistance;
	/**
	 * The maximum recursion depth allowed for structure placement.
	 */
	protected final int maxDepth;

	/**
	 * The blacklist name used to restrict multiple of the same type of buildings from spawning.
	 */
	protected final String blacklistName;

	/**
	 * The vertical offset (in blocks) to shift the structure upward during placement.
	 * A value of <code>1</code> will be the default sunk into ground vanilla setting.
	 */
	protected final int groundLevelDelta;

	/**
	 * The offset applied to chunk corner coordinates for centering structures. (value between 0 and 15)
	 */
	protected final int chunkCornerOffset;

	/**
	 * The blockstate to use when filling foundations beneath the structure.
	 */
	protected final BlockState foundation;

	/**
	 * The y-level threshold for protecting generation for the given piece
	 */
	protected final int protectableYLevel;

	/**
	 * 
	 * A set of key that if present allow for modifying per-piece logic 
	 */
	public final List<String> per_piece_flags;

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> maxPlaceDistanceCodec() {
		return Codec.INT.optionalFieldOf("max_place_distance", 80).forGetter((piece) -> {
			return piece.maxPlaceDistance;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> maxDepthCodec() {
		return Codec.INT.optionalFieldOf("max_depth", -1).forGetter((piece) -> {
			return piece.maxDepth;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, String> blacklistNameCodec() {
		return Codec.STRING.optionalFieldOf("blacklist_name", "NOTSET").forGetter((piece) -> {
			return piece.blacklistName;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> shiftStructureUpByCodec() {
		return Codec.INT.optionalFieldOf("ground_level_delta", 1).forGetter((piece) -> {
			return piece.groundLevelDelta;
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

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Integer> protectableYLevelCodec() {
		return Codec.INT.optionalFieldOf("protectable_y_level", 12).forGetter((piece) -> {
			return piece.protectableYLevel;
		});
	}

	protected static <E extends ExtendedSingleJigsawPiece> RecordCodecBuilder<E, Optional<List<String>>> perPieceFlagsCodec() {
		return Codec.list(Codec.STRING).optionalFieldOf("per_piece_flags").forGetter((piece) -> {
			return Optional.of(piece.per_piece_flags);
		});
	}

	/**
	 * Constructs an {@link ExtendedSingleJigsawPiece} with advanced configuration for structure placement.
	 *
	 * @param template             Either a {@link ResourceLocation} or a {@link Template} representing the structure.
	 * @param processors           The structure processor list supplier.
	 * @param placementType        The placement behavior for the jigsaw pattern.
	 * @param maxPlaceDistance     The maximum placement distance allowed.
	 * @param maxDepth             The maximum recursion depth.
	 * @param blacklist_name       The blacklist name for controlling placement conditions.
	 * @param shiftStructureUpBy   Vertical offset from ground height to spawn at.
	 * @param chunkCornerOffset    Horizontal offset from chunk corner to spawn at.
	 * @param foundation           The block to use for a foundation
	 * @param protectableYLevel    The y-level at which to enforce a spawn minimum height
	 * @param per_piece_flags      A map of key-value pairs to allow for more options than 16, which is the max CODEC can supply
	 */
	protected ExtendedSingleJigsawPiece(Either<ResourceLocation, Template> template, Supplier<StructureProcessorList> processors, 
		JigsawPattern.PlacementBehaviour placementType, int maxPlaceDistance, int maxDepth, String blacklist_name, int shiftStructureUpBy, 
		int chunkCornerOffset, BlockState foundation, int protectableYLevel, Optional<List<String>> per_piece_flags) {
		super(template, processors, placementType);
		this.maxPlaceDistance = maxPlaceDistance;
		this.maxDepth = maxDepth;
		this.blacklistName = blacklist_name;
		this.groundLevelDelta = shiftStructureUpBy;
		this.chunkCornerOffset = chunkCornerOffset;
		this.foundation = foundation;
		this.protectableYLevel = protectableYLevel;
		this.per_piece_flags = per_piece_flags.orElseGet(ArrayList::new);
		if (!flag_keys.containsAll(this.per_piece_flags)) {
		    List<String> extraFields = new ArrayList<>(this.per_piece_flags);
		    extraFields.removeAll(flag_keys); // remove all valid fields, leaving only the extras
		    ExtendedLog.warn(template + " per_piece_flags contains additional unknown fields: " + extraFields);
		}
	}
	/**
	 * Retrieves placement settings with liquid retention set according to {@link #should_apply_waterlogging()}.
	 *
	 * @param pRotation The rotation to apply.
	 * @param pBoundingBox The bounding box of placement.
	 * @param keepJigsawBlocks Whether to keep jigsaw blocks.
	 * @return The configured {@link PlacementSettings}.
	 */
	@Override
	protected PlacementSettings getSettings(Rotation pRotation, MutableBoundingBox pBoundingBox, boolean keepJigsawBlocks) {
		PlacementSettings placementsettings = super.getSettings(pRotation, pBoundingBox, keepJigsawBlocks);
		placementsettings.keepLiquids = should_apply_waterlogging();
		return placementsettings;
	}
	/**
	 * Returns a bounding box for this piece. If {@link #shouldResetMaxDepth()} is true,
	 * it will return a 1x1x1 bounding box at the target position to bypass conflicts.
	 *
	 * @param pTemplateManager The template manager.
	 * @param pPos The target position.
	 * @param pRotation The rotation to apply.
	 * @return The computed {@link MutableBoundingBox}.
	 */
	@Override
	public MutableBoundingBox getBoundingBox(TemplateManager pTemplateManager, BlockPos pPos, Rotation pRotation) {
		if(shouldResetMaxDepth()) {
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
		return "ExtendedSingle[" + this.template + ", aWater=" + ", mPl=" + maxPlaceDistance 
			+ ", mDe=" + maxDepth + ", igBB=" + ", bN=" + blacklistName + " " + ", gLD=" + groundLevelDelta 
			+", cOff="+ chunkCornerOffset + ", found=" + foundation.toString() + ", flags: "+per_piece_flags+"]";
	}
	/**
	 * If true, liquids will propagate through waterloggable blocks; if false, water will stay confined inside the designated blocks.
	 * @return Whether liquids propagate through waterloggable blocks.
	 */
	public boolean should_apply_waterlogging() {
		return !per_piece_flags.contains("disable_waterlogging");
	}
	/**
	 * If true, the piece will snap vertically to water height during placement.
	 * @return Whether the piece should snap to water height of y=62 during placement.
	 */
	public boolean shouldSnapToWaterHeight() {
		return per_piece_flags.contains("snap_to_water_height");
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
		return per_piece_flags.contains("ignore_bb_checks");
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
	 * @return The number of blocks the structure is relative to the ground
	 */
	@Override
	public int getGroundLevelDelta() {
		return groundLevelDelta;
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
	/**
	 * If true, the piece will not be used if it tries generating below a certain y-level threshold
	 * @return Whether the piece should not be used if y level is less a certain y-level threshold
	 */
	public boolean shouldProtectYLevel() {
		return !per_piece_flags.contains("shouldnt_protect_y_level");
	}
	/**
     * Returns the minimum y-level at which the piece can spawn, and not below
     *
     * @return The chunk corner offset.
     */
	public int getProtectableYLevelHeight() {
		return protectableYLevel;
	}
	/**
	 * @return Whether the current max depth stack for a piece should be reset to zero
	 */
	public boolean shouldResetMaxDepth() {
		return per_piece_flags.contains("reset_max_depth");
	}
	/**
	 * @return Whether this piece is allowed to spawn above the ground, used to disable mineshafts spawning above ground level
	 */
	public boolean disableSpawnAboveGround() {
		return per_piece_flags.contains("disable_spawn_above_ground");
	}
}