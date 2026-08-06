package com.github.maximuslotro.lotrrextended.common.datagen.map;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ExtendedMapWaypointProvider implements IDataProvider {
	public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedMapWaypointBuilder> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	private final ExistingFileHelper fileHelper;
	protected final String modId;

	public ExtendedMapWaypointProvider(DataGenerator pDataGenerator, ExistingFileHelper pFileHelper, String pModId) {
		this.dataGenerator = pDataGenerator;
		fileHelper = pFileHelper;
		this.modId = pModId;
	}

	public abstract void makeWaypoints(Consumer<ExtendedMapWaypointBuilder> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedMapWaypointBuilder> consumer = (waypoint) -> {
			if (dataHere.put(waypoint.getId(), waypoint)!=null) {
				throw new IllegalStateException("Duplicate trader profile " + waypoint.getId().toString());
			} else {
				Path path1 = createPath(path, waypoint);
				ResourceLocation waypointRegion = new ResourceLocation(waypoint.travelRegion.getNamespace(), "map/waypoints/regions/"+waypoint.travelRegion.getPath()+".json");
				if (!fileHelper.exists(waypointRegion, ResourcePackType.SERVER_DATA)) {
					System.out.println("Missing waypoint region file "+ waypointRegion.toString() +" for: " + waypoint.getId().toString());
				}
				try {
					IDataProvider.save(GSON, pCache, waypoint.serializeWaypoint(), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save trader profile {}", path1, ioexception);
				}

			}
		};
		makeWaypoints(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" waypoints.");
	}

	private Path createPath(Path pPath, ExtendedMapWaypointBuilder profile) {
		return pPath.resolve("data/" +profile.getId().getNamespace()+ "/map/waypoints/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Map Waypoint Provider";
	}

	public ExtendedMapWaypointBuilder make(String pId, int pX, int pZ) {
		return make(new ResourceLocation(modId, pId), pX, pZ);
	}

	public ExtendedMapWaypointBuilder make(ResourceLocation pId, int pX, int pZ) {
		return new ExtendedMapWaypointBuilder(pId, pX, pZ);
	}
}
