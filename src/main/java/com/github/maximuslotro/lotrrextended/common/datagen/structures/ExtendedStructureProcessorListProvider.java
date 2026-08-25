package com.github.maximuslotro.lotrrextended.common.datagen.structures;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ExtendedStructureProcessorListProvider implements IDataProvider {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	private final DataGenerator generator;
	protected final String modId;

	private final Map<ResourceLocation, StructureProcessorList> processors = new HashMap<>();

	public ExtendedStructureProcessorListProvider(DataGenerator pGenerator, String pModId) {
		this.generator = pGenerator;
		this.modId = pModId;
	}

	public abstract void registerProcessors();

	protected void add(String id, StructureProcessorList processorList) {
		ResourceLocation resLocation = new ResourceLocation(modId, id);
		if (this.processors.put(resLocation, processorList) != null) {
			throw new IllegalStateException("Duplicate structure processor list " + resLocation);
		}
	}

	protected void add(String id, ExtendedStructureProcessorListBuilder builder) {
		add(id, builder.build());
	}

	protected void add(String id, Consumer<ExtendedStructureProcessorListBuilder> consumer) {
		ExtendedStructureProcessorListBuilder builder = ExtendedStructureProcessorListBuilder.create();
		consumer.accept(builder);
		add(id, builder);
	}

	protected void addReplacement(String id, Block targetBlock, float chance, Consumer<ExtendedStructureProcessorListBuilder.WeightedBlockStateBuilder> weightConsumer) {
		add(id, builder -> builder.addReplacementProcessor(targetBlock, chance, weightConsumer));
	}

	@Override
	public void run(DirectoryCache cache) throws IOException {
		this.registerProcessors();
		Path outputFolder = this.generator.getOutputFolder();
		for (Map.Entry<ResourceLocation, StructureProcessorList> entry : this.processors.entrySet()) {
			ResourceLocation id = entry.getKey();
			StructureProcessorList list = entry.getValue();

			Path path = createPath(outputFolder, id);

			DataResult<JsonElement> result = IStructureProcessorType.DIRECT_CODEC.encodeStart(JsonOps.INSTANCE, list);
			JsonElement json = result.resultOrPartial(err -> {
				throw new IllegalStateException("Failed to encode processor list [" + id + "]: " + err);
			}).orElseThrow(IllegalStateException::new);

			try {
				IDataProvider.save(GSON, cache, json, path);
			} catch (IOException ioexception) {
				LOGGER.error("Couldn't save structure processor list {}", path, ioexception);
			}
		}
		ExtendedLog.info("Built "+processors.size()+" structure processors.");
	}
	private static Path createPath(Path outputFolder, ResourceLocation id) {
		return outputFolder.resolve(String.format("data/%s/worldgen/processor_list/%s.json", id.getNamespace(), id.getPath()));
	}
	@Override
	public String getName() {
		return "Extended Structure Processor Provider";
	}

}
