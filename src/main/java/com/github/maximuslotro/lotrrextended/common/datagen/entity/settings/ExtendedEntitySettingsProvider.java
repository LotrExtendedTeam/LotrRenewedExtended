package com.github.maximuslotro.lotrrextended.common.datagen.entity.settings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lotr.common.fac.FactionPointer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedEntitySettingsProvider implements IDataProvider {
	public static final Gson GSON_PRETTY_WRITER = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedEntitySettingsBuilder> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;

	public ExtendedEntitySettingsProvider(DataGenerator pDataGenerator) {
		this.dataGenerator = pDataGenerator;
	}

	public abstract void makeEntitySettingBuilders(Consumer<ExtendedEntitySettingsBuilder> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedEntitySettingsBuilder> consumer = (settings) -> {
	         if (dataHere.put(settings.getEntityType().getRegistryName(), settings)!=null) {
	            throw new IllegalStateException("Duplicate entity settings " + settings.getEntityType().getRegistryName().toString());
	         } else {
	            Path path1 = createPath(path, settings);

	            try {
	               save(GSON_PRETTY_WRITER, pCache, settings, path1);
	            } catch (IOException ioexception) {
	               LOGGER.error("Couldn't save entity settings {}", path1, ioexception);
	            }

	         }
	      };
		makeEntitySettingBuilders(consumer);
		ExtendedLog.infoShown("Built "+dataHere.size()+" entity settings.");
	}

	private static void save(Gson pGson, DirectoryCache pCache, ExtendedEntitySettingsBuilder settings, Path pPath) throws IOException {
		try {
			String s = pGson.toJson(settings.writeToFile());
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
				Files.createDirectories(pPath.getParent());
				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
					bufferedwriter.write(s);
				}
			}

			pCache.putNew(pPath, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save entity settings {}", pPath, ioexception);
		}
	}

	private static Path createPath(Path pPath, ExtendedEntitySettingsBuilder settings) {
		return pPath.resolve("data/" + settings.getEntityType().getRegistryName().getNamespace()+ "/npcs/entity_settings/" + settings.getEntityType().getRegistryName().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Entity Settings Provider";
	}

	public ExtendedEntitySettingsBuilder make(EntityType<?> entityType, FactionPointer fPointer) {
		return new ExtendedEntitySettingsBuilder(entityType, fPointer);
	}

	protected static class ExtendedEntitySettingsBuilder {
		private final EntityType<?> entityType;
		private final FactionPointer factionPointer;
		private float killBonus = 0.0f;
		private ResourceLocation speechbank = null;

		public ExtendedEntitySettingsBuilder(EntityType<?> entityType, FactionPointer factionPointer) {
			this.entityType = entityType;
			this.factionPointer = factionPointer;
		}

		public ExtendedEntitySettingsBuilder setBonus(float pKillBonus) {
			this.killBonus = pKillBonus;
			return this;
		}

		public ExtendedEntitySettingsBuilder setBank(ResourceLocation pSpeechbank) {
			this.speechbank = pSpeechbank;
			return this;
		}

		public ExtendedEntitySettingsBuilder setBank(String pSpeechbankLoc) {
			this.speechbank = new ResourceLocation("lotr", pSpeechbankLoc);
			return this;
		}

		private EntityType<?> getEntityType() {
			return entityType;
		}

		public JsonElement writeToFile() {
			JsonObject element = new JsonObject();
			element.addProperty("faction", factionPointer.getNameString());
			if(killBonus>0.0f) {
				element.addProperty("kill_bonus", killBonus);
			}
			if(speechbank!=null) {
				element.addProperty("speechbank", speechbank.toString());
			}
			return element;
		}

		public void save(Consumer<ExtendedEntitySettingsBuilder> pConsumer) {
			pConsumer.accept(this);
		}
	}
}