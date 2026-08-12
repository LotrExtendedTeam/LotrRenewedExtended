package com.github.maximuslotro.lotrrextended.common.datafixers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTREntities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings("deprecation")
public class ExtendedMissingMappings {
	private static final String MODID = "lotrextended";
	public static final Map<ResourceLocation, Block> BLOCKS = new HashMap<>();
	public static final Map<ResourceLocation, Item> ITEMS = new HashMap<>();
	public static final Map<ResourceLocation, EntityType<?>> ENTITIES = new HashMap<>();
	public static final Map<String, String> BLOCK_ENTITIES = new HashMap<>();
	public static final List<ResourceLocation> IGNORE_MAPPINGS = new ArrayList<>();

	private static boolean hasMappingsBeenCreated = false;

	public static void ensureMappingsCreated() {
		if (!hasMappingsBeenCreated) {
			createMappings();
			createMappingsIgnore();
		}
	}

	public static void onMissingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
		ExtendedMissingMappings.fixMappings(event, ExtendedMissingMappings.BLOCKS, ForgeRegistries.BLOCKS, "block");
	}

	public static void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event) {
		ExtendedMissingMappings.fixMappings(event, ExtendedMissingMappings.ITEMS, ForgeRegistries.ITEMS, "item");
	}

	public static void onMissingEntityMappings(RegistryEvent.MissingMappings<EntityType<?>> event) {
		ExtendedMissingMappings.fixMappings(event, ExtendedMissingMappings.ENTITIES, ForgeRegistries.ENTITIES, "entity");
	}


	public static <T extends IForgeRegistryEntry<T>> void fixMappings(MissingMappings<T> event, Map<ResourceLocation, T> explicitOverrides, IForgeRegistry<T> registry, String typeName) {
		ensureMappingsCreated();
		for (Mapping<T> mapping : event.getAllMappings()) {
			ResourceLocation entryLocation = mapping.key;
			// Check explicit manual overrides first
			if (explicitOverrides.containsKey(entryLocation)) {
				T target = explicitOverrides.get(entryLocation);
				mapping.remap(target);
				ExtendedLog.info("Remapped {} (explicit override), old id={}, new id={}", typeName, entryLocation, target.getRegistryName());
				continue;
			}
			// Handle dynamic Namespace Fallback: Remap lotr:x -> lotrextended:x
			if (entryLocation.getNamespace().equals("lotr") || entryLocation.getNamespace().equals(MODID)) {
				ResourceLocation newLoc = new ResourceLocation(MODID, entryLocation.getPath());
				if (registry.containsKey(newLoc)) {
					T target = registry.getValue(newLoc);
					if (target != null) {
						mapping.remap(target);
						ExtendedLog.info("Auto-remapped {} namespace, old id={}, new id={}", typeName, entryLocation, newLoc);
						continue;
					}
				}
				if(IGNORE_MAPPINGS.contains(entryLocation)) {
					ExtendedLog.info("Ignored remapping {} {}", typeName, entryLocation);
					mapping.ignore();
					continue;
				}
				ExtendedLog.warn("Failed remapping lotr {} {}", typeName, entryLocation);
			}
		}
	}

	private static void createMappingsIgnore() {
		addIgnoreRemap("entity_spawner_hobbit_bartender", "lotr");
		addIgnoreRemap("entity_spawner_hobbit_farmer", "lotr");
		addIgnoreRemap("group_entity_spawner_ranger_camp_animals", "lotr");
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
		//addRemapBlock("entity_spawnerhobbit_farmer", "lotr", ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_FARMER.getSerializedName()));
		//addRemapBlock("entity_spawnerhobbit_bartender", "lotr", ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()));
		// REMOVED IN 1.11

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

		//Beta 1.10.3 Renamed Dunlending Shield to Dunlending Shield
		addRemapItem("dunlanding_shield", "lotr", ExtendedItems.DUNLENDING_SHIELD);

		//Beta 1.11.0 Renamed Large Pot Morgul Flower id path
		addRemapBlock("large_pot_morgum_flower", "lotr", ExtendedBlocks.LARGE_FLOWER_POT_MORGUL_FLOWER);
		//BEta 1.11.0 Remaped tile entities
		addRemapBlockEntity("stone_chest_tileentity", "lotr", ExtendedTileEntities.STONE_CHEST);
		addRemapBlockEntity("straw_bed_tileentity", "lotr", ExtendedTileEntities.STRAW_BED);
		addRemapBlockEntity("orc_bed_tileentity", "lotr", ExtendedTileEntities.ORC_BED);
		addRemapBlockEntity("black_fur_bed_tileentity", "lotr", ExtendedTileEntities.BLACK_FUR_BED);
		addRemapBlockEntity("fur_bed_tileentity", "lotr", ExtendedTileEntities.FUR_BED);
		addRemapBlockEntity("gray_fur_bed_tileentity", "lotr", ExtendedTileEntities.GRAY_FUR_BED);
		addRemapBlockEntity("silver_fur_bed_tileentity", "lotr", ExtendedTileEntities.SILVER_FUR_BED);
		addRemapBlockEntity("white_fur_bed_tileentity", "lotr", ExtendedTileEntities.WHITE_FUR_BED);
		addRemapBlockEntity("no_render_tileentity", "lotr", ExtendedTileEntities.NORENDER);
		addRemapBlockEntity("bookshelf_tileentity", "lotr", ExtendedTileEntities.BOOKSHELF);
		addRemapBlockEntity("weapon_rack_tileentity", "lotr", ExtendedTileEntities.WEAPON_RACK);
		addRemapBlockEntity("reed_basket_tileentity", "lotr", ExtendedTileEntities.REED_BASKET);
		addRemapBlockEntity("orc_barrel_tileentity", "lotr", ExtendedTileEntities.ORC_BARREL);
		addRemapBlockEntity("stone_grinder_tileentity", "lotr", ExtendedTileEntities.STONE_GRINDER);
		addRemapBlockEntity("banner_s_tileentity", "lotr", ExtendedTileEntities.BANNER_STANDING);
		addRemapBlockEntity("banner_h_tileentity", "lotr", ExtendedTileEntities.BANNER_HANGING);
		addRemapBlockEntity("grass_void_tileentity", "lotr", ExtendedTileEntities.GRASS_VOID);
		addRemapBlockEntity("stone_engraving", "lotr", ExtendedTileEntities.STONE_SIGN);
		addRemapBlockEntity("dwarven_bed_tileentity", "lotr", ExtendedTileEntities.DWARVEN_BED);
		addRemapBlockEntity("hoist_tileentity", "lotr", ExtendedTileEntities.HOIST);
		addRemapBlockEntity("large_pot_tileentity", "lotr", ExtendedTileEntities.LARGE_POT);
		addRemapBlockEntity("chimney_pot_tileentity", "lotr", ExtendedTileEntities.CHIMNEY_POT);
		addRemapBlockEntity("high_elven_bed_tileentity", "lotr", ExtendedTileEntities.HIGH_ELVEN_BED);
		addRemapBlockEntity("crucible_tileentity", "lotr", ExtendedTileEntities.CRUCIBLE);
	}

	private static void addIgnoreRemap(String previousMapping, String previousNamespace) {
		IGNORE_MAPPINGS.add(new ResourceLocation(previousNamespace, previousMapping));
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
	private static void addRemapBlockEntity(String previousMapping, String previousNamespace,  Supplier<? extends TileEntityType<?>> remapBlockEntity) {
		BLOCK_ENTITIES.put(new ResourceLocation(previousNamespace, previousMapping).toString(), remapBlockEntity.get().getRegistryName().toString());
	}
}
