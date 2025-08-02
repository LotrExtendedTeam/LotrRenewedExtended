package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedHoistTileEntity;
import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedHoistBlock extends Block {

	public ExtendedHoistBlock(Properties props) {
		super(props);
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (!world.isClientSide) {
			return ActionResultType.CONSUME;
		} else {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof ExtendedHoistTileEntity) {
				if(player.isCreative()) {
					ExtendedClientsideSeparatedScreenHandler.openHoistGUI((ExtendedHoistTileEntity)tileentity);
				}
			}
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ExtendedHoistTileEntity();
	}
}
