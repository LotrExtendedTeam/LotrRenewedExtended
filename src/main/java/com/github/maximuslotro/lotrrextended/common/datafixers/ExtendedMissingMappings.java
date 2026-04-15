package com.github.maximuslotro.lotrrextended.common.datafixers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTREntities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;

@SuppressWarnings("deprecation")
public class ExtendedMissingMappings {
	public static final Map<ResourceLocation, Block> BLOCKS = new HashMap<>();
	public static final Map<ResourceLocation, Item> ITEMS = new HashMap<>();
	public static final Map<ResourceLocation, EntityType<?>> ENTITIES = new HashMap<>();

	private static boolean hasMappingsBeenCreated = false;

	public static void ensureMappingsCreated() {
	    if (!hasMappingsBeenCreated) {
	        createMappings();
	    }
	}

	public static void fixBlockMappings(MissingMappings<Block> event) {
		for(Mapping<Block> mapping : event.getAllMappings()) {
			ResourceLocation entryLocation = mapping.key;
			//Handle Out Mod's Stuff, or handle Stuff That We Have Registered under "lotr" or Handle stuff we messed with
			if (entryLocation.getNamespace().equals("lotrextended") || entryLocation.getNamespace().equals("lotr")) {
				ensureMappingsCreated();
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
				ensureMappingsCreated();
				if (ITEMS.containsKey(entryLocation)) {
					Item item = ITEMS.get(entryLocation);
					mapping.remap(item);
					ExtendedLog.info("Remapped item, old id was=%s, new id is=%s", entryLocation, item.getRegistryName());
				}
				continue;
			}
		}
	}

	public static void fixEntityMappings(MissingMappings<EntityType<?>> event) {
		for(Mapping<EntityType<?>> mapping : event.getAllMappings()) {
			ResourceLocation entryLocation = mapping.key;
			//Handle Out Mod's Stuff, or handle Stuff That We Have Registered under "lotr" or Handle stuff we messed with
			if (entryLocation.getNamespace().equals("lotrextended") || entryLocation.getNamespace().equals("lotr")) {
				ensureMappingsCreated();
				if (ENTITIES.containsKey(entryLocation)) {
					EntityType<?> entity = ENTITIES.get(entryLocation);
					mapping.remap(entity);
					ExtendedLog.info("Remapped entity, old id was=%s, new id is=%s", entryLocation, entity.getRegistryName());
				}
				continue;
			}
		}
	}

	private static void createMappings() {
		hasMappingsBeenCreated = true;
		//Alpha 1.1 Changed Tan Fur -> Grey Fur and Brown Packed Fur -> Packed Fur?
		addRemapBlockWithItem("tan_packed_fur", "lotr", ExtendedBlocks.GRAY_MATTED_FUR, ExtendedItems.GRAY_MATTED_FUR);
		addRemapBlockWithItem("brown_packed_fur", "lotr", ExtendedBlocks.BROWN_MATTED_FUR, ExtendedItems.BROWN_MATTED_FUR);

		//Beta 1.1-1.2 Between Which added Berry Plants and Renamed them
		addRemapBlockWithItem("blackberry_bush_plant", "lotr", ExtendedBlocks.BLACKBERRY_BUSH, ExtendedItems.BLACKBERRY);
		addRemapBlockWithItem("blueberry_bush_plant", "lotr", ExtendedBlocks.BLUEBERRY_BUSH, ExtendedItems.BLUEBERRY);
		addRemapBlockWithItem("cranberry_bush_plant", "lotr", ExtendedBlocks.CRANBERRY_BUSH, ExtendedItems.CRANBERRY);
		addRemapBlockWithItem("elderberry_bush_plant", "lotr", ExtendedBlocks.ELDERBERRY_BUSH, ExtendedItems.ELDERBERRY);
		addRemapBlockWithItem("raspberry_bush_plant", "lotr", ExtendedBlocks.RASPBERRY_BUSH, ExtendedItems.RASPBERRY);
		addRemapBlockWithItem("wildberry_bush_plant", "lotr", ExtendedBlocks.WILDBERRY_BUSH, ExtendedItems.WILDBERRY);
		//Also added and renamed Morgul wall torches
		addRemapBlock("morgul_wall_torch", "lotr", ExtendedBlocks.MORGUL_WALL_TORCH);

		//Beta 1.2 Fixed incorrect naming of spawner blocks
		addRemapBlock("entity_spawnerhobbit_farmer", "lotr", ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_FARMER.getSerializedName()));
		addRemapBlock("entity_spawnerhobbit_bartender", "lotr", ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()));

		//Beta 1.3.0-alpha-1 Renamed Stone Taurethrim table name
		addRemapBlockWithItem("taurethrim_stone_crafting_table", "lotr", ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, ExtendedItems.TAURETHRIM_CRAFTING_TABLE);

		//Beta 1.4.0 Changed Grey->Gray
		addRemapItem("grey_fur", "lotr", ExtendedItems.GRAY_FUR);
		addRemapBlockWithItem("grey_fur_bundle", "lotr", ExtendedBlocks.GRAY_FUR_BUNDLE, ExtendedItems.GRAY_FUR_BUNDLE);
		addRemapBlockWithItem("grey_packed_fur", "lotr", ExtendedBlocks.GRAY_MATTED_FUR, ExtendedItems.GRAY_MATTED_FUR);
		addRemapBlockWithItem("grey_packed_fur_slab", "lotr", ExtendedBlocks.GRAY_MATTED_FUR_SLAB, ExtendedItems.GRAY_MATTED_FUR_SLAB);
		addRemapBlockWithItem("grey_packed_fur_stairs", "lotr", ExtendedBlocks.GRAY_MATTED_FUR_STAIRS, ExtendedItems.GRAY_MATTED_FUR_STAIRS);
		addRemapBlockWithItem("grey_packed_fur_wall", "lotr", ExtendedBlocks.GRAY_MATTED_FUR_WALL, ExtendedItems.GRAY_MATTED_FUR_WALL);

		//Beta 1.5.1 Merged Flowery Chestnut Leaves with Chestnut Leaves
		addRemapBlockWithItem("chestnut_leaves_flowers", "lotr", ExtendedBlocks.CHESTNUT_LEAVES, ExtendedItems.CHESTNUT_LEAVES);

		//Beta 1.6.2-alpha-1 Renamed Millstone to Stone Grinder
		addRemapBlockWithItem("millstone", "lotr", ExtendedBlocks.STONE_GRINDER, ExtendedItems.STONE_GRINDER);

		//Beta 1.7.0-alpha-2 Remove morgul dagger and spear, merge taurethrim tables
		addRemapItem("morgul_spear", "lotr", ExtendedItems.ANGMAR_SPEAR);
		addRemapItem("morgul_dagger", "lotr", ExtendedItems.ANGMAR_DAGGER);
		addRemapBlockWithItem("stone_taurethrim_crafting_table", "lotr", ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, ExtendedItems.TAURETHRIM_CRAFTING_TABLE);

		//Beta 1.8.0 Remove separate hired unit types
		addRemapEntity("hired_dunedain_ranger", "lotr", ExtendedEntities.DUNEDAIN_RANGER);
		addRemapEntity("hired_gundabad_orc", "lotr", LOTREntities.GUNDABAD_ORC);
		addRemapEntity("hired_bree_guard", "lotr", LOTREntities.BREE_GUARD);

		// Throwing axes changed names through development of 1.8.0
		addRemapItem("blue_mountains_dwarven_throwable_axe", "lotr", ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE);
		addRemapItem("bronze_throwable_axe", "lotr", ExtendedItems.BRONZE_THROWABLE_AXE);
		addRemapItem("dwarven_throwable_axe", "lotr", ExtendedItems.DWARVEN_THROWABLE_AXE);
		addRemapItem("iron_throwable_axe", "lotr", ExtendedItems.IRON_THROWABLE_AXE);
		addRemapItem("lossarnach_throwable_axe", "lotr", ExtendedItems.LOSSARNACH_THROWABLE_AXE);
		
		//Beta 1.8.0 Rename Mordor Moss Vines to Dead Moss Vines
		addRemapBlockWithItem("mordor_moss_vines", "lotr", ExtendedBlocks.DEAD_MOSS_VINES, ExtendedItems.DEAD_MOSS_VINES);

		//Beta 1.9.0 Renamed Morgul Lantern to Morgul Brazier
		addRemapBlockWithItem("morgul_lantern", "lotr", ExtendedBlocks.MORGUL_BRAZIER, ExtendedItems.MORGUL_BRAZIER);

		//Beta 1.9.2 Renamed Packed Fur to Matted Fur
		addRemapBlockWithItem("white_packed_fur", "lotr", ExtendedBlocks.WHITE_MATTED_FUR, ExtendedItems.WHITE_MATTED_FUR);
		addRemapBlockWithItem("white_packed_fur_slab", "lotr", ExtendedBlocks.WHITE_MATTED_FUR_SLAB, ExtendedItems.WHITE_MATTED_FUR_SLAB);
		addRemapBlockWithItem("white_packed_fur_stairs", "lotr", ExtendedBlocks.WHITE_MATTED_FUR_STAIRS, ExtendedItems.WHITE_MATTED_FUR_STAIRS);
		addRemapBlockWithItem("white_packed_fur_wall", "lotr", ExtendedBlocks.WHITE_MATTED_FUR_WALL, ExtendedItems.WHITE_MATTED_FUR_WALL);
		addRemapBlockWithItem("silver_packed_fur", "lotr", ExtendedBlocks.SILVER_MATTED_FUR, ExtendedItems.SILVER_MATTED_FUR);
		addRemapBlockWithItem("silver_packed_fur_slab", "lotr", ExtendedBlocks.SILVER_MATTED_FUR_SLAB, ExtendedItems.SILVER_MATTED_FUR_SLAB);
		addRemapBlockWithItem("silver_packed_fur_stairs", "lotr", ExtendedBlocks.SILVER_MATTED_FUR_STAIRS, ExtendedItems.SILVER_MATTED_FUR_STAIRS);
		addRemapBlockWithItem("silver_packed_fur_wall", "lotr", ExtendedBlocks.SILVER_MATTED_FUR_WALL, ExtendedItems.SILVER_MATTED_FUR_WALL);
		addRemapBlockWithItem("gray_packed_fur", "lotr", ExtendedBlocks.GRAY_MATTED_FUR, ExtendedItems.GRAY_MATTED_FUR);
		addRemapBlockWithItem("gray_packed_fur_slab", "lotr", ExtendedBlocks.GRAY_MATTED_FUR_SLAB, ExtendedItems.GRAY_MATTED_FUR_SLAB);
		addRemapBlockWithItem("gray_packed_fur_stairs", "lotr", ExtendedBlocks.GRAY_MATTED_FUR_STAIRS, ExtendedItems.GRAY_MATTED_FUR_STAIRS);
		addRemapBlockWithItem("gray_packed_fur_wall", "lotr", ExtendedBlocks.GRAY_MATTED_FUR_WALL, ExtendedItems.GRAY_MATTED_FUR_WALL);
		addRemapBlockWithItem("black_packed_fur", "lotr", ExtendedBlocks.BLACK_MATTED_FUR, ExtendedItems.BLACK_MATTED_FUR);
		addRemapBlockWithItem("black_packed_fur_slab", "lotr", ExtendedBlocks.BLACK_MATTED_FUR_SLAB, ExtendedItems.BLACK_MATTED_FUR_SLAB);
		addRemapBlockWithItem("black_packed_fur_stairs", "lotr", ExtendedBlocks.BLACK_MATTED_FUR_STAIRS, ExtendedItems.BLACK_MATTED_FUR_STAIRS);
		addRemapBlockWithItem("black_packed_fur_wall", "lotr", ExtendedBlocks.BLACK_MATTED_FUR_WALL, ExtendedItems.BLACK_MATTED_FUR_WALL);
		addRemapBlockWithItem("packed_fur", "lotr", ExtendedBlocks.BROWN_MATTED_FUR, ExtendedItems.BROWN_MATTED_FUR);
		addRemapBlockWithItem("packed_fur_slab", "lotr", ExtendedBlocks.BROWN_MATTED_FUR_SLAB, ExtendedItems.BROWN_MATTED_FUR_SLAB);
		addRemapBlockWithItem("packed_fur_stairs", "lotr", ExtendedBlocks.BROWN_MATTED_FUR_STAIRS, ExtendedItems.BROWN_MATTED_FUR_STAIRS);
		addRemapBlockWithItem("packed_fur_wall", "lotr", ExtendedBlocks.BROWN_MATTED_FUR_WALL, ExtendedItems.BROWN_MATTED_FUR_WALL);

		//Beta 1.10.0-alpha-2 Renamed Morgul Armor to Morgul Vassal Armor
		addRemapItem("morgul_helmet", "lotr", ExtendedItems.MORGUL_VASSAL_HELMET);
		addRemapItem("morgul_chestplate", "lotr", ExtendedItems.MORGUL_VASSAL_CHESTPLATE);
		addRemapItem("morgul_leggings", "lotr", ExtendedItems.MORGUL_VASSAL_LEGGINGS);
		addRemapItem("morgul_boots", "lotr", ExtendedItems.MORGUL_VASSAL_BOOTS);
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
	private static void addRemapEntity(String previousMapping, String previousNamespace,  Supplier<? extends EntityType<?>> remapEntity) {
		ENTITIES.put(new ResourceLocation(previousNamespace, previousMapping), remapEntity.get());
	}
}
