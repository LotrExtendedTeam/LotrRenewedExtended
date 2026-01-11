package lotr.common.world;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ExtendedInvasions {

    RIVENDELL("lotr:high_elves,rivendell", new ItemStack(LOTRItems.RIVENDELL_SWORD.get(), 1)),
    LINDON("lotr:high_elves,lindon", new ItemStack(LOTRItems.LINDON_SWORD.get(), 1)),
    GUNDABAD("lotr:northern_orcs", new ItemStack(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1)),
    GUNDABAD_WARGS("lotr:northern_orcs,wargs", new ItemStack(LOTRItems.WARG_BONE.get(), 1)),
    DUNEDAIN("lotr:dunedain_north", new ItemStack(ExtendedItems.DUNEDAIN_BOW.get(), 1)),
    MORDOR("lotr:mordor", new ItemStack(LOTRItems.MORDOR_SCIMITAR.get(), 1)),
    GONDOR("lotr:gondor", new ItemStack(LOTRItems.GONDOR_SWORD.get(), 1)),
    LOTHLORIEN("lotr:lothlorien", new ItemStack(LOTRItems.GALADHRIM_SWORD.get(), 1)),
    WOODLAND_REALM("lotr:woodland_realm", new ItemStack(LOTRItems.WOOD_ELVEN_SWORD.get(), 1));

    private final String key;
    private final ItemStack stack;

    private static final Map<String, ExtendedInvasions> BY_KEY =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            ExtendedInvasions::getKey,
                            e -> e
                    ));

    ExtendedInvasions(String key, ItemStack stack) {
        this.key = key;
        this.stack = stack;
    }

    public String getKey() {
        return key;
    }

    public ItemStack getStack() {
        return stack;
    }

    public static Optional<ExtendedInvasions> fromKey(String key) {
        return Optional.ofNullable(BY_KEY.get(key));
    }

}
