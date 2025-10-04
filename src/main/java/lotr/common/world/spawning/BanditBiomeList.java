package lotr.common.world.spawning;

import java.util.HashMap;
import java.util.Map;

public class BanditBiomeList {

    // biome name and percent chance every roll
    protected static Map<String, Integer> bandits = new HashMap<>();

    static {
        // According to Legacy wiki no bandits in the shire except the Moors
        bandits.put("lotr:shire", 0);
        bandits.put("lotr:shire_moors", 5);
        bandits.put("lotr:shire_marshes", 0);
        bandits.put("lotr:shire_woodlands", 0);
        bandits.put("lotr:white_downs", 0);

        // TODO: Finish going through and updating these
        /* bandits.put("lotr:blue_mountains", genMap("lotr:rivendell_wanderer", 25, "lotr:oddment_collector", 75));
        bandits.put("lotr:blue_mountains_foothills", genMap("lotr:rivendell_wanderer", 25, "lotr:oddment_collector", 75));

        // Eriador
        bandits.put("lotr:eriador", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
        bandits.put("lotr:eryn_vorn", genMap("lotr:galadhrim_wanderer", 35, "lotr:rivendell_wanderer", 35, "lotr:oddment_collector", 20, "lotr:blue_mountains_merchant", 10));
        bandits.put("lotr:hills_of_evendim", genMap("lotr:galadhrim_wanderer", 15, "lotr:rivendell_wanderer", 15, "lotr:oddment_collector", 40, "lotr:blue_mountains_merchant", 30));
        bandits.put("lotr:eriador_downs", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
        bandits.put("lotr:tower_hills", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 5, "lotr:rivendell_wanderer", 5, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 25));
        bandits.put("lotr:minhiriath", genMap("lotr:galadhrim_wanderer", 20, "lotr:oddment_collector", 45, "lotr:blue_mountains_merchant", 35));
        bandits.put("lotr:swanfleet", new HashMap<>()); // Swanfleet has no wandering bandits according to the wiki

        // Rivendell only gets Galadhrim bandits
        bandits.put("lotr:rivendell", genRates("lotr:galadhrim_wanderer"));
        bandits.put("lotr:rivendell_hills", genRates("lotr:galadhrim_wanderer"));

        bandits.put("lotr:gondor", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:further_gondor", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:field_of_cormallen", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:anorien", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:blackroot_vale", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:lamedon", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:lamedon_hills", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:pelargir", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:anfalas", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:lossarnach", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:pinnath_gelin", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        // higher chance of elves in Ithilien because of the forests
        bandits.put("lotr:ithilien", genMap("lotr:galadhrim_wanderer", 50, "lotr:oddment_collector", 50));
        bandits.put("lotr:lebennin", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));
        bandits.put("lotr:emyn_en_ernil", new HashMap<>()); // Emyn-En-Ernil has no bandits in Legacy code
        bandits.put("lotr:dor_en_ernil", genMap("lotr:galadhrim_wanderer", 35, "lotr:oddment_collector", 65));

        // Older elven regions
        bandits.put("lotr:lindon", genMap("lotr:galadhrim_wanderer", 40, "lotr:rivendell_wanderer", 35, "lotr:blue_mountains_merchant", 25));
        bandits.put("lotr:lindon_woodlands", genMap("lotr:galadhrim_wanderer", 40, "lotr:rivendell_wanderer", 35, "lotr:blue_mountains_merchant", 25));
        bandits.put("lotr:eregion", genMap("lotr:galadhrim_wanderer", 20, "lotr:rivendell_wanderer", 20, "lotr:oddment_collector", 30, "lotr:blue_mountains_merchant", 30));

        // Lothlorien only gets Rivendell bandits
        bandits.put("lotr:lothlorien_eaves", genRates("lotr:rivendell_wanderer"));
        bandits.put("lotr:lothlorien", genRates("lotr:rivendell_wanderer"));

        bandits.put("lotr:gladden_fields", genMap("lotr:galadhrim_wanderer", 50, "lotr:oddment_collector", 50));

        // Bree-land
        bandits.put("lotr:breeland", genMap("lotr:galadhrim_wanderer", 7, "lotr:rivendell_wanderer", 8, "lotr:oddment_collector", 45, "lotr:blue_mountains_merchant", 40));
        bandits.put("lotr:chetwood", genMap("lotr:bree_merchant", 35, "lotr:galadhrim_wanderer", 8, "lotr:rivendell_wanderer", 12, "lotr:oddment_collector", 25, "lotr:blue_mountains_merchant", 20));

        bandits.put("lotr:rohan", genMap("lotr:galadhrim_wanderer", 30, "lotr:oddment_collector", 70));
        bandits.put("lotr:wold", genMap("lotr:galadhrim_wanderer", 30, "lotr:oddment_collector", 70));

        bandits.put("lotr:fangorn", genRates("lotr:galadhrim_wanderer"));
        bandits.put("lotr:mouths_of_entwash", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

        bandits.put("lotr:enedwaith", genRates("lotr:oddment_collector"));

        //  are the closest
        bandits.put("lotr:northern_dale", genMap("lotr:oddment_collector", 50, "lotr:blue_mountains_merchant", 50));
        bandits.put("lotr:dale", genMap("lotr:oddment_collector", 50, "lotr:blue_mountains_merchant", 50));

        bandits.put("lotr:iron_hills", genMap("lotr:oddment_collector", 60, "lotr:blue_mountains_merchant", 40));

        bandits.put("lotr:dorwinion", genMap("lotr:galadhrim_wanderer", 40, "lotr:oddment_collector", 60));
        bandits.put("lotr:emyn_winion", genMap("lotr:galadhrim_wanderer", 40, "lotr:oddment_collector", 60));

        bandits.put("lotr:dunland", genRates("lotr:oddment_collector", "lotr:blue_mountains_merchant"));

        bandits.put("lotr:umbar", genRates("lotr:oddment_collector"));
        bandits.put("lotr:umbar_hills", genRates("lotr:oddment_collector"));
        bandits.put("lotr:umbar_forest", genRates("lotr:oddment_collector"));

        bandits.put("lotr:northern_wilderland", genRates("lotr:oddment_collector"));

        bandits.put("lotr:harnennor", genRates("lotr:oddment_collector"));

        // Lone-lands
        bandits.put("lotr:lone_lands", genMap("lotr:galadhrim_wanderer", 8, "lotr:oddment_collector", 30, "lotr:rivendell_wanderer", 8, "lotr:blue_mountains_merchant", 24, "lotr:bree_merchant", 30));
        bandits.put("lotr:lone_lands_hills", genMap("lotr:galadhrim_wanderer", 8, "lotr:oddment_collector", 30, "lotr:rivendell_wanderer", 8, "lotr:blue_mountains_merchant", 24, "lotr:bree_merchant", 30));

        // Mirkwood
        bandits.put("lotr:woodland_realm", genRates("lotr:galadhrim_wanderer"));
        // in Legacy no travelling bandits come to Northern Mirkwood, but I am adding them here because I feel like it fits the theme
        bandits.put("lotr:northern_mirkwood", genRates("lotr:galadhrim_wanderer"));
        bandits.put("lotr:mirkwood", new HashMap<>()); // no bandits visit Southern Mirkwood (too corrupted)

        bandits.put("lotr:vales_of_anduin", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
        bandits.put("lotr:anduin_hills", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));
        bandits.put("lotr:ethir_anduin", genRates("lotr:galadhrim_wanderer", "lotr:oddment_collector"));

        // biomes with no bandits to make sure it is clear that they were added
        bandits.put("lotr:andrast", new HashMap<>());
        bandits.put("lotr:angmar", new HashMap<>());
        bandits.put("lotr:angmar_mountains", new HashMap<>());
        bandits.put("lotr:brown_lands", new HashMap<>());
        bandits.put("lotr:coldfells", new HashMap<>());
        bandits.put("lotr:dagorlad", new HashMap<>());
        bandits.put("lotr:dead_marshes", new HashMap<>());
        bandits.put("lotr:dense_snowy_northlands_forest", new HashMap<>());
        bandits.put("lotr:dense_northlands_forest", new HashMap<>());
        bandits.put("lotr:druwaith_iaur", new HashMap<>());
        bandits.put("lotr:eastern_desolation", new HashMap<>());
        bandits.put("lotr:ettenmoors", new HashMap<>());
        bandits.put("lotr:forochel", new HashMap<>());
        bandits.put("lotr:forodwaith", new HashMap<>());
        bandits.put("lotr:forodwaith_mountains", new HashMap<>());
        bandits.put("lotr:gorgoroth", new HashMap<>());
        bandits.put("lotr:grey_mountains", new HashMap<>());
        bandits.put("lotr:grey_mountains_foothills", new HashMap<>());
        bandits.put("lotr:harad_desert", new HashMap<>());
        bandits.put("lotr:harad_desert_hills", new HashMap<>());
        bandits.put("lotr:harad_half_desert", new HashMap<>());
        bandits.put("lotr:harondor", new HashMap<>());
        bandits.put("lotr:long_marshes", new HashMap<>());
        bandits.put("lotr:lostladen", new HashMap<>());
        bandits.put("lotr:trollshaws", new HashMap<>());
        bandits.put("lotr:midgewater", new HashMap<>());
        bandits.put("lotr:mirkwood_mountains", new HashMap<>());
        bandits.put("lotr:misty_mountains", new HashMap<>());
        bandits.put("lotr:misty_mountains_foothills", new HashMap<>());
        bandits.put("lotr:mordor", new HashMap<>());
        bandits.put("lotr:mordor_mountains", new HashMap<>());
        bandits.put("lotr:morgul_vale", new HashMap<>());
        bandits.put("lotr:nan_curunir", new HashMap<>());
        bandits.put("lotr:nindalf", new HashMap<>());
        bandits.put("lotr:northlands", new HashMap<>());
        bandits.put("lotr:northlands_forest", new HashMap<>());
        bandits.put("lotr:nurn", new HashMap<>());
        bandits.put("lotr:nurn_marshes", new HashMap<>());
        bandits.put("lotr:nurnen", new HashMap<>()); // nurnen is a sea no bandits spawn here
        bandits.put("lotr:old_forest", new HashMap<>());
        bandits.put("lotr:snowy_northlands", new HashMap<>());
        bandits.put("lotr:snowy_northlands_forest", new HashMap<>());
        bandits.put("lotr:southron_coasts", new HashMap<>());
        bandits.put("lotr:southron_coasts_forest", new HashMap<>());
        bandits.put("lotr:tolfalas", new HashMap<>());
        bandits.put("lotr:western_isles", new HashMap<>());
        bandits.put("lotr:white_beach", new HashMap<>());
        bandits.put("lotr:white_mountains", new HashMap<>());
        bandits.put("lotr:white_mountains_foothills", new HashMap<>());
        // TODO: since this is close to Harad it seems like from the wiki it should only have Harad bandits
        //  which means there is nothing that can go here yet
        bandits.put("lotr:wilderland", new HashMap<>());*/
    }
}
