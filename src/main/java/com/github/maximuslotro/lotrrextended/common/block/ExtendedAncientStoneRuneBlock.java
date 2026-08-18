package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ExtendedAncientStoneRuneBlock extends Block {
	private static final int maxTypes = 2;
	public static final IntegerProperty TYPE = IntegerProperty.create("rune_type", 1, maxTypes);

	public ExtendedAncientStoneRuneBlock(Properties pProps) {
		super(pProps);
		this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, 1));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(TYPE);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand pHand, BlockRayTraceResult p_225533_6_) {
		ItemStack itemstack = player.getItemInHand(pHand);
		if(itemstack.getItem()!=ExtendedItems.CHISEL.get()) {
			return ActionResultType.PASS;
		}
		int newType = state.getValue(TYPE) + 1;
		if(newType>maxTypes) {
			newType = 1;
		}
		world.setBlock(blockPos, state.setValue(TYPE, newType), 2);
		if (!player.abilities.instabuild) {
			itemstack.hurtAndBreak(1, player, (playerSend) -> { 
				playerSend.broadcastBreakEvent(pHand);
			});
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
	}
}
