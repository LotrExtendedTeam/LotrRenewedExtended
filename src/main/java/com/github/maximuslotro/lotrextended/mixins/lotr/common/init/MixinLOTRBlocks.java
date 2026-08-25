package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

import lotr.common.block.*;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRParticles;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Used to replace blocks registered by Renewed
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
            case "chalk_slab":
            	// Allows chalk slabs to be turned into chalk path slabs
            	returnBlock = BLOCKS.register(name, () -> new ExtendedAxialSlabDirtBlock(LOTRBlocks.CHALK.get(), ExtendedBlocks.CHALK_PATH_SLAB));
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
}
