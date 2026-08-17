package com.github.maximuslotro.lotrrextended.common.datagen;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedStructureValidator implements IDataProvider {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().create();

	private final DataGenerator generator;
	private final ExistingFileHelper fileHelper;
	private final String modId;

	public ExtendedStructureValidator(DataGenerator generator, ExistingFileHelper fileHelper, String modId) {
		this.generator = generator;
		this.fileHelper = fileHelper;
		this.modId = modId;
	}

	@Override
	public void run(DirectoryCache cache) {
		Path generatedResources = this.generator.getOutputFolder();
		Path mainResources = generatedResources.getParent().getParent().resolve("main/resources");
		List<Path> searchRoots = Arrays.asList(mainResources);//, generatedResources);
		for (Path rootPath : searchRoots) {
			Path templatePoolDir = rootPath.resolve("data").resolve(modId).resolve("worldgen").resolve("template_pool");
			if (!Files.exists(templatePoolDir)) {
				LOGGER.warn("Template pool directory does not exist: {}", templatePoolDir);
				continue;
			}
			try (Stream<Path> stream = Files.walk(templatePoolDir)) {
				stream.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".json"))
				.forEach(filePath -> validateTemplatePool(templatePoolDir, filePath));
			} catch (Exception e) {
				LOGGER.error("Failed to walk template pool directory at: {}", templatePoolDir, e);
			}
		}
	}

	private void validateTemplatePool(Path baseDir, Path filePath) {
		Path relativePath = baseDir.relativize(filePath);
		String shorthandPath = relativePath.toString().replace('\\', '/').replaceAll("\\.json$", "");
		ResourceLocation expectedResourceLoc = new ResourceLocation(modId, shorthandPath);
		try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(filePath))) {
			JsonObject jsonObject = GSON.fromJson(reader, JsonObject.class);
			if (jsonObject == null) {
				return;
			}
			if (jsonObject.has("name")) {
				String nameInJson = jsonObject.get("name").getAsString();
				if (!nameInJson.equals(expectedResourceLoc.toString())) {
					LOGGER.error("[Template Pool Error] Mismatched 'name' property in file {}. Expected: '{}', Found: '{}'", filePath.getFileName(), expectedResourceLoc, nameInJson);
				}
			} else {
				LOGGER.error("[Template Pool Error] Missing 'name' field in template pool: {}", filePath);
			}
			if (jsonObject.has("fallback")) {
                String fallbackStr = jsonObject.get("fallback").getAsString();
                if (!"minecraft:empty".equals(fallbackStr)) {
                    ResourceLocation fallbackLoc = new ResourceLocation(fallbackStr);
                    boolean fallbackExists = this.fileHelper.exists(fallbackLoc, ResourcePackType.SERVER_DATA, ".json", "worldgen/template_pool");
                    if (!fallbackExists) {
                        LOGGER.error("[Template Pool Error] Invalid fallback pool location '{}' referenced in pool file '{}'", fallbackStr, filePath.getFileName());
                    }
                }
            } else {
                LOGGER.error("[Template Pool Error] Missing 'fallback' field in template pool: {}", filePath.getFileName());
            }
			if (jsonObject.has("elements") && jsonObject.get("elements").isJsonArray()) {
				JsonArray elements = jsonObject.getAsJsonArray("elements");
				for (int i = 0; i < elements.size(); i++) {
					JsonObject entry = elements.get(i).getAsJsonObject();
					if (!entry.has("element")) {
						continue;
					}
					JsonObject element = entry.getAsJsonObject("element");
					if (element.has("location")) {
						String locationStr = element.get("location").getAsString();
						if (!this.fileHelper.exists(new ResourceLocation(locationStr), ResourcePackType.SERVER_DATA, ".nbt", "structures")) {
							LOGGER.error("[Template Pool Error] Invalid element piece location '{}' in pool '{}' (Element index {})", locationStr, filePath.getFileName(), i);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Failed to validate template pool file: {}", filePath, e);
		}
	}

	@Override
	public String getName() {
		return "Extended Template Pool and Piece Location Validator";
	}
}
