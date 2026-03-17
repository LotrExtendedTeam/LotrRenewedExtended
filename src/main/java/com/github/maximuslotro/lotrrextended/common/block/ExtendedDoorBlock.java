package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ExtendedDoorBlock extends DoorBlock {

	public ExtendedDoorBlock(Properties p_i48413_1_) {
		super(p_i48413_1_);
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		// Server Config synced to client on login
		if (this.material == Material.METAL && ExtendedServerConfig.makeMetalDoorsRequirePower.get()) {
			return ActionResultType.PASS;
		} else {
			blockState = blockState.cycle(OPEN);
			world.setBlock(blockPos, blockState, 10);
			world.levelEvent(player, blockState.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), blockPos, 0);
			return ActionResultType.sidedSuccess(world.isClientSide);
		}
	}

	private int getCloseSound() {
		return this.material == Material.METAL ? 1011 : 1012;
	}

	private int getOpenSound() {
		return this.material == Material.METAL ? 1005 : 1006;
	}
}
