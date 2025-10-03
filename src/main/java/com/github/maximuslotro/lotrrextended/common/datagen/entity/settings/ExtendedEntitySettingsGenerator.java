package com.github.maximuslotro.lotrrextended.common.datagen.entity.settings;

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
		make(ExtendedEntities.BARROW_WIGHT.get(), FactionPointers.HOSTILE).setBankE("wight/barrow_wight").save(pConsumer);
		make(ExtendedEntities.BANDIT.get(), FactionPointers.UNALIGNED).setBankE("bandit").save(pConsumer);
		make(ExtendedEntities.GUNDABAD_URUK.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBank("northern_orcs/gundabad/orc").save(pConsumer);
		make(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBank("northern_orcs/gundabad/orc").save(pConsumer);
		make(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBank("northern_orcs/gundabad/orc_chieftain").save(pConsumer);
		make(ExtendedEntities.GUNDABAD_CAMP_TRADER.get(), FactionPointers.NORTHERN_ORCS).setBonus(2).setBank("northern_orcs/gundabad/orc_scrounger").save(pConsumer);
		make(ExtendedEntities.DWARVEN_COMMANDER.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBankE("durins_folk/commander").save(pConsumer);
		make(ExtendedEntities.DWARVEN_MINER.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBankE("durins_folk/miner").save(pConsumer);
		make(ExtendedEntities.DWARVEN_SMITH.get(), FactionPointers.DURINS_FOLK).setBonus(2).setBankE("durins_folk/smith").save(pConsumer);
		make(ExtendedEntities.DUNEDAIN_RANGER.get(), FactionPointers.DUNEDAIN_NORTH).setBonus(1).setBankE("dunedain_north/ranger").save(pConsumer);
		make(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get(), FactionPointers.DUNEDAIN_NORTH).setBonus(1).setBankE("dunedain_north/ranger").save(pConsumer);
		make(ExtendedEntities.GALADHRIM_WANDERER.get(), FactionPointers.LOTHLORIEN).setBonus(1).setBank("lothlorien/galadhrim_wanderer").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_COMMANDER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/commander").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_MINER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/miner").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_SMITH.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/smith").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_BARTENDER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/bartender").save(pConsumer);
		make(ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(1).setBankE("blue_mountains/merchant").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_BUTCHER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/dwarf").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_STONEMASON.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/dwarf").save(pConsumer);
		make(LOTREntities.BLUE_MOUNTAINS_DWARF.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(1).setBankE("blue_mountains/dwarf").save(pConsumer);
		make(LOTREntities.BLUE_MOUNTAINS_WARRIOR.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/warrior").save(pConsumer);
		make(LOTREntities.BLUE_MOUNTAINS_ARCHER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/warrior").save(pConsumer);
		make(ExtendedEntities.BLUE_DWARVEN_AXE_THROWER.get(), FactionPointers.BLUE_MOUNTAINS).setBonus(2).setBankE("blue_mountains/warrior").save(pConsumer);
		make(ExtendedEntities.HOBBIT_BARTENDER.get(), FactionPointers.HOBBITS).setBonus(1).setBank("hobbits/hobbit_bartender").save(pConsumer);
		make(ExtendedEntities.HOBBIT_FARMER.get(), FactionPointers.HOBBITS).setBonus(1).setBank("hobbits/hobbit_farmer").save(pConsumer);
		make(ExtendedEntities.ODDMENT_COLLECTOR.get(), ExtendedFactionPointers.ODDMENT_COLLECTORS).setBankE("oddment_collectors/oddment_collector").save(pConsumer);
		make(ExtendedEntities.RIVENDELL_WANDERER.get(), FactionPointers.HIGH_ELVES).setBonus(1).setBank("high_elves/rivendell/wanderer").save(pConsumer);
		make(ExtendedEntities.TOLKIEN.get(), FactionPointers.UNALIGNED).setBankE("tolkien").save(pConsumer);
		make(LOTREntities.BREE_MAN.get(), FactionPointers.BREE).setBonus(1).setBankE("bree/man").save(pConsumer);
		make(LOTREntities.BREE_HOBBIT.get(), FactionPointers.BREE).setBonus(1).setBankE("bree/hobbit").save(pConsumer);
		make(LOTREntities.BREE_GUARD.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/guard").save(pConsumer);
		make(ExtendedEntities.BREE_BAKER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_BREWER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_BUTCHER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_CHEESEMONGER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
		make(ExtendedEntities.BREE_FARMER.get(), FactionPointers.BREE).setBonus(2).setBankE("bree/man").save(pConsumer);
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
	}
}