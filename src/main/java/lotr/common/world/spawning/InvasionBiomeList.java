package lotr.common.world.spawning;

import java.util.HashMap;
import java.util.Map;

public class InvasionBiomeList {

    protected static Map<String, Map<String, InvasionChance>> invasions = new HashMap<>();

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
        invasions.put("lotr:eriador", genMap("lotr:dunedain_north", InvasionChance.UNCOMMON, "lotr:northern_orcs", InvasionChance.UNCOMMON, "lotr:northern_orcs,wargs", InvasionChance.UNCOMMON));
        // TODO: Finish going through and updating these
        /*"lotr:eryn_vorn"
        "lotr:hills_of_evendim"
        "lotr:eriador_downs"
        "lotr:tower_hills"
        "lotr:minhiriath"
        "lotr:swanfleet"*/
    }

    public static Map<String, InvasionChance> genMap(Object... entries) {
        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments. Must be even (key-value pairs).");
        }
        Map<String, InvasionChance> map = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            String key = (String) entries[i];
            InvasionChance value = (InvasionChance) entries[i + 1];
            map.put(key, value);
        }
        return map;
    }
}
