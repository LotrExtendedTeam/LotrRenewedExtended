package com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles;

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
		make(ExtendedCaptainTypes.BLUE_DWARF_COMMANDER).addProfile(ExtendedHiredUnitTypes.BLUE_DWARF)
		.addProfile(ExtendedHiredUnitTypes.BLUE_DWARF_WARRIOR).addProfile(ExtendedHiredUnitTypes.BLUE_DWARF_ARCHER)
		.addProfile(ExtendedHiredUnitTypes.BLUE_DWARF_AXE_THROWER).save(pConsumer);

		make(ExtendedCaptainTypes.BREE_SHERIFF).addProfile(ExtendedHiredUnitTypes.BREE_HOBBIT)
		.addProfile(ExtendedHiredUnitTypes.BREE_MAN).addProfile(ExtendedHiredUnitTypes.BREE_GUARD).save(pConsumer);

		make(ExtendedCaptainTypes.DUNEDAIN_CAPTAIN).addProfile(ExtendedHiredUnitTypes.DUNEDAIN_RANGER).save(pConsumer);

		make(ExtendedCaptainTypes.DUNLENDING_WARLORD).addProfile(ExtendedHiredUnitTypes.DUNLENDING)
		.addProfile(ExtendedHiredUnitTypes.DUNLENDING_WARRIOR).addProfile(ExtendedHiredUnitTypes.DUNLENDING_BOWMAN)
		.addProfile(ExtendedHiredUnitTypes.DUNLENDING_AXE_THROWER).addProfile(ExtendedHiredUnitTypes.DUNLENDING_BERSERKER).save(pConsumer);

		make(ExtendedCaptainTypes.DWARF_COMMANDER).addProfile(ExtendedHiredUnitTypes.DWARF)
		.addProfile(ExtendedHiredUnitTypes.DWARF_WARRIOR).addProfile(ExtendedHiredUnitTypes.DWARF_ARCHER)
		.save(pConsumer);

		make(ExtendedCaptainTypes.GUNDABAD_CHIEFTAIN).addProfile(ExtendedHiredUnitTypes.GUNDABAD_ORC)
		.addProfile(ExtendedHiredUnitTypes.GUNDABAD_ORC_ARCHER).addProfile(ExtendedHiredUnitTypes.GUNDABAD_URUK)
		.addProfile(ExtendedHiredUnitTypes.GUNDABAD_URUK_ARCHER).save(pConsumer);

		make(ExtendedCaptainTypes.LINDON_LORD).addProfile(ExtendedHiredUnitTypes.LINDON_ELF)
		.addProfile(ExtendedHiredUnitTypes.LINDON_WARRIOR).save(pConsumer);

		make(ExtendedCaptainTypes.RIVENDELL_CAPTAIN).addProfile(ExtendedHiredUnitTypes.RIVENDELL_ELF)
		.addProfile(ExtendedHiredUnitTypes.RIVENDELL_WARRIOR).addProfile(ExtendedHiredUnitTypes.IMLADRIS_GUARD).save(pConsumer);

		make(ExtendedCaptainTypes.ISENGARD_CAPTAIN).addProfile(ExtendedHiredUnitTypes.ISENGARD_SNAGA)
		.addProfile(ExtendedHiredUnitTypes.ISENGARD_SNAGA_ARCHER).addProfile(ExtendedHiredUnitTypes.ISENGARD_URUK)
		.addProfile(ExtendedHiredUnitTypes.ISENGARD_URUK_ARCHER).addProfile(ExtendedHiredUnitTypes.ISENGARD_URUK_BERSERKER).save(pConsumer);
	}
}
