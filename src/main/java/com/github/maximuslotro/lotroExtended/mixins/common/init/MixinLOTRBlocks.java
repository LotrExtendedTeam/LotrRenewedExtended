package com.github.maximuslotro.lotroExtended.mixins.common.init;

import lotr.common.block.*;

import lotr.common.init.ExtendedItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;

import static lotr.common.init.LOTRBlocks.constantLight;

@Mixin(LOTRBlocks.class)
public class MixinLOTRBlocks {
	
	@Final
	@Shadow(remap=false)
	public static DeferredRegister<Block> BLOCKS;
	
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		//ORE STORAGE
		ExtendedBlocks.GALVORN_BLOCK = BLOCKS.register("galvorn_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(4.0F, 8.0F).sound(SoundType.METAL), 2);});
		ExtendedBlocks.BLACK_URUK_STEEL_BLOCK = BLOCKS.register("black_uruk_steel_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
		ExtendedBlocks.GILDED_IRON_BLOCK = BLOCKS.register("gilded_iron_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
		ExtendedBlocks.MORGUL_STEEL_BLOCK = BLOCKS.register("morgul_steel_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});

		//Packed Fur
		ExtendedBlocks.WHITE_PACKED_FUR = BLOCKS.register("white_packed_fur", () -> new BundleBlock(MaterialColor.WOOL));
		ExtendedBlocks.WHITE_PACKED_FUR_SLAB = BLOCKS.register("white_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		ExtendedBlocks.WHITE_PACKED_FUR_STAIRS = BLOCKS.register("white_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		ExtendedBlocks.WHITE_PACKED_FUR_WALL = BLOCKS.register("white_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		
		ExtendedBlocks.SILVER_PACKED_FUR = BLOCKS.register("silver_packed_fur", () -> new BundleBlock(MaterialColor.COLOR_LIGHT_GRAY));
		ExtendedBlocks.SILVER_PACKED_FUR_SLAB = BLOCKS.register("silver_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		ExtendedBlocks.SILVER_PACKED_FUR_STAIRS = BLOCKS.register("silver_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		ExtendedBlocks.SILVER_PACKED_FUR_WALL = BLOCKS.register("silver_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		
		ExtendedBlocks.GREY_PACKED_FUR = BLOCKS.register("grey_packed_fur", () -> new BundleBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.GREY_PACKED_FUR_SLAB = BLOCKS.register("grey_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.GREY_PACKED_FUR));
		ExtendedBlocks.GREY_PACKED_FUR_STAIRS = BLOCKS.register("grey_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GREY_PACKED_FUR));
		ExtendedBlocks.GREY_PACKED_FUR_WALL = BLOCKS.register("grey_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.GREY_PACKED_FUR));
		
		ExtendedBlocks.BROWN_PACKED_FUR = BLOCKS.register("packed_fur", () -> new BundleBlock(MaterialColor.COLOR_BROWN));
		ExtendedBlocks.BROWN_PACKED_FUR_SLAB = BLOCKS.register("packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.BROWN_PACKED_FUR));
		ExtendedBlocks.BROWN_PACKED_FUR_STAIRS = BLOCKS.register("packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.BROWN_PACKED_FUR));
		ExtendedBlocks.BROWN_PACKED_FUR_WALL = BLOCKS.register("packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.BROWN_PACKED_FUR));
		
		ExtendedBlocks.BLACK_PACKED_FUR = BLOCKS.register("black_packed_fur", () -> new BundleBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.BLACK_PACKED_FUR_SLAB = BLOCKS.register("black_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.BLACK_PACKED_FUR));
		ExtendedBlocks.BLACK_PACKED_FUR_STAIRS = BLOCKS.register("black_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.BLACK_PACKED_FUR));
		ExtendedBlocks.BLACK_PACKED_FUR_WALL = BLOCKS.register("black_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.BLACK_PACKED_FUR));
		
		//Bundled Fur
		ExtendedBlocks.WHITE_FUR_BUNDLE = BLOCKS.register("white_fur_bundle", () -> new BundleBlock(MaterialColor.WOOL));
		ExtendedBlocks.SILVER_FUR_BUNDLE = BLOCKS.register("silver_fur_bundle", () -> new BundleBlock(MaterialColor.COLOR_LIGHT_GRAY));
		ExtendedBlocks.GREY_FUR_BUNDLE = BLOCKS.register("grey_fur_bundle", () -> new BundleBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.BLACK_FUR_BUNDLE = BLOCKS.register("black_fur_bundle", () -> new BundleBlock(MaterialColor.COLOR_BLACK));

		//GEM STORAGE
		ExtendedBlocks.TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.AMETHYST_BLOCK = BLOCKS.register("amethyst_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.SAPPHIRE_BLOCK = BLOCKS.register("sapphire_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.AMBER_BLOCK = BLOCKS.register("amber_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.DIAMOND_BLOCK = BLOCKS.register("diamond_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.OPAL_BLOCK = BLOCKS.register("opal_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.EMERALD_BLOCK = BLOCKS.register("emerald_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.CORAL_BLOCK = BLOCKS.register("coral_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));
		ExtendedBlocks.PEARL_BLOCK = BLOCKS.register("pearl_block", () -> new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1));

		//GEM ORES
		ExtendedBlocks.AMBER_ORE = BLOCKS.register("amber_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.AMETHYST_ORE = BLOCKS.register("amethyst_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.SAPPHIRE_ORE = BLOCKS.register("sapphire_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.RUBY_ORE = BLOCKS.register("ruby_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.DIAMOND_ORE = BLOCKS.register("diamond_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.OPAL_ORE = BLOCKS.register("opal_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.EMERALD_ORE = BLOCKS.register("emerald_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));

		///////////////////////////////BRICKS
		//Taurethrim
		ExtendedBlocks.TAURETHRIM_BRICK = BLOCKS.register("taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.TAURETHRIM_BRICK_SLAB = BLOCKS.register("taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_BRICK_STAIRS = BLOCKS.register("taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_BRICK_WALL = BLOCKS.register("taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.TAURETHRIM_PILLAR = BLOCKS.register("taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.TAURETHRIM_PILLAR_SLAB = BLOCKS.register("taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.TAURETHRIM_PILLAR));
		
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK = BLOCKS.register("mossy_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB = BLOCKS.register("mossy_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS = BLOCKS.register("mossy_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL = BLOCKS.register("mossy_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK));
		
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK = BLOCKS.register("cracked_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_GREEN));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB = BLOCKS.register("cracked_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS = BLOCKS.register("cracked_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL = BLOCKS.register("cracked_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK));
		
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK = BLOCKS.register("golden_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB = BLOCKS.register("golden_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS = BLOCKS.register("golden_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL = BLOCKS.register("golden_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK));
		ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR = BLOCKS.register("golden_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.GOLD));
		ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB = BLOCKS.register("golden_taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR));

		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK = BLOCKS.register("obsidian_taurethrim_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB = BLOCKS.register("obsidian_taurethrim_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS = BLOCKS.register("obsidian_taurethrim_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL = BLOCKS.register("obsidian_taurethrim_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR = BLOCKS.register("obsidian_taurethrim_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB = BLOCKS.register("obsidian_taurethrim_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR));
		
		//Dol Guldur
		ExtendedBlocks.DOL_GULDUR_BRICK = BLOCKS.register("dol_guldur_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.DOL_GULDUR_BRICK_SLAB = BLOCKS.register("dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS = BLOCKS.register("dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_BRICK_WALL = BLOCKS.register("dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.DOL_GULDUR_PILLAR = BLOCKS.register("dol_guldur_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB = BLOCKS.register("dol_guldur_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.DOL_GULDUR_PILLAR));
		
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK = BLOCKS.register("mossy_dol_guldur_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB = BLOCKS.register("mossy_dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS = BLOCKS.register("mossy_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL = BLOCKS.register("mossy_dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK));
		
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK = BLOCKS.register("cracked_dol_guldur_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_BLACK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB = BLOCKS.register("cracked_dol_guldur_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS = BLOCKS.register("cracked_dol_guldur_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL = BLOCKS.register("cracked_dol_guldur_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK));
		
		ExtendedBlocks.CARVED_DOL_GULDUR_BRICK = BLOCKS.register("carved_dol_guldur_brick", () -> new LOTRStoneBlock(ExtendedBlocks.DOL_GULDUR_BRICK));

		//Rhunic
		ExtendedBlocks.RHUNIC_BRICK = BLOCKS.register("rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RHUNIC_BRICK_SLAB = BLOCKS.register("rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_BRICK_STAIRS = BLOCKS.register("rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_BRICK_WALL = BLOCKS.register("rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.RHUNIC_PILLAR = BLOCKS.register("rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RHUNIC_PILLAR_SLAB = BLOCKS.register("rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RHUNIC_BRICK = BLOCKS.register("carved_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RHUNIC_BRICK));
		ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK = BLOCKS.register("gold_trimmed_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RHUNIC_BRICK));

		ExtendedBlocks.CRACKED_RHUNIC_BRICK = BLOCKS.register("cracked_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB = BLOCKS.register("cracked_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS = BLOCKS.register("cracked_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));
		ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL = BLOCKS.register("cracked_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.CRACKED_RHUNIC_BRICK));

		ExtendedBlocks.MOSSY_RHUNIC_BRICK = BLOCKS.register("mossy_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB = BLOCKS.register("mossy_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS = BLOCKS.register("mossy_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));
		ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL = BLOCKS.register("mossy_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_RHUNIC_BRICK));

		ExtendedBlocks.FLOWERY_RHUNIC_BRICK = BLOCKS.register("flowery_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB = BLOCKS.register("flowery_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS = BLOCKS.register("flowery_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL = BLOCKS.register("flowery_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.FLOWERY_RHUNIC_BRICK));
		
		ExtendedBlocks.RED_RHUNIC_BRICK = BLOCKS.register("red_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RED_RHUNIC_BRICK_SLAB = BLOCKS.register("red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS = BLOCKS.register("red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_WALL = BLOCKS.register("red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_PILLAR = BLOCKS.register("red_rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB = BLOCKS.register("red_rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RED_RHUNIC_BRICK = BLOCKS.register("carved_red_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RED_RHUNIC_BRICK));

		//Drytone
		ExtendedBlocks.DRYSTONE_SLAB = BLOCKS.register("drystone_slab", () -> new AxialSlabBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.DRYSTONE_STAIRS = BLOCKS.register("drystone_stairs", () -> new LOTRStairsBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.DRYSTONE_WALL = BLOCKS.register("drystone_wall", () -> new LOTRWallBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.COBBLED_DRYSTONE = BLOCKS.register("cobbled_drystone", () -> new LOTRStoneBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.CUT_DRYSTONE = BLOCKS.register("cut_drystone", () -> new LOTRStoneBlock(LOTRBlocks.DRYSTONE));
		
		ExtendedBlocks.MOSSY_DRYSTONE = BLOCKS.register("mossy_drystone", () -> new LOTRStoneBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.MOSSY_DRYSTONE_SLAB = BLOCKS.register("mossy_drystone_slab", () -> new AxialSlabBlock(ExtendedBlocks.MOSSY_DRYSTONE));
		ExtendedBlocks.MOSSY_DRYSTONE_STAIRS = BLOCKS.register("mossy_drystone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.MOSSY_DRYSTONE));
		ExtendedBlocks.MOSSY_DRYSTONE_WALL = BLOCKS.register("mossy_drystone_wall", () -> new LOTRWallBlock(ExtendedBlocks.MOSSY_DRYSTONE));
		
		//BUSHES
		ExtendedBlocks.RASPBERRY_BUSH = BLOCKS.register("raspberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.RASPBERRY, ExtendedItems.RASPBERRY_BUSH));
		ExtendedBlocks.BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.BLUEBERRY, ExtendedItems.BLUEBERRY_BUSH));
		ExtendedBlocks.BLACKBERRY_BUSH = BLOCKS.register("blackberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.BLACKBERRY, ExtendedItems.BLACKBERRY_BUSH));
		ExtendedBlocks.CRANBERRY_BUSH = BLOCKS.register("cranberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.CRANBERRY, ExtendedItems.CRANBERRY_BUSH));
		ExtendedBlocks.ELDERBERRY_BUSH = BLOCKS.register("elderberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.ELDERBERRY, ExtendedItems.ELDERBERRY_BUSH));
		ExtendedBlocks.WILDBERRY_BUSH = BLOCKS.register("wildberry_bush", () -> new ExtendedBerryBushBlock(ExtendedItems.WILDBERRY, ExtendedItems.WILDBERRY_BUSH));

		//CRAFTING TABLES
		ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE = BLOCKS.register("taurethrim_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Taurethrim(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE = BLOCKS.register("dol_guldur_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Dol_Guldur(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.RHUNIC_CRAFTING_TABLE = BLOCKS.register("rhunic_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Rhunic(ExtendedBlocks.RHUNIC_BRICK));
		
		ExtendedBlocks.MORGUL_LANTERN = BLOCKS.register("morgul_lantern", MorgulLanternBlock::new);
		
		ExtendedBlocks.MORGUL_TORCH = BLOCKS.register("morgul_torch", () -> new LOTRTorchBlock(14));
		ExtendedBlocks.MORGUL_WALL_TORCH = BLOCKS.register("morgul_wall_torch", () -> new LOTRWallTorchBlock(ExtendedBlocks.MORGUL_TORCH));
	}
}
