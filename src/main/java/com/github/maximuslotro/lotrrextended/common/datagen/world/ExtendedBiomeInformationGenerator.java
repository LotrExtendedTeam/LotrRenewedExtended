package com.github.maximuslotro.lotrrextended.common.datagen.world;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.enums.BanditChance;
import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedBiomeInformation;

import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import net.minecraft.data.DataGenerator;

public class ExtendedBiomeInformationGenerator extends ExtendedBiomeInformationProvider{

	public ExtendedBiomeInformationGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}
	// Unported Biomes & Conquest Gain Rates:
	// - dorEnErnil: 0.5
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
		make(ExtendedBiomes.FANGORN_WASTELAND.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(ExtendedBiomes.GORGOROTH_RIVER.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.MORDOR_RIVER.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.MORGUL_VALE_RIVER.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(ExtendedBiomes.NURN_RIVER.getInitialisedBiome()).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_CLIFFS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_RIVER.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.RIVENDELL_VALLEY.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(ExtendedBiomes.URUK_HIGHLANDS.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.SHIRE.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.MORDOR.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.ANORIEN.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ROHAN.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.MISTY_MOUNTAINS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.SHIRE_WOODLANDS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.RIVER.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.TROLLSHAWS.getInitialisedBiome()).setBanditChance(BanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.BLUE_MOUNTAINS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ERIADOR.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LONE_LANDS.getInitialisedBiome()).setBanditChance(BanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.ITHILIEN.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.BROWN_LANDS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.LOTHLORIEN.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.IRON_HILLS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.DUNLAND.getInitialisedBiome()).setConquestRate(0.5f).setBanditChance(BanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.EMYN_MUIL.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LINDON.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.SOUTHRON_COASTS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NAN_CURUNIR.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.FORODWAITH.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.EREGION.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.MIRKWOOD.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.GREY_MOUNTAINS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_MOUNTAINS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.FANGORN.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.WOODLAND_REALM.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.DALE.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANGMAR.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.HARONDOR.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ENEDWAITH.getInitialisedBiome()).setBanditChance(BanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.VALES_OF_ANDUIN.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ANDUIN_HILLS.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.WILDERLAND.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.OLD_FOREST.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.BREELAND.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.CHETWOOD.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.MISTY_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.BLUE_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.GREY_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_MOUNTAINS_FOOTHILLS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.MORDOR_MOUNTAINS.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.FORODWAITH_MOUNTAINS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.ANGMAR_MOUNTAINS.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.NURN.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.UMBAR.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARAD_DESERT.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LINDON_WOODLANDS.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.ERIADOR_DOWNS.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LONE_LANDS_HILLS.getInitialisedBiome()).setBanditChance(BanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.NORTHLANDS.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.SEA.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.ISLAND.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.BEACH.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.TOLFALAS.getInitialisedBiome()).setBanditChance(BanditChance.COMMON).save(pConsumer);
		make(LOTRBiomes.LAKE.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.NURNEN.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.DOR_EN_ERNIL.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.EMYN_EN_ERNIL.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.WESTERN_ISLES.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.COLDFELLS.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ETTENMOORS.getInitialisedBiome()).setConquestRate(0.75f).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.HARNENNOR.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.DAGORLAD.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.WHITE_BEACH.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.DORWINION.getInitialisedBiome()).setConquestRate(0.75f).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.EMYN_WINION.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.WOLD.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.MINHIRIATH.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.ERYN_VORN.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.DRUWAITH_IAUR.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANDRAST.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LOSSARNACH.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LEBENNIN.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.PELARGIR.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LAMEDON.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LAMEDON_HILLS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.BLACKROOT_VALE.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.PINNATH_GELIN.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ANFALAS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NORTHERN_WILDERLAND.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NORTHERN_DALE.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.RIVENDELL.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.RIVENDELL_HILLS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.FURTHER_GONDOR.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SHIRE_MOORS.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.WHITE_DOWNS.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.MIDGEWATER.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.SWANFLEET.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.GLADDEN_FIELDS.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.LONG_MARSHES.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.NINDALF.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.DEAD_MARSHES.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.MOUTHS_OF_ENTWASH.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.ETHIR_ANDUIN.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
		make(LOTRBiomes.SHIRE_MARSHES.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.NURN_MARSHES.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.GORGOROTH.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.FOROCHEL.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.LOTHLORIEN_EAVES.getInitialisedBiome()).setConquestRate(0.2f).save(pConsumer);
		make(LOTRBiomes.HARAD_HALF_DESERT.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HARAD_DESERT_HILLS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.LOSTLADEN.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.EASTERN_DESOLATION.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.NORTHERN_MIRKWOOD.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.MIRKWOOD_MOUNTAINS.getInitialisedBiome()).save(pConsumer);
		make(LOTRBiomes.MORGUL_VALE.getInitialisedBiome()).setConquestRate(0.5f).save(pConsumer);
		make(LOTRBiomes.DENSE_NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SNOWY_NORTHLANDS.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SNOWY_NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.DENSE_SNOWY_NORTHLANDS_FOREST.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.TOWER_HILLS.getInitialisedBiome()).setConquestRate(0.5f).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.SOUTHRON_COASTS_FOREST.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.UMBAR_FOREST.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.UMBAR_HILLS.getInitialisedBiome()).setConquestRate(0.2f).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.FIELD_OF_CORMALLEN.getInitialisedBiome()).setBanditChance(BanditChance.RARE).save(pConsumer);
		make(LOTRBiomes.HILLS_OF_EVENDIM.getInitialisedBiome()).setBanditChance(BanditChance.UNCOMMON).save(pConsumer);
	}
}