package lotr.common.world.spawning;

import java.util.*;

import javax.annotation.Nonnull;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import lotr.common.fac.Faction;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class WanderingTradersList {

	private static Map<ResourceLocation, Map<ExtendedWanderingTraderData, Integer>> BIOME_TRADER_WEIGHTS = new HashMap<>();
	private static Map<ResourceLocation, ExtendedWanderingTraderData> TRADER_REGISTRY = new HashMap<>();

	static {
		// Shire
		register("lotr:shire", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
		register("lotr:shire_moors", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
		register("lotr:shire_marshes", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
		register("lotr:shire_woodlands", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
		register("lotr:white_downs", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));

		register("lotr:blue_mountains", genMap("lotr:rivendell_wanderer", 25, "lotr:oddment_collector", 75));
		register("lotr:blue_mountains_foothills", genMap("lotr:rivendell_wanderer", 25, "lotr:oddment_collector", 75));

		// Eriador
		// TODO: Finish going through and updating these
		register("lotr:eriador", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
		register("lotr:eryn_vorn", genMap("lotr:galadhrim_wanderer", 35, "lotr:rivendell_wanderer", 35, "lotr:oddment_collector", 20, "lotr:blue_mountains_merchant", 10));
		register("lotr:hills_of_evendim", genMap("lotr:galadhrim_wanderer", 15, "lotr:rivendell_wanderer", 15, "lotr:oddment_collector", 40, "lotr:blue_mountains_merchant", 30));
		register("lotr:eriador_downs", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
		register("lotr:tower_hills", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
		register("lotr:minhiriath", genMap("lotr:galadhrim_wanderer", 20, "lotr:oddment_collector", 45, "lotr:blue_mountains_merchant", 35));
		register("lotr:swanfleet", new HashMap<>()); // Swanfleet has no wandering traders according to the wiki

		// Rivendell only gets Galadhrim traders
		register("lotr:rivendell", genRates("lotr:galadhrim_wanderer"));
		register("lotr:rivendell_hills", genRates("lotr:galadhrim_wanderer"));
		register("lotr:rivendell_valley", genRates("lotr:galadhrim_wanderer"));
		register("lotr:rivendell_cliffs", new HashMap<>());
		register("lotr:rivendell_river", new HashMap<>());

		register("lotr:gondor", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:further_gondor", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:field_of_cormallen", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:anorien", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:blackroot_vale", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:lamedon", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:lamedon_hills", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:pelargir", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:anfalas", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:lossarnach", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:pinnath_gelin", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		// higher chance of elves in Ithilien because of the forests
		register("lotr:ithilien", genMap("lotr:galadhrim_wanderer", 50, "lotr:oddment_collector", 50));
		register("lotr:lebennin", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		register("lotr:emyn_en_ernil", new HashMap<>()); // Emyn-En-Ernil has no traders in Legacy code
		register("lotr:dor_en_ernil", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));

		// Older elven regions
		register("lotr:lindon", genMap("lotr:galadhrim_wanderer", 40, "lotr:rivendell_wanderer", 35, "lotr:blue_mountains_merchant", 25));
		register("lotr:lindon_woodlands", genMap("lotr:galadhrim_wanderer", 40, "lotr:rivendell_wanderer", 35, "lotr:blue_mountains_merchant", 25));
		register("lotr:eregion", genMap("lotr:galadhrim_wanderer", 20, "lotr:rivendell_wanderer", 20, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 30));

		// Lothlorien only gets Rivendell traders
		register("lotr:lothlorien_eaves", genRates("lotr:rivendell_wanderer"));
		register("lotr:lothlorien", genRates("lotr:rivendell_wanderer"));

		register("lotr:gladden_fields", genMap("lotr:galadhrim_wanderer", 50, "lotr:oddment_collector", 50));

		// Bree-land
		register("lotr:breeland", genMap("lotr:galadhrim_wanderer", 7, "lotr:rivendell_wanderer", 8, "lotr:oddment_collector", 45, "lotr:blue_mountains_merchant", 40));
		register("lotr:chetwood", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 8, "lotr:rivendell_wanderer", 12, "lotr:oddment_collector", 25, "lotr:blue_mountains_merchant", 20));

		register("lotr:rohan", genMap("lotr:galadhrim_wanderer", 30, "lotr:oddment_collector", 70));
		register("lotr:wold", genMap("lotr:galadhrim_wanderer", 30, "lotr:oddment_collector", 70));
		register("lotr:uruk_highlands", genMap("lotr:oddment_collector", 1));

		register("lotr:fangorn", genRates("lotr:galadhrim_wanderer"));
		register("lotr:mouths_of_entwash", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

		register("lotr:enedwaith", genRates("lotr:oddment_collector"));

		// TODO: Further change these when Iron Hills merchants are added so that they are the highest percentage since they
		//  are the closest
		register("lotr:northern_dale", genMap("lotr:oddment_collector", 50, "lotr:blue_mountains_merchant", 50));
		register("lotr:dale", genMap("lotr:oddment_collector", 50, "lotr:blue_mountains_merchant", 50));

		// TODO: Maybe add a Dalish wandering trader one day for this area?
		register("lotr:iron_hills", genMap("lotr:oddment_collector", 60, "lotr:blue_mountains_merchant", 40));

		register("lotr:dorwinion", genMap("lotr:galadhrim_wanderer", 40, "lotr:oddment_collector", 60));
		register("lotr:emyn_winion", genMap("lotr:galadhrim_wanderer", 40, "lotr:oddment_collector", 60));

		register("lotr:dunland", genRates("lotr:oddment_collector", "lotr:blue_mountains_merchant"));

		register("lotr:umbar", genRates("lotr:oddment_collector"));
		register("lotr:umbar_hills", genRates("lotr:oddment_collector"));
		register("lotr:umbar_forest", genRates("lotr:oddment_collector"));

		register("lotr:northern_wilderland", genRates("lotr:oddment_collector"));

		register("lotr:harnennor", genRates("lotr:oddment_collector"));

		// Lone-lands
		register("lotr:lone_lands", genMap("lotr:galadhrim_wanderer", 8, "lotr:oddment_collector", 30, "lotr:rivendell_wanderer", 8, "lotr:blue_mountains_merchant", 24, "lotr:bree_merchant", 30));
		register("lotr:lone_lands_hills", genMap("lotr:galadhrim_wanderer", 8, "lotr:oddment_collector", 30, "lotr:rivendell_wanderer", 8, "lotr:blue_mountains_merchant", 24, "lotr:bree_merchant", 30));

		// Mirkwood
		register("lotr:woodland_realm", genRates("lotr:galadhrim_wanderer"));
		// in Legacy no travelling traders come to Northern Mirkwood, but I am adding them here because I feel like it fits the theme
		register("lotr:northern_mirkwood", genRates("lotr:galadhrim_wanderer"));
		register("lotr:mirkwood", new HashMap<>()); // no traders visit Southern Mirkwood (too corrupted)

		register("lotr:vales_of_anduin", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		register("lotr:anduin_hills", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		register("lotr:ethir_anduin", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

		// biomes with no traders to make sure it is clear that they were added
		// TODO: The Southron biomes need to have the Nomad merchant when it is added
		register("lotr:andrast", new HashMap<>());
		register("lotr:angmar", new HashMap<>());
		register("lotr:angmar_mountains", new HashMap<>());
		register("lotr:brown_lands", new HashMap<>());
		register("lotr:coldfells", new HashMap<>());
		register("lotr:dagorlad", new HashMap<>());
		register("lotr:dead_marshes", new HashMap<>());
		register("lotr:dense_snowy_northlands_forest", new HashMap<>());
		register("lotr:dense_northlands_forest", new HashMap<>());
		register("lotr:druwaith_iaur", new HashMap<>());
		register("lotr:eastern_desolation", new HashMap<>());
		register("lotr:ettenmoors", new HashMap<>());
		register("lotr:forochel", new HashMap<>());
		register("lotr:forodwaith", new HashMap<>());
		register("lotr:forodwaith_mountains", new HashMap<>());
		register("lotr:gorgoroth", new HashMap<>());
		register("lotr:grey_mountains", new HashMap<>());
		register("lotr:grey_mountains_foothills", new HashMap<>());
		register("lotr:harad_desert", new HashMap<>());
		register("lotr:harad_desert_hills", new HashMap<>());
		register("lotr:harad_half_desert", new HashMap<>());
		register("lotr:harondor", new HashMap<>());
		register("lotr:long_marshes", new HashMap<>());
		register("lotr:lostladen", new HashMap<>());
		register("lotr:trollshaws", new HashMap<>());
		register("lotr:midgewater", new HashMap<>());
		register("lotr:mirkwood_mountains", new HashMap<>());
		register("lotr:misty_mountains", new HashMap<>());
		register("lotr:misty_mountains_foothills", new HashMap<>());
		register("lotr:mordor", new HashMap<>());
		register("lotr:mordor_mountains", new HashMap<>());
		register("lotr:morgul_vale", new HashMap<>());
		register("lotr:nan_curunir", new HashMap<>());
		register("lotr:nindalf", new HashMap<>());
		register("lotr:northlands", new HashMap<>());
		register("lotr:northlands_forest", new HashMap<>());
		register("lotr:nurn", new HashMap<>());
		register("lotr:nurn_marshes", new HashMap<>());
		register("lotr:nurnen", new HashMap<>()); // nurnen is a sea no traders spawn here
		register("lotr:old_forest", new HashMap<>());
		register("lotr:snowy_northlands", new HashMap<>());
		register("lotr:snowy_northlands_forest", new HashMap<>());
		register("lotr:southron_coasts", new HashMap<>());
		register("lotr:southron_coasts_forest", new HashMap<>());
		register("lotr:tolfalas", new HashMap<>());
		register("lotr:western_isles", new HashMap<>());
		register("lotr:white_beach", new HashMap<>());
		register("lotr:white_mountains", new HashMap<>());
		register("lotr:white_mountains_foothills", new HashMap<>());
		// TODO: since this is close to Harad it seems like from the wiki it should only have Harad traders
		//  which means there is nothing that can go here yet
		register("lotr:wilderland", new HashMap<>());


		// Biomes that have no traders
		register("lotr:sea", new HashMap<>());
		register("lotr:river", new HashMap<>());
		register("lotr:mordor_river", new HashMap<>());
		register("lotr:gorgoroth_river", new HashMap<>());
		register("lotr:morgul_vale_river", new HashMap<>());
		register("lotr:nurn_river", new HashMap<>());
		register("lotr:barrow_downs", new HashMap<>());
		register("lotr:barrow_downs_river", new HashMap<>());
		ExtendedLog.info("Made {} wandering trader pool entries: ", BIOME_TRADER_WEIGHTS.size());

	};

	private static void register(String biomeName, Map<String, Integer> traderBiomeMap) {
		register(new ResourceLocation(biomeName), traderBiomeMap);
	}

	private static void register(ResourceLocation biomeKey, Map<String, Integer> traderBiomeMap) {
		Map<ExtendedWanderingTraderData, Integer> convertedWeights = new HashMap<>();
		for (Map.Entry<String, Integer> entry : traderBiomeMap.entrySet()) {
			ResourceLocation traderId = new ResourceLocation(entry.getKey());
			// Create a brand new empty container data profile if it doesn't exist yet
			ExtendedWanderingTraderData traderData = TRADER_REGISTRY.computeIfAbsent(traderId, id -> {
				if(!ForgeRegistries.ENTITIES.containsKey(traderId)) {
					ExtendedLog.error("Recieved a trader id that does not exist: "+traderId.toString());
					throw new IllegalArgumentException("Recieved a trader id that does not exist: "+traderId.toString());
				}
				EntityType<?> traderType = ForgeRegistries.ENTITIES.getValue(traderId);
				if(traderType==null || ServerLifecycleHooks.getCurrentServer()==null) {
					throw new IllegalStateException("Can't create wandering trader pools on client!");
				}
				Faction traderFaction = NPCEntitySettingsManager.serverInstance().getCurrentLoadedEntitySettings().getEntityTypeSettings(traderType).getAssignedFaction(ServerLifecycleHooks.getCurrentServer().overworld());
				return new ExtendedWanderingTraderData(traderType, traderFaction);});
			convertedWeights.put(traderData, entry.getValue());
		}
		BIOME_TRADER_WEIGHTS.put(biomeKey, convertedWeights);
	}

	private static HashMap<String, Integer> genRates(String ... names) {
		if (names.length == 0) {
			throw new IllegalArgumentException("At least one name must be provided.");
		}

		HashMap<String, Integer> rates = new HashMap<>();
		int numEntries = names.length;
		int baseRate = 100 / numEntries; // Equal distribution
		int remainder = 100 % numEntries; // Handle rounding errors

		// Assign baseRate to each name
		for (String name : names) {
			rates.put(name, baseRate);
		}

		// Distribute the remainder randomly among entries
		Random rand = new Random();
		for (int i = 0; i < remainder; i++) {
			String randomKey = names[rand.nextInt(numEntries)];
			rates.put(randomKey, rates.get(randomKey) + 1);
		}

		return rates;
	}

	public static Map<String, Integer> genMap(Object... entries) {
		if (entries.length % 2 != 0) {
			throw new IllegalArgumentException("Invalid number of arguments. Must be even (key-value pairs).");
		}
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < entries.length; i += 2) {
			String key = (String) entries[i];
			Integer value = (Integer) entries[i + 1];
			map.put(key, value);
		}
		return map;
	}

	public static Optional<ExtendedWanderingTraderData> pickTraderFromBiomeId(World world, Optional<RegistryKey<Biome>> biomeKey) throws IllegalArgumentException {
		return pickTraderIdFromBiomeName(world, biomeKey.get().location());
	}

	public static Optional<ExtendedWanderingTraderData> pickTraderIdFromBiomeName(World world, ResourceLocation biomeName) {
		if (!biomeName.getNamespace().equalsIgnoreCase("lotr")) {
			return Optional.empty();
		}
		Map<ExtendedWanderingTraderData, Integer> weights = BIOME_TRADER_WEIGHTS.get(biomeName);
		if (weights == null || weights.isEmpty()) {
			return Optional.empty();
		}
		return rollWeightedTrader(weights, world);
	}

	private static Optional<ExtendedWanderingTraderData> rollWeightedTrader(Map<ExtendedWanderingTraderData, Integer> weights, World world) {
		int totalWeight = weights.values().stream().mapToInt(Integer::intValue).sum();
		if (totalWeight <= 0) {
			ExtendedLog.error("Found empty total biome weights");
			return Optional.empty();
		}

		int roll = world.random.nextInt(totalWeight);
		int countedWeight = 0;

		for (Map.Entry<ExtendedWanderingTraderData, Integer> entry : weights.entrySet()) {
			countedWeight += entry.getValue();
			if (roll < countedWeight) {
				return Optional.of(entry.getKey());
			}
		}
		return Optional.empty();
	}

	public static class ExtendedWanderingTraderData {
		final @Nonnull EntityType<?> traderType;
		final Faction traderFaction;

		private ExtendedWanderingTraderData(@Nonnull EntityType<?> pTraderType, Faction pTraderFaction){
			this.traderType = pTraderType;
			this.traderFaction = pTraderFaction;
		}
		@Nonnull
		private EntityType<?> getTraderType() {
			return traderType;
		}

		private Faction getTraderFaction() {
			return traderFaction;
		}
	}
}
