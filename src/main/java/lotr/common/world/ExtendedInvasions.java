package lotr.common.world;

import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.DatagenModLoader;

import java.util.*;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

public enum ExtendedInvasions {

	RIVENDELL("lotr:high_elves_rivendell", FactionPointers.HIGH_ELVES, new ItemStack(LOTRItems.RIVENDELL_SWORD.get())),
	LINDON("lotr:high_elves_lindon", FactionPointers.HIGH_ELVES, new ItemStack(LOTRItems.LINDON_SWORD.get())),
	GUNDABAD("lotr:northern_orcs", FactionPointers.NORTHERN_ORCS, new ItemStack(ExtendedItems.GUNDABAD_URUK_CLEAVER.get())),
	GUNDABAD_WARGS("lotr:northern_orcs_wargs", FactionPointers.NORTHERN_ORCS, new ItemStack(LOTRItems.WARG_BONE.get())),
	DUNEDAIN("lotr:dunedain_north", FactionPointers.DUNEDAIN_NORTH, new ItemStack(ExtendedItems.DUNEDAIN_BOW.get())),
	MORDOR("lotr:mordor", FactionPointers.MORDOR, new ItemStack(LOTRItems.MORDOR_SCIMITAR.get())),
	GONDOR("lotr:gondor", FactionPointers.GONDOR, new ItemStack(LOTRItems.GONDOR_SWORD.get())),
	LOTHLORIEN("lotr:lothlorien", FactionPointers.LOTHLORIEN, new ItemStack(LOTRItems.GALADHRIM_SWORD.get())),
	WOODLAND_REALM("lotr:woodland_realm", FactionPointers.WOODLAND_REALM, new ItemStack(LOTRItems.WOOD_ELVEN_SWORD.get())),
	ROHAN("lotr:rohan", FactionPointers.ROHAN, new ItemStack(LOTRItems.ROHAN_SWORD.get())),
	ISENGARD("lotr:isengard", FactionPointers.ISENGARD, new ItemStack(LOTRItems.URUK_CLEAVER.get())),
	DUNLAND("lotrextended:isengard", FactionPointers.DUNLAND, new ItemStack(LOTRItems.DUNLENDING_CLUB.get())),
	BREE("lotrextended:dunland", FactionPointers.BREE, new ItemStack(Items.IRON_SWORD)),
	BLUE_MOUNTAINS("lotrextended:blue_mountains", FactionPointers.BLUE_MOUNTAINS, new ItemStack(LOTRItems.BLUE_DWARVEN_SWORD.get())),
	DALE("lotrextended:dale", FactionPointers.DALE, new ItemStack(LOTRItems.DALE_SWORD.get())),
	DURINS_FOLK("lotrextended:durins_folk", FactionPointers.DURINS_FOLK, new ItemStack(LOTRItems.DWARVEN_SWORD.get())),
	UMBAR("lotrextended:near_harad_umbar", FactionPointers.NEAR_HARAD, new ItemStack(LOTRItems.UMBAR_SCIMITAR.get()));

	private final ResourceLocation typeKey;
	private final ItemStack stack;
	private List<InvasionSpawnEntry> spawnEntries = new ArrayList<>();
	private final FactionPointer factionPointer;

	private static final Map<ResourceLocation, ExtendedInvasions> BY_KEY = Arrays.stream(values()).collect(Collectors.toMap(ExtendedInvasions::getTypeKey, e -> e));

	private ExtendedInvasions(String pTypeKey, FactionPointer pFactionPointer, ItemStack pStack) {
		this(new ResourceLocation(pTypeKey), pFactionPointer, pStack);
	}

	private ExtendedInvasions(ResourceLocation pTypeKey, FactionPointer pFactionPointer, ItemStack pStack) {
		this.typeKey = pTypeKey;
		this.factionPointer = pFactionPointer;
		this.stack = pStack;
		
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.invasionNames.add("lotrextended.invasion." + this.name().toLowerCase());
		}
	}

	public ResourceLocation getTypeKey() {
		return typeKey;
	}

	public FactionPointer getFactionPointer() {
		return factionPointer;
	}

	public ItemStack getStack() {
		return stack;
	}

	public List<InvasionSpawnEntry> getSpawnEntries() {
		return spawnEntries;
	}

	public static Optional<ExtendedInvasions> fromTypeKey(ResourceLocation key) {
		return Optional.ofNullable(BY_KEY.get(key));
	}

	public IFormattableTextComponent getTranslatableName() {
		return new TranslationTextComponent("lotrextended.invasion." + this.name().toLowerCase());
	}

	private void addInvasionMob(InvasionSpawnEntry entry) {
		spawnEntries.add(entry);
	}

	public static class InvasionSpawnEntry extends WeightedRandom.Item {

		private EntityType<? extends MobEntity> entityType;

		public InvasionSpawnEntry(EntityType<? extends MobEntity> type, int chance) {
			super(chance);
			this.entityType = type;
		}

		public EntityType<? extends MobEntity> getEntityType() {
			return entityType;
		}
	}

	static {
		ExtendedInvasions.RIVENDELL.addInvasionMob(new InvasionSpawnEntry(LOTREntities.RIVENDELL_WARRIOR.get(), 10));
		// TODO: Add Banner Bearers

		ExtendedInvasions.LINDON.addInvasionMob(new InvasionSpawnEntry(LOTREntities.LINDON_WARRIOR.get(), 10));
		// TODO: Add Banner Bearers

		ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_ORC.get(), 20));
		ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_ORC_ARCHER.get(), 10));
		ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_WARG.get(), 20));
		ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.GUNDABAD_URUK.get(), 5));
		ExtendedInvasions.GUNDABAD.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), 2));
        // TODO: Add Banner Bearers

		ExtendedInvasions.GUNDABAD_WARGS.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GUNDABAD_WARG.get(), 10));

		ExtendedInvasions.DUNEDAIN.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.DUNEDAIN_RANGER.get(), 15));
        // TODO: Add Banner Bearers

		ExtendedInvasions.MORDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.MORDOR_ORC.get(), 10));
		ExtendedInvasions.MORDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.MORDOR_ORC_ARCHER.get(), 5));
		ExtendedInvasions.MORDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.MORDOR_WARG.get(), 10));
        // TODO: Add Banner Bearers, Bombardiers, Warg Bombardiers, Black Uruk Archer/Warrior/Banner Bearer, OlogHai

		ExtendedInvasions.GONDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GONDOR_SOLDIER.get(), 10));
		ExtendedInvasions.GONDOR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GONDOR_ARCHER.get(), 5));
		// TODO: Add Banner Bearers, Levyman

		ExtendedInvasions.LOTHLORIEN.addInvasionMob(new InvasionSpawnEntry(LOTREntities.GALADHRIM_WARRIOR.get(), 10));
        // TODO: Add Banner Bearers

		ExtendedInvasions.WOODLAND_REALM.addInvasionMob(new InvasionSpawnEntry(LOTREntities.WOOD_ELF_WARRIOR.get(), 10));
        // TODO: Add Banner Bearers, Scout

		ExtendedInvasions.ROHAN.addInvasionMob(new InvasionSpawnEntry(LOTREntities.ROHIRRIM_WARRIOR.get(), 10));
		ExtendedInvasions.ROHAN.addInvasionMob(new InvasionSpawnEntry(LOTREntities.ROHIRRIM_BOWMAN.get(), 5));
        // TODO: Add Banner Bearers

		ExtendedInvasions.ISENGARD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.ISENGARD_SNAGA.get(), 5));
		ExtendedInvasions.ISENGARD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.ISENGARD_SNAGA_ARCHER.get(), 5));
		ExtendedInvasions.ISENGARD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.URUK.get(), 10));
		ExtendedInvasions.ISENGARD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.URUK_ARCHER.get(), 5));
		ExtendedInvasions.ISENGARD.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.URUK_BERSERKER.get(), 2));
		ExtendedInvasions.ISENGARD.addInvasionMob(new InvasionSpawnEntry(LOTREntities.ISENGARD_WARG.get(), 10));
		// TODO: Add Crossbow Uruk, Sappers, Banner Bearers, and Warg Bombardiers

		ExtendedInvasions.DUNLAND.addInvasionMob(new InvasionSpawnEntry(LOTREntities.DUNLENDING.get(), 10));
		ExtendedInvasions.DUNLAND.addInvasionMob(new InvasionSpawnEntry(LOTREntities.DUNLENDING_WARRIOR.get(), 5));
		ExtendedInvasions.DUNLAND.addInvasionMob(new InvasionSpawnEntry(LOTREntities.DUNLENDING_BOWMAN.get(), 3));
		ExtendedInvasions.DUNLAND.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.DUNLENDING_AXE_THROWER.get(), 3));
		ExtendedInvasions.DUNLAND.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.DUNLENDING_BERSERKER.get(), 2));
		// TODO: Add Banner Bearers

		ExtendedInvasions.BREE.addInvasionMob(new InvasionSpawnEntry(LOTREntities.BREE_GUARD.get(), 15));
		// TODO: Add Banner Bearers

		ExtendedInvasions.BLUE_MOUNTAINS.addInvasionMob(new InvasionSpawnEntry(LOTREntities.BLUE_MOUNTAINS_WARRIOR.get(), 10));
		ExtendedInvasions.BLUE_MOUNTAINS.addInvasionMob(new InvasionSpawnEntry(LOTREntities.BLUE_MOUNTAINS_ARCHER.get(), 8));
		ExtendedInvasions.BLUE_MOUNTAINS.addInvasionMob(new InvasionSpawnEntry(ExtendedEntities.BLUE_DWARVEN_AXE_THROWER.get(), 5));
		// TODO: Add Banner Bearers

		ExtendedInvasions.DALE.addInvasionMob(new InvasionSpawnEntry(LOTREntities.DALE_SOLDIER.get(), 10));
		ExtendedInvasions.DALE.addInvasionMob(new InvasionSpawnEntry(LOTREntities.DALE_BOWMAN.get(), 5));
		// TODO: Add Banner Bearers, Levyman

		ExtendedInvasions.DURINS_FOLK.addInvasionMob(new InvasionSpawnEntry(LOTREntities.DWARF_WARRIOR.get(), 10));
		ExtendedInvasions.DURINS_FOLK.addInvasionMob(new InvasionSpawnEntry(LOTREntities.DWARF_ARCHER.get(), 8));
		// TODO: Add Banner Bearers, Axe Thrower

		ExtendedInvasions.UMBAR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.UMBAR_SOLDIER.get(), 100));
		ExtendedInvasions.UMBAR.addInvasionMob(new InvasionSpawnEntry(LOTREntities.UMBAR_ARCHER.get(), 50));
		// TODO: Add Banner Bearers, Mercenary, Renegade
	}
}
