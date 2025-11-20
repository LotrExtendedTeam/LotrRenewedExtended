package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;

public class ExtendedLanternBlock extends LanternBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public ExtendedLanternBlock(int lightLevel) {
		super(Properties.of(Material.DECORATION).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(3.5F).sound(SoundType.LANTERN).lightLevel(ExtendedBlockParms.litBlockEmission(lightLevel)).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(LIT, Boolean.TRUE));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
		super.createBlockStateDefinition(stateBuilder);
		stateBuilder.add(LIT);
	}
}
