package com.github.maximuslotro.lotroExtended.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;

public class ExtendedTags {
	public static void init() {
		Blocks.init();
		Items.init();
	}

	public static class Blocks {
		public static void init() {}
		public static final INamedTag<Block> GREEN_FIRE_BASE_BLOCKS = bind("lotrextended", "green_fire_base_blocks");


		private static INamedTag<Block> bind(String namespace, String name) {
			return BlockTags.bind(new ResourceLocation(namespace, name).toString());
		}
	}

	public static class Items {
		public static void init() {}
		private static INamedTag<Item> bind(String namespace, String name) {
			return ItemTags.bind(new ResourceLocation(namespace, name).toString());
		}
	}
}
