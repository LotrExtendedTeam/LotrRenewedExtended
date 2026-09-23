package com.github.maximuslotro.lotrrextended.common.datagen.world;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBanditChance;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedInvasionChance;
import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedBiomeInformation;

import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.ExtendedInvasions;
import net.minecraft.data.DataGenerator;

public class ExtendedBiomeInformationGenerator extends ExtendedBiomeInformationProvider{

	public ExtendedBiomeInformationGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}
	// Unported Biomes & Conquest Gain Rates:
	// - dorEnErnilHills: 0.5
	// - lindonCoast: 0.5
	// - pelennor: 0.2
	// - erebor: 0.2
	// - woodlandRealmHills: 0.2
	// - nanUngol: 0.5
	// - tauredainClearing: 0.5
	// - dorwinionHills: 0.75
	// - dolGuldur: 0.2
	// - udun: 0.5
	// - rhunLand: 0.75
	// - rhunRedForest: 0.5
	@Override
	public void makeBiomeInformation(Consumer<ExtendedBiomeInformation> pConsumer) {
		make(ExtendedBiomes.BARROW_DOWNS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.BARROW_DOWNS_RIVER.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.GORGOROTH_RIVER.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.MORDOR_RIVER.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.MORGUL_VALE_RIVER.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.NURN_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_CLIFFS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_RIVER.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_VALLEY.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.URUK_HIGHLANDS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.COMMON).save(pConsumer);
		make(LOTRBiomes.SHIRE.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.SHIRE_WOODLANDS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.MORDOR.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.ANORIEN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ROHAN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.WOLD.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.MISTY_MOUNTAINS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.MISTY_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.RIVER.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.TROLLSHAWS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.BLUE_MOUNTAINS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.BLUE_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ERIADOR.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LONE_LANDS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LONE_LANDS_HILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ITHILIEN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.BROWN_LANDS.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LOTHLORIEN.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.LOTHLORIEN_EAVES.getInitialisedBiome()).setConquestRate(0.2f)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.IRON_HILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.DUNLAND.getInitialisedBiome()).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.EMYN_MUIL.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LINDON.getInitialisedBiome()).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.SOUTHRON_COASTS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NAN_CURUNIR.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.FORODWAITH.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.EREGION.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.MIRKWOOD.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.WOODLAND_REALM, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.NORTHERN_MIRKWOOD.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.WOODLAND_REALM, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.MIRKWOOD_MOUNTAINS.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.WOODLAND_REALM, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.GREY_MOUNTAINS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DURINS_FOLK, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.GREY_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DURINS_FOLK, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_MOUNTAINS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.FANGORN.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.FANGORN_WASTELAND.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.WOODLAND_REALM.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.DALE.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.NORTHERN_DALE.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARONDOR.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ENEDWAITH.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.VALES_OF_ANDUIN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ANDUIN_HILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ETHIR_ANDUIN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.WILDERLAND.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.OLD_FOREST.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.BREELAND.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.CHETWOOD.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.MORDOR_MOUNTAINS.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.FORODWAITH_MOUNTAINS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.ANGMAR.getInitialisedBiome()).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LINDON, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANGMAR_MOUNTAINS.getInitialisedBiome()).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LINDON, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.NURN.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.UMBAR.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARAD_DESERT.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LINDON_WOODLANDS.getInitialisedBiome()).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ERIADOR_DOWNS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.NORTHLANDS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.SEA.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.ISLAND.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.BEACH.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.TOLFALAS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.LAKE.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.NURNEN.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.DOR_EN_ERNIL.getInitialisedBiome()).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.EMYN_EN_ERNIL.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.WESTERN_ISLES.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.COLDFELLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ETTENMOORS.getInitialisedBiome()).setConquestRate(0.75f).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.COMMON).save(pConsumer);
		make(LOTRBiomes.HARNENNOR.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.DAGORLAD.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.WHITE_BEACH.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.DORWINION.getInitialisedBiome()).setConquestRate(0.75f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.EMYN_WINION.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.MINHIRIATH.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ERYN_VORN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.DRUWAITH_IAUR.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANDRAST.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LOSSARNACH.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LEBENNIN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.PELARGIR.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LAMEDON.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LAMEDON_HILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.BLACKROOT_VALE.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.PINNATH_GELIN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANFALAS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.NORTHERN_WILDERLAND.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.RIVENDELL.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.RIVENDELL_HILLS.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.FURTHER_GONDOR.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.SHIRE_MOORS.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_DOWNS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.MIDGEWATER.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.SWANFLEET.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.GLADDEN_FIELDS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LONG_MARSHES.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NINDALF.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.DEAD_MARSHES.getInitialisedBiome())
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.MOUTHS_OF_ENTWASH.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SHIRE_MARSHES.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.NURN_MARSHES.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.GORGOROTH.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.FOROCHEL.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.HARAD_HALF_DESERT.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARAD_DESERT_HILLS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LOSTLADEN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.EASTERN_DESOLATION.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.MORGUL_VALE.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.DENSE_NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SNOWY_NORTHLANDS.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SNOWY_NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.DENSE_SNOWY_NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.TOWER_HILLS.getInitialisedBiome()).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SOUTHRON_COASTS_FOREST.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.UMBAR_FOREST.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.UMBAR_HILLS.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.FIELD_OF_CORMALLEN.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HILLS_OF_EVENDIM.getInitialisedBiome()).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
	}
}