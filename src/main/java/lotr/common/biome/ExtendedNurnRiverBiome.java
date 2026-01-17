package lotr.common.biome;

import lotr.common.world.biome.NurnBiome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class ExtendedNurnRiverBiome extends NurnBiome {
	public ExtendedNurnRiverBiome() {
		super(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.RIVER).depth(-0.75F).scale(0.0F).temperature(0.8F).downfall(0.5F), false);
	}

	@Override
	public boolean isRiver() {
		return true;
	}
}