package com.github.maximuslotro.lotrrextended.client.render.color;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class ExtendedBlockItemColors {
	public static void makeBlockColors(ColorHandlerEvent.Block event) {
		BlockColors colors = event.getBlockColors();
		IBlockColor naturalTint = (state, lightReader, pos, tintIndex) -> tintIndex != 0 ? -1 : (lightReader != null && pos != null) ? BiomeColors.getAverageGrassColor(lightReader, pos) : GrassColors.get(0.5D, 1.0D);
		colors.register(naturalTint, ExtendedBlocks.GRASS_BLOCK_SLAB.get());
		colors.register(naturalTint, ExtendedBlocks.MOSS_BLOCK.get());
		colors.register(naturalTint, ExtendedBlocks.MOSS_CARPET.get());
		colors.register(naturalTint, ExtendedBlocks.LARGE_FLOWER_POT_FERNSPROUT.get());
		colors.register(naturalTint, ExtendedBlocks.LARGE_FLOWER_POT_NETTLES.get());
		colors.register(naturalTint, ExtendedBlocks.LARGE_FLOWER_POT_THISTLE.get());
		colors.register(naturalTint, ExtendedBlocks.LARGE_FLOWER_POT_FERN.get());
		colors.register(naturalTint, ExtendedBlocks.LARGE_FLOWER_POT_FOUR_CLOVER.get());
		colors.register(naturalTint, ExtendedBlocks.LARGE_FLOWER_POT_CLOVER.get());
	}

	public static void makeItemsColors(ColorHandlerEvent.Item event) {
		BlockColors blockColors = event.getBlockColors();
		ItemColors itemColors = event.getItemColors();
		IItemColor blockTinter = (itemstack, tintIndex) -> {
			BlockState state = ((BlockItem) itemstack.getItem()).getBlock().defaultBlockState();
			return blockColors.getColor(state, null, null, tintIndex);
		};
		itemColors.register(blockTinter, ExtendedBlocks.GRASS_BLOCK_SLAB.get());
		itemColors.register(blockTinter, ExtendedBlocks.MOSS_BLOCK.get());
		itemColors.register(blockTinter, ExtendedBlocks.MOSS_CARPET.get());
		IItemColor dyedArmor = (itemstack, tintIndex) -> {
			return tintIndex > 0 ? -1 : ((IDyeableArmorItem)itemstack.getItem()).getColor(itemstack);
		};
		itemColors.register(dyedArmor, ExtendedItems.LEATHER_HAT.get());
	}
}
