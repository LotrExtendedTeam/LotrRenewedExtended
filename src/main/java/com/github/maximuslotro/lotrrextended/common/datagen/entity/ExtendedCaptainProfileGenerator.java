package com.github.maximuslotro.lotrrextended.common.datagen.entity;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainProfile;

import net.minecraft.data.DataGenerator;

public class ExtendedCaptainProfileGenerator extends ExtendedCaptainProfileProvider {
	public ExtendedCaptainProfileGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeCaptainProfiles(Consumer<ExtendedCaptainProfile> pConsumer) {
		make(ExtendedCaptainTypes.BLUE_DWARF_COMMANDER, 200).addProfile(ExtendedHiredUnitTypes.BLUE_DWARF)
		.addProfile(ExtendedHiredUnitTypes.BLUE_DWARF_WARRIOR).addProfile(ExtendedHiredUnitTypes.BLUE_DWARF_ARCHER)
		.addProfile(ExtendedHiredUnitTypes.BLUE_DWARF_AXE_THROWER).save(pConsumer);
		// Bree
		make(ExtendedCaptainTypes.BREE_SHERIFF, 100).addProfile(ExtendedHiredUnitTypes.BREE_HOBBIT)
		.addProfile(ExtendedHiredUnitTypes.BREE_MAN).addProfile(ExtendedHiredUnitTypes.BREE_GUARD).save(pConsumer);
		make(ExtendedCaptainTypes.BREE_FARMER, 0).addProfile(ExtendedHiredUnitTypes.BREE_FARMHAND).save(pConsumer);

		make(ExtendedCaptainTypes.DUNEDAIN_CAPTAIN, 300).addProfile(ExtendedHiredUnitTypes.DUNEDAIN_RANGER).save(pConsumer);

		make(ExtendedCaptainTypes.DUNLENDING_WARLORD, 100).addProfile(ExtendedHiredUnitTypes.DUNLENDING)
		.addProfile(ExtendedHiredUnitTypes.DUNLENDING_WARRIOR).addProfile(ExtendedHiredUnitTypes.DUNLENDING_BOWMAN)
		.addProfile(ExtendedHiredUnitTypes.DUNLENDING_AXE_THROWER).addProfile(ExtendedHiredUnitTypes.DUNLENDING_BERSERKER).save(pConsumer);
		make(ExtendedCaptainTypes.DUNLENDING_FARMER, 0).addProfile(ExtendedHiredUnitTypes.DUNLENDING_FARMHAND).save(pConsumer);

		make(ExtendedCaptainTypes.DWARF_COMMANDER, 200).addProfile(ExtendedHiredUnitTypes.DWARF)
		.addProfile(ExtendedHiredUnitTypes.DWARF_WARRIOR).addProfile(ExtendedHiredUnitTypes.DWARF_ARCHER)
		.save(pConsumer);

		make(ExtendedCaptainTypes.GUNDABAD_CHIEFTAIN, 100).addProfile(ExtendedHiredUnitTypes.GUNDABAD_ORC)
		.addProfile(ExtendedHiredUnitTypes.GUNDABAD_ORC_ARCHER).addProfile(ExtendedHiredUnitTypes.GUNDABAD_URUK)
		.addProfile(ExtendedHiredUnitTypes.GUNDABAD_URUK_ARCHER).save(pConsumer);

		make(ExtendedCaptainTypes.LINDON_LORD, 300).addProfile(ExtendedHiredUnitTypes.LINDON_ELF)
		.addProfile(ExtendedHiredUnitTypes.LINDON_WARRIOR).save(pConsumer);

		make(ExtendedCaptainTypes.RIVENDELL_CAPTAIN, 300).addProfile(ExtendedHiredUnitTypes.RIVENDELL_ELF)
		.addProfile(ExtendedHiredUnitTypes.RIVENDELL_WARRIOR).addProfile(ExtendedHiredUnitTypes.IMLADRIS_GUARD).save(pConsumer);

		make(ExtendedCaptainTypes.ISENGARD_CAPTAIN, 150).addProfile(ExtendedHiredUnitTypes.ISENGARD_SNAGA)
		.addProfile(ExtendedHiredUnitTypes.ISENGARD_SNAGA_ARCHER).addProfile(ExtendedHiredUnitTypes.ISENGARD_URUK)
		.addProfile(ExtendedHiredUnitTypes.ISENGARD_URUK_ARCHER).addProfile(ExtendedHiredUnitTypes.ISENGARD_URUK_BERSERKER).save(pConsumer);

		make(ExtendedCaptainTypes.HOBBIT_FARMER, 0).addProfile(ExtendedHiredUnitTypes.HOBBIT_FARMHAND).save(pConsumer);
	}
}
