package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;

public class ExtendedConquestTableBlock extends Block{
	public static final BooleanProperty SEPIA = BooleanProperty.create("sepia");
	public ExtendedConquestTableBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(SEPIA, false));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext placeContext) {
		BlockState state = super.getStateForPlacement(placeContext);
		if(state !=null && placeContext.getPlayer() != null && placeContext.getPlayer().isCrouching()) {
			return state.setValue(SEPIA, true);
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(SEPIA);
	}
}
