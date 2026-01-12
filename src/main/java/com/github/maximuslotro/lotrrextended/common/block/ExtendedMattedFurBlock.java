package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedWargFurType;

public class ExtendedMattedFurBlock extends ExtendedBundleBlock {
	private final ExtendedWargFurType wargFurType;

	public ExtendedMattedFurBlock(ExtendedWargFurType wargFurType) {
		super(wargFurType.getDyeColor().getMaterialColor());
		this.wargFurType = wargFurType;
	}

	public ExtendedWargFurType getWargFurType() {
		return this.wargFurType;
	}
}
