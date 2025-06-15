package com.github.maximuslotro.lotrrextended.common.item;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenStoneTextSignPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedChiselItem extends BlockItem {

	public ExtendedChiselItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
	}

	@Override
	public String getDescriptionId() {
		return this.getOrCreateDescriptionId();
	}

	@Override
	protected BlockState getPlacementState(BlockItemUseContext pContext) {
		BlockState blockstate1 = null;
		IWorldReader iworldreader = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();

		for(Direction direction : pContext.getNearestLookingDirections()) {
			if (direction.getAxis().isHorizontal()) {
				BlockState blockstate2 = this.getBlock().getStateForPlacement(pContext);
				if (blockstate2 != null && blockstate2.canSurvive(iworldreader, blockpos)) {
					blockstate1 = blockstate2;
					break;
				}
			}
		}

		return blockstate1 != null && iworldreader.isUnobstructed(blockstate1, blockpos, ISelectionContext.empty()) ? blockstate1 : null;
	}

	@Override
	protected boolean updateCustomBlockEntityTag(BlockPos pPos, World pLevel, @Nullable PlayerEntity pPlayer, ItemStack pStack, BlockState pState) {
		boolean flag = super.updateCustomBlockEntityTag(pPos, pLevel, pPlayer, pStack, pState);
		if (!pLevel.isClientSide && !flag && pPlayer != null) {
			((ExtendedStoneTextSignTileEntity)pLevel.getBlockEntity(pPos)).setAllowedPlayerEditor(pPlayer);
			ExtendedPacketHandler.sendToClient(new ExtendedSOpenStoneTextSignPacket(((ExtendedStoneTextSignTileEntity)pLevel.getBlockEntity(pPos)).getBlockPos()), (ServerPlayerEntity) pPlayer);
		}

		return flag;
	}
}
