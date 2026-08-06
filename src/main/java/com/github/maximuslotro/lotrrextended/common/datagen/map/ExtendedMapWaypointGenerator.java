package com.github.maximuslotro.lotrrextended.common.datagen.map;

import java.util.function.Consumer;

import lotr.common.fac.FactionPointers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedMapWaypointGenerator extends ExtendedMapWaypointProvider {

	public ExtendedMapWaypointGenerator(DataGenerator pDataGenerator, ExistingFileHelper pFileHelper, String pModId) {
		super(pDataGenerator, pFileHelper, pModId);
	}

	@Override
	public void makeWaypoints(Consumer<ExtendedMapWaypointBuilder> pConsumer) {
		make("uruk_highlands", 1147, 1059).setRegion(ExtendedTravelRegions.ROHAN).setFaction(FactionPointers.ISENGARD).save(pConsumer);
	}
}