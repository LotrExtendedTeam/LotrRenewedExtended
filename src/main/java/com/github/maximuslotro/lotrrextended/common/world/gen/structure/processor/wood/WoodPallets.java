package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Blocks;

public class WoodPallets {
	public static Map<String, Supplier<WoodPallet>> pallets = new HashMap<>();
	private static boolean created = false;

	private static void create() {
		// Vanilla Minecraft Pallets + Renewed's Additional Wood Blocks
		pallets.put("minecraft:acacia", () -> new WoodPallet("minecraft:acacia", Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, LOTRBlocks.ACACIA_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_ACACIA_LOG_SLAB.get(), Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD,
				LOTRBlocks.ACACIA_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_ACACIA_WOOD_SLAB.get(), LOTRBlocks.ACACIA_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_ACACIA_WOOD_STAIRS.get(), LOTRBlocks.ACACIA_BRANCH.get(), LOTRBlocks.STRIPPED_ACACIA_BRANCH.get(),
				Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB, Blocks.ACACIA_STAIRS, LOTRBlocks.ACACIA_BEAM.get(),
				LOTRBlocks.ACACIA_BEAM_SLAB.get(), Blocks.ACACIA_FENCE, Blocks.ACACIA_FENCE_GATE, Blocks.ACACIA_DOOR,
				Blocks.ACACIA_TRAPDOOR, Blocks.ACACIA_PRESSURE_PLATE, Blocks.ACACIA_BUTTON,
				Blocks.ACACIA_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.ACACIA_LEAVES, LOTRBlocks.ACACIA_VERTICAL_SLAB.get()));
		pallets.put("minecraft:birch", () -> new WoodPallet("minecraft:birch", Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, LOTRBlocks.BIRCH_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_BIRCH_LOG_SLAB.get(), Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD,
				LOTRBlocks.BIRCH_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_BIRCH_WOOD_SLAB.get(), LOTRBlocks.BIRCH_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_BIRCH_WOOD_STAIRS.get(), LOTRBlocks.BIRCH_BRANCH.get(), LOTRBlocks.STRIPPED_BIRCH_BRANCH.get(),
				Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB, Blocks.BIRCH_STAIRS, LOTRBlocks.BIRCH_BEAM.get(),
				LOTRBlocks.BIRCH_BEAM_SLAB.get(), Blocks.BIRCH_FENCE, Blocks.BIRCH_FENCE_GATE, Blocks.BIRCH_DOOR,
				Blocks.BIRCH_TRAPDOOR, Blocks.BIRCH_PRESSURE_PLATE, Blocks.BIRCH_BUTTON,
				Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.BIRCH_LEAVES, LOTRBlocks.BIRCH_VERTICAL_SLAB.get()));
		pallets.put("minecraft:dark_oak", () -> new WoodPallet("minecraft:dark_oak", Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, LOTRBlocks.DARK_OAK_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_DARK_OAK_LOG_SLAB.get(), Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD,
				LOTRBlocks.DARK_OAK_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_DARK_OAK_WOOD_SLAB.get(), LOTRBlocks.DARK_OAK_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_DARK_OAK_WOOD_STAIRS.get(), LOTRBlocks.DARK_OAK_BRANCH.get(), LOTRBlocks.STRIPPED_DARK_OAK_BRANCH.get(),
				Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_STAIRS, LOTRBlocks.DARK_OAK_BEAM.get(),
				LOTRBlocks.DARK_OAK_BEAM_SLAB.get(), Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_FENCE_GATE, Blocks.DARK_OAK_DOOR,
				Blocks.DARK_OAK_TRAPDOOR, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.DARK_OAK_BUTTON,
				Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.DARK_OAK_LEAVES, LOTRBlocks.DARK_OAK_VERTICAL_SLAB.get()));
		pallets.put("minecraft:jungle", () -> new WoodPallet("minecraft:jungle", Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, LOTRBlocks.JUNGLE_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_JUNGLE_LOG_SLAB.get(), Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD,
				LOTRBlocks.JUNGLE_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_JUNGLE_WOOD_SLAB.get(), LOTRBlocks.JUNGLE_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_JUNGLE_WOOD_STAIRS.get(), LOTRBlocks.JUNGLE_BRANCH.get(), LOTRBlocks.STRIPPED_JUNGLE_BRANCH.get(),
				Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_STAIRS, LOTRBlocks.JUNGLE_BEAM.get(),
				LOTRBlocks.JUNGLE_BEAM_SLAB.get(), Blocks.JUNGLE_FENCE, Blocks.JUNGLE_FENCE_GATE, Blocks.JUNGLE_DOOR,
				Blocks.JUNGLE_TRAPDOOR, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.JUNGLE_BUTTON,
				Blocks.JUNGLE_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.JUNGLE_LEAVES, LOTRBlocks.JUNGLE_VERTICAL_SLAB.get()));
		pallets.put("minecraft:oak", () -> new WoodPallet("minecraft:oak", Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, LOTRBlocks.OAK_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_OAK_LOG_SLAB.get(), Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD,
				LOTRBlocks.OAK_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_OAK_WOOD_SLAB.get(), LOTRBlocks.OAK_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_OAK_WOOD_STAIRS.get(), LOTRBlocks.OAK_BRANCH.get(), LOTRBlocks.STRIPPED_OAK_BRANCH.get(),
				Blocks.OAK_PLANKS, Blocks.OAK_SLAB, Blocks.OAK_STAIRS, LOTRBlocks.OAK_BEAM.get(),
				LOTRBlocks.OAK_BEAM_SLAB.get(), Blocks.OAK_FENCE, Blocks.OAK_FENCE_GATE, Blocks.OAK_DOOR,
				Blocks.OAK_TRAPDOOR, Blocks.OAK_PRESSURE_PLATE, Blocks.OAK_BUTTON,
				Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.OAK_LEAVES, LOTRBlocks.OAK_VERTICAL_SLAB.get()));
		pallets.put("minecraft:spruce", () -> new WoodPallet("minecraft:spruce", Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, LOTRBlocks.SPRUCE_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_SPRUCE_LOG_SLAB.get(), Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD,
				LOTRBlocks.SPRUCE_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_SPRUCE_WOOD_SLAB.get(), LOTRBlocks.SPRUCE_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_SPRUCE_WOOD_STAIRS.get(), LOTRBlocks.SPRUCE_BRANCH.get(), LOTRBlocks.STRIPPED_SPRUCE_BRANCH.get(),
				Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_STAIRS, LOTRBlocks.SPRUCE_BEAM.get(),
				LOTRBlocks.SPRUCE_BEAM_SLAB.get(), Blocks.SPRUCE_FENCE, Blocks.SPRUCE_FENCE_GATE, Blocks.SPRUCE_DOOR,
				Blocks.SPRUCE_TRAPDOOR, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.SPRUCE_BUTTON,
				Blocks.SPRUCE_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.SPRUCE_LEAVES, LOTRBlocks.SPRUCE_VERTICAL_SLAB.get()));

		// Extended's Wood Pallets
		pallets.put("lotrextended:banana", () -> new WoodPallet("lotrextended:banana", ExtendedBlocks.BANANA_LOG.get(), ExtendedBlocks.STRIPPED_BANANA_LOG.get(), ExtendedBlocks.BANANA_LOG_SLAB.get(),
				ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB.get(), ExtendedBlocks.BANANA_WOOD.get(), ExtendedBlocks.STRIPPED_BANANA_WOOD.get(),
				ExtendedBlocks.BANANA_WOOD_SLAB.get(), ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB.get(), ExtendedBlocks.BANANA_WOOD_STAIRS.get(),
				ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS.get(), ExtendedBlocks.BANANA_BRANCH.get(), ExtendedBlocks.STRIPPED_BANANA_BRANCH.get(),
				ExtendedBlocks.BANANA_PLANKS.get(), ExtendedBlocks.BANANA_SLAB.get(), ExtendedBlocks.BANANA_STAIRS.get(), ExtendedBlocks.BANANA_BEAM.get(),
				ExtendedBlocks.BANANA_BEAM_SLAB.get(), ExtendedBlocks.BANANA_FENCE.get(), ExtendedBlocks.BANANA_FENCE_GATE.get(), ExtendedBlocks.BANANA_DOOR.get(),
				ExtendedBlocks.BANANA_TRAPDOOR.get(), ExtendedBlocks.BANANA_PRESSURE_PLATE.get(), ExtendedBlocks.BANANA_BUTTON.get(),
				ExtendedBlocks.BANANA_SIGN.get(), ExtendedBlocks.BANANA_WALL_SIGN.get(), ExtendedBlocks.BANANA_LEAVES.get(), Blocks.AIR));
		pallets.put("lotrextended:chestnut", () -> new WoodPallet("lotrextended:chestnut", ExtendedBlocks.CHESTNUT_LOG.get(), ExtendedBlocks.STRIPPED_CHESTNUT_LOG.get(), ExtendedBlocks.CHESTNUT_LOG_SLAB.get(),
				ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB.get(), ExtendedBlocks.CHESTNUT_WOOD.get(), ExtendedBlocks.STRIPPED_CHESTNUT_WOOD.get(),
				ExtendedBlocks.CHESTNUT_WOOD_SLAB.get(), ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB.get(), ExtendedBlocks.CHESTNUT_WOOD_STAIRS.get(),
				ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS.get(), ExtendedBlocks.CHESTNUT_BRANCH.get(), ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH.get(),
				ExtendedBlocks.CHESTNUT_PLANKS.get(), ExtendedBlocks.CHESTNUT_SLAB.get(), ExtendedBlocks.CHESTNUT_STAIRS.get(), ExtendedBlocks.CHESTNUT_BEAM.get(),
				ExtendedBlocks.CHESTNUT_BEAM_SLAB.get(), ExtendedBlocks.CHESTNUT_FENCE.get(), ExtendedBlocks.CHESTNUT_FENCE_GATE.get(), ExtendedBlocks.CHESTNUT_DOOR.get(),
				ExtendedBlocks.CHESTNUT_TRAPDOOR.get(), ExtendedBlocks.CHESTNUT_PRESSURE_PLATE.get(), ExtendedBlocks.CHESTNUT_BUTTON.get(),
				ExtendedBlocks.CHESTNUT_SIGN.get(), ExtendedBlocks.CHESTNUT_WALL_SIGN.get(), ExtendedBlocks.CHESTNUT_LEAVES.get(), Blocks.AIR));

		// Renewed's Wood Pallets
		pallets.put("lotr:apple", () -> new WoodPallet("lotr:apple", LOTRBlocks.APPLE_LOG.get(), LOTRBlocks.STRIPPED_APPLE_LOG.get(), LOTRBlocks.APPLE_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_APPLE_LOG_SLAB.get(), LOTRBlocks.APPLE_WOOD.get(), LOTRBlocks.STRIPPED_APPLE_WOOD.get(),
				LOTRBlocks.APPLE_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_APPLE_WOOD_SLAB.get(), LOTRBlocks.APPLE_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_APPLE_WOOD_STAIRS.get(), LOTRBlocks.APPLE_BRANCH.get(), LOTRBlocks.STRIPPED_APPLE_BRANCH.get(),
				LOTRBlocks.APPLE_PLANKS.get(), LOTRBlocks.APPLE_SLAB.get(), LOTRBlocks.APPLE_STAIRS.get(), LOTRBlocks.APPLE_BEAM.get(),
				LOTRBlocks.APPLE_BEAM_SLAB.get(), LOTRBlocks.APPLE_FENCE.get(), LOTRBlocks.APPLE_FENCE_GATE.get(), LOTRBlocks.APPLE_DOOR.get(),
				LOTRBlocks.APPLE_TRAPDOOR.get(), LOTRBlocks.APPLE_PRESSURE_PLATE.get(), LOTRBlocks.APPLE_BUTTON.get(),
				LOTRBlocks.APPLE_SIGN.get(), LOTRBlocks.APPLE_WALL_SIGN.get(), LOTRBlocks.APPLE_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:aspen", () -> new WoodPallet("lotr:aspen", LOTRBlocks.ASPEN_LOG.get(), LOTRBlocks.STRIPPED_ASPEN_LOG.get(), LOTRBlocks.ASPEN_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_ASPEN_LOG_SLAB.get(), LOTRBlocks.ASPEN_WOOD.get(), LOTRBlocks.STRIPPED_ASPEN_WOOD.get(),
				LOTRBlocks.ASPEN_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_ASPEN_WOOD_SLAB.get(), LOTRBlocks.ASPEN_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_ASPEN_WOOD_STAIRS.get(), LOTRBlocks.ASPEN_BRANCH.get(), LOTRBlocks.STRIPPED_ASPEN_BRANCH.get(),
				LOTRBlocks.ASPEN_PLANKS.get(), LOTRBlocks.ASPEN_SLAB.get(), LOTRBlocks.ASPEN_STAIRS.get(), LOTRBlocks.ASPEN_BEAM.get(),
				LOTRBlocks.ASPEN_BEAM_SLAB.get(), LOTRBlocks.ASPEN_FENCE.get(), LOTRBlocks.ASPEN_FENCE_GATE.get(), LOTRBlocks.ASPEN_DOOR.get(),
				LOTRBlocks.ASPEN_TRAPDOOR.get(), LOTRBlocks.ASPEN_PRESSURE_PLATE.get(), LOTRBlocks.ASPEN_BUTTON.get(),
				LOTRBlocks.ASPEN_SIGN.get(), LOTRBlocks.ASPEN_WALL_SIGN.get(), LOTRBlocks.ASPEN_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:beech", () -> new WoodPallet("lotr:beech", LOTRBlocks.BEECH_LOG.get(), LOTRBlocks.STRIPPED_BEECH_LOG.get(), LOTRBlocks.BEECH_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_BEECH_LOG_SLAB.get(), LOTRBlocks.BEECH_WOOD.get(), LOTRBlocks.STRIPPED_BEECH_WOOD.get(),
				LOTRBlocks.BEECH_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_BEECH_WOOD_SLAB.get(), LOTRBlocks.BEECH_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_BEECH_WOOD_STAIRS.get(), LOTRBlocks.BEECH_BRANCH.get(), LOTRBlocks.STRIPPED_BEECH_BRANCH.get(),
				LOTRBlocks.BEECH_PLANKS.get(), LOTRBlocks.BEECH_SLAB.get(), LOTRBlocks.BEECH_STAIRS.get(), LOTRBlocks.BEECH_BEAM.get(),
				LOTRBlocks.BEECH_BEAM_SLAB.get(), LOTRBlocks.BEECH_FENCE.get(), LOTRBlocks.BEECH_FENCE_GATE.get(), LOTRBlocks.BEECH_DOOR.get(),
				LOTRBlocks.BEECH_TRAPDOOR.get(), LOTRBlocks.BEECH_PRESSURE_PLATE.get(), LOTRBlocks.BEECH_BUTTON.get(),
				LOTRBlocks.BEECH_SIGN.get(), LOTRBlocks.BEECH_WALL_SIGN.get(), LOTRBlocks.BEECH_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:cedar", () -> new WoodPallet("lotr:cedar", LOTRBlocks.CEDAR_LOG.get(), LOTRBlocks.STRIPPED_CEDAR_LOG.get(), LOTRBlocks.CEDAR_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_CEDAR_LOG_SLAB.get(), LOTRBlocks.CEDAR_WOOD.get(), LOTRBlocks.STRIPPED_CEDAR_WOOD.get(),
				LOTRBlocks.CEDAR_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_CEDAR_WOOD_SLAB.get(), LOTRBlocks.CEDAR_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_CEDAR_WOOD_STAIRS.get(), LOTRBlocks.CEDAR_BRANCH.get(), LOTRBlocks.STRIPPED_CEDAR_BRANCH.get(),
				LOTRBlocks.CEDAR_PLANKS.get(), LOTRBlocks.CEDAR_SLAB.get(), LOTRBlocks.CEDAR_STAIRS.get(), LOTRBlocks.CEDAR_BEAM.get(),
				LOTRBlocks.CEDAR_BEAM_SLAB.get(), LOTRBlocks.CEDAR_FENCE.get(), LOTRBlocks.CEDAR_FENCE_GATE.get(), LOTRBlocks.CEDAR_DOOR.get(),
				LOTRBlocks.CEDAR_TRAPDOOR.get(), LOTRBlocks.CEDAR_PRESSURE_PLATE.get(), LOTRBlocks.CEDAR_BUTTON.get(),
				LOTRBlocks.CEDAR_SIGN.get(), LOTRBlocks.CEDAR_WALL_SIGN.get(), LOTRBlocks.CEDAR_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:charred", () -> new WoodPallet("lotr:charred", LOTRBlocks.CHARRED_LOG.get(), LOTRBlocks.STRIPPED_CHARRED_LOG.get(), LOTRBlocks.CHARRED_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_CHARRED_LOG_SLAB.get(), LOTRBlocks.CHARRED_WOOD.get(), LOTRBlocks.STRIPPED_CHARRED_WOOD.get(),
				LOTRBlocks.CHARRED_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_CHARRED_WOOD_SLAB.get(), LOTRBlocks.CHARRED_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_CHARRED_WOOD_STAIRS.get(), LOTRBlocks.CHARRED_BRANCH.get(), LOTRBlocks.STRIPPED_CHARRED_BRANCH.get(),
				LOTRBlocks.CHARRED_PLANKS.get(), LOTRBlocks.CHARRED_SLAB.get(), LOTRBlocks.CHARRED_STAIRS.get(), LOTRBlocks.CHARRED_BEAM.get(),
				LOTRBlocks.CHARRED_BEAM_SLAB.get(), LOTRBlocks.CHARRED_FENCE.get(), LOTRBlocks.CHARRED_FENCE_GATE.get(), LOTRBlocks.CHARRED_DOOR.get(),
				LOTRBlocks.CHARRED_TRAPDOOR.get(), LOTRBlocks.CHARRED_PRESSURE_PLATE.get(), LOTRBlocks.CHARRED_BUTTON.get(),
				LOTRBlocks.CHARRED_SIGN.get(), LOTRBlocks.CHARRED_WALL_SIGN.get(), Blocks.AIR, Blocks.AIR));
		pallets.put("lotr:cherry", () -> new WoodPallet("lotr:cherry", LOTRBlocks.CHERRY_LOG.get(), LOTRBlocks.STRIPPED_CHERRY_LOG.get(), LOTRBlocks.CHERRY_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_CHERRY_LOG_SLAB.get(), LOTRBlocks.CHERRY_WOOD.get(), LOTRBlocks.STRIPPED_CHERRY_WOOD.get(),
				LOTRBlocks.CHERRY_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_CHERRY_WOOD_SLAB.get(), LOTRBlocks.CHERRY_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_CHERRY_WOOD_STAIRS.get(), LOTRBlocks.CHERRY_BRANCH.get(), LOTRBlocks.STRIPPED_CHERRY_BRANCH.get(),
				LOTRBlocks.CHERRY_PLANKS.get(), LOTRBlocks.CHERRY_SLAB.get(), LOTRBlocks.CHERRY_STAIRS.get(), LOTRBlocks.CHERRY_BEAM.get(),
				LOTRBlocks.CHERRY_BEAM_SLAB.get(), LOTRBlocks.CHERRY_FENCE.get(), LOTRBlocks.CHERRY_FENCE_GATE.get(), LOTRBlocks.CHERRY_DOOR.get(),
				LOTRBlocks.CHERRY_TRAPDOOR.get(), LOTRBlocks.CHERRY_PRESSURE_PLATE.get(), LOTRBlocks.CHERRY_BUTTON.get(),
				LOTRBlocks.CHERRY_SIGN.get(), LOTRBlocks.CHERRY_WALL_SIGN.get(), LOTRBlocks.CHERRY_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:culumalda", () -> new WoodPallet("lotr:culumalda", LOTRBlocks.CULUMALDA_LOG.get(), LOTRBlocks.STRIPPED_CULUMALDA_LOG.get(), LOTRBlocks.CULUMALDA_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_CULUMALDA_LOG_SLAB.get(), LOTRBlocks.CULUMALDA_WOOD.get(), LOTRBlocks.STRIPPED_CULUMALDA_WOOD.get(),
				LOTRBlocks.CULUMALDA_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_CULUMALDA_WOOD_SLAB.get(), LOTRBlocks.CULUMALDA_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_CULUMALDA_WOOD_STAIRS.get(), LOTRBlocks.CULUMALDA_BRANCH.get(), LOTRBlocks.STRIPPED_CULUMALDA_BRANCH.get(),
				LOTRBlocks.CULUMALDA_PLANKS.get(), LOTRBlocks.CULUMALDA_SLAB.get(), LOTRBlocks.CULUMALDA_STAIRS.get(), LOTRBlocks.CULUMALDA_BEAM.get(),
				LOTRBlocks.CULUMALDA_BEAM_SLAB.get(), LOTRBlocks.CULUMALDA_FENCE.get(), LOTRBlocks.CULUMALDA_FENCE_GATE.get(), LOTRBlocks.CULUMALDA_DOOR.get(),
				LOTRBlocks.CULUMALDA_TRAPDOOR.get(), LOTRBlocks.CULUMALDA_PRESSURE_PLATE.get(), LOTRBlocks.CULUMALDA_BUTTON.get(),
				LOTRBlocks.CULUMALDA_SIGN.get(), LOTRBlocks.CULUMALDA_WALL_SIGN.get(), LOTRBlocks.CULUMALDA_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:cypress", () -> new WoodPallet("lotr:cypress", LOTRBlocks.CYPRESS_LOG.get(), LOTRBlocks.STRIPPED_CYPRESS_LOG.get(), LOTRBlocks.CYPRESS_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_CYPRESS_LOG_SLAB.get(), LOTRBlocks.CYPRESS_WOOD.get(), LOTRBlocks.STRIPPED_CYPRESS_WOOD.get(),
				LOTRBlocks.CYPRESS_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_CYPRESS_WOOD_SLAB.get(), LOTRBlocks.CYPRESS_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_CYPRESS_WOOD_STAIRS.get(), LOTRBlocks.CYPRESS_BRANCH.get(), LOTRBlocks.STRIPPED_CYPRESS_BRANCH.get(),
				LOTRBlocks.CYPRESS_PLANKS.get(), LOTRBlocks.CYPRESS_SLAB.get(), LOTRBlocks.CYPRESS_STAIRS.get(), LOTRBlocks.CYPRESS_BEAM.get(),
				LOTRBlocks.CYPRESS_BEAM_SLAB.get(), LOTRBlocks.CYPRESS_FENCE.get(), LOTRBlocks.CYPRESS_FENCE_GATE.get(), LOTRBlocks.CYPRESS_DOOR.get(),
				LOTRBlocks.CYPRESS_TRAPDOOR.get(), LOTRBlocks.CYPRESS_PRESSURE_PLATE.get(), LOTRBlocks.CYPRESS_BUTTON.get(),
				LOTRBlocks.CYPRESS_SIGN.get(), LOTRBlocks.CYPRESS_WALL_SIGN.get(), LOTRBlocks.CYPRESS_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:fir", () -> new WoodPallet("lotr:fir", LOTRBlocks.FIR_LOG.get(), LOTRBlocks.STRIPPED_FIR_LOG.get(), LOTRBlocks.FIR_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_FIR_LOG_SLAB.get(), LOTRBlocks.FIR_WOOD.get(), LOTRBlocks.STRIPPED_FIR_WOOD.get(),
				LOTRBlocks.FIR_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_FIR_WOOD_SLAB.get(), LOTRBlocks.FIR_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_FIR_WOOD_STAIRS.get(), LOTRBlocks.FIR_BRANCH.get(), LOTRBlocks.STRIPPED_FIR_BRANCH.get(),
				LOTRBlocks.FIR_PLANKS.get(), LOTRBlocks.FIR_SLAB.get(), LOTRBlocks.FIR_STAIRS.get(), LOTRBlocks.FIR_BEAM.get(),
				LOTRBlocks.FIR_BEAM_SLAB.get(), LOTRBlocks.FIR_FENCE.get(), LOTRBlocks.FIR_FENCE_GATE.get(), LOTRBlocks.FIR_DOOR.get(),
				LOTRBlocks.FIR_TRAPDOOR.get(), LOTRBlocks.FIR_PRESSURE_PLATE.get(), LOTRBlocks.FIR_BUTTON.get(),
				LOTRBlocks.FIR_SIGN.get(), LOTRBlocks.FIR_WALL_SIGN.get(), LOTRBlocks.FIR_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:green_oak", () -> new WoodPallet("lotr:green_oak", LOTRBlocks.GREEN_OAK_LOG.get(), LOTRBlocks.STRIPPED_GREEN_OAK_LOG.get(), LOTRBlocks.GREEN_OAK_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_GREEN_OAK_LOG_SLAB.get(), LOTRBlocks.GREEN_OAK_WOOD.get(), LOTRBlocks.STRIPPED_GREEN_OAK_WOOD.get(),
				LOTRBlocks.GREEN_OAK_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_GREEN_OAK_WOOD_SLAB.get(), LOTRBlocks.GREEN_OAK_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_GREEN_OAK_WOOD_STAIRS.get(), LOTRBlocks.GREEN_OAK_BRANCH.get(), LOTRBlocks.STRIPPED_GREEN_OAK_BRANCH.get(),
				LOTRBlocks.GREEN_OAK_PLANKS.get(), LOTRBlocks.GREEN_OAK_SLAB.get(), LOTRBlocks.GREEN_OAK_STAIRS.get(), LOTRBlocks.GREEN_OAK_BEAM.get(),
				LOTRBlocks.GREEN_OAK_BEAM_SLAB.get(), LOTRBlocks.GREEN_OAK_FENCE.get(), LOTRBlocks.GREEN_OAK_FENCE_GATE.get(), LOTRBlocks.GREEN_OAK_DOOR.get(),
				LOTRBlocks.GREEN_OAK_TRAPDOOR.get(), LOTRBlocks.GREEN_OAK_PRESSURE_PLATE.get(), LOTRBlocks.GREEN_OAK_BUTTON.get(),
				LOTRBlocks.GREEN_OAK_SIGN.get(), LOTRBlocks.GREEN_OAK_WALL_SIGN.get(), LOTRBlocks.GREEN_OAK_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:holly", () -> new WoodPallet("lotr:holly", LOTRBlocks.HOLLY_LOG.get(), LOTRBlocks.STRIPPED_HOLLY_LOG.get(), LOTRBlocks.HOLLY_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_HOLLY_LOG_SLAB.get(), LOTRBlocks.HOLLY_WOOD.get(), LOTRBlocks.STRIPPED_HOLLY_WOOD.get(),
				LOTRBlocks.HOLLY_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_HOLLY_WOOD_SLAB.get(), LOTRBlocks.HOLLY_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_HOLLY_WOOD_STAIRS.get(), LOTRBlocks.HOLLY_BRANCH.get(), LOTRBlocks.STRIPPED_HOLLY_BRANCH.get(),
				LOTRBlocks.HOLLY_PLANKS.get(), LOTRBlocks.HOLLY_SLAB.get(), LOTRBlocks.HOLLY_STAIRS.get(), LOTRBlocks.HOLLY_BEAM.get(),
				LOTRBlocks.HOLLY_BEAM_SLAB.get(), LOTRBlocks.HOLLY_FENCE.get(), LOTRBlocks.HOLLY_FENCE_GATE.get(), LOTRBlocks.HOLLY_DOOR.get(),
				LOTRBlocks.HOLLY_TRAPDOOR.get(), LOTRBlocks.HOLLY_PRESSURE_PLATE.get(), LOTRBlocks.HOLLY_BUTTON.get(),
				LOTRBlocks.HOLLY_SIGN.get(), LOTRBlocks.HOLLY_WALL_SIGN.get(), LOTRBlocks.HOLLY_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:lairelosse", () -> new WoodPallet("lotr:lairelosse", LOTRBlocks.LAIRELOSSE_LOG.get(), LOTRBlocks.STRIPPED_LAIRELOSSE_LOG.get(), LOTRBlocks.LAIRELOSSE_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_LAIRELOSSE_LOG_SLAB.get(), LOTRBlocks.LAIRELOSSE_WOOD.get(), LOTRBlocks.STRIPPED_LAIRELOSSE_WOOD.get(),
				LOTRBlocks.LAIRELOSSE_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_LAIRELOSSE_WOOD_SLAB.get(), LOTRBlocks.LAIRELOSSE_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_LAIRELOSSE_WOOD_STAIRS.get(), LOTRBlocks.LAIRELOSSE_BRANCH.get(), LOTRBlocks.STRIPPED_LAIRELOSSE_BRANCH.get(),
				LOTRBlocks.LAIRELOSSE_PLANKS.get(), LOTRBlocks.LAIRELOSSE_SLAB.get(), LOTRBlocks.LAIRELOSSE_STAIRS.get(), LOTRBlocks.LAIRELOSSE_BEAM.get(),
				LOTRBlocks.LAIRELOSSE_BEAM_SLAB.get(), LOTRBlocks.LAIRELOSSE_FENCE.get(), LOTRBlocks.LAIRELOSSE_FENCE_GATE.get(), LOTRBlocks.LAIRELOSSE_DOOR.get(),
				LOTRBlocks.LAIRELOSSE_TRAPDOOR.get(), LOTRBlocks.LAIRELOSSE_PRESSURE_PLATE.get(), LOTRBlocks.LAIRELOSSE_BUTTON.get(),
				LOTRBlocks.LAIRELOSSE_SIGN.get(), LOTRBlocks.LAIRELOSSE_WALL_SIGN.get(), LOTRBlocks.LAIRELOSSE_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:larch", () -> new WoodPallet("lotr:larch", LOTRBlocks.LARCH_LOG.get(), LOTRBlocks.STRIPPED_LARCH_LOG.get(), LOTRBlocks.LARCH_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_LARCH_LOG_SLAB.get(), LOTRBlocks.LARCH_WOOD.get(), LOTRBlocks.STRIPPED_LARCH_WOOD.get(),
				LOTRBlocks.LARCH_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_LARCH_WOOD_SLAB.get(), LOTRBlocks.LARCH_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_LARCH_WOOD_STAIRS.get(), LOTRBlocks.LARCH_BRANCH.get(), LOTRBlocks.STRIPPED_LARCH_BRANCH.get(),
				LOTRBlocks.LARCH_PLANKS.get(), LOTRBlocks.LARCH_SLAB.get(), LOTRBlocks.LARCH_STAIRS.get(), LOTRBlocks.LARCH_BEAM.get(),
				LOTRBlocks.LARCH_BEAM_SLAB.get(), LOTRBlocks.LARCH_FENCE.get(), LOTRBlocks.LARCH_FENCE_GATE.get(), LOTRBlocks.LARCH_DOOR.get(),
				LOTRBlocks.LARCH_TRAPDOOR.get(), LOTRBlocks.LARCH_PRESSURE_PLATE.get(), LOTRBlocks.LARCH_BUTTON.get(),
				LOTRBlocks.LARCH_SIGN.get(), LOTRBlocks.LARCH_WALL_SIGN.get(), LOTRBlocks.LARCH_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:lebethron", () -> new WoodPallet("lotr:lebethron", LOTRBlocks.LEBETHRON_LOG.get(), LOTRBlocks.STRIPPED_LEBETHRON_LOG.get(), LOTRBlocks.LEBETHRON_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_LEBETHRON_LOG_SLAB.get(), LOTRBlocks.LEBETHRON_WOOD.get(), LOTRBlocks.STRIPPED_LEBETHRON_WOOD.get(),
				LOTRBlocks.LEBETHRON_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_LEBETHRON_WOOD_SLAB.get(), LOTRBlocks.LEBETHRON_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_LEBETHRON_WOOD_STAIRS.get(), LOTRBlocks.LEBETHRON_BRANCH.get(), LOTRBlocks.STRIPPED_LEBETHRON_BRANCH.get(),
				LOTRBlocks.LEBETHRON_PLANKS.get(), LOTRBlocks.LEBETHRON_SLAB.get(), LOTRBlocks.LEBETHRON_STAIRS.get(), LOTRBlocks.LEBETHRON_BEAM.get(),
				LOTRBlocks.LEBETHRON_BEAM_SLAB.get(), LOTRBlocks.LEBETHRON_FENCE.get(), LOTRBlocks.LEBETHRON_FENCE_GATE.get(), LOTRBlocks.LEBETHRON_DOOR.get(),
				LOTRBlocks.LEBETHRON_TRAPDOOR.get(), LOTRBlocks.LEBETHRON_PRESSURE_PLATE.get(), LOTRBlocks.LEBETHRON_BUTTON.get(),
				LOTRBlocks.LEBETHRON_SIGN.get(), LOTRBlocks.LEBETHRON_WALL_SIGN.get(), LOTRBlocks.LEBETHRON_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:mallorn", () -> new WoodPallet("lotr:mallorn", LOTRBlocks.MALLORN_LOG.get(), LOTRBlocks.STRIPPED_MALLORN_LOG.get(), LOTRBlocks.MALLORN_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_MALLORN_LOG_SLAB.get(), LOTRBlocks.MALLORN_WOOD.get(), LOTRBlocks.STRIPPED_MALLORN_WOOD.get(),
				LOTRBlocks.MALLORN_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_MALLORN_WOOD_SLAB.get(), LOTRBlocks.MALLORN_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_MALLORN_WOOD_STAIRS.get(), LOTRBlocks.MALLORN_BRANCH.get(), LOTRBlocks.STRIPPED_MALLORN_BRANCH.get(),
				LOTRBlocks.MALLORN_PLANKS.get(), LOTRBlocks.MALLORN_SLAB.get(), LOTRBlocks.MALLORN_STAIRS.get(), LOTRBlocks.MALLORN_BEAM.get(),
				LOTRBlocks.MALLORN_BEAM_SLAB.get(), LOTRBlocks.MALLORN_FENCE.get(), LOTRBlocks.MALLORN_FENCE_GATE.get(), LOTRBlocks.MALLORN_DOOR.get(),
				LOTRBlocks.MALLORN_TRAPDOOR.get(), LOTRBlocks.MALLORN_PRESSURE_PLATE.get(), LOTRBlocks.MALLORN_BUTTON.get(),
				LOTRBlocks.MALLORN_SIGN.get(), LOTRBlocks.MALLORN_WALL_SIGN.get(), LOTRBlocks.MALLORN_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:maple", () -> new WoodPallet("lotr:maple", LOTRBlocks.MAPLE_LOG.get(), LOTRBlocks.STRIPPED_MAPLE_LOG.get(), LOTRBlocks.MAPLE_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_MAPLE_LOG_SLAB.get(), LOTRBlocks.MAPLE_WOOD.get(), LOTRBlocks.STRIPPED_MAPLE_WOOD.get(),
				LOTRBlocks.MAPLE_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_MAPLE_WOOD_SLAB.get(), LOTRBlocks.MAPLE_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_MAPLE_WOOD_STAIRS.get(), LOTRBlocks.MAPLE_BRANCH.get(), LOTRBlocks.STRIPPED_MAPLE_BRANCH.get(),
				LOTRBlocks.MAPLE_PLANKS.get(), LOTRBlocks.MAPLE_SLAB.get(), LOTRBlocks.MAPLE_STAIRS.get(), LOTRBlocks.MAPLE_BEAM.get(),
				LOTRBlocks.MAPLE_BEAM_SLAB.get(), LOTRBlocks.MAPLE_FENCE.get(), LOTRBlocks.MAPLE_FENCE_GATE.get(), LOTRBlocks.MAPLE_DOOR.get(),
				LOTRBlocks.MAPLE_TRAPDOOR.get(), LOTRBlocks.MAPLE_PRESSURE_PLATE.get(), LOTRBlocks.MAPLE_BUTTON.get(),
				LOTRBlocks.MAPLE_SIGN.get(), LOTRBlocks.MAPLE_WALL_SIGN.get(), LOTRBlocks.MAPLE_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:mirk_oak", () -> new WoodPallet("lotr:mirk_oak", LOTRBlocks.MIRK_OAK_LOG.get(), LOTRBlocks.STRIPPED_MIRK_OAK_LOG.get(), LOTRBlocks.MIRK_OAK_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_MIRK_OAK_LOG_SLAB.get(), LOTRBlocks.MIRK_OAK_WOOD.get(), LOTRBlocks.STRIPPED_MIRK_OAK_WOOD.get(),
				LOTRBlocks.MIRK_OAK_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_MIRK_OAK_WOOD_SLAB.get(), LOTRBlocks.MIRK_OAK_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_MIRK_OAK_WOOD_STAIRS.get(), LOTRBlocks.MIRK_OAK_BRANCH.get(), LOTRBlocks.STRIPPED_MIRK_OAK_BRANCH.get(),
				LOTRBlocks.MIRK_OAK_PLANKS.get(), LOTRBlocks.MIRK_OAK_SLAB.get(), LOTRBlocks.MIRK_OAK_STAIRS.get(), LOTRBlocks.MIRK_OAK_BEAM.get(),
				LOTRBlocks.MIRK_OAK_BEAM_SLAB.get(), LOTRBlocks.MIRK_OAK_FENCE.get(), LOTRBlocks.MIRK_OAK_FENCE_GATE.get(), LOTRBlocks.MIRK_OAK_DOOR.get(),
				LOTRBlocks.MIRK_OAK_TRAPDOOR.get(), LOTRBlocks.MIRK_OAK_PRESSURE_PLATE.get(), LOTRBlocks.MIRK_OAK_BUTTON.get(),
				LOTRBlocks.MIRK_OAK_SIGN.get(), LOTRBlocks.MIRK_OAK_WALL_SIGN.get(), LOTRBlocks.MIRK_OAK_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:pear", () -> new WoodPallet("lotr:pear", LOTRBlocks.PEAR_LOG.get(), LOTRBlocks.STRIPPED_PEAR_LOG.get(), LOTRBlocks.PEAR_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_PEAR_LOG_SLAB.get(), LOTRBlocks.PEAR_WOOD.get(), LOTRBlocks.STRIPPED_PEAR_WOOD.get(),
				LOTRBlocks.PEAR_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_PEAR_WOOD_SLAB.get(), LOTRBlocks.PEAR_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_PEAR_WOOD_STAIRS.get(), LOTRBlocks.PEAR_BRANCH.get(), LOTRBlocks.STRIPPED_PEAR_BRANCH.get(),
				LOTRBlocks.PEAR_PLANKS.get(), LOTRBlocks.PEAR_SLAB.get(), LOTRBlocks.PEAR_STAIRS.get(), LOTRBlocks.PEAR_BEAM.get(),
				LOTRBlocks.PEAR_BEAM_SLAB.get(), LOTRBlocks.PEAR_FENCE.get(), LOTRBlocks.PEAR_FENCE_GATE.get(), LOTRBlocks.PEAR_DOOR.get(),
				LOTRBlocks.PEAR_TRAPDOOR.get(), LOTRBlocks.PEAR_PRESSURE_PLATE.get(), LOTRBlocks.PEAR_BUTTON.get(),
				LOTRBlocks.PEAR_SIGN.get(), LOTRBlocks.PEAR_WALL_SIGN.get(), LOTRBlocks.PEAR_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:pine", () -> new WoodPallet("lotr:pine", LOTRBlocks.PINE_LOG.get(), LOTRBlocks.STRIPPED_PINE_LOG.get(), LOTRBlocks.PINE_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_PINE_LOG_SLAB.get(), LOTRBlocks.PINE_WOOD.get(), LOTRBlocks.STRIPPED_PINE_WOOD.get(),
				LOTRBlocks.PINE_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_PINE_WOOD_SLAB.get(), LOTRBlocks.PINE_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_PINE_WOOD_STAIRS.get(), LOTRBlocks.PINE_BRANCH.get(), LOTRBlocks.STRIPPED_PINE_BRANCH.get(),
				LOTRBlocks.PINE_PLANKS.get(), LOTRBlocks.PINE_SLAB.get(), LOTRBlocks.PINE_STAIRS.get(), LOTRBlocks.PINE_BEAM.get(),
				LOTRBlocks.PINE_BEAM_SLAB.get(), LOTRBlocks.PINE_FENCE.get(), LOTRBlocks.PINE_FENCE_GATE.get(), LOTRBlocks.PINE_DOOR.get(),
				LOTRBlocks.PINE_TRAPDOOR.get(), LOTRBlocks.PINE_PRESSURE_PLATE.get(), LOTRBlocks.PINE_BUTTON.get(),
				LOTRBlocks.PINE_SIGN.get(), LOTRBlocks.PINE_WALL_SIGN.get(), LOTRBlocks.PINE_LEAVES.get(), Blocks.AIR));
		pallets.put("lotr:rotten", () -> new WoodPallet("lotr:rotten", LOTRBlocks.ROTTEN_LOG.get(), LOTRBlocks.STRIPPED_ROTTEN_LOG.get(), LOTRBlocks.ROTTEN_LOG_SLAB.get(),
				LOTRBlocks.STRIPPED_ROTTEN_LOG_SLAB.get(), LOTRBlocks.ROTTEN_WOOD.get(), LOTRBlocks.STRIPPED_ROTTEN_WOOD.get(),
				LOTRBlocks.ROTTEN_WOOD_SLAB.get(), LOTRBlocks.STRIPPED_ROTTEN_WOOD_SLAB.get(), LOTRBlocks.ROTTEN_WOOD_STAIRS.get(),
				LOTRBlocks.STRIPPED_ROTTEN_WOOD_STAIRS.get(), LOTRBlocks.ROTTEN_BRANCH.get(), LOTRBlocks.STRIPPED_ROTTEN_BRANCH.get(),
				LOTRBlocks.ROTTEN_PLANKS.get(), LOTRBlocks.ROTTEN_SLAB.get(), LOTRBlocks.ROTTEN_STAIRS.get(), LOTRBlocks.ROTTEN_BEAM.get(),
				LOTRBlocks.ROTTEN_BEAM_SLAB.get(), LOTRBlocks.ROTTEN_FENCE.get(), LOTRBlocks.ROTTEN_FENCE_GATE.get(), LOTRBlocks.ROTTEN_DOOR.get(),
				LOTRBlocks.ROTTEN_TRAPDOOR.get(), LOTRBlocks.ROTTEN_PRESSURE_PLATE.get(), LOTRBlocks.ROTTEN_BUTTON.get(),
				LOTRBlocks.ROTTEN_SIGN.get(), LOTRBlocks.ROTTEN_WALL_SIGN.get(), Blocks.AIR, Blocks.AIR));
	}

	public static WoodPallet getPalletForname(String name) {
		if(!created) {
			create();
		}
		if(pallets.containsKey(name)) {
			return pallets.get(name).get();
		}
		else {
			return WoodPallet.EMPTY;
		}
	}
}
