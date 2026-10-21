package com.github.maximuslotro.lotrrextended.common.datagen.entity;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainProfile;

import net.minecraft.data.DataGenerator;

public class ExtendedCaptainProfileGenerator extends ExtendedCaptainProfileProvider {
	public ExtendedCaptainProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeCaptainProfiles(Consumer<ExtendedCaptainProfile> pConsumer) {
		make(ExtendedCaptainTypes.BLUE_DWARF_COMMANDER, 200).addProfile(ExtendedUnitTypes.BLUE_DWARF)
		.addProfile(ExtendedUnitTypes.BLUE_DWARF_WARRIOR).addProfile(ExtendedUnitTypes.BLUE_DWARF_ARCHER)
		.addProfile(ExtendedUnitTypes.BLUE_DWARF_AXE_THROWER).save(pConsumer);
		// Bree
		make(ExtendedCaptainTypes.BREE_SHERIFF, 100).addProfile(ExtendedUnitTypes.BREE_HOBBIT)
		.addProfile(ExtendedUnitTypes.BREE_MAN).addProfile(ExtendedUnitTypes.BREE_GUARD).save(pConsumer);
		make(ExtendedCaptainTypes.BREE_FARMER, 0).addProfile(ExtendedUnitTypes.BREE_FARMHAND).save(pConsumer);

		make(ExtendedCaptainTypes.DUNEDAIN_CAPTAIN, 300).addProfile(ExtendedUnitTypes.DUNEDAIN_RANGER).save(pConsumer);

		make(ExtendedCaptainTypes.DUNLENDING_WARLORD, 100).addProfile(ExtendedUnitTypes.DUNLENDING)
		.addProfile(ExtendedUnitTypes.DUNLENDING_WARRIOR).addProfile(ExtendedUnitTypes.DUNLENDING_BOWMAN)
		.addProfile(ExtendedUnitTypes.DUNLENDING_AXE_THROWER).addProfile(ExtendedUnitTypes.DUNLENDING_BERSERKER).save(pConsumer);
		make(ExtendedCaptainTypes.DUNLENDING_FARMER, 0).addProfile(ExtendedUnitTypes.DUNLENDING_FARMHAND).save(pConsumer);

		make(ExtendedCaptainTypes.DWARF_COMMANDER, 200).addProfile(ExtendedUnitTypes.DWARF)
		.addProfile(ExtendedUnitTypes.DWARF_WARRIOR).addProfile(ExtendedUnitTypes.DWARF_ARCHER)
		.save(pConsumer);

		make(ExtendedCaptainTypes.GUNDABAD_CHIEFTAIN, 100).addProfile(ExtendedUnitTypes.GUNDABAD_ORC)
		.addProfile(ExtendedUnitTypes.GUNDABAD_ORC_ARCHER).addProfile(ExtendedUnitTypes.GUNDABAD_URUK)
		.addProfile(ExtendedUnitTypes.GUNDABAD_URUK_ARCHER).save(pConsumer);

		make(ExtendedCaptainTypes.LINDON_LORD, 300).addProfile(ExtendedUnitTypes.LINDON_ELF)
		.addProfile(ExtendedUnitTypes.LINDON_WARRIOR).save(pConsumer);

		make(ExtendedCaptainTypes.RIVENDELL_CAPTAIN, 300).addProfile(ExtendedUnitTypes.RIVENDELL_ELF)
		.addProfile(ExtendedUnitTypes.RIVENDELL_WARRIOR).addProfile(ExtendedUnitTypes.IMLADRIS_GUARD).save(pConsumer);

		make(ExtendedCaptainTypes.ISENGARD_CAPTAIN, 150).addProfile(ExtendedUnitTypes.ISENGARD_SNAGA)
		.addProfile(ExtendedUnitTypes.ISENGARD_SNAGA_ARCHER).addProfile(ExtendedUnitTypes.ISENGARD_URUK)
		.addProfile(ExtendedUnitTypes.ISENGARD_URUK_ARCHER).addProfile(ExtendedUnitTypes.ISENGARD_URUK_BERSERKER).save(pConsumer);

		make(ExtendedCaptainTypes.HOBBIT_FARMER, 0).addProfile(ExtendedUnitTypes.HOBBIT_FARMHAND).save(pConsumer);

		make(ExtendedCaptainTypes.UMBAR_CAPTAIN, 150).addProfile(ExtendedUnitTypes.UMBAR_SOLDIER)
		.addProfile(ExtendedUnitTypes.UMBAR_ARCHER).save(pConsumer);

		make(ExtendedCaptainTypes.MORDOR_COMMANDER, 150).addProfile(ExtendedUnitTypes.MORDOR_ORC)
		.addProfile(ExtendedUnitTypes.MORDOR_ORC_ARCHER).save(pConsumer);
		
		make(ExtendedCaptainTypes.DALISH_CAPTAIN, 100).addProfile(ExtendedUnitTypes.DALE_SOLDIER)
		.addProfile(ExtendedUnitTypes.DALE_BOWMAN).save(pConsumer);

		make(ExtendedCaptainTypes.GONDORIAN_CAPTAIN, 200).addProfile(ExtendedUnitTypes.GONDOR_SOLDIER)
		.addProfile(ExtendedUnitTypes.GONDOR_ARCHER).save(pConsumer);

		make(ExtendedCaptainTypes.WOOD_ELF_CAPTAIN, 250).addProfile(ExtendedUnitTypes.WOOD_ELF)
		.addProfile(ExtendedUnitTypes.WOOD_ELF_WARRIOR).save(pConsumer);

		make(ExtendedCaptainTypes.GALADHRIM_LORD, 300).addProfile(ExtendedUnitTypes.GALADHRIM_ELF)
		.addProfile(ExtendedUnitTypes.GALADHRIM_WARRIOR).save(pConsumer);

		make(ExtendedCaptainTypes.COAST_SOUTHRON_WARLORD, 150).addProfile(ExtendedUnitTypes.COAST_SOUTHRON_WARRIOR)
		.addProfile(ExtendedUnitTypes.COAST_SOUTHRON_ARCHER).save(pConsumer);

		make(ExtendedCaptainTypes.HARNENNOR_WARLORD, 150).addProfile(ExtendedUnitTypes.HARNENNOR_WARRIOR)
		.addProfile(ExtendedUnitTypes.HARNENNOR_ARCHER).save(pConsumer);

		make(ExtendedCaptainTypes.ROHAN_MARSHAL, 150).addProfile(ExtendedUnitTypes.ROHAN_WARRIOR)
		.addProfile(ExtendedUnitTypes.ROHAN_BOWMAN).save(pConsumer);
	}
}
