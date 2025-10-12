package com.github.maximuslotro.lotrrextended.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ExtendedCakeBlock extends CakeBlock{

	public ExtendedCakeBlock(Properties p_i48434_1_, float compostValue) {
		super(p_i48434_1_);
		if(compostValue>0) {
			CompostingHelper.prepareCompostable(this, compostValue);
		}
	}
}
