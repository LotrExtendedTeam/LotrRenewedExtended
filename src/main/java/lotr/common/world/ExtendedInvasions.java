package lotr.common.world;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

import java.util.*;
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
    WOODLAND_REALM("lotr:woodland_realm", new ItemStack(LOTRItems.WOOD_ELVEN_SWORD.get(), 1)),
    ROHAN("lotr:rohan", new ItemStack(LOTRItems.ROHAN_SWORD.get(), 1));

    private final String key;
    private final ItemStack stack;
    private List<InvasionSpawnEntry> spawnEntries = new ArrayList<>();

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

    private void addInvasionMob(InvasionSpawnEntry entry) {
        spawnEntries.add(entry);
    }

    public static class InvasionSpawnEntry extends WeightedRandom.Item {

        private EntityType<?> entityType;

        public InvasionSpawnEntry(EntityType<?> type, int chance) {
            super(chance);
            this.entityType = type;
        }

        public EntityType<?> getEntityType() {
            return entityType;
        }
    }

    static {
        ExtendedInvasions.RIVENDELL.addInvasionMob(new InvasionSpawnEntry(LOTREntities.RIVENDELL_WARRIOR.get(), 10));
        ExtendedInvasions.LINDON.addInvasionMob(new InvasionSpawnEntry(LOTREntities.LINDON_WARRIOR.get(), 10));

        ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_ORC.get(), 20));
        ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_ORC_ARCHER.get(), 10));
        ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_WARG.get(), 20));
        ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.GUNDABAD_URUK.get(), 5));
        ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), 2));

        ExtendedInvasions.GUNDABAD_WARGS.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_WARG.get(), 10));

        ExtendedInvasions.DUNEDAIN.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.DUNEDAIN_RANGER.get(), 15));

        ExtendedInvasions.MORDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.MORDOR_ORC.get(), 10));
        ExtendedInvasions.MORDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.MORDOR_ORC_ARCHER.get(), 5));
        ExtendedInvasions.MORDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.MORDOR_WARG.get(), 10));
        // TODO: Add Bombardiers when they are added
        // TODO: Add Black Uruks when they are added
        // TODO: Add Olog-hai when they are added

        ExtendedInvasions.GONDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GONDOR_SOLDIER.get(), 10));
        ExtendedInvasions.GONDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GONDOR_ARCHER.get(), 5));
        // TODO: Add Gondor Levyman when they are added

        ExtendedInvasions.LOTHLORIEN.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GALADHRIM_WARRIOR.get(), 10));

        ExtendedInvasions.WOODLAND_REALM.addInvasionMob(new InvasionSpawnEntry(LOTREntities.WOOD_ELF_WARRIOR.get(), 10));

        ExtendedInvasions.ROHAN.addInvasionMob(new InvasionSpawnEntry(LOTREntities.ROHIRRIM_WARRIOR.get(), 10));
        ExtendedInvasions.ROHAN.addInvasionMob(new InvasionSpawnEntry(LOTREntities.ROHIRRIM_WARRIOR.get(), 5));
    }



}
