package com.github.maximuslotro.lotroExtended.mixins.common.init;


import lotr.common.init.ExtendedItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.ExtendedBerryBushBlock;
import lotr.common.block.ExtendedBerryPlantBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedFactionCraftingTableBlock;
import lotr.common.block.ExtendedMorgulLanternBlock;
import lotr.common.block.ExtendedPackedBundleBlock;
import lotr.common.block.ExtendedSpawnerBlock;
import lotr.common.block.LOTROreBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.block.MineralBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import lotr.common.tileentitie.spawners.ExtendedEntitySpawnTypes;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import static lotr.common.init.LOTRBlocks.constantLight;

import java.util.function.Supplier;

@Mixin(LOTRBlocks.class)
public class MixinLOTRBlocks {
	
	@Final
	@Shadow(remap=false)
	public static DeferredRegister<Block> BLOCKS;
	
	private static RegistryObject<Block> registerB(String name, Supplier<? extends Block> sup) {
		RegistryObject<Block> block = BLOCKS.register(name, sup);
		ExtendedBlocks.REGISTEREDBLOCKS.put(name, block);
		return block;
		
    }
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		//ORE STORAGE
		ExtendedBlocks.GALVORN_BLOCK = registerB("galvorn_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(4.0F, 8.0F).sound(SoundType.METAL), 2);});
		ExtendedBlocks.BLACK_URUK_STEEL_BLOCK = registerB("black_uruk_steel_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
		ExtendedBlocks.GILDED_IRON_BLOCK = registerB("gilded_iron_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
		ExtendedBlocks.MORGUL_STEEL_BLOCK = registerB("morgul_steel_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GREEN).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});

		//Packed Fur
		ExtendedBlocks.WHITE_PACKED_FUR = registerB("white_packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.WOOL));
		ExtendedBlocks.WHITE_PACKED_FUR_SLAB = registerB("white_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		ExtendedBlocks.WHITE_PACKED_FUR_STAIRS = registerB("white_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		ExtendedBlocks.WHITE_PACKED_FUR_WALL = registerB("white_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.WHITE_PACKED_FUR));
		
		ExtendedBlocks.SILVER_PACKED_FUR = registerB("silver_packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.COLOR_LIGHT_GRAY));
		ExtendedBlocks.SILVER_PACKED_FUR_SLAB = registerB("silver_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		ExtendedBlocks.SILVER_PACKED_FUR_STAIRS = registerB("silver_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		ExtendedBlocks.SILVER_PACKED_FUR_WALL = registerB("silver_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.SILVER_PACKED_FUR));
		
		ExtendedBlocks.GREY_PACKED_FUR = registerB("grey_packed_fur", () -> new ExtendedPackedBundleBlock(MaterialColor.COLOR_GRAY));
		ExtendedBlocks.GREY_PACKED_FUR_SLAB = registerB("grey_packed_fur_slab", () -> new AxialSlabBlock(ExtendedBlocks.GREY_PACKED_FUR));
		ExtendedBlocks.GREY_PACKED_FUR_STAIRS = registerB("grey_packed_fur_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.GREY_PACKED_FUR));
		ExtendedBlocks.GREY_PACKED_FUR_WALL = registerB("grey_packed_fur_wall", () -> new LOTRWallBlock(ExtendedBlocks.GREY_PACKED_FUR));
		
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
		ExtendedBlocks.GREY_FUR_BUNDLE = registerB("grey_fur_bundle", () -> new ExtendedDirectionalBundleBlock(MaterialColor.COLOR_GRAY));
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
		ExtendedBlocks.AMBER_ORE = registerB("amber_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.TOPAZ_ORE = registerB("topaz_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.AMETHYST_ORE = registerB("amethyst_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.SAPPHIRE_ORE = registerB("sapphire_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.RUBY_ORE = registerB("ruby_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.DIAMOND_ORE = registerB("diamond_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.OPAL_ORE = registerB("opal_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));
		ExtendedBlocks.EMERALD_ORE = registerB("emerald_ore", () -> new LOTROreBlock(Properties.of(Material.STONE).strength(3.0F, 3.0F).lightLevel(constantLight(0))).setOreLevel(1));

		///////////////////////////////BRICKS
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
		
		ExtendedBlocks.RED_RHUNIC_BRICK = registerB("red_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RED_RHUNIC_BRICK_SLAB = registerB("red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS = registerB("red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_WALL = registerB("red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_PILLAR = registerB("red_rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB = registerB("red_rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RED_RHUNIC_BRICK = registerB("carved_red_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RED_RHUNIC_BRICK));

		//Gondor
		ExtendedBlocks.SMOOTH_GONDOR_STAIRS = registerB("smooth_gondor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_GONDOR_ROCK));
		
		//Rohan
		ExtendedBlocks.SMOOTH_ROHAN_STAIRS = registerB("smooth_rohan_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_ROHAN_ROCK));

		//Blue Mountians
		ExtendedBlocks.SMOOTH_BLUE_STAIRS = registerB("smooth_blue_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_BLUE_ROCK));
		
		//Red Mountians
		ExtendedBlocks.SMOOTH_RED_STAIRS = registerB("smooth_red_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_RED_ROCK));

		//Mordor
		ExtendedBlocks.SMOOTH_MORDOR_STAIRS = registerB("smooth_mordor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_MORDOR_ROCK));

		//Drytone
		ExtendedBlocks.DRYSTONE_SLAB = registerB("drystone_slab", () -> new AxialSlabBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.DRYSTONE_STAIRS = registerB("drystone_stairs", () -> new LOTRStairsBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.DRYSTONE_WALL = registerB("drystone_wall", () -> new LOTRWallBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.COBBLED_DRYSTONE = registerB("cobbled_drystone", () -> new LOTRStoneBlock(LOTRBlocks.DRYSTONE));
		ExtendedBlocks.CUT_DRYSTONE = registerB("cut_drystone", () -> new LOTRStoneBlock(LOTRBlocks.DRYSTONE));
		
		ExtendedBlocks.MOSSY_DRYSTONE = registerB("mossy_drystone", () -> new LOTRStoneBlock(LOTRBlocks.DRYSTONE));
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
		ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE = registerB("taurethrim_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Taurethrim(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE = registerB("dol_guldur_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Dol_Guldur(ExtendedBlocks.DOL_GULDUR_BRICK));
		ExtendedBlocks.RHUNIC_CRAFTING_TABLE = registerB("rhunic_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Rhunic(ExtendedBlocks.RHUNIC_BRICK));
		//TODO add block props
		ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE = registerB("half_troll_crafting_table", () -> new ExtendedFactionCraftingTableBlock.HalfTroll(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.GUNDABAD_CRAFTING_TABLE = registerB("gundabad_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Gundabad(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.MORWAITH_CRAFTING_TABLE = registerB("morwaith_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Morwaith(ExtendedBlocks.TAURETHRIM_BRICK));
		
		ExtendedBlocks.MORGUL_LANTERN = registerB("morgul_lantern", ExtendedMorgulLanternBlock::new);
		
		ExtendedBlocks.MORGUL_TORCH = registerB("morgul_torch", () -> new LOTRTorchBlock(14));
		ExtendedBlocks.MORGUL_WALL_TORCH = registerB("morgul_torch_wall", () -> new LOTRWallTorchBlock(ExtendedBlocks.MORGUL_TORCH));
		
		for(ExtendedEntitySpawnTypes type : ExtendedEntitySpawnTypes.values()) {
			ExtendedBlocks.ENTITYSPAWNERBLOCKS.put(type.getSerializedName(), registerB("entity_spawner_"+type.getSerializedName(), () -> new ExtendedSpawnerBlock(Properties.of(Material.STONE).strength(-1.0F).noCollission().noDrops(), type)));
		}
	}
}
