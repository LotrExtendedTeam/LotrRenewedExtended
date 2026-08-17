package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedBlockStateUtils;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.ExtendedStructureProcessors;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lotr.common.block.LOTRBlockStates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * A structure processor that replaces blocks in a structure based on a wood pallet mapping.
 * <p>
 * This processor handles block replacement by swapping blocks from a base wood pallet
 * with corresponding blocks from a picked wood pallet. It supports weighted random selection
 * among multiple wood types to vary the structure's wood style.
 * <p>
 * The processor uses a codec for serialization and deserialization of its state,
 * including the base pallet name, an optionally preselected picked pallet name,
 * and a weighted list of wood type entries.
 */
public class WoodPaletteProcessor extends StructureProcessor {
	/**
	 * Codec for serializing and deserializing the processor.
	 * Includes:
	 * - built_pallet: base wood pallet name
	 * - picked_pallet: optionally preselected pallet name (default "null")
	 * - woodTypes: weighted list of wood types with weights
	 */
	public static final Codec<WoodPaletteProcessor> CODEC = RecordCodecBuilder.create((processorInstance) -> processorInstance.group(Codec.STRING.fieldOf("built_pallet").forGetter(WoodPaletteProcessor::getBasePalletName),
		Codec.STRING.fieldOf("picked_pallet").orElse("null").forGetter(WoodPaletteProcessor::getPickedPalletName),
		Codec.mapPair(Codec.STRING.fieldOf("wood_type"), Codec.INT.fieldOf("weight")).codec().listOf().promotePartial(Util.prefix("Pool element: ", ExtendedLog::error))
		.fieldOf("woodTypes").forGetter(WoodPaletteProcessor::getRawPallets)).apply(processorInstance, WoodPaletteProcessor::new));
	/** The currently selected wood pallet to apply during structure processing. May be null if not yet initialized. */
	private WoodPallet pickedPallet;
	/** The base/default wood pallet used as a reference for block replacements. */
	private final WoodPallet basePallet;
	/** Raw list of wood pallet name and weight pairs used to build the weighted pallet selection pool. */
	private final List<Pair<String, Integer>> rawPallets;
	/** Flattened list of pallet names duplicated according to their weight, used for random selection. */
	public final List<String> pallets;
	/** Flag indicating whether the processor has logged a runtime initialization warning. */
	private boolean flaged = false;
	/** Flag indicating whether the pallet replacement has been applied and logged. */
	private boolean applied = false;
	/**
	 * Constructs a WoodPaletteProcessor instance.
	 *
	 * @param defaultName The name of the base wood pallet used for initial block mappings.
	 * @param selectedpallet The optionally preselected wood pallet name; "null" if none.
	 * @param rawPallets List of pairs of wood pallet names and their weights for random selection.
	 */
	public WoodPaletteProcessor(String defaultName, String selectedpallet, List<Pair<String, Integer>> rawPallets) {
		this.rawPallets = rawPallets;
		this.pallets = Lists.newArrayList();

		for(Pair<String, Integer> pair : rawPallets) {
			String pallet = pair.getFirst();
			for(int i = 0; i < pair.getSecond(); ++i) {
				this.pallets.add(pallet);
			}
		}
		if(!selectedpallet.equals("null")) {
			pickedPallet = WoodPallets.getPalletForname(selectedpallet);
		}
		basePallet = WoodPallets.getPalletForname(defaultName);
	}
	/**
	 * Checks if the processor has an initialized picked wood pallet.
	 *
	 * @return true if a picked pallet has been selected, false otherwise.
	 */
	public boolean isInitialized() {
		return pickedPallet!=null;
	}
	/**
	 * Randomly selects a wood pallet from the weighted list.
	 *
	 * @param pRandom Random number generator to select the pallet.
	 * @return The randomly chosen WoodPallet.
	 */
	private WoodPallet getRandomTemplate(Random pRandom) {
		return WoodPallets.getPalletForname(this.pallets.get(pRandom.nextInt(this.pallets.size())));
	}
	/**
	 * Generates and sets the picked pallet randomly from the weighted pool.
	 *
	 * @param random Random instance to use for selection.
	 */
	public void generateAndSetPallet(Random random) {
		pickedPallet = this.getRandomTemplate(random);
	}
	/**
	 * Processes a block during structure placement, replacing blocks from the base wood pallet
	 * with corresponding blocks from the picked wood pallet according to the pallet mappings.
	 * <p>
	 * If the processor is not yet initialized with a picked pallet, it generates one using
	 * the placement settings' random source. It logs a warning and flags if this happens
	 * at runtime unexpectedly.
	 * <p>
	 * Blocks that belong to the base pallet's block map are replaced by the matching block
	 * from the picked pallet. Special handling is done for vertical slabs and slab variants
	 * to ensure correct block type substitutions.
	 * <p>
	 * If the picked pallet is empty or the same as the base pallet, the original block is returned unchanged.
	 * <p>
	 * Air blocks and missing blocks in the picked pallet map are preserved without replacement.
	 *
	 * @param world The world reader providing context about the world.
	 * @param pos The position of the structure being placed.
	 * @param blockPos The position of the block within the structure.
	 * @param blockInfoLocal The block information from the local template.
	 * @param blockInfoWorld The block information as it currently exists in the world.
	 * @param placmentData Placement settings, including random number generator.
	 * @param template The structure template being processed, may be null.
	 * @return A new {@link net.minecraft.world.gen.feature.template.Template.BlockInfo} with the replaced block state if applicable,
	 *         or the original block info if no replacement is performed.
	 */
	@Override
	public Template.BlockInfo process(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData, @Nullable Template template) {
		if(!isInitialized()) {
			ExtendedLog.warn("Needed to set WoodPallet on BlockProcess, this should not happen! Please report this and what you did!");
			generateAndSetPallet(placmentData.getRandom(blockInfoWorld.pos));
			if(!flaged) {
				IllegalStateException e =  new IllegalStateException("WoodPaller set on runTime!");
				e.printStackTrace();
				flaged = true;
			}
		}
		if(!applied) {
			if(ExtendedServerConfig.enableExtendedLogging.get()) {
				ExtendedLog.info("Replacing {} pallet with {}", basePallet.getName(), pickedPallet.getName());
			}
			applied = true;
		}
		if(pickedPallet.isEmpty() || pickedPallet.equals(basePallet)) {
			return blockInfoWorld;
		}
		BlockState blockstate = blockInfoWorld.state;
		if(blockstate.getBlock().equals(Blocks.AIR)) {
			return blockInfoWorld;
		}
		if(basePallet.getBlockMap().containsValue(blockstate.getBlock().getRegistryName())) {
			String key = getKey(basePallet.getBlockMap(), blockstate.getBlock().getRegistryName());
			Block pickedBlock = ForgeRegistries.BLOCKS.getValue(pickedPallet.getBlockMap().get(key));
			//Handle Vanilla Spoofed Vertical Slabs selecting non-vanilla vertical slabs
			if("SLAB_VERT".equals(key) && pickedBlock.equals(Blocks.AIR)) {
				Block newPickedBlock = ForgeRegistries.BLOCKS.getValue(pickedPallet.getBlockMap().get("SLAB"));
				if(pickedBlock.equals(Blocks.AIR)) {
					return blockInfoWorld;
				}
				pickedBlock=newPickedBlock;
			}
			//Handle vertical slabs picking slab
			else if("SLAB".equals(key) && blockstate.hasProperty(LOTRBlockStates.SLAB_AXIS) && blockstate.getValue(LOTRBlockStates.SLAB_AXIS)!=Axis.Y) {
				Block newPickedBlock = ForgeRegistries.BLOCKS.getValue(pickedPallet.getBlockMap().get("SLAB_VERT"));
				if(pickedBlock.equals(Blocks.AIR)) {
					return blockInfoWorld;
				}
				//Replace picked vanilla slab with its special vertical version
				pickedBlock=newPickedBlock;
			}
			//Handle Missing Block Instances
			if(pickedBlock==null) {
				ExtendedLog.error("Found Missing Block in WoodPallet: "+pickedPallet.getName()+", KEY: "+key);
				return blockInfoWorld;
			}
			//ExtendedLog.info("Replaced "+blockstate.getBlock().getRegistryName().toString() +" with"+ pickedBlock.getRegistryName().toString());
			return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(pickedBlock, blockstate), null);
		}
		return blockInfoWorld;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.WOOD_PALETTE_PROCESSOR;
	}
	/**
	 * Gets the currently picked wood pallet.
	 *
	 * @return The picked WoodPallet, or null if none selected.
	 */
	@Nullable
	public WoodPallet getPickedPallet() {
		return pickedPallet;
	}
	/**
	 * Gets the name of the currently picked wood pallet.
	 *
	 * @return The name of the picked pallet, or "null" if none selected.
	 */
	public String getPickedPalletName() {
		if(!isInitialized()) {
			return "null";
		}
		return pickedPallet.getName();
	}
	/**
	 * Gets the base wood pallet used for initial block mappings.
	 *
	 * @return The base WoodPallet.
	 */
	public WoodPallet getBasePallet() {
		return basePallet;
	}
	/**
	 * Gets the name of the base wood pallet.
	 *
	 * @return The base pallet name.
	 */
	public String getBasePalletName() {
		return basePallet.getName();
	}
	/**
	 * Helper method to find the reverse key in a map for a given value.
	 *
	 * @param map The map to search.
	 * @param value The value to find.
	 * @param <K> The key type.
	 * @param <V> The value type.
	 * @return The key associated with the given value, or null if not found.
	 */
	private <K, V> K getKey(Map<K, V> map, V value) {
		for (Entry<K, V> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}
	/**
	 * Gets the raw weighted list of wood pallets.
	 *
	 * @return The list of pairs containing wood pallet names and their weights.
	 */
	public List<Pair<String, Integer>> getRawPallets() {
		return rawPallets;
	}
}