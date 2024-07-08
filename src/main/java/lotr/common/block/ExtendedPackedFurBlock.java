package lotr.common.block;

import com.github.maximuslotro.lotrrextended.common.enums.WargFurType;

public class ExtendedPackedFurBlock extends BundleBlock {
	private final WargFurType wargFurType;

	public ExtendedPackedFurBlock(WargFurType wargFurType) {
		super(wargFurType.getDyeColor().getMaterialColor());
		this.wargFurType = wargFurType;
	}

	public WargFurType getWargFurType() {
		return this.wargFurType;
	}
}
