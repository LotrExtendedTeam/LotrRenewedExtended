package lotr.common.world.spawning;

import java.util.HashMap;
import java.util.Map;

/**
 * For now my logic for spawning bandits will work very different than it did in Legacy because I would like to make
 * it simple and keep it similar to how I did Wandering Traders.
 *
 * In Legacy there were 3 possible frequencies for 3 bandits that a biome could have, RARE, UNCOMMON, and COMMON. These
 * were 10%, 30%, and 80% per hour roughly with a precalculated probability table and a check run once a second.
 *
 * To make it match it Wandering Traders and be simpler for now, I will simply have a time that must always pass for a player
 * before it checks if they should spawn a bandit. The full reset period for this for a player will be 30 minutes to give them
 * a respite. Then when that timer elapses it will look at the biome they are in and check the chance. RARE for us is 5%.
 * If the check fails it will add a random amount of seconds between 30 seconds and 3 minutes and try again. Eventually
 * when it succeeds the clock will be reset to 30 minutes again.
 *
 * @author RichardB122
 */
public class BanditBiomeList {



    // biome name and percent chance every roll
    private static Map<String, BanditChance> bandits = new HashMap<>();

    static {
        // According to Legacy wiki no bandits in the shire except the Moors
        bandits.put("lotr:shire", BanditChance.NONE);
        bandits.put("lotr:shire_moors", BanditChance.RARE);
        bandits.put("lotr:shire_marshes", BanditChance.NONE);
        bandits.put("lotr:shire_woodlands", BanditChance.NONE);
        bandits.put("lotr:white_downs", BanditChance.NONE);

        bandits.put("lotr:blue_mountains", BanditChance.RARE);
        bandits.put("lotr:blue_mountains_foothills", BanditChance.RARE);

        // Eriador
        bandits.put("lotr:eriador", BanditChance.UNCOMMON);
        bandits.put("lotr:eryn_vorn", BanditChance.UNCOMMON);
        bandits.put("lotr:hills_of_evendim", BanditChance.UNCOMMON);
        bandits.put("lotr:eriador_downs", BanditChance.UNCOMMON);
        bandits.put("lotr:tower_hills", BanditChance.NONE);
        bandits.put("lotr:minhiriath", BanditChance.UNCOMMON);
        bandits.put("lotr:swanfleet", BanditChance.RARE);

        // Rivendell
        bandits.put("lotr:rivendell", BanditChance.NONE);
        bandits.put("lotr:rivendell_hills", BanditChance.NONE);

        // Gondor
        bandits.put("lotr:gondor", BanditChance.RARE);
        bandits.put("lotr:further_gondor", BanditChance.RARE);
        bandits.put("lotr:field_of_cormallen", BanditChance.RARE);
        bandits.put("lotr:anorien", BanditChance.RARE);
        bandits.put("lotr:blackroot_vale", BanditChance.RARE);
        bandits.put("lotr:lamedon", BanditChance.RARE);
        bandits.put("lotr:lamedon_hills", BanditChance.RARE);
        bandits.put("lotr:pelargir", BanditChance.RARE);
        bandits.put("lotr:anfalas", BanditChance.RARE);
        bandits.put("lotr:lossarnach", BanditChance.RARE);
        bandits.put("lotr:pinnath_gelin", BanditChance.RARE);
        bandits.put("lotr:ithilien", BanditChance.UNCOMMON);
        bandits.put("lotr:lebennin", BanditChance.RARE);
        bandits.put("lotr:emyn_en_ernil", BanditChance.RARE);
        bandits.put("lotr:dor_en_ernil", BanditChance.RARE);

        // Older elven regions
        bandits.put("lotr:lindon", BanditChance.NONE);
        bandits.put("lotr:lindon_woodlands", BanditChance.NONE);
        bandits.put("lotr:eregion", BanditChance.RARE);

        bandits.put("lotr:lothlorien_eaves", BanditChance.NONE);
        bandits.put("lotr:lothlorien", BanditChance.NONE);

        bandits.put("lotr:gladden_fields", BanditChance.UNCOMMON);

        // Bree-land
        bandits.put("lotr:breeland", BanditChance.RARE);
        bandits.put("lotr:chetwood", BanditChance.UNCOMMON);

        // Rohan
        bandits.put("lotr:rohan", BanditChance.RARE);
        bandits.put("lotr:wold", BanditChance.UNCOMMON);

        bandits.put("lotr:fangorn", BanditChance.NONE);
        bandits.put("lotr:mouths_of_entwash", BanditChance.RARE);
        bandits.put("lotr:enedwaith", BanditChance.COMMON);

        bandits.put("lotr:northern_dale", BanditChance.RARE);
        bandits.put("lotr:dale", BanditChance.RARE);

        bandits.put("lotr:iron_hills", BanditChance.RARE);
        bandits.put("lotr:dorwinion", BanditChance.RARE);

        bandits.put("lotr:emyn_winion", BanditChance.RARE); // TODO: Don't really know what this should be
        bandits.put("lotr:dunland", BanditChance.COMMON);

        bandits.put("lotr:umbar", BanditChance.RARE);
        bandits.put("lotr:umbar_hills", BanditChance.RARE);
        bandits.put("lotr:umbar_forest", BanditChance.RARE);

        // TODO: Not really sure - It's at the edge of the world so just putting uncommon for now
        bandits.put("lotr:northern_wilderland", BanditChance.UNCOMMON);

        bandits.put("lotr:harnennor", BanditChance.RARE);

        // Lone-lands
        bandits.put("lotr:lone_lands", BanditChance.COMMON);
        bandits.put("lotr:lone_lands_hills", BanditChance.COMMON);

        // Mirkwood
        bandits.put("lotr:woodland_realm", BanditChance.NONE);
        bandits.put("lotr:northern_mirkwood", BanditChance.NONE);
        bandits.put("lotr:mirkwood", BanditChance.NONE);

        bandits.put("lotr:vales_of_anduin", BanditChance.UNCOMMON);
        bandits.put("lotr:anduin_hills", BanditChance.UNCOMMON);
        bandits.put("lotr:ethir_anduin", BanditChance.UNCOMMON);

        bandits.put("lotr:andrast", BanditChance.UNCOMMON);

        bandits.put("lotr:angmar", BanditChance.NONE);
        bandits.put("lotr:angmar_mountains", BanditChance.NONE);

        bandits.put("lotr:brown_lands", BanditChance.NONE);
        bandits.put("lotr:coldfells", BanditChance.UNCOMMON);

        bandits.put("lotr:dagorlad", BanditChance.NONE);
        bandits.put("lotr:dead_marshes", BanditChance.NONE);

        // going to say it's rare since it is dense snowy inhospitable forest
        bandits.put("lotr:dense_snowy_northlands_forest", BanditChance.RARE);
        bandits.put("lotr:dense_northlands_forest", BanditChance.UNCOMMON);

        bandits.put("lotr:druwaith_iaur", BanditChance.RARE);

        bandits.put("lotr:eastern_desolation", BanditChance.NONE); // Mordor so no bandits
        bandits.put("lotr:ettenmoors", BanditChance.UNCOMMON);

        bandits.put("lotr:forochel", BanditChance.NONE); // TODO: Not sure what the staff's opinion will be
        bandits.put("lotr:forodwaith", BanditChance.NONE);
        bandits.put("lotr:forodwaith_mountains", BanditChance.NONE);

        bandits.put("lotr:gorgoroth", BanditChance.NONE); // Mordor again so None

        bandits.put("lotr:grey_mountains", BanditChance.RARE);
        bandits.put("lotr:grey_mountains_foothills", BanditChance.RARE);

        // TODO: In Legacy there is a special Harad Bandit type that should be ported eventually
        //  for now they will just use the normal bandit
        bandits.put("lotr:harad_desert", BanditChance.RARE);
        bandits.put("lotr:harad_desert_hills", BanditChance.RARE);
        bandits.put("lotr:harad_half_desert", BanditChance.RARE);

        bandits.put("lotr:harondor", BanditChance.UNCOMMON);
        bandits.put("lotr:long_marshes", BanditChance.RARE);

        bandits.put("lotr:lostladen", BanditChance.RARE); // TODO: Also needs a custom Harad Bandit

        bandits.put("lotr:trollshaws", BanditChance.COMMON);
        bandits.put("lotr:midgewater", BanditChance.UNCOMMON);

        bandits.put("lotr:mirkwood_mountains", BanditChance.NONE);

        bandits.put("lotr:misty_mountains", BanditChance.NONE);
        bandits.put("lotr:misty_mountains_foothills", BanditChance.NONE);

        bandits.put("lotr:mordor", BanditChance.NONE);
        bandits.put("lotr:mordor_mountains", BanditChance.NONE);
        bandits.put("lotr:morgul_vale", BanditChance.NONE);
        bandits.put("lotr:nan_curunir", BanditChance.UNCOMMON);

        bandits.put("lotr:nindalf", BanditChance.UNCOMMON);
        bandits.put("lotr:northlands", BanditChance.UNCOMMON);
        bandits.put("lotr:northlands_forest", BanditChance.UNCOMMON);
        bandits.put("lotr:nurn", BanditChance.NONE);
        bandits.put("lotr:nurn_marshes", BanditChance.NONE);
        bandits.put("lotr:nurnen", BanditChance.NONE); // nurnen is a sea
        bandits.put("lotr:snowy_northlands", BanditChance.UNCOMMON);
        bandits.put("lotr:snowy_northlands_forest", BanditChance.UNCOMMON);

        bandits.put("lotr:old_forest", BanditChance.NONE);

        bandits.put("lotr:southron_coasts", BanditChance.RARE);
        bandits.put("lotr:southron_coasts_forest", BanditChance.RARE);

        bandits.put("lotr:tolfalas", BanditChance.COMMON);
        bandits.put("lotr:western_isles", BanditChance.NONE); // TODO: I think this should be NONE

        bandits.put("lotr:white_beach", BanditChance.NONE);
        bandits.put("lotr:white_mountains", BanditChance.RARE);
        bandits.put("lotr:white_mountains_foothills", BanditChance.RARE);

        bandits.put("lotr:wilderland", BanditChance.UNCOMMON);
    }

    /**
     * This method given a biome name from World#getBiomeName() will attempt to check if it is a valid LOTR Mod biome
     * and if it is return the chance a bandit spawn there.
     *
     * @param biomeName the name of the biome to query
     * @return The percent chance a bandit should spawn there
     * @throws IllegalArgumentException if the given biome is not a LOTR biome
     */
    public static int getChanceOfBanditFromBiomeName(String biomeName) throws IllegalArgumentException {
        // determine the LOTR biome
        String lotrBiome;
        int index = biomeName.indexOf("lotr");
        if (index != -1) {
            lotrBiome = biomeName.substring(index);
            lotrBiome = lotrBiome.replace("]", "");
        } else {
            throw new IllegalArgumentException(biomeName + " is not a LOTR Mod biome");
        }

        if (bandits.containsKey(lotrBiome)) {
            BanditChance chance = bandits.get(lotrBiome);
            return chance.getChance();
        } else {
            return 0;
        }
    }
}
