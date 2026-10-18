package com.github.maximuslotro.lotrrextended.common.datagen.entity;

import java.util.function.Consumer;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedFactionPointers;
import lotr.common.init.LOTREntities;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedEntitySettingsGenerator extends ExtendedEntitySettingsProvider{

	public ExtendedEntitySettingsGenerator(DataGenerator pDataGenerator, ExistingFileHelper fileHelper) {
		super(pDataGenerator, fileHelper);
	}

	@Override
	public void makeEntitySettingBuilders(Consumer<ExtendedEntitySettingsBuilder> pConsumer) {
		// Lindon
		make(LOTREntities.LINDON_ELF.get(), FactionPointers.HIGH_ELVES).setBonus(1).setBankE("high_elves/lindon/elf").save(pConsumer);
		make(LOTREntities.LINDON_WARRIOR.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/lindon/warrior").save(pConsumer);
		make(ExtendedEntities.LINDON_LORD.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/lindon/warrior").save(pConsumer);
		make(ExtendedEntities.LINDON_SMITH.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/lindon/elf").save(pConsumer);
		make(ExtendedEntities.LINDON_WEAVER.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/lindon/elf").save(pConsumer);
		make(ExtendedEntities.LINDON_FLORIST.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/lindon/elf").save(pConsumer);
		// Rivendell
		make(LOTREntities.RIVENDELL_ELF.get(), FactionPointers.HIGH_ELVES).setBonus(1).setBankE("high_elves/rivendell/elf").save(pConsumer);
		make(LOTREntities.RIVENDELL_WARRIOR.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/rivendell/warrior").save(pConsumer);
		make(ExtendedEntities.RIVENDELL_WANDERER.get(), FactionPointers.HIGH_ELVES).setBonus(1).setBankE("high_elves/rivendell/wanderer").save(pConsumer);
		make(ExtendedEntities.RIVENDELL_CAPTAIN.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/rivendell/warrior").save(pConsumer);
		make(ExtendedEntities.RIVENDELL_SMITH.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/rivendell/elf").save(pConsumer);
		make(ExtendedEntities.IMLADRIS_GUARD.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/rivendell/warrior").save(pConsumer);
		make(ExtendedEntities.ELROND.get(), FactionPointers.HIGH_ELVES).setBonus(0).setBankE("lore_character/elrond").save(pConsumer);
		make(ExtendedEntities.RIVENDELL_WEAVER.get(), FactionPointers.HIGH_ELVES).setBonus(2).setBankE("high_elves/rivendell/elf").save(pConsumer);
		// Miscellaneous
		make(ExtendedEntities.BARROW_WIGHT.get(), FactionPointers.HOSTILE).setBankE("wight/barrow_wight").save(pConsumer);
		make(ExtendedEntities.BANDIT.get(), FactionPointers.HOSTILE).setBankE("bandit").save(pConsumer);
		make(ExtendedEntities.TOLKIEN.get(), FactionPointers.UNALIGNED).setBankE("lore_character/tolkien").save(pConsumer);
		// Utumno
		make(ExtendedEntities.ODDMENT_COLLECTOR.get(), ExtendedFactionPointers.ODDMENT_COLLECTORS).setBankE("oddment_collectors/oddment_collector").save(pConsumer);
		// Gundabad
		make(LOTREntities.GUNDABAD_ORC.get(), FactionPointers.NORTHERN_ORCS).setBonus(1).setBank("northern_orcs/gundabad/orc").save(pConsumer);
		make(LOTREntities.GUNDABAD_ORC_ARCHER.get(), FactionPointers.NORTHERN_ORCS).setBonus(1).setBank("northern_orcs/gundabad/orc").save(pConsumer);
		make(LOTREntities.GUNDABAD_WARG.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).save(pConsumer);
		make(ExtendedEntities.GUNDABAD_URUK.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBank("northern_orcs/gundabad/orc").save(pConsumer);
		make(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBank("northern_orcs/gundabad/orc").save(pConsumer);
		make(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBankE("northern_orcs/gundabad/orc_chieftain").save(pConsumer);
		make(ExtendedEntities.GUNDABAD_CAMP_TRADER.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBankE("northern_orcs/gundabad/orc_scrounger").save(pConsumer);
		// Durin's Folk
		make(LOTREntities.DWARF.get(), FactionPointers.DURINS_FOLK).setBonus(1).setBank("durins_folk/dwarf").save(pConsumer);
		make(LOTREntities.DWARF_WARRIOR.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBank("durins_folk/warrior").save(pConsumer);
		make(LOTREntities.DWARF_ARCHER.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBank("durins_folk/warrior").save(pConsumer);
		make(ExtendedEntities.DWARVEN_COMMANDER.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBankE("durins_folk/commander").save(pConsumer);
		make(ExtendedEntities.DWARVEN_MINER.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBankE("durins_folk/miner").save(pConsumer);
		make(ExtendedEntities.DWARVEN_SMITH.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBankE("durins_folk/smith").save(pConsumer);
		// Rangers of the North
		make(ExtendedEntities.DUNEDAIN_RANGER.get(), FactionPointers.DUNEDAIN_NORTH).setBonus(1).setBankE("dunedain_north/ranger").save(pConsumer);
		make(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get(), FactionPointers.DUNEDAIN_NORTH).setBonus(1).setBankE("dunedain_north/ranger").save(pConsumer);
		// Galadhrim
		make(LOTREntities.GALADHRIM_ELF.get(), FactionPointers.LOTHLORIEN).setBonus(1).setBank("lothlorien/elf").save(pConsumer);
		make(LOTREntities.GALADHRIM_WARRIOR.get(), FactionPointers.LOTHLORIEN).setBonus(2).setBank("lothlorien/warrior").save(pConsumer);
		make(ExtendedEntities.GALADHRIM_LORD.get(), FactionPointers.LOTHLORIEN).setBonus(5).setBankE("lothlorien/lord").save(pConsumer);
		make(ExtendedEntities.GALADHRIM_WANDERER.get(), FactionPointers.LOTHLORIEN).setBonus(1).setBankE("lothlorien/galadhrim_wanderer").save(pConsumer);
		// Blue Mountains
		make(LOTREntities.BLUE_MOUNTAINS_DWARF.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(1).setBankE("blue_mountains/dwarf").save(pConsumer);
		make(LOTREntities.BLUE_MOUNTAINS_WARRIOR.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/warrior").save(pConsumer);
		make(LOTREntities.BLUE_MOUNTAINS_ARCHER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/warrior").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_COMMANDER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/commander").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_MINER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/miner").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_SMITH.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/smith").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_BARTENDER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/bartender").save(pConsumer);
		make(ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(1).setBankE("blue_mountains/merchant").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_BUTCHER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/dwarf").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_STONEMASON.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/dwarf").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_AXE_THROWER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/warrior").save(pConsumer);
		// Hobbits
		make(LOTREntities.HOBBIT.get(), FactionPointers.HOBBITS).setBonus(1).setBank("hobbits/hobbit").save(pConsumer);
		make(ExtendedEntities.HOBBIT_FARMHAND.get(), FactionPointers.HOBBITS).setBonus(1).setBank("hobbits/hobbit").save(pConsumer);
		make(ExtendedEntities.HOBBIT_BARTENDER.get(), FactionPointers.HOBBITS).setBonus(1).setBankE("hobbits/hobbit_bartender").save(pConsumer);
		make(ExtendedEntities.HOBBIT_FARMER.get(), FactionPointers.HOBBITS).setBonus(1).setBankE("hobbits/hobbit_farmer").save(pConsumer);
		// Bree
		make(LOTREntities.BREE_MAN.get(), FactionPointers.BREE).setBonus(1).setBankE("bree/man").save(pConsumer);
		make(LOTREntities.BREE_HOBBIT.get(), FactionPointers.BREE).setBonus(1).setBankE("bree/hobbit").save(pConsumer);
		make(LOTREntities.BREE_GUARD.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/guard").save(pConsumer);
		make(ExtendedEntities.BREE_BAKER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_BREWER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_BUTCHER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_CHEESEMONGER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_FARMER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_FARMHAND.get(), FactionPointers.BREE).setBonus(1).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_FLORIST.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_HOBBIT_BAKER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/hobbit").save(pConsumer);
		make(ExtendedEntities.BREE_HOBBIT_BREWER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/hobbit").save(pConsumer);
		make(ExtendedEntities.BREE_HOBBIT_BUTCHER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/hobbit").save(pConsumer);
		make(ExtendedEntities.BREE_HOBBIT_FLORIST.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/hobbit").save(pConsumer);
		make(ExtendedEntities.BREE_HOBBIT_INNKEEPER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/hobbit").save(pConsumer);
		make(ExtendedEntities.BREE_HOBBIT_POTTER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/hobbit").save(pConsumer);
		make(ExtendedEntities.BREE_INNKEEPER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_LEATHERWORKER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_LUMBERMAN.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_MERCHANT.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/merchant").save(pConsumer);
		make(ExtendedEntities.BREE_POTTER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_SHERIFF.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/sheriff").save(pConsumer);
		make(ExtendedEntities.BREE_SMITH.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_STABLEMASTER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_STONEMASON.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		// Isengard
		make(LOTREntities.ISENGARD_SNAGA.get(), FactionPointers.ISENGARD).setBonus(1).setBankE("isengard/orc").save(pConsumer);
		make(LOTREntities.ISENGARD_SNAGA_ARCHER.get(), FactionPointers.ISENGARD).setBonus(1).setBankE("isengard/orc").save(pConsumer);
		make(LOTREntities.URUK.get(), FactionPointers.ISENGARD).setBonus(2).setBankE("isengard/uruk").save(pConsumer);
		make(LOTREntities.URUK_ARCHER.get(), FactionPointers.ISENGARD).setBonus(2).setBankE("isengard/uruk").save(pConsumer);
		make(LOTREntities.ISENGARD_WARG.get(), FactionPointers.ISENGARD).setBonus(2).save(pConsumer);
		make(ExtendedEntities.URUK_BERSERKER.get(), FactionPointers.ISENGARD).setBonus(2).setBankE("isengard/uruk").save(pConsumer);
		make(ExtendedEntities.URUK_CAPTAIN.get(), FactionPointers.ISENGARD).setBonus(5).setBankE("isengard/captain").save(pConsumer);
		make(ExtendedEntities.URUK_SMITH.get(), FactionPointers.ISENGARD).setBonus(2).setBankE("isengard/smith").save(pConsumer);
		make(ExtendedEntities.URUK_SUPPLIER.get(), FactionPointers.ISENGARD).setBonus(2).setBankE("isengard/supplier").save(pConsumer);
		// Dunland
		make(LOTREntities.DUNLENDING.get(), FactionPointers.DUNLAND).setBonus(1).setBankE("dunland/dunlending").save(pConsumer);
		make(LOTREntities.DUNLENDING_WARRIOR.get(), FactionPointers.DUNLAND).setBonus(2).setBankE("dunland/dunlending").save(pConsumer);
		make(LOTREntities.DUNLENDING_BOWMAN.get(), FactionPointers.DUNLAND).setBonus(2).setBankE("dunland/dunlending").save(pConsumer);
		make(ExtendedEntities.DUNLENDING_WARLORD.get(), FactionPointers.DUNLAND).setBonus(5).setBankE("dunland/warlord").save(pConsumer);
		make(ExtendedEntities.DUNLENDING_AXE_THROWER.get(), FactionPointers.DUNLAND).setBonus(2).setBankE("dunland/dunlending").save(pConsumer);
		make(ExtendedEntities.DUNLENDING_BERSERKER.get(), FactionPointers.DUNLAND).setBonus(2).setBankE("dunland/dunlending").save(pConsumer);
		make(ExtendedEntities.DUNLENDING_BARTENDER.get(), FactionPointers.DUNLAND).setBonus(2).setBankE("dunland/bartender").save(pConsumer);
		make(ExtendedEntities.DUNLENDING_SMITH.get(), FactionPointers.DUNLAND).setBonus(2).setBankE("dunland/smith").save(pConsumer);
		make(ExtendedEntities.DUNLENDING_FARMER.get(), FactionPointers.DUNLAND).setBonus(2).setBankE("dunland/farmer").save(pConsumer);
		make(ExtendedEntities.DUNLENDING_FARMHAND.get(), FactionPointers.DUNLAND).setBonus(1).setBankE("dunland/dunlending").save(pConsumer);
		// Dale
		make(LOTREntities.DALE_MAN.get(), FactionPointers.DALE).setBonus(1).setBankE("dale/man").save(pConsumer);
		make(LOTREntities.DALE_SOLDIER.get(), FactionPointers.DALE).setBonus(2).setBankE("dale/soldier").save(pConsumer);
		make(LOTREntities.DALE_BOWMAN.get(), FactionPointers.DALE).setBonus(2).setBankE("dale/soldier").save(pConsumer);
		make(ExtendedEntities.DALE_CAPTAIN.get(), FactionPointers.DALE).setBonus(5).setBankE("dale/captain").save(pConsumer);
		// Coast Southron
		make(LOTREntities.COAST_SOUTHRON.get(), FactionPointers.NEAR_HARAD).setBonus(1).setBankE("near_harad/coast/man").save(pConsumer);
		make(LOTREntities.COAST_SOUTHRON_ARCHER.get(), FactionPointers.NEAR_HARAD).setBonus(2).setBankE("near_harad/coast/warrior").save(pConsumer);
		make(LOTREntities.COAST_SOUTHRON_WARRIOR.get(), FactionPointers.NEAR_HARAD).setBonus(2).setBankE("near_harad/coast/warrior").save(pConsumer);
		make(ExtendedEntities.COAST_SOUTHRON_WARLORD.get(), FactionPointers.NEAR_HARAD).setBonus(5).setBankE("near_harad/coast/warlord").save(pConsumer);
		// Gondor
		make(LOTREntities.GONDOR_MAN.get(), FactionPointers.GONDOR).setBonus(1).setBank("gondor/man").save(pConsumer);
		make(LOTREntities.GONDOR_ARCHER.get(), FactionPointers.GONDOR).setBonus(2).setBank("gondor/soldier").save(pConsumer);
		make(LOTREntities.GONDOR_SOLDIER.get(), FactionPointers.GONDOR).setBonus(2).setBank("gondor/soldier").save(pConsumer);
		make(ExtendedEntities.GONDOR_CAPTAIN.get(), FactionPointers.GONDOR).setBonus(5).setBankE("gondor/captain").save(pConsumer);
		// Harnennor
		make(LOTREntities.HARNEDHRIM.get(), FactionPointers.NEAR_HARAD).setBonus(1).setBankE("near_harad/harnennor/man").save(pConsumer);
		make(LOTREntities.HARNENNOR_ARCHER.get(), FactionPointers.NEAR_HARAD).setBonus(2).setBankE("near_harad/harnennor/warrior").save(pConsumer);
		make(LOTREntities.HARNENNOR_WARRIOR.get(), FactionPointers.NEAR_HARAD).setBonus(2).setBankE("near_harad/harnennor/warrior").save(pConsumer);
		make(ExtendedEntities.HARNENNOR_WARLORD.get(), FactionPointers.NEAR_HARAD).setBonus(5).setBankE("near_harad/harnennor/warlord").save(pConsumer);
		// Mordor
		make(LOTREntities.MORDOR_ORC.get(), FactionPointers.MORDOR).setBonus(1).setBank("mordor/orc").save(pConsumer);
		make(LOTREntities.MORDOR_ORC_ARCHER.get(), FactionPointers.MORDOR).setBonus(1).setBank("mordor/orc").save(pConsumer);
		make(LOTREntities.MORDOR_WARG.get(), FactionPointers.MORDOR).setBonus(2).save(pConsumer);
		make(ExtendedEntities.MORDOR_ORC_COMMANDER.get(), FactionPointers.NEAR_HARAD).setBonus(5).setBankE("mordor/commander").save(pConsumer);
		// Rohan
		make(LOTREntities.ROHAN_MAN.get(), FactionPointers.ROHAN).setBonus(1).setBank("rohan/man").save(pConsumer);
		make(LOTREntities.ROHIRRIM_BOWMAN.get(), FactionPointers.ROHAN).setBonus(2).setBank("rohan/warrior").save(pConsumer);
		make(LOTREntities.ROHIRRIM_WARRIOR.get(), FactionPointers.ROHAN).setBonus(2).setBank("rohan/warrior").save(pConsumer);
		make(ExtendedEntities.ROHIRRIM_MARSHAL.get(), FactionPointers.ROHAN).setBonus(5).setBankE("rohan/marshal").save(pConsumer);
		// Umbar
		make(LOTREntities.UMBAR_MAN.get(), FactionPointers.ROHAN).setBonus(1).setBankE("near_harad/umbar/man").save(pConsumer);
		make(LOTREntities.UMBAR_ARCHER.get(), FactionPointers.ROHAN).setBonus(2).setBankE("near_harad/umbar/soldier").save(pConsumer);
		make(LOTREntities.UMBAR_SOLDIER.get(), FactionPointers.ROHAN).setBonus(2).setBankE("near_harad/umbar/soldier").save(pConsumer);
		make(ExtendedEntities.UMBAR_CAPTAIN.get(), FactionPointers.ROHAN).setBonus(5).setBankE("near_harad/umbar/captain").save(pConsumer);
		// Wood Elf
		make(LOTREntities.WOOD_ELF.get(), FactionPointers.WOODLAND_REALM).setBonus(1).setBankE("woodland_realm/elf").save(pConsumer);
		make(LOTREntities.WOOD_ELF_WARRIOR.get(), FactionPointers.WOODLAND_REALM).setBonus(2).setBankE("woodland_realm/warrior").save(pConsumer);
		make(ExtendedEntities.WOOD_ELF_CAPTAIN.get(), FactionPointers.WOODLAND_REALM).setBonus(5).setBankE("woodland_realm/captain").save(pConsumer);
	}
}