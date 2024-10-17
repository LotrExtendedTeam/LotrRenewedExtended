package com.github.maximuslotro.lotrrextended.common.datafixers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;

@SuppressWarnings("deprecation")
public class ExtendedDataIdFixer {
	public static final Map<ResourceLocation, Block> BLOCKS = new HashMap<>();
	public static final Map<ResourceLocation, Item> ITEMS = new HashMap<>();
	private static boolean hasMappingsBeenCreated = false;

	public static void fixBlockMappings(MissingMappings<Block> event) {
		for(Mapping<Block> mapping : event.getAllMappings()) {
			ResourceLocation entryLocation = mapping.key;
			//Handle Out Mod's Stuff, or handle Stuff That We Have Registered under "lotr" or Handle stuff we messed with
			if (entryLocation.getNamespace().equals("lotrextended") || entryLocation.getNamespace().equals("lotr")) {
				if (!hasMappingsBeenCreated) {
					createMappings();
				}
				if (BLOCKS.containsKey(entryLocation)) {
					Block block = BLOCKS.get(entryLocation);
					mapping.remap(block);
					ExtendedLog.info("Remapped block, old id was=%s, new id is=%s", entryLocation, block.getRegistryName());
				}
				continue;
			}
		}
	}

	public static void fixItemMappings(MissingMappings<Item> event) {
		for(Mapping<Item> mapping : event.getAllMappings()) {
			ResourceLocation entryLocation = mapping.key;
			//Handle Out Mod's Stuff, or handle Stuff That We Have Registered under "lotr" or Handle stuff we messed with
			if (entryLocation.getNamespace().equals("lotrextended") || entryLocation.getNamespace().equals("lotr")) {
				if (!hasMappingsBeenCreated) {
					createMappings();
				}
				if (ITEMS.containsKey(entryLocation)) {
					Item item = ITEMS.get(entryLocation);
					mapping.remap(item);
					ExtendedLog.info("Remapped item, old id was=%s, new id is=%s", entryLocation, item.getRegistryName());
				}
				continue;
			}
		}
	}

	private static void createMappings() {
		hasMappingsBeenCreated = true;
		//Alpha 1.1 Changed Tan Fur -> Grey Fur and Brown Packed Fur -> Packed Fur?
		addRemapBlockWithItem("tan_packed_fur", "lotr", ExtendedBlocks.GRAY_PACKED_FUR, ExtendedItems.GRAY_PACKED_FUR);
		addRemapBlockWithItem("brown_packed_fur", "lotr", ExtendedBlocks.BROWN_PACKED_FUR, ExtendedItems.BROWN_PACKED_FUR);

		//Beta 1.1-1.2 Between Which added Berry Plants and Renamed them
		addRemapBlockWithItem("blackberry_bush_plant", "lotr", ExtendedBlocks.BLACKBERRY_BUSH_PLANT, ExtendedItems.BLACKBERRY);
		addRemapBlockWithItem("blueberry_bush_plant", "lotr", ExtendedBlocks.BLUEBERRY_BUSH_PLANT, ExtendedItems.BLUEBERRY);
		addRemapBlockWithItem("cranberry_bush_plant", "lotr", ExtendedBlocks.CRANBERRY_BUSH_PLANT, ExtendedItems.CRANBERRY);
		addRemapBlockWithItem("elderberry_bush_plant", "lotr", ExtendedBlocks.ELDERBERRY_BUSH_PLANT, ExtendedItems.ELDERBERRY);
		addRemapBlockWithItem("raspberry_bush_plant", "lotr", ExtendedBlocks.RASPBERRY_BUSH_PLANT, ExtendedItems.RASPBERRY);
		addRemapBlockWithItem("wildberry_bush_plant", "lotr", ExtendedBlocks.WILDBERRY_BUSH_PLANT, ExtendedItems.WILDBERRY);
		//Also added and renamed Morgul wall torches
		addRemapBlock("morgul_wall_torch", "lotr", ExtendedBlocks.MORGUL_WALL_TORCH);

		//Beta 1.2 Fixed incorrect naming of spawner blocks
		addRemapBlock("entity_spawnerhobbit_farmer", "lotr", ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_FARMER.getSerializedName()));
		addRemapBlock("entity_spawnerhobbit_bartender", "lotr", ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()));

		//Beta 1.3.0-alpha-1 Renamed Stone Taurethrim table name
		addRemapBlock("taurethrim_stone_crafting_table", "lotr", ExtendedBlocks.TAURETHRIM_STONE_CRAFTING_TABLE);

		//Beta 1.4.0 Changed Grey->Gray
		addRemapItem("grey_fur", "lotr", ExtendedItems.GRAY_FUR);
		addRemapBlockWithItem("grey_fur_bundle", "lotr", ExtendedBlocks.GRAY_FUR_BUNDLE, ExtendedItems.GRAY_FUR_BUNDLE);
		addRemapBlockWithItem("grey_packed_fur", "lotr", ExtendedBlocks.GRAY_PACKED_FUR, ExtendedItems.GRAY_PACKED_FUR);
		addRemapBlockWithItem("grey_packed_fur_slab", "lotr", ExtendedBlocks.GRAY_PACKED_FUR_SLAB, ExtendedItems.GRAY_PACKED_FUR_SLAB);
		addRemapBlockWithItem("grey_packed_fur_stairs", "lotr", ExtendedBlocks.GRAY_PACKED_FUR_STAIRS, ExtendedItems.GRAY_PACKED_FUR_STAIRS);
		addRemapBlockWithItem("grey_packed_fur_wall", "lotr", ExtendedBlocks.GRAY_PACKED_FUR_WALL, ExtendedItems.GRAY_PACKED_FUR_WALL);
		
		//Beta 1.5.1 Merged Flowery Chestnut Leaves with Chestnut Leaves
		addRemapBlockWithItem("chestnut_leaves_flowers", "lotr", ExtendedBlocks.CHESTNUT_LEAVES, ExtendedItems.CHESTNUT_LEAVES);
		
		//Beta 1.6.2-alpha-1 Renamed Millstone to Stone Grinder
		addRemapBlockWithItem("millstone", "lotr", ExtendedBlocks.STONE_GRINDER, ExtendedItems.STONE_GRINDER);
		
		//Beta 1.7.0-alpha-2 Removed Morgul Dagger and Spear
		addRemapItem("morgul_spear", "lotr", ExtendedItems.ANGMAR_SPEAR);
		addRemapItem("morgul_dagger", "lotr", ExtendedItems.ANGMAR_DAGGER);
	}

	private static void addRemapBlock(String previousMapping, String previousNamespace, Supplier<? extends Block> remapBlock) {
		BLOCKS.put(new ResourceLocation(previousNamespace, previousMapping), remapBlock.get());
	}
	private static void addRemapItem(String previousMapping, String previousNamespace,  Supplier<? extends Item> remapItem) {
		ITEMS.put(new ResourceLocation(previousNamespace, previousMapping), remapItem.get());
	}
	private static void addRemapBlockWithItem(String previousMapping, String previousNamespace, Supplier<? extends Block> newBlock, Supplier<? extends Item> newItem) {
		addRemapBlock(previousMapping, previousNamespace, newBlock);
		addRemapItem(previousMapping, previousNamespace, newItem);
	}
}
