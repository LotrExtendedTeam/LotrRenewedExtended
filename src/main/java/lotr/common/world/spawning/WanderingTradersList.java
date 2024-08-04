package lotr.common.world.spawning;

import lotr.common.util.MapUtil;
import net.minecraft.world.World;

import java.util.*;

public class WanderingTradersList {

    public static Map<String, List<String>> traders = MapUtil.mapOf(
            // Shire
            MapUtil.entry("lotr:shire", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:shire_moors", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:shire_marshes", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:shire_woodlands", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:white_downs", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),

            // Eriador
            MapUtil.entry("lotr:eriador", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:eriador_downs", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:tower_hills", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:minhiriath", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:eryn_vorn", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:swanfleet", Collections.emptyList()), // Swanfleet has no wandering traders according to the wiki

            MapUtil.entry("lotr:trollshaws", Collections.emptyList()), // Trollshaws has no wandering traders according to the wiki

            // Rivendell only gets Galadhrim traders
            MapUtil.entry("lotr:rivendell", Collections.singletonList("lotr:galadhrim_wanderer")),
            MapUtil.entry("lotr:rivendell_hills", Collections.singletonList("lotr:galadhrim_wanderer")),

            // TODO: unsure if this includes all Gondor fiefdoms
            MapUtil.entry("lotr:gondor", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:blackroot_vale", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:lamedon", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:lamedon_hills", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:pelargir", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:ithilien", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:lebennin", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:emyn_en_ernil", Collections.emptyList()), // Emyn-En-Ernil has no traders in Legacy code
            MapUtil.entry("lotr:dor_en_ernil", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),

            MapUtil.entry("lotr:harondor", Collections.emptyList()), // Harondor has no traders according to Legacy code

            // Older elven regions
            MapUtil.entry("lotr:lindon", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer")),
            MapUtil.entry("lotr:lindon_woodlands", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer")),
            MapUtil.entry("lotr:eregion", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),

            // Lothlorien only gets Rivendell traders
            MapUtil.entry("lotr:lothlorien_eaves", Collections.singletonList("lotr:rivendell_wanderer")),
            MapUtil.entry("lotr:lothlorien", Collections.singletonList("lotr:rivendell_wanderer")),

            // Bree-land
            MapUtil.entry("lotr:breeland", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:chetwood", Arrays.asList("lotr:galadhrim_wanderer", "lotr:rivendell_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:midgewater", Collections.emptyList()),

            // TODO: make sure rest of Rohan is added?
            MapUtil.entry("lotr:rohan", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),
            MapUtil.entry("lotr:wold", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector")),

            MapUtil.entry("lotr:fangorn", Collections.singletonList("lotr:galadhrim_wanderer")),

            MapUtil.entry("lotr:enedwaith", Collections.singletonList("lotr:oddment_collector")),

            MapUtil.entry("lotr:northern_dale", Collections.singletonList("lotr:oddment_collector")),
            MapUtil.entry("lotr:dale", Collections.singletonList("lotr:oddment_collector")),

            // Lone-lands
            MapUtil.entry("lotr:lone_lands", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector", "lotr:rivendell_wanderer")),
            MapUtil.entry("lotr:lone_lands_hills", Arrays.asList("lotr:galadhrim_wanderer", "lotr:oddment_collector", "lotr:rivendell_wanderer")),

            // Mirkwood
            MapUtil.entry("lotr:woodland_realm", Collections.singletonList("lotr:galadhrim_wanderer")),
            // in Legacy no travelling traders come to Northern Mirkwood, but I am adding them here because I feel like it fits the theme
            MapUtil.entry("lotr:northern_mirkwood", Collections.singletonList("lotr:galadhrim_wanderer")),
            MapUtil.entry("lotr:mirkwood", Collections.emptyList()), // no traders visit Southern Mirkwood (too corrupted)

            MapUtil.entry("lotr:forochel", Collections.emptyList()), // no traders visit Forochel (too cold)
            MapUtil.entry("lotr:white_mountains", Collections.emptyList()), // no traders
            MapUtil.entry("lotr:white_mountains_foothills", Collections.emptyList()) // no traders

            // TODO: Make sure areas west of Lindon and the shire have wandering traders
    );

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
