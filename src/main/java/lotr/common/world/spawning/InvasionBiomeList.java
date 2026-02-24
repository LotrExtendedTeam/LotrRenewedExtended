package lotr.common.world.spawning;

import lotr.common.LOTRLog;
import lotr.common.world.ExtendedInvasions;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InvasionBiomeList {

    protected static Map<String, Map<ExtendedInvasions, InvasionChance>> invasions = new HashMap<>();

    static {
        // Shire is free from invasions
        invasions.put("lotr:shire", new HashMap<>());
        invasions.put("lotr:shire_moors", new HashMap<>());
        invasions.put("lotr:shire_marshes", new HashMap<>());
        invasions.put("lotr:shire_woodlands", new HashMap<>());
        invasions.put("lotr:white_downs", new HashMap<>());

        // Blue Mountains
        invasions.put("lotr:blue_mountains", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:blue_mountains_foothills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));

        // Eriador
        // TODO: Add the following to Eriador when they are added: Rhudaur Hillmen [Uncommon], Angmar [Rare], Angmar Wargs [Rare]
        invasions.put("lotr:eriador", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        invasions.put("lotr:eryn_vorn", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        invasions.put("lotr:hills_of_evendim", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:eriador_downs", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        invasions.put("lotr:tower_hills", genMap(ExtendedInvasions.GUNDABAD, InvasionChance.RARE));
        invasions.put("lotr:minhiriath", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        invasions.put("lotr:swanfleet", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE)
        );

        // only Rivendell Hills have invasions
        invasions.put("lotr:rivendell", new HashMap<>());
        invasions.put("lotr:rivendell_valley", new HashMap<>());
        invasions.put("lotr:rivendell_hills", genMap(
                ExtendedInvasions.RIVENDELL, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Gondor
        // TODO: These are missing a ton of factions because the NPCs are missing
        //       Add Factions like Black Uruks later
        invasions.put("lotr:gondor", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        // TODO: For now there is no Harad factions so no invasions here
        invasions.put("lotr:further_gondor", new HashMap<>());
        invasions.put("lotr:field_of_cormallen", new HashMap<>()); // None here either?
        invasions.put("lotr:anorien", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:blackroot_vale", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:lamedon", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:lamedon_hills", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:pelargir", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:anfalas", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:lossarnach", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:pinnath_gelin", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:ithilien", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.COMMON,
                ExtendedInvasions.GONDOR, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:lebennin", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:emyn_en_ernil", genMap(ExtendedInvasions.MORDOR, InvasionChance.RARE));
        invasions.put("lotr:dor_en_ernil", genMap(ExtendedInvasions.MORDOR, InvasionChance.RARE));

        // Older elven regions
        invasions.put("lotr:lindon", genMap(ExtendedInvasions.GUNDABAD, InvasionChance.RARE));
        invasions.put("lotr:lindon_woodlands", genMap(ExtendedInvasions.GUNDABAD, InvasionChance.RARE));
        invasions.put("lotr:eregion", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));

        // TODO: Add Dol-Guldur when they are added
        invasions.put("lotr:lothlorien_eaves", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));
        invasions.put("lotr:lothlorien", new HashMap<>()); // no invasions in Lothlorien proper

        invasions.put("lotr:gladden_fields", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));

        // Bree-land
        invasions.put("lotr:breeland", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));
        invasions.put("lotr:chetwood", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Rohan
        // TODO: Add Isengard and Dunlending invasions when the factions are added
        invasions.put("lotr:rohan", new HashMap<>());
        invasions.put("lotr:wold", new HashMap<>());

        invasions.put("lotr:fangorn", new HashMap<>()); // no invasions in Fangorn
        invasions.put("lotr:mouths_of_entwash", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        invasions.put("lotr:enedwaith", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add Dunlendings here when they are added
        ));

        invasions.put("lotr:northern_dale", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON,
                ExtendedInvasions.MORDOR, InvasionChance.RARE
                // TODO: Add Dunland when they are added
                // TODO: Add Rhun when they are added
        ));
        invasions.put("lotr:dale", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON,
                ExtendedInvasions.MORDOR, InvasionChance.RARE
                // TODO: Add Dunland when they are added
                // TODO: Add Rhun when they are added
        ));

        invasions.put("lotr:iron_hills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:dorwinion", genMap(
                // TODO: Add Dol-Guldur when it's added
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        invasions.put("lotr:emyn_winion", genMap(
            ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        invasions.put("lotr:dunland", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.ROHAN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Umbar has no invasions
        invasions.put("lotr:umbar", new HashMap<>());
        invasions.put("lotr:umbar_hills", new HashMap<>());
        invasions.put("lotr:umbar_forest", new HashMap<>());

        // Northern Wilderland has no invasions?
        invasions.put("lotr:northern_wilderland", new  HashMap<>());

        invasions.put("lotr:harondor", genMap(
                ExtendedInvasions.GONDOR, InvasionChance.UNCOMMON,
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
                // TODO: Add the following when they are added
                //ExtendedInvasions.GONDOR_ITHILIEN, LOTREventSpawner.EventChance.COMMON);
                //LOTRInvasions.GONDOR_DOL_AMROTH, LOTREventSpawner.EventChance.RARE);
                //LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
                //LOTRInvasions.NEAR_HARAD_HARNEDOR, LOTREventSpawner.EventChance.COMMON);
                //LOTRInvasions.NEAR_HARAD_COAST, LOTREventSpawner.EventChance.UNCOMMON);
                //LOTRInvasions.NEAR_HARAD_UMBAR, LOTREventSpawner.EventChance.RARE);
                //LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.UNCOMMON);
                //LOTRInvasions.NEAR_HARAD_GULF, LOTREventSpawner.EventChance.RARE);
        ));
        invasions.put("lotr:harnennor", genMap(
                ExtendedInvasions.GONDOR, InvasionChance.UNCOMMON,
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
        ));

        // Lone-lands
        invasions.put("lotr:lone_lands", genMap(
               ExtendedInvasions.DUNEDAIN, InvasionChance.COMMON,
               ExtendedInvasions.GUNDABAD, InvasionChance.COMMON,
               ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.COMMON,
               ExtendedInvasions.RIVENDELL, InvasionChance.RARE
               // TODO: Add Angmar here when they are added
               // TODO: Add Rhudaur Hillmen here when they are added
        ));
        invasions.put("lotr:lone_lands_hills", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.COMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.COMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.COMMON,
                ExtendedInvasions.RIVENDELL, InvasionChance.RARE
                // TODO: Add Angmar here when they are added
                // TODO: Add Rhudaur Hillmen here when they are added
        ));

        // Mirkwood
        invasions.put("lotr:woodland_realm", new HashMap<>()); // TODO: Add Dol-Guldur when they are added
        invasions.put("lotr:northern_mirkwood", genMap(
                // TODO: Add Dol-Guldur when they are added
                ExtendedInvasions.WOODLAND_REALM, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:mirkwood", genMap(
                ExtendedInvasions.WOODLAND_REALM, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:mirkwood_mountains", genMap(
                ExtendedInvasions.WOODLAND_REALM, InvasionChance.UNCOMMON
        ));

        // TODO: Add more factions here when others are ready
        // Anduin
        invasions.put("lotr:vales_of_anduin", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.LOTHLORIEN, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:anduin_hills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.LOTHLORIEN, InvasionChance.UNCOMMON
        ));
        invasions.put("lotr:ethir_anduin", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.LOTHLORIEN, InvasionChance.UNCOMMON
        ));

        invasions.put("lotr:andrast", new HashMap<>());
        invasions.put("lotr:angmar", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.LINDON, InvasionChance.RARE,
                ExtendedInvasions.RIVENDELL, InvasionChance.RARE
        ));
        invasions.put("lotr:angmar_mountains", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.LINDON, InvasionChance.RARE,
                ExtendedInvasions.RIVENDELL, InvasionChance.RARE
        ));

        invasions.put("lotr:brown_lands", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
                // TODO: Add Mordor Wargs and Mordor Black Uruks when they are added
        ));

        invasions.put("lotr:coldfells", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add these when they are ready
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        ));

        invasions.put("lotr:dagorlad", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
                // TODO: Add Mordor Wargs and Mordor Black Uruks when they are added
        ));

        invasions.put("lotr:dead_marshes", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        invasions.put("lotr:dense_snowy_northlands_forest", new HashMap<>()); // no invasions here
        invasions.put("lotr:dense_northlands_forest", new HashMap<>()); // no invasions here
        invasions.put("lotr:druwaith_iaur", new HashMap<>()); // no invasions here

        // one does not simply invade Mordor
        invasions.put("lotr:mordor", new HashMap<>());
        invasions.put("lotr:mordor_mountains", new HashMap<>());
        invasions.put("lotr:eastern_desolation", new HashMap<>());
        invasions.put("lotr:gorgoroth", new HashMap<>());
        invasions.put("lotr:morgul_vale", new HashMap<>());
        invasions.put("lotr:nurn", new HashMap<>());
        invasions.put("lotr:nurn_marshes", new HashMap<>());
        invasions.put("lotr:nurnen", new HashMap<>());

        invasions.put("lotr:ettenmoors", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.COMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.COMMON
                // TODO: Add these when they are ready
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        ));

        // forochel and forodwaith have no invasions
        invasions.put("lotr:forochel", new HashMap<>());
        invasions.put("lotr:forodwaith", new HashMap<>());
        invasions.put("lotr:forodwaith_mountains", new HashMap<>());

        invasions.put("lotr:grey_mountains", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
               // TODO: Add the Iron Hills Dwarves when they are added
        ));
        invasions.put("lotr:grey_mountains_foothills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add the Iron Hills Dwarves when they are added
        ));

        // TODO: Not sure what should be in these biomes or when we'll have factions that make sense here
        invasions.put("lotr:harad_desert", new HashMap<>());
        invasions.put("lotr:harad_desert_hills", new HashMap<>());
        invasions.put("lotr:harad_half_desert", new HashMap<>());
        invasions.put("lotr:lostladen", new HashMap<>());

        invasions.put("lotr:long_marshes", new HashMap<>()); // none here

        invasions.put("lotr:trollshaws", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add these when they are ready
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        ));

        invasions.put("lotr:midgewater", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Misty Mountains has no invasions
        invasions.put("lotr:misty_mountains", new HashMap<>());
        invasions.put("lotr:misty_mountains_foothills", new HashMap<>());

        invasions.put("lotr:nindalf", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        invasions.put("lotr:nan_curunir", genMap(
                ExtendedInvasions.ROHAN, InvasionChance.RARE
        ));

        // no invasions in Northlands
        invasions.put("lotr:northlands", new HashMap<>());
        invasions.put("lotr:northlands_forest", new HashMap<>());
        invasions.put("lotr:snowy_northlands", new HashMap<>());
        invasions.put("lotr:snowy_northlands_forest", new HashMap<>());

        invasions.put("lotr:old_forest", new HashMap<>()); // no invasions in Old Forest

        // TODO: Not sure what invasions we should eventually go here
        invasions.put("lotr:southron_coasts", new HashMap<>());
        invasions.put("lotr:southron_coasts_forest", new HashMap<>());

        invasions.put("lotr:tolfalas", new HashMap<>()); // none

        // none
        invasions.put("lotr:white_beach", new HashMap<>());
        invasions.put("lotr:western_isles", new HashMap<>());

        invasions.put("lotr:wilderland", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
                // TODO: Add the rest of these add they are added
                /*LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.RARE,
                LOTRInvasions.DALE, LOTREventSpawner.EventChance.RARE
                LOTRInvasions.DORWINION, LOTREventSpawner.EventChance.RARE
                LOTRInvasions.RHUN, LOTREventSpawner.EventChance.UNCOMMON */
        ));

        invasions.put("lotr:white_mountains", genMap(
               ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));
        invasions.put("lotr:white_mountains_foothills", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));
    }

    public static Map<ExtendedInvasions, InvasionChance> genMap(Object... entries) {
        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments. Must be even (key-value pairs).");
        }
        Map<ExtendedInvasions, InvasionChance> map = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            ExtendedInvasions key = (ExtendedInvasions) entries[i];
            InvasionChance value = (InvasionChance) entries[i + 1];
            map.put(key, value);
        }
        return map;
    }

    /**
     * This method given a biome name from World#getBiomeName() will attempt to check if it is a valid LOTR Mod biome
     * and if it is pick an invasion from its list and return the ExtendedInvasions Enum. If no invasions
     * spawn in that biome it returns null. If it is not a valid biome it throws an IllegalArgumentException
     *
     * @param world the {@link World} to check
     * @param biomeName the name of the biome to query
     * @return The {@link String} name of the trader to spawn, null if no traders can spawn
     * @throws IllegalArgumentException if the given biome is not a LOTR biome
     */
    public static ExtendedInvasions pickInvasionFromBiomeName(World world, String biomeName) throws IllegalArgumentException {
        // determine the LOTR biome
        String lotrBiome;
        int index = biomeName.indexOf("lotr");
        if (index != -1) {
            lotrBiome = biomeName.substring(index);
            lotrBiome = lotrBiome.replace("]", "");
        } else {
            throw new IllegalArgumentException(biomeName + " is not a LOTR Mod biome");
        }

        if (invasions.containsKey(lotrBiome)) {
            Map<ExtendedInvasions, InvasionChance> rates = invasions.get(lotrBiome);

            if (rates.isEmpty()) {
                return null;
            } else {
                return getInvasionFromHashmap(rates, world);
            }
        } else {
            return null;
        }
    }

    private static ExtendedInvasions getInvasionFromHashmap(Map<ExtendedInvasions, InvasionChance> rates, World world) {
        // generate minimums
        Map<Integer, ExtendedInvasions> minimums = new HashMap<>();
        int minimum = 1;
        for (ExtendedInvasions key : rates.keySet()) {
            minimums.put(minimum, key);
            minimum += rates.get(key).getChance();
        }

        int random = world.random.nextInt(100) + 1;
        Object[] minimumsSorted = minimums.keySet().toArray();
        Arrays.sort(minimumsSorted);
        for (int i = minimumsSorted.length - 1; i > -1; i--) {
            if (random >= (Integer) minimumsSorted[i]) return minimums.get(minimumsSorted[i]);
        }

        return null;
    }

    public static Map<ExtendedInvasions, InvasionChance> getInvasionsPerBiome(String biomeName) {
        String lotrBiome;
        int index = biomeName.indexOf("lotr");
        if (index != -1) {
            lotrBiome = biomeName.substring(index);
            lotrBiome = lotrBiome.replace("]", "");
        } else {
            LOTRLog.info("Attempted to determine invasion on \"" +  biomeName + "\" that is not a LOTR Mod biome");
            return null;
        }
        return invasions.get(lotrBiome);
    }
}
