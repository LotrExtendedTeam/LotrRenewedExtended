package lotr.common.biome;

import lotr.common.world.biome.MorgulValeBiome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class ExtendedMorgulValeRiverBiome extends MorgulValeBiome implements ExtendedBiomeSetter {
	public ExtendedMorgulValeRiverBiome() {
		super(false);
	}

	@Override
	public boolean isRiver() {
		return true;
	}

	@Override
	public Builder getExtendedBuilder() {
		return new Builder().precipitation(RainType.NONE).biomeCategory(Category.RIVER).depth(-0.75F).scale(0.0F).temperature(0.8F).downfall(0.4F);
	}
}