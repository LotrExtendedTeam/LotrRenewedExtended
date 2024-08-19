package lotr.common.world.spawning;

import net.minecraft.world.World;

import java.util.*;

public class WanderingTradersList {

	public static Map<String, List<String>> traders = new HashMap<String, List<String>>() {{
	    // Shire
	    put("lotr:shire", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:shire_moors", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:shire_marshes", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:shire_woodlands", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:white_downs", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));

		put("lotr:blue_mountains", Arrays.asList("lotr:rivendell_wanderer", "lotr:oddment_collector"));
		put("lotr:blue_mountains_foothills", Arrays.asList("lotr:rivendell_wanderer", "lotr:oddment_collector"));

	    // Eriador
	    put("lotr:eriador", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
		put("lotr:eryn_vorn", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
		put("lotr:hills_of_evendim", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:eriador_downs", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:tower_hills", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:minhiriath", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:eryn_vorn", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:swanfleet", Collections.emptyList()); // Swanfleet has no wandering traders according to the wiki

	    // Rivendell only gets Galadhrim traders
	    put("lotr:rivendell", Collections.singletonList("lotr:galadhrim_wanderer"));
	    put("lotr:rivendell_hills", Collections.singletonList("lotr:galadhrim_wanderer"));

	    put("lotr:gondor", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:further_gondor", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:field_of_cormallen", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:anorien", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:blackroot_vale", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:lamedon", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:lamedon_hills", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:pealargir", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:anfalas", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:lossarnach", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:pinnath_gelin", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:ithilien", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:lebennin", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:emyn_en_ernil", Collections.emptyList()); // Emyn-En-Ernil has no traders in Legacy code
	    put("lotr:dor_en_ernil", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

	    // Older elven regions
	    put("lotr:lindon", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:blue_mountains_merchant"));
	    put("lotr:lindon_woodlands", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:blue_mountains_merchant"));
	    put("lotr:eregion", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));

	    // Lothlorien only gets Rivendell traders
	    put("lotr:lothlorien_eaves", Collections.singletonList("lotr:rivendell_wanderer"));
	    put("lotr:lothlorien", Collections.singletonList("lotr:rivendell_wanderer"));

		put("lotr:gladden_fields", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

	    // Bree-land
	    put("lotr:breeland", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:chetwood", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector", "lotr:blue_mountains_merchant"));

	    put("lotr:rohan", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
	    put("lotr:wold", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

	    put("lotr:fangorn", Collections.singletonList("lotr:galadhrim_wanderer"));
		put("lotr:mouths_of_entwash", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

	    put("lotr:enedwaith", Collections.singletonList("lotr:oddment_collector"));

	    put("lotr:northern_dale", Arrays.asList("lotr:oddment_collector", "lotr:blue_mountains_merchant"));
	    put("lotr:dale", Arrays.asList("lotr:oddment_collector", "lotr:blue_mountains_merchant"));

		put("lotr:iron_hills", Arrays.asList("lotr:oddment_collector", "lotr:blue_mountains_merchant"));

		put("lotr:dorwinion", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:emyn_winion", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

		put("lotr:dunland", Arrays.asList("lotr:oddment_collector", "lotr:blue_mountains_merchant"));

		put("lotr:umbar", Collections.singletonList("lotr:oddment_collector"));
		put("lotr:umbar_hills", Collections.singletonList("lotr:oddment_collector"));
		put("lotr:umbar_forest", Collections.singletonList("lotr:oddment_collector"));

		put("lotr:northern_wilderland", Collections.singletonList("lotr:oddment_collector"));

		put("lotr:harnennor", Collections.singletonList("lotr:oddment_collector"));

	    // Lone-lands
	    put("lotr:lone_lands", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector", "lotr:rivendell_wanderer", "lotr:blue_mountains_merchant"));
	    put("lotr:lone_lands_hills", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector", "lotr:rivendell_wanderer", "lotr:blue_mountains_merchant"));
	    // Mirkwood
	    put("lotr:woodland_realm", Collections.singletonList("lotr:galadhrim_wanderer"));
	    // in Legacy no travelling traders come to Northern Mirkwood, but I am adding them here because I feel like it fits the theme
	    put("lotr:northern_mirkwood", Collections.singletonList("lotr:galadhrim_wanderer"));
	    put("lotr:mirkwood", Collections.emptyList()); // no traders visit Southern Mirkwood (too corrupted)

		put("lotr:vales_of_anduin", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:anduin_hills", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
		put("lotr:ethir_anduin", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

		// biomes with no traders to make sure it is clear that they were added
		// TODO: The Southron biomes need to have the Nomad merchant when it is added
		put("lotr:andrast", Collections.emptyList());
		put("lotr:angmar", Collections.emptyList());
		put("lotr:angmar_mountains", Collections.emptyList());
		put("lotr:brown_lands", Collections.emptyList());
		put("lotr:coldfells", Collections.emptyList());
		put("lotr:dagorlad", Collections.emptyList());
		put("lotr:dead_marshes", Collections.emptyList());
		put("lotr:dense_snowy_northlands_forest", Collections.emptyList());
		put("lotr:dense_northlands_forest", Collections.emptyList());
		put("lotr:druwaith_iaur", Collections.emptyList());
		put("lotr:eastern_desolation", Collections.emptyList());
		put("lotr:ettenmoors", Collections.emptyList());
		put("lotr:forochel", Collections.emptyList());
		put("lotr:forodwaith", Collections.emptyList());
		put("lotr:forodwaith_mountains", Collections.emptyList());
		put("lotr:gorgoroth", Collections.emptyList());
		put("lotr:grey_mountains", Collections.emptyList());
		put("lotr:grey_mountains_foothills", Collections.emptyList());
		put("lotr:harad_desert", Collections.emptyList());
		put("lotr:harad_desert_hills", Collections.emptyList());
		put("lotr:harad_half_desert", Collections.emptyList());
		put("lotr:harondor", Collections.emptyList());
		put("lotr:long_marshes", Collections.emptyList());
		put("lotr:lostladen", Collections.emptyList());
		put("lotr:trollshaws", Collections.emptyList());
		put("lotr:midgewater", Collections.emptyList());
		put("lotr:mirkwood_mountains", Collections.emptyList());
		put("lotr:misty_mountains", Collections.emptyList());
		put("lotr:misty_mountains_foothills", Collections.emptyList());
		put("lotr:mordor", Collections.emptyList());
		put("lotr:mordor_mountains", Collections.emptyList());
		put("lotr:morgul_vale", Collections.emptyList());
		put("lotr:nan_curunir", Collections.emptyList());
		put("lotr:nindalf", Collections.emptyList());
		put("lotr:northlands", Collections.emptyList());
		put("lotr:northlands_forest", Collections.emptyList());
		put("lotr:nurn", Collections.emptyList());
		put("lotr:nurn_marshes", Collections.emptyList());
		put("lotr:nurnen", Collections.emptyList()); // nurnen is a sea no traders spawn here
		put("lotr:old_forest", Collections.emptyList());
		put("lotr:snowy_northlands", Collections.emptyList());
		put("lotr:snowy_northlands_forest", Collections.emptyList());
		put("lotr:southron_coasts", Collections.emptyList());
		put("lotr:southron_coasts_forest", Collections.emptyList());
		put("lotr:tolfalas", Collections.emptyList());
		put("lotr:western_isles", Collections.emptyList());
		put("lotr:white_beach", Collections.emptyList());
		put("lotr:white_mountains", Collections.emptyList());
		put("lotr:white_mountains_foothills", Collections.emptyList());
		// TODO: since this is close to Harad it seems like from the wiki it should only have Harad traders
		//  which means there is nothing that can go here yet
		put("lotr:wilderland", Collections.emptyList());
	}};

    /**
     * This method given a biome name from World#getBiomeName() will attempt to check if it is a valid LOTR Mod biome
     * and if it is pick a random wandering trader from it's list and return the entity name. If no wandering traders
     * spawn in that biome it returns null. If it is not a valid biome it throws an IllegalArgumentException
     *
     * @param biomeName
     * @return
     * @throws IllegalArgumentException
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
           List<String> possibleTraders = traders.get(lotrBiome);

           if (possibleTraders.isEmpty()) {
               return null;
           } else {
               return possibleTraders.get(world.random.nextInt(possibleTraders.size()));
           }
        } else {
            return null;
        }
    }

}
