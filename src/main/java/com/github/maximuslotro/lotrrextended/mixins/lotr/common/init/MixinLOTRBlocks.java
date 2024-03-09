package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;


import lotr.common.init.ExtendedItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryPlantBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCakeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGreenFireBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundDirtBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundFarmlandBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundGrassBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundPathBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedSingleChestBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneChestTileEntity;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedFactionCraftingTableBlock;
import lotr.common.block.ExtendedGreenHearthBlock;
import lotr.common.block.ExtendedLOTRTorchBlock;
import lotr.common.block.ExtendedLOTRWallTorchBlock;
import lotr.common.block.ExtendedLanternBlock;
import lotr.common.block.ExtendedPackedBundleBlock;
import lotr.common.block.ExtendedRemainsBlock;
import lotr.common.block.ExtendedStoneBlock;
import lotr.common.block.ExtendedTwoTypeDummyBlock;
import lotr.common.block.LOTROreBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.MineralBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
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
		//Scorched Stuff
		ExtendedBlocks.SCORCHED_STONE = registerB("scorched_stone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
		ExtendedBlocks.SCORCHED_STONE_SLAB = registerB("scorched_stone_slab", () -> new AxialSlabBlock(ExtendedBlocks.SCORCHED_STONE));
		ExtendedBlocks.SCORCHED_STONE_STAIRS = registerB("scorched_stone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SCORCHED_STONE));
		ExtendedBlocks.SCORCHED_COBBLESTONE = registerB("scorched_cobblestone", () -> new ExtendedStoneBlock(MaterialColor.STONE));
		ExtendedBlocks.SCORCHED_COBBLESTONE_SLAB = registerB("scorched_cobblestone_slab", () -> new AxialSlabBlock(ExtendedBlocks.SCORCHED_COBBLESTONE));
		ExtendedBlocks.SCORCHED_COBBLESTONE_STAIRS = registerB("scorched_cobblestone_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.SCORCHED_COBBLESTONE));
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
		
		ExtendedBlocks.RED_RHUNIC_BRICK = registerB("red_rhunic_brick", () -> new LOTRStoneBlock(MaterialColor.COLOR_YELLOW));
		ExtendedBlocks.RED_RHUNIC_BRICK_SLAB = registerB("red_rhunic_brick_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS = registerB("red_rhunic_brick_stairs", () -> new LOTRStairsBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_BRICK_WALL = registerB("red_rhunic_brick_wall", () -> new LOTRWallBlock(ExtendedBlocks.RED_RHUNIC_BRICK));
		ExtendedBlocks.RED_RHUNIC_PILLAR = registerB("red_rhunic_pillar", () -> new LOTRPillarBlock(MaterialColor.COLOR_RED));
		ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB = registerB("red_rhunic_pillar_slab", () -> new AxialSlabBlock(ExtendedBlocks.RED_RHUNIC_PILLAR));
		ExtendedBlocks.CARVED_RED_RHUNIC_BRICK = registerB("carved_red_rhunic_brick", () -> new LOTRStoneBlock(ExtendedBlocks.RED_RHUNIC_BRICK));

		//Gondor
		ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK = registerB("black_carved_gondor_brick", () -> new LOTRStoneBlock(LOTRBlocks.NUMENOREAN_BRICK));
		ExtendedBlocks.SMOOTH_GONDOR_STAIRS = registerB("smooth_gondor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_GONDOR_ROCK));
		
		//Rohan
		ExtendedBlocks.SMOOTH_ROHAN_STAIRS = registerB("smooth_rohan_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_ROHAN_ROCK));

		//Blue Mountains
		ExtendedBlocks.SMOOTH_BLUE_STAIRS = registerB("smooth_blue_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_BLUE_ROCK));
		
		//Red Mountains
		ExtendedBlocks.SMOOTH_RED_STAIRS = registerB("smooth_red_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_RED_ROCK));

		//Mordor
		ExtendedBlocks.SMOOTH_MORDOR_STAIRS = registerB("smooth_mordor_rock_stairs", () -> new LOTRStairsBlock(LOTRBlocks.SMOOTH_MORDOR_ROCK));

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
		//TODO add block props
		ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE = registerB("half_troll_crafting_table", () -> new ExtendedFactionCraftingTableBlock.HalfTroll(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.GUNDABAD_CRAFTING_TABLE = registerB("gundabad_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Gundabad(ExtendedBlocks.TAURETHRIM_BRICK));
		ExtendedBlocks.MORWAITH_CRAFTING_TABLE = registerB("morwaith_crafting_table", () -> new ExtendedFactionCraftingTableBlock.Morwaith(ExtendedBlocks.TAURETHRIM_BRICK));
		
		ExtendedBlocks.MORGUL_LANTERN = registerB("morgul_lantern", () -> new ExtendedLanternBlock(14));
		ExtendedBlocks.MORGUL_TORCH = registerB("morgul_torch", () -> new ExtendedLOTRTorchBlock(14).setParticles(() -> { return ExtendedParticles.MORGUL_FLAME.get();}));
		ExtendedBlocks.MORGUL_WALL_TORCH = registerB("morgul_torch_wall", () -> new ExtendedLOTRWallTorchBlock(ExtendedBlocks.MORGUL_TORCH));
		ExtendedBlocks.GREEN_FLAME_HEARTH_BLOCK = registerB("green_fire_hearth_block", () -> new ExtendedGreenHearthBlock());
		ExtendedBlocks.GREEN_FIRE_BLOCK = registerB("green_fire", () -> new ExtendedGreenFireBlock(AbstractBlock.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN).noCollission().instabreak().lightLevel((p_235467_0_) -> {
		      return 10;
		   }).sound(SoundType.WOOL)));

		for(ExtendedEntitySpawnTypes type : ExtendedEntitySpawnTypes.values()) {
			ExtendedBlocks.ENTITYSPAWNERBLOCKS.put(type.getSerializedName(), registerB("entity_spawner_"+type.getSerializedName(), () -> new ExtendedSpawnerBlock(Properties.of(Material.STONE).strength(-1.0F).noCollission().noDrops(), type)));
		}
		ExtendedBlocks.CHEESE_WHEEL = registerB("cheese_wheel", () -> new ExtendedCakeBlock(Properties.copy(Blocks.CAKE), 1.0F, ExtendedItems.CHEESE_SLICE));

		ExtendedBlocks.STONE_CHEST = registerB("stone_chest", () -> new ExtendedSingleChestBlock(()-> new ExtendedStoneChestTileEntity()));
	}

	@Inject(method = "register()V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, remap=false)
	private static void bootstrapExtendedBiomeFeatures(CallbackInfo ci, IEventBus bus) {
		bus.addGenericListener(Block.class, ExtendedBiomeFeatures::setup);
	}
}
