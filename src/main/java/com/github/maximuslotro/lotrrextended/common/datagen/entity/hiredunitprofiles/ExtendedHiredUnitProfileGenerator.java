package com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import net.minecraft.data.DataGenerator;

public class ExtendedHiredUnitProfileGenerator extends ExtendedHiredUnitProfileProvider{

	public ExtendedHiredUnitProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedHiredUnitProfile> pConsumer) {
		// ANIMALS
		make().setLocal(true, "bree_guard", "Bree-land Guard").setPurchaseInfo(30, false).save(pConsumer, ExtendedHiredUnitTypes.BREE_GUARD);
	}
}