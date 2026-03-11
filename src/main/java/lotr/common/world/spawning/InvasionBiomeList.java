package lotr.common.world.spawning;

import lotr.common.world.ExtendedInvasions;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

/**
 * Central registry mapping LOTR biomes to the invasions that may spawn within them.
 *
 * <p>This class serves two primary use cases:</p>
 * <ul>
 *   <li><b>Forced spawns</b> — uses weighted selection via
 *       {@link #pickInvasionFromBiomeName(World, ResourceLocation)}</li>
 *   <li><b>Random spawns</b> — retrieves all possible invasions via
 *       {@link #getInvasionsPerBiome(ResourceLocation)}</li>
 * </ul>
 *
 * <p>Each biome maps to a set of {@link ExtendedInvasions} with their associated
 * {@link InvasionChance} values.</p>
 */
public class InvasionBiomeList {
	/**
     * Internal registry of biome → invasion chance mappings.
     *
     * <p>Key: LOTR biome {@link ResourceLocation}</p>
     * <p>Value: Map of possible {@link ExtendedInvasions} and their
     * associated {@link InvasionChance}</p>
     *
     * <p>This map is populated during mod initialization via the private
     * {@code register(...)} helpers.</p>
     */
    protected static final Map<ResourceLocation, Map<ExtendedInvasions, InvasionChance>> invasions = new HashMap<>();

    static {
        // Shire is free from invasions
        register("lotr:shire", new HashMap<>());
        register("lotr:shire_moors", new HashMap<>());
        register("lotr:shire_marshes", new HashMap<>());
        register("lotr:shire_woodlands", new HashMap<>());
        register("lotr:white_downs", new HashMap<>());

        // Blue Mountains
        register("lotr:blue_mountains", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));
        register("lotr:blue_mountains_foothills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));

        // Eriador
        // TODO: Add the following to Eriador when they are added: Rhudaur Hillmen [Uncommon], Angmar [Rare], Angmar Wargs [Rare]
        register("lotr:eriador", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        register("lotr:eryn_vorn", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        register("lotr:hills_of_evendim", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));
        register("lotr:eriador_downs", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        register("lotr:tower_hills", genMap(ExtendedInvasions.GUNDABAD, InvasionChance.RARE));
        register("lotr:minhiriath", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON)
        );
        register("lotr:swanfleet", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE)
        );

        // only Rivendell Hills have invasions
        register("lotr:rivendell", new HashMap<>());
        register("lotr:rivendell_valley", new HashMap<>());
        register("lotr:rivendell_hills", genMap(
                ExtendedInvasions.RIVENDELL, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Gondor
        // TODO: These are missing a ton of factions because the NPCs are missing
        //       Add Factions like Black Uruks later
        register("lotr:gondor", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        // TODO: For now there is no Harad factions so no invasions here
        register("lotr:further_gondor", new HashMap<>());
        register("lotr:field_of_cormallen", new HashMap<>()); // None here either?
        register("lotr:anorien", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:blackroot_vale", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:lamedon", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:lamedon_hills", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:pelargir", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:anfalas", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:lossarnach", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:pinnath_gelin", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:ithilien", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.COMMON,
                ExtendedInvasions.GONDOR, InvasionChance.UNCOMMON
        ));
        register("lotr:lebennin", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:emyn_en_ernil", genMap(ExtendedInvasions.MORDOR, InvasionChance.RARE));
        register("lotr:dor_en_ernil", genMap(ExtendedInvasions.MORDOR, InvasionChance.RARE));

        // Older elven regions
        register("lotr:lindon", genMap(ExtendedInvasions.GUNDABAD, InvasionChance.RARE));
        register("lotr:lindon_woodlands", genMap(ExtendedInvasions.GUNDABAD, InvasionChance.RARE));
        register("lotr:eregion", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));

        // TODO: Add Dol-Guldur when they are added
        register("lotr:lothlorien_eaves", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));
        register("lotr:lothlorien", new HashMap<>()); // no invasions in Lothlorien proper

        register("lotr:gladden_fields", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));

        // Bree-land
        register("lotr:breeland", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));
        register("lotr:chetwood", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Rohan
        // TODO: Add Dunlending invasions when the faction is added
        register("lotr:rohan", genMap(
                ExtendedInvasions.ISENGARD, InvasionChance.UNCOMMON
        ));
        register("lotr:wold", genMap(
                ExtendedInvasions.ISENGARD, InvasionChance.UNCOMMON
        ));

        register("lotr:fangorn", new HashMap<>()); // no invasions in Fangorn
        register("lotr:mouths_of_entwash", genMap(ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON));
        register("lotr:enedwaith", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add Dunlendings here when they are added
        ));

        register("lotr:northern_dale", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON,
                ExtendedInvasions.MORDOR, InvasionChance.RARE
                // TODO: Add Dunland when they are added
                // TODO: Add Rhun when they are added
        ));
        register("lotr:dale", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON,
                ExtendedInvasions.MORDOR, InvasionChance.RARE
                // TODO: Add Dunland when they are added
                // TODO: Add Rhun when they are added
        ));

        register("lotr:iron_hills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
        ));
        register("lotr:dorwinion", genMap(
                // TODO: Add Dol-Guldur when it's added
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        register("lotr:emyn_winion", genMap(
            ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        register("lotr:dunland", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.ROHAN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Umbar has no invasions
        register("lotr:umbar", new HashMap<>());
        register("lotr:umbar_hills", new HashMap<>());
        register("lotr:umbar_forest", new HashMap<>());

        // Northern Wilderland has no invasions?
        register("lotr:northern_wilderland", new  HashMap<>());

        register("lotr:harondor", genMap(
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
        register("lotr:harnennor", genMap(
                ExtendedInvasions.GONDOR, InvasionChance.UNCOMMON,
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
        ));

        // Lone-lands
        register("lotr:lone_lands", genMap(
               ExtendedInvasions.DUNEDAIN, InvasionChance.COMMON,
               ExtendedInvasions.GUNDABAD, InvasionChance.COMMON,
               ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.COMMON,
               ExtendedInvasions.RIVENDELL, InvasionChance.RARE
               // TODO: Add Angmar here when they are added
               // TODO: Add Rhudaur Hillmen here when they are added
        ));
        register("lotr:lone_lands_hills", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.COMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.COMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.COMMON,
                ExtendedInvasions.RIVENDELL, InvasionChance.RARE
                // TODO: Add Angmar here when they are added
                // TODO: Add Rhudaur Hillmen here when they are added
        ));

        // Mirkwood
        register("lotr:woodland_realm", new HashMap<>()); // TODO: Add Dol-Guldur when they are added
        register("lotr:northern_mirkwood", genMap(
                // TODO: Add Dol-Guldur when they are added
                ExtendedInvasions.WOODLAND_REALM, InvasionChance.UNCOMMON
        ));
        register("lotr:mirkwood", genMap(
                ExtendedInvasions.WOODLAND_REALM, InvasionChance.UNCOMMON
        ));
        register("lotr:mirkwood_mountains", genMap(
                ExtendedInvasions.WOODLAND_REALM, InvasionChance.UNCOMMON
        ));

        // TODO: Add more factions here when others are ready
        // Anduin
        register("lotr:vales_of_anduin", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.LOTHLORIEN, InvasionChance.UNCOMMON
        ));
        register("lotr:anduin_hills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.LOTHLORIEN, InvasionChance.UNCOMMON
        ));
        register("lotr:ethir_anduin", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.LOTHLORIEN, InvasionChance.UNCOMMON
        ));

        register("lotr:andrast", new HashMap<>());
        register("lotr:angmar", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.LINDON, InvasionChance.RARE,
                ExtendedInvasions.RIVENDELL, InvasionChance.RARE
        ));
        register("lotr:angmar_mountains", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.LINDON, InvasionChance.RARE,
                ExtendedInvasions.RIVENDELL, InvasionChance.RARE
        ));

        register("lotr:brown_lands", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
                // TODO: Add Mordor Wargs and Mordor Black Uruks when they are added
        ));

        register("lotr:coldfells", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add these when they are ready
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        ));

        register("lotr:dagorlad", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
                // TODO: Add Mordor Wargs and Mordor Black Uruks when they are added
        ));

        register("lotr:dead_marshes", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        register("lotr:dense_snowy_northlands_forest", new HashMap<>()); // no invasions here
        register("lotr:dense_northlands_forest", new HashMap<>()); // no invasions here
        register("lotr:druwaith_iaur", new HashMap<>()); // no invasions here

        // one does not simply invade Mordor
        register("lotr:mordor", new HashMap<>());
        register("lotr:mordor_mountains", new HashMap<>());
        register("lotr:eastern_desolation", new HashMap<>());
        register("lotr:gorgoroth", new HashMap<>());
        register("lotr:morgul_vale", new HashMap<>());
        register("lotr:nurn", new HashMap<>());
        register("lotr:nurn_marshes", new HashMap<>());
        register("lotr:nurnen", new HashMap<>());

        register("lotr:ettenmoors", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.COMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.COMMON
                // TODO: Add these when they are ready
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        ));

        // forochel and forodwaith have no invasions
        register("lotr:forochel", new HashMap<>());
        register("lotr:forodwaith", new HashMap<>());
        register("lotr:forodwaith_mountains", new HashMap<>());

        register("lotr:grey_mountains", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
               // TODO: Add the Iron Hills Dwarves when they are added
        ));
        register("lotr:grey_mountains_foothills", genMap(
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add the Iron Hills Dwarves when they are added
        ));

        // TODO: Not sure what should be in these biomes or when we'll have factions that make sense here
        register("lotr:harad_desert", new HashMap<>());
        register("lotr:harad_desert_hills", new HashMap<>());
        register("lotr:harad_half_desert", new HashMap<>());
        register("lotr:lostladen", new HashMap<>());

        register("lotr:long_marshes", new HashMap<>()); // none here

        register("lotr:trollshaws", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD, InvasionChance.UNCOMMON,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.UNCOMMON
                // TODO: Add these when they are ready
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
                //this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        ));

        register("lotr:midgewater", genMap(
                ExtendedInvasions.DUNEDAIN, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD, InvasionChance.RARE,
                ExtendedInvasions.GUNDABAD_WARGS, InvasionChance.RARE
        ));

        // Misty Mountains has no invasions
        register("lotr:misty_mountains", new HashMap<>());
        register("lotr:misty_mountains_foothills", new HashMap<>());

        register("lotr:nindalf", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));

        register("lotr:nan_curunir", genMap(
                ExtendedInvasions.ROHAN, InvasionChance.RARE
        ));

        // no invasions in Northlands
        register("lotr:northlands", new HashMap<>());
        register("lotr:northlands_forest", new HashMap<>());
        register("lotr:snowy_northlands", new HashMap<>());
        register("lotr:snowy_northlands_forest", new HashMap<>());

        register("lotr:old_forest", new HashMap<>()); // no invasions in Old Forest

        // TODO: Not sure what invasions we should eventually go here
        register("lotr:southron_coasts", new HashMap<>());
        register("lotr:southron_coasts_forest", new HashMap<>());

        register("lotr:tolfalas", new HashMap<>()); // none

        // none
        register("lotr:white_beach", new HashMap<>());
        register("lotr:western_isles", new HashMap<>());

        register("lotr:wilderland", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.UNCOMMON
                // TODO: Add the rest of these add they are added
                /*LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.RARE,
                LOTRInvasions.DALE, LOTREventSpawner.EventChance.RARE
                LOTRInvasions.DORWINION, LOTREventSpawner.EventChance.RARE
                LOTRInvasions.RHUN, LOTREventSpawner.EventChance.UNCOMMON */
        ));

        register("lotr:white_mountains", genMap(
               ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));
        register("lotr:white_mountains_foothills", genMap(
                ExtendedInvasions.MORDOR, InvasionChance.RARE
        ));
    }

    /**
     * Registers invasion spawn rates for a biome using a string biome name.
     *
     * <p>The biome name will be converted into a {@link ResourceLocation} and
     * associated with the provided invasion chance map.</p>
     *
     * @param biomeName the biome identifier string (e.g., "lotr:shire")
     * @param invasionMap mapping of {@link ExtendedInvasions} to their spawn chances
     */
    private static void register(String biomeName, Map<ExtendedInvasions, InvasionChance> invasionMap) {
    	invasions.put(new ResourceLocation(biomeName), invasionMap);
    }

    /**
     * Registers invasion spawn rates for a biome.
     *
     * @param biomeName the biome {@link ResourceLocation}
     * @param invasionMap mapping of {@link ExtendedInvasions} to their spawn chances
     */
    private static void register(ResourceLocation biomeName, Map<ExtendedInvasions, InvasionChance> invasionMap) {
    	invasions.put(biomeName, invasionMap);
    }

    /**
     * Utility method for constructing an invasion chance map using
     * alternating key/value pairs.
     *
     * <p>Arguments must be provided in the form:</p>
     * <pre>
     * genMap(INVASION_A, CHANCE_A,
     *        INVASION_B, CHANCE_B)
     * </pre>
     *
     * @param entries alternating {@link ExtendedInvasions} and {@link InvasionChance}
     * @return a populated {@link Map} of invasions to their chances
     *
     * @throws IllegalArgumentException if the number of arguments is not even
     * @throws ClassCastException if any entry is of the wrong type
     */
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
	 * Convenience overload that selects an invasion using an optional biome key.
	 *
	 * <p>If the optional is empty, this method returns {@code null}. Otherwise,
	 * the biome registry name is extracted and delegated to
	 * {@link #pickInvasionFromBiomeName(World, ResourceLocation)}.</p>
	 *
	 * <p>This is primarily used in forced-spawn paths where biome lookup may fail
	 * but should not be treated as an error.</p>
	 *
	 * @param world the world used for randomness
	 * @param biomeKey optional biome registry key from the world
	 * @return the selected {@link ExtendedInvasions}, or {@code null} if the biome is absent or has no valid invasions
	 *
	 * @throws IllegalArgumentException if the resolved biome is not part of the LOTR namespace
	 */
	public static ExtendedInvasions pickInvasionFromBiomeName(World world, Optional<RegistryKey<Biome>> biomeKey) throws IllegalArgumentException {
		if(!biomeKey.isPresent()) {
			throw new IllegalArgumentException("Unable to determine biome for location");
        }
    	return pickInvasionFromBiomeName(world, biomeKey.get().location());
	}
	/**
	 * Selects a random invasion for the given LOTR biome.
	 *
	 * <p>This method is primarily used for <b>forced invasion spawns</b> (e.g., commands).
	 * It validates that the biome belongs to the LOTR namespace and then performs a
	 * weighted random selection using the registered invasion chances.</p>
	 *
	 * <p>Behavior:</p>
	 * <ul>
	 *   <li>If the biome is not an LOTR biome → throws {@link IllegalArgumentException}</li>
	 *   <li>If the biome has no registered invasions → returns {@code null}</li>
	 *   <li>If the biome has entries → returns a weighted random invasion</li>
	 * </ul>
	 *
	 * @param world the world used for randomness
	 * @param biomeName the biome registry name to query
	 * @return the selected {@link ExtendedInvasions}, or {@code null} if none can spawn
	 *
	 * @throws IllegalArgumentException if the biome is not part of the LOTR namespace
	 */
    public static ExtendedInvasions pickInvasionFromBiomeName(World world, ResourceLocation biomeName) throws IllegalArgumentException {
        if (!biomeName.getNamespace().contains("lotr")) {
        	throw new IllegalArgumentException(biomeName + " is not a LOTR Mod biome");
        }

        if (invasions.containsKey(biomeName)) {
            Map<ExtendedInvasions, InvasionChance> rates = invasions.get(biomeName);
            if (rates == null || rates.isEmpty()) {
                return null;
            }
            return getInvasionFromHashmap(rates, world);
        } else {
            return null;
        }
    }
    /**
     * Performs a weighted random selection from the provided invasion map.
     *
     * <p>Each {@link InvasionChance#getChance()} value contributes to the total
     * weight out of 100. The world's random instance is used for selection.</p>
     *
     * @param rates map of invasions to their weighted chances
     * @param world the world providing the random source
     * @return the selected {@link ExtendedInvasions}, or {@code null} if none matched
     */
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
    /**
     * Convenience overload that resolves invasions from an optional biome key.
     *
     * <p>If the optional is empty, this method returns an empty map. Otherwise,
     * the biome's {@link ResourceLocation} is extracted and delegated to
     * {@link #getInvasionsPerBiome(ResourceLocation)}.</p>
     *
     * <p>This is primarily used in random spawning paths where the biome lookup
     * may legitimately fail.</p>
     *
     * @param biomeKey optional biome registry key from the world
     * @return a map of possible invasions and their chances, or an empty map if the biome is absent or has no registered invasions
     */
    public static Map<ExtendedInvasions, InvasionChance> getInvasionsPerBiome(Optional<RegistryKey<Biome>> biomeKey) {
    	if(!biomeKey.isPresent()) {
    		ExtendedLog.info("Unable to determine biome for location");
        	return Collections.emptyMap();
        }
    	return getInvasionsPerBiome(biomeKey.get().location());
    }
    /**
     * Retrieves all invasions that are allowed to spawn in the given biome.
     *
     * <p>This method is primarily used during <b>random per-tick spawning</b>.
     * Unlike {@link #pickInvasionFromBiomeName(World, ResourceLocation)},
     * this method does not perform any random selection.</p>
     *
     * <p>If the biome is not part of the LOTR namespace, an empty map is returned
     * and a log entry is written.</p>
     *
     * @param biomeName the biome registry name
     * @return a map of possible invasions and their chances, or an empty map if none apply
     */
    @Nonnull
    public static Map<ExtendedInvasions, InvasionChance> getInvasionsPerBiome(ResourceLocation biomeName) {
    	if (!biomeName.getNamespace().contains("lotr")) {
    		ExtendedLog.info("Attempted to determine invasion for {" +  biomeName.toString() + "}, but it is not a LOTR Mod biome!");
            return Collections.emptyMap();
        }
    	return invasions.getOrDefault(biomeName, Collections.emptyMap());
    }
}
