package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import lotr.common.init.*;
import lotr.common.item.*;
import lotr.common.item.ExtendedSilverCoin.CoinType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.item.Item.Properties;

@Mixin(LOTRItems.class)
public class MixinLOTRItems {

	@Shadow(remap=false)
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}
	private static RegistryObject<Item> regItemB(String name, Supplier<? extends Item> sup)  {
		RegistryObject<Item> item = regItem(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(name, item);
		}
		return item;
	}

	@Shadow(remap=false)
	private static RegistryObject<Item> regCakeBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	private static RegistryObject<Item> regCakeBlockItemD(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regCakeBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	@Shadow(remap=false)
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	private static RegistryObject<Item> regBlockItemB(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	@Shadow(remap=false)
	private static RegistryObject<Item> regMaterialItem(String name) {return null;}
	private static RegistryObject<Item> regMaterialItemB(String name)  {
		RegistryObject<Item> item = regMaterialItem(name);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(name, item);
		}
		return item;
	}

	@Shadow(remap=false)
	private static RegistryObject<Item> regWallOrFloorBlockItem(RegistryObject <Block>wallBlockSup, RegistryObject<Block> floorBlockSup, ItemGroup group)  {return null;}
	private static RegistryObject<Item> regWallOrFloorBlockItemB(RegistryObject <Block>wallBlockSup, RegistryObject<Block> floorBlockSup, ItemGroup group)  {
		RegistryObject<Item> item = regWallOrFloorBlockItem(wallBlockSup, floorBlockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(wallBlockSup.getId().getPath(), item);
		}
		return item;
	}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		//###########################################################BLOCKS################################################################
		//ORE/GEM STORAGE
		ExtendedItems.GALVORN_BLOCK = regBlockItemB(ExtendedBlocks.GALVORN_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_URUK_STEEL_BLOCK = regBlockItemB(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GILDED_IRON_BLOCK = regBlockItemB(ExtendedBlocks.GILDED_IRON_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MORGUL_STEEL_BLOCK = regBlockItemB(ExtendedBlocks.MORGUL_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.TOPAZ_BLOCK = regBlockItemB(ExtendedBlocks.TOPAZ_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_BLOCK = regBlockItemB(ExtendedBlocks.AMETHYST_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_BLOCK = regBlockItemB(ExtendedBlocks.SAPPHIRE_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_BLOCK = regBlockItemB(ExtendedBlocks.RUBY_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_BLOCK = regBlockItemB(ExtendedBlocks.AMBER_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_BLOCK = regBlockItemB(ExtendedBlocks.DIAMOND_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_BLOCK = regBlockItemB(ExtendedBlocks.OPAL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_BLOCK = regBlockItemB(ExtendedBlocks.EMERALD_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.PEARL_BLOCK = regBlockItemB(ExtendedBlocks.PEARL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CORAL_BLOCK = regBlockItemB(ExtendedBlocks.CORAL_BLOCK, LOTRItemGroups.BLOCKS);

		//ORE
		ExtendedItems.TOPAZ_ORE = regBlockItemB(ExtendedBlocks.TOPAZ_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_ORE = regBlockItemB(ExtendedBlocks.AMETHYST_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_ORE = regBlockItemB(ExtendedBlocks.SAPPHIRE_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_ORE = regBlockItemB(ExtendedBlocks.RUBY_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_ORE = regBlockItemB(ExtendedBlocks.AMBER_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_ORE = regBlockItemB(ExtendedBlocks.DIAMOND_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_ORE = regBlockItemB(ExtendedBlocks.OPAL_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_ORE = regBlockItemB(ExtendedBlocks.EMERALD_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_ELF = regBlockItemB(ExtendedBlocks.REMAINS_ELF, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_MAN = regBlockItemB(ExtendedBlocks.REMAINS_MAN, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_ORC = regBlockItemB(ExtendedBlocks.REMAINS_ORC, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_DWARF = regBlockItemB(ExtendedBlocks.REMAINS_DWARF, LOTRItemGroups.BLOCKS);

		//############################################################DECORATION##############################################################
		//Bushes
		ExtendedItems.RASPBERRY_BUSH = regBlockItemB(ExtendedBlocks.RASPBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.BLUEBERRY_BUSH = regBlockItemB(ExtendedBlocks.BLUEBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.BLACKBERRY_BUSH = regBlockItemB(ExtendedBlocks.BLACKBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.CRANBERRY_BUSH = regBlockItemB(ExtendedBlocks.CRANBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.ELDERBERRY_BUSH = regBlockItemB(ExtendedBlocks.ELDERBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.WILDBERRY_BUSH = regBlockItemB(ExtendedBlocks.WILDBERRY_BUSH, LOTRItemGroups.DECO);
		
		ExtendedItems.WHITE_PACKED_FUR = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.WHITE_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.WHITE_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.WHITE_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.GREY_PACKED_FUR = regBlockItemB(ExtendedBlocks.GREY_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.GREY_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.GREY_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.GREY_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.GREY_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.GREY_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.GREY_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		
		ExtendedItems.WHITE_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.WHITE_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.SILVER_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.GREY_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.GREY_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.BLACK_FUR_BUNDLE, LOTRItemGroups.DECO);
		
		ExtendedItems.MORGUL_LANTERN = regBlockItemB(ExtendedBlocks.MORGUL_LANTERN, LOTRItemGroups.DECO);
		
		//########### BLOCK TYPES ##################
		//Stone types
		ExtendedItems.DRYSTONE_SLAB = regBlockItemB(ExtendedBlocks.DRYSTONE_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.DRYSTONE_STAIRS = regBlockItemB(ExtendedBlocks.DRYSTONE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.DRYSTONE_WALL = regBlockItemB(ExtendedBlocks.DRYSTONE_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.COBBLED_DRYSTONE = regBlockItemB(ExtendedBlocks.COBBLED_DRYSTONE, LOTRItemGroups.BLOCKS);
		ExtendedItems.CUT_DRYSTONE = regBlockItemB(ExtendedBlocks.CUT_DRYSTONE, LOTRItemGroups.BLOCKS);
		
		ExtendedItems.MOSSY_DRYSTONE = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DRYSTONE_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DRYSTONE_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DRYSTONE_WALL = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE_WALL, LOTRItemGroups.BLOCKS);
		
		//Taurethrim Bricks
		ExtendedItems.TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_PILLAR = regBlockItemB(ExtendedBlocks.TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_PILLAR = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		
		//Dol Guldur Bricks
		ExtendedItems.DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_SLAB = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_WALL = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_PILLAR = regBlockItemB(ExtendedBlocks.DOL_GULDUR_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.CARVED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);

		//Rhunic Bricks
		ExtendedItems.RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_PILLAR = regBlockItemB(ExtendedBlocks.RHUNIC_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.RHUNIC_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.CARVED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_PILLAR = regBlockItemB(ExtendedBlocks.RED_RHUNIC_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_RED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.CARVED_RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);

		ExtendedItems.MORGUL_TORCH = regWallOrFloorBlockItemB(ExtendedBlocks.MORGUL_TORCH, ExtendedBlocks.MORGUL_WALL_TORCH, LOTRItemGroups.DECO);
		
		ExtendedItems.SMOOTH_GONDOR_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_GONDOR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_MORDOR_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_MORDOR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_ROHAN_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_ROHAN_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_BLUE_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_BLUE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_RED_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_RED_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_STONE_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_STONE_STAIRS, LOTRItemGroups.BLOCKS);
		//###################################################################MATERIALS########################################################
		//Misc
		ExtendedItems.OBSIDIAN_SHARD = regMaterialItemB("obsidian_shard");
		ExtendedItems.HITHLAIN = regMaterialItemB("hithlain");
		ExtendedItems.ITHILDIN = regMaterialItemB("ithildin");

		ExtendedItems.WHITE_FUR = regMaterialItemB("white_fur");
		ExtendedItems.SILVER_FUR = regMaterialItemB("silver_fur");
		ExtendedItems.GREY_FUR = regMaterialItemB("grey_fur");
		ExtendedItems.BLACK_FUR = regMaterialItemB("black_fur");

		//Ingots
		ExtendedItems.GALVORN_INGOT = regMaterialItemB("galvorn_ingot");
		ExtendedItems.BLACK_URUK_STEEL_INGOT = regMaterialItemB("black_uruk_steel_ingot");
		ExtendedItems.GILDED_IRON_INGOT = regMaterialItemB("gilded_iron_ingot");
		ExtendedItems.MORGUL_STEEL_INGOT = regMaterialItemB("morgul_steel_ingot");

		//Gems
		ExtendedItems.TOPAZ = regMaterialItemB("topaz");
		ExtendedItems.AMETHYST = regMaterialItemB("amethyst");
		ExtendedItems.SAPPHIRE = regMaterialItemB("sapphire");
		ExtendedItems.RUBY = regMaterialItemB("ruby");
		ExtendedItems.AMBER = regMaterialItemB("amber");
		ExtendedItems.DIAMOND = regMaterialItemB("diamond");
		ExtendedItems.OPAL = regMaterialItemB("opal");
		ExtendedItems.EMERALD = regMaterialItemB("emerald");
		ExtendedItems.PEARL = regMaterialItemB("pearl");
		ExtendedItems.CORAL = regMaterialItemB("coral");

		//#########################################################################TOOLS#####################################################
		//Taurethrim Tools
		ExtendedItems.TAURETHRIM_SHOVEL = regItemB("taurethrim_shovel",() -> new LOTRShovelItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_PICKAXE = regItemB("taurethrim_pickaxe",() -> new LOTRPickaxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_AXE = regItemB("taurethrim_axe",() -> new LOTRAxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_HOE = regItemB("taurethrim_hoe",() -> new LOTRHoeItem(LOTRMaterial.valueOf("TAURETHRIM"))); 
		
		//Angmar Tools
		ExtendedItems.ANGMAR_SHOVEL = regItemB("angmar_shovel",() -> new LOTRShovelItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_PICKAXE = regItemB("angmar_pickaxe",() -> new LOTRPickaxeItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_AXE = regItemB("angmar_axe",() -> new LOTRAxeItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_HOE = regItemB("angmar_hoe",() -> new LOTRHoeItem(LOTRMaterial.valueOf("ANGMAR")));

		//Dol Guldur Tools
		ExtendedItems.DOL_GULDUR_SHOVEL = regItemB("dol_guldur_shovel",() -> new LOTRShovelItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_PICKAXE = regItemB("dol_guldur_pickaxe",() -> new LOTRPickaxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_AXE = regItemB("dol_guldur_axe",() -> new LOTRAxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_HOE = regItemB("dol_guldur_hoe",() -> new LOTRHoeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		//######################################################################FOOD########################################################
		//Berries
		ExtendedItems.BLACKBERRY = regItemB("blackberry", () -> new BlockNamedItem(ExtendedBlocks.BLACKBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLACKBERRY)));
		ExtendedItems.BLUEBERRY = regItemB("blueberry", () -> new BlockNamedItem(ExtendedBlocks.BLUEBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLUEBERRY)));
		ExtendedItems.CRANBERRY = regItemB("cranberry", () -> new BlockNamedItem(ExtendedBlocks.CRANBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CRANBERRY)));
		ExtendedItems.ELDERBERRY = regItemB("elderberry", () -> new BlockNamedItem(ExtendedBlocks.ELDERBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ELDERBERRY)));
		ExtendedItems.RASPBERRY = regItemB("raspberry", () -> new BlockNamedItem(ExtendedBlocks.RASPBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.RASPBERRY)));
		ExtendedItems.WILDBERRY = regItemB("wildberry", ()-> new BlockNamedItem(ExtendedBlocks.WILDBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.WILDBERRY)));

		ExtendedItems.BOTTLE_OF_POISON = regItemB("bottle_of_poison", () -> new ExtendedPoisonBottleItem((new Properties()).tab(LOTRItemGroups.MISC)));
		ExtendedItems.CHEESE_WHEEL  = regCakeBlockItemD(ExtendedBlocks.CHEESE_WHEEL, LOTRItemGroups.FOOD);
		//#######################################################################EQUIPMENT##################################################
		ExtendedItems.COMMANDHORN = regItemB("horn_of_command", () -> new ExtendedCommandHornItem());

		//Morgul Equipment
		ExtendedItems.MORGUL_HELMET = regItemB("morgul_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.MORGUL_CHESTPLATE = regItemB("morgul_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.CHEST));
		ExtendedItems.MORGUL_LEGGINGS = regItemB("morgul_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.LEGS));
		ExtendedItems.MORGUL_BOOTS = regItemB("morgul_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.FEET));
		ExtendedItems.MORGUL_SWORD = regItemB("morgul_blade",() -> new LOTRSwordItem(LOTRMaterial.valueOf("MORGUL")));

		//Taurethrim Equipment
		ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET = regItemB("taurethrim_chieftain_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD,"chieftain_helmet"));
		ExtendedItems.TAURETHRIM_HELMET = regItemB("taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD));
		ExtendedItems.TAURETHRIM_CHESTPLATE = regItemB("taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.TAURETHRIM_LEGGINGS = regItemB("taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.TAURETHRIM_BOOTS = regItemB("taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.FEET));
		ExtendedItems.TAURETHRIM_SWORD = regItemB("taurethrim_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("TAURETHRIM")));

		ExtendedItems.GOLDEN_TAURETHRIM_HELMET = regItemB("golden_taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE = regItemB("golden_taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS = regItemB("golden_taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.GOLDEN_TAURETHRIM_BOOTS = regItemB("golden_taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.FEET));

		//Angmar Equipment
		ExtendedItems.ANGMAR_HELMET = regItemB("angmar_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.HEAD));
		ExtendedItems.ANGMAR_CHESTPLATE = regItemB("angmar_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.CHEST));
		ExtendedItems.ANGMAR_LEGGINGS = regItemB("angmar_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.LEGS));
		ExtendedItems.ANGMAR_BOOTS = regItemB("angmar_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.FEET));
		ExtendedItems.ANGMAR_SWORD = regItemB("angmar_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("ANGMAR")));

		//Dol Guldur Equipment
		ExtendedItems.DOL_GULDUR_HELMET = regItemB("dol_guldur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.HEAD));
		ExtendedItems.DOL_GULDUR_CHESTPLATE = regItemB("dol_guldur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.CHEST));
		ExtendedItems.DOL_GULDUR_LEGGINGS = regItemB("dol_guldur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.LEGS));
		ExtendedItems.DOL_GULDUR_BOOTS = regItemB("dol_guldur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.FEET));
		ExtendedItems.DOL_GULDUR_SWORD = regItemB("dol_guldur_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("DOL_GULDUR")));

		// misc equipment
		ExtendedItems.LEATHER_HAT = regItemB("leather_hat", () -> new LOTRArmorItem(LOTRMaterial.valueOf("COSMETIC"), EquipmentSlotType.HEAD, "leather_hat"));

		//########################################################################UTIL##########################################################
		//Crafting Tables
		ExtendedItems.DOL_GULDUR_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.TAURETHRIM_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.RHUNIC_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.RHUNIC_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.RHUNIC_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.GUNDABAD_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.RHUNIC_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.MORWAITH_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.RHUNIC_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE, LOTRItemGroups.UTIL);

		//########################################################################MISC##########################################################

		ExtendedItems.HOBBIT_MARRIAGE_RING =  regItemB("hobbit_marriage_ring", () -> new Item((new Properties()).tab(LOTRItemGroups.MISC)));
		ExtendedItems.DWARF_MARRIAGE_RING =  regItemB("dwarven_marriage_ring", () -> new Item((new Properties()).tab(LOTRItemGroups.MISC)));
		ExtendedItems.SILVER_COIN_ONE = regItemB("silver_coin_one", () -> new ExtendedSilverCoin((new Properties()).tab(LOTRItemGroups.MISC), CoinType.ONE));
		ExtendedItems.SILVER_COIN_TEN = regItemB("silver_coin_ten", () -> new ExtendedSilverCoin((new Properties()).tab(LOTRItemGroups.MISC), CoinType.TEN));
		ExtendedItems.SILVER_COIN_HUNDRED = regItemB("silver_coin_hundred", () -> new ExtendedSilverCoin((new Properties()).tab(LOTRItemGroups.MISC), CoinType.HUNDRED));
	}
}
