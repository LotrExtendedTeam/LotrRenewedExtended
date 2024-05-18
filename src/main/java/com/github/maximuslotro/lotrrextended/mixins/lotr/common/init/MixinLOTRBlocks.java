package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;


import lotr.common.init.ExtendedItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryPlantBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCakeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCheeseWheelBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGreenFireBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundDirtBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundFarmlandBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundGrassBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundPathBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGrowableAxialSlabBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedReedBasketBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBlackFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedGrayFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedOrcBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedSilverFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedStrawBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedWhiteFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedGroupSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedSingleChestBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCheeseState;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnGroupTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedDoubleTorchBlock;
import lotr.common.block.ExtendedFactionCraftingTableBlock;
import lotr.common.block.ExtendedGreenHearthBlock;
import lotr.common.block.ExtendedLOTRTorchBlock;
import lotr.common.block.ExtendedLOTRWallTorchBlock;
import lotr.common.block.ExtendedLanternBlock;
import lotr.common.block.ExtendedPackedBundleBlock;
import lotr.common.block.ExtendedRemainsBlock;
import lotr.common.block.ExtendedSignTypes;
import lotr.common.block.ExtendedStoneBlock;
import lotr.common.block.ExtendedTwoTypeDummyBlock;
import lotr.common.block.FallenLeavesBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRFenceBlock;
import lotr.common.block.LOTRFenceGateBlock;
import lotr.common.block.LOTRLeavesBlock;
import lotr.common.block.LOTROreBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRPlanksBlock;
import lotr.common.block.LOTRPottedPlantBlock;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStandingSignBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRStrippedLogBlock;
import lotr.common.block.LOTRStrippedWoodBlock;
import lotr.common.block.LOTRTrapdoorBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallSignBlock;
import lotr.common.block.LOTRWoodButtonBlock;
import lotr.common.block.LOTRWoodPressurePlateBlock;
import lotr.common.block.LogSlabBlock;
import lotr.common.block.LogStairsBlock;
import lotr.common.block.MineralBlock;
import lotr.common.block.StrippableBranchBlock;
import lotr.common.block.StrippableLogBlock;
import lotr.common.block.StrippableLogSlabBlock;
import lotr.common.block.StrippableLogStairsBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.WoodBeamBlock;
import lotr.common.block.trees.ChestnutTree;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRParticles;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mixin(LOTRBlocks.class)
public class MixinLOTRBlocks {

	@Final
	@Shadow(remap=false)
	public static DeferredRegister<Block> BLOCKS;

	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> BLUE_MALLORN_TORCH;

	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> GREEN_MALLORN_TORCH;

	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> GOLD_MALLORN_TORCH;

	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> SILVER_MALLORN_TORCH;

	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> HIGH_ELVEN_TORCH;

	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> DWARVEN_TORCH;

	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> BLUBBER_TORCH;
	
	@SuppressWarnings("unchecked")
	@Redirect(method = "<clinit>", 
			at = @At(value = "INVOKE", 
			target = "Lnet/minecraftforge/registries/DeferredRegister;register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/minecraftforge/fml/RegistryObject;"),remap = false)
	private static RegistryObject<Block> injectedBlockRegister(DeferredRegister<Block> register, String name, Supplier<? extends Block> sup) {
		RegistryObject<Block> returnBlock = null;
		if(name.equals("blue_mallorn_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return (new ExtendedLOTRTorchBlock(14)).setParticles(new Supplier[]{LOTRParticles.BLUE_ELVEN_GLOW});});
		}else if(name.equals("blue_mallorn_wall_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRWallTorchBlock(BLUE_MALLORN_TORCH);});
		}else if(name.equals("green_mallorn_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return (new ExtendedLOTRTorchBlock(14)).setParticles(new Supplier[]{LOTRParticles.GREEN_ELVEN_GLOW});});
		}else if(name.equals("green_mallorn_wall_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRWallTorchBlock(GREEN_MALLORN_TORCH);});
		}else if(name.equals("gold_mallorn_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return (new ExtendedLOTRTorchBlock(14)).setParticles(new Supplier[]{LOTRParticles.GOLD_ELVEN_GLOW});});
		}else if(name.equals("gold_mallorn_wall_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRWallTorchBlock(GOLD_MALLORN_TORCH);});
		}else if(name.equals("silver_mallorn_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return (new ExtendedLOTRTorchBlock(14)).setParticles(new Supplier[]{LOTRParticles.SILVER_ELVEN_GLOW});});
		}else if(name.equals("silver_mallorn_wall_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRWallTorchBlock(SILVER_MALLORN_TORCH);});
		}else if(name.equals("high_elven_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return (new ExtendedLOTRTorchBlock(14)).setParticles(new Supplier[]{LOTRParticles.BLUE_ELVEN_GLOW});});
		}else if(name.equals("high_elven_wall_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRWallTorchBlock(HIGH_ELVEN_TORCH);});
		}else if(name.equals("dwarven_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRTorchBlock(14);});
		}else if(name.equals("dwarven_wall_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRWallTorchBlock(DWARVEN_TORCH);});
		}else if(name.equals("blubber_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRTorchBlock(12);});
		}else if(name.equals("blubber_wall_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedLOTRWallTorchBlock(BLUBBER_TORCH);});
		}else if(name.equals("orc_torch")) {
			returnBlock = BLOCKS.register(name, () -> {
				return new ExtendedDoubleTorchBlock(14);});
		}
		if(returnBlock==null) {
			returnBlock = BLOCKS.register(name, sup);
		}
		return returnBlock;
	}

	private static RegistryObject<Block> registerB(String name, Supplier<? extends Block> sup) {
		RegistryObject<Block> block = BLOCKS.register(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedBlocks.REGISTEREDBLOCKS.put(name, block);
		}
		return block;
    }

	@SuppressWarnings("unchecked")
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedBlocks(CallbackInfo ci) {
		//ORE STORAGE
		ExtendedBlocks.GALVORN_BLOCK = registerB("galvorn_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(4.0F, 8.0F).sound(SoundType.METAL), 2);});
		ExtendedBlocks.BLACK_URUK_STEEL_BLOCK = registerB("black_uruk_steel_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
		ExtendedBlocks.GILDED_IRON_BLOCK = registerB("gilded_iron_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
		ExtendedBlocks.MORGUL_STEEL_BLOCK = registerB("morgul_steel_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});

		//Wood Types
		ExtendedBlocks.CHESTNUT_LOG = registerB("chestnut_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL, () -> ExtendedBlocks.STRIPPED_CHESTNUT_LOG.get()));
		ExtendedBlocks.CHESTNUT_WOOD = registerB("chestnut_wood", () -> new StrippableWoodBlock(ExtendedBlocks.CHESTNUT_LOG, () -> ExtendedBlocks.STRIPPED_CHESTNUT_WOOD.get()));
		ExtendedBlocks.CHESTNUT_PLANKS = registerB("chestnut_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
		ExtendedBlocks.CHESTNUT_LEAVES = registerB("chestnut_leaves", LOTRLeavesBlock::new);
		ExtendedBlocks.CHESTNUT_LEAVES_FRUIT = registerB("chestnut_leaves_fruit", LOTRLeavesBlock::new);
		ExtendedBlocks.CHESTNUT_SAPLING = registerB("chestnut_sapling", () -> new LOTRSaplingBlock(new ChestnutTree()));
		ExtendedBlocks.POTTED_CHESTNUT_SAPLING = registerB("potted_chestnut_sapling", () -> new LOTRPottedPlantBlock(ExtendedBlocks.CHESTNUT_SAPLING));
		ExtendedBlocks.CHESTNUT_SLAB = registerB("chestnut_slab", () -> new AxialSlabBlock(ExtendedBlocks.CHESTNUT_PLANKS));
		ExtendedBlocks.CHESTNUT_STAIRS = registerB("chestnut_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CHESTNUT_PLANKS));
		ExtendedBlocks.CHESTNUT_FENCE = registerB("chestnut_fence", () -> new LOTRFenceBlock(ExtendedBlocks.CHESTNUT_PLANKS));
		ExtendedBlocks.CHESTNUT_FENCE_GATE = registerB("chestnut_fence_gate", () -> new LOTRFenceGateBlock(ExtendedBlocks.CHESTNUT_PLANKS));
		ExtendedBlocks.CHESTNUT_DOOR = registerB("chestnut_door", () -> new LOTRDoorBlock(ExtendedBlocks.CHESTNUT_PLANKS));
		ExtendedBlocks.CHESTNUT_TRAPDOOR = registerB("chestnut_trapdoor", () -> new LOTRTrapdoorBlock(ExtendedBlocks.CHESTNUT_PLANKS));
		ExtendedBlocks.CHESTNUT_PRESSURE_PLATE = registerB("chestnut_pressure_plate", () -> new LOTRWoodPressurePlateBlock(ExtendedBlocks.CHESTNUT_PLANKS));
		ExtendedBlocks.CHESTNUT_BUTTON = registerB("chestnut_button", LOTRWoodButtonBlock::new);
		ExtendedBlocks.STRIPPED_CHESTNUT_LOG = registerB("stripped_chestnut_log", () -> new LOTRStrippedLogBlock(ExtendedBlocks.CHESTNUT_LOG));
		ExtendedBlocks.STRIPPED_CHESTNUT_WOOD = registerB("stripped_chestnut_wood", () -> new LOTRStrippedWoodBlock(ExtendedBlocks.STRIPPED_CHESTNUT_LOG));
		ExtendedBlocks.CHESTNUT_BEAM = registerB("chestnut_beam", () -> new WoodBeamBlock(ExtendedBlocks.CHESTNUT_LOG));
		ExtendedBlocks.CHESTNUT_SIGN = registerB("chestnut_sign", () -> new LOTRStandingSignBlock(ExtendedBlocks.CHESTNUT_PLANKS, ExtendedSignTypes.CHESTNUT));
		ExtendedBlocks.CHESTNUT_WALL_SIGN = registerB("chestnut_wall_sign", () -> new LOTRWallSignBlock(ExtendedBlocks.CHESTNUT_SIGN));
		ExtendedBlocks.CHESTNUT_LOG_SLAB = registerB("chestnut_log_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.CHESTNUT_LOG, () -> ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB.get()));
		ExtendedBlocks.CHESTNUT_WOOD_SLAB = registerB("chestnut_wood_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.CHESTNUT_WOOD, () -> ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB.get()));
		ExtendedBlocks.CHESTNUT_WOOD_STAIRS = registerB("chestnut_wood_stairs", () -> new StrippableLogStairsBlock(ExtendedBlocks.CHESTNUT_WOOD, () -> ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS.get()));
		ExtendedBlocks.CHESTNUT_BRANCH = registerB("chestnut_branch", () -> new StrippableBranchBlock(ExtendedBlocks.CHESTNUT_WOOD, () -> ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH.get()));
		ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB = registerB("stripped_chestnut_log_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_CHESTNUT_LOG));
		ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB = registerB("stripped_chestnut_wood_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD));
		ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS = registerB("stripped_chestnut_wood_stairs", () -> new LogStairsBlock(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD));
		ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH = registerB("stripped_chestnut_branch", () -> new BranchBlock(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD));
		ExtendedBlocks.CHESTNUT_BEAM_SLAB = registerB("chestnut_beam_slab", () -> new LogSlabBlock(ExtendedBlocks.CHESTNUT_BEAM));
		ExtendedBlocks.FALLEN_CHESTNUT_LEAVES = registerB("fallen_chestnut_leaves", () -> new FallenLeavesBlock(ExtendedBlocks.CHESTNUT_LEAVES));
		
		ExtendedBlocks.BANANA_LOG = registerB("banana_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL, () -> ExtendedBlocks.STRIPPED_BANANA_LOG.get()));
		//ExtendedBlocks.BANANA_WOOD = registerB("banana_wood", () -> new StrippableWoodBlock(ExtendedBlocks.BANANA_LOG, () -> ExtendedBlocks.STRIPPED_BANANA_WOOD.get()));
		ExtendedBlocks.BANANA_PLANKS = registerB("banana_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
		ExtendedBlocks.BANANA_LEAVES = registerB("banana_leaves", LOTRLeavesBlock::new);
		ExtendedBlocks.BANANA_SAPLING = registerB("banana_sapling", () -> new LOTRSaplingBlock(new ChestnutTree()));
		ExtendedBlocks.POTTED_BANANA_SAPLING = registerB("potted_banana_sapling", () -> new LOTRPottedPlantBlock(ExtendedBlocks.BANANA_SAPLING));
		ExtendedBlocks.BANANA_SLAB = registerB("banana_slab", () -> new AxialSlabBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_STAIRS = registerB("banana_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_FENCE = registerB("banana_fence", () -> new LOTRFenceBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_FENCE_GATE = registerB("banana_fence_gate", () -> new LOTRFenceGateBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_DOOR = registerB("banana_door", () -> new LOTRDoorBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_TRAPDOOR = registerB("banana_trapdoor", () -> new LOTRTrapdoorBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_PRESSURE_PLATE = registerB("banana_pressure_plate", () -> new LOTRWoodPressurePlateBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_BUTTON = registerB("banana_button", LOTRWoodButtonBlock::new);
		ExtendedBlocks.STRIPPED_BANANA_LOG = registerB("stripped_banana_log", () -> new LOTRStrippedLogBlock(ExtendedBlocks.BANANA_LOG));
		//ExtendedBlocks.STRIPPED_BANANA_WOOD = registerB("stripped_banana_wood", () -> new LOTRStrippedWoodBlock(ExtendedBlocks.STRIPPED_BANANA_LOG));
		ExtendedBlocks.BANANA_BEAM = registerB("banana_beam", () -> new WoodBeamBlock(ExtendedBlocks.BANANA_LOG));
		ExtendedBlocks.BANANA_SIGN = registerB("banana_sign", () -> new LOTRStandingSignBlock(ExtendedBlocks.BANANA_PLANKS, ExtendedSignTypes.BANANA));
		ExtendedBlocks.BANANA_WALL_SIGN = registerB("banana_wall_sign", () -> new LOTRWallSignBlock(ExtendedBlocks.BANANA_SIGN));
		ExtendedBlocks.BANANA_LOG_SLAB = registerB("banana_log_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.BANANA_LOG, () -> ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB.get()));
		//ExtendedBlocks.BANANA_WOOD_SLAB = registerB("banana_wood_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.BANANA_WOOD, () -> ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB.get()));
		//ExtendedBlocks.BANANA_WOOD_STAIRS = registerB("banana_wood_stairs", () -> new StrippableLogStairsBlock(ExtendedBlocks.BANANA_WOOD, () -> ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS.get()));
		ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB = registerB("stripped_banana_log_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_BANANA_LOG));
		//ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB = registerB("stripped_banana_wood_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_BANANA_WOOD));
		//ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS = registerB("stripped_banana_wood_stairs", () -> new LogStairsBlock(ExtendedBlocks.STRIPPED_BANANA_WOOD));
		ExtendedBlocks.BANANA_BEAM_SLAB = registerB("banana_beam_slab", () -> new LogSlabBlock(ExtendedBlocks.BANANA_BEAM));
		ExtendedBlocks.FALLEN_BANANA_LEAVES = registerB("fallen_banana_leaves", () -> new FallenLeavesBlock(ExtendedBlocks.BANANA_LEAVES));

		//Packed Fur
		ExtendedBlocks.WHITE_PACKED_FUR = registerB("white_packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.WOOL));
		ExtendedBlocks.WHITE_PACKED_FUR_SLAB = registerB("white_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		ExtendedBlocks.WHITE_PACKED_FUR_STAIRS = registerB("white_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		ExtendedBlocks.WHITE_PACKED_FUR_WALL = registerB("white_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		
		ExtendedBlocks.SILVER_PACKED_FUR = registerB("silver_packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.COLOR_LIGHT_GRAY));
		ExtendedBlocks.SILVER_PACKED_FUR_SLAB = registerB("silver_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		ExtendedBlocks.SILVER_PACKED_FUR_STAIRS = registerB("silver_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		ExtendedBlocks.SILVER_PACKED_FUR_WALL = registerB("silver_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		
		ExtendedBlocks.GRAY_PACKED_FUR = registerB("gray_packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.GRAY_PACKED_FUR_SLAB = registerB("gray_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.GRAY_PACKED_FUR));
		ExtendedBlocks.GRAY_PACKED_FUR_STAIRS = registerB("gray_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GRAY_PACKED_FUR));
		ExtendedBlocks.GRAY_PACKED_FUR_WALL = registerB("gray_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.GRAY_PACKED_FUR));
		
		ExtendedBlocks.BROWN_PACKED_FUR = registerB("packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.BROWN_PACKED_FUR_SLAB = registerB("packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.BROWN_PACKED_FUR));
		ExtendedBlocks.BROWN_PACKED_FUR_STAIRS = registerB("packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.BROWN_PACKED_FUR));
		ExtendedBlocks.BROWN_PACKED_FUR_WALL = registerB("packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.BROWN_PACKED_FUR));
		
		ExtendedBlocks.BLACK_PACKED_FUR = registerB("black_packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.BLACK_PACKED_FUR_SLAB = registerB("black_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.BLACK_PACKED_FUR));
		ExtendedBlocks.BLACK_PACKED_FUR_STAIRS = registerB("black_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.BLACK_PACKED_FUR));
		ExtendedBlocks.BLACK_PACKED_FUR_WALL = registerB("black_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.BLACK_PACKED_FUR));
		
		//Bundled Fur
		ExtendedBlocks.WHITE_FUR_BUNDLE = registerB("white_fur_bundle", () -> new ExtendedDirectionalBundleBlock(MaterialColor.WOOL));
		ExtendedBlocks.SILVER_FUR_BUNDLE = registerB("silver_fur_bundle", () -> new ExtendedDirectionalBundleBlock(MaterialColor.COLOR_LIGHT_GRAY));
		ExtendedBlocks.GRAY_FUR_BUNDLE = registerB("gray_fur_bundle", () -> new ExtendedDirectionalBundleBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.BLACK_FUR_BUNDLE = registerB("black_fur_bundle", () -> new ExtendedDirectionalBundleBlock(MaterialColor.COLOR_BLACK));

		//GEM STORAGE
		ExtendedBlocks.TOPAZ_BLOCK = registerB("topaz_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.AMETHYST_BLOCK = registerB("amethyst_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.SAPPHIRE_BLOCK = registerB("sapphire_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.RUBY_BLOCK = registerB("ruby_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_RED).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.AMBER_BLOCK = registerB("amber_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.DIAMOND_BLOCK = registerB("diamond_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.OPAL_BLOCK = registerB("opal_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.WOOL).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.EMERALD_BLOCK = registerB("emerald_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.CORAL_BLOCK = registerB("coral_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.PEARL_BLOCK = registerB("pearl_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.WOOL).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));

		//GEM ORES
		ExtendedBlocks.AMBER_ORE = registerB("amber_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		ExtendedBlocks.TOPAZ_ORE = registerB("topaz_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		ExtendedBlocks.AMETHYST_ORE = registerB("amethyst_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		ExtendedBlocks.SAPPHIRE_ORE = registerB("sapphire_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		ExtendedBlocks.RUBY_ORE = registerB("ruby_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		ExtendedBlocks.DIAMOND_ORE = registerB("diamond_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		ExtendedBlocks.OPAL_ORE = registerB("opal_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		ExtendedBlocks.EMERALD_ORE = registerB("emerald_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(1));
		//Remains ores
		ExtendedBlocks.REMAINS_ORC = registerB("remains_ore_orc", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));
		ExtendedBlocks.REMAINS_ELF = registerB("remains_ore_elf", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));
		ExtendedBlocks.REMAINS_MAN = registerB("remains_ore_man", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));
		ExtendedBlocks.REMAINS_DWARF = registerB("remains_ore_dwarf", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));

		///////////////////////////////BRICKS
		ExtendedBlocks.BARREN_DIRT = registerB("barren_dirt", () -> new Block(AbstractBlock.Properties.copy(Blocks.DIRT)));
		ExtendedBlocks.BARREN_DIRT_SLAB = registerB("barren_dirt_slab", () -> new AxialSlabBlock(ExtendedBlocks.BARREN_DIRT));
		ExtendedBlocks.COARSE_DIRT_SLAB = registerB("coarse_dirt_slab", () -> new AxialSlabBlock(Blocks.COARSE_DIRT));
		ExtendedBlocks.DIRT_SLAB = registerB("dirt_slab", () -> new AxialSlabBlock(Blocks.DIRT));
		ExtendedBlocks.GRASS_BLOCK_SLAB = registerB("grass_block_slab", () -> new ExtendedGrowableAxialSlabBlock(Blocks.GRASS_BLOCK, ExtendedBlocks.DIRT_SLAB, () -> Blocks.DIRT, () -> Blocks.GRASS_BLOCK));
		ExtendedBlocks.GRASS_PATH_SLAB = registerB("grass_path_slab", () -> new AxialSlabBlock(Blocks.GRASS_PATH));
		ExtendedBlocks.PODZOL_SLAB = registerB("podzol_slab", () -> new AxialSlabBlock(Blocks.PODZOL));
		ExtendedBlocks.MYCELIUM_SLAB = registerB("mycelium_slab", () -> new ExtendedGrowableAxialSlabBlock(Blocks.MYCELIUM, ExtendedBlocks.DIRT_SLAB, () -> Blocks.DIRT, () -> Blocks.MYCELIUM));
		//Scorched Stuff
		ExtendedBlocks.SCORCHED_STONE = registerB("scorched_stone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
		ExtendedBlocks.SCORCHED_STONE_SLAB = registerB("scorched_stone_slab", () -> new AxialSlabBlock(ExtendedBlocks.SCORCHED_STONE));
		ExtendedBlocks.SCORCHED_STONE_STAIRS = registerB("scorched_stone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SCORCHED_STONE));
		ExtendedBlocks.SCORCHED_STONE_WALL = registerB("scorched_stone_wall", () -> new LOTRWallBlock(ExtendedBlocks.SCORCHED_STONE));
		ExtendedBlocks.SCORCHED_COBBLESTONE = registerB("scorched_cobblestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
		ExtendedBlocks.SCORCHED_COBBLESTONE_SLAB = registerB("scorched_cobblestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.SCORCHED_COBBLESTONE));
		ExtendedBlocks.SCORCHED_COBBLESTONE_STAIRS = registerB("scorched_cobblestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SCORCHED_COBBLESTONE));
		ExtendedBlocks.SCORCHED_COBBLESTONE_WALL = registerB("scorched_cobblestone_wall", () -> new LOTRWallBlock(ExtendedBlocks.SCORCHED_COBBLESTONE));
		ExtendedBlocks.SCORCHED_BRICK = registerB("scorched_brick", () -> new ExtendedStoneBlock(MaterialColor.STONE));
		ExtendedBlocks.SCORCHED_BRICK_SLAB = registerB("scorched_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.SCORCHED_BRICK));
		ExtendedBlocks.SCORCHED_BRICK_STAIRS = registerB("scorched_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SCORCHED_BRICK));
		ExtendedBlocks.SCORCHED_BRICK_WALL = registerB("scorched_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.SCORCHED_BRICK));
		//Mud
		ExtendedBlocks.MUD = registerB("mud", () -> new ExtendedGroundDirtBlock(MaterialColor.DIRT, SoundType.GRAVEL, ExtendedBlocks.MUD_PATH, ExtendedBlocks.MUD_FARMLAND));
		ExtendedBlocks.MUD_SLAB = registerB("mud_slab", () -> new AxialSlabBlock(ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_STAIRS = registerB("mud_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_PATH = registerB("mud_path", () -> new ExtendedGroundPathBlock(MaterialColor.DIRT, SoundType.GRASS, ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_PATH_SLAB = registerB("mud_path_slab", () -> new AxialSlabBlock(ExtendedBlocks.MUD_PATH));
		ExtendedBlocks.MUD_PATH_STAIRS = registerB("mud_path_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MUD_PATH));
		ExtendedBlocks.MUD_GRASS = registerB("mud_grass", () -> new ExtendedGroundGrassBlock(MaterialColor.DIRT, SoundType.GRASS, ExtendedBlocks.MUD, ExtendedBlocks.MUD_PATH, ExtendedBlocks.MUD_FARMLAND));
		ExtendedBlocks.MUD_FARMLAND = registerB("mud_farmland", () -> new ExtendedGroundFarmlandBlock(MaterialColor.DIRT, SoundType.GRAVEL, ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_BRICK = registerB("mud_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.MUD_BRICK_SLAB = registerB("mud_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MUD_BRICK));
		ExtendedBlocks.MUD_BRICK_STAIRS = registerB("mud_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MUD_BRICK));
		ExtendedBlocks.MUD_BRICK_WALL = registerB("mud_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MUD_BRICK));
		ExtendedBlocks.CRACKED_MUD_BRICK = registerB("cracked_mud_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.CRACKED_MUD_BRICK_SLAB = registerB("cracked_mud_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_MUD_BRICK));
		ExtendedBlocks.CRACKED_MUD_BRICK_STAIRS = registerB("cracked_mud_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_MUD_BRICK));
		ExtendedBlocks.CRACKED_MUD_BRICK_WALL = registerB("cracked_mud_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_MUD_BRICK));
		ExtendedBlocks.MOSSY_MUD_BRICK = registerB("mossy_mud_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.MOSSY_MUD_BRICK_SLAB = registerB("mossy_mud_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_MUD_BRICK));
		ExtendedBlocks.MOSSY_MUD_BRICK_STAIRS = registerB("mossy_mud_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_MUD_BRICK));
		ExtendedBlocks.MOSSY_MUD_BRICK_WALL = registerB("mossy_mud_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_MUD_BRICK));
		//Taurethrim
		ExtendedBlocks.TAURETHRIM_BRICK = registerB("taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.TAURETHRIM_BRICK_SLAB = registerB("taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_BRICK_STAIRS = registerB("taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_BRICK_WALL = registerB("taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_PILLAR = registerB("taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.TAURETHRIM_PILLAR_SLAB = registerB("taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.TAURETHRIM_PILLAR));
		
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK = registerB("mossy_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB = registerB("mossy_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS = registerB("mossy_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL = registerB("mossy_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK = registerB("cracked_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB = registerB("cracked_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS = registerB("cracked_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL = registerB("cracked_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK = registerB("golden_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB = registerB("golden_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS = registerB("golden_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL = registerB("golden_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR = registerB("golden_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.GOLD));
		ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB = registerB("golden_taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR));

		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK = registerB("obsidian_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB = registerB("obsidian_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS = registerB("obsidian_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL = registerB("obsidian_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR = registerB("obsidian_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB = registerB("obsidian_taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR));

		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK = registerB("obsidian_dwarven_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_SLAB = registerB("obsidian_dwarven_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK));
		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_STAIRS = registerB("obsidian_dwarven_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK));
		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_WALL = registerB("obsidian_dwarven_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK));

		//Dol Guldur
		ExtendedBlocks.DOL_GULDUR_BRICK = registerB("dol_guldur_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.DOL_GULDUR_BRICK_SLAB = registerB("dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS = registerB("dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_BRICK_WALL = registerB("dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_PILLAR = registerB("dol_guldur_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB = registerB("dol_guldur_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.DOL_GULDUR_PILLAR));
		
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK = registerB("mossy_dol_guldur_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB = registerB("mossy_dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS = registerB("mossy_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL = registerB("mossy_dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK = registerB("cracked_dol_guldur_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB = registerB("cracked_dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS = registerB("cracked_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL = registerB("cracked_dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		
		ExtendedBlocks.CARVED_DOL_GULDUR_BRICK = registerB("carved_dol_guldur_brick", () -> new LOTRStoneBlock(ExtendedBlocks.DOL_GULDUR_BRICK));

		//Rhunic
		ExtendedBlocks.RHUNIC_BRICK = registerB("rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RHUNIC_BRICK_SLAB = registerB("rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_BRICK_STAIRS = registerB("rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_BRICK_WALL = registerB("rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_PILLAR = registerB("rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RHUNIC_PILLAR_SLAB = registerB("rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RHUNIC_BRICK = registerB("carved_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RHUNIC_BRICK));
		
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK = registerB("gold_trimmed_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_SLAB = registerB("gold_trimmed_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS = registerB("gold_trimmed_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_WALL = registerB("gold_trimmed_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RHUNIC_BRICK));

		ExtendedBlocks.CRACKED_RHUNIC_BRICK = registerB("cracked_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB = registerB("cracked_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS = registerB("cracked_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL = registerB("cracked_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));

		ExtendedBlocks.MOSSY_RHUNIC_BRICK = registerB("mossy_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB = registerB("mossy_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS = registerB("mossy_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL = registerB("mossy_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));

		ExtendedBlocks.FLOWERY_RHUNIC_BRICK = registerB("flowery_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB = registerB("flowery_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS = registerB("flowery_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL = registerB("flowery_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		
		ExtendedBlocks.RED_RHUNIC_BRICK = registerB("red_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.RED_RHUNIC_BRICK_SLAB = registerB("red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS = registerB("red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_WALL = registerB("red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_PILLAR = registerB("red_rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB = registerB("red_rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RED_RHUNIC_BRICK = registerB("carved_red_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RED_RHUNIC_BRICK));

		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK = registerB("cracked_red_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_SLAB = registerB("cracked_red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_STAIRS = registerB("cracked_red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_WALL = registerB("cracked_red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK));

		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK = registerB("mossy_red_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_SLAB = registerB("mossy_red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_STAIRS = registerB("mossy_red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_WALL = registerB("mossy_red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK));

		ExtendedBlocks.ROHAN_BEAM = registerB("rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));
		ExtendedBlocks.ORNATE_ROHAN_BEAM = registerB("ornate_rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));
		ExtendedBlocks.GOLDEN_ROHAN_BEAM = registerB("golden_rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));
		ExtendedBlocks.ORNATE_GOLDEN_ROHAN_BEAM = registerB("ornate_golden_rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));
		
		//Gondor
		ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK = registerB("black_carved_gondor_brick", () -> new LOTRStoneBlock(LOTRBlocks.NUMENOREAN_BRICK));
		ExtendedBlocks.CARVED_BLACK_UMBAR_BRICK = registerB("carved_black_umbar_brick", () -> new LOTRStoneBlock(LOTRBlocks.NUMENOREAN_BRICK));
		ExtendedBlocks.CARVED_UMBAR_BRICK = registerB("carved_umbar_brick", () -> new LOTRStoneBlock(LOTRBlocks.UMBAR_BRICK));
		ExtendedBlocks.SMOOTH_GONDOR_ROCK_STAIRS = registerB("smooth_gondor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_GONDOR_ROCK));
		
		//Rohan
		ExtendedBlocks.SMOOTH_ROHAN_ROCK_STAIRS = registerB("smooth_rohan_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_ROHAN_ROCK));

		//Blue Mountains
		ExtendedBlocks.SMOOTH_BLUE_ROCK_STAIRS = registerB("smooth_blue_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_BLUE_ROCK));
		
		//Red Mountains
		ExtendedBlocks.SMOOTH_RED_ROCK_STAIRS = registerB("smooth_red_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_RED_ROCK));

		//Mordor
		ExtendedBlocks.SMOOTH_MORDOR_ROCK_STAIRS = registerB("smooth_mordor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_MORDOR_ROCK));

		//Drytone/stone
		ExtendedBlocks.DRYSTONE_SLAB = registerB("drystone_slab", () -> new AxialSlabBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.DRYSTONE_STAIRS = registerB("drystone_stairs", () -> new LOTRStairsBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.DRYSTONE_WALL = registerB("drystone_wall", () -> new LOTRWallBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.COBBLED_DRYSTONE = registerB("cobbled_drystone", () -> new ExtendedStoneBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.CUT_DRYSTONE = registerB("cut_drystone", () -> new ExtendedStoneBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.SMOOTH_STONE_STAIRS = registerB("smooth_stone_stairs", () -> new LOTRStairsBlock(Blocks.SMOOTH_STONE));
		
		ExtendedBlocks.MOSSY_DRYSTONE = registerB("mossy_drystone", () -> new ExtendedStoneBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.MOSSY_DRYSTONE_SLAB = registerB("mossy_drystone_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_DRYSTONE));
		ExtendedBlocks.MOSSY_DRYSTONE_STAIRS = registerB("mossy_drystone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_DRYSTONE));
		ExtendedBlocks.MOSSY_DRYSTONE_WALL = registerB("mossy_drystone_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_DRYSTONE));
		
		//BUSHES
		ExtendedBlocks.BLACKBERRY_BUSH = registerB("blackberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.BLACKBERRY, ExtendedItems.BLACKBERRY_BUSH));
		ExtendedBlocks.BLUEBERRY_BUSH = registerB("blueberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.BLUEBERRY, ExtendedItems.BLUEBERRY_BUSH));
		ExtendedBlocks.CRANBERRY_BUSH = registerB("cranberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.CRANBERRY, ExtendedItems.CRANBERRY_BUSH));
		ExtendedBlocks.ELDERBERRY_BUSH = registerB("elderberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.ELDERBERRY, ExtendedItems.ELDERBERRY_BUSH));
		ExtendedBlocks.RASPBERRY_BUSH = registerB("raspberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.RASPBERRY, ExtendedItems.RASPBERRY_BUSH));
		ExtendedBlocks.WILDBERRY_BUSH = registerB("wildberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.WILDBERRY, ExtendedItems.WILDBERRY_BUSH));
		ExtendedBlocks.BLACKBERRY_BUSH_PLANT = registerB("blackberry_plant", () -> new ExtendedBerryPlantBlock(ExtendedItems.BLACKBERRY));
		ExtendedBlocks.BLUEBERRY_BUSH_PLANT = registerB("blueberry_plant", () -> new ExtendedBerryPlantBlock(ExtendedItems.BLUEBERRY));
		ExtendedBlocks.CRANBERRY_BUSH_PLANT = registerB("cranberry_plant", () -> new ExtendedBerryPlantBlock(ExtendedItems.CRANBERRY));
		ExtendedBlocks.ELDERBERRY_BUSH_PLANT = registerB("elderberry_plant", () -> new ExtendedBerryPlantBlock(ExtendedItems.ELDERBERRY));
		ExtendedBlocks.RASPBERRY_BUSH_PLANT = registerB("raspberry_plant", () -> new ExtendedBerryPlantBlock(ExtendedItems.RASPBERRY));
		ExtendedBlocks.WILDBERRY_BUSH_PLANT = registerB("wildberry_plant", () -> new ExtendedBerryPlantBlock(ExtendedItems.WILDBERRY));

		//CRAFTING TABLES
		ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE = registerB("taurethrim_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Taurethrim(ExtendedBlocks.TAURETHRIM_BRICK, ExtendedBlocks.TAURETHRIM_STONE_CRAFTING_TABLE));
		ExtendedBlocks.TAURETHRIM_STONE_CRAFTING_TABLE = registerB("stone_taurethrim_crafting_table", () -> new ExtendedTwoTypeDummyBlock(ExtendedBlocks.TAURETHRIM_BRICK, ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE));
		ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE = registerB("dol_guldur_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Dol_Guldur(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.RHUNIC_CRAFTING_TABLE = registerB("rhunic_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Rhunic(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.CONQUEST_TABLE = registerB("conquest_table", () -> new ExtendedConquestTableBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.5F)));
		//TODO add block props
		ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE = registerB("half_troll_crafting_table", () -> new ExtendedFactionCraftingTableBlock.HalfTroll(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.GUNDABAD_CRAFTING_TABLE = registerB("gundabad_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Gundabad(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.MORWAITH_CRAFTING_TABLE = registerB("morwaith_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Morwaith(ExtendedBlocks.TAURETHRIM_BRICK));
		
		ExtendedBlocks.MORGUL_LANTERN = registerB("morgul_lantern", () -> new ExtendedLanternBlock(14));
		ExtendedBlocks.MORGUL_TORCH = registerB("morgul_torch", () -> new ExtendedLOTRTorchBlock(14).setParticles(ExtendedParticles.MORGUL_FLAME, () -> ParticleTypes.SMOKE));
		ExtendedBlocks.MORGUL_WALL_TORCH = registerB("morgul_torch_wall", () -> new ExtendedLOTRWallTorchBlock(ExtendedBlocks.MORGUL_TORCH));
		ExtendedBlocks.GREEN_FLAME_HEARTH_BLOCK = registerB("green_fire_hearth_block", () -> new ExtendedGreenHearthBlock());
		ExtendedBlocks.GREEN_FIRE_BLOCK = registerB("green_fire", () -> new ExtendedGreenFireBlock(AbstractBlock.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN).noCollission().instabreak().lightLevel((p_235467_0_) -> {
		      return 10;
		   }).sound(SoundType.WOOL)));

		for(ExtendedEntitySpawnTypes type : ExtendedEntitySpawnTypes.values()) {
			ExtendedBlocks.ENTITYSPAWNERBLOCKS.put(type.getSerializedName(), registerB("entity_spawner_"+type.getSerializedName(), () -> new ExtendedSpawnerBlock(Properties.of(Material.STONE).strength(-1.0F).noCollission().noDrops(), type)));
		}
		for(ExtendedEntitySpawnGroupTypes type : ExtendedEntitySpawnGroupTypes.values()) {
			ExtendedBlocks.ENTITYGROUPSPAWNERBLOCKS.put(type.getSerializedName(), registerB("group_entity_spawner_"+type.getSerializedName(), () -> new ExtendedGroupSpawnerBlock(Properties.of(Material.STONE).strength(-1.0F).noCollission().noDrops(), type)));
		}
		ExtendedBlocks.CHEESE_WHEEL = registerB("cheese_wheel", () -> new ExtendedCheeseWheelBlock(Properties.copy(Blocks.CAKE), 1.0F, ExtendedItems.CHEESE_SLICE, ExtendedCheeseState.UNAFFECTED));
		ExtendedBlocks.AGED_CHEESE_WHEEL = registerB("aged_cheese_wheel", () -> new ExtendedCheeseWheelBlock(Properties.copy(Blocks.CAKE), 1.0F, ExtendedItems.AGED_CHEESE_SLICE, ExtendedCheeseState.AGED));

		ExtendedBlocks.BANANA_CAKE = registerB("banana_cake", () -> new ExtendedCakeBlock(Properties.copy(Blocks.CAKE), 1.0F));

		ExtendedBlocks.STONE_CHEST = registerB("stone_chest", () -> new ExtendedSingleChestBlock(()-> new ExtendedStoneChestTileEntity()));
		ExtendedBlocks.REED_BASKET = registerB("reed_basket", () -> new ExtendedReedBasketBlock(AbstractBlock.Properties.of(Material.GRASS).sound(SoundType.GRASS).strength(0.5F).noOcclusion()));
		ExtendedBlocks.WEAPON_RACK = registerB("weapon_rack", () -> new ExtendedWeaponRackBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.5F).noCollission()));

		ExtendedBlocks.STRAW_BED = registerB("straw_bed", () -> new ExtendedStrawBedBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
		ExtendedBlocks.ORC_BED = registerB("orc_bed", () -> new ExtendedOrcBedBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.BLACK_FUR_BED = registerB("black_fur_bed", () -> new ExtendedBlackFurBedBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.FUR_BED = registerB("fur_bed", () -> new ExtendedFurBedBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.GRAY_FUR_BED = registerB("gray_fur_bed", () -> new ExtendedGrayFurBedBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.SILVER_FUR_BED = registerB("silver_fur_bed", () -> new ExtendedSilverFurBedBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.WHITE_FUR_BED = registerB("white_fur_bed", () -> new ExtendedWhiteFurBedBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.BEDROLL = registerB("bedroll", () -> new ExtendedBedrollBlock(AbstractBlock.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));

	}

	@Inject(method = "register()V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, remap=false)
	private static void bootstrapExtendedBiomeFeatures(CallbackInfo ci, IEventBus bus) {
		bus.addGenericListener(Block.class, ExtendedBiomeFeatures::setup);
	}
}
