package com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitAttackType;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.entity.npc.util.LeatherDyeUtil;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ExtendedHiredUnitProfileGenerator extends ExtendedHiredUnitProfileProvider{

	public ExtendedHiredUnitProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedHiredUnitProfile> pConsumer) {
		// Bree Units
		make(ExtendedEntities.HIRED_BREE_GUARD.get(), ExtendedUnitAttackType.MELEE).setLocal(true, "bree_guard", "Bree-land Guard").setPurchaseInfo(30, false, 100)
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
		.setIdlePoolFromMeleePool()
		.save(pConsumer, ExtendedHiredUnitTypes.BREE_GUARD);

		// Gundabad Units
		make(ExtendedEntities.HIRED_GUNDABAD_ORC.get(), ExtendedUnitAttackType.MELEE).setLocal(true, "gundabad_orc", "Gundabad Orc").setPurchaseInfo(25, false, 100)
			.addHelmetPoolItem(new ItemStack(LOTRItems.BONE_HELMET.get()), 1)
			.addHelmetPoolItem(new ItemStack(Items.LEATHER_HELMET), 1)
			.addHelmetPoolItem(new ItemStack(LOTRItems.BRONZE_HELMET.get()), 1)
			.addHelmetPoolItem(new ItemStack(ExtendedItems.ANGMAR_HELMET.get()), 1)
			.addHelmetPoolItem(new ItemStack(LOTRItems.MORDOR_HELMET.get()), 1)
			.addHelmetPoolItem(new ItemStack(LOTRItems.FUR_HELMET.get()), 1)
			.addHelmetPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_HELMET.get()), 1)
			.addHelmetPoolItem(new ItemStack(ExtendedItems.DOL_GULDUR_HELMET.get()), 1)
			.addChestplatePoolItem(new ItemStack(Items.LEATHER_CHESTPLATE), 1)
			.addChestplatePoolItem(new ItemStack(LOTRItems.BONE_CHESTPLATE.get()), 1)
			.addChestplatePoolItem(new ItemStack(LOTRItems.BRONZE_CHESTPLATE.get()), 1)
			.addChestplatePoolItem(new ItemStack(LOTRItems.MORDOR_CHESTPLATE.get()), 1)
			.addChestplatePoolItem(new ItemStack(LOTRItems.FUR_CHESTPLATE.get()), 1)
			.addChestplatePoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get()), 1)
			.addChestplatePoolItem(new ItemStack(ExtendedItems.ANGMAR_CHESTPLATE.get()), 1)
			.addChestplatePoolItem(new ItemStack(ExtendedItems.DOL_GULDUR_CHESTPLATE.get()), 1)
			.addLeggingPoolItem(new ItemStack(Items.LEATHER_LEGGINGS), 1)
			.addLeggingPoolItem(new ItemStack(LOTRItems.MORDOR_LEGGINGS.get()), 1)
			.addLeggingPoolItem(new ItemStack(LOTRItems.FUR_LEGGINGS.get()), 1)
			.addLeggingPoolItem(new ItemStack(LOTRItems.BONE_LEGGINGS.get()), 1)
			.addLeggingPoolItem(new ItemStack(LOTRItems.BRONZE_LEGGINGS.get()), 1)
			.addLeggingPoolItem(new ItemStack(ExtendedItems.DOL_GULDUR_LEGGINGS.get()), 1)
			.addLeggingPoolItem(new ItemStack(ExtendedItems.ANGMAR_LEGGINGS.get()), 1)
			.addLeggingPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_LEGGINGS.get()), 1)
			.addBootsPoolItem(new ItemStack(Items.LEATHER_BOOTS), 1)
			.addBootsPoolItem(new ItemStack(LOTRItems.FUR_BOOTS.get()), 1)
			.addBootsPoolItem(new ItemStack(LOTRItems.BONE_BOOTS.get()), 1)
			.addBootsPoolItem(new ItemStack(LOTRItems.BRONZE_BOOTS.get()), 1)
			.addBootsPoolItem(new ItemStack(LOTRItems.MORDOR_BOOTS.get()), 1)
			.addBootsPoolItem(new ItemStack(ExtendedItems.ANGMAR_BOOTS.get()), 1)
			.addBootsPoolItem(new ItemStack(ExtendedItems.GUNDABAD_URUK_BOOTS.get()), 1)
			.addBootsPoolItem(new ItemStack(ExtendedItems.DOL_GULDUR_BOOTS.get()), 1)
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
			.setIdlePoolFromMeleePool()
			.save(pConsumer, ExtendedHiredUnitTypes.GUNDABAD_ORC);
		
		//Dunedain Units
		make(ExtendedEntities.HIRED_DUNEDAIN_RANGER.get(), ExtendedUnitAttackType.RANGED).setLocal(true, "dunedain_ranger", "Dúnedain Ranger").setPurchaseInfo(40, false, 100)
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
		.addRangedPoolItem(new ItemStack(ExtendedItems.LONGBOW.get()), 1)
		.addRangedPoolItem(new ItemStack(Items.BOW), 1)
		.addShieldItem(new ItemStack(ExtendedItems.DUNEDAIN_SHIELD.get()), 1)
		.addShieldItem(ItemStack.EMPTY, 2)
		.setIdlePoolFromRangedPool()
		.save(pConsumer, ExtendedHiredUnitTypes.DUNEDAIN_RANGER);
	}

}