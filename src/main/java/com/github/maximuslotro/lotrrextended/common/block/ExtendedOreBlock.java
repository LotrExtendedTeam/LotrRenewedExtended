package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;
import java.util.function.ToIntFunction;

import net.minecraft.block.OreBlock;

public class ExtendedOreBlock extends OreBlock {
	private final ToIntFunction<Random> xpFunction;

	public ExtendedOreBlock(Properties properties, ToIntFunction<Random> pXPFunction) {
		super(properties);
		this.xpFunction = pXPFunction;
	}

	@Override
	public int xpOnDrop(final Random rand) {
		return this.xpFunction.applyAsInt(rand);
	}
}
