package com.github.maximuslotro.lotrrextended.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class ExtendedStoneBlock extends Block{
	public ExtendedStoneBlock(Supplier<Block> block) {
		this(Properties.copy(block.get()));
	}

	public ExtendedStoneBlock(Properties props) {
		super(props);
	}

	public ExtendedStoneBlock(MaterialColor color) {
		this(Properties.of(Material.STONE, color).requiresCorrectToolForDrops().strength(1.5F, 6.0F));
	}
}
