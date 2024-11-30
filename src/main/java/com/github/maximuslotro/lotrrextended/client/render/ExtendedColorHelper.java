package com.github.maximuslotro.lotrrextended.client.render;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class ExtendedColorHelper {
	public static void makeBlockColors(ColorHandlerEvent.Block event) {
		BlockColors colors = event.getBlockColors();
		IBlockColor naturalTint = (state, lightReader, pos, tintIndex) -> {
			return tintIndex != 0 ? -1 : (lightReader != null && pos != null) ? BiomeColors.getAverageGrassColor(lightReader, pos) : GrassColors.get(0.5D, 1.0D);
		};
		colors.register(naturalTint, ExtendedBlocks.GRASS_BLOCK_SLAB.get());
		colors.register(naturalTint, ExtendedBlocks.MOSS_BLOCK.get());
		colors.register(naturalTint, ExtendedBlocks.MOSS_CARPET.get());
	}

	public static void makeItemsColors(ColorHandlerEvent.Item event) {
		BlockColors colors = event.getBlockColors();
		ItemColors itemColors = event.getItemColors();
		IItemColor blockTinter = (itemstack, tintIndex) -> {
			BlockState state = ((BlockItem) itemstack.getItem()).getBlock().defaultBlockState();
			return colors.getColor(state, null, null, tintIndex);
		};
		itemColors.register(blockTinter, ExtendedBlocks.GRASS_BLOCK_SLAB.get());
		itemColors.register(blockTinter, ExtendedBlocks.MOSS_BLOCK.get());
		itemColors.register(blockTinter, ExtendedBlocks.MOSS_CARPET.get());
	}

	public static class ARGB32 {
		public static int alpha(int p_13656_) {
			return p_13656_ >>> 24;
		}

		public static int red(int p_13666_) {
			return p_13666_ >> 16 & 0xFF;
		}

		public static int green(int p_13668_) {
			return p_13668_ >> 8 & 0xFF;
		}

		public static int blue(int p_13670_) {
			return p_13670_ & 0xFF;
		}

		public static int color(int p_13661_, int p_13662_, int p_13663_, int p_13664_) {
			return p_13661_ << 24 | p_13662_ << 16 | p_13663_ << 8 | p_13664_;
		}

		public static int multiply(int p_13658_, int p_13659_) {
			return color(alpha(p_13658_) * alpha(p_13659_) / 255, red(p_13658_) * red(p_13659_) / 255, green(p_13658_) * green(p_13659_) / 255, blue(p_13658_) * blue(p_13659_) / 255);
		}
	}
}
