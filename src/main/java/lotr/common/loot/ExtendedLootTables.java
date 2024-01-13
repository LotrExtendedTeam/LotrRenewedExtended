package lotr.common.loot;

import java.util.Set;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class ExtendedLootTables {
	private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
	public static final ResourceLocation HOBBIT_LARDER = register("chests/hobbit_larder");
	public static final ResourceLocation HOBBIT_LIVING = register("chests/hobbit_living");
	public static final ResourceLocation HOBBIT_TREASURE = register("chests/hobbit_treasure");

	public static void init() {}

	private static ResourceLocation register(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, location));
	}

	private static ResourceLocation register(ResourceLocation location) {
		if (LOCATIONS.add(location)) {
			return location;
		} else {
			throw new IllegalArgumentException(location + " is already a registered built-in loot table");
		}
	}

	public static Set<ResourceLocation> getLocations() {
		return LOCATIONS;
	}
}
