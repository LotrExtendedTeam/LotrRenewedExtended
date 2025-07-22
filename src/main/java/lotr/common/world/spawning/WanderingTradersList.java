package lotr.common.world.spawning;

import java.util.*;

import net.minecraft.world.World;

public class WanderingTradersList {

	protected static Map<String, Map<String, Integer>> traders = new HashMap<>();

	static
	{
	    // Shire
	    traders.put("lotr:shire", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
	    traders.put("lotr:shire_moors", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
	    traders.put("lotr:shire_marshes", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
	    traders.put("lotr:shire_woodlands", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));
	    traders.put("lotr:white_downs", genMap("lotr:bree_merchant", 40, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 20));

		traders.put("lotr:blue_mountains", genMap("lotr:rivendell_wanderer", 25, "lotr:oddment_collector", 75));
		traders.put("lotr:blue_mountains_foothills", genMap("lotr:rivendell_wanderer", 25, "lotr:oddment_collector", 75));

	    // Eriador
		// TODO: Finish going through and updating these
	    traders.put("lotr:eriador", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
		traders.put("lotr:eryn_vorn", genMap("lotr:galadhrim_wanderer", 35, "lotr:rivendell_wanderer", 35, "lotr:oddment_collector", 20, "lotr:blue_mountains_merchant", 10));
		traders.put("lotr:hills_of_evendim", genMap("lotr:galadhrim_wanderer", 15, "lotr:rivendell_wanderer", 15, "lotr:oddment_collector", 40, "lotr:blue_mountains_merchant", 30));
		traders.put("lotr:eriador_downs", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
	    traders.put("lotr:tower_hills", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
	    traders.put("lotr:minhiriath", genMap("lotr:galadhrim_wanderer", 20, "lotr:oddment_collector", 45, "lotr:blue_mountains_merchant", 35));
	    traders.put("lotr:swanfleet", new HashMap<>()); // Swanfleet has no wandering traders according to the wiki

	    // Rivendell only gets Galadhrim traders
	    traders.put("lotr:rivendell", genRates("lotr:galadhrim_wanderer"));
	    traders.put("lotr:rivendell_hills", genRates("lotr:galadhrim_wanderer"));

	    traders.put("lotr:gondor", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		traders.put("lotr:further_gondor", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		traders.put("lotr:field_of_cormallen", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		traders.put("lotr:anorien", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
	    traders.put("lotr:blackroot_vale", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
	    traders.put("lotr:lamedon", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
	    traders.put("lotr:lamedon_hills", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
	    traders.put("lotr:pelargir", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		traders.put("lotr:anfalas", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		traders.put("lotr:lossarnach", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		traders.put("lotr:pinnath_gelin", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
		// higher chance of elves in Ithilien because of the forests
	    traders.put("lotr:ithilien", genMap("lotr:galadhrim_wanderer", 50, "lotr:oddment_collector", 50));
	    traders.put("lotr:lebennin", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
	    traders.put("lotr:emyn_en_ernil", new HashMap<>()); // Emyn-En-Ernil has no traders in Legacy code
	    traders.put("lotr:dor_en_ernil", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));

	    // Older elven regions
	    traders.put("lotr:lindon", genMap("lotr:galadhrim_wanderer", 40, "lotr:rivendell_wanderer", 35, "lotr:blue_mountains_merchant", 25));
	    traders.put("lotr:lindon_woodlands", genMap("lotr:galadhrim_wanderer", 40, "lotr:rivendell_wanderer", 35, "lotr:blue_mountains_merchant", 25));
	    traders.put("lotr:eregion", genMap("lotr:galadhrim_wanderer", 20, "lotr:rivendell_wanderer", 20, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 30));

	    // Lothlorien only gets Rivendell traders
	    traders.put("lotr:lothlorien_eaves", genRates("lotr:rivendell_wanderer"));
	    traders.put("lotr:lothlorien", genRates("lotr:rivendell_wanderer"));

		traders.put("lotr:gladden_fields", genMap("lotr:galadhrim_wanderer", 50, "lotr:oddment_collector", 50));

	    // Bree-land
	    traders.put("lotr:breeland", genMap("lotr:galadhrim_wanderer", 7, "lotr:rivendell_wanderer", 8, "lotr:oddment_collector", 45, "lotr:blue_mountains_merchant", 40));
	    traders.put("lotr:chetwood", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 8, "lotr:rivendell_wanderer", 12, "lotr:oddment_collector", 25, "lotr:blue_mountains_merchant", 20));

	    traders.put("lotr:rohan", genMap("lotr:galadhrim_wanderer", 30, "lotr:oddment_collector", 70));
	    traders.put("lotr:wold", genMap("lotr:galadhrim_wanderer", 30, "lotr:oddment_collector", 70));

	    traders.put("lotr:fangorn", genRates("lotr:galadhrim_wanderer"));
		traders.put("lotr:mouths_of_entwash", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

	    traders.put("lotr:enedwaith", genRates("lotr:oddment_collector"));

		// TODO: Further change these when Iron Hills merchants are added so that they are the highest percentage since they
		//  are the closest
	    traders.put("lotr:northern_dale", genMap("lotr:oddment_collector", 50, "lotr:blue_mountains_merchant", 50));
	    traders.put("lotr:dale", genMap("lotr:oddment_collector", 50, "lotr:blue_mountains_merchant", 50));

		// TODO: Maybe add a Dalish wandering trader one day for this area?
		traders.put("lotr:iron_hills", genMap("lotr:oddment_collector", 60, "lotr:blue_mountains_merchant", 40));

		traders.put("lotr:dorwinion", genMap("lotr:galadhrim_wanderer", 40, "lotr:oddment_collector", 60));
		traders.put("lotr:emyn_winion", genMap("lotr:galadhrim_wanderer", 40, "lotr:oddment_collector", 60));

		traders.put("lotr:dunland", genRates("lotr:oddment_collector", "lotr:blue_mountains_merchant"));

		traders.put("lotr:umbar", genRates("lotr:oddment_collector"));
		traders.put("lotr:umbar_hills", genRates("lotr:oddment_collector"));
		traders.put("lotr:umbar_forest", genRates("lotr:oddment_collector"));

		traders.put("lotr:northern_wilderland", genRates("lotr:oddment_collector"));

		traders.put("lotr:harnennor", genRates("lotr:oddment_collector"));

	    // Lone-lands
	    traders.put("lotr:lone_lands", genMap("lotr:galadhrim_wanderer", 8, "lotr:oddment_collector", 30, "lotr:rivendell_wanderer", 8, "lotr:blue_mountains_merchant", 24, "lotr:bree_merchant", 30));
		traders.put("lotr:lone_lands_hills", genMap("lotr:galadhrim_wanderer", 8, "lotr:oddment_collector", 30, "lotr:rivendell_wanderer", 8, "lotr:blue_mountains_merchant", 24, "lotr:bree_merchant", 30));

	    // Mirkwood
	    traders.put("lotr:woodland_realm", genRates("lotr:galadhrim_wanderer"));
	    // in Legacy no travelling traders come to Northern Mirkwood, but I am adding them here because I feel like it fits the theme
	    traders.put("lotr:northern_mirkwood", genRates("lotr:galadhrim_wanderer"));
	    traders.put("lotr:mirkwood", new HashMap<>()); // no traders visit Southern Mirkwood (too corrupted)

		traders.put("lotr:vales_of_anduin", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		traders.put("lotr:anduin_hills", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		traders.put("lotr:ethir_anduin", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

		// biomes with no traders to make sure it is clear that they were added
		// TODO: The Southron biomes need to have the Nomad merchant when it is added
		traders.put("lotr:andrast", new HashMap<>());
		traders.put("lotr:angmar", new HashMap<>());
		traders.put("lotr:angmar_mountains", new HashMap<>());
		traders.put("lotr:brown_lands", new HashMap<>());
		traders.put("lotr:coldfells", new HashMap<>());
		traders.put("lotr:dagorlad", new HashMap<>());
		traders.put("lotr:dead_marshes", new HashMap<>());
		traders.put("lotr:dense_snowy_northlands_forest", new HashMap<>());
		traders.put("lotr:dense_northlands_forest", new HashMap<>());
		traders.put("lotr:druwaith_iaur", new HashMap<>());
		traders.put("lotr:eastern_desolation", new HashMap<>());
		traders.put("lotr:ettenmoors", new HashMap<>());
		traders.put("lotr:forochel", new HashMap<>());
		traders.put("lotr:forodwaith", new HashMap<>());
		traders.put("lotr:forodwaith_mountains", new HashMap<>());
		traders.put("lotr:gorgoroth", new HashMap<>());
		traders.put("lotr:grey_mountains", new HashMap<>());
		traders.put("lotr:grey_mountains_foothills", new HashMap<>());
		traders.put("lotr:harad_desert", new HashMap<>());
		traders.put("lotr:harad_desert_hills", new HashMap<>());
		traders.put("lotr:harad_half_desert", new HashMap<>());
		traders.put("lotr:harondor", new HashMap<>());
		traders.put("lotr:long_marshes", new HashMap<>());
		traders.put("lotr:lostladen", new HashMap<>());
		traders.put("lotr:trollshaws", new HashMap<>());
		traders.put("lotr:midgewater", new HashMap<>());
		traders.put("lotr:mirkwood_mountains", new HashMap<>());
		traders.put("lotr:misty_mountains", new HashMap<>());
		traders.put("lotr:misty_mountains_foothills", new HashMap<>());
		traders.put("lotr:mordor", new HashMap<>());
		traders.put("lotr:mordor_mountains", new HashMap<>());
		traders.put("lotr:morgul_vale", new HashMap<>());
		traders.put("lotr:nan_curunir", new HashMap<>());
		traders.put("lotr:nindalf", new HashMap<>());
		traders.put("lotr:northlands", new HashMap<>());
		traders.put("lotr:northlands_forest", new HashMap<>());
		traders.put("lotr:nurn", new HashMap<>());
		traders.put("lotr:nurn_marshes", new HashMap<>());
		traders.put("lotr:nurnen", new HashMap<>()); // nurnen is a sea no traders spawn here
		traders.put("lotr:old_forest", new HashMap<>());
		traders.put("lotr:snowy_northlands", new HashMap<>());
		traders.put("lotr:snowy_northlands_forest", new HashMap<>());
		traders.put("lotr:southron_coasts", new HashMap<>());
		traders.put("lotr:southron_coasts_forest", new HashMap<>());
		traders.put("lotr:tolfalas", new HashMap<>());
		traders.put("lotr:western_isles", new HashMap<>());
		traders.put("lotr:white_beach", new HashMap<>());
		traders.put("lotr:white_mountains", new HashMap<>());
		traders.put("lotr:white_mountains_foothills", new HashMap<>());
		// TODO: since this is close to Harad it seems like from the wiki it should only have Harad traders
		//  which means there is nothing that can go here yet
		traders.put("lotr:wilderland", new HashMap<>());
	};

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

    /**
     * This method given a biome name from World#getBiomeName() will attempt to check if it is a valid LOTR Mod biome
     * and if it is pick a random wandering trader from its list and return the entity name. If no wandering traders
     * spawn in that biome it returns null. If it is not a valid biome it throws an IllegalArgumentException
     * @param world the {@link World} to check
     * @param biomeName the name of the biome to query
     * @return The {@link String} name of the trader to spawn, null if no traders can spawn
     * @throws IllegalArgumentException if the given biome is not a LOTR biome
     */
    public static String pickTraderFromBiomeName(World world, String biomeName) throws IllegalArgumentException {
        // determine the LOTR biome
        String lotrBiome;
        int index = biomeName.indexOf("lotr");
        if (index != -1) {
            lotrBiome = biomeName.substring(index);
            lotrBiome = lotrBiome.replace("]", "");
        } else {
            throw new IllegalArgumentException(biomeName + " is not a LOTR Mod biome");
        }

        if (traders.containsKey(lotrBiome)) {
           Map<String, Integer> rates = traders.get(lotrBiome);

           if (rates.isEmpty()) {
               return null;
           } else {
               return getTraderFromHashmap(rates, world);
           }
        } else {
            return null;
        }
    }

	private static String getTraderFromHashmap(Map<String, Integer> rates, World world) {
		// generate minimums
		Map<Integer, String> minimums = new HashMap<>();
		int minimum = 1;
		for (String key : rates.keySet()) {
			minimums.put(minimum, key);
			minimum += rates.get(key);
		}

		int random = world.random.nextInt(100) + 1;
		Object[] minimumsSorted = minimums.keySet().toArray();
		Arrays.sort(minimumsSorted);
		for (int i = minimumsSorted.length - 1; i > -1; i--) {
			if (random >= (Integer) minimumsSorted[i]) return minimums.get(minimumsSorted[i]);
		}

		return "";
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

}
