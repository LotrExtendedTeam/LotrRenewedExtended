package com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.entity.npc.util.LeatherDyeUtil;
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
		// BREE UNITS
		make().setLocal(true, "bree_guard", "Bree-land Guard").setPurchaseInfo(30, false)
		.addHelmetPoolItem(new ItemStack(Items.IRON_HELMET), 0)
		.addChestplatePoolItem(LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, 7823440), 0)
		.addLeggingPoolItem(new ItemStack(Items.CHAINMAIL_LEGGINGS), 0)
		.addBootsPoolItem(new ItemStack(Items.CHAINMAIL_BOOTS), 0)
		.addWeaponPoolItem(new ItemStack(LOTRItems.IRON_SPEAR.get()), 0)
		.addShieldItem(new ItemStack(Items.SHIELD), 0).save(pConsumer, ExtendedHiredUnitTypes.BREE_GUARD);
	}
}