package com.github.maximuslotro.lotrrextended.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;

public class ExtendedStandingSignBlock extends StandingSignBlock {

	public ExtendedStandingSignBlock(Supplier<Block> pPlanksBlock, WoodType pWoodType) {
		super(Properties.of(Material.WOOD, pPlanksBlock.get().defaultMaterialColor()).noCollission().strength(1.0f).sound(SoundType.WOOD), pWoodType);
		ExtendedSignTileEntitySetup.add(this);
	}
}