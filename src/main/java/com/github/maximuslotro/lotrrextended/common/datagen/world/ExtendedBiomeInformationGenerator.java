package com.github.maximuslotro.lotrrextended.common.datagen.world;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBanditChance;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedInvasionChance;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBiomePointers;
import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedBiomeInformation;

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
		make(ExtendedBiomePointers.RIVER).save(pConsumer);
		make(ExtendedBiomePointers.SEA).save(pConsumer);
		make(ExtendedBiomePointers.ISLAND).save(pConsumer);
		make(ExtendedBiomePointers.WESTERN_ISLES).save(pConsumer);
		make(ExtendedBiomePointers.BEACH).save(pConsumer);
		make(ExtendedBiomePointers.WHITE_BEACH).save(pConsumer);
		make(ExtendedBiomePointers.LAKE).save(pConsumer);
		// Shire
		ExtendedBiomeInformation shire = make(ExtendedBiomePointers.SHIRE).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomePointers.SHIRE_WOODLANDS).parent(shire).save(pConsumer);
		make(ExtendedBiomePointers.SHIRE_MOORS).parent(shire).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.WHITE_DOWNS).parent(shire).save(pConsumer);
		make(ExtendedBiomePointers.SHIRE_MARSHES).parent(shire).save(pConsumer);
		make(ExtendedBiomePointers.OLD_FOREST).setConquestRate(0).save(pConsumer);
		// Bree
		ExtendedBiomeInformation bree = make(ExtendedBiomePointers.BREELAND).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.CHETWOOD).parent(bree).setBanditChance(ExtendedBanditChance.UNCOMMON).save(pConsumer);
		// Barrow downs
		ExtendedBiomeInformation barrowDowns = make(ExtendedBiomePointers.BARROW_DOWNS).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomePointers.BARROW_DOWNS_RIVER).parent(barrowDowns).save(pConsumer);
		// Rivendell
		ExtendedBiomeInformation rivendell = make(ExtendedBiomePointers.RIVENDELL).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomePointers.RIVENDELL_CLIFFS).parent(rivendell).save(pConsumer);
		make(ExtendedBiomePointers.RIVENDELL_RIVER).parent(rivendell).save(pConsumer);
		make(ExtendedBiomePointers.RIVENDELL_VALLEY).parent(rivendell).save(pConsumer);
		make(ExtendedBiomePointers.RIVENDELL_HILLS).parent(rivendell)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		// Lindon
		ExtendedBiomeInformation lindon = make(ExtendedBiomePointers.LINDON).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.LINDON_WOODLANDS).parent(lindon).save(pConsumer);
		// Blue Mountains
		ExtendedBiomeInformation blueMountains = make(ExtendedBiomePointers.BLUE_MOUNTAINS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.BLUE_MOUNTAINS_FOOTHILLS).parent(blueMountains).save(pConsumer);
		// Lone Lands
		ExtendedBiomeInformation loneLands = make(ExtendedBiomePointers.LONE_LANDS).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.LONE_LANDS_HILLS).parent(loneLands).save(pConsumer);
		// Eriador
		make(ExtendedBiomePointers.ERIADOR).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.ERIADOR_DOWNS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.HILLS_OF_EVENDIM).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.EREGION).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.ETTENMOORS).setConquestRate(0.75f).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.COMMON).save(pConsumer);
		make(ExtendedBiomePointers.TROLLSHAWS).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.COLDFELLS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.MIDGEWATER).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.SWANFLEET).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.TOWER_HILLS).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.MINHIRIATH).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.ERYN_VORN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Angmar
		make(ExtendedBiomePointers.ANGMAR).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LINDON, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.ANGMAR_MOUNTAINS).setConquestRate(0.5f)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LINDON, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.RIVENDELL, ExtendedInvasionChance.RARE).save(pConsumer);
		// Dunland
		make(ExtendedBiomePointers.DUNLAND).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.DRUWAITH_IAUR).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.ENEDWAITH).setBanditChance(ExtendedBanditChance.COMMON)
		.addInvasion(ExtendedInvasions.DUNEDAIN, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Isengard
		make(ExtendedBiomePointers.NAN_CURUNIR).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.RARE).save(pConsumer);
		// Rohan
		make(ExtendedBiomePointers.ROHAN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.WOLD).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DUNLAND, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.URUK_HIGHLANDS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.COMMON).save(pConsumer);
		// Gondor
		make(ExtendedBiomePointers.ANORIEN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.ITHILIEN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.COMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		ExtendedBiomeInformation whiteMountains = make(ExtendedBiomePointers.WHITE_MOUNTAINS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.WHITE_MOUNTAINS_FOOTHILLS).parent(whiteMountains).save(pConsumer);
		make(ExtendedBiomePointers.ANDRAST).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.LOSSARNACH).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.LEBENNIN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.PELARGIR).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.LAMEDON).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.LAMEDON_HILLS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.BLACKROOT_VALE).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.PINNATH_GELIN).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.ANFALAS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.FURTHER_GONDOR).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.TOLFALAS).setBanditChance(ExtendedBanditChance.COMMON).save(pConsumer);
		make(ExtendedBiomePointers.DOR_EN_ERNIL).setConquestRate(0.5f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.EMYN_EN_ERNIL).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		// Fangorn
		make(ExtendedBiomePointers.FANGORN).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomePointers.FANGORN_WASTELAND).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ISENGARD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.ROHAN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Misty Mountains
		ExtendedBiomeInformation mistyMountains = make(ExtendedBiomePointers.MISTY_MOUNTAINS).save(pConsumer);
		make(ExtendedBiomePointers.MISTY_MOUNTAINS_FOOTHILLS).parent(mistyMountains).save(pConsumer);
		// Mordor
		make(ExtendedBiomePointers.MORDOR).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomePointers.MORDOR_RIVER).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomePointers.MORDOR_MOUNTAINS).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomePointers.BROWN_LANDS)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.EMYN_MUIL).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.NURNEN).save(pConsumer);
		make(ExtendedBiomePointers.NURN).save(pConsumer);
		make(ExtendedBiomePointers.NURN_RIVER).save(pConsumer);
		make(ExtendedBiomePointers.NURN_MARSHES).save(pConsumer);
		make(ExtendedBiomePointers.GORGOROTH).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomePointers.GORGOROTH_RIVER).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomePointers.DAGORLAD)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		ExtendedBiomeInformation morgulVale = make(ExtendedBiomePointers.MORGUL_VALE).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomePointers.MORGUL_VALE_RIVER).parent(morgulVale).save(pConsumer);
		// Lothlorien
		ExtendedBiomeInformation lothlorien = make(ExtendedBiomePointers.LOTHLORIEN).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomePointers.LOTHLORIEN_EAVES).parent(lothlorien)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.RARE).save(pConsumer);
		// Mirkwood
		ExtendedBiomeInformation mirkwood = make(ExtendedBiomePointers.MIRKWOOD)
		.addInvasion(ExtendedInvasions.WOODLAND_REALM, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.NORTHERN_MIRKWOOD).parent(mirkwood).save(pConsumer);
		make(ExtendedBiomePointers.MIRKWOOD_MOUNTAINS).parent(mirkwood).save(pConsumer);
		// Dale
		ExtendedBiomeInformation dale = make(ExtendedBiomePointers.DALE).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.NORTHERN_DALE).parent(dale).save(pConsumer);
		// Woodland realm
		make(ExtendedBiomePointers.WOODLAND_REALM).setConquestRate(0.2f).save(pConsumer);
		// Iron Hills
		make(ExtendedBiomePointers.IRON_HILLS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Fordwaith
		make(ExtendedBiomePointers.FORODWAITH).save(pConsumer);
		make(ExtendedBiomePointers.FORODWAITH_MOUNTAINS).save(pConsumer);
		make(ExtendedBiomePointers.FOROCHEL).save(pConsumer);
		// North-lands
		make(ExtendedBiomePointers.NORTHLANDS).setBanditChance(ExtendedBanditChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.DENSE_NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.SNOWY_NORTHLANDS).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.SNOWY_NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.DENSE_SNOWY_NORTHLANDS_FOREST).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		// Grey Mountains
		make(ExtendedBiomePointers.GREY_MOUNTAINS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DURINS_FOLK, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.GREY_MOUNTAINS_FOOTHILLS).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.DURINS_FOLK, ExtendedInvasionChance.RARE).save(pConsumer);
		// Wilderland
		make(ExtendedBiomePointers.WILDERLAND).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.NORTHERN_WILDERLAND).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		// Dorwinion
		make(ExtendedBiomePointers.DORWINION).setConquestRate(0.75f).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.EMYN_WINION).setConquestRate(0.75f)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		// Anduin
		make(ExtendedBiomePointers.VALES_OF_ANDUIN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.ANDUIN_HILLS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.GLADDEN_FIELDS).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD_WARGS, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomePointers.LONG_MARSHES).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.NINDALF).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.DEAD_MARSHES)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.MOUTHS_OF_ENTWASH).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.FIELD_OF_CORMALLEN).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.ETHIR_ANDUIN).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GUNDABAD, ExtendedInvasionChance.RARE)
		.addInvasion(ExtendedInvasions.LOTHLORIEN, ExtendedInvasionChance.UNCOMMON).save(pConsumer);
		// Harad
		make(ExtendedBiomePointers.SOUTHRON_COASTS).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.SOUTHRON_COASTS_FOREST).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.HARONDOR).setBanditChance(ExtendedBanditChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.HARNENNOR).setBanditChance(ExtendedBanditChance.RARE)
		.addInvasion(ExtendedInvasions.MORDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.GONDOR, ExtendedInvasionChance.UNCOMMON)
		.addInvasion(ExtendedInvasions.UMBAR, ExtendedInvasionChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.HARAD_DESERT).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.HARAD_HALF_DESERT).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.HARAD_DESERT_HILLS).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.LOSTLADEN).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.EASTERN_DESOLATION).setConquestRate(0.5f).save(pConsumer);
		// Umbar
		ExtendedBiomeInformation umbar = make(ExtendedBiomePointers.UMBAR).setConquestRate(0.2f).setBanditChance(ExtendedBanditChance.RARE).save(pConsumer);
		make(ExtendedBiomePointers.UMBAR_FOREST).parent(umbar).save(pConsumer);
		make(ExtendedBiomePointers.UMBAR_HILLS).parent(umbar).save(pConsumer);
	}
}