package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import lotr.common.loot.functions.ExtendedSetWargFurType;
import lotr.common.loot.functions.SetNPCDrinkPotency;
import lotr.common.loot.functions.SetPouchColorFromEntityFaction;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedEntityLootGenerator extends EntityLootTables  {
	//Parts
	ResourceLocation coins;
	ResourceLocation wargFur;
	ResourceLocation orcBones;
	ResourceLocation manBones;
	ResourceLocation elfBones;
	ResourceLocation hobbitBones;
	ResourceLocation dwarfBones;
	ResourceLocation wargBones;
	ResourceLocation arrows;
	ResourceLocation lembas;
	ResourceLocation rareDwarfDrops;
	ResourceLocation pouch;
	//Bases
	ResourceLocation orcBase;
	ResourceLocation elfBase;
	ResourceLocation hobbitBase;
	ResourceLocation wargBase;
	ResourceLocation manBase;
	ResourceLocation dwarfBase;
	//Extended Bases
	ResourceLocation orcBaseSteel;
	ResourceLocation orcBaseUrukSteel;
	ResourceLocation elfBaseMiruvor;
	//Faction tables
	ResourceLocation hobbit;
	ResourceLocation hobbitBree;
	ResourceLocation manBree;
	ResourceLocation manDale;
	ResourceLocation manDunlending;
	ResourceLocation manRohan;
	ResourceLocation manGondor;
	ResourceLocation manHarnedhrim;
	ResourceLocation manCoastSouthron;
	ResourceLocation manUmbar;
	ResourceLocation dwarfBlueMountians;
	ResourceLocation dwarfDurinsFolk;
	ResourceLocation elfLindon;
	ResourceLocation elfRivendell;
	ResourceLocation elfWoodElf;
	ResourceLocation elfGaladhrim;
	ResourceLocation orcGundabad;
	ResourceLocation orcUruk;
	ResourceLocation orcMordor;
	ResourceLocation orcIsengard;
	
	@Override
	protected void addTables() {
		makePartTables();
		makeBaseTables();
		makeExtendedTables();
		makeFactionTables();
		makeEntities();
	}

	private void makePartTables() {
		coins = makeQuickyBasePart("coins", LootTable.lootTable().withPool(genSingleQuickyBuilder(ExtendedItems.SILVER_COIN_ONE.get(), 0, 3)));
		wargFur = makeQuickyBasePart("warg_fur", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.FUR.get(), 1, 3)
				.apply(ExtendedSetWargFurType.setWargFurTypeBuilder())));
		orcBones = makeQuickyBasePart("orc_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.ORC_BONE.get(), 0, 1)));
		manBones = makeQuickyBasePart("man_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.BONE, 0, 1)));
		elfBones = makeQuickyBasePart("elf_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.ELF_BONE.get(), 0, 1)));
		hobbitBones = makeQuickyBasePart("hobbit_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.HOBBIT_BONE.get(), 0, 1)));
		dwarfBones = makeQuickyBasePart("dwarf_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.DWARF_BONE.get(), 0, 1)));
		wargBones = makeQuickyBasePart("warg_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.WARG_BONE.get(), 0, 3)));
		arrows = makeQuickyBasePart("arrows", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.ARROW, 1, 2)));
		lembas = makeQuickyBasePart("elf_lembas", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.LEMBAS.get())));
		rareDwarfDrops = makeQuickyBasePart("dwarf_rare_drops", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.IRON_INGOT)
				.add(genQuickyItemBuilder(LOTRItems.DWARVEN_STEEL_INGOT.get()))
				.add(genQuickyItemBuilder(Items.GOLD_NUGGET, 1, 3))
				.add(genQuickyItemBuilder(LOTRItems.SILVER_NUGGET.get(), 1, 3))));
		pouch = makeQuickyBasePart("pouch", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
				.add(genQuickyItemBuilder(LOTRItems.SMALL_POUCH.get()).setWeight(6))
				.add(genQuickyItemBuilder(LOTRItems.MEDIUM_POUCH.get()).setWeight(3))
				.add(genQuickyItemBuilder(LOTRItems.LARGE_POUCH.get()).setWeight(1))
				.apply(SetPouchColorFromEntityFaction.setPouchColorFromEntityFactionBuilder(0.5F))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.016666F))));
	}

	private void makeBaseTables() {
		orcBase = makeQuickyBasePart("orc", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(orcBones))
				.withPool(addAnotherQuickyPoolRefrence(pouch))
				.withPool(addAnotherQuickyPoolRefrence(coins))
				.withPool(genSingleQuickyBuilder(Items.ROTTEN_FLESH, 0, 2))
				.withPool(genSingleQuickyBuilder(LOTRItems.MAGGOTY_BREAD.get(), 1, 2)
						.when(RandomChance.randomChance(0.1F)))
				.withPool(createQuickyDrinkBuilder(LOTRItems.ORC_DRAUGHT.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		elfBase = makeQuickyBasePart("elf", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(elfBones))
				.withPool(addAnotherQuickyPoolRefrence(pouch))
				.withPool(addAnotherQuickyPoolRefrence(coins))
				.withPool(addAnotherQuickyPoolRefrence(arrows)));
		hobbitBase = makeQuickyBasePart("hobbit", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(hobbitBones))
				.withPool(addAnotherQuickyPoolRefrence(pouch)));
		wargBase = makeQuickyBasePart("warg", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(wargBones))
				.withPool(addAnotherQuickyPoolRefrence(wargFur)));
		manBase = makeQuickyBasePart("man", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(manBones))
				.withPool(addAnotherQuickyPoolRefrence(pouch))
				.withPool(addAnotherQuickyPoolRefrence(coins)));
		dwarfBase = makeQuickyBasePart("dwarf", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(dwarfBones))
				.withPool(addAnotherQuickyPoolRefrence(pouch))
				.withPool(addAnotherQuickyPoolRefrence(coins))
				.withPool(addAnotherQuickyPoolRefrence(rareDwarfDrops)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F)))
				.withPool(genSingleQuickyBuilder(LOTRItems.BOOK_OF_TRUE_SILVER.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.005F))));
	}

	private void makeExtendedTables() {
		orcBaseSteel = makeQuickyBasePart("orc_with_orc_steel", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(orcBase))
				.withPool(genSingleQuickyBuilder(LOTRItems.ORC_STEEL_INGOT.get(), 1, 2)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		orcBaseUrukSteel = makeQuickyBasePart("orc_with_uruk_steel", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(orcBase))
				.withPool(genSingleQuickyBuilder(LOTRItems.URUK_STEEL_INGOT.get(), 1, 2)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		elfBaseMiruvor = makeQuickyBasePart("elf_with_miruvor", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(elfBase))
				.withPool(createQuickyDrinkBuilder(LOTRItems.MIRUVOR.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F))));
	}

	private void makeFactionTables() {
		hobbit = makeQuickyFactionPart("hobbit", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(hobbitBase)));
		hobbitBree = makeQuickyFactionPart("bree_hobbit", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(hobbitBase)));
		manBree = makeQuickyFactionPart("bree_man", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		manDale = makeQuickyFactionPart("dale", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		manDunlending = makeQuickyFactionPart("dunlending", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		manRohan = makeQuickyFactionPart("rohan", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		manGondor = makeQuickyFactionPart("gondor", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		manHarnedhrim = makeQuickyFactionPart("harnedhrim", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		manCoastSouthron = makeQuickyFactionPart("coast_southron", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		manUmbar = makeQuickyFactionPart("umbar", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(manBase)));
		dwarfBlueMountians = makeQuickyFactionPart("blue_mountians", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(dwarfBase)));
		dwarfDurinsFolk = makeQuickyFactionPart("durins_folk", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(dwarfBase)));
		elfLindon = makeQuickyFactionPart("lindon", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(elfBase)));
		elfRivendell = makeQuickyFactionPart("rivendell", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(elfBase)));
		elfWoodElf = makeQuickyFactionPart("wood_elf", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(elfBase)));
		elfGaladhrim = makeQuickyFactionPart("galadhrim", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(elfBaseMiruvor)));
		orcGundabad = makeQuickyFactionPart("gundabad", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(orcBaseSteel)));
		orcUruk = makeQuickyFactionPart("uruk", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(orcBaseUrukSteel)));
		orcMordor = makeQuickyFactionPart("mordor", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(orcBaseSteel)));
		orcIsengard = makeQuickyFactionPart("isengard_snaga", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(orcBaseSteel)));
	}

	private void makeEntities() {
		makeEntity(LOTREntities.HOBBIT, hobbit);
		makeEntity(LOTREntities.MORDOR_ORC, orcMordor);
		makeEntity(LOTREntities.GONDOR_MAN, manGondor);
		makeEntity(LOTREntities.GALADHRIM_ELF, elfGaladhrim);
		makeEntity(LOTREntities.GONDOR_SOLDIER, manGondor);
		makeEntity(LOTREntities.DWARF, dwarfDurinsFolk);
		makeEntity(LOTREntities.DWARF_WARRIOR, dwarfDurinsFolk);
		makeEntity(LOTREntities.GALADHRIM_WARRIOR, elfGaladhrim);
		makeEntity(LOTREntities.URUK, orcUruk);
		makeEntity(LOTREntities.ROHAN_MAN, manRohan);
		makeEntity(LOTREntities.ROHIRRIM_WARRIOR, manRohan);
		makeEntity(LOTREntities.GUNDABAD_ORC, orcGundabad);
		makeEntity(LOTREntities.DALE_MAN, manDale);
		makeEntity(LOTREntities.DALE_SOLDIER, manDale);
		makeEntity(LOTREntities.DUNLENDING, manDunlending);
		makeEntity(LOTREntities.DUNLENDING_WARRIOR, manDunlending);
		makeEntity(LOTREntities.LINDON_ELF, elfLindon);
		makeEntity(LOTREntities.LINDON_WARRIOR, elfLindon);
		makeEntity(LOTREntities.RIVENDELL_ELF, elfRivendell);
		makeEntity(LOTREntities.RIVENDELL_WARRIOR, elfRivendell);
		makeEntity(LOTREntities.COAST_SOUTHRON, manCoastSouthron);
		makeEntity(LOTREntities.COAST_SOUTHRON_WARRIOR, manCoastSouthron);
		makeEntity(LOTREntities.HARNEDHRIM, manHarnedhrim);
		makeEntity(LOTREntities.HARNENNOR_WARRIOR, manHarnedhrim);
		makeEntity(LOTREntities.BLUE_MOUNTAINS_DWARF, dwarfBlueMountians);
		makeEntity(LOTREntities.BLUE_MOUNTAINS_WARRIOR, dwarfBlueMountians);
		makeEntity(LOTREntities.UMBAR_MAN, manUmbar);
		makeEntity(LOTREntities.UMBAR_SOLDIER, manUmbar);
		makeEntity(LOTREntities.GONDOR_ARCHER, manGondor, arrows);
		makeEntity(LOTREntities.ROHIRRIM_BOWMAN, manRohan, arrows);
		makeEntity(LOTREntities.DALE_BOWMAN, manDale, arrows);
		makeEntity(LOTREntities.DUNLENDING_BOWMAN, manDunlending, arrows);
		makeEntity(LOTREntities.COAST_SOUTHRON_ARCHER, manCoastSouthron, arrows);
		makeEntity(LOTREntities.HARNENNOR_ARCHER, manHarnedhrim, arrows);
		makeEntity(LOTREntities.UMBAR_ARCHER, manUmbar, arrows);
		makeEntity(LOTREntities.MORDOR_ORC_ARCHER, orcMordor, arrows);
		makeEntity(LOTREntities.GUNDABAD_ORC_ARCHER, orcGundabad, arrows);
		makeEntity(LOTREntities.URUK_ARCHER, orcUruk, arrows);
		makeEntity(LOTREntities.DWARF_ARCHER, dwarfDurinsFolk, arrows);
		makeEntity(LOTREntities.BLUE_MOUNTAINS_ARCHER, dwarfBlueMountians, arrows);
		makeEntity(LOTREntities.WOOD_ELF, elfWoodElf);
		makeEntity(LOTREntities.WOOD_ELF_WARRIOR, elfWoodElf);
		makeEntity(LOTREntities.BREE_MAN, manBree);
		makeEntity(LOTREntities.BREE_HOBBIT, hobbitBree);
		makeEntity(LOTREntities.BREE_GUARD, manBree);
		makeEntity(LOTREntities.GUNDABAD_WARG, wargBase);
		makeEntity(LOTREntities.ISENGARD_WARG, wargBase);
		makeEntity(LOTREntities.MORDOR_WARG, wargBase);
		makeEntity(LOTREntities.ISENGARD_SNAGA, orcIsengard);
		makeEntity(LOTREntities.ISENGARD_SNAGA_ARCHER, orcIsengard, arrows);
		add(LOTREntities.CARACAL.get(), LootTable.lootTable());
		makeEntity(ExtendedEntities.HOBBIT_BARTENDER, hobbit);
		makeEntity(ExtendedEntities.HOBBIT_FARMER, hobbit);
		makeEntity(ExtendedEntities.ODDMENT_COLLECTOR, manBase); // TODO: Update this to their own
		makeEntity(ExtendedEntities.DUNEDAIN_RANGER, manBase); // TODO: Update this to their own
	}

	public ResourceLocation makeQuickyBasePart(String name, Builder builder) {
		ResourceLocation loc = new ResourceLocation(LotrExtendedMod.MODID, "entities/common/parts/" + name);
		add(loc, builder);
		return loc;
	}
	public ResourceLocation makeQuickyFactionPart(String name, Builder builder) {
		ResourceLocation loc = new ResourceLocation(LotrExtendedMod.MODID, "entities/common/faction_bases/" + name);
		add(loc, builder);
		return loc;
	}

	public void makeEntity(Supplier<? extends EntityType<?>> entityType, ResourceLocation... pools) {
		Builder table = LootTable.lootTable();
		for(ResourceLocation loc : pools) {
			table.withPool(addAnotherQuickyPoolRefrence(loc));
		}
		add(entityType.get(), table);
	}

	@Override
	protected Iterable<EntityType<?>> getKnownEntities() {
		List<EntityType<?>> filtered = new ArrayList<>();
		for(RegistryObject<EntityType<?>> bl : LOTREntities.ENTITIES.getEntries()) {
			filtered.add(bl.get());
		}
        return filtered.stream()::iterator;
	}

	public LootPool.Builder makeQuickyPoolBuilder(int numRoles){
		return LootPool.lootPool().setRolls(ConstantRange.exactly(numRoles));
	}
	
	public LootPool.Builder genSingleQuickyBuilder(IItemProvider item, float minCount, float maxCount){
		return makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(item, minCount, maxCount));
	}

	public LootPool.Builder genSingleQuickyBuilder(IItemProvider item){
		return makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(item));
	}

	public ItemLootEntry.Builder<?> genQuickyItemBuilder(IItemProvider item){
		return ItemLootEntry.lootTableItem(item);
	}
	
	public ItemLootEntry.Builder<?> genQuickyItemBuilder(IItemProvider item, float minCount, float maxCount){
		return genQuickyItemBuilder(item, minCount, maxCount, 0.0F, 1.0F);
	}

	public LootPool.Builder addAnotherQuickyPoolRefrence(ResourceLocation quickyLocation) {
		return makeQuickyPoolBuilder(1).add(TableLootEntry.lootTableReference(quickyLocation));
	}
	
	public ItemLootEntry.Builder<?> genQuickyItemBuilder(IItemProvider item, float minCount, float maxCount, float minloot, float maxloot){
		return genQuickyItemBuilder(item)
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))
				.apply(SetCount.setCount(RandomValueRange.between(minCount, maxCount)));
	}
	
	public LootPool.Builder createQuickyDrinkBuilder(IItemProvider item) {
		if ((item.asItem() instanceof VesselDrinkItem) && ((VesselDrinkItem) item.asItem()).hasPotencies) {
			return makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(item).apply(SetNPCDrinkPotency.setNPCDrinkPotencyBuilder()));
		}
		throw new IllegalArgumentException("INCORRECT SYNTAX WITH DRINK ITEM: "+item.asItem().getRegistryName());
	}
}