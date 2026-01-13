package lotr.common.biome;

import lotr.common.world.biome.MordorBiome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class ExtendedMordorRiverBiome extends MordorBiome {
	public ExtendedMordorRiverBiome() {
		super(new Builder().precipitation(RainType.NONE).biomeCategory(Category.RIVER).depth(-0.75F).scale(0.0F).temperature(1.5F).downfall(0.0F), false);
		biomeColors.setGrass(7496538).setFoliage(5987138).setSky(6313301).setClouds(6705223).setFog(6701621);
	}

	@Override
	public boolean isRiver() {
		return true;
	}
}