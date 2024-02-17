package lotr.common.item;

import com.github.maximuslotro.lotrExtended.common.enums.CoinType;

import net.minecraft.item.Item;

public class ExtendedSilverCoin extends Item {
	public final CoinType type;
	public ExtendedSilverCoin(Properties p_i48487_1_, CoinType type) {
		super(p_i48487_1_);
		this.type = type;
	}
}
