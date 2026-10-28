package com.github.maximuslotro.lotrrextended.common.init;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.block.*;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedBookshelfBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedLargePotBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedOrcBarrelBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedReedBasketBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedStoneChestBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.block.trees.BananaTree;
import com.github.maximuslotro.lotrrextended.common.block.trees.ChestnutTree;
import com.github.maximuslotro.lotrrextended.common.block.trees.EbonyTree;
import com.github.maximuslotro.lotrrextended.common.block.trees.PlumTree;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBedType;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCheeseState;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedWargFurType;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.ChandelierBlock;
import lotr.common.block.DripstoneBlock;
import lotr.common.block.ExtendedAxialSlabDirtBlock;
import lotr.common.block.ExtendedAxialSlabPathBlock;
import lotr.common.block.ExtendedAxialSlabPlantableBlock;
import lotr.common.block.ExtendedAxialSlabPlantableSpreadableBlock;
import lotr.common.block.ExtendedAxialSlabPlantableSpreadableGrowableBlock;
import lotr.common.block.ExtendedAxialSlabStemableBlock;
import lotr.common.block.ExtendedBottleVesselDrinkBlock;
import lotr.common.block.ExtendedBrazierBlock;
import lotr.common.block.ExtendedBurnableAxialSlabBlock;
import lotr.common.block.ExtendedBurnableLOTRStairsBlock;
import lotr.common.block.ExtendedChandelierBlock;
import lotr.common.block.ExtendedDwarvenGateBlock;
import lotr.common.block.ExtendedFactionCraftingTableBlock;
import lotr.common.block.ExtendedGreenHearthBlock;
import lotr.common.block.ExtendedLOTRTorchBlock;
import lotr.common.block.ExtendedLOTRWallTorchBlock;
import lotr.common.block.FallenLeavesBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRFenceBlock;
import lotr.common.block.LOTRFenceGateBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRPlanksBlock;
import lotr.common.block.LOTRPottedPlantBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStrippedLogBlock;
import lotr.common.block.LOTRStrippedWoodBlock;
import lotr.common.block.LOTRTrapdoorBlock;
import lotr.common.block.LOTRWoodButtonBlock;
import lotr.common.block.LOTRWoodPressurePlateBlock;
import lotr.common.block.LogSlabBlock;
import lotr.common.block.LogStairsBlock;
import lotr.common.block.StrippableBranchBlock;
import lotr.common.block.StrippableLogBlock;
import lotr.common.block.StrippableLogSlabBlock;
import lotr.common.block.StrippableLogStairsBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.block.WoodBeamBlock;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LotrExtendedMod.MODID);
	// Only used for dataGen
	public static Map<String, RegistryObject<Block>> REGISTEREDPOTS = new HashMap<>();

	//ORE STORAGE
	public static RegistryObject<Block> GALVORN_BLOCK = register("galvorn_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(4.0F, 8.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)));
	public static RegistryObject<Block> BLACK_URUK_STEEL_BLOCK = register("black_uruk_steel_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)));
	public static RegistryObject<Block> GILDED_IRON_BLOCK = register("gilded_iron_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(4.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)));
	public static RegistryObject<Block> MORGUL_STEEL_BLOCK = register("morgul_steel_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)));

	//Wood Types
	public static RegistryObject<Block> CHESTNUT_LOG = register("chestnut_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL, ExtendedBlocks.STRIPPED_CHESTNUT_LOG));
	public static RegistryObject<Block> CHESTNUT_WOOD = register("chestnut_wood", () -> new StrippableWoodBlock(CHESTNUT_LOG, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD));
	public static RegistryObject<Block> CHESTNUT_PLANKS = register("chestnut_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
	public static RegistryObject<Block> CHESTNUT_LEAVES = register("chestnut_leaves", ExtendedLeavesBlock::new);
	public static RegistryObject<Block> CHESTNUT_LEAVES_FRUIT = register("chestnut_leaves_fruit", ExtendedLeavesBlock::new);
	public static RegistryObject<Block> CHESTNUT_SAPLING = register("chestnut_sapling", () -> new ExtendedSaplingBlock(new ChestnutTree(), Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0f).sound(SoundType.GRASS)));
	public static RegistryObject<Block> POTTED_CHESTNUT_SAPLING = register("potted_chestnut_sapling", () -> new LOTRPottedPlantBlock(CHESTNUT_SAPLING));
	public static RegistryObject<Block> LARGE_POTTED_CHESTNUT_SAPLING = registerPOT("large_pot_chestnut_sapling", () -> new ExtendedLargeFlowerPotBlock(CHESTNUT_SAPLING));
	public static RegistryObject<Block> CHESTNUT_SLAB = register("chestnut_slab", () -> new AxialSlabBlock(CHESTNUT_PLANKS));
	public static RegistryObject<Block> CHESTNUT_STAIRS = register("chestnut_stairs", () -> new LOTRStairsBlock(CHESTNUT_PLANKS));
	public static RegistryObject<Block> CHESTNUT_FENCE = register("chestnut_fence", () -> new LOTRFenceBlock(CHESTNUT_PLANKS));
	public static RegistryObject<Block> CHESTNUT_FENCE_GATE = register("chestnut_fence_gate", () -> new LOTRFenceGateBlock(CHESTNUT_PLANKS));
	public static RegistryObject<Block> CHESTNUT_DOOR = register("chestnut_door", () -> new LOTRDoorBlock(CHESTNUT_PLANKS));
	public static RegistryObject<Block> CHESTNUT_TRAPDOOR = register("chestnut_trapdoor", () -> new LOTRTrapdoorBlock(CHESTNUT_PLANKS));
	public static RegistryObject<Block> CHESTNUT_PRESSURE_PLATE = register("chestnut_pressure_plate", () -> new LOTRWoodPressurePlateBlock(CHESTNUT_PLANKS));
	public static RegistryObject<Block> CHESTNUT_BUTTON = register("chestnut_button", LOTRWoodButtonBlock::new);
	public static RegistryObject<Block> STRIPPED_CHESTNUT_LOG = register("stripped_chestnut_log", () -> new LOTRStrippedLogBlock(CHESTNUT_LOG));
	public static RegistryObject<Block> STRIPPED_CHESTNUT_WOOD = register("stripped_chestnut_wood", () -> new LOTRStrippedWoodBlock(STRIPPED_CHESTNUT_LOG));
	public static RegistryObject<Block> CHESTNUT_BEAM = register("chestnut_beam", () -> new WoodBeamBlock(CHESTNUT_LOG));
	public static RegistryObject<Block> CHESTNUT_BEAM_SLAB = register("chestnut_beam_slab", () -> new LogSlabBlock(CHESTNUT_BEAM));
	public static RegistryObject<Block> CHESTNUT_SIGN = register("chestnut_sign", () -> new ExtendedStandingSignBlock(CHESTNUT_PLANKS, ExtendedSignTypes.CHESTNUT));
	public static RegistryObject<Block> CHESTNUT_WALL_SIGN = register("chestnut_wall_sign", () -> new ExtendedWallSignBlock(CHESTNUT_SIGN, ExtendedSignTypes.CHESTNUT));
	public static RegistryObject<Block> CHESTNUT_LOG_SLAB = register("chestnut_log_slab", () -> new StrippableLogSlabBlock(CHESTNUT_LOG, ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB));
	public static RegistryObject<Block> CHESTNUT_WOOD_SLAB = register("chestnut_wood_slab", () -> new StrippableLogSlabBlock(CHESTNUT_WOOD, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB));
	public static RegistryObject<Block> CHESTNUT_WOOD_STAIRS = register("chestnut_wood_stairs", () -> new StrippableLogStairsBlock(CHESTNUT_WOOD, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS));
	public static RegistryObject<Block> CHESTNUT_BRANCH = register("chestnut_branch", () -> new StrippableBranchBlock(CHESTNUT_WOOD, ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH));
	public static RegistryObject<Block> STRIPPED_CHESTNUT_LOG_SLAB = register("stripped_chestnut_log_slab", () -> new LogSlabBlock(STRIPPED_CHESTNUT_LOG));
	public static RegistryObject<Block> STRIPPED_CHESTNUT_WOOD_SLAB = register("stripped_chestnut_wood_slab", () -> new LogSlabBlock(STRIPPED_CHESTNUT_WOOD));
	public static RegistryObject<Block> STRIPPED_CHESTNUT_WOOD_STAIRS = register("stripped_chestnut_wood_stairs", () -> new LogStairsBlock(STRIPPED_CHESTNUT_WOOD));
	public static RegistryObject<Block> STRIPPED_CHESTNUT_BRANCH = register("stripped_chestnut_branch", () -> new BranchBlock(STRIPPED_CHESTNUT_WOOD));
	public static RegistryObject<Block> FALLEN_CHESTNUT_LEAVES = register("fallen_chestnut_leaves", () -> new FallenLeavesBlock(CHESTNUT_LEAVES));

	public static RegistryObject<Block> BANANA_LOG = register("banana_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.COLOR_YELLOW, ExtendedBlocks.STRIPPED_BANANA_LOG));
	public static RegistryObject<Block> BANANA_WOOD = register("banana_wood", () -> new StrippableWoodBlock(BANANA_LOG, ExtendedBlocks.STRIPPED_BANANA_WOOD));
	public static RegistryObject<Block> BANANA_PLANKS = register("banana_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
	public static RegistryObject<Block> BANANA_LEAVES = register("banana_leaves", ExtendedLeavesBlock::new);
	public static RegistryObject<Block> BANANA_SAPLING = register("banana_sapling", () -> new ExtendedSaplingBlock(new BananaTree(), Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0f).sound(SoundType.GRASS)));
	public static RegistryObject<Block> POTTED_BANANA_SAPLING = register("potted_banana_sapling", () -> new LOTRPottedPlantBlock(BANANA_SAPLING));
	public static RegistryObject<Block> LARGE_POTTED_BANANA_SAPLING = registerPOT("large_pot_banana_sapling", () -> new ExtendedLargeFlowerPotBlock(BANANA_SAPLING));
	public static RegistryObject<Block> BANANA_SLAB = register("banana_slab", () -> new AxialSlabBlock(BANANA_PLANKS));
	public static RegistryObject<Block> BANANA_STAIRS = register("banana_stairs", () -> new LOTRStairsBlock(BANANA_PLANKS));
	public static RegistryObject<Block> BANANA_FENCE = register("banana_fence", () -> new LOTRFenceBlock(BANANA_PLANKS));
	public static RegistryObject<Block> BANANA_FENCE_GATE = register("banana_fence_gate", () -> new LOTRFenceGateBlock(BANANA_PLANKS));
	public static RegistryObject<Block> BANANA_DOOR = register("banana_door", () -> new LOTRDoorBlock(BANANA_PLANKS));
	public static RegistryObject<Block> BANANA_TRAPDOOR = register("banana_trapdoor", () -> new LOTRTrapdoorBlock(BANANA_PLANKS));
	public static RegistryObject<Block> BANANA_PRESSURE_PLATE = register("banana_pressure_plate", () -> new LOTRWoodPressurePlateBlock(BANANA_PLANKS));
	public static RegistryObject<Block> BANANA_BUTTON = register("banana_button", LOTRWoodButtonBlock::new);
	public static RegistryObject<Block> STRIPPED_BANANA_LOG = register("stripped_banana_log", () -> new LOTRStrippedLogBlock(BANANA_LOG));
	public static RegistryObject<Block> STRIPPED_BANANA_WOOD = register("stripped_banana_wood", () -> new LOTRStrippedWoodBlock(STRIPPED_BANANA_LOG));
	public static RegistryObject<Block> BANANA_BEAM = register("banana_beam", () -> new WoodBeamBlock(BANANA_LOG));
	public static RegistryObject<Block> BANANA_BEAM_SLAB = register("banana_beam_slab", () -> new LogSlabBlock(BANANA_BEAM));
	public static RegistryObject<Block> BANANA_SIGN = register("banana_sign", () -> new ExtendedStandingSignBlock(BANANA_PLANKS, ExtendedSignTypes.BANANA));
	public static RegistryObject<Block> BANANA_WALL_SIGN = register("banana_wall_sign", () -> new ExtendedWallSignBlock(BANANA_SIGN, ExtendedSignTypes.BANANA));
	public static RegistryObject<Block> BANANA_LOG_SLAB = register("banana_log_slab", () -> new StrippableLogSlabBlock(BANANA_LOG, ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB));
	public static RegistryObject<Block> BANANA_WOOD_SLAB = register("banana_wood_slab", () -> new StrippableLogSlabBlock(BANANA_WOOD, ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB));
	public static RegistryObject<Block> BANANA_WOOD_STAIRS = register("banana_wood_stairs", () -> new StrippableLogStairsBlock(BANANA_WOOD, ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS));
	public static RegistryObject<Block> BANANA_BRANCH = register("banana_branch", () -> new StrippableBranchBlock(BANANA_WOOD, ExtendedBlocks.STRIPPED_BANANA_BRANCH));
	public static RegistryObject<Block> STRIPPED_BANANA_LOG_SLAB = register("stripped_banana_log_slab", () -> new LogSlabBlock(STRIPPED_BANANA_LOG));
	public static RegistryObject<Block> STRIPPED_BANANA_WOOD_SLAB = register("stripped_banana_wood_slab", () -> new LogSlabBlock(STRIPPED_BANANA_WOOD));
	public static RegistryObject<Block> STRIPPED_BANANA_WOOD_STAIRS = register("stripped_banana_wood_stairs", () -> new LogStairsBlock(STRIPPED_BANANA_WOOD));
	public static RegistryObject<Block> STRIPPED_BANANA_BRANCH = register("stripped_banana_branch", () -> new BranchBlock(STRIPPED_BANANA_WOOD));
	public static RegistryObject<Block> FALLEN_BANANA_LEAVES = register("fallen_banana_leaves", () -> new FallenLeavesBlock(BANANA_LEAVES));
	public static RegistryObject<Block> HANGING_BANANA = register("hanging_banana", ExtendedHangingBlock::new);

	public static RegistryObject<Block> PLUM_LOG = register("plum_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.COLOR_PURPLE, ExtendedBlocks.STRIPPED_PLUM_LOG));
	public static RegistryObject<Block> PLUM_WOOD = register("plum_wood", () -> new StrippableWoodBlock(PLUM_LOG, ExtendedBlocks.STRIPPED_PLUM_WOOD));
	public static RegistryObject<Block> PLUM_PLANKS = register("plum_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
	public static RegistryObject<Block> PLUM_LEAVES = register("plum_leaves", () -> new ExtendedLeavesBlock(MaterialColor.COLOR_PURPLE));
	public static RegistryObject<Block> PLUM_LEAVES_FRUIT = register("plum_leaves_fruit", () -> new ExtendedLeavesBlock(MaterialColor.COLOR_PURPLE));
	public static RegistryObject<Block> PLUM_SAPLING = register("plum_sapling", () -> new ExtendedSaplingBlock(new PlumTree(), Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0f).sound(SoundType.GRASS)));
	public static RegistryObject<Block> POTTED_PLUM_SAPLING = register("potted_plum_sapling", () -> new LOTRPottedPlantBlock(PLUM_SAPLING));
	public static RegistryObject<Block> LARGE_POTTED_PLUM_SAPLING = registerPOT("large_pot_plum_sapling", () -> new ExtendedLargeFlowerPotBlock(PLUM_SAPLING));
	public static RegistryObject<Block> PLUM_SLAB = register("plum_slab", () -> new AxialSlabBlock(PLUM_PLANKS));
	public static RegistryObject<Block> PLUM_STAIRS = register("plum_stairs", () -> new LOTRStairsBlock(PLUM_PLANKS));
	public static RegistryObject<Block> PLUM_FENCE = register("plum_fence", () -> new LOTRFenceBlock(PLUM_PLANKS));
	public static RegistryObject<Block> PLUM_FENCE_GATE = register("plum_fence_gate", () -> new LOTRFenceGateBlock(PLUM_PLANKS));
	public static RegistryObject<Block> PLUM_DOOR = register("plum_door", () -> new LOTRDoorBlock(PLUM_PLANKS));
	public static RegistryObject<Block> PLUM_TRAPDOOR = register("plum_trapdoor", () -> new LOTRTrapdoorBlock(PLUM_PLANKS));
	public static RegistryObject<Block> PLUM_PRESSURE_PLATE = register("plum_pressure_plate", () -> new LOTRWoodPressurePlateBlock(PLUM_PLANKS));
	public static RegistryObject<Block> PLUM_BUTTON = register("plum_button", LOTRWoodButtonBlock::new);
	public static RegistryObject<Block> STRIPPED_PLUM_LOG = register("stripped_plum_log", () -> new LOTRStrippedLogBlock(PLUM_LOG));
	public static RegistryObject<Block> STRIPPED_PLUM_WOOD = register("stripped_plum_wood", () -> new LOTRStrippedWoodBlock(STRIPPED_PLUM_LOG));
	public static RegistryObject<Block> PLUM_BEAM = register("plum_beam", () -> new WoodBeamBlock(PLUM_LOG));
	public static RegistryObject<Block> PLUM_BEAM_SLAB = register("plum_beam_slab", () -> new LogSlabBlock(PLUM_BEAM));
	public static RegistryObject<Block> PLUM_SIGN = register("plum_sign", () -> new ExtendedStandingSignBlock(PLUM_PLANKS, ExtendedSignTypes.PLUM));
	public static RegistryObject<Block> PLUM_WALL_SIGN = register("plum_wall_sign", () -> new ExtendedWallSignBlock(PLUM_SIGN, ExtendedSignTypes.PLUM));
	public static RegistryObject<Block> PLUM_LOG_SLAB = register("plum_log_slab", () -> new StrippableLogSlabBlock(PLUM_LOG, ExtendedBlocks.STRIPPED_PLUM_LOG_SLAB));
	public static RegistryObject<Block> PLUM_WOOD_SLAB = register("plum_wood_slab", () -> new StrippableLogSlabBlock(PLUM_WOOD, ExtendedBlocks.STRIPPED_PLUM_WOOD_SLAB));
	public static RegistryObject<Block> PLUM_WOOD_STAIRS = register("plum_wood_stairs", () -> new StrippableLogStairsBlock(PLUM_WOOD, ExtendedBlocks.STRIPPED_PLUM_WOOD_STAIRS));
	public static RegistryObject<Block> PLUM_BRANCH = register("plum_branch", () -> new StrippableBranchBlock(PLUM_WOOD, ExtendedBlocks.STRIPPED_PLUM_BRANCH));
	public static RegistryObject<Block> STRIPPED_PLUM_LOG_SLAB = register("stripped_plum_log_slab", () -> new LogSlabBlock(STRIPPED_PLUM_LOG));
	public static RegistryObject<Block> STRIPPED_PLUM_WOOD_SLAB = register("stripped_plum_wood_slab", () -> new LogSlabBlock(STRIPPED_PLUM_WOOD));
	public static RegistryObject<Block> STRIPPED_PLUM_WOOD_STAIRS = register("stripped_plum_wood_stairs", () -> new LogStairsBlock(STRIPPED_PLUM_WOOD));
	public static RegistryObject<Block> STRIPPED_PLUM_BRANCH = register("stripped_plum_branch", () -> new BranchBlock(STRIPPED_PLUM_WOOD));
	public static RegistryObject<Block> FALLEN_PLUM_LEAVES = register("fallen_plum_leaves", () -> new FallenLeavesBlock(PLUM_LEAVES));

	public static RegistryObject<Block> WILLOW_LOG = register("willow_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.SAND, ExtendedBlocks.STRIPPED_WILLOW_LOG));
	public static RegistryObject<Block> WILLOW_WOOD = register("willow_wood", () -> new StrippableWoodBlock(WILLOW_LOG, ExtendedBlocks.STRIPPED_WILLOW_WOOD));
	public static RegistryObject<Block> WILLOW_PLANKS = register("willow_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
	public static RegistryObject<Block> WILLOW_LEAVES = register("willow_leaves", ExtendedLeavesBlock::new);
	public static RegistryObject<Block> WILLOW_SAPLING = register("willow_sapling", () -> new ExtendedWillowTreeSapling(new OakTree(), Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0f).sound(SoundType.GRASS)));
	public static RegistryObject<Block> POTTED_WILLOW_SAPLING = register("potted_willow_sapling", () -> new LOTRPottedPlantBlock(WILLOW_SAPLING));
	public static RegistryObject<Block> LARGE_POTTED_WILLOW_SAPLING = registerPOT("large_pot_willow_sapling", () -> new ExtendedLargeFlowerPotBlock(WILLOW_SAPLING));
	public static RegistryObject<Block> WILLOW_SLAB = register("willow_slab", () -> new AxialSlabBlock(WILLOW_PLANKS));
	public static RegistryObject<Block> WILLOW_STAIRS = register("willow_stairs", () -> new LOTRStairsBlock(WILLOW_PLANKS));
	public static RegistryObject<Block> WILLOW_FENCE = register("willow_fence", () -> new LOTRFenceBlock(WILLOW_PLANKS));
	public static RegistryObject<Block> WILLOW_FENCE_GATE = register("willow_fence_gate", () -> new LOTRFenceGateBlock(WILLOW_PLANKS));
	public static RegistryObject<Block> WILLOW_DOOR = register("willow_door", () -> new LOTRDoorBlock(WILLOW_PLANKS));
	public static RegistryObject<Block> WILLOW_TRAPDOOR = register("willow_trapdoor", () -> new LOTRTrapdoorBlock(WILLOW_PLANKS));
	public static RegistryObject<Block> WILLOW_PRESSURE_PLATE = register("willow_pressure_plate", () -> new LOTRWoodPressurePlateBlock(WILLOW_PLANKS));
	public static RegistryObject<Block> WILLOW_BUTTON = register("willow_button", LOTRWoodButtonBlock::new);
	public static RegistryObject<Block> STRIPPED_WILLOW_LOG = register("stripped_willow_log", () -> new LOTRStrippedLogBlock(WILLOW_LOG));
	public static RegistryObject<Block> STRIPPED_WILLOW_WOOD = register("stripped_willow_wood", () -> new LOTRStrippedWoodBlock(STRIPPED_WILLOW_LOG));
	public static RegistryObject<Block> WILLOW_BEAM = register("willow_beam", () -> new WoodBeamBlock(WILLOW_LOG));
	public static RegistryObject<Block> WILLOW_SIGN = register("willow_sign", () -> new ExtendedStandingSignBlock(WILLOW_PLANKS, ExtendedSignTypes.WILLOW));
	public static RegistryObject<Block> WILLOW_WALL_SIGN = register("willow_wall_sign", () -> new ExtendedWallSignBlock(WILLOW_SIGN, ExtendedSignTypes.WILLOW));
	public static RegistryObject<Block> WILLOW_LOG_SLAB = register("willow_log_slab", () -> new StrippableLogSlabBlock(WILLOW_LOG, ExtendedBlocks.STRIPPED_WILLOW_LOG_SLAB));
	public static RegistryObject<Block> WILLOW_WOOD_SLAB = register("willow_wood_slab", () -> new StrippableLogSlabBlock(WILLOW_WOOD, ExtendedBlocks.STRIPPED_WILLOW_WOOD_SLAB));
	public static RegistryObject<Block> WILLOW_WOOD_STAIRS = register("willow_wood_stairs", () -> new StrippableLogStairsBlock(WILLOW_WOOD, ExtendedBlocks.STRIPPED_WILLOW_WOOD_STAIRS));
	public static RegistryObject<Block> WILLOW_BRANCH = register("willow_branch", () -> new StrippableBranchBlock(WILLOW_WOOD, ExtendedBlocks.STRIPPED_WILLOW_BRANCH));
	public static RegistryObject<Block> STRIPPED_WILLOW_LOG_SLAB = register("stripped_willow_log_slab", () -> new LogSlabBlock(STRIPPED_WILLOW_LOG));
	public static RegistryObject<Block> STRIPPED_WILLOW_WOOD_SLAB = register("stripped_willow_wood_slab", () -> new LogSlabBlock(STRIPPED_WILLOW_WOOD));
	public static RegistryObject<Block> STRIPPED_WILLOW_WOOD_STAIRS = register("stripped_willow_wood_stairs", () -> new LogStairsBlock(STRIPPED_WILLOW_WOOD));
	public static RegistryObject<Block> STRIPPED_WILLOW_BRANCH = register("stripped_willow_branch", () -> new BranchBlock(STRIPPED_WILLOW_WOOD));
	public static RegistryObject<Block> WILLOW_BEAM_SLAB = register("willow_beam_slab", () -> new LogSlabBlock(WILLOW_BEAM));
	public static RegistryObject<Block> FALLEN_WILLOW_LEAVES = register("fallen_willow_leaves", () -> new FallenLeavesBlock(WILLOW_LEAVES));
	public static RegistryObject<Block> WILLOW_VINES = register("willow_vines", () -> new ExtendedVineBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE)));

	public static RegistryObject<Block> EBONY_LOG = register("ebony_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.COLOR_BLACK, ExtendedBlocks.STRIPPED_EBONY_LOG));
	public static RegistryObject<Block> EBONY_WOOD = register("ebony_wood", () -> new StrippableWoodBlock(EBONY_LOG, ExtendedBlocks.STRIPPED_EBONY_WOOD));
	public static RegistryObject<Block> EBONY_PLANKS = register("ebony_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
	public static RegistryObject<Block> EBONY_LEAVES = register("ebony_leaves", ExtendedLeavesBlock::new);
	public static RegistryObject<Block> EBONY_SAPLING = register("ebony_sapling", () -> new ExtendedSaplingBlock(new EbonyTree(), Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0f).sound(SoundType.GRASS)));
	public static RegistryObject<Block> POTTED_EBONY_SAPLING = register("potted_ebony_sapling", () -> new LOTRPottedPlantBlock(EBONY_SAPLING));
	public static RegistryObject<Block> LARGE_POTTED_EBONY_SAPLING = registerPOT("large_pot_ebony_sapling", () -> new ExtendedLargeFlowerPotBlock(EBONY_SAPLING));
	public static RegistryObject<Block> EBONY_SLAB = register("ebony_slab", () -> new AxialSlabBlock(EBONY_PLANKS));
	public static RegistryObject<Block> EBONY_STAIRS = register("ebony_stairs", () -> new LOTRStairsBlock(EBONY_PLANKS));
	public static RegistryObject<Block> EBONY_FENCE = register("ebony_fence", () -> new LOTRFenceBlock(EBONY_PLANKS));
	public static RegistryObject<Block> EBONY_FENCE_GATE = register("ebony_fence_gate", () -> new LOTRFenceGateBlock(EBONY_PLANKS));
	public static RegistryObject<Block> EBONY_DOOR = register("ebony_door", () -> new LOTRDoorBlock(EBONY_PLANKS));
	public static RegistryObject<Block> EBONY_TRAPDOOR = register("ebony_trapdoor", () -> new LOTRTrapdoorBlock(EBONY_PLANKS));
	public static RegistryObject<Block> EBONY_PRESSURE_PLATE = register("ebony_pressure_plate", () -> new LOTRWoodPressurePlateBlock(EBONY_PLANKS));
	public static RegistryObject<Block> EBONY_BUTTON = register("ebony_button", LOTRWoodButtonBlock::new);
	public static RegistryObject<Block> STRIPPED_EBONY_LOG = register("stripped_ebony_log", () -> new LOTRStrippedLogBlock(EBONY_LOG));
	public static RegistryObject<Block> STRIPPED_EBONY_WOOD = register("stripped_ebony_wood", () -> new LOTRStrippedWoodBlock(STRIPPED_EBONY_LOG));
	public static RegistryObject<Block> EBONY_BEAM = register("ebony_beam", () -> new WoodBeamBlock(EBONY_LOG));
	public static RegistryObject<Block> EBONY_BEAM_SLAB = register("ebony_beam_slab", () -> new LogSlabBlock(EBONY_BEAM));
	public static RegistryObject<Block> EBONY_SIGN = register("ebony_sign", () -> new ExtendedStandingSignBlock(EBONY_PLANKS, ExtendedSignTypes.EBONY));
	public static RegistryObject<Block> EBONY_WALL_SIGN = register("ebony_wall_sign", () -> new ExtendedWallSignBlock(EBONY_SIGN, ExtendedSignTypes.EBONY));
	public static RegistryObject<Block> EBONY_LOG_SLAB = register("ebony_log_slab", () -> new StrippableLogSlabBlock(EBONY_LOG, ExtendedBlocks.STRIPPED_EBONY_LOG_SLAB));
	public static RegistryObject<Block> EBONY_WOOD_SLAB = register("ebony_wood_slab", () -> new StrippableLogSlabBlock(EBONY_WOOD, ExtendedBlocks.STRIPPED_EBONY_WOOD_SLAB));
	public static RegistryObject<Block> EBONY_WOOD_STAIRS = register("ebony_wood_stairs", () -> new StrippableLogStairsBlock(EBONY_WOOD, ExtendedBlocks.STRIPPED_EBONY_WOOD_STAIRS));
	public static RegistryObject<Block> EBONY_BRANCH = register("ebony_branch", () -> new StrippableBranchBlock(EBONY_WOOD, ExtendedBlocks.STRIPPED_EBONY_BRANCH));
	public static RegistryObject<Block> STRIPPED_EBONY_LOG_SLAB = register("stripped_ebony_log_slab", () -> new LogSlabBlock(STRIPPED_EBONY_LOG));
	public static RegistryObject<Block> STRIPPED_EBONY_WOOD_SLAB = register("stripped_ebony_wood_slab", () -> new LogSlabBlock(STRIPPED_EBONY_WOOD));
	public static RegistryObject<Block> STRIPPED_EBONY_WOOD_STAIRS = register("stripped_ebony_wood_stairs", () -> new LogStairsBlock(STRIPPED_EBONY_WOOD));
	public static RegistryObject<Block> STRIPPED_EBONY_BRANCH = register("stripped_ebony_branch", () -> new BranchBlock(STRIPPED_EBONY_WOOD));
	public static RegistryObject<Block> FALLEN_EBONY_LEAVES = register("fallen_ebony_leaves", () -> new FallenLeavesBlock(EBONY_LEAVES));

	//Packed Fur
	public static RegistryObject<Block> WHITE_MATTED_FUR = register("white_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.WHITE));
	public static RegistryObject<Block> WHITE_MATTED_FUR_SLAB = register("white_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(WHITE_MATTED_FUR));
	public static RegistryObject<Block> WHITE_MATTED_FUR_STAIRS = register("white_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(WHITE_MATTED_FUR));
	public static RegistryObject<Block> WHITE_MATTED_FUR_WALL = register("white_matted_fur_wall", () -> new ExtendedBurnableWallBlock(WHITE_MATTED_FUR));

	public static RegistryObject<Block> SILVER_MATTED_FUR = register("silver_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.SILVER));
	public static RegistryObject<Block> SILVER_MATTED_FUR_SLAB = register("silver_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(SILVER_MATTED_FUR));
	public static RegistryObject<Block> SILVER_MATTED_FUR_STAIRS = register("silver_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(SILVER_MATTED_FUR));
	public static RegistryObject<Block> SILVER_MATTED_FUR_WALL = register("silver_matted_fur_wall", () -> new ExtendedBurnableWallBlock(SILVER_MATTED_FUR));

	public static RegistryObject<Block> GRAY_MATTED_FUR = register("gray_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.GRAY));
	public static RegistryObject<Block> GRAY_MATTED_FUR_SLAB = register("gray_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(GRAY_MATTED_FUR));
	public static RegistryObject<Block> GRAY_MATTED_FUR_STAIRS = register("gray_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(GRAY_MATTED_FUR));
	public static RegistryObject<Block> GRAY_MATTED_FUR_WALL = register("gray_matted_fur_wall", () -> new ExtendedBurnableWallBlock(GRAY_MATTED_FUR));

	public static RegistryObject<Block> BROWN_MATTED_FUR = register("matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.BROWN));
	public static RegistryObject<Block> BROWN_MATTED_FUR_SLAB = register("matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(BROWN_MATTED_FUR));
	public static RegistryObject<Block> BROWN_MATTED_FUR_STAIRS = register("matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(BROWN_MATTED_FUR));
	public static RegistryObject<Block> BROWN_MATTED_FUR_WALL = register("matted_fur_wall", () -> new ExtendedBurnableWallBlock(BROWN_MATTED_FUR));

	public static RegistryObject<Block> BLACK_MATTED_FUR = register("black_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.BLACK));
	public static RegistryObject<Block> BLACK_MATTED_FUR_SLAB = register("black_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(BLACK_MATTED_FUR));
	public static RegistryObject<Block> BLACK_MATTED_FUR_STAIRS = register("black_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(BLACK_MATTED_FUR));
	public static RegistryObject<Block> BLACK_MATTED_FUR_WALL = register("black_matted_fur_wall", () -> new ExtendedBurnableWallBlock(BLACK_MATTED_FUR));

	//Bundled Fur
	public static RegistryObject<Block> WHITE_FUR_BUNDLE = register("white_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.WOOL));
	public static RegistryObject<Block> SILVER_FUR_BUNDLE = register("silver_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.COLOR_LIGHT_GRAY));
	public static RegistryObject<Block> GRAY_FUR_BUNDLE = register("gray_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.COLOR_GRAY));
	public static RegistryObject<Block> BLACK_FUR_BUNDLE = register("black_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.COLOR_BLACK));

	//Fur carpets
	public static RegistryObject<Block> BLACK_FUR_CARPET = register("black_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.BLACK));
	public static RegistryObject<Block> BROWN_FUR_CARPET = register("brown_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.BROWN));
	public static RegistryObject<Block> GRAY_FUR_CARPET = register("gray_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.GRAY));
	public static RegistryObject<Block> SILVER_FUR_CARPET = register("silver_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.SILVER));
	public static RegistryObject<Block> WHITE_FUR_CARPET = register("white_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.SILVER));

	//GEM STORAGE
	public static RegistryObject<Block> TOPAZ_BLOCK = register("topaz_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> AMETHYST_BLOCK = register("amethyst_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> SAPPHIRE_BLOCK = register("sapphire_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> RUBY_BLOCK = register("ruby_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_RED).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> AMBER_BLOCK = register("amber_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> DIAMOND_BLOCK = register("diamond_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_BLUE).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(2)));
	public static RegistryObject<Block> OPAL_BLOCK = register("opal_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.WOOL).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> EMERALD_BLOCK = register("emerald_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> CORAL_BLOCK = register("coral_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> DEAD_CORAL_BLOCK = register("dead_coral_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 6.0F).sound(SoundType.METAL).harvestLevel(1)));
	public static RegistryObject<Block> PEARL_BLOCK = register("pearl_block", () -> new ExtendedMaterialBlock(Properties.of(Material.METAL, MaterialColor.WOOL).strength(4.0F, 6.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).harvestLevel(1)));

	//GEM ORES
	public static RegistryObject<Block> METEORITE_ORE = register("meteorite", () -> new ExtendedMaterialBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).harvestLevel(3).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(50.0F, 1200.0F)));
	public static RegistryObject<Block> AMBER_ORE = register("amber_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 1, 3)));
	public static RegistryObject<Block> TOPAZ_ORE = register("topaz_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 1, 3)));
	public static RegistryObject<Block> AMETHYST_ORE = register("amethyst_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 1, 3)));
	public static RegistryObject<Block> SAPPHIRE_ORE = register("sapphire_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 1, 3)));
	public static RegistryObject<Block> RUBY_ORE = register("ruby_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 1, 4)));
	public static RegistryObject<Block> DIAMOND_ORE = register("diamond_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 2, 6)));
	public static RegistryObject<Block> OPAL_ORE = register("opal_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 1, 4)));
	public static RegistryObject<Block> EMERALD_ORE = register("emerald_ore", () -> new ExtendedOreBlock(Properties.of(Material.STONE).harvestLevel(2).requiresCorrectToolForDrops().strength(3.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), rand -> MathHelper.nextInt(rand, 2, 6)));
	//Remains ores
	public static RegistryObject<Block> REMAINS_ORC = register("remains_ore_orc", () -> new ExtendedOreRemainsBlock(Properties.copy(Blocks.DIRT).harvestLevel(0).harvestTool(ToolType.SHOVEL)));
	public static RegistryObject<Block> REMAINS_ELF = register("remains_ore_elf", () -> new ExtendedOreRemainsBlock(Properties.copy(Blocks.DIRT).harvestLevel(0).harvestTool(ToolType.SHOVEL)));
	public static RegistryObject<Block> REMAINS_MAN = register("remains_ore_man", () -> new ExtendedOreRemainsBlock(Properties.copy(Blocks.DIRT).harvestLevel(0).harvestTool(ToolType.SHOVEL)));
	public static RegistryObject<Block> REMAINS_DWARF = register("remains_ore_dwarf", () -> new ExtendedOreRemainsBlock(Properties.copy(Blocks.DIRT).harvestLevel(0).harvestTool(ToolType.SHOVEL)));

	///////////////////////////////BRICKS
	public static RegistryObject<Block> BARREN_DIRT = register("barren_dirt", () -> new ExtendedBarrenDirt(Properties.copy(Blocks.DIRT).harvestTool(ToolType.SHOVEL)));
	public static RegistryObject<Block> BARREN_DIRT_SLAB = register("barren_dirt_slab", () -> new ExtendedAxialSlabStemableBlock(BARREN_DIRT));
	public static RegistryObject<Block> COARSE_DIRT_SLAB = register("coarse_dirt_slab", () -> new ExtendedAxialSlabPlantableBlock(Blocks.COARSE_DIRT));
	public static RegistryObject<Block> DIRT_SLAB = register("dirt_slab", () -> new ExtendedAxialSlabDirtBlock(Blocks.DIRT, ExtendedBlocks.GRASS_PATH_SLAB));
	public static RegistryObject<Block> GRASS_BLOCK_SLAB = register("grass_block_slab", () -> new ExtendedAxialSlabPlantableSpreadableGrowableBlock(Blocks.GRASS_BLOCK, DIRT_SLAB, () -> Blocks.DIRT, () -> Blocks.GRASS_BLOCK));
	public static RegistryObject<Block> GRASS_PATH_SLAB = register("grass_path_slab", () -> new ExtendedAxialSlabPathBlock(Blocks.GRASS_PATH));
	public static RegistryObject<Block> PODZOL_SLAB = register("podzol_slab", () -> new ExtendedAxialSlabPlantableBlock(Blocks.PODZOL));
	public static RegistryObject<Block> MYCELIUM_SLAB = register("mycelium_slab", () -> new ExtendedAxialSlabPlantableSpreadableBlock(Blocks.MYCELIUM, DIRT_SLAB, () -> Blocks.DIRT, () -> Blocks.MYCELIUM));
	public static RegistryObject<Block> MORDOR_DIRT_SLAB = register("mordor_dirt_slab", () -> new ExtendedAxialSlabDirtBlock(LOTRBlocks.MORDOR_DIRT.get(), ExtendedBlocks.MORDOR_DIRT_PATH_SLAB));
	public static RegistryObject<Block> MORDOR_DIRT_PATH_SLAB = register("mordor_dirt_path_slab", () -> new ExtendedAxialSlabPathBlock(LOTRBlocks.MORDOR_DIRT_PATH.get()));
	public static RegistryObject<Block> SNOW_SLAB = register("snow_slab", () -> new ExtendedAxialSlabDirtBlock(Blocks.SNOW, ExtendedBlocks.SNOW_PATH_SLAB));
	public static RegistryObject<Block> SNOW_PATH_SLAB = register("snow_path_slab", () -> new ExtendedAxialSlabPathBlock(LOTRBlocks.SNOW_PATH.get()));
	public static RegistryObject<Block> CHALK_PATH_SLAB = register("chalk_path_slab", () -> new ExtendedAxialSlabPathBlock(LOTRBlocks.CHALK_PATH.get()) {
		@Override
		public net.minecraftforge.common.ToolType getHarvestTool(BlockState state) {
			return ToolType.PICKAXE;
		}
	});

	//Bone Block
	public static RegistryObject<Block> BONE_BLOCK_SLAB = register("bone_block_slab", () -> new AxialSlabBlock(Blocks.BONE_BLOCK));
	public static RegistryObject<Block> BONE_BLOCK_STAIRS = register("bone_block_stairs", () -> new LOTRStairsBlock(Blocks.BONE_BLOCK));
	public static RegistryObject<Block> BONE_BLOCK_WALL = register("bone_block_wall", () -> new ExtendedWallBlock(Blocks.BONE_BLOCK));
	//Scorched Stuff
	public static RegistryObject<Block> SCORCHED_STONE = register("scorched_stone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	public static RegistryObject<Block> SCORCHED_STONE_SLAB = register("scorched_stone_slab", () -> new AxialSlabBlock(SCORCHED_STONE));
	public static RegistryObject<Block> SCORCHED_STONE_STAIRS = register("scorched_stone_stairs", () -> new LOTRStairsBlock(SCORCHED_STONE));
	public static RegistryObject<Block> SCORCHED_STONE_WALL = register("scorched_stone_wall", () -> new ExtendedWallBlock(SCORCHED_STONE));
	public static RegistryObject<Block> SCORCHED_COBBLESTONE = register("scorched_cobblestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	public static RegistryObject<Block> SCORCHED_COBBLESTONE_SLAB = register("scorched_cobblestone_slab", () -> new AxialSlabBlock(SCORCHED_COBBLESTONE));
	public static RegistryObject<Block> SCORCHED_COBBLESTONE_STAIRS = register("scorched_cobblestone_stairs", () -> new LOTRStairsBlock(SCORCHED_COBBLESTONE));
	public static RegistryObject<Block> SCORCHED_COBBLESTONE_WALL = register("scorched_cobblestone_wall", () -> new ExtendedWallBlock(SCORCHED_COBBLESTONE));
	public static RegistryObject<Block> SCORCHED_BRICK = register("scorched_brick", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	public static RegistryObject<Block> SCORCHED_BRICK_SLAB = register("scorched_brick_slab", () -> new AxialSlabBlock(SCORCHED_BRICK));
	public static RegistryObject<Block> SCORCHED_BRICK_STAIRS = register("scorched_brick_stairs", () -> new LOTRStairsBlock(SCORCHED_BRICK));
	public static RegistryObject<Block> SCORCHED_BRICK_WALL = register("scorched_brick_wall", () -> new ExtendedWallBlock(SCORCHED_BRICK));
	//Arnor
	public static RegistryObject<Block> CRACKED_ARNOR_PILLAR = register("cracked_arnor_pillar", () -> new LOTRPillarBlock(MaterialColor.STONE));
	public static RegistryObject<Block> CRACKED_ARNOR_PILLAR_SLAB = register("cracked_arnor_pillar_slab", () -> new AxialSlabBlock(CRACKED_ARNOR_PILLAR));
	public static RegistryObject<Block> MOSSY_ARNOR_PILLAR = register("mossy_arnor_pillar", () -> new LOTRPillarBlock(MaterialColor.STONE));
	public static RegistryObject<Block> MOSSY_ARNOR_PILLAR_SLAB = register("mossy_arnor_pillar_slab", () -> new AxialSlabBlock(MOSSY_ARNOR_PILLAR));
	public static RegistryObject<Block> CRACKED_STONE_PILLAR = register("cracked_stone_pillar", () -> new LOTRPillarBlock(MaterialColor.STONE));
	public static RegistryObject<Block> CRACKED_STONE_PILLAR_SLAB = register("cracked_stone_pillar_slab", () -> new AxialSlabBlock(CRACKED_STONE_PILLAR));
	// New Blocks
	//		ExtendedBlocks.GNEISS_ROCK = registerB("gneiss", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.GNEISS_ROCK_SLAB = registerB("gneiss_slab", () -> new AxialSlabBlock(ExtendedBlocks.GNEISS_ROCK));
	//		ExtendedBlocks.GNEISS_ROCK_STAIRS = registerB("gneiss_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GNEISS_ROCK));
	//		ExtendedBlocks.GNEISS_ROCK_WALL = registerB("gneiss_wall", () -> new ExtendedWallBlock(ExtendedBlocks.GNEISS_ROCK));
	//		ExtendedBlocks.GNEISS_BRICK = registerB("gneiss_brick", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.GNEISS_BRICK_SLAB = registerB("gneiss_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.GNEISS_BRICK));
	//		ExtendedBlocks.GNEISS_BRICK_STAIRS = registerB("gneiss_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GNEISS_BRICK));
	//		ExtendedBlocks.GNEISS_BRICK_WALL = registerB("gneiss_brick_wall", () -> new ExtendedWallBlock(ExtendedBlocks.GNEISS_BRICK));
	//		ExtendedBlocks.POLISHED_GNEISS = registerB("polished_gneiss", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.POLISHED_GNEISS_SLAB = registerB("polished_gneiss_slab", () -> new AxialSlabBlock(ExtendedBlocks.POLISHED_GNEISS));
	//		ExtendedBlocks.POLISHED_GNEISS_STAIRS = registerB("polished_gneiss_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.POLISHED_GNEISS));
	//		ExtendedBlocks.POLISHED_GNEISS_WALL = registerB("polished_gneiss_wall", () -> new ExtendedWallBlock(ExtendedBlocks.POLISHED_GNEISS));
	//		ExtendedBlocks.LIMESTONE_ROCK = registerB("limestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.LIMESTONE_ROCK_SLAB = registerB("limestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.LIMESTONE_ROCK));
	//		ExtendedBlocks.LIMESTONE_ROCK_STAIRS = registerB("limestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.LIMESTONE_ROCK));
	//		ExtendedBlocks.LIMESTONE_ROCK_WALL = registerB("limestone_wall", () -> new ExtendedWallBlock(ExtendedBlocks.LIMESTONE_ROCK));
	//		ExtendedBlocks.LIMESTONE_BRICK = registerB("limestone_brick", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.LIMESTONE_BRICK_SLAB = registerB("limestone_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.LIMESTONE_BRICK));
	//		ExtendedBlocks.LIMESTONE_BRICK_STAIRS = registerB("limestone_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.LIMESTONE_BRICK));
	//		ExtendedBlocks.LIMESTONE_BRICK_WALL = registerB("limestone_brick_wall", () -> new ExtendedWallBlock(ExtendedBlocks.LIMESTONE_BRICK));
	//		ExtendedBlocks.POLISHED_LIMESTONE = registerB("polished_limestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.POLISHED_LIMESTONE_SLAB = registerB("polished_limestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.POLISHED_LIMESTONE));
	//		ExtendedBlocks.POLISHED_LIMESTONE_STAIRS = registerB("polished_limestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.POLISHED_LIMESTONE));
	//		ExtendedBlocks.POLISHED_LIMESTONE_WALL = registerB("polished_limestone_wall", () -> new ExtendedWallBlock(ExtendedBlocks.POLISHED_LIMESTONE));
	//		ExtendedBlocks.SMOOTH_LIMESTONE = registerB("smooth_limestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.SMOOTH_LIMESTONE_SLAB = registerB("smooth_limestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.SMOOTH_LIMESTONE));
	//		ExtendedBlocks.SMOOTH_LIMESTONE_STAIRS = registerB("smooth_limestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SMOOTH_LIMESTONE));
	//		ExtendedBlocks.SMOOTH_LIMESTONE_WALL = registerB("smooth_limestone_wall", () -> new ExtendedWallBlock(ExtendedBlocks.SMOOTH_LIMESTONE));
	//		ExtendedBlocks.LIMESTONE_TILE = registerB("limestone_tile", () -> new ExtendedStoneBlock(MaterialColor.STONE));
	//		ExtendedBlocks.LIMESTONE_TILE_SLAB = registerB("limestone_tile_slab", () -> new AxialSlabBlock(ExtendedBlocks.LIMESTONE_TILE));
	//		ExtendedBlocks.LIMESTONE_TILE_STAIRS = registerB("limestone_tile_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.LIMESTONE_TILE));
	//		ExtendedBlocks.LIMESTONE_TILE_WALL = registerB("limestone_tile_wall", () -> new ExtendedWallBlock(ExtendedBlocks.LIMESTONE_TILE));
	//		ExtendedBlocks.SLATE_ROCK = registerB("slate", () -> new ExtendedStoneBlock(MaterialColor.QUARTZ));
	//		ExtendedBlocks.SLATE_ROCK_SLAB = registerB("slate_slab", () -> new AxialSlabBlock(ExtendedBlocks.SLATE_ROCK));
	//		ExtendedBlocks.SLATE_ROCK_STAIRS = registerB("slate_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SLATE_ROCK));
	//		ExtendedBlocks.SLATE_ROCK_WALL = registerB("slate_wall", () -> new ExtendedWallBlock(ExtendedBlocks.SLATE_ROCK));
	//		ExtendedBlocks.SLATE_BRICK = registerB("slate_brick", () -> new ExtendedStoneBlock(MaterialColor.QUARTZ));
	//		ExtendedBlocks.SLATE_BRICK_SLAB = registerB("slate_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.SLATE_BRICK));
	//		ExtendedBlocks.SLATE_BRICK_STAIRS = registerB("slate_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SLATE_BRICK));
	//		ExtendedBlocks.SLATE_BRICK_WALL = registerB("slate_brick_wall", () -> new ExtendedWallBlock(ExtendedBlocks.SLATE_BRICK));
	//		ExtendedBlocks.POLISHED_SLATE = registerB("polished_slate", () -> new ExtendedStoneBlock(MaterialColor.QUARTZ));
	//		ExtendedBlocks.POLISHED_SLATE_SLAB = registerB("polished_slate_slab", () -> new AxialSlabBlock(ExtendedBlocks.POLISHED_SLATE));
	//		ExtendedBlocks.POLISHED_SLATE_STAIRS = registerB("polished_slate_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.POLISHED_SLATE));
	//		ExtendedBlocks.POLISHED_SLATE_WALL = registerB("polished_slate_wall", () -> new ExtendedWallBlock(ExtendedBlocks.POLISHED_SLATE));

	public static RegistryObject<Block> MOSS_BLOCK = register("moss_block", () -> new ExtendedMossBlock(Properties.of(ExtendedMaterials.MOSS, MaterialColor.COLOR_GREEN).harvestTool(ToolType.HOE).strength(0.1f).sound(ExtendedSoundTypes.MOSS)));
	public static RegistryObject<Block> MOSS_CARPET = register("moss_carpet", () -> new ExtendedMossCarpetBlock(Properties.of(Material.PLANT, MaterialColor.COLOR_GREEN).strength(0.1f).sound(ExtendedSoundTypes.MOSS)));
	public static RegistryObject<Block> ANCIENT_RUNE_BLOCK = register("ancient_rune_stone", () -> new ExtendedAncientStoneRuneBlock(Properties.of(Material.STONE, MaterialColor.STONE).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE).strength(1.5F, 6.0F)));

	//Mud
	public static RegistryObject<Block> MUD = register("mud", () -> new ExtendedGroundDirtBlock(MaterialColor.DIRT, SoundType.GRAVEL, ExtendedBlocks.MUD_PATH, ExtendedBlocks.MUD_FARMLAND));
	public static RegistryObject<Block> MUD_SLAB = register("mud_slab", () -> new ExtendedAxialSlabDirtBlock(MUD.get(), ExtendedBlocks.MUD_PATH_SLAB));
	public static RegistryObject<Block> MUD_STAIRS = register("mud_stairs", () -> new LOTRStairsBlock(MUD));
	public static RegistryObject<Block> MUD_PATH = register("mud_path", () -> new ExtendedGroundPathBlock(MaterialColor.DIRT, SoundType.GRASS, MUD));
	public static RegistryObject<Block> MUD_PATH_SLAB = register("mud_path_slab", () -> new ExtendedAxialSlabPathBlock(MUD_PATH.get()));
	public static RegistryObject<Block> MUD_PATH_STAIRS = register("mud_path_stairs", () -> new LOTRStairsBlock(MUD_PATH));
	public static RegistryObject<Block> MUD_GRASS = register("mud_grass", () -> new ExtendedGroundGrassBlock(MaterialColor.DIRT, SoundType.GRASS, MUD, MUD_PATH, ExtendedBlocks.MUD_FARMLAND));
	public static RegistryObject<Block> MUD_FARMLAND = register("mud_farmland", () -> new ExtendedGroundFarmlandBlock(MaterialColor.DIRT, SoundType.GRAVEL, MUD));
	public static RegistryObject<Block> MUD_BRICK = register("mud_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BROWN));
	public static RegistryObject<Block> MUD_BRICK_SLAB = register("mud_brick_slab", () -> new AxialSlabBlock(MUD_BRICK));
	public static RegistryObject<Block> MUD_BRICK_STAIRS = register("mud_brick_stairs", () -> new LOTRStairsBlock(MUD_BRICK));
	public static RegistryObject<Block> MUD_BRICK_WALL = register("mud_brick_wall", () -> new ExtendedWallBlock(MUD_BRICK));
	public static RegistryObject<Block> CRACKED_MUD_BRICK = register("cracked_mud_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BROWN));
	public static RegistryObject<Block> CRACKED_MUD_BRICK_SLAB = register("cracked_mud_brick_slab", () -> new AxialSlabBlock(CRACKED_MUD_BRICK));
	public static RegistryObject<Block> CRACKED_MUD_BRICK_STAIRS = register("cracked_mud_brick_stairs", () -> new LOTRStairsBlock(CRACKED_MUD_BRICK));
	public static RegistryObject<Block> CRACKED_MUD_BRICK_WALL = register("cracked_mud_brick_wall", () -> new ExtendedWallBlock(CRACKED_MUD_BRICK));
	public static RegistryObject<Block> MOSSY_MUD_BRICK = register("mossy_mud_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BROWN));
	public static RegistryObject<Block> MOSSY_MUD_BRICK_SLAB = register("mossy_mud_brick_slab", () -> new AxialSlabBlock(MOSSY_MUD_BRICK));
	public static RegistryObject<Block> MOSSY_MUD_BRICK_STAIRS = register("mossy_mud_brick_stairs", () -> new LOTRStairsBlock(MOSSY_MUD_BRICK));
	public static RegistryObject<Block> MOSSY_MUD_BRICK_WALL = register("mossy_mud_brick_wall", () -> new ExtendedWallBlock(MOSSY_MUD_BRICK));
	//Taurethrim
	public static RegistryObject<Block> TAURETHRIM_BRICK = register("taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GREEN));
	public static RegistryObject<Block> TAURETHRIM_BRICK_SLAB = register("taurethrim_brick_slab", () -> new AxialSlabBlock(TAURETHRIM_BRICK));
	public static RegistryObject<Block> TAURETHRIM_BRICK_STAIRS = register("taurethrim_brick_stairs", () -> new LOTRStairsBlock(TAURETHRIM_BRICK));
	public static RegistryObject<Block> TAURETHRIM_BRICK_WALL = register("taurethrim_brick_wall", () -> new ExtendedWallBlock(TAURETHRIM_BRICK));
	public static RegistryObject<Block> TAURETHRIM_PILLAR = register("taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GREEN));
	public static RegistryObject<Block> TAURETHRIM_PILLAR_SLAB = register("taurethrim_pillar_slab", () -> new AxialSlabBlock(TAURETHRIM_PILLAR));

	public static RegistryObject<Block> MOSSY_TAURETHRIM_BRICK = register("mossy_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GREEN));
	public static RegistryObject<Block> MOSSY_TAURETHRIM_BRICK_SLAB = register("mossy_taurethrim_brick_slab", () -> new AxialSlabBlock(MOSSY_TAURETHRIM_BRICK));
	public static RegistryObject<Block> MOSSY_TAURETHRIM_BRICK_STAIRS = register("mossy_taurethrim_brick_stairs", () -> new LOTRStairsBlock(MOSSY_TAURETHRIM_BRICK));
	public static RegistryObject<Block> MOSSY_TAURETHRIM_BRICK_WALL = register("mossy_taurethrim_brick_wall", () -> new ExtendedWallBlock(MOSSY_TAURETHRIM_BRICK));

	public static RegistryObject<Block> CRACKED_TAURETHRIM_BRICK = register("cracked_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GREEN));
	public static RegistryObject<Block> CRACKED_TAURETHRIM_BRICK_SLAB = register("cracked_taurethrim_brick_slab", () -> new AxialSlabBlock(CRACKED_TAURETHRIM_BRICK));
	public static RegistryObject<Block> CRACKED_TAURETHRIM_BRICK_STAIRS = register("cracked_taurethrim_brick_stairs", () -> new LOTRStairsBlock(CRACKED_TAURETHRIM_BRICK));
	public static RegistryObject<Block> CRACKED_TAURETHRIM_BRICK_WALL = register("cracked_taurethrim_brick_wall", () -> new ExtendedWallBlock(CRACKED_TAURETHRIM_BRICK));

	public static RegistryObject<Block> GOLDEN_TAURETHRIM_BRICK = register("golden_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
	public static RegistryObject<Block> GOLDEN_TAURETHRIM_BRICK_SLAB = register("golden_taurethrim_brick_slab", () -> new AxialSlabBlock(GOLDEN_TAURETHRIM_BRICK));
	public static RegistryObject<Block> GOLDEN_TAURETHRIM_BRICK_STAIRS = register("golden_taurethrim_brick_stairs", () -> new LOTRStairsBlock(GOLDEN_TAURETHRIM_BRICK));
	public static RegistryObject<Block> GOLDEN_TAURETHRIM_BRICK_WALL = register("golden_taurethrim_brick_wall", () -> new ExtendedWallBlock(GOLDEN_TAURETHRIM_BRICK));
	public static RegistryObject<Block> GOLDEN_TAURETHRIM_PILLAR = register("golden_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.GOLD));
	public static RegistryObject<Block> GOLDEN_TAURETHRIM_PILLAR_SLAB = register("golden_taurethrim_pillar_slab", () -> new AxialSlabBlock(GOLDEN_TAURETHRIM_PILLAR));

	public static RegistryObject<Block> OBSIDIAN_TAURETHRIM_BRICK = register("obsidian_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
	public static RegistryObject<Block> OBSIDIAN_TAURETHRIM_BRICK_SLAB = register("obsidian_taurethrim_brick_slab", () -> new AxialSlabBlock(OBSIDIAN_TAURETHRIM_BRICK));
	public static RegistryObject<Block> OBSIDIAN_TAURETHRIM_BRICK_STAIRS = register("obsidian_taurethrim_brick_stairs", () -> new LOTRStairsBlock(OBSIDIAN_TAURETHRIM_BRICK));
	public static RegistryObject<Block> OBSIDIAN_TAURETHRIM_BRICK_WALL = register("obsidian_taurethrim_brick_wall", () -> new ExtendedWallBlock(OBSIDIAN_TAURETHRIM_BRICK));
	public static RegistryObject<Block> OBSIDIAN_TAURETHRIM_PILLAR = register("obsidian_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_BLACK));
	public static RegistryObject<Block> OBSIDIAN_TAURETHRIM_PILLAR_SLAB = register("obsidian_taurethrim_pillar_slab", () -> new AxialSlabBlock(OBSIDIAN_TAURETHRIM_PILLAR));

	public static RegistryObject<Block> OBSIDIAN_DWARVEN_BRICK = register("obsidian_dwarven_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
	public static RegistryObject<Block> OBSIDIAN_DWARVEN_BRICK_SLAB = register("obsidian_dwarven_brick_slab", () -> new AxialSlabBlock(OBSIDIAN_DWARVEN_BRICK));
	public static RegistryObject<Block> OBSIDIAN_DWARVEN_BRICK_STAIRS = register("obsidian_dwarven_brick_stairs", () -> new LOTRStairsBlock(OBSIDIAN_DWARVEN_BRICK));
	public static RegistryObject<Block> OBSIDIAN_DWARVEN_BRICK_WALL = register("obsidian_dwarven_brick_wall", () -> new ExtendedWallBlock(OBSIDIAN_DWARVEN_BRICK));
	// ANGMAR
	public static RegistryObject<Block> RED_ANGMAR_BRICK = register("red_angmar_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> RED_ANGMAR_BRICK_SLAB = register("red_angmar_brick_slab", () -> new AxialSlabBlock(RED_ANGMAR_BRICK));
	public static RegistryObject<Block> RED_ANGMAR_BRICK_STAIRS = register("red_angmar_brick_stairs", () -> new LOTRStairsBlock(RED_ANGMAR_BRICK));
	public static RegistryObject<Block> RED_ANGMAR_BRICK_WALL = register("red_angmar_brick_wall", () -> new ExtendedWallBlock(RED_ANGMAR_BRICK));
	public static RegistryObject<Block> CRACKED_RED_ANGMAR_BRICK = register("cracked_red_angmar_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> CRACKED_RED_ANGMAR_BRICK_SLAB = register("cracked_red_angmar_brick_slab", () -> new AxialSlabBlock(CRACKED_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> CRACKED_RED_ANGMAR_BRICK_STAIRS = register("cracked_red_angmar_brick_stairs", () -> new LOTRStairsBlock(CRACKED_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> CRACKED_RED_ANGMAR_BRICK_WALL = register("cracked_red_angmar_brick_wall", () -> new ExtendedWallBlock(CRACKED_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> MOSSY_RED_ANGMAR_BRICK = register("mossy_red_angmar_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> MOSSY_RED_ANGMAR_BRICK_SLAB = register("mossy_red_angmar_brick_slab", () -> new AxialSlabBlock(MOSSY_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> MOSSY_RED_ANGMAR_BRICK_STAIRS = register("mossy_red_angmar_brick_stairs", () -> new LOTRStairsBlock(MOSSY_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> MOSSY_RED_ANGMAR_BRICK_WALL = register("mossy_red_angmar_brick_wall", () -> new ExtendedWallBlock(MOSSY_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> SNOWY_RED_ANGMAR_BRICK = register("snowy_red_angmar_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> SNOWY_RED_ANGMAR_BRICK_SLAB = register("snowy_red_angmar_brick_slab", () -> new AxialSlabBlock(SNOWY_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> SNOWY_RED_ANGMAR_BRICK_STAIRS = register("snowy_red_angmar_brick_stairs", () -> new LOTRStairsBlock(SNOWY_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> SNOWY_RED_ANGMAR_BRICK_WALL = register("snowy_red_angmar_brick_wall", () -> new ExtendedWallBlock(SNOWY_RED_ANGMAR_BRICK));
	public static RegistryObject<Block> RED_ANGMAR_PILLAR = register("red_angmar_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> RED_ANGMAR_PILLAR_SLAB = register("red_angmar_pillar_slab", () -> new AxialSlabBlock(RED_ANGMAR_PILLAR));
	public static RegistryObject<Block> CARVED_RED_ANGMAR_BRICK = register("carved_red_angmar_brick", () -> new ExtendedStoneBlock(RED_ANGMAR_BRICK));

	//Dol Guldur
	public static RegistryObject<Block> DOL_GULDUR_BRICK = register("dol_guldur_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
	public static RegistryObject<Block> DOL_GULDUR_BRICK_SLAB = register("dol_guldur_brick_slab", () -> new AxialSlabBlock(DOL_GULDUR_BRICK));
	public static RegistryObject<Block> DOL_GULDUR_BRICK_STAIRS = register("dol_guldur_brick_stairs", () -> new LOTRStairsBlock(DOL_GULDUR_BRICK));
	public static RegistryObject<Block> DOL_GULDUR_BRICK_WALL = register("dol_guldur_brick_wall", () -> new ExtendedWallBlock(DOL_GULDUR_BRICK));
	public static RegistryObject<Block> DOL_GULDUR_PILLAR = register("dol_guldur_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GRAY));
	public static RegistryObject<Block> DOL_GULDUR_PILLAR_SLAB = register("dol_guldur_pillar_slab", () -> new AxialSlabBlock(DOL_GULDUR_PILLAR));

	public static RegistryObject<Block> MOSSY_DOL_GULDUR_BRICK = register("mossy_dol_guldur_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
	public static RegistryObject<Block> MOSSY_DOL_GULDUR_BRICK_SLAB = register("mossy_dol_guldur_brick_slab", () -> new AxialSlabBlock(MOSSY_DOL_GULDUR_BRICK));
	public static RegistryObject<Block> MOSSY_DOL_GULDUR_BRICK_STAIRS = register("mossy_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(MOSSY_DOL_GULDUR_BRICK));
	public static RegistryObject<Block> MOSSY_DOL_GULDUR_BRICK_WALL = register("mossy_dol_guldur_brick_wall", () -> new ExtendedWallBlock(MOSSY_DOL_GULDUR_BRICK));

	public static RegistryObject<Block> CRACKED_DOL_GULDUR_BRICK = register("cracked_dol_guldur_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
	public static RegistryObject<Block> CRACKED_DOL_GULDUR_BRICK_SLAB = register("cracked_dol_guldur_brick_slab", () -> new AxialSlabBlock(CRACKED_DOL_GULDUR_BRICK));
	public static RegistryObject<Block> CRACKED_DOL_GULDUR_BRICK_STAIRS = register("cracked_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(CRACKED_DOL_GULDUR_BRICK));
	public static RegistryObject<Block> CRACKED_DOL_GULDUR_BRICK_WALL = register("cracked_dol_guldur_brick_wall", () -> new ExtendedWallBlock(CRACKED_DOL_GULDUR_BRICK));

	public static RegistryObject<Block> CARVED_DOL_GULDUR_BRICK = register("carved_dol_guldur_brick", () -> new ExtendedStoneBlock(DOL_GULDUR_BRICK));

	//Rhunic
	public static RegistryObject<Block> RHUNIC_BRICK = register("rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
	public static RegistryObject<Block> RHUNIC_BRICK_SLAB = register("rhunic_brick_slab", () -> new AxialSlabBlock(RHUNIC_BRICK));
	public static RegistryObject<Block> RHUNIC_BRICK_STAIRS = register("rhunic_brick_stairs", () -> new LOTRStairsBlock(RHUNIC_BRICK));
	public static RegistryObject<Block> RHUNIC_BRICK_WALL = register("rhunic_brick_wall", () -> new ExtendedWallBlock(RHUNIC_BRICK));
	public static RegistryObject<Block> RHUNIC_PILLAR = register("rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_YELLOW));
	public static RegistryObject<Block> RHUNIC_PILLAR_SLAB = register("rhunic_pillar_slab", () -> new AxialSlabBlock(RHUNIC_PILLAR));
	public static RegistryObject<Block> CARVED_RHUNIC_BRICK = register("carved_rhunic_brick", () -> new ExtendedStoneBlock(RHUNIC_BRICK));

	public static RegistryObject<Block> GOLD_TRIMMED_RHUNIC_BRICK = register("gold_trimmed_rhunic_brick", () -> new ExtendedStoneBlock(RHUNIC_BRICK));
	public static RegistryObject<Block> GOLD_TRIMMED_RHUNIC_BRICK_SLAB = register("gold_trimmed_rhunic_brick_slab", () -> new AxialSlabBlock(RHUNIC_BRICK));
	public static RegistryObject<Block> GOLD_TRIMMED_RHUNIC_BRICK_STAIRS = register("gold_trimmed_rhunic_brick_stairs", () -> new LOTRStairsBlock(RHUNIC_BRICK));
	public static RegistryObject<Block> GOLD_TRIMMED_RHUNIC_BRICK_WALL = register("gold_trimmed_rhunic_brick_wall", () -> new ExtendedWallBlock(RHUNIC_BRICK));

	public static RegistryObject<Block> CRACKED_RHUNIC_BRICK = register("cracked_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
	public static RegistryObject<Block> CRACKED_RHUNIC_BRICK_SLAB = register("cracked_rhunic_brick_slab", () -> new AxialSlabBlock(CRACKED_RHUNIC_BRICK));
	public static RegistryObject<Block> CRACKED_RHUNIC_BRICK_STAIRS = register("cracked_rhunic_brick_stairs", () -> new LOTRStairsBlock(CRACKED_RHUNIC_BRICK));
	public static RegistryObject<Block> CRACKED_RHUNIC_BRICK_WALL = register("cracked_rhunic_brick_wall", () -> new ExtendedWallBlock(CRACKED_RHUNIC_BRICK));

	public static RegistryObject<Block> MOSSY_RHUNIC_BRICK = register("mossy_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
	public static RegistryObject<Block> MOSSY_RHUNIC_BRICK_SLAB = register("mossy_rhunic_brick_slab", () -> new AxialSlabBlock(MOSSY_RHUNIC_BRICK));
	public static RegistryObject<Block> MOSSY_RHUNIC_BRICK_STAIRS = register("mossy_rhunic_brick_stairs", () -> new LOTRStairsBlock(MOSSY_RHUNIC_BRICK));
	public static RegistryObject<Block> MOSSY_RHUNIC_BRICK_WALL = register("mossy_rhunic_brick_wall", () -> new ExtendedWallBlock(MOSSY_RHUNIC_BRICK));

	public static RegistryObject<Block> FLOWERY_RHUNIC_BRICK = register("flowery_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
	public static RegistryObject<Block> FLOWERY_RHUNIC_BRICK_SLAB = register("flowery_rhunic_brick_slab", () -> new AxialSlabBlock(FLOWERY_RHUNIC_BRICK));
	public static RegistryObject<Block> FLOWERY_RHUNIC_BRICK_STAIRS = register("flowery_rhunic_brick_stairs", () -> new LOTRStairsBlock(FLOWERY_RHUNIC_BRICK));
	public static RegistryObject<Block> FLOWERY_RHUNIC_BRICK_WALL = register("flowery_rhunic_brick_wall", () -> new ExtendedWallBlock(FLOWERY_RHUNIC_BRICK));

	public static RegistryObject<Block> RED_RHUNIC_BRICK = register("red_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> RED_RHUNIC_BRICK_SLAB = register("red_rhunic_brick_slab", () -> new AxialSlabBlock(RED_RHUNIC_BRICK));
	public static RegistryObject<Block> RED_RHUNIC_BRICK_STAIRS = register("red_rhunic_brick_stairs", () -> new LOTRStairsBlock(RED_RHUNIC_BRICK));
	public static RegistryObject<Block> RED_RHUNIC_BRICK_WALL = register("red_rhunic_brick_wall", () -> new ExtendedWallBlock(RED_RHUNIC_BRICK));
	public static RegistryObject<Block> RED_RHUNIC_PILLAR = register("red_rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> RED_RHUNIC_PILLAR_SLAB = register("red_rhunic_pillar_slab", () -> new AxialSlabBlock(RED_RHUNIC_PILLAR));
	public static RegistryObject<Block> CARVED_RED_RHUNIC_BRICK = register("carved_red_rhunic_brick", () -> new ExtendedStoneBlock(RED_RHUNIC_BRICK));

	public static RegistryObject<Block> CRACKED_RED_RHUNIC_BRICK = register("cracked_red_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> CRACKED_RED_RHUNIC_BRICK_SLAB = register("cracked_red_rhunic_brick_slab", () -> new AxialSlabBlock(CRACKED_RED_RHUNIC_BRICK));
	public static RegistryObject<Block> CRACKED_RED_RHUNIC_BRICK_STAIRS = register("cracked_red_rhunic_brick_stairs", () -> new LOTRStairsBlock(CRACKED_RED_RHUNIC_BRICK));
	public static RegistryObject<Block> CRACKED_RED_RHUNIC_BRICK_WALL = register("cracked_red_rhunic_brick_wall", () -> new ExtendedWallBlock(CRACKED_RED_RHUNIC_BRICK));

	public static RegistryObject<Block> MOSSY_RED_RHUNIC_BRICK = register("mossy_red_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
	public static RegistryObject<Block> MOSSY_RED_RHUNIC_BRICK_SLAB = register("mossy_red_rhunic_brick_slab", () -> new AxialSlabBlock(MOSSY_RED_RHUNIC_BRICK));
	public static RegistryObject<Block> MOSSY_RED_RHUNIC_BRICK_STAIRS = register("mossy_red_rhunic_brick_stairs", () -> new LOTRStairsBlock(MOSSY_RED_RHUNIC_BRICK));
	public static RegistryObject<Block> MOSSY_RED_RHUNIC_BRICK_WALL = register("mossy_red_rhunic_brick_wall", () -> new ExtendedWallBlock(MOSSY_RED_RHUNIC_BRICK));

	public static RegistryObject<Block> ROHAN_BEAM = register("rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F).harvestTool(ToolType.AXE)));
	public static RegistryObject<Block> ORNATE_ROHAN_BEAM = register("ornate_rohan_beam", () -> new LOTRPillarBlock(Properties.copy(ROHAN_BEAM.get())));
	public static RegistryObject<Block> GOLDEN_ROHAN_BEAM = register("golden_rohan_beam", () -> new LOTRPillarBlock(Properties.copy(ROHAN_BEAM.get())));
	public static RegistryObject<Block> ORNATE_GOLDEN_ROHAN_BEAM = register("ornate_golden_rohan_beam", () -> new LOTRPillarBlock(Properties.copy(ROHAN_BEAM.get())));

	// Goblin
	public static RegistryObject<Block> GOBLIN_BRICK = register("goblin_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GRAY));
	public static RegistryObject<Block> GOBLIN_BRICK_SLAB = register("goblin_brick_slab", () -> new AxialSlabBlock(GOBLIN_BRICK));
	public static RegistryObject<Block> GOBLIN_BRICK_STAIRS = register("goblin_brick_stairs", () -> new LOTRStairsBlock(GOBLIN_BRICK));
	public static RegistryObject<Block> GOBLIN_BRICK_WALL = register("goblin_brick_wall", () -> new ExtendedWallBlock(GOBLIN_BRICK));
	public static RegistryObject<Block> GOBLIN_PILLAR = register("goblin_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GRAY));
	public static RegistryObject<Block> GOBLIN_PILLAR_SLAB = register("goblin_pillar_slab", () -> new AxialSlabBlock(GOBLIN_PILLAR));

	public static RegistryObject<Block> ORC_PLATING_WALL = register("orc_plating_wall", () -> new ExtendedWallBlock(LOTRBlocks.ORC_PLATING));
	public static RegistryObject<Block> RUSTED_ORC_PLATING_WALL = register("rusted_orc_plating_wall", () -> new ExtendedWallBlock(LOTRBlocks.RUSTED_ORC_PLATING));

	//Gondor
	public static RegistryObject<Block> CARVED_BLACK_GONDOR_BRICK = register("black_carved_gondor_brick", () -> new ExtendedStoneBlock(LOTRBlocks.NUMENOREAN_BRICK));
	public static RegistryObject<Block> CARVED_BLACK_UMBAR_BRICK = register("carved_black_umbar_brick", () -> new ExtendedStoneBlock(LOTRBlocks.NUMENOREAN_BRICK));
	public static RegistryObject<Block> CARVED_UMBAR_BRICK = register("carved_umbar_brick", () -> new ExtendedStoneBlock(LOTRBlocks.UMBAR_BRICK));
	public static RegistryObject<Block> SMOOTH_GONDOR_ROCK_STAIRS = register("smooth_gondor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_GONDOR_ROCK));

	public static RegistryObject<Block> MORGOND = register("morgond", () -> new ExtendedStoneBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(500.0F, 1200.0F)));
	public static RegistryObject<Block> MORGOND_SLAB = register("morgond_slab", () -> new AxialSlabBlock(MORGOND));
	public static RegistryObject<Block> MORGOND_STAIRS = register("morgond_stairs", () -> new LOTRStairsBlock(MORGOND));
	public static RegistryObject<Block> MORGOND_WALL = register("morgond_wall", () -> new ExtendedWallBlock(MORGOND));
	public static RegistryObject<Block> MORGOND_DRIPSTONE = register("morgond_dripstone", () -> new DripstoneBlock(MORGOND));
	public static RegistryObject<Block> MORGOND_PILLAR = register("morgond_pillar", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(500.0F, 1200.0F)));
	public static RegistryObject<Block> MORGOND_PILLAR_SLAB = register("morgond_pillar_slab", () -> new AxialSlabBlock(MORGOND_PILLAR));
	public static RegistryObject<Block> CARVED_MORGOND = register("carved_morgond", () -> new ExtendedStoneBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(500.0F, 1200.0F)));
	public static RegistryObject<Block> CARVED_MORGOND_SLAB = register("carved_morgond_slab", () -> new AxialSlabBlock(CARVED_MORGOND));
	public static RegistryObject<Block> CARVED_MORGOND_STAIRS = register("carved_morgond_stairs", () -> new LOTRStairsBlock(CARVED_MORGOND));
	public static RegistryObject<Block> CARVED_MORGOND_WALL = register("carved_morgond_wall", () -> new ExtendedWallBlock(CARVED_MORGOND));
	public static RegistryObject<Block> SMOOTH_MORGOND = register("smooth_morgond", () -> new ExtendedStoneBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(500.0F, 1200.0F)));
	public static RegistryObject<Block> SMOOTH_MORGOND_SLAB = register("smooth_morgond_slab", () -> new AxialSlabBlock(SMOOTH_MORGOND));
	public static RegistryObject<Block> SMOOTH_MORGOND_STAIRS = register("smooth_morgond_stairs", () -> new LOTRStairsBlock(SMOOTH_MORGOND));
	public static RegistryObject<Block> SMOOTH_MORGOND_WALL = register("smooth_morgond_wall", () -> new ExtendedWallBlock(SMOOTH_MORGOND));
	public static RegistryObject<Block> SMOOTH_MORGOND_DRIPSTONE = register("smooth_morgond_dripstone", () -> new DripstoneBlock(SMOOTH_MORGOND));

	//Rohan
	public static RegistryObject<Block> SMOOTH_ROHAN_ROCK_STAIRS = register("smooth_rohan_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_ROHAN_ROCK));

	//Blue Mountains
	public static RegistryObject<Block> SMOOTH_BLUE_ROCK_STAIRS = register("smooth_blue_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_BLUE_ROCK));

	//Red Mountains
	public static RegistryObject<Block> SMOOTH_RED_ROCK_STAIRS = register("smooth_red_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_RED_ROCK));

	//Mordor
	public static RegistryObject<Block> SMOOTH_MORDOR_ROCK_STAIRS = register("smooth_mordor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_MORDOR_ROCK));

	public static RegistryObject<Block> CROSSED_WATTLE_AND_DAUB = register("crossed_wattle_and_daub", () -> new ExtendedThreeTallPillarBlock(Properties.of(Material.GRASS, MaterialColor.QUARTZ).harvestTool(ToolType.AXE).sound(SoundType.SCAFFOLDING).strength(1.0F)));
	//Drytone/stone
	public static RegistryObject<Block> DRYSTONE_SLAB = register("drystone_slab", () -> new AxialSlabBlock(LOTRBlocks.DRYSTONE));
	public static RegistryObject<Block> DRYSTONE_STAIRS = register("drystone_stairs", () -> new LOTRStairsBlock(LOTRBlocks.DRYSTONE));
	public static RegistryObject<Block> DRYSTONE_WALL = register("drystone_wall", () -> new ExtendedWallBlock(LOTRBlocks.DRYSTONE));
	public static RegistryObject<Block> COBBLED_DRYSTONE = register("cobbled_drystone", () -> new ExtendedStoneBlock(LOTRBlocks.DRYSTONE));
	public static RegistryObject<Block> CUT_DRYSTONE = register("cut_drystone", () -> new ExtendedStoneBlock(LOTRBlocks.DRYSTONE));
	public static RegistryObject<Block> SMOOTH_STONE_STAIRS = register("smooth_stone_stairs", () -> new LOTRStairsBlock(Blocks.SMOOTH_STONE));

	public static RegistryObject<Block> MOSSY_DRYSTONE = register("mossy_drystone", () -> new ExtendedStoneBlock(LOTRBlocks.DRYSTONE));
	public static RegistryObject<Block> MOSSY_DRYSTONE_SLAB = register("mossy_drystone_slab", () -> new AxialSlabBlock(MOSSY_DRYSTONE));
	public static RegistryObject<Block> MOSSY_DRYSTONE_STAIRS = register("mossy_drystone_stairs", () -> new LOTRStairsBlock(MOSSY_DRYSTONE));
	public static RegistryObject<Block> MOSSY_DRYSTONE_WALL = register("mossy_drystone_wall", () -> new ExtendedWallBlock(MOSSY_DRYSTONE));

	//BUSHES
	public static RegistryObject<Block> BLACKBERRY_HEDGE = register("blackberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.BLACKBERRY, ExtendedItems.BLACKBERRY_HEDGE));
	public static RegistryObject<Block> BLUEBERRY_HEDGE = register("blueberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.BLUEBERRY, ExtendedItems.BLUEBERRY_HEDGE));
	public static RegistryObject<Block> CRANBERRY_HEDGE = register("cranberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.CRANBERRY, ExtendedItems.CRANBERRY_HEDGE));
	public static RegistryObject<Block> ELDERBERRY_HEDGE = register("elderberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.ELDERBERRY, ExtendedItems.ELDERBERRY_HEDGE));
	public static RegistryObject<Block> RASPBERRY_HEDGE = register("raspberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.RASPBERRY, ExtendedItems.RASPBERRY_HEDGE));
	public static RegistryObject<Block> WILDBERRY_HEDGE = register("wildberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.WILDBERRY, ExtendedItems.WILDBERRY_HEDGE));
	public static RegistryObject<Block> BLACKBERRY_BUSH = register("blackberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.BLACKBERRY, BLACKBERRY_HEDGE));
	public static RegistryObject<Block> BLUEBERRY_BUSH = register("blueberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.BLUEBERRY, BLUEBERRY_HEDGE));
	public static RegistryObject<Block> CRANBERRY_BUSH = register("cranberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.CRANBERRY, CRANBERRY_HEDGE));
	public static RegistryObject<Block> ELDERBERRY_BUSH = register("elderberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.ELDERBERRY, ELDERBERRY_HEDGE));
	public static RegistryObject<Block> RASPBERRY_BUSH = register("raspberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.RASPBERRY, RASPBERRY_HEDGE));
	public static RegistryObject<Block> WILDBERRY_BUSH = register("wildberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.WILDBERRY, WILDBERRY_HEDGE));

	//CRAFTING TABLES
	public static RegistryObject<Block> TAURETHRIM_CRAFTING_TABLE = register("taurethrim_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Taurethrim(TAURETHRIM_BRICK));
	public static RegistryObject<Block> DOL_GULDUR_CRAFTING_TABLE = register("dol_guldur_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Dol_Guldur(DOL_GULDUR_BRICK));
	public static RegistryObject<Block> RHUNIC_CRAFTING_TABLE = register("rhunic_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Rhunic(RHUNIC_BRICK));
	public static RegistryObject<Block> HALF_TROLL_CRAFTING_TABLE = register("half_troll_crafting_table", () -> new ExtendedFactionCraftingTableBlock.HalfTroll(()-> Blocks.COBBLESTONE));
	public static RegistryObject<Block> NORTHERN_ORC_CRAFTING_TABLE = register("gundabad_crafting_table", () -> new ExtendedFactionCraftingTableBlock.NorthernOrc(GOBLIN_BRICK));
	public static RegistryObject<Block> MORWAITH_CRAFTING_TABLE = register("morwaith_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Morwaith(()-> Blocks.TERRACOTTA));

	public static RegistryObject<Block> CONQUEST_TABLE = register("conquest_table", () -> new ExtendedConquestTableBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.5F)));
	public static RegistryObject<Block> STONE_GRINDER = register("stone_grinder", () -> new ExtendedStoneGrinderBlock(Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0f)));
	public static RegistryObject<Block> CRUCIBLE = register("crucible", () -> new ExtendedCrucibleBlock(Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 6.0F).lightLevel(ExtendedBlockParms.litBlockEmission(10))));

	public static RegistryObject<Block> MORGUL_BRAZIER = register("morgul_brazier", () -> new ExtendedBrazierBlock(12));
	public static RegistryObject<Block> MORGUL_CHANDELIER = register("morgul_chandelier", () -> new ExtendedChandelierBlock(ExtendedParticles.MORGUL_FLAME));
	public static RegistryObject<Block> ORC_CHANDELIER = register("orc_chandelier", ChandelierBlock::new);
	public static RegistryObject<Block> URUK_CHANDELIER = register("uruk_chandelier", ChandelierBlock::new);
	public static RegistryObject<Block> DWARVEN_CHANDELIER = register("dwarven_chandelier", ChandelierBlock::new);
	public static RegistryObject<Block> BLUE_DWARVEN_CHANDELIER = register("blue_dwarven_chandelier", ChandelierBlock::new);
	public static RegistryObject<Block> HIGH_ELVEN_LANTERN = register("high_elven_lantern", () -> new ExtendedLanternElvenBlock(Properties.of(Material.DECORATION).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(3.5F).sound(SoundType.LANTERN).lightLevel(ExtendedBlockParms.litBlockEmission(12)).noOcclusion()));
	public static RegistryObject<Block> NUMENOREAN_LANTERN = register("numenorean_lantern", () -> new ExtendedLanternNumenoreanBlock(Properties.of(Material.DECORATION).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(3.5F).sound(SoundType.LANTERN).lightLevel(ExtendedBlockParms.litBlockEmission(12)).noOcclusion()));

	public static RegistryObject<Block> ORC_STEEL_DOOR = register("orc_steel_door", () -> new ExtendedDoorBlock(Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).noOcclusion().harvestTool(ToolType.PICKAXE)));
	public static RegistryObject<Block> ORC_STEEL_TRAPDOOR = register("orc_steel_trapdoor", () -> new ExtendedTrapDoorBlock(Properties.copy(ORC_STEEL_DOOR.get())));
	public static RegistryObject<Block> ORC_CHAIN = register("orc_chain", () -> new ExtendedChainBlock(Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion()));

	@SuppressWarnings("unchecked")
	public static RegistryObject<Block> MORGUL_TORCH = register("morgul_torch", () -> new ExtendedLOTRTorchBlock(12).setParticles(ExtendedParticles.MORGUL_FLAME, () -> ParticleTypes.SMOKE));
	public static RegistryObject<Block> MORGUL_WALL_TORCH = register("morgul_torch_wall", () -> new ExtendedLOTRWallTorchBlock(MORGUL_TORCH));
	public static RegistryObject<Block> GREEN_FLAME_HEARTH_BLOCK = register("green_fire_hearth_block", ExtendedGreenHearthBlock::new);
	public static RegistryObject<Block> GREEN_FIRE_BLOCK = register("green_fire", () -> new ExtendedGreenFireBlock(Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN).noCollission().instabreak().lightLevel((blockState) -> 10).sound(SoundType.WOOL)));
	public static RegistryObject<Block> WASTE_BLOCK = register("waste_block", () -> new ExtendedWasteBlock(Properties.of(Material.SAND, MaterialColor.COLOR_BROWN).strength(1.0F).speedFactor(0.4F).harvestTool(ToolType.SHOVEL).sound(SoundType.GRASS).isValidSpawn(ExtendedBlockParms::always).isRedstoneConductor(ExtendedBlockParms::always).isViewBlocking(ExtendedBlockParms::always).isSuffocating(ExtendedBlockParms::always)));
	public static RegistryObject<Block> CHEESE_WHEEL = register("cheese_wheel", () -> new ExtendedCheeseWheelBlock(Properties.copy(Blocks.CAKE), 1.0F, ExtendedItems.CHEESE_SLICE, ExtendedFoods.CHEESE_WHEEL_SLICE, ExtendedCheeseState.UNAFFECTED));
	public static RegistryObject<Block> AGED_CHEESE_WHEEL = register("aged_cheese_wheel", () -> new ExtendedCheeseWheelBlock(Properties.copy(Blocks.CAKE), 1.1F, ExtendedItems.AGED_CHEESE_SLICE, ExtendedFoods.AGED_CHEESE_WHEEL_SLICE, ExtendedCheeseState.AGED));

	public static RegistryObject<Block> BANANA_CAKE = register("banana_cake", () -> new ExtendedCakeBlock(ExtendedFoods.BANANA_CAKE, Stats.EAT_CAKE_SLICE));
	public static RegistryObject<Block> BLACKBERRY_PIE = register("blackberry_pie", () -> new ExtendedCakeBlock(ExtendedFoods.BLACKBERRY_PIE, ExtendedStats.EAT_PIE_SLICE));
	public static RegistryObject<Block> BLUEBERRY_PIE = register("blueberry_pie", () -> new ExtendedCakeBlock(ExtendedFoods.BLUEBERRY_PIE, ExtendedStats.EAT_PIE_SLICE));
	public static RegistryObject<Block> CRANBERRY_PIE = register("cranberry_pie", () -> new ExtendedCakeBlock(ExtendedFoods.CRANBERRY_PIE, ExtendedStats.EAT_PIE_SLICE));
	public static RegistryObject<Block> ELDERBERRY_PIE = register("elderberry_pie", () -> new ExtendedCakeBlock(ExtendedFoods.ELDERBERRY_PIE, ExtendedStats.EAT_PIE_SLICE));
	public static RegistryObject<Block> RASPBERRY_PIE = register("raspberry_pie", () -> new ExtendedCakeBlock(ExtendedFoods.RASPBERRY_PIE, ExtendedStats.EAT_PIE_SLICE));
	public static RegistryObject<Block> SWEET_BERRY_PIE = register("sweet_berry_pie", () -> new ExtendedCakeBlock(ExtendedFoods.SWEET_BERRY_PIE, ExtendedStats.EAT_PIE_SLICE));
	public static RegistryObject<Block> WILDBERRY_PIE = register("wildberry_pie", () -> new ExtendedCakeBlock(ExtendedFoods.WILDBERRY_PIE, ExtendedStats.EAT_PIE_SLICE));

	public static RegistryObject<Block> STONE_CHEST = register("stone_chest", () -> new ExtendedStoneChestBlock(ExtendedStoneChestTileEntity::new));
	public static RegistryObject<Block> REED_BASKET = register("reed_basket", () -> new ExtendedReedBasketBlock(Properties.of(Material.GRASS).sound(SoundType.GRASS).strength(0.5F).noOcclusion()));
	public static RegistryObject<Block> ORC_BARREL = register("orc_barrel", () -> new ExtendedOrcBarrelBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.5F).noOcclusion()));
	public static RegistryObject<Block> WEAPON_RACK = register("weapon_rack", () -> new ExtendedWeaponRackBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.5F).noCollission()));

	public static RegistryObject<Block> STRAW_BED = register("straw_bed", () -> new ExtendedBedBlock(ExtendedBedType.STRAW, Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> ORC_BED = register("orc_bed", () -> new ExtendedBedBlock(ExtendedBedType.ORC, Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> BLACK_FUR_BED = register("black_fur_bed", () -> new ExtendedBedBlock(ExtendedBedType.BLACK_FUR, Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> FUR_BED = register("fur_bed", () -> new ExtendedBedBlock(ExtendedBedType.BROWN_FUR, Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> GRAY_FUR_BED = register("gray_fur_bed", () -> new ExtendedBedBlock(ExtendedBedType.GRAY_FUR, Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> SILVER_FUR_BED = register("silver_fur_bed", () -> new ExtendedBedBlock(ExtendedBedType.SILVER, Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> WHITE_FUR_BED = register("white_fur_bed", () -> new ExtendedBedBlock(ExtendedBedType.WHITE, Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> BEDROLL = register("bedroll", () -> new ExtendedBedrollBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> DWARVEN_BED = register("dwarven_bed", () -> new ExtendedBedBlock(ExtendedBedType.DWARVEN, Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
	public static RegistryObject<Block> HIGH_ELVEN_BED = register("high_elven_bed", () -> new ExtendedBedBlock(ExtendedBedType.HIGH_ELVEN, Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));

	public static RegistryObject<Block> ROPE = register("rope", () -> new ExtendedRopeBlock(Properties.of(Material.DECORATION).sound(ExtendedSoundTypes.ROPE).strength(0.2F).noOcclusion()));

	public static RegistryObject<Block> SKULL_GOBLET = register("skull_goblet", () -> new VesselDrinkBlock(SoundType.BONE_BLOCK, 8.0F, 10.0F));
	public static RegistryObject<Block> GLASS_BOTTLE = register("glass_bottle", () -> new ExtendedBottleVesselDrinkBlock(Properties.of(Material.DECORATION).strength(0.0F).sound(SoundType.GLASS).noOcclusion(), createVesselShape(5, 7)));
	public static RegistryObject<Block> LARGE_POT = register("large_pot", () -> new ExtendedLargePotBlock(Properties.of(Material.DECORATION).instabreak().noOcclusion()));
	public static RegistryObject<Block> CHIMNEYPOT = register("chimney_pot", () -> new ExtendedChimneyPotBlock(Properties.of(Material.DECORATION).instabreak().noOcclusion().randomTicks()));
	// Large flower pot
	public static RegistryObject<Block> LARGE_FLOWER_POT = register("large_flower_pot", ExtendedLargeFlowerPotBlock::new);
	public static RegistryObject<Block> LARGE_FLOWER_POT_APPLE_SAPLING = registerPOT("large_pot_apple_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.APPLE_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_ASPEN_SAPLING = registerPOT("large_pot_aspen_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ASPEN_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_ASPHODEL = registerPOT("large_pot_asphodel", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ASPHODEL));
	public static RegistryObject<Block> LARGE_FLOWER_POT_ATHELAS = registerPOT("large_pot_athelas", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ATHELAS));
	public static RegistryObject<Block> LARGE_FLOWER_POT_BEECH_SAPLING = registerPOT("large_pot_beech_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.BEECH_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_BLACKROOT = registerPOT("large_pot_blackroot", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.BLACKROOT));
	public static RegistryObject<Block> LARGE_FLOWER_POT_BLUEBELL = registerPOT("large_pot_bluebell", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.BLUEBELL));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CEDAR_SAPLING = registerPOT("large_pot_cedar_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CEDAR_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CHERRY_SAPLING = registerPOT("large_pot_cherry_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CHERRY_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CLOVER = registerPOT("large_pot_clover", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CLOVER));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CULUMALDA_SAPLING = registerPOT("large_pot_culumalda_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CULUMALDA_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CYPRESS_SAPLING = registerPOT("large_pot_cypress_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CYPRESS_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_DWARFWORT = registerPOT("large_pot_dwarfwort", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.DWARFWORT));
	public static RegistryObject<Block> LARGE_FLOWER_POT_ELANOR = registerPOT("large_pot_elanor", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ELANOR));
	public static RegistryObject<Block> LARGE_FLOWER_POT_FERNSPROUT = registerPOT("large_pot_fernsprout", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.FERNSPROUT));
	public static RegistryObject<Block> LARGE_FLOWER_POT_FIR_SAPLING = registerPOT("large_pot_fir_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.FIR_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_FOUR_CLOVER = registerPOT("large_pot_four_leaf_clover", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.FOUR_LEAF_CLOVER));
	public static RegistryObject<Block> LARGE_FLOWER_POT_GREEN_OAK_SAPLING = registerPOT("large_pot_green_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.GREEN_OAK_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_HARAD_DAISY = registerPOT("large_pot_harad_daisy", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.HARAD_DAISY));
	public static RegistryObject<Block> LARGE_FLOWER_POT_HOLLY_SAPLING = registerPOT("large_pot_holly_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.HOLLY_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_LAIRELOSSE_SAPLING = registerPOT("large_pot_lairelosse_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LAIRELOSSE_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_LARCH_SAPLING = registerPOT("large_pot_larch_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LARCH_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_LAVENDER = registerPOT("large_pot_lavender", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LAVENDER));
	public static RegistryObject<Block> LARGE_FLOWER_POT_LEBETHRON_SAPLING = registerPOT("large_pot_lebethron_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LEBETHRON_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MALLORN_SAPLING = registerPOT("large_pot_mallorn_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MALLORN_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MALLOS = registerPOT("large_pot_mallos", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MALLOS));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MAPLE_SAPLING = registerPOT("large_pot_maple_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MAPLE_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MARIGOLD = registerPOT("large_pot_marigold", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MARIGOLD));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MIRK_OAK_SAPLING = registerPOT("large_pot_mirk_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MIRK_OAK_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MIRK_SHROOM = registerPOT("large_pot_mirk_shroom", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MIRK_SHROOM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MORDOR_THORN = registerPOT("large_pot_mordor_thorn", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MORDOR_THORN));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MORGUL_FLOWER = registerPOT("large_pot_morgul_flower", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MORGUL_FLOWER));
	public static RegistryObject<Block> LARGE_FLOWER_POT_MORGUL_SHROOM = registerPOT("large_pot_morgul_shroom", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MORGUL_SHROOM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_NETTLES = registerPOT("large_pot_nettles", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.NETTLES));
	public static RegistryObject<Block> LARGE_FLOWER_POT_NIPHREDIL = registerPOT("large_pot_niphredil", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.NIPHREDIL));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CHRYSANTHEMUM = registerPOT("large_pot_orange_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ORANGE_CHRYSANTHEMUM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_PEAR_SAPLING = registerPOT("large_pot_pear_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.PEAR_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_PINE_SAPLING = registerPOT("large_pot_pine_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.PINE_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_PINK_CHRYSANTHEMUM = registerPOT("large_pot_pink_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.PINK_CHRYSANTHEMUM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_RED_CHRYSANTHEMUM = registerPOT("large_pot_red_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.RED_CHRYSANTHEMUM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_RED_OAK_SAPLING = registerPOT("large_pot_red_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.RED_OAK_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_RED_SAND_GEM = registerPOT("large_pot_red_sand_gem", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.RED_SAND_GEM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_SHIRE_HEATHER = registerPOT("large_pot_shire_heather", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.SHIRE_HEATHER));
	public static RegistryObject<Block> LARGE_FLOWER_POT_SIMBELMYNE = registerPOT("large_pot_simbelmyne", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.SIMBELMYNE));
	public static RegistryObject<Block> LARGE_FLOWER_POT_SOUTHBELL = registerPOT("large_pot_southbell", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.SOUTHBELL));
	public static RegistryObject<Block> LARGE_FLOWER_POT_THISTLE = registerPOT("large_pot_thistle", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.THISTLE));
	public static RegistryObject<Block> LARGE_FLOWER_POT_WHITE_CHRYSANTHEMUM = registerPOT("large_pot_white_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.WHITE_CHRYSANTHEMUM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_WILD_FLAX= registerPOT("large_pot_wild_flax", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.WILD_FLAX));
	public static RegistryObject<Block> LARGE_FLOWER_POT_WILD_PIPEWEED = registerPOT("large_pot_wild_pipeweed", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.WILD_PIPEWEED));
	public static RegistryObject<Block> LARGE_FLOWER_POT_YELLOW_CHRYSANTHEMUM = registerPOT("large_pot_yellow_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.YELLOW_CHRYSANTHEMUM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_YELLOW_SAND_GEM = registerPOT("large_pot_yellow_sand_gem", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.YELLOW_SAND_GEM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_ACACIA_SAPLING = registerPOT("large_pot_acacia_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.ACACIA_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_ALLIUM = registerPOT("large_pot_allium", () -> new ExtendedLargeFlowerPotBlock(Blocks.ALLIUM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_AZURE_BLUET = registerPOT("large_pot_azure_bluet", () -> new ExtendedLargeFlowerPotBlock(Blocks.AZURE_BLUET));
	public static RegistryObject<Block> LARGE_FLOWER_POT_BAMBOO = registerPOT("large_pot_bamboo", () -> new ExtendedLargeFlowerPotBlock(Blocks.BAMBOO));
	public static RegistryObject<Block> LARGE_FLOWER_POT_BIRCH_SAPLING = registerPOT("large_pot_birch_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.BIRCH_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_BLUE_ORCHID = registerPOT("large_pot_blue_orchid", () -> new ExtendedLargeFlowerPotBlock(Blocks.BLUE_ORCHID));
	public static RegistryObject<Block> LARGE_FLOWER_POT_BROWN_MUSHROOM = registerPOT("large_pot_brown_mushroom", () -> new ExtendedLargeFlowerPotBlock(Blocks.BROWN_MUSHROOM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CACTUS = registerPOT("large_pot_cactus", () -> new ExtendedLargeFlowerPotBlock(Blocks.CACTUS));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CORNFLOWER = registerPOT("large_pot_cornflower", () -> new ExtendedLargeFlowerPotBlock(Blocks.CORNFLOWER));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CRIMSON_FUNGUS = registerPOT("large_pot_crimson_fungus", () -> new ExtendedLargeFlowerPotBlock(Blocks.CRIMSON_FUNGUS));
	public static RegistryObject<Block> LARGE_FLOWER_POT_CRIMSON_ROOTS = registerPOT("large_pot_crimson_roots", () -> new ExtendedLargeFlowerPotBlock(Blocks.CRIMSON_ROOTS));
	public static RegistryObject<Block> LARGE_FLOWER_POT_DANDELION = registerPOT("large_pot_dandelion", () -> new ExtendedLargeFlowerPotBlock(Blocks.DANDELION));
	public static RegistryObject<Block> LARGE_FLOWER_POT_DARK_OAK_SAPLING = registerPOT("large_pot_dark_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.DARK_OAK_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_DEAD_BUSH = registerPOT("large_pot_dead_bush", () -> new ExtendedLargeFlowerPotBlock(Blocks.DEAD_BUSH));
	public static RegistryObject<Block> LARGE_FLOWER_POT_FERN = registerPOT("large_pot_fern", () -> new ExtendedLargeFlowerPotBlock(Blocks.FERN));
	public static RegistryObject<Block> LARGE_FLOWER_POT_JUNGLE_SAPLING = registerPOT("large_pot_jungle_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.JUNGLE_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_LILY_OF_THE_VALLY = registerPOT("large_pot_lily_of_the_valley", () -> new ExtendedLargeFlowerPotBlock(Blocks.LILY_OF_THE_VALLEY));
	public static RegistryObject<Block> LARGE_FLOWER_POT_OAK_SAPLING = registerPOT("large_pot_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.OAK_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_ORANGE_TULIP = registerPOT("large_pot_orange_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.ORANGE_TULIP));
	public static RegistryObject<Block> LARGE_FLOWER_POT_OXEYE_DAISY = registerPOT("large_pot_oxeye_daisy", () -> new ExtendedLargeFlowerPotBlock(Blocks.OXEYE_DAISY));
	public static RegistryObject<Block> LARGE_FLOWER_POT_PINK_TULIP = registerPOT("large_pot_pink_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.PINK_TULIP));
	public static RegistryObject<Block> LARGE_FLOWER_POT_POPPY = registerPOT("large_pot_poppy", () -> new ExtendedLargeFlowerPotBlock(Blocks.POPPY));
	public static RegistryObject<Block> LARGE_FLOWER_POT_RED_MUSHROOM = registerPOT("large_pot_red_mushroom", () -> new ExtendedLargeFlowerPotBlock(Blocks.RED_MUSHROOM));
	public static RegistryObject<Block> LARGE_FLOWER_POT_RED_TULIP = registerPOT("large_pot_red_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.RED_TULIP));
	public static RegistryObject<Block> LARGE_FLOWER_POT_SPRUCE_SAPLING = registerPOT("large_pot_spruce_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.SPRUCE_SAPLING));
	public static RegistryObject<Block> LARGE_FLOWER_POT_WARPED_FUNGUS = registerPOT("large_pot_warped_fungus", () -> new ExtendedLargeFlowerPotBlock(Blocks.WARPED_FUNGUS));
	public static RegistryObject<Block> LARGE_FLOWER_POT_WARPED_ROOTS = registerPOT("large_pot_warped_roots", () -> new ExtendedLargeFlowerPotBlock(Blocks.WARPED_ROOTS));
	public static RegistryObject<Block> LARGE_FLOWER_POT_WHITE_TULIP = registerPOT("large_pot_white_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.WHITE_TULIP));
	public static RegistryObject<Block> LARGE_FLOWER_POT_WITHER_ROSE = registerPOT("large_pot_wither_rose", () -> new ExtendedLargeFlowerPotBlock(Blocks.WITHER_ROSE));

	//Banners
	public static RegistryObject<Block> DUNEDAIN_BANNER = register("dunedain_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DUNEDAIN));
	public static RegistryObject<Block> DUNEDAIN_WALL_BANNER = register("dunedain_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DUNEDAIN));
	public static RegistryObject<Block> HOBBIT_BANNER = register("hobbit_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.SHIRE));
	public static RegistryObject<Block> HOBBIT_WALL_BANNER = register("hobbit_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.SHIRE));
	public static RegistryObject<Block> DURINS_FOLK_BANNER = register("durins_folk_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DURINSFOLK));
	public static RegistryObject<Block> DURINS_FOLK_WALL_BANNER = register("durins_folk_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DURINSFOLK));
	public static RegistryObject<Block> BLUE_MOUNTAINS_BANNER = register("blue_mountains_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.BLUEMOUNTIANS));
	public static RegistryObject<Block> BLUE_MOUNTAINS_WALL_BANNER = register("blue_mountains_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.BLUEMOUNTIANS));
	public static RegistryObject<Block> GUNDABAD_ORC_BANNER = register("gundabad_orc_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.GUNDABAD_ORC));
	public static RegistryObject<Block> GUNDABAD_ORC_WALL_BANNER = register("gundabad_orc_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.GUNDABAD_ORC));
	public static RegistryObject<Block> BREE_BANNER = register("bree_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.BREE));
	public static RegistryObject<Block> BREE_WALL_BANNER = register("bree_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.BREE));
	public static RegistryObject<Block> ANGMAR_BANNER = register("angmar_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.ANGMAR));
	public static RegistryObject<Block> ANGMAR_WALL_BANNER = register("angmar_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.ANGMAR));
	public static RegistryObject<Block> DALE_BANNER = register("dale_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DALE));
	public static RegistryObject<Block> DALE_WALL_BANNER = register("dale_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DALE));
	public static RegistryObject<Block> DOL_AMROTH_BANNER = register("dol_amroth_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DOL_AMROTH));
	public static RegistryObject<Block> DOL_AMROTH_WALL_BANNER = register("dol_amroth_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DOL_AMROTH));
	public static RegistryObject<Block> DOL_GULDUR_BANNER = register("dol_guldur_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DOL_GULDUR));
	public static RegistryObject<Block> DOL_GULDUR_WALL_BANNER = register("dol_guldur_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DOL_GULDUR));
	public static RegistryObject<Block> DORWINION_BANNER = register("dorwinion_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DORWINION));
	public static RegistryObject<Block> DORWINION_WALL_BANNER = register("dorwinion_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DORWINION));
	public static RegistryObject<Block> DUNLAND_BANNER = register("dunland_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DUNLAND));
	public static RegistryObject<Block> DUNLAND_WALL_BANNER = register("dunland_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DUNLAND));
	public static RegistryObject<Block> GONDOR_BANNER = register("gondor_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.GONDOR));
	public static RegistryObject<Block> GONDOR_WALL_BANNER = register("gondor_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.GONDOR));
	public static RegistryObject<Block> ISENGARD_BANNER = register("isengard_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.ISENGARD));
	public static RegistryObject<Block> ISENGARD_WALL_BANNER = register("isengard_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.ISENGARD));
	public static RegistryObject<Block> LINDON_BANNER = register("lindon_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.LINDON));
	public static RegistryObject<Block> LINDON_WALL_BANNER = register("lindon_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.LINDON));
	public static RegistryObject<Block> LOTHLORIEN_BANNER = register("lothlorien_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.LOTHLORIEN));
	public static RegistryObject<Block> LOTHLORIEN_WALL_BANNER = register("lothlorien_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.LOTHLORIEN));
	public static RegistryObject<Block> MORDOR_BANNER = register("mordor_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.MORDOR));
	public static RegistryObject<Block> MORDOR_WALL_BANNER = register("mordor_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.MORDOR));
	public static RegistryObject<Block> RIVENDELL_BANNER = register("rivendell_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.RIVENDELL));
	public static RegistryObject<Block> RIVENDELL_WALL_BANNER = register("rivendell_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.RIVENDELL));
	public static RegistryObject<Block> ROHAN_BANNER = register("rohan_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.ROHAN));
	public static RegistryObject<Block> ROHAN_WALL_BANNER = register("rohan_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.ROHAN));
	public static RegistryObject<Block> WOODLAND_REALM_BANNER = register("woodland_realm_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.WOODLAND_REALM));
	public static RegistryObject<Block> WOODLAND_REALM_WALL_BANNER = register("woodland_realm_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.WOODLAND_REALM));

	public static RegistryObject<Block> DEAD_MOSS_VINES = register("dead_moss_vines", () -> new ExtendedVineBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE)));
	// Birdcages
	public static RegistryObject<Block> WOODEN_BIRDCAGE = register("wooden_birdcage", () -> new ExtendedBirdcageBlock(Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).strength(0.5F)));
	public static RegistryObject<Block> BRONZE_BIRDCAGE = register("bronze_birdcage", () -> new ExtendedBirdcageBlock(Properties.of(Material.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(1.0F)));
	public static RegistryObject<Block> IRON_BIRDCAGE = register("iron_birdcage", () -> new ExtendedBirdcageBlock(Properties.of(Material.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(2.0F)));
	public static RegistryObject<Block> SILVER_BIRDCAGE = register("silver_birdcage", () -> new ExtendedBirdcageBlock(Properties.of(Material.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(3.0F)));
	public static RegistryObject<Block> GOLDEN_BIRDCAGE = register("golden_birdcage", () -> new ExtendedBirdcageBlock(Properties.of(Material.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(4.0F)));
	public static RegistryObject<Block> MITHRIL_BIRDCAGE = register("mithril_birdcage", () -> new ExtendedBirdcageBlock(Properties.of(Material.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(5.0f)));

	public static RegistryObject<Block> GRASS_VOID = register("grass_void", () -> new ExtendedGrassVoidBlock(Properties.of(Material.STRUCTURAL_AIR).noCollission().noDrops().isValidSpawn(ExtendedBlockParms::always)));

	public static RegistryObject<Block> DWARVEN_DOOR = register("dwarven_door", () -> new ExtendedDwarvenGateBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 10.0F).sound(SoundType.STONE).noOcclusion(), false));
	public static RegistryObject<Block> DWARVEN_DOOR_LUIGON = register("blue_dwarven_door", () -> new ExtendedDwarvenGateBlock(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 10.0F).sound(SoundType.STONE).noOcclusion(), false));
	public static RegistryObject<Block> DWARVEN_DOOR_CARGON = register("red_dwarven_door", () -> new ExtendedDwarvenGateBlock(Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 10.0F).sound(SoundType.STONE).noOcclusion(), false));

	public static RegistryObject<Block> EDHELVIR_CRYSTAL_CHANDELIER = register("edhelvir_crystal_chandelier", () -> new ExtendedCrystalChandelierBlock(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).lightLevel((s) -> 20).sound(SoundType.STONE).noOcclusion()));
	public static RegistryObject<Block> GLOWSTONE_CRYSTAL_CHANDELIER = register("glowstone_crystal_chandelier", () -> new ExtendedCrystalChandelierBlock(Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).lightLevel((s) -> 20).sound(SoundType.STONE).noOcclusion()));
	public static RegistryObject<Block> GULDURIL_CRYSTAL_CHANDELIER = register("gulduril_crystal_chandelier", () -> new ExtendedCrystalChandelierBlock(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GREEN).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).lightLevel((s) -> 20).sound(SoundType.STONE).noOcclusion()));

	public static RegistryObject<Block> STONE_SIGN = register("stone_engraving", () -> new ExtendedStoneTextSignBlock(Properties.of(Material.DECORATION, MaterialColor.NONE).noCollission().strength(0.5F).sound(SoundType.STONE)));
	public static RegistryObject<Block> HOIST = register("mine_placeholder", () -> new ExtendedHoistBlock(Properties.of(Material.STONE).strength(4.0F, 10.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));

	public static RegistryObject<Block> HIGH_ELVEN_CHAIN = register("high_elven_chain", () -> new ExtendedChainBlock(Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion()));
	public static RegistryObject<Block> SHARDS_OF_NARSIL = register("shards_of_narsil", () -> new ExtendedNarsilShardsBlock(Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.5F).noOcclusion()));

	public static RegistryObject<Block> PLACED_BOOKS = register("placed_books", () -> new ExtendedPlacedBooksBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.5F).noOcclusion()));
	public static RegistryObject<Block> EXTENDED_BOOKSHELF = register("bookshelf", () -> new ExtendedBookshelfBlock(Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD)));

	public static RegistryObject<Block> ORC_BOMB = register("orc_bomb", () -> new ExtendedOrcBombBlock(Properties.of(Material.EXPLOSIVE).sound(SoundType.METAL).strength(3F, 0.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE), 0, false));
	public static RegistryObject<Block> ORC_BOMB_DOUBLE = register("orc_bomb_double", () -> new ExtendedOrcBombBlock(Properties.copy(ORC_BOMB.get()), 1, false));
	public static RegistryObject<Block> ORC_BOMB_TRIPLE = register("orc_bomb_triple", () -> new ExtendedOrcBombBlock(Properties.copy(ORC_BOMB.get()), 2, false));
	public static RegistryObject<Block> ORC_BOMB_FIRE = register("orc_bomb_fire", () -> new ExtendedOrcBombBlock(Properties.copy(ORC_BOMB.get()), 0, true));
	public static RegistryObject<Block> ORC_BOMB_FIRE_DOUBLE = register("orc_bomb_double_fire", () -> new ExtendedOrcBombBlock(Properties.copy(ORC_BOMB.get()), 1, true));
	public static RegistryObject<Block> ORC_BOMB_FIRE_TRIPLE = register("orc_bomb_triple_fire", () -> new ExtendedOrcBombBlock(Properties.copy(ORC_BOMB.get()), 2, true));

	public static RegistryObject<Block> NUMENOREAN_BARS = register("numenorean_bars", () -> new ExtendedBarsBlock(Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));

	public static RegistryObject<Block> LEEKS = register("leeks", () -> new ExtendedLeekBlock(Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.CROP)));

	public static RegistryObject<Block> SUNFRUIT = register("sunfruit", () -> new ExtendedFangornFlowerBlock(Effects.FIRE_RESISTANCE, 7, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> SPRINGLEAF = register("springleaf", () -> new ExtendedFangornFlowerBlock(Effects.JUMP, 7, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> RIVERWEED = register("riverweed", () -> new ExtendedFangornRiverWeedBlock(Effects.WATER_BREATHING, 15, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> TEARS_OF_YAVANNA = register("tears_of_yavanna", () -> new ExtendedFangornFlowerBlock(Effects.MOVEMENT_SPEED, 6, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> ELFSONG = register("elfsong", () -> new ExtendedFangornFlowerBlock(Effects.REGENERATION, 7, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> MOONFLOWER = register("moonflower", () -> new ExtendedFangornFlowerBlock(Effects.NIGHT_VISION, 9, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> FANGORNS_BEARD = register("fangorns_beard", () -> new ExtendedFangornFlowerBlock(Effects.SATURATION, 6, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> HOURN_LEAF = register("huorn_leaf", () -> new ExtendedFangornHournLeafBlock(Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
	public static RegistryObject<Block> STONEROOT = register("stone_root", () -> new ExtendedFangornStoneRootBlock(Effects.DAMAGE_RESISTANCE, 12, Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));

	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
		bus.addGenericListener(Block.class, ExtendedSignTileEntitySetup::setup);
		bus.addGenericListener(Block.class, ExtendedBiomeFeatures::setup);
	}

	private static RegistryObject<Block> register(String name, Supplier<? extends Block> sup) {
		return BLOCKS.register(name, sup);
	}

	private static RegistryObject<Block> registerPOT(String name, Supplier<? extends Block> sup) {
		RegistryObject<Block> block = register(name, sup);
		REGISTEREDPOTS.put(name, block);
		return block;
	}

	private static VoxelShape createVesselShape(float width, float height) {
		float halfWidth = width / 2.0F;
		return Block.box(8.0D - halfWidth, 0.0D, 8.0D - halfWidth, 8.0D + halfWidth, height, 8.0D + halfWidth);
	}
}
