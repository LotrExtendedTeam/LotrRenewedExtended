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
	// - dolGuldur: 0.2
	// - udun: 0.5
	// - rhunLand: 0.75
	// - rhunRedForest: 0.5
	@Override
	public void makeBiomeInformation(Consumer<ExtendedBiomeInformation> pConsumer) {
		// Generic
		make(LOTRBiomes.RIVER).save(pConsumer);
		make(LOTRBiomes.SEA).save(pConsumer);
		make(LOTRBiomes.ISLAND).save(pConsumer);
		make(LOTRBiomes.WESTERN_ISLES).save(pConsumer);
		make(LOTRBiomes.BEACH).save(pConsumer);
		make(LOTRBiomes.WHITE_BEACH).save(pConsumer);
		make(LOTRBiomes.LAKE).save(pConsumer);
		// Shire
		ExtendedBiomeInformation shire = make(LOTRBiomes.SHIRE).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.SHIRE_WOODLANDS).parent(shire).save(pConsumer);
		make(LOTRBiomes.SHIRE_MOORS).parent(shire).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_DOWNS).parent(shire).save(pConsumer);
		make(LOTRBiomes.SHIRE_MARSHES).parent(shire).save(pConsumer);
		make(LOTRBiomes.OLD_FOREST).setConquestRate(0).save(pConsumer);
		// Bree
		make(LOTRBiomes.BREELAND).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.CHETWOOD).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		ExtendedBiomeInformation barrowDowns = make(ExtendedBiomes.BARROW_DOWNS).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.BARROW_DOWNS_RIVER).parent(barrowDowns).save(pConsumer);
		make(LOTRBiomes.MIDGEWATER).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		// Rivendell
		ExtendedBiomeInformation rivendell = make(LOTRBiomes.RIVENDELL).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_CLIFFS).parent(rivendell).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_RIVER).parent(rivendell).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_VALLEY).parent(rivendell).save(pConsumer);
		make(LOTRBiomes.RIVENDELL_HILLS).parent(rivendell)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		// Lindon
		ExtendedBiomeInformation lindon = make(LOTRBiomes.LINDON).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LINDON_WOODLANDS).parent(lindon).save(pConsumer);
		// Blue Mountains
		ExtendedBiomeInformation blueMountains = make(LOTRBiomes.BLUE_MOUNTAINS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.BLUE_MOUNTAINS_FOOTHILLS).parent(blueMountains).save(pConsumer);
		// Lone Lands
		ExtendedBiomeInformation loneLands = make(LOTRBiomes.LONE_LANDS).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LONE_LANDS_HILLS).parent(loneLands).save(pConsumer);
		// Eriador
		make(LOTRBiomes.ERIADOR).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ERIADOR_DOWNS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.HILLS_OF_EVENDIM).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.EREGION).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ETTENMOORS).setConquestRate(0.75f).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.COMMON).save(pConsumer);
		make(LOTRBiomes.TROLLSHAWS).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.COLDFELLS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.SWANFLEET).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.TOWER_HILLS).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.MINHIRIATH).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ERYN_VORN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Angmar
		make(LOTRBiomes.ANGMAR).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LINDON, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANGMAR_MOUNTAINS).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LINDON, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		// Dunland
		make(LOTRBiomes.DUNLAND).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.DRUWAITH_IAUR).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ENEDWAITH).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Isengard
		make(LOTRBiomes.NAN_CURUNIR).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.RARE).save(pConsumer);
		// Rohan
		make(LOTRBiomes.ROHAN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.WOLD).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomes.URUK_HIGHLANDS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.COMMON).save(pConsumer);
		// Gondor
		make(LOTRBiomes.ANORIEN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ITHILIEN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		ExtendedBiomeInformation whiteMountains = make(LOTRBiomes.WHITE_MOUNTAINS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_MOUNTAINS_FOOTHILLS).parent(whiteMountains).save(pConsumer);
		make(LOTRBiomes.ANDRAST).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LOSSARNACH).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LEBENNIN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.PELARGIR).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LAMEDON).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.LAMEDON_HILLS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.BLACKROOT_VALE).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.PINNATH_GELIN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANFALAS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.FURTHER_GONDOR).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.TOLFALAS).setBanditChance(ExtendedBanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.DOR_EN_ERNIL).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.EMYN_EN_ERNIL).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		// Fangorn
		make(LOTRBiomes.FANGORN).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.FANGORN_WASTELAND).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Misty Mountains
		ExtendedBiomeInformation mistyMountains = make(LOTRBiomes.MISTY_MOUNTAINS).save(pConsumer);
		make(LOTRBiomes.MISTY_MOUNTAINS_FOOTHILLS).parent(mistyMountains).save(pConsumer);
		// Mordor
		make(LOTRBiomes.MORDOR).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.MORDOR_RIVER).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.MORDOR_MOUNTAINS).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.BROWN_LANDS)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.EMYN_MUIL).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NURNEN).save(pConsumer);
		make(LOTRBiomes.NURN).save(pConsumer);
		make(ExtendedBiomes.NURN_RIVER).save(pConsumer);
		make(LOTRBiomes.NURN_MARSHES).save(pConsumer);
		make(LOTRBiomes.GORGOROTH).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.GORGOROTH_RIVER).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.DAGORLAD)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		ExtendedBiomeInformation morgulVale = make(LOTRBiomes.MORGUL_VALE).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.MORGUL_VALE_RIVER).parent(morgulVale).save(pConsumer);
		// Lothlorien
		ExtendedBiomeInformation lothlorien = make(LOTRBiomes.LOTHLORIEN).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.LOTHLORIEN_EAVES).parent(lothlorien)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		// Mirkwood
		ExtendedBiomeInformation mirkwood = make(LOTRBiomes.MIRKWOOD)
		.addInvasion(ExtendedInvasions.WOODLAND_REALM, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.NORTHERN_MIRKWOOD).parent(mirkwood).save(pConsumer);
		make(LOTRBiomes.MIRKWOOD_MOUNTAINS).parent(mirkwood).save(pConsumer);
		// Dale
		ExtendedBiomeInformation dale = make(LOTRBiomes.DALE).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.NORTHERN_DALE).parent(dale).save(pConsumer);
		// Woodland realm
		make(LOTRBiomes.WOODLAND_REALM).setConquestRate(0.2f).save(pConsumer);
		// Iron Hills
		make(LOTRBiomes.IRON_HILLS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Fordwaith
		make(LOTRBiomes.FORODWAITH).save(pConsumer);
		make(LOTRBiomes.FORODWAITH_MOUNTAINS).save(pConsumer);
		make(LOTRBiomes.FOROCHEL).save(pConsumer);
		// North-lands
		make(LOTRBiomes.NORTHLANDS).setBanditChance(ExtendedBanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.DENSE_NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SNOWY_NORTHLANDS).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SNOWY_NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.DENSE_SNOWY_NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		// Grey Mountains
		make(LOTRBiomes.GREY_MOUNTAINS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DURINS_FOLK, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.GREY_MOUNTAINS_FOOTHILLS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DURINS_FOLK, ExtendedInvasionChance.RARE).save(pConsumer);
		// Wilderland
		make(LOTRBiomes.WILDERLAND).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.NORTHERN_WILDERLAND).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		// Dorwinion
		make(LOTRBiomes.DORWINION).setConquestRate(0.75f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.EMYN_WINION).setConquestRate(0.75f)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		// Anduin
		make(LOTRBiomes.VALES_OF_ANDUIN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ANDUIN_HILLS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.GLADDEN_FIELDS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LONG_MARSHES).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NINDALF).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.DEAD_MARSHES)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.MOUTHS_OF_ENTWASH).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.FIELD_OF_CORMALLEN).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ETHIR_ANDUIN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Harad
		make(LOTRBiomes.SOUTHRON_COASTS).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SOUTHRON_COASTS_FOREST).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARONDOR).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARNENNOR).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARAD_DESERT).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARAD_HALF_DESERT).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARAD_DESERT_HILLS).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LOSTLADEN).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.EASTERN_DESOLATION).setConquestRate(0.5f).save(pConsumer);
		// Umbar
		ExtendedBiomeInformation umbar = make(LOTRBiomes.UMBAR).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.UMBAR_FOREST).parent(umbar).save(pConsumer);
		make(LOTRBiomes.UMBAR_HILLS).parent(umbar).save(pConsumer);
	}
}