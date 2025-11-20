package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedWargFurType;

public class ExtendedPackedFurBlock extends ExtendedBundleBlock {
	private final ExtendedWargFurType wargFurType;

	public ExtendedPackedFurBlock(ExtendedWargFurType wargFurType) {
		super(wargFurType.getDyeColor().getMaterialColor());
		this.wargFurType = wargFurType;
	}

	public ExtendedWargFurType getWargFurType() {
		return this.wargFurType;
	}
}
