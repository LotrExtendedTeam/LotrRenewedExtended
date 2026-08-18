package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.util.math.MathHelper;

public class ExtendedOreRemainsBlock extends ExtendedOreBlock {

	public ExtendedOreRemainsBlock(Properties properties) {
		super(properties, rand -> MathHelper.nextInt(rand, 0, 1));
	}
}
