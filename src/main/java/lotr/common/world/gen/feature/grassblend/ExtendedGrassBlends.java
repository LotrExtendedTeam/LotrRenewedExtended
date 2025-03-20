package lotr.common.world.gen.feature.grassblend;

public class ExtendedGrassBlends extends GrassBlends{
	public static final SingleGrassBlend BARROW_DOWNS_GRASS;

	static {
		BARROW_DOWNS_GRASS  = SingleGrassBlend.of(GRASS_CONFIG, 500, SHORT_GRASS_CONFIG, 100, CLOVER_CONFIG, 250,
				THISTLE_CONFIG, 5, NETTLES_CONFIG, 10, PURPLE_MOOR_GRASS_CONFIG, 40, RED_MOOR_GRASS_CONFIG, 120);
	}
}
