package com.github.maximuslotro.lotrrextended.common.utils;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

/**
 * A standalone implementation of {@link BlockItem} 
 * */
public class ExtendedPlacementUtils {

	public static boolean place(Block blockToPlace, BlockItemUseContext blockitemusecontext) {
		if (!blockitemusecontext.canPlace()) {
			return false;
		} else {
			BlockState blockstate = getPlacementState(blockToPlace, blockitemusecontext);
			if (blockstate == null) {
				return false;
			} else if (!placeBlock(blockitemusecontext, blockstate)) {
				return false;
			} else {
				BlockPos blockpos = blockitemusecontext.getClickedPos();
				World world = blockitemusecontext.getLevel();
				PlayerEntity playerentity = blockitemusecontext.getPlayer();
				ItemStack itemstack = blockitemusecontext.getItemInHand();
				BlockState blockstate1 = world.getBlockState(blockpos);
				Block block = blockstate1.getBlock();
				if (block == blockstate.getBlock()) {
					//blockstate1 = this.updateBlockStateFromTag(blockpos, world, itemstack, blockstate1);
					//this.updateCustomBlockEntityTag(blockpos, world, playerentity, itemstack, blockstate1);
					block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
					if (playerentity instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
					}
				}

				SoundType soundtype = blockstate1.getSoundType(world, blockpos, blockitemusecontext.getPlayer());
				world.playSound(playerentity, blockpos, getPlaceSound(blockstate1, world, blockpos, blockitemusecontext.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if (playerentity == null || !playerentity.abilities.instabuild) {
					itemstack.shrink(1);
				}

				return true;
			}
		}
	}

	protected static SoundEvent getPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity entity) {
		return state.getSoundType(world, pos, entity).getPlaceSound();
	}

	protected static boolean placeBlock(BlockItemUseContext pContext, BlockState pState) {
		return pContext.getLevel().setBlock(pContext.getClickedPos(), pState, 11);
	}
	@Nullable
	protected static BlockState getPlacementState(Block blockToPlace, BlockItemUseContext pContext) {
		BlockState blockstate = blockToPlace.getStateForPlacement(pContext);
		return blockstate != null && canPlace(pContext, blockstate) ? blockstate : null;
	}

	protected static boolean canPlace(BlockItemUseContext pContext, BlockState pState) {
		PlayerEntity playerentity = pContext.getPlayer();
		ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.empty() : ISelectionContext.of(playerentity);
		return pState.canSurvive(pContext.getLevel(), pContext.getClickedPos()) && pContext.getLevel().isUnobstructed(pState, pContext.getClickedPos(), iselectioncontext);
	}
}
