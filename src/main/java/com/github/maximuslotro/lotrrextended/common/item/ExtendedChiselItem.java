package com.github.maximuslotro.lotrrextended.common.item;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenStoneTextSignPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
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

	/**
	 * Damage the item instead of decreasing the stack size
	 * */
	@Override
	public ActionResultType place(BlockItemUseContext pContext) {
		if (!pContext.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockItemUseContext blockitemusecontext = this.updatePlacementContext(pContext);
			if (blockitemusecontext == null) {
				return ActionResultType.FAIL;
			} else {
				BlockState blockstate = this.getPlacementState(blockitemusecontext);
				if (blockstate == null) {
					return ActionResultType.FAIL;
				} else if (!this.placeBlock(blockitemusecontext, blockstate)) {
					return ActionResultType.FAIL;
				} else {
					BlockPos blockpos = blockitemusecontext.getClickedPos();
					World world = blockitemusecontext.getLevel();
					PlayerEntity playerentity = blockitemusecontext.getPlayer();
					ItemStack itemstack = blockitemusecontext.getItemInHand();
					BlockState blockstate1 = world.getBlockState(blockpos);
					Block block = blockstate1.getBlock();
					if (block == blockstate.getBlock()) {
						blockstate1 = this.updateBlockStateFromTag(blockpos, world, itemstack, blockstate1);
						this.updateCustomBlockEntityTag(blockpos, world, playerentity, itemstack, blockstate1);
						block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
						if (playerentity instanceof ServerPlayerEntity) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
						}
					}

					SoundType soundtype = blockstate1.getSoundType(world, blockpos, pContext.getPlayer());
					world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, pContext.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					if (playerentity == null || !playerentity.abilities.instabuild) {
						// Extended Start
						itemstack.hurtAndBreak(1, playerentity, (player) -> player.broadcastBreakEvent(playerentity.getUsedItemHand()));
						// Extended End
					}

					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		}
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

	private BlockState updateBlockStateFromTag(BlockPos pPos, World pLevel, ItemStack pStack, BlockState pState) {
		BlockState blockstate = pState;
		CompoundNBT compoundnbt = pStack.getTag();
		if (compoundnbt != null) {
			CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
			StateContainer<Block, BlockState> statecontainer = pState.getBlock().getStateDefinition();

			for(String s : compoundnbt1.getAllKeys()) {
				Property<?> property = statecontainer.getProperty(s);
				if (property != null) {
					String s1 = compoundnbt1.get(s).getAsString();
					blockstate = updateState(blockstate, property, s1);
				}
			}
		}

		if (blockstate != pState) {
			pLevel.setBlock(pPos, blockstate, 2);
		}

		return blockstate;
	}

	private static <T extends Comparable<T>> BlockState updateState(BlockState pState, Property<T> pProperty, String pValueIdentifier) {
		return pProperty.getValue(pValueIdentifier).map((p_219986_2_) -> {
			return pState.setValue(pProperty, p_219986_2_);
		}).orElse(pState);
	}
}
