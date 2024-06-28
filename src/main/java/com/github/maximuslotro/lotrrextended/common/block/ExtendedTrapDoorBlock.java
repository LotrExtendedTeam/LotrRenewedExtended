package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.block.BlockState;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ExtendedTrapDoorBlock extends TrapDoorBlock {

	public ExtendedTrapDoorBlock(Properties p_i48307_1_) {
		super(p_i48307_1_);
	}

	@Override
	public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (this.material == Material.METAL && ExtendedServerConfig.makeMetalDoorsRequirePower.get()) {
			return ActionResultType.PASS;
		} else {
			p_225533_1_ = p_225533_1_.cycle(OPEN);
			p_225533_2_.setBlock(p_225533_3_, p_225533_1_, 2);
			if (p_225533_1_.getValue(WATERLOGGED)) {
				p_225533_2_.getLiquidTicks().scheduleTick(p_225533_3_, Fluids.WATER, Fluids.WATER.getTickDelay(p_225533_2_));
			}
			this.playSound(p_225533_4_, p_225533_2_, p_225533_3_, p_225533_1_.getValue(OPEN));
			return ActionResultType.sidedSuccess(p_225533_2_.isClientSide);
		}
	}
}
