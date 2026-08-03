package com.github.maximuslotro.lotrrextended.common.datagen.entity;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lotr.common.fac.FactionPointer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ExtendedEntitySettingsProvider implements IDataProvider {
	public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedEntitySettingsBuilder> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	private final ExistingFileHelper fileHelper;

	public ExtendedEntitySettingsProvider(DataGenerator pDataGenerator, ExistingFileHelper fileHelper) {
		this.dataGenerator = pDataGenerator;
		this.fileHelper = fileHelper;

	}

	public abstract void makeEntitySettingBuilders(Consumer<ExtendedEntitySettingsBuilder> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedEntitySettingsBuilder> consumer = (settings) -> {
			if (dataHere.put(settings.getEntityType().getRegistryName(), settings)!=null) {
				throw new IllegalStateException("Duplicate entity settings " + settings.getEntityType().getRegistryName().toString());
			} else {
				if(settings.speechbank!=null) {
					ResourceLocation speechLoc = new ResourceLocation(settings.speechbank.getNamespace(), "speech/"+settings.speechbank.getPath().replace(".", "/")+"/en_us.json");
					if (!fileHelper.exists(speechLoc, ResourcePackType.CLIENT_RESOURCES)) {
						System.out.println("Missing speechbank "+ speechLoc.toString() +" for: " + settings.getEntityType().getRegistryName().toString());
					}
				}
				Path path1 = createPath(path, settings);

				try {
					IDataProvider.save(GSON, pCache, settings.writeToFile(), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save entity settings {}", path1, ioexception);
				}

			}
		};
		makeEntitySettingBuilders(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" entity settings.");
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
		private int killBonus = 0;
		private ResourceLocation speechbank = null;

		public ExtendedEntitySettingsBuilder(EntityType<?> entityType, FactionPointer factionPointer) {
			this.entityType = entityType;
			this.factionPointer = factionPointer;
		}

		public ExtendedEntitySettingsBuilder setBonus(int pKillBonus) {
			this.killBonus = pKillBonus;
			return this;
		}

		public ExtendedEntitySettingsBuilder setBank(ResourceLocation pSpeechbank) {
			this.speechbank = pSpeechbank;
			return this;
		}

		public ExtendedEntitySettingsBuilder setBank(String pSpeechbankLoc) {
			if(pSpeechbankLoc.contains(":")) {
				this.speechbank = new ResourceLocation(pSpeechbankLoc);
			}else {
				this.speechbank = new ResourceLocation("lotr", pSpeechbankLoc);
			}
			return this;
		}

		public ExtendedEntitySettingsBuilder setBankE(String pSpeechbankLoc) {
			this.speechbank = new ResourceLocation("lotrextended", pSpeechbankLoc);
			return this;
		}

		private EntityType<?> getEntityType() {
			return entityType;
		}

		public JsonElement writeToFile() {
			JsonObject element = new JsonObject();
			element.addProperty("faction", factionPointer.getNameString());
			if(killBonus>0) {
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