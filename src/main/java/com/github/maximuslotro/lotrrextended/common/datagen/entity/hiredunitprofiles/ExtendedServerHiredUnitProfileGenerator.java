package com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitAttackType;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.entity.npc.util.LeatherDyeUtil;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ExtendedServerHiredUnitProfileGenerator extends ExtendedServerHiredUnitProfileProvider{

	public ExtendedServerHiredUnitProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedServerHiredUnitProfile> pConsumer) {
		// Bree Units
		make(LOTREntities.BREE_MAN.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(20, false, 75)
		.addHelmetPoolItem(ItemStack.EMPTY, 1)
		.addChestplatePoolItem(ItemStack.EMPTY, 2)
		.addLeggingPoolItem(ItemStack.EMPTY, 2)
		.addBootsPoolItem(ItemStack.EMPTY, 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 3)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_DAGGER.get()), 4)
		.addMeleePoolItem(new ItemStack(Items.IRON_SWORD), 1)
		.addMeleePoolItem(new ItemStack(Items.IRON_AXE), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.disableIdleWeaponUsingOtherPools().save(pConsumer, ExtendedHiredUnitTypes.BREE_MAN);

		make(LOTREntities.BREE_HOBBIT.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(20, false, 75)
		.addHelmetPoolItem(ItemStack.EMPTY, 1)
		.addChestplatePoolItem(ItemStack.EMPTY, 2)
		.addLeggingPoolItem(ItemStack.EMPTY, 2)
		.addBootsPoolItem(ItemStack.EMPTY, 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 3)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_DAGGER.get()), 4)
		.addShieldItem(ItemStack.EMPTY, 1)
		.disableIdleWeaponUsingOtherPools().save(pConsumer, ExtendedHiredUnitTypes.BREE_HOBBIT);

		make(LOTREntities.BREE_GUARD.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(30, false, 100)
		.addHelmetPoolItem(new ItemStack(Items.IRON_HELMET), 1)
		.addChestplatePoolItem(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 2)
		.addChestplatePoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, 11373426), 1)
		.addChestplatePoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, 7823440), 1)
		.addChestplatePoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, 5983041), 1)
		.addChestplatePoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, 9535090), 1)
		.addLeggingPoolItem(new ItemStack(Items.CHAINMAIL_LEGGINGS), 2)
		.addLeggingPoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_LEGGINGS, 11373426), 1)
		.addLeggingPoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_LEGGINGS, 7823440), 1)
		.addLeggingPoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_LEGGINGS, 5983041), 1)
		.addLeggingPoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_LEGGINGS, 9535090), 1)
		.addBootsPoolItem(new ItemStack(Items.CHAINMAIL_BOOTS), 1)
		.addBootsPoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_BOOTS, 7823440), 2)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(Items.IRON_SWORD), 1)
		.addShieldItem(new ItemStack(ExtendedItems.BREE_SHIELD.get()), 1)
		.setIdlePoolFromMeleePool().save(pConsumer, ExtendedHiredUnitTypes.BREE_GUARD);

		// Gundabad Units
		make(LOTREntities.GUNDABAD_ORC.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(25, false, 100)
		.addHelmetPoolItem(new ItemStack(LOTRItems.BONE_HELMET.get()), 1)
		.addHelmetPoolItem(new ItemStack(Items.LEATHER_HELMET), 1)
		.addHelmetPoolItem(new ItemStack(LOTRItems.BRONZE_HELMET.get()), 1)
		.addHelmetPoolItem(new ItemStack(ExtendedItems.ANGMAR_HELMET.get()), 1)
		.addHelmetPoolItem(new ItemStack(LOTRItems.FUR_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(Items.LEATHER_CHESTPLATE), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.BONE_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.BRONZE_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.MORDOR_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.FUR_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(ExtendedItems.ANGMAR_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(Items.LEATHER_LEGGINGS), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.MORDOR_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.FUR_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.BONE_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.BRONZE_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(ExtendedItems.ANGMAR_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(Items.LEATHER_BOOTS), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.FUR_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.BONE_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.BRONZE_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.MORDOR_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(ExtendedItems.ANGMAR_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(Items.IRON_SWORD), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BRONZE_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BRONZE_SWORD.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.STONE_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.STONE_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(Items.STONE_SWORD), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.MORDOR_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.MORDOR_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.ANGMAR_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.ANGMAR_SWORD.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.ANGMAR_DAGGER.get()), 1)
		.addShieldItem(new ItemStack(LOTRItems.MORDOR_SHIELD.get()), 3)
		.addShieldItem(new ItemStack(ExtendedItems.GUNDABAD_SHIELD.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 2)
		.setIdlePoolFromMeleePool().save(pConsumer, ExtendedHiredUnitTypes.GUNDABAD_ORC);

		make(LOTREntities.GUNDABAD_ORC_ARCHER.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(50, false, 150)
		.addHelmetPoolItem(new ItemStack(LOTRItems.BONE_HELMET.get()), 1)
		.addHelmetPoolItem(new ItemStack(Items.LEATHER_HELMET), 1)
		.addHelmetPoolItem(new ItemStack(LOTRItems.BRONZE_HELMET.get()), 1)
		.addHelmetPoolItem(new ItemStack(ExtendedItems.ANGMAR_HELMET.get()), 1)
		.addHelmetPoolItem(new ItemStack(LOTRItems.MORDOR_HELMET.get()), 1)
		.addHelmetPoolItem(new ItemStack(LOTRItems.FUR_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(Items.LEATHER_CHESTPLATE), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.BONE_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.BRONZE_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.MORDOR_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.FUR_CHESTPLATE.get()), 1)
		.addChestplatePoolItem(new ItemStack(ExtendedItems.ANGMAR_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(Items.LEATHER_LEGGINGS), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.MORDOR_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.FUR_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.BONE_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.BRONZE_LEGGINGS.get()), 1)
		.addLeggingPoolItem(new ItemStack(ExtendedItems.ANGMAR_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(Items.LEATHER_BOOTS), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.FUR_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.BONE_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.BRONZE_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.MORDOR_BOOTS.get()), 1)
		.addBootsPoolItem(new ItemStack(ExtendedItems.ANGMAR_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.STONE_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.MORDOR_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.ANGMAR_DAGGER.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.ORC_BOW.get()), 3)
		.addRangedPoolItem(new ItemStack(ExtendedItems.LONGBOW.get()), 1)
		.addRangedPoolItem(new ItemStack(Items.BOW), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromRangedPool().save(pConsumer, ExtendedHiredUnitTypes.GUNDABAD_ORC_ARCHER);

		make(ExtendedEntities.GUNDABAD_URUK.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(30, false, 350)
		.addHelmetPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(Items.IRON_SWORD), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.MORDOR_DAGGER.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.MORDOR_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.MORDOR_SCIMITAR.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_CLEAVER.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_DAGGER.get()), 1)
		.addShieldItem(new ItemStack(ExtendedItems.GUNDABAD_SHIELD.get()), 3)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromMeleePool().save(pConsumer, ExtendedHiredUnitTypes.GUNDABAD_URUK);

		make(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(50, false, 400)
		.addHelmetPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_DAGGER.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_BOW.get()), 2)
		.addRangedPoolItem(new ItemStack(ExtendedItems.ORC_BOW.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.LONGBOW.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromRangedPool().save(pConsumer, ExtendedHiredUnitTypes.GUNDABAD_URUK_ARCHER);

		// Dunedain Units
		make(ExtendedEntities.DUNEDAIN_RANGER.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(40, false, 100)
		.addHelmetPoolItem(new ItemStack(LOTRItems.RANGER_NORTH_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.RANGER_NORTH_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.RANGER_NORTH_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.RANGER_NORTH_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 4)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_DAGGER.get()), 2)
		.addMeleePoolItem(new ItemStack(ExtendedItems.BARROW_BLADE.get()), 2)
		.addMeleePoolItem(new ItemStack(Items.IRON_SWORD), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.IRON_SPEAR.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.DUNEDAIN_BOW.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.RANGER_BOW.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.LONGBOW.get()), 1)
		.addRangedPoolItem(new ItemStack(Items.BOW), 1)
		.addShieldItem(new ItemStack(ExtendedItems.DUNEDAIN_SHIELD.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 2)
		.setIdlePoolFromRangedPool().save(pConsumer, ExtendedHiredUnitTypes.DUNEDAIN_RANGER);

		// Blue Mountain Units
		make(LOTREntities.BLUE_MOUNTAINS_DWARF.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(30, false, 200)
		.addHelmetPoolItem(ItemStack.EMPTY, 1)
		.addChestplatePoolItem(ItemStack.EMPTY, 1)
		.addLeggingPoolItem(ItemStack.EMPTY, 1)
		.addBootsPoolItem(ItemStack.EMPTY, 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_DAGGER.get()), 7)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_SWORD.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_AXE.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.disableIdleWeaponUsingOtherPools().save(pConsumer, ExtendedHiredUnitTypes.BLUE_DWARF);

		make(LOTREntities.BLUE_MOUNTAINS_WARRIOR.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(40, false, 250)
		.addHelmetPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_SWORD.get()), 7)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_AXE.get()), 1)
		.addShieldItem(new ItemStack(ExtendedItems.BLUE_DWARVEN_SHIELD.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromMeleePool().save(pConsumer, ExtendedHiredUnitTypes.BLUE_DWARF_WARRIOR);

		make(LOTREntities.BLUE_MOUNTAINS_ARCHER.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(50, false, 300)
		.addHelmetPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_DAGGER.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.LONGBOW.get()), 1)
		.addRangedPoolItem(new ItemStack(Items.BOW), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromRangedPool().save(pConsumer, ExtendedHiredUnitTypes.BLUE_DWARF_ARCHER);

		make(ExtendedEntities.BLUE_DWARVEN_AXE_THROWER.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(75, false, 300)
		.addHelmetPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.BLUE_DWARVEN_DAGGER.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromRangedPool().save(pConsumer, ExtendedHiredUnitTypes.BLUE_DWARF_AXE_THROWER);

		// Dwarven Units
		make(LOTREntities.DWARF.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(30, false, 200)
		.addHelmetPoolItem(ItemStack.EMPTY, 1)
		.addChestplatePoolItem(ItemStack.EMPTY, 1)
		.addLeggingPoolItem(ItemStack.EMPTY, 1)
		.addBootsPoolItem(ItemStack.EMPTY, 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_DAGGER.get()), 7)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_SWORD.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_AXE.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.disableIdleWeaponUsingOtherPools().save(pConsumer, ExtendedHiredUnitTypes.DWARF);

		make(LOTREntities.DWARF_WARRIOR.get(), ExtendedUnitAttackType.MELEE).setPurchaseInfo(45, false, 250)
		.addHelmetPoolItem(new ItemStack(LOTRItems.DWARVEN_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.DWARVEN_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.DWARVEN_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.DWARVEN_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_SWORD.get()), 7)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_AXE.get()), 1)
		.addShieldItem(new ItemStack(Items.SHIELD), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromMeleePool().save(pConsumer, ExtendedHiredUnitTypes.DWARF_WARRIOR);

		make(LOTREntities.DWARF_ARCHER.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(50, false, 300)
		.addHelmetPoolItem(new ItemStack(LOTRItems.DWARVEN_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.DWARVEN_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.DWARVEN_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.DWARVEN_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.DWARVEN_DAGGER.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.LONGBOW.get()), 1)
		.addRangedPoolItem(new ItemStack(Items.BOW), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.setIdlePoolFromRangedPool().save(pConsumer, ExtendedHiredUnitTypes.DWARF_ARCHER);

		// Rivendell units
		make(LOTREntities.RIVENDELL_ELF.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(45, false, 300)
		.addHelmetPoolItem(ItemStack.EMPTY, 1)
		.addChestplatePoolItem(ItemStack.EMPTY, 1)
		.addLeggingPoolItem(ItemStack.EMPTY, 1)
		.addBootsPoolItem(ItemStack.EMPTY, 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_DAGGER.get()), 6)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_AXE.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_SHOVEL.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_PICKAXE.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_HOE.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 1)
		.disableIdleWeaponUsingOtherPools().save(pConsumer, ExtendedHiredUnitTypes.RIVENDELL_ELF);

		make(LOTREntities.RIVENDELL_WARRIOR.get(), ExtendedUnitAttackType.RANGED).setPurchaseInfo(75, false, 400)
		.addHelmetPoolItem(new ItemStack(LOTRItems.RIVENDELL_HELMET.get()), 1)
		.addChestplatePoolItem(new ItemStack(LOTRItems.RIVENDELL_CHESTPLATE.get()), 1)
		.addLeggingPoolItem(new ItemStack(LOTRItems.RIVENDELL_LEGGINGS.get()), 1)
		.addBootsPoolItem(new ItemStack(LOTRItems.RIVENDELL_BOOTS.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_SWORD.get()), 7)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_SPEAR.get()), 1)
		.addMeleePoolItem(new ItemStack(LOTRItems.RIVENDELL_AXE.get()), 1)
		.addRangedPoolItem(new ItemStack(ExtendedItems.LONGBOW.get()), 1)
		.addRangedPoolItem(new ItemStack(Items.BOW), 1)
		.addShieldItem(new ItemStack(ExtendedItems.RIVENDELL_SHIELD.get()), 1)
		.setIdlePoolFromMeleePool().save(pConsumer, ExtendedHiredUnitTypes.RIVENDELL_WARRIOR);
	}
}