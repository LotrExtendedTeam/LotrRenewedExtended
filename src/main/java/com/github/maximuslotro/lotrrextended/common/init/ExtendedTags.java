package com.github.maximuslotro.lotrrextended.common.init;

import lotr.common.init.LOTRTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ExtendedTags {
	public static void init() {
		Blocks.init();
		Items.init();
		// Bootstrap start Renewed's tags, to prevent "tag lotr:pipe_magic_smoke_ingredients was used before it was bound" like errors
		LOTRTags.Blocks.APPLE_LOGS.getClass();
		LOTRTags.Items.APPLE_LOGS.getClass();
	}

	public static class Blocks {
		public static void init() {}
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_GALVORN = BlockTags.bind("forge:storage_blocks/galvorn");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_ORC_STEEL = BlockTags.bind("forge:storage_blocks/orc_steel");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_BLACK_URUK_STEEL = BlockTags.bind("forge:storage_blocks/black_uruk_steel");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_GILDED_IRON = BlockTags.bind("forge:storage_blocks/gilded_iron");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_MORGUL_STEEL = BlockTags.bind("forge:storage_blocks/morgul_steel");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_DWARVEN_STEEL = BlockTags.bind("forge:storage_blocks/dwarven_steel");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_ELVEN_STEEL = BlockTags.bind("forge:storage_blocks/elven_steel");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_MITHRIL = BlockTags.bind("forge:storage_blocks/mithril");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_URUK_STEEL = BlockTags.bind("forge:storage_blocks/uruk_steel");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_TOPAZ = BlockTags.bind("forge:storage_blocks/lotr_topaz");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_AMETHYST = BlockTags.bind("forge:storage_blocks/lotr_amethyst");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_SAPPHIRE = BlockTags.bind("forge:storage_blocks/lotr_sapphire");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_RUBY = BlockTags.bind("forge:storage_blocks/lotr_ruby");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_AMBER = BlockTags.bind("forge:storage_blocks/lotr_amber");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_DIAMOND = BlockTags.bind("forge:storage_blocks/lotr_diamond");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_OPAL = BlockTags.bind("forge:storage_blocks/lotr_opal");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_EMERALD = BlockTags.bind("forge:storage_blocks/lotr_emerald");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_PEARL = BlockTags.bind("forge:storage_blocks/lotr_pearl");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_CORAL = BlockTags.bind("forge:storage_blocks/lotr_coral");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_DEAD_CORAL = BlockTags.bind("forge:storage_blocks/lotr_dead_coral");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_DURNOR = BlockTags.bind("forge:storage_blocks/durnor");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_EDHELVIR = BlockTags.bind("forge:storage_blocks/edhelvir");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_GULDURIL = BlockTags.bind("forge:storage_blocks/gulduril");

		public static final ITag.INamedTag<Block> COPPER_ORE = BlockTags.bind("forge:ores/copper");
		public static final ITag.INamedTag<Block> NITER_ORE = BlockTags.bind("forge:ores/niter");
		public static final ITag.INamedTag<Block> SALT_ORE = BlockTags.bind("forge:ores/salt");
		public static final ITag.INamedTag<Block> SILVER_ORE = BlockTags.bind("forge:ores/silver");
		public static final ITag.INamedTag<Block> SULFUR_ORE = BlockTags.bind("forge:ores/sulfur");
		public static final ITag.INamedTag<Block> TIN_ORE = BlockTags.bind("forge:ores/tin");

		public static final ITag.INamedTag<Block> GLOWSTONE_ORE = BlockTags.bind("forge:ores/glowstone");
		public static final ITag.INamedTag<Block> MITHRIL_ORE = BlockTags.bind("forge:ores/mithril");
		public static final ITag.INamedTag<Block> MORGUL_IRON_ORE = BlockTags.bind("forge:ores/morgul_iron");
		public static final ITag.INamedTag<Block> TOPAZ_ORE = BlockTags.bind("forge:ores/lotr_topaz");
		public static final ITag.INamedTag<Block> AMETHYST_ORE = BlockTags.bind("forge:ores/lotr_amethyst");
		public static final ITag.INamedTag<Block> SAPPHIRE_ORE = BlockTags.bind("forge:ores/lotr_sapphire");
		public static final ITag.INamedTag<Block> RUBY_ORE = BlockTags.bind("forge:ores/lotr_ruby");
		public static final ITag.INamedTag<Block> AMBER_ORE = BlockTags.bind("forge:ores/lotr_amber");
		public static final ITag.INamedTag<Block> DIAMOND_ORE = BlockTags.bind("forge:ores/lotr_diamond");
		public static final ITag.INamedTag<Block> OPAL_ORE = BlockTags.bind("forge:ores/lotr_opal");
		public static final ITag.INamedTag<Block> EMERALD_ORE = BlockTags.bind("forge:ores/lotr_emerald");
		public static final ITag.INamedTag<Block> DURNOR_ORE = BlockTags.bind("forge:ores/durnor");
		public static final ITag.INamedTag<Block> EDHELVIR_ORE = BlockTags.bind("forge:ores/edhelvir");
		public static final ITag.INamedTag<Block> GULDURIL_ORE = BlockTags.bind("forge:ores/gulduril");

		public static final ITag.INamedTag<Block> SHEARABLE_BLOCKS = BlockTags.bind("forge:shearable");

		public static final ITag.INamedTag<Block> FUR_BLOCKS = BlockTags.bind("lotrextended:fur_blocks");
		public static final ITag.INamedTag<Block> GREEN_FIRE_BASE_BLOCKS = BlockTags.bind("lotrextended:green_fire_base_blocks");
		public static final ITag.INamedTag<Block> CHESTNUT_LOGS = BlockTags.bind("lotrextended:chestnut_logs");
		public static final ITag.INamedTag<Block> BANANA_LOGS = BlockTags.bind("lotrextended:banana_logs");
		public static final ITag.INamedTag<Block> PLUM_LOGS = BlockTags.bind("lotrextended:plum_logs");
		public static final ITag.INamedTag<Block> WILLOW_LOGS = BlockTags.bind("lotrextended:willow_logs");
		public static final ITag.INamedTag<Block> EBONY_LOGS = BlockTags.bind("lotrextended:ebony_logs");

		public static final ITag.INamedTag<Block> LARGE_FLOWER_POT_COMPATABLE = BlockTags.bind("lotrextended:large_flower_pot_compatable");

		public static final ITag.INamedTag<Block> MOSS_REPLACEABLE = BlockTags.bind("lotrextended:moss_replaceable");

		public static final ITag.INamedTag<Block> REMAINS_ORE = BlockTags.bind("lotrextended:remains_ores");

		public static final ITag.INamedTag<Block> FIXED_SIZE_PILLARS = BlockTags.bind("lotrextended:fixed_size_pillars");

		public static final ITag.INamedTag<Block> CHAINS = BlockTags.bind("lotrextended:chains");
		public static final ITag.INamedTag<Block> WOOL_BLOCKS = BlockTags.bind("lotrextended:wool_blocks");

		public static final ITag.INamedTag<Block> MIDDLE_EARTH_PLACEABLE_SURFACE = BlockTags.bind("lotrextended:middle_earth_surface_placeable");

		public static final ITag.INamedTag<Block> BASE_STONE_MIDDLE_EARTH = BlockTags.bind("lotrextended:base_stone_middle_earth");
		public static final ITag.INamedTag<Block> BASE_STONE_SLAB_MIDDLE_EARTH = BlockTags.bind("lotrextended:base_stone_slab_middle_earth");
		public static final ITag.INamedTag<Block> BASE_STONE_STAIRS_MIDDLE_EARTH = BlockTags.bind("lotrextended:base_stone_stair_middle_earth");
	}

	public static class Items {
		public static void init() {}
		public static final ITag.INamedTag<Item> COPPER_INGOTS = ItemTags.bind("forge:ingots/copper");
		public static final ITag.INamedTag<Item> TIN_INGOT = ItemTags.bind("forge:ingots/tin");
		public static final ITag.INamedTag<Item> BRONZE_INGOT = ItemTags.bind("forge:ingots/bronze");
		public static final ITag.INamedTag<Item> MORGUL_STEEL_INGOT = ItemTags.bind("forge:ingots/morgul_steel");
		public static final ITag.INamedTag<Item> ORC_STEEL_INGOT = ItemTags.bind("forge:ingots/orc_steel");
		public static final ITag.INamedTag<Item> MORGUL_INGOT = ItemTags.bind("forge:ingots/morgul_ingots");
		public static final ITag.INamedTag<Item> GALVORN_INGOT = ItemTags.bind("forge:ingots/galvorn");
		public static final ITag.INamedTag<Item> BLACK_URUK_STEEL_INGOT = ItemTags.bind("forge:ingots/black_uruk_steel");
		public static final ITag.INamedTag<Item> GILDED_IRON_INGOT = ItemTags.bind("forge:ingots/gilded_iron");
		public static final ITag.INamedTag<Item> DWARVEN_STEEL_INGOT = ItemTags.bind("forge:ingots/dwarven_steel");
		public static final ITag.INamedTag<Item> ELVEN_STEEL_INGOT = ItemTags.bind("forge:ingots/elven_steel");
		public static final ITag.INamedTag<Item> URUK_STEEL_INGOT = ItemTags.bind("forge:ingots/uruk_steel");
		public static final ITag.INamedTag<Item> SILVER_INGOT = ItemTags.bind("forge:ingots/silver");
		public static final ITag.INamedTag<Item> MITHRIL_INGOT = ItemTags.bind("forge:ingots/mithril");

		public static final ITag.INamedTag<Item> ORC_STEEL_NUGGET = ItemTags.bind("forge:nuggets/orc_steel");
		public static final ITag.INamedTag<Item> BRONZE_NUGGET = ItemTags.bind("forge:nuggets/bronze");
		public static final ITag.INamedTag<Item> SILVER_NUGGET = ItemTags.bind("forge:nuggets/silver");
		
		public static final ITag.INamedTag<Item> TOPAZ_GEMS = ItemTags.bind("forge:gems/lotr_topaz");
		public static final ITag.INamedTag<Item> AMETHYST_GEMS = ItemTags.bind("forge:gems/lotr_amethyst");
		public static final ITag.INamedTag<Item> SAPPHIRE_GEMS = ItemTags.bind("forge:gems/lotr_sapphire");
		public static final ITag.INamedTag<Item> RUBY_GEMS = ItemTags.bind("forge:gems/lotr_ruby");
		public static final ITag.INamedTag<Item> AMBER_GEMS = ItemTags.bind("forge:gems/lotr_amber");
		public static final ITag.INamedTag<Item> DIAMOND_GEMS = ItemTags.bind("forge:gems/lotr_diamond");
		public static final ITag.INamedTag<Item> OPAL_GEMS = ItemTags.bind("forge:gems/lotr_opal");
		public static final ITag.INamedTag<Item> EMERALD_GEMS = ItemTags.bind("forge:gems/lotr_emerald");
		public static final ITag.INamedTag<Item> PEARL_GEMS = ItemTags.bind("forge:gems/lotr_pearl");
		public static final ITag.INamedTag<Item> CORAL_GEMS = ItemTags.bind("forge:gems/lotr_coral");
		public static final ITag.INamedTag<Item> DEAD_CORAL_GEMS = ItemTags.bind("forge:gems/lotr_dead_coral");
		public static final ITag.INamedTag<Item> DURNOR_GEMS = ItemTags.bind("forge:gems/lotr_durnor");
		public static final ITag.INamedTag<Item> EDHELVIR_GEMS = ItemTags.bind("forge:gems/lotr_edhelvir");
		public static final ITag.INamedTag<Item> GULDURIL_GEMS = ItemTags.bind("forge:gems/lotr_gulduril");

		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_GALVORN = ItemTags.bind("forge:storage_blocks/galvorn");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_BLACK_URUK_STEEL = ItemTags.bind("forge:storage_blocks/black_uruk_steel");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_GILDED_IRON = ItemTags.bind("forge:storage_blocks/gilded_iron");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_ORC_STEEL = ItemTags.bind("forge:storage_blocks/orc_steel");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_MORGUL_STEEL = ItemTags.bind("forge:storage_blocks/morgul_steel");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_DWARVEN_STEEL = ItemTags.bind("forge:storage_blocks/dwarven_steel");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_ELVEN_STEEL = ItemTags.bind("forge:storage_blocks/elven_steel");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_MITHRIL = ItemTags.bind("forge:storage_blocks/mithril");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_URUK_STEEL = ItemTags.bind("forge:storage_blocks/uruk_steel");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_TOPAZ = ItemTags.bind("forge:storage_blocks/lotr_topaz");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_AMETHYST = ItemTags.bind("forge:storage_blocks/lotr_amethyst");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_SAPPHIRE = ItemTags.bind("forge:storage_blocks/lotr_sapphire");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_RUBY = ItemTags.bind("forge:storage_blocks/lotr_ruby");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_AMBER = ItemTags.bind("forge:storage_blocks/lotr_amber");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_DIAMOND = ItemTags.bind("forge:storage_blocks/lotr_diamond");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_OPAL = ItemTags.bind("forge:storage_blocks/lotr_opal");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_EMERALD = ItemTags.bind("forge:storage_blocks/lotr_emerald");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_PEARL = ItemTags.bind("forge:storage_blocks/lotr_pearl");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_CORAL = ItemTags.bind("forge:storage_blocks/lotr_coral");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_DEAD_CORAL = ItemTags.bind("forge:storage_blocks/lotr_dead_coral");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_DURNOR = ItemTags.bind("forge:storage_blocks/lotr_durnor");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_EDHELVIR = ItemTags.bind("forge:storage_blocks/lotr_edhelvir");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_GULDURIL = ItemTags.bind("forge:storage_blocks/lotr_gulduril");

		public static final ITag.INamedTag<Item> COPPER_ORE = ItemTags.bind("forge:ores/copper");
		public static final ITag.INamedTag<Item> NITER_ORE = ItemTags.bind("forge:ores/niter");
		public static final ITag.INamedTag<Item> SALT_ORE = ItemTags.bind("forge:ores/salt");
		public static final ITag.INamedTag<Item> SILVER_ORE = ItemTags.bind("forge:ores/silver");
		public static final ITag.INamedTag<Item> SULFUR_ORE = ItemTags.bind("forge:ores/sulfur");
		public static final ITag.INamedTag<Item> TIN_ORE = ItemTags.bind("forge:ores/tin");

		public static final ITag.INamedTag<Item> GLOWSTONE_ORE = ItemTags.bind("forge:ores/glowstone");
		public static final ITag.INamedTag<Item> MITHRIL_ORE = ItemTags.bind("forge:ores/mithril");
		public static final ITag.INamedTag<Item> MORGUL_IRON_ORE = ItemTags.bind("forge:ores/morgul_iron");
		public static final ITag.INamedTag<Item> TOPAZ_ORE = ItemTags.bind("forge:ores/lotr_topaz");
		public static final ITag.INamedTag<Item> AMETHYST_ORE = ItemTags.bind("forge:ores/lotr_amethyst");
		public static final ITag.INamedTag<Item> SAPPHIRE_ORE = ItemTags.bind("forge:ores/lotr_sapphire");
		public static final ITag.INamedTag<Item> RUBY_ORE = ItemTags.bind("forge:ores/lotr_ruby");
		public static final ITag.INamedTag<Item> AMBER_ORE = ItemTags.bind("forge:ores/lotr_amber");
		public static final ITag.INamedTag<Item> DIAMOND_ORE = ItemTags.bind("forge:ores/lotr_diamond");
		public static final ITag.INamedTag<Item> OPAL_ORE = ItemTags.bind("forge:ores/lotr_opal");
		public static final ITag.INamedTag<Item> EMERALD_ORE = ItemTags.bind("forge:ores/lotr_emerald");
		public static final ITag.INamedTag<Item> DURNOR_ORE = ItemTags.bind("forge:ores/durnor");
		public static final ITag.INamedTag<Item> EDHELVIR_ORE = ItemTags.bind("forge:ores/edhelvir");
		public static final ITag.INamedTag<Item> GULDURIL_ORE = ItemTags.bind("forge:ores/gulduril");

		public static final ITag.INamedTag<Item> ANVIL_IRON_BLOCK = ItemTags.bind("lotrextended:anvil_iron_block");
		public static final ITag.INamedTag<Item> ANVIL_IRON_INGOT = ItemTags.bind("lotrextended:anvil_iron_ingot");

		public static final ITag.INamedTag<Item> FURS = ItemTags.bind("forge:furs");

		public static final ITag.INamedTag<Item> CHESTNUT_LOGS = ItemTags.bind("lotrextended:chestnut_logs");
		public static final ITag.INamedTag<Item> BANANA_LOGS = ItemTags.bind("lotrextended:banana_logs");
		public static final ITag.INamedTag<Item> PLUM_LOGS = ItemTags.bind("lotrextended:plum_logs");
		public static final ITag.INamedTag<Item> WILLOW_LOGS = ItemTags.bind("lotrextended:willow_logs");
		public static final ITag.INamedTag<Item> EBONY_LOGS = ItemTags.bind("lotrextended:ebony_logs");

		public static final ITag.INamedTag<Item> WEAPONRACK_ITEMS = ItemTags.bind("lotrextended:weapon_rack_additional_items");
		public static final ITag.INamedTag<Item> WEAPONRACK_ITEMS_EXCLUDE = ItemTags.bind("lotrextended:weapon_rack_excluded_items");
		public static final ITag.INamedTag<Item> WEAPONRACK_ITEMS_FULL_SCALE = ItemTags.bind("lotrextended:weapon_rack_items_full_scale");

		public static final ITag.INamedTag<Item> DUCK_FOOD = ItemTags.bind("lotrextended:foods/duck_feedables");
		public static final ITag.INamedTag<Item> BOAR_FOOD = ItemTags.bind("lotrextended:foods/boar_feedables");
		public static final ITag.INamedTag<Item> BOAR_BREEDING = ItemTags.bind("lotrextended:foods/boar_breedables");
		public static final ITag.INamedTag<Item> HEDGEHOG_FOOD = ItemTags.bind("lotrextended:foods/hedgehog_food");

		public static final ITag.INamedTag<Item> COIN_ALL = ItemTags.bind("lotrextended:coins/coin_all");
		public static final ITag.INamedTag<Item> COIN_ONE = ItemTags.bind("lotrextended:coins/coin_one");
		public static final ITag.INamedTag<Item> COIN_TEN = ItemTags.bind("lotrextended:coins/coin_ten");
		public static final ITag.INamedTag<Item> COIN_HUNDRED = ItemTags.bind("lotrextended:coins/coin_hundred");

		public static final ITag.INamedTag<Item> TORCH_EXTINGUISHING = ItemTags.bind("lotrextended:torch_extinguishing");
		public static final ITag.INamedTag<Item> TORCH_LIGHTING = ItemTags.bind("lotrextended:torch_lighting");

		public static final ITag.INamedTag<Item> STONE_GRINDER_LUBRICANTS = ItemTags.bind("lotrextended:stone_grinder/lubricants");

		public static final ITag.INamedTag<Item> CHAINS = ItemTags.bind("lotrextended:chains");
		public static final ITag.INamedTag<Item> WOOL_BLOCKS = ItemTags.bind("lotrextended:wool_blocks");
		
		public static final ITag.INamedTag<Item> COIN_MINTING_ONE = ItemTags.bind("lotrextended:coin_mintables_one");
		public static final ITag.INamedTag<Item> COIN_MINTING_TEN = ItemTags.bind("lotrextended:coin_mintables_ten");
		public static final ITag.INamedTag<Item> COIN_MINTING_HUNDRED = ItemTags.bind("lotrextended:coin_mintables_hundred");

		public static final ITag.INamedTag<Item> PLACEABLE_BOOKS = ItemTags.bind("lotrextended:placeable_books");

		public static final ITag.INamedTag<Item> FLESH = ItemTags.bind("lotrextended:flesh");
		public static final ITag.INamedTag<Item> MORGOND_ROCKS = ItemTags.bind("lotrextended:morgond_rocks");

		public static final ITag.INamedTag<Item> CROPS_LEEK = ItemTags.bind("forge:crops/leek");
	}
}
