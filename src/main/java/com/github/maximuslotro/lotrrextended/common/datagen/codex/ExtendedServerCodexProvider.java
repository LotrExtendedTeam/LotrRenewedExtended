package com.github.maximuslotro.lotrrextended.common.datagen.codex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedCodexEntry;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ExtendedServerCodexProvider implements IDataProvider {
	private static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON_PRETTY_WRITER = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	private final Map<ResourceLocation, ExtendedCodexEntry> dataHere = new TreeMap<>();
	private final DataGenerator generator;
	protected ExistingFileHelper fileHelper;

	public ExtendedServerCodexProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
		this.generator = generatorIn;
		this.fileHelper = fileHelperIn;
	}

	/**
	 * Performs this provider's action.
	 */
	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.generator.getOutputFolder();
		Consumer<ExtendedCodexEntry> consumer = (codexEntry) -> {
			if (dataHere.put(codexEntry.getId(), codexEntry)!=null) {
				throw new IllegalStateException("Duplicate server codex entry " + codexEntry.getId());
			} else {
				Path path1 = createPath(path, codexEntry);

				try {
					IDataProvider.save(GSON_PRETTY_WRITER, pCache, ExtendedCodexEntry.serialize(codexEntry), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save server codex entries {}", path1, ioexception);
				}

			}
		};
		registerServerCodexEntries(consumer, fileHelper);
		ExtendedLog.info("Built "+dataHere.size()+" server codex entries.");
	}

	/**
	 * Method for registering and generating custom {@link ExtendedCodexEntry}s. <p>
	 * @param consumer used for the register function from {@link Advancement.Builder}
	 * @param fileHelper used for the register function from {@link Advancement.Builder}
	 */
	public abstract void registerServerCodexEntries(Consumer<ExtendedCodexEntry> consumer, ExistingFileHelper fileHelper);

	private static Path createPath(Path pPath, ExtendedCodexEntry pAdvancement) {
		return pPath.resolve("data/" + pAdvancement.getId().getNamespace() + "/codex/" + pAdvancement.getId().getPath()+".json");
	}

	/**
	 * Gets a name for this provider, to use in logging.
	 */
	public String getName() {
		return "Renewed Extended Server Codex Provider";
	}


	public ExtendedCodexEntry.Builder make(ExtendedCodexEntryType type) {
		return new ExtendedCodexEntry.Builder(type);
	}
}