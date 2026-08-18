package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;

public class ExtendedSaplingBlock extends SaplingBlock {
	public ExtendedSaplingBlock(Tree pTreeGrower, Properties pProperties) {
		super(pTreeGrower, pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, 0.3F);
	}
}