package com.github.maximuslotro.lotrrextended.common.datagen.codex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.client.codex.ExtendedClientCodexMap;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ExtendedClientCodexProvider implements IDataProvider {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	private final DataGenerator generator;
	protected ExistingFileHelper fileHelper;
	private final ExtendedServerCodexEntryGenerator serverCodex;

	public ExtendedClientCodexProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn, ExtendedServerCodexEntryGenerator serverCodex) {
		this.generator = generatorIn;
		this.fileHelper = fileHelperIn;
		this.serverCodex = serverCodex;
	}

	/**
	 * Performs this provider's action.
	 */
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.generator.getOutputFolder();
		Set<ResourceLocation> set = Sets.newHashSet();
		Consumer<ExtendedBaseCodexEntry> consumer = (codexEntry) -> {
			if (!set.add(codexEntry.getLocationID())) {
				throw new IllegalStateException("Duplicate client codex entry " + codexEntry.getLocationID());
			} else {
				Path path1 = createPath(path, codexEntry);

				try {
					IDataProvider.save(GSON, pCache, ExtendedClientCodexMap.serialize(codexEntry), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save client codex entry {}", path1, ioexception);
				}

			}
		};

		registerClientCodexEntries(consumer, fileHelper);
		ExtendedLog.info("Built 0 server codex entries.");
	}

	/**
	 * Method for registering and generating custom {@link ExtendedBaseCodexEntry}s. <p>
	 * @param consumer used for the register function from {@link Advancement.Builder}
	 * @param fileHelper used for the register function from {@link Advancement.Builder}
	 */
	public abstract void registerClientCodexEntries(Consumer<ExtendedBaseCodexEntry> consumer, ExistingFileHelper fileHelper);

	private static Path createPath(Path pPath, ExtendedBaseCodexEntry pAdvancement) {
		return pPath.resolve("assets/" + pAdvancement.getLocationID().getNamespace() + "/codex/" + pAdvancement.getLocationID().getPath() + "_en_us.json");
	}

	/**
	 * Gets a name for this provider, to use in logging.
	 */
	public String getName() {
		return "Renewed Extended Client Codex Provider";
	}
}