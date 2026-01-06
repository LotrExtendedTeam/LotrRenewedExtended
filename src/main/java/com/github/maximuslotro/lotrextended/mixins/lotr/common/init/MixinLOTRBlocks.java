package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.block.*;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBlackFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedDwarvenBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedGrayFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedHighElvenBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedOrcBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedSilverFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedStrawBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedWhiteFurBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedOrcBarrelBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedReedBasketBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedStoneChestBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedGroupSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.trees.BananaTree;
import com.github.maximuslotro.lotrrextended.common.block.trees.ChestnutTree;
import com.github.maximuslotro.lotrrextended.common.block.trees.PlumTree;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCheeseState;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnGroupTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedWargFurType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedMaterials;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSoundTypes;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;

import lotr.common.block.*;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRParticles;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Used to register new blocks added by Extended
 * @Mixin (LOTRBlocks.class)
 * */
@SuppressWarnings("deprecation")
@Mixin(LOTRBlocks.class)
public class MixinLOTRBlocks {

	/**
	 * Get the block DeferredRegister for Renewed's blocks
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static DeferredRegister<Block> BLOCKS;
	/**
	 * Referencing we can register new Blue Mallorn Wall Torches
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> BLUE_MALLORN_TORCH;
	/**
	 * Referencing we can register new Green Mallorn Wall Torches
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> GREEN_MALLORN_TORCH;
	/**
	 * Referencing we can register new Gold Mallorn Wall Torches
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> GOLD_MALLORN_TORCH;
	/**
	 * Referencing we can register new Silver Mallorn Wall Torches
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> SILVER_MALLORN_TORCH;
	/**
	 * Referencing we can register new High Elven Wall Torches
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> HIGH_ELVEN_TORCH;
	/**
	 * Referencing we can register new Dwarven Wall Torches
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> DWARVEN_TORCH;
	/**
	 * Referencing we can register new Blubber Wall Torches
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> BLUBBER_TORCH;
	/**
	 * Referencing we can register new Wattle And Daub Pillar block
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static RegistryObject<Block> WATTLE_AND_DAUB;

	/**
	 * @reason Overrides the default registration of Renewed's block to fix things, override registrations, and replace functionalityUsed for toggleable torches, axe mineable wattle and daub, shovel mineable white sand
	 * @Redirect (method = "&lt;clinit&gt;", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/registries/DeferredRegister;register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/minecraftforge/fml/RegistryObject;"),remap = false)
	 * @param register The DeferredRegister instance to register to
	 * @param name The name of the block to register, is used in the registry name
	 * @param sup The Block supplier for the block instance to be registered
	 * @return Returns either the default registration of the Renewed block, or a modified class instance
	 * */
	@SuppressWarnings("unchecked")
	@Redirect(method = "<clinit>",
			at = @At(value = "INVOKE",
			target = "Lnet/minecraftforge/registries/DeferredRegister;register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/minecraftforge/fml/RegistryObject;"),remap = false)
	private static RegistryObject<Block> injectedBlockRegister(DeferredRegister<Block> register, String name, Supplier<? extends Block> sup) {
		RegistryObject<Block> returnBlock = null;
        switch (name) {
            case "blue_mallorn_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> (new ExtendedLOTRTorchBlock(14)).setParticles(LOTRParticles.BLUE_ELVEN_GLOW));
                break;
            case "blue_mallorn_wall_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRWallTorchBlock(BLUE_MALLORN_TORCH));
                break;
            case "green_mallorn_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> (new ExtendedLOTRTorchBlock(14)).setParticles(LOTRParticles.GREEN_ELVEN_GLOW));
                break;
            case "green_mallorn_wall_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRWallTorchBlock(GREEN_MALLORN_TORCH));
                break;
            case "gold_mallorn_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> (new ExtendedLOTRTorchBlock(14)).setParticles(LOTRParticles.GOLD_ELVEN_GLOW));
                break;
            case "gold_mallorn_wall_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRWallTorchBlock(GOLD_MALLORN_TORCH));
                break;
            case "silver_mallorn_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> (new ExtendedLOTRTorchBlock(14)).setParticles(LOTRParticles.SILVER_ELVEN_GLOW));
                break;
            case "silver_mallorn_wall_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRWallTorchBlock(SILVER_MALLORN_TORCH));
                break;
            case "high_elven_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> (new ExtendedLOTRTorchBlock(14)).setParticles(LOTRParticles.BLUE_ELVEN_GLOW));
                break;
            case "high_elven_wall_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRWallTorchBlock(HIGH_ELVEN_TORCH));
                break;
            case "dwarven_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRTorchBlock(14));
                break;
            case "dwarven_wall_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRWallTorchBlock(DWARVEN_TORCH));
                break;
            case "blubber_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRTorchBlock(12));
                break;
            case "blubber_wall_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedLOTRWallTorchBlock(BLUBBER_TORCH));
                break;
            case "orc_torch":
                // Override torch to add an on/off ability
                returnBlock = BLOCKS.register(name, () -> new ExtendedDoubleTorchBlock(14));
                break;
            case "white_sand":
                //Fixes white sand not being mined faster with shovels.
                //Also removes lotr.common.block.LOTRSandBlock from being used
                returnBlock = BLOCKS.register(name, () -> new SandBlock(15856113, Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)));
                break;
            case "wattle_and_daub":
                //Fixes wattle and daub not being mined faster with axes.
                returnBlock = BLOCKS.register(name, () -> new WattleAndDaubBlock(Properties.of(Material.GRASS, MaterialColor.QUARTZ).strength(1.0F).harvestTool(ToolType.AXE).sound(SoundType.SCAFFOLDING)));
                break;
            case "wattle_and_daub_pillar":
                //Fixes wattle and daub pillar not being mined faster with axes.
                //Also removes lotr.common.block.WattleAndDaubPillarBlock from being used
                returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRPillarBlock(WATTLE_AND_DAUB));
                break;
            case "white_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.WHITE_WOOL));
            	break;
            case "orange_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.ORANGE_WOOL));
            	break;
            case "magenta_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.MAGENTA_WOOL));
            	break;
            case "light_blue_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.LIGHT_BLUE_WOOL));
            	break;
            case "yellow_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.YELLOW_WOOL));
            	break;
            case "lime_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.LIME_WOOL));
            	break;
            case "pink_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.PINK_WOOL));
            	break;
            case "gray_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.GRAY_WOOL));
            	break;
            case "light_gray_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.LIGHT_GRAY_WOOL));
            	break;
            case "cyan_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.CYAN_WOOL));
            	break;
            case "purple_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.PURPLE_WOOL));
            	break;
            case "blue_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.BLUE_WOOL));
            	break;
            case "brown_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.BROWN_WOOL));
            	break;
            case "green_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.GREEN_WOOL));
            	break;
            case "red_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.RED_WOOL));
            	break;
            case "black_wool_slab":
            	// Fixes wool slabs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableAxialSlabBlock(Blocks.BLACK_WOOL));
            	break;
            case "white_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.WHITE_WOOL));
            	break;
            case "orange_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.ORANGE_WOOL));
            	break;
            case "magenta_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.MAGENTA_WOOL));
            	break;
            case "light_blue_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.LIGHT_BLUE_WOOL));
            	break;
            case "yellow_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.YELLOW_WOOL));
            	break;
            case "lime_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.LIME_WOOL));
            	break;
            case "pink_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.PINK_WOOL));
            	break;
            case "gray_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.GRAY_WOOL));
            	break;
            case "light_gray_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.LIGHT_GRAY_WOOL));
            	break;
            case "cyan_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.CYAN_WOOL));
            	break;
            case "purple_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.PURPLE_WOOL));
            	break;
            case "blue_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.BLUE_WOOL));
            	break;
            case "brown_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.BROWN_WOOL));
            	break;
            case "green_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.GREEN_WOOL));
            	break;
            case "red_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.RED_WOOL));
            	break;
            case "black_wool_stairs":
            	// Fixes wool stairs not burning
            	returnBlock = BLOCKS.register(name, () -> new ExtendedBurnableLOTRStairsBlock(Blocks.BLACK_WOOL));
            	break;
        }
		if(returnBlock==null) {
			returnBlock = BLOCKS.register(name, sup);
		}else {
			//Try cleaning unused block declarations
			sup = null;
		}
		return returnBlock;
	}

	/**
	 * Register our own blocks, with dataGen handling
	 * @param name A String name of the block
	 * @param sup A Block supplier to build
	 * @return A built RegistryObject for the Block 
	 * */
	@Unique
	private static RegistryObject<Block> registerB(String name, Supplier<? extends Block> sup) {
		RegistryObject<Block> block = BLOCKS.register(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedBlocks.REGISTEREDBLOCKS.put(name, block);
		}
		return block;
    }

	/**
	 * Register our own blocks, with dataGen handling, specifically for pot blocks
	 * @param name A String name of the block
	 * @param sup A Block supplier to build
	 * @return A built RegistryObject for the Block 
	 * */
	@Unique
	private static RegistryObject<Block> registerPOT(String name, Supplier<? extends Block> sup) {
		RegistryObject<Block> block = BLOCKS.register(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedBlocks.REGISTEREDBLOCKS.put(name, block);
		}
		ExtendedBlocks.REGISTEREDPOTS.put(name, block);
		return block;
    }

	/**
	 * @reason Register Extended's blocks after Renewed's static registering is done, but before the block differed register is registered
	 * @Inject (method = "register()V", at = @At("HEAD"), remap=false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@SuppressWarnings("unchecked")
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedBlocks(CallbackInfo ci) {
		//ORE STORAGE
		ExtendedBlocks.GALVORN_BLOCK = registerB("galvorn_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(4.0F, 8.0F).sound(SoundType.METAL), 2));
		ExtendedBlocks.BLACK_URUK_STEEL_BLOCK = registerB("black_uruk_steel_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.GILDED_IRON_BLOCK = registerB("gilded_iron_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.MORGUL_STEEL_BLOCK = registerB("morgul_steel_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));

		//Wood Types
		ExtendedBlocks.CHESTNUT_LOG = registerB("chestnut_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL, () -> ExtendedBlocks.STRIPPED_CHESTNUT_LOG.get()));
		ExtendedBlocks.CHESTNUT_WOOD = registerB("chestnut_wood", () -> new StrippableWoodBlock(ExtendedBlocks.CHESTNUT_LOG, () -> ExtendedBlocks.STRIPPED_CHESTNUT_WOOD.get()));
		ExtendedBlocks.CHESTNUT_PLANKS = registerB("chestnut_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
		ExtendedBlocks.CHESTNUT_LEAVES = registerB("chestnut_leaves", LOTRLeavesBlock::new);
		ExtendedBlocks.CHESTNUT_LEAVES_FRUIT = registerB("chestnut_leaves_fruit", LOTRLeavesBlock::new);
		ExtendedBlocks.CHESTNUT_SAPLING = registerB("chestnut_sapling", () -> new LOTRSaplingBlock(new ChestnutTree()));
		ExtendedBlocks.POTTED_CHESTNUT_SAPLING = registerB("potted_chestnut_sapling", () -> new LOTRPottedPlantBlock(ExtendedBlocks.CHESTNUT_SAPLING));
		registerPOT("large_pot_chestnut_sapling", () -> new ExtendedLargeFlowerPotBlock(ExtendedBlocks.CHESTNUT_SAPLING));
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

		ExtendedBlocks.BANANA_LOG = registerB("banana_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.COLOR_YELLOW, () -> ExtendedBlocks.STRIPPED_BANANA_LOG.get()));
		ExtendedBlocks.BANANA_WOOD = registerB("banana_wood", () -> new StrippableWoodBlock(ExtendedBlocks.BANANA_LOG, () -> ExtendedBlocks.STRIPPED_BANANA_WOOD.get()));
		ExtendedBlocks.BANANA_PLANKS = registerB("banana_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
		ExtendedBlocks.BANANA_LEAVES = registerB("banana_leaves", LOTRLeavesBlock::new);
		ExtendedBlocks.BANANA_SAPLING = registerB("banana_sapling", () -> new LOTRSaplingBlock(new BananaTree()));
		ExtendedBlocks.POTTED_BANANA_SAPLING = registerB("potted_banana_sapling", () -> new LOTRPottedPlantBlock(ExtendedBlocks.BANANA_SAPLING));
		registerPOT("large_pot_banana_sapling", () -> new ExtendedLargeFlowerPotBlock(ExtendedBlocks.BANANA_SAPLING));
		ExtendedBlocks.BANANA_SLAB = registerB("banana_slab", () -> new AxialSlabBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_STAIRS = registerB("banana_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_FENCE = registerB("banana_fence", () -> new LOTRFenceBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_FENCE_GATE = registerB("banana_fence_gate", () -> new LOTRFenceGateBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_DOOR = registerB("banana_door", () -> new LOTRDoorBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_TRAPDOOR = registerB("banana_trapdoor", () -> new LOTRTrapdoorBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_PRESSURE_PLATE = registerB("banana_pressure_plate", () -> new LOTRWoodPressurePlateBlock(ExtendedBlocks.BANANA_PLANKS));
		ExtendedBlocks.BANANA_BUTTON = registerB("banana_button", LOTRWoodButtonBlock::new);
		ExtendedBlocks.STRIPPED_BANANA_LOG = registerB("stripped_banana_log", () -> new LOTRStrippedLogBlock(ExtendedBlocks.BANANA_LOG));
		ExtendedBlocks.STRIPPED_BANANA_WOOD = registerB("stripped_banana_wood", () -> new LOTRStrippedWoodBlock(ExtendedBlocks.STRIPPED_BANANA_LOG));
		ExtendedBlocks.BANANA_BEAM = registerB("banana_beam", () -> new WoodBeamBlock(ExtendedBlocks.BANANA_LOG));
		ExtendedBlocks.BANANA_SIGN = registerB("banana_sign", () -> new LOTRStandingSignBlock(ExtendedBlocks.BANANA_PLANKS, ExtendedSignTypes.BANANA));
		ExtendedBlocks.BANANA_WALL_SIGN = registerB("banana_wall_sign", () -> new LOTRWallSignBlock(ExtendedBlocks.BANANA_SIGN));
		ExtendedBlocks.BANANA_LOG_SLAB = registerB("banana_log_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.BANANA_LOG, () -> ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB.get()));
		ExtendedBlocks.BANANA_WOOD_SLAB = registerB("banana_wood_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.BANANA_WOOD, () -> ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB.get()));
		ExtendedBlocks.BANANA_WOOD_STAIRS = registerB("banana_wood_stairs", () -> new StrippableLogStairsBlock(ExtendedBlocks.BANANA_WOOD, () -> ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS.get()));
		ExtendedBlocks.BANANA_BRANCH = registerB("banana_branch", () -> new StrippableBranchBlock(ExtendedBlocks.BANANA_WOOD, () -> ExtendedBlocks.STRIPPED_BANANA_BRANCH.get()));
		ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB = registerB("stripped_banana_log_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_BANANA_LOG));
		ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB = registerB("stripped_banana_wood_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_BANANA_WOOD));
		ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS = registerB("stripped_banana_wood_stairs", () -> new LogStairsBlock(ExtendedBlocks.STRIPPED_BANANA_WOOD));
		ExtendedBlocks.STRIPPED_BANANA_BRANCH = registerB("stripped_banana_branch", () -> new BranchBlock(ExtendedBlocks.STRIPPED_BANANA_WOOD));
		ExtendedBlocks.BANANA_BEAM_SLAB = registerB("banana_beam_slab", () -> new LogSlabBlock(ExtendedBlocks.BANANA_BEAM));
		ExtendedBlocks.FALLEN_BANANA_LEAVES = registerB("fallen_banana_leaves", () -> new FallenLeavesBlock(ExtendedBlocks.BANANA_LEAVES));
		ExtendedBlocks.HANGING_BANANA = registerB("hanging_banana", ExtendedHangingBlock::new);

		ExtendedBlocks.PLUM_LOG = registerB("plum_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.COLOR_PURPLE, () -> ExtendedBlocks.STRIPPED_PLUM_LOG.get()));
		ExtendedBlocks.PLUM_WOOD = registerB("plum_wood", () -> new StrippableWoodBlock(ExtendedBlocks.PLUM_LOG, () -> ExtendedBlocks.STRIPPED_PLUM_WOOD.get()));
		ExtendedBlocks.PLUM_PLANKS = registerB("plum_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
		ExtendedBlocks.PLUM_LEAVES = registerB("plum_leaves", LOTRLeavesBlock::new);
		ExtendedBlocks.PLUM_LEAVES_FRUIT = registerB("plum_leaves_fruit", LOTRLeavesBlock::new);
		ExtendedBlocks.PLUM_SAPLING = registerB("plum_sapling", () -> new LOTRSaplingBlock(new PlumTree()));
		ExtendedBlocks.POTTED_PLUM_SAPLING = registerB("potted_plum_sapling", () -> new LOTRPottedPlantBlock(ExtendedBlocks.PLUM_SAPLING));
		registerPOT("large_pot_plum_sapling", () -> new ExtendedLargeFlowerPotBlock(ExtendedBlocks.PLUM_SAPLING));
		ExtendedBlocks.PLUM_SLAB = registerB("plum_slab", () -> new AxialSlabBlock(ExtendedBlocks.PLUM_PLANKS));
		ExtendedBlocks.PLUM_STAIRS = registerB("plum_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.PLUM_PLANKS));
		ExtendedBlocks.PLUM_FENCE = registerB("plum_fence", () -> new LOTRFenceBlock(ExtendedBlocks.PLUM_PLANKS));
		ExtendedBlocks.PLUM_FENCE_GATE = registerB("plum_fence_gate", () -> new LOTRFenceGateBlock(ExtendedBlocks.PLUM_PLANKS));
		ExtendedBlocks.PLUM_DOOR = registerB("plum_door", () -> new LOTRDoorBlock(ExtendedBlocks.PLUM_PLANKS));
		ExtendedBlocks.PLUM_TRAPDOOR = registerB("plum_trapdoor", () -> new LOTRTrapdoorBlock(ExtendedBlocks.PLUM_PLANKS));
		ExtendedBlocks.PLUM_PRESSURE_PLATE = registerB("plum_pressure_plate", () -> new LOTRWoodPressurePlateBlock(ExtendedBlocks.PLUM_PLANKS));
		ExtendedBlocks.PLUM_BUTTON = registerB("plum_button", LOTRWoodButtonBlock::new);
		ExtendedBlocks.STRIPPED_PLUM_LOG = registerB("stripped_plum_log", () -> new LOTRStrippedLogBlock(ExtendedBlocks.PLUM_LOG));
		ExtendedBlocks.STRIPPED_PLUM_WOOD = registerB("stripped_plum_wood", () -> new LOTRStrippedWoodBlock(ExtendedBlocks.STRIPPED_PLUM_LOG));
		ExtendedBlocks.PLUM_BEAM = registerB("plum_beam", () -> new WoodBeamBlock(ExtendedBlocks.PLUM_LOG));
		ExtendedBlocks.PLUM_SIGN = registerB("plum_sign", () -> new LOTRStandingSignBlock(ExtendedBlocks.PLUM_PLANKS, ExtendedSignTypes.PLUM));
		ExtendedBlocks.PLUM_WALL_SIGN = registerB("plum_wall_sign", () -> new LOTRWallSignBlock(ExtendedBlocks.PLUM_SIGN));
		ExtendedBlocks.PLUM_LOG_SLAB = registerB("plum_log_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.PLUM_LOG, () -> ExtendedBlocks.STRIPPED_PLUM_LOG_SLAB.get()));
		ExtendedBlocks.PLUM_WOOD_SLAB = registerB("plum_wood_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.PLUM_WOOD, () -> ExtendedBlocks.STRIPPED_PLUM_WOOD_SLAB.get()));
		ExtendedBlocks.PLUM_WOOD_STAIRS = registerB("plum_wood_stairs", () -> new StrippableLogStairsBlock(ExtendedBlocks.PLUM_WOOD, () -> ExtendedBlocks.STRIPPED_PLUM_WOOD_STAIRS.get()));
		ExtendedBlocks.PLUM_BRANCH = registerB("plum_branch", () -> new StrippableBranchBlock(ExtendedBlocks.PLUM_WOOD, () -> ExtendedBlocks.STRIPPED_PLUM_BRANCH.get()));
		ExtendedBlocks.STRIPPED_PLUM_LOG_SLAB = registerB("stripped_plum_log_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_PLUM_LOG));
		ExtendedBlocks.STRIPPED_PLUM_WOOD_SLAB = registerB("stripped_plum_wood_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_PLUM_WOOD));
		ExtendedBlocks.STRIPPED_PLUM_WOOD_STAIRS = registerB("stripped_plum_wood_stairs", () -> new LogStairsBlock(ExtendedBlocks.STRIPPED_PLUM_WOOD));
		ExtendedBlocks.STRIPPED_PLUM_BRANCH = registerB("stripped_plum_branch", () -> new BranchBlock(ExtendedBlocks.STRIPPED_PLUM_WOOD));
		ExtendedBlocks.PLUM_BEAM_SLAB = registerB("plum_beam_slab", () -> new LogSlabBlock(ExtendedBlocks.PLUM_BEAM));
		ExtendedBlocks.FALLEN_PLUM_LEAVES = registerB("fallen_plum_leaves", () -> new FallenLeavesBlock(ExtendedBlocks.PLUM_LEAVES));

		ExtendedBlocks.WILLOW_LOG = registerB("willow_log", () -> new StrippableLogBlock(MaterialColor.WOOD, MaterialColor.COLOR_PURPLE, () -> ExtendedBlocks.STRIPPED_WILLOW_LOG.get()));
		ExtendedBlocks.WILLOW_WOOD = registerB("willow_wood", () -> new StrippableWoodBlock(ExtendedBlocks.WILLOW_LOG, () -> ExtendedBlocks.STRIPPED_WILLOW_WOOD.get()));
		ExtendedBlocks.WILLOW_PLANKS = registerB("willow_planks", () -> new LOTRPlanksBlock(MaterialColor.WOOD));
		ExtendedBlocks.WILLOW_LEAVES = registerB("willow_leaves", LOTRLeavesBlock::new);
		ExtendedBlocks.WILLOW_SAPLING = registerB("willow_sapling", () -> new ExtendedWillowTreeSapling(new OakTree()));
		ExtendedBlocks.POTTED_WILLOW_SAPLING = registerB("potted_willow_sapling", () -> new LOTRPottedPlantBlock(ExtendedBlocks.WILLOW_SAPLING));
		registerPOT("large_pot_willow_sapling", () -> new ExtendedLargeFlowerPotBlock(ExtendedBlocks.WILLOW_SAPLING));
		ExtendedBlocks.WILLOW_SLAB = registerB("willow_slab", () -> new AxialSlabBlock(ExtendedBlocks.WILLOW_PLANKS));
		ExtendedBlocks.WILLOW_STAIRS = registerB("willow_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.WILLOW_PLANKS));
		ExtendedBlocks.WILLOW_FENCE = registerB("willow_fence", () -> new LOTRFenceBlock(ExtendedBlocks.WILLOW_PLANKS));
		ExtendedBlocks.WILLOW_FENCE_GATE = registerB("willow_fence_gate", () -> new LOTRFenceGateBlock(ExtendedBlocks.WILLOW_PLANKS));
		ExtendedBlocks.WILLOW_DOOR = registerB("willow_door", () -> new LOTRDoorBlock(ExtendedBlocks.WILLOW_PLANKS));
		ExtendedBlocks.WILLOW_TRAPDOOR = registerB("willow_trapdoor", () -> new LOTRTrapdoorBlock(ExtendedBlocks.WILLOW_PLANKS));
		ExtendedBlocks.WILLOW_PRESSURE_PLATE = registerB("willow_pressure_plate", () -> new LOTRWoodPressurePlateBlock(ExtendedBlocks.WILLOW_PLANKS));
		ExtendedBlocks.WILLOW_BUTTON = registerB("willow_button", LOTRWoodButtonBlock::new);
		ExtendedBlocks.STRIPPED_WILLOW_LOG = registerB("stripped_willow_log", () -> new LOTRStrippedLogBlock(ExtendedBlocks.WILLOW_LOG));
		ExtendedBlocks.STRIPPED_WILLOW_WOOD = registerB("stripped_willow_wood", () -> new LOTRStrippedWoodBlock(ExtendedBlocks.STRIPPED_WILLOW_LOG));
		ExtendedBlocks.WILLOW_BEAM = registerB("willow_beam", () -> new WoodBeamBlock(ExtendedBlocks.WILLOW_LOG));
		ExtendedBlocks.WILLOW_SIGN = registerB("willow_sign", () -> new LOTRStandingSignBlock(ExtendedBlocks.WILLOW_PLANKS, ExtendedSignTypes.WILLOW));
		ExtendedBlocks.WILLOW_WALL_SIGN = registerB("willow_wall_sign", () -> new LOTRWallSignBlock(ExtendedBlocks.WILLOW_SIGN));
		ExtendedBlocks.WILLOW_LOG_SLAB = registerB("willow_log_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.WILLOW_LOG, () -> ExtendedBlocks.STRIPPED_WILLOW_LOG_SLAB.get()));
		ExtendedBlocks.WILLOW_WOOD_SLAB = registerB("willow_wood_slab", () -> new StrippableLogSlabBlock(ExtendedBlocks.WILLOW_WOOD, () -> ExtendedBlocks.STRIPPED_WILLOW_WOOD_SLAB.get()));
		ExtendedBlocks.WILLOW_WOOD_STAIRS = registerB("willow_wood_stairs", () -> new StrippableLogStairsBlock(ExtendedBlocks.WILLOW_WOOD, () -> ExtendedBlocks.STRIPPED_WILLOW_WOOD_STAIRS.get()));
		ExtendedBlocks.WILLOW_BRANCH = registerB("willow_branch", () -> new StrippableBranchBlock(ExtendedBlocks.WILLOW_WOOD, () -> ExtendedBlocks.STRIPPED_WILLOW_BRANCH.get()));
		ExtendedBlocks.STRIPPED_WILLOW_LOG_SLAB = registerB("stripped_willow_log_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_WILLOW_LOG));
		ExtendedBlocks.STRIPPED_WILLOW_WOOD_SLAB = registerB("stripped_willow_wood_slab", () -> new LogSlabBlock(ExtendedBlocks.STRIPPED_WILLOW_WOOD));
		ExtendedBlocks.STRIPPED_WILLOW_WOOD_STAIRS = registerB("stripped_willow_wood_stairs", () -> new LogStairsBlock(ExtendedBlocks.STRIPPED_WILLOW_WOOD));
		ExtendedBlocks.STRIPPED_WILLOW_BRANCH = registerB("stripped_willow_branch", () -> new BranchBlock(ExtendedBlocks.STRIPPED_WILLOW_WOOD));
		ExtendedBlocks.WILLOW_BEAM_SLAB = registerB("willow_beam_slab", () -> new LogSlabBlock(ExtendedBlocks.WILLOW_BEAM));
		ExtendedBlocks.FALLEN_WILLOW_LEAVES = registerB("fallen_willow_leaves", () -> new FallenLeavesBlock(ExtendedBlocks.WILLOW_LEAVES));
		ExtendedBlocks.WILLOW_VINES = registerB("willow_vines", () -> new ExtendedVineBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE)));

		//Packed Fur
		ExtendedBlocks.WHITE_MATTED_FUR = registerB("white_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.WHITE));
		ExtendedBlocks.WHITE_MATTED_FUR_SLAB = registerB("white_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(ExtendedBlocks.WHITE_MATTED_FUR));
		ExtendedBlocks.WHITE_MATTED_FUR_STAIRS = registerB("white_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(ExtendedBlocks.WHITE_MATTED_FUR));
		ExtendedBlocks.WHITE_MATTED_FUR_WALL = registerB("white_matted_fur_wall", () -> new ExtendedBurnableLOTRWallBlock(ExtendedBlocks.WHITE_MATTED_FUR));

		ExtendedBlocks.SILVER_MATTED_FUR = registerB("silver_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.SILVER));
		ExtendedBlocks.SILVER_MATTED_FUR_SLAB = registerB("silver_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(ExtendedBlocks.SILVER_MATTED_FUR));
		ExtendedBlocks.SILVER_MATTED_FUR_STAIRS = registerB("silver_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(ExtendedBlocks.SILVER_MATTED_FUR));
		ExtendedBlocks.SILVER_MATTED_FUR_WALL = registerB("silver_matted_fur_wall", () -> new ExtendedBurnableLOTRWallBlock(ExtendedBlocks.SILVER_MATTED_FUR));

		ExtendedBlocks.GRAY_MATTED_FUR = registerB("gray_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.GRAY));
		ExtendedBlocks.GRAY_MATTED_FUR_SLAB = registerB("gray_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(ExtendedBlocks.GRAY_MATTED_FUR));
		ExtendedBlocks.GRAY_MATTED_FUR_STAIRS = registerB("gray_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(ExtendedBlocks.GRAY_MATTED_FUR));
		ExtendedBlocks.GRAY_MATTED_FUR_WALL = registerB("gray_matted_fur_wall", () -> new ExtendedBurnableLOTRWallBlock(ExtendedBlocks.GRAY_MATTED_FUR));

		ExtendedBlocks.BROWN_MATTED_FUR = registerB("matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.BROWN));
		ExtendedBlocks.BROWN_MATTED_FUR_SLAB = registerB("matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(ExtendedBlocks.BROWN_MATTED_FUR));
		ExtendedBlocks.BROWN_MATTED_FUR_STAIRS = registerB("matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(ExtendedBlocks.BROWN_MATTED_FUR));
		ExtendedBlocks.BROWN_MATTED_FUR_WALL = registerB("matted_fur_wall", () -> new ExtendedBurnableLOTRWallBlock(ExtendedBlocks.BROWN_MATTED_FUR));

		ExtendedBlocks.BLACK_MATTED_FUR = registerB("black_matted_fur", () -> new ExtendedMattedFurBlock(ExtendedWargFurType.BLACK));
		ExtendedBlocks.BLACK_MATTED_FUR_SLAB = registerB("black_matted_fur_slab", () -> new ExtendedBurnableAxialSlabBlock(ExtendedBlocks.BLACK_MATTED_FUR));
		ExtendedBlocks.BLACK_MATTED_FUR_STAIRS = registerB("black_matted_fur_stairs", () -> new ExtendedBurnableLOTRStairsBlock(ExtendedBlocks.BLACK_MATTED_FUR));
		ExtendedBlocks.BLACK_MATTED_FUR_WALL = registerB("black_matted_fur_wall", () -> new ExtendedBurnableLOTRWallBlock(ExtendedBlocks.BLACK_MATTED_FUR));

		//Bundled Fur
		ExtendedBlocks.WHITE_FUR_BUNDLE = registerB("white_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.WOOL));
		ExtendedBlocks.SILVER_FUR_BUNDLE = registerB("silver_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.COLOR_LIGHT_GRAY));
		ExtendedBlocks.GRAY_FUR_BUNDLE = registerB("gray_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.BLACK_FUR_BUNDLE = registerB("black_fur_bundle", () -> new ExtendedBundleBlock(MaterialColor.COLOR_BLACK));

		//Fur carpets
		ExtendedBlocks.BLACK_FUR_CARPET = registerB("black_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.BLACK));
		ExtendedBlocks.BROWN_FUR_CARPET = registerB("brown_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.BROWN));
		ExtendedBlocks.GRAY_FUR_CARPET = registerB("gray_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.GRAY));
		ExtendedBlocks.SILVER_FUR_CARPET = registerB("silver_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.SILVER));
		ExtendedBlocks.WHITE_FUR_CARPET = registerB("white_fur_carpet", () -> new ExtendedWargCarpetBlock(ExtendedWargFurType.SILVER));

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
		ExtendedBlocks.METEORITE_ORE = registerB("meteorite", () -> new MineralBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(3).requiresCorrectToolForDrops().strength(50.0F, 1200.0F), 1));
		ExtendedBlocks.AMBER_ORE = registerB("amber_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		ExtendedBlocks.TOPAZ_ORE = registerB("topaz_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		ExtendedBlocks.AMETHYST_ORE = registerB("amethyst_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		ExtendedBlocks.SAPPHIRE_ORE = registerB("sapphire_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		ExtendedBlocks.RUBY_ORE = registerB("ruby_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		ExtendedBlocks.DIAMOND_ORE = registerB("diamond_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		ExtendedBlocks.OPAL_ORE = registerB("opal_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		ExtendedBlocks.EMERALD_ORE = registerB("emerald_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F)).setOreLevel(2));
		//Remains ores
		ExtendedBlocks.REMAINS_ORC = registerB("remains_ore_orc", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));
		ExtendedBlocks.REMAINS_ELF = registerB("remains_ore_elf", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));
		ExtendedBlocks.REMAINS_MAN = registerB("remains_ore_man", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));
		ExtendedBlocks.REMAINS_DWARF = registerB("remains_ore_dwarf", () -> new ExtendedRemainsBlock(Properties.copy(Blocks.DIRT), ToolType.SHOVEL).setOreLevel(0));

		///////////////////////////////BRICKS
		ExtendedBlocks.BARREN_DIRT = registerB("barren_dirt", () -> new ExtendedBarrenDirt(Properties.copy(Blocks.DIRT).harvestTool(ToolType.SHOVEL)));
		ExtendedBlocks.BARREN_DIRT_SLAB = registerB("barren_dirt_slab", () -> new ExtendedStemableAxialSlabBlock(ExtendedBlocks.BARREN_DIRT));
		ExtendedBlocks.COARSE_DIRT_SLAB = registerB("coarse_dirt_slab", () -> new ExtendedPlantableAxialSlabBlock(Blocks.COARSE_DIRT));
		ExtendedBlocks.DIRT_SLAB = registerB("dirt_slab", () -> new ExtendedDirtSlabBlock(Blocks.DIRT));
		ExtendedBlocks.GRASS_BLOCK_SLAB = registerB("grass_block_slab", () -> new ExtendedPlantableSpreadableGrowableAxialSlabBlock(Blocks.GRASS_BLOCK, ExtendedBlocks.DIRT_SLAB, () -> Blocks.DIRT, () -> Blocks.GRASS_BLOCK));
		ExtendedBlocks.GRASS_PATH_SLAB = registerB("grass_path_slab", () -> new ExtendedSwappablePathSlabBlock(Blocks.GRASS_PATH));
		ExtendedBlocks.PODZOL_SLAB = registerB("podzol_slab", () -> new ExtendedPlantableAxialSlabBlock(Blocks.PODZOL));
		ExtendedBlocks.MYCELIUM_SLAB = registerB("mycelium_slab", () -> new ExtendedPlantableSpreadableAxialSlabBlock(Blocks.MYCELIUM, ExtendedBlocks.DIRT_SLAB, () -> Blocks.DIRT, () -> Blocks.MYCELIUM));
		//Bone Block
		ExtendedBlocks.BONE_BLOCK_SLAB = registerB("bone_block_slab", () -> new AxialSlabBlock(Blocks.BONE_BLOCK));
		ExtendedBlocks.BONE_BLOCK_STAIRS = registerB("bone_block_stairs", () -> new LOTRStairsBlock(Blocks.BONE_BLOCK));
		ExtendedBlocks.BONE_BLOCK_WALL = registerB("bone_block_wall", () -> new LOTRWallBlock(Blocks.BONE_BLOCK));
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
		//Arnor
		ExtendedBlocks.CRACKED_ARNOR_PILLAR = registerB("cracked_arnor_pillar", () -> new LOTRPillarBlock(MaterialColor.STONE));
		ExtendedBlocks.CRACKED_ARNOR_PILLAR_SLAB = registerB("cracked_arnor_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_ARNOR_PILLAR));
		ExtendedBlocks.MOSSY_ARNOR_PILLAR = registerB("mossy_arnor_pillar", () -> new LOTRPillarBlock(MaterialColor.STONE));
		ExtendedBlocks.MOSSY_ARNOR_PILLAR_SLAB = registerB("mossy_arnor_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_ARNOR_PILLAR));
		ExtendedBlocks.CRACKED_STONE_PILLAR = registerB("cracked_stone_pillar", () -> new LOTRPillarBlock(MaterialColor.STONE));
		ExtendedBlocks.CRACKED_STONE_PILLAR_SLAB = registerB("cracked_stone_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_STONE_PILLAR));
		// New Blocks
//		ExtendedBlocks.GNEISS_ROCK = registerB("gneiss", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.GNEISS_ROCK_SLAB = registerB("gneiss_slab", () -> new AxialSlabBlock(ExtendedBlocks.GNEISS_ROCK));
//		ExtendedBlocks.GNEISS_ROCK_STAIRS = registerB("gneiss_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GNEISS_ROCK));
//		ExtendedBlocks.GNEISS_ROCK_WALL = registerB("gneiss_wall", () -> new LOTRWallBlock(ExtendedBlocks.GNEISS_ROCK));
//		ExtendedBlocks.GNEISS_BRICK = registerB("gneiss_brick", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.GNEISS_BRICK_SLAB = registerB("gneiss_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.GNEISS_BRICK));
//		ExtendedBlocks.GNEISS_BRICK_STAIRS = registerB("gneiss_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GNEISS_BRICK));
//		ExtendedBlocks.GNEISS_BRICK_WALL = registerB("gneiss_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.GNEISS_BRICK));
//		ExtendedBlocks.POLISHED_GNEISS = registerB("polished_gneiss", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.POLISHED_GNEISS_SLAB = registerB("polished_gneiss_slab", () -> new AxialSlabBlock(ExtendedBlocks.POLISHED_GNEISS));
//		ExtendedBlocks.POLISHED_GNEISS_STAIRS = registerB("polished_gneiss_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.POLISHED_GNEISS));
//		ExtendedBlocks.POLISHED_GNEISS_WALL = registerB("polished_gneiss_wall", () -> new LOTRWallBlock(ExtendedBlocks.POLISHED_GNEISS));
//		ExtendedBlocks.LIMESTONE_ROCK = registerB("limestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.LIMESTONE_ROCK_SLAB = registerB("limestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.LIMESTONE_ROCK));
//		ExtendedBlocks.LIMESTONE_ROCK_STAIRS = registerB("limestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.LIMESTONE_ROCK));
//		ExtendedBlocks.LIMESTONE_ROCK_WALL = registerB("limestone_wall", () -> new LOTRWallBlock(ExtendedBlocks.LIMESTONE_ROCK));
//		ExtendedBlocks.LIMESTONE_BRICK = registerB("limestone_brick", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.LIMESTONE_BRICK_SLAB = registerB("limestone_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.LIMESTONE_BRICK));
//		ExtendedBlocks.LIMESTONE_BRICK_STAIRS = registerB("limestone_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.LIMESTONE_BRICK));
//		ExtendedBlocks.LIMESTONE_BRICK_WALL = registerB("limestone_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.LIMESTONE_BRICK));
//		ExtendedBlocks.POLISHED_LIMESTONE = registerB("polished_limestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.POLISHED_LIMESTONE_SLAB = registerB("polished_limestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.POLISHED_LIMESTONE));
//		ExtendedBlocks.POLISHED_LIMESTONE_STAIRS = registerB("polished_limestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.POLISHED_LIMESTONE));
//		ExtendedBlocks.POLISHED_LIMESTONE_WALL = registerB("polished_limestone_wall", () -> new LOTRWallBlock(ExtendedBlocks.POLISHED_LIMESTONE));
//		ExtendedBlocks.SMOOTH_LIMESTONE = registerB("smooth_limestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.SMOOTH_LIMESTONE_SLAB = registerB("smooth_limestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.SMOOTH_LIMESTONE));
//		ExtendedBlocks.SMOOTH_LIMESTONE_STAIRS = registerB("smooth_limestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SMOOTH_LIMESTONE));
//		ExtendedBlocks.SMOOTH_LIMESTONE_WALL = registerB("smooth_limestone_wall", () -> new LOTRWallBlock(ExtendedBlocks.SMOOTH_LIMESTONE));
//		ExtendedBlocks.LIMESTONE_TILE = registerB("limestone_tile", () -> new ExtendedStoneBlock(MaterialColor.STONE));
//		ExtendedBlocks.LIMESTONE_TILE_SLAB = registerB("limestone_tile_slab", () -> new AxialSlabBlock(ExtendedBlocks.LIMESTONE_TILE));
//		ExtendedBlocks.LIMESTONE_TILE_STAIRS = registerB("limestone_tile_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.LIMESTONE_TILE));
//		ExtendedBlocks.LIMESTONE_TILE_WALL = registerB("limestone_tile_wall", () -> new LOTRWallBlock(ExtendedBlocks.LIMESTONE_TILE));
//		ExtendedBlocks.SLATE_ROCK = registerB("slate", () -> new ExtendedStoneBlock(MaterialColor.QUARTZ));
//		ExtendedBlocks.SLATE_ROCK_SLAB = registerB("slate_slab", () -> new AxialSlabBlock(ExtendedBlocks.SLATE_ROCK));
//		ExtendedBlocks.SLATE_ROCK_STAIRS = registerB("slate_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SLATE_ROCK));
//		ExtendedBlocks.SLATE_ROCK_WALL = registerB("slate_wall", () -> new LOTRWallBlock(ExtendedBlocks.SLATE_ROCK));
//		ExtendedBlocks.SLATE_BRICK = registerB("slate_brick", () -> new ExtendedStoneBlock(MaterialColor.QUARTZ));
//		ExtendedBlocks.SLATE_BRICK_SLAB = registerB("slate_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.SLATE_BRICK));
//		ExtendedBlocks.SLATE_BRICK_STAIRS = registerB("slate_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SLATE_BRICK));
//		ExtendedBlocks.SLATE_BRICK_WALL = registerB("slate_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.SLATE_BRICK));
//		ExtendedBlocks.POLISHED_SLATE = registerB("polished_slate", () -> new ExtendedStoneBlock(MaterialColor.QUARTZ));
//		ExtendedBlocks.POLISHED_SLATE_SLAB = registerB("polished_slate_slab", () -> new AxialSlabBlock(ExtendedBlocks.POLISHED_SLATE));
//		ExtendedBlocks.POLISHED_SLATE_STAIRS = registerB("polished_slate_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.POLISHED_SLATE));
//		ExtendedBlocks.POLISHED_SLATE_WALL = registerB("polished_slate_wall", () -> new LOTRWallBlock(ExtendedBlocks.POLISHED_SLATE));

		ExtendedBlocks.MOSS_BLOCK = registerB("moss_block", () -> new ExtendedMossBlock(Properties.of(ExtendedMaterials.MOSS, MaterialColor.COLOR_GREEN).harvestTool(ToolType.HOE).strength(0.1f).sound(ExtendedSoundTypes.MOSS)));
		ExtendedBlocks.MOSS_CARPET = registerB("moss_carpet", () -> new ExtendedMossCarpetBlock(Properties.of(Material.PLANT, MaterialColor.COLOR_GREEN).strength(0.1f).sound(ExtendedSoundTypes.MOSS)));
		ExtendedBlocks.ANCIENT_RUNE_BLOCK = registerB("ancient_rune_stone", ExtendedAncientStoneRuneBlock::new);

		//Mud
		ExtendedBlocks.MUD = registerB("mud", () -> new ExtendedGroundDirtBlock(MaterialColor.DIRT, SoundType.GRAVEL, ExtendedBlocks.MUD_PATH, ExtendedBlocks.MUD_FARMLAND));
		ExtendedBlocks.MUD_SLAB = registerB("mud_slab", () -> new AxialSlabBlock(ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_STAIRS = registerB("mud_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_PATH = registerB("mud_path", () -> new ExtendedGroundPathBlock(MaterialColor.DIRT, SoundType.GRASS, ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_PATH_SLAB = registerB("mud_path_slab", () -> new AxialSlabBlock(ExtendedBlocks.MUD_PATH));
		ExtendedBlocks.MUD_PATH_STAIRS = registerB("mud_path_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MUD_PATH));
		ExtendedBlocks.MUD_GRASS = registerB("mud_grass", () -> new ExtendedGroundGrassBlock(MaterialColor.DIRT, SoundType.GRASS, ExtendedBlocks.MUD, ExtendedBlocks.MUD_PATH, ExtendedBlocks.MUD_FARMLAND));
		ExtendedBlocks.MUD_FARMLAND = registerB("mud_farmland", () -> new ExtendedGroundFarmlandBlock(MaterialColor.DIRT, SoundType.GRAVEL, ExtendedBlocks.MUD));
		ExtendedBlocks.MUD_BRICK = registerB("mud_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.MUD_BRICK_SLAB = registerB("mud_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MUD_BRICK));
		ExtendedBlocks.MUD_BRICK_STAIRS = registerB("mud_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MUD_BRICK));
		ExtendedBlocks.MUD_BRICK_WALL = registerB("mud_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MUD_BRICK));
		ExtendedBlocks.CRACKED_MUD_BRICK = registerB("cracked_mud_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.CRACKED_MUD_BRICK_SLAB = registerB("cracked_mud_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_MUD_BRICK));
		ExtendedBlocks.CRACKED_MUD_BRICK_STAIRS = registerB("cracked_mud_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_MUD_BRICK));
		ExtendedBlocks.CRACKED_MUD_BRICK_WALL = registerB("cracked_mud_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_MUD_BRICK));
		ExtendedBlocks.MOSSY_MUD_BRICK = registerB("mossy_mud_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.MOSSY_MUD_BRICK_SLAB = registerB("mossy_mud_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_MUD_BRICK));
		ExtendedBlocks.MOSSY_MUD_BRICK_STAIRS = registerB("mossy_mud_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_MUD_BRICK));
		ExtendedBlocks.MOSSY_MUD_BRICK_WALL = registerB("mossy_mud_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_MUD_BRICK));
		//Taurethrim
		ExtendedBlocks.TAURETHRIM_BRICK = registerB("taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.TAURETHRIM_BRICK_SLAB = registerB("taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_BRICK_STAIRS = registerB("taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_BRICK_WALL = registerB("taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_PILLAR = registerB("taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.TAURETHRIM_PILLAR_SLAB = registerB("taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.TAURETHRIM_PILLAR));

		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK = registerB("mossy_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB = registerB("mossy_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS = registerB("mossy_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL = registerB("mossy_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));

		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK = registerB("cracked_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB = registerB("cracked_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS = registerB("cracked_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL = registerB("cracked_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));

		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK = registerB("golden_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB = registerB("golden_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS = registerB("golden_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL = registerB("golden_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR = registerB("golden_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.GOLD));
		ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB = registerB("golden_taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR));

		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK = registerB("obsidian_taurethrim_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB = registerB("obsidian_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS = registerB("obsidian_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL = registerB("obsidian_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR = registerB("obsidian_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB = registerB("obsidian_taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR));

		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK = registerB("obsidian_dwarven_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_SLAB = registerB("obsidian_dwarven_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK));
		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_STAIRS = registerB("obsidian_dwarven_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK));
		ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_WALL = registerB("obsidian_dwarven_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK));

		//Dol Guldur
		ExtendedBlocks.DOL_GULDUR_BRICK = registerB("dol_guldur_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.DOL_GULDUR_BRICK_SLAB = registerB("dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS = registerB("dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_BRICK_WALL = registerB("dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_PILLAR = registerB("dol_guldur_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB = registerB("dol_guldur_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.DOL_GULDUR_PILLAR));

		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK = registerB("mossy_dol_guldur_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB = registerB("mossy_dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS = registerB("mossy_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL = registerB("mossy_dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));

		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK = registerB("cracked_dol_guldur_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB = registerB("cracked_dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS = registerB("cracked_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL = registerB("cracked_dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));

		ExtendedBlocks.CARVED_DOL_GULDUR_BRICK = registerB("carved_dol_guldur_brick", () -> new ExtendedStoneBlock(ExtendedBlocks.DOL_GULDUR_BRICK));

		//Rhunic
		ExtendedBlocks.RHUNIC_BRICK = registerB("rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RHUNIC_BRICK_SLAB = registerB("rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_BRICK_STAIRS = registerB("rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_BRICK_WALL = registerB("rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_PILLAR = registerB("rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RHUNIC_PILLAR_SLAB = registerB("rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RHUNIC_BRICK = registerB("carved_rhunic_brick", () -> new ExtendedStoneBlock(ExtendedBlocks.RHUNIC_BRICK));

		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK = registerB("gold_trimmed_rhunic_brick", () -> new ExtendedStoneBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_SLAB = registerB("gold_trimmed_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS = registerB("gold_trimmed_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_WALL = registerB("gold_trimmed_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RHUNIC_BRICK));

		ExtendedBlocks.CRACKED_RHUNIC_BRICK = registerB("cracked_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB = registerB("cracked_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS = registerB("cracked_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL = registerB("cracked_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));

		ExtendedBlocks.MOSSY_RHUNIC_BRICK = registerB("mossy_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB = registerB("mossy_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS = registerB("mossy_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL = registerB("mossy_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));

		ExtendedBlocks.FLOWERY_RHUNIC_BRICK = registerB("flowery_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB = registerB("flowery_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS = registerB("flowery_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL = registerB("flowery_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));

		ExtendedBlocks.RED_RHUNIC_BRICK = registerB("red_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.RED_RHUNIC_BRICK_SLAB = registerB("red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS = registerB("red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_WALL = registerB("red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_PILLAR = registerB("red_rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB = registerB("red_rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RED_RHUNIC_BRICK = registerB("carved_red_rhunic_brick", () -> new ExtendedStoneBlock(ExtendedBlocks.RED_RHUNIC_BRICK));

		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK = registerB("cracked_red_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_SLAB = registerB("cracked_red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_STAIRS = registerB("cracked_red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_WALL = registerB("cracked_red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK));

		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK = registerB("mossy_red_rhunic_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_SLAB = registerB("mossy_red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_STAIRS = registerB("mossy_red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_WALL = registerB("mossy_red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK));

		ExtendedBlocks.ROHAN_BEAM = registerB("rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));
		ExtendedBlocks.ORNATE_ROHAN_BEAM = registerB("ornate_rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));
		ExtendedBlocks.GOLDEN_ROHAN_BEAM = registerB("golden_rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));
		ExtendedBlocks.ORNATE_GOLDEN_ROHAN_BEAM = registerB("ornate_golden_rohan_beam", () -> new LOTRPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.5F, 6.0F)));

		// Goblin
		ExtendedBlocks.GOBLIN_BRICK = registerB("goblin_brick", () -> new ExtendedStoneBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.GOBLIN_BRICK_SLAB = registerB("goblin_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOBLIN_BRICK));
		ExtendedBlocks.GOBLIN_BRICK_STAIRS = registerB("goblin_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GOBLIN_BRICK));
		ExtendedBlocks.GOBLIN_BRICK_WALL = registerB("goblin_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.GOBLIN_BRICK));
		ExtendedBlocks.GOBLIN_PILLAR = registerB("goblin_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.GOBLIN_PILLAR_SLAB = registerB("goblin_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOBLIN_PILLAR));
		//Gondor
		ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK = registerB("black_carved_gondor_brick", () -> new ExtendedStoneBlock(LOTRBlocks.NUMENOREAN_BRICK));
		ExtendedBlocks.CARVED_BLACK_UMBAR_BRICK = registerB("carved_black_umbar_brick", () -> new ExtendedStoneBlock(LOTRBlocks.NUMENOREAN_BRICK));
		ExtendedBlocks.CARVED_UMBAR_BRICK = registerB("carved_umbar_brick", () -> new ExtendedStoneBlock(LOTRBlocks.UMBAR_BRICK));
		ExtendedBlocks.SMOOTH_GONDOR_ROCK_STAIRS = registerB("smooth_gondor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_GONDOR_ROCK));

		//Rohan
		ExtendedBlocks.SMOOTH_ROHAN_ROCK_STAIRS = registerB("smooth_rohan_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_ROHAN_ROCK));

		//Blue Mountains
		ExtendedBlocks.SMOOTH_BLUE_ROCK_STAIRS = registerB("smooth_blue_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_BLUE_ROCK));

		//Red Mountains
		ExtendedBlocks.SMOOTH_RED_ROCK_STAIRS = registerB("smooth_red_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_RED_ROCK));

		//Mordor
		ExtendedBlocks.SMOOTH_MORDOR_ROCK_STAIRS = registerB("smooth_mordor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_MORDOR_ROCK));

		ExtendedBlocks.CROSSED_WATTLE_AND_DAUB = registerB("crossed_wattle_and_daub", () -> new ExtendedThreeTallPillarBlock(Properties.of(Material.GRASS, MaterialColor.QUARTZ).harvestTool(ToolType.AXE).sound(SoundType.SCAFFOLDING).strength(1.0F)));
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
		ExtendedBlocks.BLACKBERRY_HEDGE = registerB("blackberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.BLACKBERRY, ExtendedItems.BLACKBERRY_HEDGE));
		ExtendedBlocks.BLUEBERRY_HEDGE = registerB("blueberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.BLUEBERRY, ExtendedItems.BLUEBERRY_HEDGE));
		ExtendedBlocks.CRANBERRY_HEDGE = registerB("cranberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.CRANBERRY, ExtendedItems.CRANBERRY_HEDGE));
		ExtendedBlocks.ELDERBERRY_HEDGE = registerB("elderberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.ELDERBERRY, ExtendedItems.ELDERBERRY_HEDGE));
		ExtendedBlocks.RASPBERRY_HEDGE = registerB("raspberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.RASPBERRY, ExtendedItems.RASPBERRY_HEDGE));
		ExtendedBlocks.WILDBERRY_HEDGE = registerB("wildberry_bush", () -> new ExtendedBerryHedgeBlock(ExtendedItems.WILDBERRY, ExtendedItems.WILDBERRY_HEDGE));
		ExtendedBlocks.BLACKBERRY_BUSH = registerB("blackberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.BLACKBERRY, ExtendedBlocks.BLACKBERRY_HEDGE));
		ExtendedBlocks.BLUEBERRY_BUSH = registerB("blueberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.BLUEBERRY, ExtendedBlocks.BLUEBERRY_HEDGE));
		ExtendedBlocks.CRANBERRY_BUSH = registerB("cranberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.CRANBERRY, ExtendedBlocks.CRANBERRY_HEDGE));
		ExtendedBlocks.ELDERBERRY_BUSH = registerB("elderberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.ELDERBERRY, ExtendedBlocks.ELDERBERRY_HEDGE));
		ExtendedBlocks.RASPBERRY_BUSH = registerB("raspberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.RASPBERRY, ExtendedBlocks.RASPBERRY_HEDGE));
		ExtendedBlocks.WILDBERRY_BUSH = registerB("wildberry_plant", () -> new ExtendedBerryBushBlock(ExtendedItems.WILDBERRY, ExtendedBlocks.WILDBERRY_HEDGE));
		
		//CRAFTING TABLES
		ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE = registerB("taurethrim_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Taurethrim(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE = registerB("dol_guldur_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Dol_Guldur(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.RHUNIC_CRAFTING_TABLE = registerB("rhunic_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Rhunic(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE = registerB("half_troll_crafting_table", () -> new ExtendedFactionCraftingTableBlock.HalfTroll(()-> Blocks.COBBLESTONE));
		ExtendedBlocks.NORTHERN_ORC_CRAFTING_TABLE = registerB("gundabad_crafting_table", () -> new ExtendedFactionCraftingTableBlock.NorthernOrc(ExtendedBlocks.GOBLIN_BRICK));
		ExtendedBlocks.MORWAITH_CRAFTING_TABLE = registerB("morwaith_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Morwaith(()-> Blocks.TERRACOTTA));

		ExtendedBlocks.CONQUEST_TABLE = registerB("conquest_table", () -> new ExtendedConquestTableBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.5F)));
		ExtendedBlocks.STONE_GRINDER = registerB("stone_grinder", () -> new ExtendedStoneGrinderBlock(Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(4.0f)));
		ExtendedBlocks.CRUCIBLE = registerB("crucible", () -> new ExtendedCrucibleBlock(Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(4.0F, 6.0F)));

		ExtendedBlocks.MORGUL_BRAZIER = registerB("morgul_brazier", () -> new ExtendedBrazierBlock(12));
		ExtendedBlocks.MORGUL_CHANDELIER = registerB("morgul_chandelier", () -> new ExtendedChandelierBlock(ExtendedParticles.MORGUL_FLAME));
		ExtendedBlocks.ORC_CHANDELIER = registerB("orc_chandelier", ChandelierBlock::new);
		ExtendedBlocks.URUK_CHANDELIER = registerB("uruk_chandelier", ChandelierBlock::new);
		ExtendedBlocks.DWARVEN_CHANDELIER = registerB("dwarven_chandelier", ChandelierBlock::new);
		ExtendedBlocks.BLUE_DWARVEN_CHANDELIER = registerB("blue_dwarven_chandelier", ChandelierBlock::new);
		ExtendedBlocks.HIGH_ELVEN_LANTERN = registerB("high_elven_lantern", () -> new ExtendedElvenLanternBlock(12));

		ExtendedBlocks.ORC_STEEL_DOOR = registerB("orc_steel_door", () -> new ExtendedDoorBlock(Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).noOcclusion()));
		ExtendedBlocks.ORC_STEEL_TRAPDOOR = registerB("orc_steel_trapdoor", () -> new ExtendedTrapDoorBlock(Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).noOcclusion()));
		ExtendedBlocks.ORC_CHAIN = registerB("orc_chain", () -> new ExtendedChainBlock(Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion()));

		ExtendedBlocks.MORGUL_TORCH = registerB("morgul_torch", () -> new ExtendedLOTRTorchBlock(12).setParticles(ExtendedParticles.MORGUL_FLAME, () -> ParticleTypes.SMOKE));
		ExtendedBlocks.MORGUL_WALL_TORCH = registerB("morgul_torch_wall", () -> new ExtendedLOTRWallTorchBlock(ExtendedBlocks.MORGUL_TORCH));
		ExtendedBlocks.GREEN_FLAME_HEARTH_BLOCK = registerB("green_fire_hearth_block", ExtendedGreenHearthBlock::new);
		ExtendedBlocks.GREEN_FIRE_BLOCK = registerB("green_fire", () -> new ExtendedGreenFireBlock(Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN).noCollission().instabreak().lightLevel((blockState) -> 10).sound(SoundType.WOOL)));
		ExtendedBlocks.WASTE_BLOCK = registerB("waste_block", () -> new ExtendedWasteBlock(AbstractBlock.Properties.of(Material.SAND, MaterialColor.COLOR_BROWN).strength(1.0F).speedFactor(0.4F).harvestTool(ToolType.SHOVEL).sound(SoundType.GRASS).isValidSpawn(ExtendedBlockParms::always).isRedstoneConductor(ExtendedBlockParms::always).isViewBlocking(ExtendedBlockParms::always).isSuffocating(ExtendedBlockParms::always)));
		for(ExtendedEntitySpawnTypes type : ExtendedEntitySpawnTypes.values()) {
			ExtendedBlocks.ENTITYSPAWNERBLOCKS.put(type.getSerializedName(), registerB("entity_spawner_"+type.getSerializedName(), () -> new ExtendedSpawnerBlock(Properties.of(Material.STONE).strength(-1.0F).noCollission().noDrops(), type)));
		}
		for(ExtendedEntitySpawnGroupTypes type : ExtendedEntitySpawnGroupTypes.values()) {
			ExtendedBlocks.ENTITYGROUPSPAWNERBLOCKS.put(type.getSerializedName(), registerB("group_entity_spawner_"+type.getSerializedName(), () -> new ExtendedGroupSpawnerBlock(Properties.of(Material.STONE).strength(-1.0F).noCollission().noDrops(), type)));
		}
		ExtendedBlocks.CHEESE_WHEEL = registerB("cheese_wheel", () -> new ExtendedCheeseWheelBlock(Properties.copy(Blocks.CAKE), 1.0F, ExtendedItems.CHEESE_SLICE, ExtendedCheeseState.UNAFFECTED));
		ExtendedBlocks.AGED_CHEESE_WHEEL = registerB("aged_cheese_wheel", () -> new ExtendedCheeseWheelBlock(Properties.copy(Blocks.CAKE), 1.0F, ExtendedItems.AGED_CHEESE_SLICE, ExtendedCheeseState.AGED));

		ExtendedBlocks.BANANA_CAKE = registerB("banana_cake", () -> new ExtendedCakeBlock(Properties.copy(Blocks.CAKE), 1.0F));

		ExtendedBlocks.STONE_CHEST = registerB("stone_chest", () -> new ExtendedStoneChestBlock(ExtendedStoneChestTileEntity::new));
		ExtendedBlocks.REED_BASKET = registerB("reed_basket", () -> new ExtendedReedBasketBlock(Properties.of(Material.GRASS).sound(SoundType.GRASS).strength(0.5F).noOcclusion()));
		ExtendedBlocks.ORC_BARREL = registerB("orc_barrel", () -> new ExtendedOrcBarrelBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.5F).noOcclusion()));
		ExtendedBlocks.WEAPON_RACK = registerB("weapon_rack", () -> new ExtendedWeaponRackBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.5F).noCollission()));

		ExtendedBlocks.STRAW_BED = registerB("straw_bed", () -> new ExtendedStrawBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
		ExtendedBlocks.ORC_BED = registerB("orc_bed", () -> new ExtendedOrcBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.BLACK_FUR_BED = registerB("black_fur_bed", () -> new ExtendedBlackFurBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.FUR_BED = registerB("fur_bed", () -> new ExtendedFurBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.GRAY_FUR_BED = registerB("gray_fur_bed", () -> new ExtendedGrayFurBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.SILVER_FUR_BED = registerB("silver_fur_bed", () -> new ExtendedSilverFurBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.WHITE_FUR_BED = registerB("white_fur_bed", () -> new ExtendedWhiteFurBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.BEDROLL = registerB("bedroll", () -> new ExtendedBedrollBlock(Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion()));
		ExtendedBlocks.DWARVEN_BED = registerB("dwarven_bed", () -> new ExtendedDwarvenBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
		ExtendedBlocks.HIGH_ELVEN_BED = registerB("high_elven_bed", () -> new ExtendedHighElvenBedBlock(Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));

		ExtendedBlocks.ROPE = registerB("rope", () -> new ExtendedRopeBlock(Properties.of(Material.DECORATION).sound(ExtendedSoundTypes.ROPE).strength(0.2F).noOcclusion()));

		ExtendedBlocks.SKULL_GOBLET = registerB("skull_goblet", () -> new VesselDrinkBlock(SoundType.BONE_BLOCK, 8.0F, 10.0F));
		ExtendedBlocks.GLASS_BOTTLE = registerB("glass_bottle", () -> new ExtendedBottleVesselDrinkBlock(Properties.of(Material.DECORATION).strength(0.0F).sound(SoundType.GLASS).noOcclusion(), createVesselShape(5, 7)));
		ExtendedBlocks.LARGE_POT = registerB("large_pot", () -> new ExtendedLargePotBlock(Properties.of(Material.DECORATION).instabreak().noOcclusion()));
		ExtendedBlocks.CHIMNEYPOT = registerB("chimney_pot", () -> new ExtendedChimneyPotBlock(Properties.of(Material.DECORATION).instabreak().noOcclusion().randomTicks()));
		// Large flower pot
		ExtendedBlocks.LARGE_FLOWER_POT = registerB("large_flower_pot", ExtendedLargeFlowerPotBlock::new);
		registerPOT("large_pot_apple_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.APPLE_SAPLING));
		registerPOT("large_pot_aspen_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ASPEN_SAPLING));
		registerPOT("large_pot_asphodel", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ASPHODEL));
		registerPOT("large_pot_athelas", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ATHELAS));
		registerPOT("large_pot_beech_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.BEECH_SAPLING));
		registerPOT("large_pot_blackroot", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.BLACKROOT));
		registerPOT("large_pot_bluebell", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.BLUEBELL));
		registerPOT("large_pot_cedar_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CEDAR_SAPLING));
		registerPOT("large_pot_cherry_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CHERRY_SAPLING));
		ExtendedBlocks.LARGE_FLOWER_POT_CLOVER = registerPOT("large_pot_clover", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CLOVER));
		registerPOT("large_pot_culumalda_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CULUMALDA_SAPLING));
		registerPOT("large_pot_cypress_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.CYPRESS_SAPLING));
		registerPOT("large_pot_dwarfwort", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.DWARFWORT));
		registerPOT("large_pot_elanor", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ELANOR));
		ExtendedBlocks.LARGE_FLOWER_POT_FERNSPROUT = registerPOT("large_pot_fernsprout", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.FERNSPROUT));
		registerPOT("large_pot_fir_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.FIR_SAPLING));
		ExtendedBlocks.LARGE_FLOWER_POT_FOUR_CLOVER = registerPOT("large_pot_four_leaf_clover", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.FOUR_LEAF_CLOVER));
		registerPOT("large_pot_green_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.GREEN_OAK_SAPLING));
		registerPOT("large_pot_harad_daisy", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.HARAD_DAISY));
		registerPOT("large_pot_holly_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.HOLLY_SAPLING));
		registerPOT("large_pot_lairelosse_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LAIRELOSSE_SAPLING));
		registerPOT("large_pot_larch_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LARCH_SAPLING));
		registerPOT("large_pot_lavender", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LAVENDER));
		registerPOT("large_pot_lebethron_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.LEBETHRON_SAPLING));
		registerPOT("large_pot_mallorn_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MALLORN_SAPLING));
		registerPOT("large_pot_mallos", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MALLOS));
		registerPOT("large_pot_maple_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MAPLE_SAPLING));
		registerPOT("large_pot_marigold", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MARIGOLD));
		registerPOT("large_pot_mirk_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MIRK_OAK_SAPLING));
		registerPOT("large_pot_mirk_shroom", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MIRK_SHROOM));
		registerPOT("large_pot_mordor_thorn", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MORDOR_THORN));
		registerPOT("large_pot_morgum_flower", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MORGUL_FLOWER));
		registerPOT("large_pot_morgul_shroom", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.MORGUL_SHROOM));
		ExtendedBlocks.LARGE_FLOWER_POT_NETTLES = registerPOT("large_pot_nettles", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.NETTLES));
		registerPOT("large_pot_niphredil", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.NIPHREDIL));
		registerPOT("large_pot_orange_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.ORANGE_CHRYSANTHEMUM));
		registerPOT("large_pot_pear_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.PEAR_SAPLING));
		registerPOT("large_pot_pine_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.PINE_SAPLING));
		registerPOT("large_pot_pink_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.PINK_CHRYSANTHEMUM));
		registerPOT("large_pot_red_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.RED_CHRYSANTHEMUM));
		registerPOT("large_pot_red_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.RED_OAK_SAPLING));
		registerPOT("large_pot_red_sand_gem", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.RED_SAND_GEM));
		registerPOT("large_pot_shire_heather", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.SHIRE_HEATHER));
		registerPOT("large_pot_simbelmyne", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.SIMBELMYNE));
		registerPOT("large_pot_southbell", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.SOUTHBELL));
		ExtendedBlocks.LARGE_FLOWER_POT_THISTLE = registerPOT("large_pot_thistle", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.THISTLE));
		registerPOT("large_pot_white_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.WHITE_CHRYSANTHEMUM));
		registerPOT("large_pot_wild_flax", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.WILD_FLAX));
		registerPOT("large_pot_wild_pipeweed", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.WILD_PIPEWEED));
		registerPOT("large_pot_yellow_chrysanthemum", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.YELLOW_CHRYSANTHEMUM));
		registerPOT("large_pot_yellow_sand_gem", () -> new ExtendedLargeFlowerPotBlock(LOTRBlocks.YELLOW_SAND_GEM));
		registerPOT("large_pot_acacia_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.ACACIA_SAPLING));
		registerPOT("large_pot_allium", () -> new ExtendedLargeFlowerPotBlock(Blocks.ALLIUM));
		registerPOT("large_pot_azure_bluet", () -> new ExtendedLargeFlowerPotBlock(Blocks.AZURE_BLUET));
		registerPOT("large_pot_bamboo", () -> new ExtendedLargeFlowerPotBlock(Blocks.BAMBOO));
		registerPOT("large_pot_birch_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.BIRCH_SAPLING));
		registerPOT("large_pot_blue_orchid", () -> new ExtendedLargeFlowerPotBlock(Blocks.BLUE_ORCHID));
		registerPOT("large_pot_brown_mushroom", () -> new ExtendedLargeFlowerPotBlock(Blocks.BROWN_MUSHROOM));
		registerPOT("large_pot_cactus", () -> new ExtendedLargeFlowerPotBlock(Blocks.CACTUS));
		registerPOT("large_pot_cornflower", () -> new ExtendedLargeFlowerPotBlock(Blocks.CORNFLOWER));
		registerPOT("large_pot_crimson_fungus", () -> new ExtendedLargeFlowerPotBlock(Blocks.CRIMSON_FUNGUS));
		registerPOT("large_pot_crimson_roots", () -> new ExtendedLargeFlowerPotBlock(Blocks.CRIMSON_ROOTS));
		registerPOT("large_pot_dandelion", () -> new ExtendedLargeFlowerPotBlock(Blocks.DANDELION));
		registerPOT("large_pot_dark_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.DARK_OAK_SAPLING));
		registerPOT("large_pot_dead_bush", () -> new ExtendedLargeFlowerPotBlock(Blocks.DEAD_BUSH));
		ExtendedBlocks.LARGE_FLOWER_POT_FERN = registerPOT("large_pot_fern", () -> new ExtendedLargeFlowerPotBlock(Blocks.FERN));
		registerPOT("large_pot_jungle_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.JUNGLE_SAPLING));
		registerPOT("large_pot_lily_of_the_valley", () -> new ExtendedLargeFlowerPotBlock(Blocks.LILY_OF_THE_VALLEY));
		registerPOT("large_pot_oak_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.OAK_SAPLING));
		registerPOT("large_pot_orange_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.ORANGE_TULIP));
		registerPOT("large_pot_oxeye_daisy", () -> new ExtendedLargeFlowerPotBlock(Blocks.OXEYE_DAISY));
		registerPOT("large_pot_pink_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.PINK_TULIP));
		registerPOT("large_pot_poppy", () -> new ExtendedLargeFlowerPotBlock(Blocks.POPPY));
		registerPOT("large_pot_red_mushroom", () -> new ExtendedLargeFlowerPotBlock(Blocks.RED_MUSHROOM));
		registerPOT("large_pot_red_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.RED_TULIP));
		registerPOT("large_pot_spruce_sapling", () -> new ExtendedLargeFlowerPotBlock(Blocks.SPRUCE_SAPLING));
		registerPOT("large_pot_warped_fungus", () -> new ExtendedLargeFlowerPotBlock(Blocks.WARPED_FUNGUS));
		registerPOT("large_pot_warped_roots", () -> new ExtendedLargeFlowerPotBlock(Blocks.WARPED_ROOTS));
		registerPOT("large_pot_white_tulip", () -> new ExtendedLargeFlowerPotBlock(Blocks.WHITE_TULIP));
		registerPOT("large_pot_wither_rose", () -> new ExtendedLargeFlowerPotBlock(Blocks.WITHER_ROSE));

		//Banners
		ExtendedBlocks.DUNEDAIN_BANNER = registerB("dunedain_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DUNEDAIN));
		ExtendedBlocks.DUNEDAIN_WALL_BANNER = registerB("dunedain_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DUNEDAIN));
		ExtendedBlocks.HOBBIT_BANNER = registerB("hobbit_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.SHIRE));
		ExtendedBlocks.HOBBIT_WALL_BANNER = registerB("hobbit_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.SHIRE));
		ExtendedBlocks.DURINS_FOLK_BANNER = registerB("durins_folk_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DURINSFOLK));
		ExtendedBlocks.DURINS_FOLK_WALL_BANNER = registerB("durins_folk_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DURINSFOLK));
		ExtendedBlocks.BLUE_MOUNTAINS_BANNER = registerB("blue_mountains_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.BLUEMOUNTIANS));
		ExtendedBlocks.BLUE_MOUNTAINS_WALL_BANNER = registerB("blue_mountains_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.BLUEMOUNTIANS));
		ExtendedBlocks.GUNDABAD_ORC_BANNER = registerB("gundabad_orc_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.GUNDABAD_ORC));
		ExtendedBlocks.GUNDABAD_ORC_WALL_BANNER = registerB("gundabad_orc_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.GUNDABAD_ORC));
		ExtendedBlocks.BREE_BANNER = registerB("bree_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.BREE));
		ExtendedBlocks.BREE_WALL_BANNER = registerB("bree_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.BREE));
		ExtendedBlocks.ANGMAR_BANNER = registerB("angmar_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.ANGMAR));
		ExtendedBlocks.ANGMAR_WALL_BANNER = registerB("angmar_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.ANGMAR));
		ExtendedBlocks.DALE_BANNER = registerB("dale_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DALE));
		ExtendedBlocks.DALE_WALL_BANNER = registerB("dale_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DALE));
		ExtendedBlocks.DOL_AMROTH_BANNER = registerB("dol_amroth_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DOL_AMROTH));
		ExtendedBlocks.DOL_AMROTH_WALL_BANNER = registerB("dol_amroth_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DOL_AMROTH));
		ExtendedBlocks.DOL_GULDUR_BANNER = registerB("dol_guldur_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DOL_GULDUR));
		ExtendedBlocks.DOL_GULDUR_WALL_BANNER = registerB("dol_guldur_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DOL_GULDUR));
		ExtendedBlocks.DORWINION_BANNER = registerB("dorwinion_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DORWINION));
		ExtendedBlocks.DORWINION_WALL_BANNER = registerB("dorwinion_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DORWINION));
		ExtendedBlocks.DUNLAND_BANNER = registerB("dunland_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.DUNLAND));
		ExtendedBlocks.DUNLAND_WALL_BANNER = registerB("dunland_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.DUNLAND));
		ExtendedBlocks.GONDOR_BANNER = registerB("gondor_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.GONDOR));
		ExtendedBlocks.GONDOR_WALL_BANNER = registerB("gondor_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.GONDOR));
		ExtendedBlocks.ISENGARD_BANNER = registerB("isengard_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.ISENGARD));
		ExtendedBlocks.ISENGARD_WALL_BANNER = registerB("isengard_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.ISENGARD));
		ExtendedBlocks.LINDON_BANNER = registerB("lindon_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.LINDON));
		ExtendedBlocks.LINDON_WALL_BANNER = registerB("lindon_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.LINDON));
		ExtendedBlocks.LOTHLORIEN_BANNER = registerB("lothlorien_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.LOTHLORIEN));
		ExtendedBlocks.LOTHLORIEN_WALL_BANNER = registerB("lothlorien_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.LOTHLORIEN));
		ExtendedBlocks.MORDOR_BANNER = registerB("mordor_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.MORDOR));
		ExtendedBlocks.MORDOR_WALL_BANNER = registerB("mordor_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.MORDOR));
		ExtendedBlocks.RIVENDELL_BANNER = registerB("rivendell_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.RIVENDELL));
		ExtendedBlocks.RIVENDELL_WALL_BANNER = registerB("rivendell_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.RIVENDELL));
		ExtendedBlocks.ROHAN_BANNER = registerB("rohan_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.ROHAN));
		ExtendedBlocks.ROHAN_WALL_BANNER = registerB("rohan_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.ROHAN));
		ExtendedBlocks.WOODLAND_REALM_BANNER = registerB("woodland_realm_banner", () -> new ExtendedMEBannerBlock(ExtendedBannerTypes.WOODLAND_REALM));
		ExtendedBlocks.WOODLAND_REALM_WALL_BANNER = registerB("woodland_realm_wall_banner", () -> new ExtendedMEBannerWallBlock(ExtendedBannerTypes.WOODLAND_REALM));

		
		ExtendedBlocks.DEAD_MOSS_VINES = registerB("dead_moss_vines", () -> new ExtendedVineBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE)));
		// Birdcages
		ExtendedBlocks.WOODEN_BIRDCAGE = registerB("wooden_birdcage", () -> new ExtendedBirdcageBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).strength(0.5F)));
		ExtendedBlocks.BRONZE_BIRDCAGE = registerB("bronze_birdcage", () -> new ExtendedBirdcageBlock(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(1.0F)));
		ExtendedBlocks.IRON_BIRDCAGE = registerB("iron_birdcage", () -> new ExtendedBirdcageBlock(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(2.0F)));
		ExtendedBlocks.SILVER_BIRDCAGE = registerB("silver_birdcage", () -> new ExtendedBirdcageBlock(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(3.0F)));
		ExtendedBlocks.GOLDEN_BIRDCAGE = registerB("golden_birdcage", () -> new ExtendedBirdcageBlock(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(4.0F)));
		ExtendedBlocks.MITHRIL_BIRDCAGE = registerB("mithril_birdcage", () -> new ExtendedBirdcageBlock(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).strength(5.0f)));

		ExtendedBlocks.GRASS_VOID = registerB("grass_void", () -> new ExtendedGrassVoidBlock(AbstractBlock.Properties.of(Material.STRUCTURAL_AIR).noCollission().noDrops().isValidSpawn(ExtendedBlockParms::always)));

		ExtendedBlocks.DWARVEN_DOOR = registerB("dwarven_door", () -> new ExtendedDwarvenGateBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 10.0F).sound(SoundType.STONE).noOcclusion(), false));
		ExtendedBlocks.DWARVEN_DOOR_LUIGON = registerB("blue_dwarven_door", () -> new ExtendedDwarvenGateBlock(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 10.0F).sound(SoundType.STONE).noOcclusion(), false));
		ExtendedBlocks.DWARVEN_DOOR_CARGON = registerB("red_dwarven_door", () -> new ExtendedDwarvenGateBlock(Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(4.0F, 10.0F).sound(SoundType.STONE).noOcclusion(), false));

		ExtendedBlocks.EDHELVIR_CRYSTAL_CHANDELIER = registerB("edhelvir_crystal_chandelier", () -> new ExtendedCrystalChandelierBlock(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).lightLevel((s) -> 20).sound(SoundType.STONE).noOcclusion()));
		ExtendedBlocks.GLOWSTONE_CRYSTAL_CHANDELIER = registerB("glowstone_crystal_chandelier", () -> new ExtendedCrystalChandelierBlock(Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).lightLevel((s) -> 20).sound(SoundType.STONE).noOcclusion()));
		ExtendedBlocks.GULDURIL_CRYSTAL_CHANDELIER = registerB("gulduril_crystal_chandelier", () -> new ExtendedCrystalChandelierBlock(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GREEN).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).lightLevel((s) -> 20).sound(SoundType.STONE).noOcclusion()));

		ExtendedBlocks.STONE_SIGN = registerB("stone_engraving", () -> new ExtendedStoneTextSignBlock(AbstractBlock.Properties.of(Material.DECORATION, MaterialColor.NONE).noCollission().strength(0.5F).sound(SoundType.STONE)));
		ExtendedBlocks.HOIST = registerB("mine_placeholder", () -> new ExtendedHoistBlock(AbstractBlock.Properties.of(Material.STONE).strength(4.0F, 10.0F).sound(SoundType.STONE)));

		ExtendedBlocks.HIGH_ELVEN_CHAIN = registerB("high_elven_chain", () -> new ExtendedChainBlock(Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion()));
		ExtendedBlocks.SHARDS_OF_NARSIL = registerB("shards_of_narsil", () -> new ExtendedNarsilShardsBlock(Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.5F).noOcclusion()));

		ExtendedBlocks.PLACED_BOOKS = registerB("placed_books", () -> new ExtendedPlacedBooksBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.5F).noOcclusion()));
		ExtendedBlocks.EXTENDED_BOOKSHELF = registerB("bookshelf", () -> new ExtendedBookshelfBlock(AbstractBlock.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD)));
	}

	/**
	 * @reason Need to setup biome features when/after block registration
	 * @Inject (method = "register()V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, remap=false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * @param bus A buss to use for registration
	 * */
	@Inject(method = "register()V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, remap=false)
	private static void bootstrapExtendedBiomeFeatures(CallbackInfo ci, IEventBus bus) {
		bus.addGenericListener(Block.class, ExtendedBiomeFeatures::setup);
	}

	private static VoxelShape createVesselShape(float width, float height) {
		float halfWidth = width / 2.0F;
		return Block.box(8.0D - halfWidth, 0.0D, 8.0D - halfWidth, 8.0D + halfWidth, height, 8.0D + halfWidth);
	}
}
