package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.conditions.ExtendedRemoveFromUnitDrops;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedModifierScrollSetter;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedSetWargFurType;

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
	ResourceLocation coinsBandit;
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
	ResourceLocation scroll;
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
		coins = makeLootCommonPart("coins", LootTable.lootTable().withPool(genSinglePoolItem(ExtendedItems.SILVER_COIN_ONE.get())
				.when(KilledByPlayer.killedByPlayer())//Requires player kill
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.125F, 0.125F))//Requires chance to spawn adding a looting modifier
				.when(ExtendedRemoveFromUnitDrops.setRemoveFromUnitDropsBuilder())//Requires no hired units
				.apply(SetCount.setCount(RandomValueRange.between(1, 7)))// Spawn between 1,7 of them
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(1.5F, 2.5F)))));// Apply a looting multiplier 
		coinsBandit = makeLootCommonPart("coins_bandit", LootTable.lootTable().withPool(genSinglePoolItemLooting(ExtendedItems.SILVER_COIN_ONE.get(), 12, 28)
				.when(KilledByPlayer.killedByPlayer())//Requires player kill
				.when(ExtendedRemoveFromUnitDrops.setRemoveFromUnitDropsBuilder())));//Requires no hired units
		scroll = makeLootCommonPart("scroll", LootTable.lootTable().withPool(genSinglePoolItem(ExtendedItems.MODIFIER_SCROLL.get())
				.when(KilledByPlayer.killedByPlayer())//Requires player kill
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.016666F, 0.00185F))//Requires chance to spawn adding a looting modifier
				.when(ExtendedRemoveFromUnitDrops.setRemoveFromUnitDropsBuilder())//Requires no hired units
				.apply(ExtendedModifierScrollSetter.random())));//Applies modifiers to scroll
		wargFur = makeLootCommonPart("warg_fur", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.FUR.get())
				.apply(SetCount.setCount(RandomValueRange.between(1, 3)))
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 0.5F)))
				.apply(ExtendedSetWargFurType.setWargFurTypeBuilder())));//Applies warg fur color based on warg type
		wargHead = makeLootCommonPart("warg_head", LootTable.lootTable().withPool(genSinglePoolItem(ExtendedItems.BROWN_WARG_HELMET.get())
				.when(KilledByPlayer.killedByPlayer())//Requires player kill
				.when(RandomChance.randomChance(0.02F))//Requires chance to spawn
				.apply(ExtendedSetWargFurType.setWargFurTypeBuilder())));//Applies warg fur color based on warg type
		orcBones = makeLootCommonPart("orc_bones", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.ORC_BONE.get())
				.apply(SetCount.setCount(RandomValueRange.between(0, 1)))
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 0.5F)))));
		manBones = makeLootCommonPart("man_bones", LootTable.lootTable().withPool(genSinglePoolItem(Items.BONE))
				.apply(SetCount.setCount(RandomValueRange.between(0, 1)))
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 0.5F))));
		elfBones = makeLootCommonPart("elf_bones", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.ELF_BONE.get())
				.apply(SetCount.setCount(RandomValueRange.between(0, 1)))
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 0.5F)))));
		hobbitBones = makeLootCommonPart("hobbit_bones", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.HOBBIT_BONE.get())
				.apply(SetCount.setCount(RandomValueRange.between(0, 1)))
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 0.5F)))));
		dwarfBones = makeLootCommonPart("dwarf_bones", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.DWARF_BONE.get())
				.apply(SetCount.setCount(RandomValueRange.between(0, 1)))
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 0.5F)))));
		wargBones = makeLootCommonPart("warg_bones", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.WARG_BONE.get())
				.apply(SetCount.setCount(RandomValueRange.between(2, 3)))
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 0.5F)))));
		arrows = makeLootCommonPart("arrows", LootTable.lootTable().withPool(genSinglePoolItemLooting(Items.ARROW, 1, 2)));
		lembas = makeLootCommonPart("elf_lembas", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.LEMBAS.get())));
		shireHeather = makeLootCommonPart("shire_heather", LootTable.lootTable().withPool(genSinglePoolItem(LOTRItems.SHIRE_HEATHER.get())
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.10F))));
		leatherHat = makeLootCommonPart("leather_hat", LootTable.lootTable().withPool(genSinglePoolItem(ExtendedItems.LEATHER_HAT.get())
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.10F))));
		rareDwarfDrops = makeLootCommonPart("dwarf_rare_drops", LootTable.lootTable().withPool(genSinglePoolItem(Items.IRON_INGOT)
				.add(genSingleItem(LOTRItems.DWARVEN_STEEL_INGOT.get()))
				.add(genSingleItemLooting(Items.GOLD_NUGGET, 1, 3))
				.add(genSingleItemLooting(LOTRItems.SILVER_NUGGET.get(), 1, 3))));
		pouch = makeLootCommonPart("pouch", LootTable.lootTable().withPool(makePool(1)
				.add(genSingleItem(LOTRItems.SMALL_POUCH.get()).setWeight(6))
				.add(genSingleItem(LOTRItems.MEDIUM_POUCH.get()).setWeight(3))
				.add(genSingleItem(LOTRItems.LARGE_POUCH.get()).setWeight(1))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.016666F, 0.00185F))//Requires chance to spawn and looting modifier
				.apply(SetPouchColorFromEntityFaction.setPouchColorFromEntityFactionBuilder(0.5F))));
		rareDunedainRangerDrops = makeLootCommonPart("dunedain_ranger_rare_drops", LootTable.lootTable().withPool(makePool(1)
				.add(genSingleItem(ExtendedItems.BARROW_BLADE.get()).apply(SetDamage.setDamage(RandomValueRange.between(0.5F, 0.75F))).setWeight(5))
				.add(genSingleItem(ExtendedItems.DUNEDAIN_BOW.get()).apply(SetDamage.setDamage(RandomValueRange.between(0.5F, 0.75F))).setWeight(5))
				.add(genSingleItem(ExtendedItems.RANGER_BOW.get()).apply(SetDamage.setDamage(RandomValueRange.between(0.5F, 0.75F))).setWeight(5))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.05F))));
		coinsRare = makeLootCommonPart("coins_rare", LootTable.lootTable().withPool(makePool(1)
				.add(genSingleItemLooting(ExtendedItems.SILVER_COIN_ONE.get(), 1, 4).setWeight(5))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.12F, 0.025F))
				.when(ExtendedRemoveFromUnitDrops.setRemoveFromUnitDropsBuilder())));
		mugs = makeLootCommonPart("mugs", LootTable.lootTable().withPool(makePool(1)
				.add(genSingleItemLooting(LOTRItems.WOODEN_MUG.get(), 1, 1).setWeight(5))
				.add(genSingleItemLooting(LOTRItems.CERAMIC_MUG.get(), 1, 1).setWeight(5))
				.add(genSingleItemLooting(LOTRItems.CLAY_MUG.get(), 1, 1).setWeight(5))
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.5F, 1F))));
		foodItems = makeLootCommonPart("food_items", LootTable.lootTable().withPool(makePool(1)
				.add(genSingleItemLooting(Items.COOKED_BEEF, 1, 1).setWeight(5))
				.add(genSingleItemLooting(Items.COOKED_CHICKEN, 1, 1).setWeight(5))
				.add(genSingleItemLooting(Items.COOKED_COD, 1, 1).setWeight(5))
				.add(genSingleItemLooting(Items.COOKED_RABBIT, 1, 1).setWeight(5))
				.add(genSingleItemLooting(Items.COOKED_MUTTON, 1, 1).setWeight(5))
				.add(genSingleItemLooting(Items.COOKED_PORKCHOP, 1, 1).setWeight(5))
				.add(genSingleItemLooting(Items.APPLE, 1, 1).setWeight(5))
				.add(genSingleItemLooting(LOTRItems.GREEN_APPLE.get(), 1, 1).setWeight(5))
				.add(genSingleItemLooting(LOTRItems.PEAR.get(), 1, 1).setWeight(5))
				.add(genSingleItemLooting(LOTRItems.LETTUCE.get(), 1, 1).setWeight(5))
				.add(genSingleItemLooting(ExtendedItems.PLUM.get(), 1, 1).setWeight(5))
				.add(genSingleItemLooting(ExtendedItems.COOKED_DUCK.get(), 1, 1).setWeight(5))
				.add(genSingleItemLooting(ExtendedItems.ROASTED_CHESTNUT.get(), 1, 1).setWeight(5))
				.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.5F, 1F))));
		gundabadUrukEquipment = makeLootCommonPart("gundabad_uruk_equipment", LootTable.lootTable().withPool(makePool(1)
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_SHIELD.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_URUK_DAGGER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_URUK_SPEAR.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.06F))));
		gundabadUrukArcherEquipment = makeLootCommonPart("gundabad_uruk_archer_equipment", LootTable.lootTable().withPool(makePool(1)
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_SHIELD.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_URUK_BOW.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_URUK_DAGGER.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(4))
				.add(genSingleItemLooting(ExtendedItems.GUNDABAD_URUK_SPEAR.get(), 1, 1).apply(SetDamage.setDamage(RandomValueRange.between(0.1F, 0.75F))).setWeight(5))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.06F))));
	}

	private void makeRaceBaseTables() {
		orcBase = makeLootCommonPart("orc", LootTable.lootTable()
				.withPool(addPoolRefrence(orcBones))
				.withPool(addPoolRefrence(pouch))
				.withPool(addPoolRefrence(coins))
				.withPool(addPoolRefrence(scroll))
				.withPool(genSinglePoolItemLooting(Items.ROTTEN_FLESH, 0, 2))
				.withPool(genSinglePoolItemLooting(LOTRItems.MAGGOTY_BREAD.get(), 1, 2)
						.when(RandomChance.randomChance(0.1F)))
				.withPool(createDrinkBuilder(LOTRItems.ORC_DRAUGHT.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		elfBase = makeLootCommonPart("elf", LootTable.lootTable()
				.withPool(addPoolRefrence(elfBones))
				.withPool(addPoolRefrence(pouch))
				.withPool(addPoolRefrence(coins))
				.withPool(addPoolRefrence(scroll))
				.withPool(addPoolRefrence(arrows)));
		hobbitBase = makeLootCommonPart("hobbit", LootTable.lootTable()
				.withPool(addPoolRefrence(hobbitBones))
				.withPool(addPoolRefrence(coins))
				.withPool(addPoolRefrence(scroll))
				.withPool(addPoolRefrence(pouch)));
		wargBase = makeLootCommonPart("warg", LootTable.lootTable()
				.withPool(addPoolRefrence(wargBones))
				.withPool(addPoolRefrence(wargFur))
				.withPool(addPoolRefrence(wargHead)));
		manBase = makeLootCommonPart("man", LootTable.lootTable()
				.withPool(addPoolRefrence(manBones))
				.withPool(addPoolRefrence(pouch))
				.withPool(addPoolRefrence(coins))
				.withPool(addPoolRefrence(scroll)));
		dwarfBase = makeLootCommonPart("dwarf", LootTable.lootTable()
				.withPool(addPoolRefrence(dwarfBones))
				.withPool(addPoolRefrence(pouch))
				.withPool(addPoolRefrence(coins))
				.withPool(addPoolRefrence(scroll))
				.withPool(addPoolRefrence(rareDwarfDrops)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.023F)))
				.withPool(genSinglePoolItem(LOTRItems.BOOK_OF_TRUE_SILVER.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.005F))));
	}

	private void makeExtendedRaceTables() {
		orcBaseSteel = makeLootCommonPart("orc_with_orc_steel", LootTable.lootTable()
				.withPool(addPoolRefrence(orcBase))
				.withPool(genSinglePoolItemLooting(LOTRItems.ORC_STEEL_INGOT.get(), 1, 2)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		orcBaseUrukSteel = makeLootCommonPart("orc_with_uruk_steel", LootTable.lootTable()
				.withPool(addPoolRefrence(orcBase))
				.withPool(genSinglePoolItemLooting(LOTRItems.URUK_STEEL_INGOT.get(), 1, 2)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		elfBaseMiruvor = makeLootCommonPart("elf_with_miruvor", LootTable.lootTable()
				.withPool(addPoolRefrence(elfBase))
				.withPool(createDrinkBuilder(LOTRItems.MIRUVOR.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F))));
		elfBaseMiruvorLembas = makeLootCommonPart("elf_with_miruvor_lembas", LootTable.lootTable()
				.withPool(addPoolRefrence(elfBase))
				.withPool(createDrinkBuilder(LOTRItems.MIRUVOR.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F)))
				.withPool(genSinglePoolItem(LOTRItems.LEMBAS.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F)))
				);
		manBreeLeatherIronSword = makeLootCommonPart("man_bree_leather_iron_sword", LootTable.lootTable()
				.withPool(addPoolRefrence(manBase))
				.withPool(genSinglePoolItemLooting(Items.LEATHER_CHESTPLATE, 1, 1)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F)))
				.withPool(genSinglePoolItemLooting(Items.LEATHER_LEGGINGS, 1, 1)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F)))
				.withPool(genSinglePoolItemLooting(Items.IRON_SWORD, 1, 1)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F)))
				);

	}

	private void makeFactionTables() {
		hobbit = makeLootCommonFaction("hobbit", LootTable.lootTable().withPool(addPoolRefrence(hobbitBase)));
		hobbitBree = makeLootCommonFaction("bree_hobbit", LootTable.lootTable().withPool(addPoolRefrence(hobbitBase)));
		manBree = makeLootCommonFaction("bree_man", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		manDale = makeLootCommonFaction("dale", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		manDunlending = makeLootCommonFaction("dunlending", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		manRohan = makeLootCommonFaction("rohan", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		manGondor = makeLootCommonFaction("gondor", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		manHarnedhrim = makeLootCommonFaction("harnedhrim", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		manCoastSouthron = makeLootCommonFaction("coast_southron", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		manUmbar = makeLootCommonFaction("umbar", LootTable.lootTable().withPool(addPoolRefrence(manBase)));
		dwarfBlueMountians = makeLootCommonFaction("blue_mountians", LootTable.lootTable().withPool(addPoolRefrence(dwarfBase)));
		dwarfDurinsFolk = makeLootCommonFaction("durins_folk", LootTable.lootTable().withPool(addPoolRefrence(dwarfBase)));
		elfLindon = makeLootCommonFaction("lindon", LootTable.lootTable().withPool(addPoolRefrence(elfBase)));
		elfRivendell = makeLootCommonFaction("rivendell", LootTable.lootTable().withPool(addPoolRefrence(elfBase)));
		elfWoodElf = makeLootCommonFaction("wood_elf", LootTable.lootTable().withPool(addPoolRefrence(elfBase)));
		elfGaladhrim = makeLootCommonFaction("galadhrim", LootTable.lootTable().withPool(addPoolRefrence(elfBaseMiruvor)));
		orcGundabad = makeLootCommonFaction("gundabad", LootTable.lootTable().withPool(addPoolRefrence(orcBaseSteel)));
		orcUruk = makeLootCommonFaction("uruk", LootTable.lootTable().withPool(addPoolRefrence(orcBaseUrukSteel)));
		orcMordor = makeLootCommonFaction("mordor", LootTable.lootTable().withPool(addPoolRefrence(orcBaseSteel)));
		orcIsengard = makeLootCommonFaction("isengard_snaga", LootTable.lootTable().withPool(addPoolRefrence(orcBaseSteel)));
		orcGundabadUruk = makeLootCommonFaction("gundabad_uruk", LootTable.lootTable().withPool(addPoolRefrence(orcBaseUrukSteel)));
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
		makeEntity(ExtendedEntities.HOBBIT_FARMHAND, hobbit);
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
		makeEntity(ExtendedEntities.BANDIT, coinsBandit, manBones, pouch);
		makeEntity(ExtendedEntities.BLUE_DWARVEN_AXE_THROWER, dwarfBlueMountians);
		makeEntity(ExtendedEntities.GUNDABAD_CAMP_TRADER, orcGundabad);
		makeEntity(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN, orcGundabad);
		makeEntity(ExtendedEntities.DUCK, makeLootCommon("duck", LootTable.lootTable()
				.withPool(makePool(1).add(genSingleItemLooting(Items.FEATHER, 0.0F, 2.0F)))
				.withPool(makePool(1)
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
						.add(EItemLootEntry.lootItem(ExtendedItems.ARNOR_SHIELD.get()).setWeight(20).setDamageRange(0.35F, 0.8F).setExactly(1))
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
		makeEntity(ExtendedEntities.BREE_FARMHAND, manBree);
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
						.add(EItemLootEntry.lootItem(Items.FEATHER).apply(SetCount.setCount(RandomValueRange.between(1, 2))))));
		add(ExtendedEntities.HORSE_BOAR.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(EItemLootEntry.lootItem(Items.PORKCHOP)
								.apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
								.apply(SetCount.setCount(RandomValueRange.between(1, 3))))));
		makeEntity(ExtendedEntities.RIVENDELL_CAPTAIN, elfRivendell);
		makeEntity(ExtendedEntities.RIVENDELL_SMITH, elfRivendell);
		makeEntity(ExtendedEntities.RIVENDELL_WEAVER, elfRivendell);
		makeEntity(ExtendedEntities.IMLADRIS_GUARD, elfRivendell);
		add(ExtendedEntities.ELROND.get(), LootTable.lootTable()
				.withPool(addPoolRefrence(elfRivendell))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(EItemLootEntry.lootItem(LOTRItems.ELVEN_STEEL_INGOT.get())
								.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.01F, 0.0F))
								.apply(SetCount.setCount(RandomValueRange.between(1, 2)))))
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.add(EItemLootEntry.lootItem(ExtendedItems.SAPPHIRE.get())
								.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.05F, 0.016F))
								.apply(SetCount.setCount(ConstantRange.exactly(1))))));
		makeEntity(ExtendedEntities.LINDON_LORD, elfLindon);
		makeEntity(ExtendedEntities.LINDON_SMITH, elfLindon);
		makeEntity(ExtendedEntities.LINDON_WEAVER, elfLindon);
		makeEntity(ExtendedEntities.LINDON_FLORIST, elfLindon);
		makeEntity(ExtendedEntities.URUK_BERSERKER, orcUruk);
		makeEntity(ExtendedEntities.URUK_CAPTAIN, orcUruk);
		makeEntity(ExtendedEntities.URUK_SMITH, orcUruk);
		makeEntity(ExtendedEntities.URUK_SUPPLIER, orcUruk);
		makeEntity(ExtendedEntities.DUNLENDING_WARLORD, manDunlending);
		makeEntity(ExtendedEntities.DUNLENDING_AXE_THROWER, manDunlending);
		makeEntity(ExtendedEntities.DUNLENDING_BERSERKER, manDunlending, arrows);
		makeEntity(ExtendedEntities.DUNLENDING_BARTENDER, manDunlending);
		makeEntity(ExtendedEntities.DUNLENDING_SMITH, manDunlending);
	}

	public ResourceLocation makeLootCommon(String name, LootTable.Builder builder) {
		ResourceLocation loc = new ResourceLocation(LotrExtendedMod.MODID, "entities/common/" + name);
		add(loc, builder);
		return loc;
	}

	public ResourceLocation makeLootCommonPart(String name, LootTable.Builder builder) {
		ResourceLocation loc = new ResourceLocation(LotrExtendedMod.MODID, "entities/common/parts/" + name);
		add(loc, builder);
		return loc;
	}

	public ResourceLocation makeLootCommonFaction(String name, LootTable.Builder builder) {
		ResourceLocation loc = new ResourceLocation(LotrExtendedMod.MODID, "entities/common/faction_bases/" + name);
		add(loc, builder);
		return loc;
	}

	public void makeEntity(Supplier<? extends EntityType<?>> entityType, ResourceLocation... pools) {
		LootTable.Builder table = LootTable.lootTable();
		for(ResourceLocation loc : pools) {
			table.withPool(addPoolRefrence(loc));
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

	public LootPool.Builder makePool(int numRoles){
		return LootPool.lootPool().setRolls(ConstantRange.exactly(numRoles));
	}

	public LootPool.Builder genSinglePoolItem(IItemProvider item){
		return makePool(1).add(genSingleItem(item));
	}

	public ItemLootEntry.Builder<?> genSingleItem(IItemProvider item){
		return ItemLootEntry.lootTableItem(item);
	}

	public LootPool.Builder addPoolRefrence(ResourceLocation quickyLocation) {
		return makePool(1).add(TableLootEntry.lootTableReference(quickyLocation));
	}

	public LootPool.Builder genSinglePoolItemLooting(IItemProvider item, float minCount, float maxCount){
		return makePool(1).add(genSingleItemLooting(item, minCount, maxCount));
	}

	public ItemLootEntry.Builder<?> genSingleItemLooting(IItemProvider item, float minCount, float maxCount){
		return genItemBuilder(item, minCount, maxCount, 0.0F, 1.0F);
	}

	public ItemLootEntry.Builder<?> genItemBuilder(IItemProvider item, float minCount, float maxCount, float minloot, float maxloot){
		return genSingleItem(item)
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(minloot, maxloot)))
				.apply(SetCount.setCount(RandomValueRange.between(minCount, maxCount)));
	}

	public LootPool.Builder createDrinkBuilder(IItemProvider item) {
		if ((item.asItem() instanceof VesselDrinkItem) && ((VesselDrinkItem) item.asItem()).hasPotencies) {
			return makePool(1).add(genSingleItem(item).apply(SetNPCDrinkPotency.setNPCDrinkPotencyBuilder()));
		}
		throw new IllegalArgumentException("INCORRECT SYNTAX WITH DRINK ITEM: "+item.asItem().getRegistryName());
	}
}