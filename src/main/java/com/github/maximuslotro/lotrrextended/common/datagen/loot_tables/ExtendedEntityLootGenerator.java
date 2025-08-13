package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedSetWargFurType;
import com.github.maximuslotro.lotrrextended.common.loot.modifiers.ExtendedRemoveUnitCoinDrops;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import lotr.common.loot.functions.SetNPCDrinkPotency;
import lotr.common.loot.functions.SetPouchColorFromEntityFaction;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetDamage;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedEntityLootGenerator extends EntityLootTables  {
	//Parts
	ResourceLocation coins;
	ResourceLocation coinsRare;
	ResourceLocation wargFur;
	ResourceLocation wargHead;
	ResourceLocation orcBones;
	ResourceLocation manBones;
	ResourceLocation manBreeLeatherIronSword;
	ResourceLocation elfBones;
	ResourceLocation hobbitBones;
	ResourceLocation dwarfBones;
	ResourceLocation wargBones;
	ResourceLocation arrows;
	ResourceLocation shireHeather;
	ResourceLocation leatherHat;
	ResourceLocation lembas;
	ResourceLocation rareDwarfDrops;
	ResourceLocation pouch;
	ResourceLocation mugs;
	ResourceLocation foodItems;
	ResourceLocation gundabadUrukEquipment;
	ResourceLocation gundabadUrukArcherEquipment;
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
	ResourceLocation elfBaseMiruvorLembas;
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
	ResourceLocation rareDunedainRangerDrops;
	ResourceLocation orcGundabadUruk;

	@Override
	protected void addTables() {
		makePartTables();
		makeRaceBaseTables();
		makeExtendedRaceTables();
		makeFactionTables();
		makeEntities();
	}

	private void makePartTables() {
		coins = makeQuickyBasePart("coins", LootTable.lootTable().withPool(genSingleQuickyBuilder(ExtendedItems.SILVER_COIN_ONE.get(), 0, 3)
			.when(ExtendedRemoveUnitCoinDrops.setRemoveUnitCoinsDropBuilder())));
		wargFur = makeQuickyBasePart("warg_fur", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.FUR.get(), 1, 3)
			.apply(ExtendedSetWargFurType.setWargFurTypeBuilder())));
		wargHead = makeQuickyBasePart("warg_head", LootTable.lootTable().withPool(genSingleQuickyBuilder(ExtendedItems.BROWN_WARG_HELMET.get())
			.apply(ExtendedSetWargFurType.setWargFurTypeBuilder())
			.when(KilledByPlayer.killedByPlayer())
			.when(RandomChance.randomChance(0.02F))));
		orcBones = makeQuickyBasePart("orc_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.ORC_BONE.get(), 0, 1)));
		manBones = makeQuickyBasePart("man_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.BONE, 0, 1)));
		elfBones = makeQuickyBasePart("elf_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.ELF_BONE.get(), 0, 1)));
		hobbitBones = makeQuickyBasePart("hobbit_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.HOBBIT_BONE.get(), 0, 1)));
		dwarfBones = makeQuickyBasePart("dwarf_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.DWARF_BONE.get(), 0, 1)));
		wargBones = makeQuickyBasePart("warg_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.WARG_BONE.get(), 0, 3)));
		arrows = makeQuickyBasePart("arrows", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.ARROW, 1, 2)));
		lembas = makeQuickyBasePart("elf_lembas", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.LEMBAS.get())));
		shireHeather = makeQuickyBasePart("shire_heather", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.SHIRE_HEATHER.get())
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.10F))));
		leatherHat = makeQuickyBasePart("leather_hat", LootTable.lootTable().withPool(genSingleQuickyBuilder(ExtendedItems.LEATHER_HAT.get())
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.10F))));
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
		rareDunedainRangerDrops = makeQuickyBasePart("dunedain_ranger_rare_drops", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
				.add(genQuickyItemBuilder(ExtendedItems.BARROW_BLADE.get()).apply(SetDamage.setDamage(RandomValueRange.between(0.5F, 0.75F))).setWeight(5))
				.add(genQuickyItemBuilder(ExtendedItems.DUNEDAIN_BOW.get()).apply(SetDamage.setDamage(RandomValueRange.between(0.5F, 0.75F))).setWeight(5))
				.add(genQuickyItemBuilder(ExtendedItems.RANGER_BOW.get()).apply(SetDamage.setDamage(RandomValueRange.between(0.5F, 0.75F))).setWeight(5))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.05F))));
		coinsRare = makeQuickyBasePart("coins_rare", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
				.add(genQuickyItemBuilder(ExtendedItems.SILVER_COIN_ONE.get(), 1, 4).setWeight(5))
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.12F, 0.025F))
				.when(ExtendedRemoveUnitCoinDrops.setRemoveUnitCoinsDropBuilder())));
		mugs = makeQuickyBasePart("mugs", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
				.add(genQuickyItemBuilder(LOTRItems.WOODEN_MUG.get(), 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(LOTRItems.CERAMIC_MUG.get(), 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(LOTRItems.CLAY_MUG.get(), 1, 1).setWeight(5))
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.5F, 1F))));
		foodItems = makeQuickyBasePart("food_items", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
				.add(genQuickyItemBuilder(Items.COOKED_BEEF, 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(Items.COOKED_CHICKEN, 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(Items.COOKED_COD, 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(Items.COOKED_RABBIT, 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(Items.COOKED_MUTTON, 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(Items.COOKED_PORKCHOP, 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(Items.APPLE, 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(LOTRItems.GREEN_APPLE.get(), 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(LOTRItems.PEAR.get(), 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(LOTRItems.LETTUCE.get(), 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(ExtendedItems.PLUM.get(), 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(ExtendedItems.COOKED_DUCK.get(), 1, 1).setWeight(5))
				.add(genQuickyItemBuilder(ExtendedItems.ROASTED_CHESTNUT.get(), 1, 1).setWeight(5))
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.5F, 1F))));
		gundabadUrukEquipment = makeQuickyBasePart("gundabad_uruk_equipment", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_SHIELD.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_URUK_DAGGER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_URUK_SPEAR.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
			.when(KilledByPlayer.killedByPlayer())
			.when(RandomChance.randomChance(0.06F))));
		gundabadUrukArcherEquipment = makeQuickyBasePart("gundabad_uruk_archer_equipment", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_SHIELD.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_URUK_BOW.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_URUK_DAGGER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
			.add(genQuickyItemBuilder(ExtendedItems.GUNDABAD_URUK_SPEAR.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
			.when(KilledByPlayer.killedByPlayer())
			.when(RandomChance.randomChance(0.06F))));
	}

	private void makeRaceBaseTables() {
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
				.withPool(addAnotherQuickyPoolRefrence(wargFur))
				.withPool(addAnotherQuickyPoolRefrence(wargHead)));
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

	private void makeExtendedRaceTables() {
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
		elfBaseMiruvorLembas = makeQuickyBasePart("elf_with_miruvor_lembas", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(elfBase))
				.withPool(createQuickyDrinkBuilder(LOTRItems.MIRUVOR.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F)))
				.withPool(genSingleQuickyBuilder(LOTRItems.LEMBAS.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F)))
		);
		manBreeLeatherIronSword = makeQuickyBasePart("man_bree_leather_iron_sword", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(manBase))
				.withPool(genSingleQuickyBuilder(Items.LEATHER_CHESTPLATE, 1, 1)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F)))
				.withPool(genSingleQuickyBuilder(Items.LEATHER_LEGGINGS, 1, 1)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F)))
				.withPool(genSingleQuickyBuilder(Items.IRON_SWORD, 1, 1)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F)))
				);

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
		orcGundabadUruk = makeQuickyFactionPart("gundabad_uruk", LootTable.lootTable().withPool(addAnotherQuickyPoolRefrence(orcBaseUrukSteel)));
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
		makeEntity(ExtendedEntities.HOBBIT_BARTENDER, hobbit, coinsRare);
		makeEntity(ExtendedEntities.HOBBIT_FARMER, hobbit, coinsRare);
		makeEntity(ExtendedEntities.ODDMENT_COLLECTOR, manBase, shireHeather, leatherHat);
		makeEntity(ExtendedEntities.DUNEDAIN_RANGER, manBase, arrows, rareDunedainRangerDrops);
		makeEntity(ExtendedEntities.RIVENDELL_WANDERER, elfBaseMiruvorLembas);
		makeEntity(ExtendedEntities.GALADHRIM_WANDERER, elfBaseMiruvorLembas);
		makeEntity(ExtendedEntities.BREE_SHERIFF, manBreeLeatherIronSword);
		makeEntity(ExtendedEntities.DWARVEN_MINER, dwarfDurinsFolk, foodItems);
		makeEntity(ExtendedEntities.DWARVEN_SMITH, dwarfDurinsFolk, foodItems);
		makeEntity(ExtendedEntities.DWARVEN_COMMANDER, dwarfDurinsFolk, foodItems);
		makeEntity(ExtendedEntities.BLUE_MOUNTAINS_MERCHANT, dwarfBlueMountians, mugs, foodItems);
		makeEntity(ExtendedEntities.BLUE_DWARVEN_MINER, dwarfBlueMountians, foodItems);
		makeEntity(ExtendedEntities.BLUE_DWARVEN_SMITH, dwarfBlueMountians, foodItems);
		makeEntity(ExtendedEntities.BLUE_DWARVEN_BUTCHER, dwarfBlueMountians, foodItems);
		makeEntity(ExtendedEntities.BLUE_DWARVEN_STONEMASON, dwarfBlueMountians, foodItems);
		makeEntity(ExtendedEntities.BLUE_DWARVEN_BARTENDER, dwarfBlueMountians, foodItems);
		makeEntity(ExtendedEntities.BLUE_DWARVEN_COMMANDER, dwarfBlueMountians, foodItems);
		makeEntity(ExtendedEntities.GUNDABAD_CAMP_TRADER, orcGundabad);
		makeEntity(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN, orcGundabad);
		makeEntity(ExtendedEntities.DUCK, makeQuickyBaseCommon("duck", LootTable.lootTable()
	    		.withPool(makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(Items.FEATHER, 0.0F, 2.0F)))
	    		.withPool(makeQuickyPoolBuilder(1)
	    				.add(ItemLootEntry.lootTableItem(ExtendedItems.RAW_DUCK.get())
	    						.apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
	    						.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))));
		add(ExtendedEntities.HEDGEHOG.get(), LootTable.lootTable());
		makeEntity(ExtendedEntities.GUNDABAD_URUK, orcGundabadUruk, gundabadUrukEquipment);
		makeEntity(ExtendedEntities.GUNDABAD_URUK_ARCHER, orcGundabadUruk, gundabadUrukArcherEquipment, arrows);
		add(ExtendedEntities.BARROW_WIGHT.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.add(EItemLootEntry.lootItem(Items.BONE).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 4)))))
			.withPool(LootPool.lootPool()
				.setRolls(RandomValueRange.between(0, 1))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_ONE.get()).setWeight(200).apply(SetCount.setCount(RandomValueRange.between(1, 20))))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_TEN.get()).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 8))))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_HUNDRED.get()).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
				.add(EItemLootEntry.lootItem(Items.GOLD_NUGGET).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 5))))
				.add(EItemLootEntry.lootItem(Items.GOLD_INGOT).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_NUGGET.get()).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 5))))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_INGOT.get()).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_NUGGET.get()).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
				.add(EItemLootEntry.lootItem(Items.ARROW).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 8))))
				.add(EItemLootEntry.lootItem(Items.SKELETON_SKULL).setWeight(100).setExactly(1))
				.add(EItemLootEntry.lootItem(Items.BONE).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
				.add(EItemLootEntry.lootItem(Items.GLASS_BOTTLE).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
				.add(EItemLootEntry.lootItem(Items.BOOK).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_RING.get()).setWeight(20).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.GOLD_RING.get()).setWeight(20).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_RING.get()).setWeight(1).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.COPPER_GOBLET.get()).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_GOBLET.get()).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
				.add(EItemLootEntry.lootItem(LOTRItems.GOLDEN_GOBLET.get()).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
				.add(EItemLootEntry.lootItem(Items.IRON_HELMET).setWeight(10).setDamageRange(0.20F, 0.75F).setExactly(1))
				.add(EItemLootEntry.lootItem(Items.IRON_CHESTPLATE).setWeight(10).setDamageRange(0.20F, 0.75F).setExactly(1))
				.add(EItemLootEntry.lootItem(Items.IRON_LEGGINGS).setWeight(10).setDamageRange(0.20F, 0.75F).setExactly(1))
				.add(EItemLootEntry.lootItem(Items.IRON_BOOTS).setWeight(10).setDamageRange(0.20F, 0.75F).setExactly(1))
				.add(EItemLootEntry.lootItem(Items.IRON_SWORD).setWeight(25).setDamageRange(0.20F, 0.75F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.IRON_DAGGER.get()).setWeight(25).setDamageRange(0.20F, 0.75F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.IRON_SPEAR.get()).setWeight(25).setDamageRange(0.20F, 0.75F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_HELMET.get()).setWeight(10).setDamageRange(0.15F, 0.65F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_CHESTPLATE.get()).setWeight(10).setDamageRange(0.15F, 0.65F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_LEGGINGS.get()).setWeight(10).setDamageRange(0.15F, 0.65F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_BOOTS.get()).setWeight(10).setDamageRange(0.15F, 0.65F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_SWORD.get()).setWeight(25).setDamageRange(0.15F, 0.65F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_DAGGER.get()).setWeight(25).setDamageRange(0.15F, 0.65F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_SPEAR.get()).setWeight(25).setDamageRange(0.15F, 0.65F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ARNOR_CHESTPLATE.get()).setWeight(25).setDamageRange(0.35F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ARNOR_LEGGINGS.get()).setWeight(25).setDamageRange(0.35F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ARNOR_BOOTS.get()).setWeight(25).setDamageRange(0.35F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ARNOR_HELMET.get()).setWeight(25).setDamageRange(0.35F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ARNOR_SWORD.get()).setWeight(25).setDamageRange(0.35F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ARNOR_DAGGER.get()).setWeight(25).setDamageRange(0.35F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ARNOR_SPEAR.get()).setWeight(25).setDamageRange(0.35F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_CHESTPLATE.get()).setWeight(1).setDamageRange(0.95F, 0.99F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_LEGGINGS.get()).setWeight(1).setDamageRange(0.95F, 0.99F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_BOOTS.get()).setWeight(1).setDamageRange(0.95F, 0.99F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_HELMET.get()).setWeight(1).setDamageRange(0.95F, 0.99F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_SWORD.get()).setWeight(1).setDamageRange(0.95F, 0.99F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_DAGGER.get()).setWeight(1).setDamageRange(0.95F, 0.99F).setExactly(1))
				.add(EItemLootEntry.lootItem(ExtendedItems.BARROW_BLADE.get()).setWeight(20).setDamageRange(0.6F, 0.8F).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.SMALL_POUCH.get()).setWeight(5).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.MEDIUM_POUCH.get()).setWeight(1).setExactly(1))));
		makeEntity(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN, manBase, arrows, rareDunedainRangerDrops);
		makeEntity(ExtendedEntities.BREE_MERCHANT, manBree); // TODO: Maybe add some other drops here?
		makeEntity(ExtendedEntities.BREE_FARMER, manBree);
		makeEntity(ExtendedEntities.BREE_POTTER, manBree);
		makeEntity(ExtendedEntities.BREE_BAKER, manBree);
		makeEntity(ExtendedEntities.BREE_BUTCHER, manBree);
		makeEntity(ExtendedEntities.BREE_LUMBERMAN, manBree);
		makeEntity(ExtendedEntities.BREE_STONEMASON, manBree);
		makeEntity(ExtendedEntities.BREE_INNKEEPER, manBree);
		makeEntity(ExtendedEntities.BREE_FLORIST, manBree);
		makeEntity(ExtendedEntities.BREE_CHEESEMONGER, manBree);
		makeEntity(ExtendedEntities.BREE_LEATHERWORKER, manBree);
		makeEntity(ExtendedEntities.BREE_BREWER, manBree);
		makeEntity(ExtendedEntities.BREE_SMITH, manBree);
		makeEntity(ExtendedEntities.BREE_STABLEMASTER, manBree);
		makeEntity(ExtendedEntities.BREE_HOBBIT_POTTER, hobbitBree);
		makeEntity(ExtendedEntities.BREE_HOBBIT_BAKER, hobbitBree);
		makeEntity(ExtendedEntities.BREE_HOBBIT_BUTCHER, hobbitBree);
		makeEntity(ExtendedEntities.BREE_HOBBIT_INNKEEPER, hobbitBree);
		makeEntity(ExtendedEntities.BREE_HOBBIT_FLORIST, hobbitBree);
		makeEntity(ExtendedEntities.BREE_HOBBIT_BREWER, hobbitBree);
		add(ExtendedEntities.TOLKIEN.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool()
				.setRolls(RandomValueRange.between(0, 3))
				.add(EItemLootEntry.lootItem(Items.WRITABLE_BOOK).setWeight(6).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
				.add(EItemLootEntry.lootItem(Items.BOOK).setWeight(4).apply(SetCount.setCount(RandomValueRange.between(1, 2)))))
			.withPool(LootPool.lootPool()
				.setRolls(RandomValueRange.between(0, 1))
				.add(EItemLootEntry.lootItem(Items.FEATHER).setWeight(1).apply(SetCount.setCount(RandomValueRange.between(1, 2))))));
		add(ExtendedEntities.HORSE_BOAR.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
				.add(EItemLootEntry.lootItem(Items.PORKCHOP).setWeight(1)
					.apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
					.apply(SetCount.setCount(RandomValueRange.between(1, 3))))));
	}

	public ResourceLocation makeQuickyBaseCommon(String name, Builder builder) {
		ResourceLocation loc = new ResourceLocation(LotrExtendedMod.MODID, "entities/common/" + name);
		add(loc, builder);
		return loc;
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
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(minloot, maxloot)))
				.apply(SetCount.setCount(RandomValueRange.between(minCount, maxCount)));
	}

	public LootPool.Builder createQuickyDrinkBuilder(IItemProvider item) {
		if ((item.asItem() instanceof VesselDrinkItem) && ((VesselDrinkItem) item.asItem()).hasPotencies) {
			return makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(item).apply(SetNPCDrinkPotency.setNPCDrinkPotencyBuilder()));
		}
		throw new IllegalArgumentException("INCORRECT SYNTAX WITH DRINK ITEM: "+item.asItem().getRegistryName());
	}
}