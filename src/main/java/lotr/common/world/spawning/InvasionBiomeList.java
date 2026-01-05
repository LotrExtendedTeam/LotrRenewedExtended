package lotr.common.world.spawning;

import lotr.common.world.ExtendedInvasions;

import java.util.HashMap;
import java.util.Map;

public class InvasionBiomeList {

    protected static Map<String, Map<ExtendedInvasions, InvasionChance>> invasions = new HashMap<>();

    static {
        /*
        // NOTE: Only factions that are supported in the spawner entity at the moment
        "lotr:high_elves,rivendell"
        "lotr:high_elves,lindon"
        "lotr:northern_orcs"
        "lotr:northern_orcs,wargs"
        "lotr:dunedain_north"
         */

        // Shire is free from invasions
        invasions.put("lotr:shire", new HashMap<>());
        invasions.put("lotr:shire_moors", new HashMap<>());
        invasions.put("lotr:shire_marshes", new HashMap<>());
        invasions.put("lotr:shire_woodlands", new HashMap<>());
        invasions.put("lotr:white_downs", new HashMap<>());

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

        // TODO: Add more factions here when others are ready
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
}
