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
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		// Server Config synced to client on login
		if (this.material == Material.METAL && ExtendedServerConfig.makeMetalDoorsRequirePower.get()) {
			return ActionResultType.PASS;
		} else {
			blockState = blockState.cycle(OPEN);
			world.setBlock(blockPos, blockState, 2);
			if (blockState.getValue(WATERLOGGED)) {
				world.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
			}
			this.playSound(player, world, blockPos, blockState.getValue(OPEN));
			return ActionResultType.sidedSuccess(world.isClientSide);
		}
	}
}
