package lotr.common.util.representation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum LOTREnchantment {
    ORC_BANE("orcbane", "bane", "melee"),
    DWARF_BANE("dwarfBane", "bane", "melee"),
    ELF_BANE("elfbane", "bane", "melee"),
    WARG_BANE("wargbane", "bane", "melee"),
    WIGHT_BANE("wightbane", "bane", "melee");

    LOTREnchantment(String key, String enchantType, String itemType) {
        this.key = key;
        this.enchantType = enchantType;
        this.itemType = itemType;
    }

    public static final Map<String, LOTREnchantment> ENCHANT_MAP;

    static {
        Map<String, LOTREnchantment> enchantMap = new HashMap<>();
        enchantMap.put("dwarfbane", LOTREnchantment.DWARF_BANE);
        enchantMap.put("elfbane", LOTREnchantment.ELF_BANE);
        enchantMap.put("orcbane", LOTREnchantment.ORC_BANE);
        enchantMap.put("wargbane", LOTREnchantment.WARG_BANE);
        enchantMap.put("wightbane", LOTREnchantment.WIGHT_BANE);
        ENCHANT_MAP = Collections.unmodifiableMap(enchantMap);
    }

    // the name that shows in the tooltip
    private String key;

    // used to determine compatibility with other enchants
    // e.g. a weapon can only have one bane type
    private String enchantType;

    // used to determine what types of items it can be applied to
    private String itemType;

    public String getKey() {
        return key;
    }

    public String getEnchantType() {
        return enchantType;
    }

    public String getItemType() {
        return itemType;
    }

}
