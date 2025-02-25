package com.github.maximuslotro.lotrrextended.common.datagen.tags;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * Copied from {@see TagsProvider} to provide more functionality
 * @version Minecraft 1.16.5
 * */
public abstract class ExtendedTagsProvider<T> implements IDataProvider {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	protected final DataGenerator generator;
	protected final Registry<T> registry;
	protected final Map<ResourceLocation, ITag.Builder> builders = Maps.newLinkedHashMap();
	protected final String modId;
	protected final String folder;
	protected final net.minecraftforge.common.data.ExistingFileHelper existingFileHelper;
	private final net.minecraftforge.common.data.ExistingFileHelper.IResourceType resourceType;

	protected ExtendedTagsProvider(DataGenerator pGenerator, Registry<T> pRegistry, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
		this(pGenerator, pRegistry, modId, existingFileHelper, null);
	}
	protected ExtendedTagsProvider(DataGenerator pGenerator, Registry<T> pRegistry, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper, @javax.annotation.Nullable String folder) {
		this.generator = pGenerator;
		this.registry = pRegistry;
		this.modId = modId;
		this.existingFileHelper = existingFileHelper;
		if (folder == null) {//TODO - 1.17: Remove getTagFolder as a method, and inline the default implementation here
			folder = getTagFolder();
		}
		this.folder = folder;
		this.resourceType = new net.minecraftforge.common.data.ExistingFileHelper.ResourceType(net.minecraft.resources.ResourcePackType.SERVER_DATA, ".json", "tags/" + this.folder);
	}

	protected abstract void addTags();

	/**
	 * Performs this provider's action.
	 */
	@Override
	public void run(DirectoryCache pCache) {
		this.builders.clear();
		this.addTags();
		ITag<T> itag = Tag.empty();
		Function<ResourceLocation, ITag<T>> function = (tagLocation) -> {
			return this.builders.containsKey(tagLocation) ? itag : null;
		};
		Function<ResourceLocation, T> function1 = (tagLocation) -> {
			return this.registry.getOptional(tagLocation).orElse((T)null);
		};
		this.builders.forEach((tagLocation, tagBuilder) -> {
			// FORGE: Add validation via existing resources
			List<ITag.Proxy> list = tagBuilder.getUnresolvedEntries(function, function1).filter(this::missing).collect(Collectors.toList());
			if (!list.isEmpty()) {
				throw new IllegalArgumentException(String.format("Couldn't define tag %s as it is missing following references: %s", tagLocation, list.stream().map(Objects::toString).collect(Collectors.joining(","))));
			} else {
				JsonObject jsonobject = formatTags(tagBuilder.serializeToJson());
				Path path = this.getPath(tagLocation);
				if (path == null) return; //Forge: Allow running this data provider without writing it. Recipe provider needs valid tags.

				try {
					String s = GSON.toJson((JsonElement)jsonobject);
					String s1 = SHA1.hashUnencodedChars(s).toString();
					if (!Objects.equals(pCache.getHash(path), s1) || !Files.exists(path)) {
						Files.createDirectories(path.getParent());

						try (BufferedWriter bufferedwriter = Files.newBufferedWriter(path)) {
							bufferedwriter.write(s);
						}
					}

					pCache.putNew(path, s1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save tags to {}", path, ioexception);
				}

			}
		});
	}

	private JsonObject formatTags(JsonObject unfomattedJson) {
		JsonObject formattedJson = new JsonObject();
		formattedJson.addProperty("replace", unfomattedJson.get("replace").getAsBoolean());
		JsonArray values = unfomattedJson.getAsJsonArray("values");
		Map<String, JsonElement> elements = new TreeMap<>(Collator.getInstance());
		for (JsonElement value : values) {
			if(value instanceof JsonPrimitive) {
				elements.put(value.getAsString(), value);
			}
			if(value instanceof JsonObject) {
				JsonObject object = (JsonObject)value;
				if(object.has("id")) {
					elements.put(object.get("id").getAsString(), value);
				}
			}
		}
		JsonArray newArray = new JsonArray();
		for(JsonElement element : elements.values()) {
			newArray.add(element);
		}
		formattedJson.add("values", newArray);
		return formattedJson;
	}

	private boolean missing(ITag.Proxy reference) {
		ITag.ITagEntry entry = reference.getEntry();
		// We only care about non-optional tag entries, this is the only type that can reference a resource and needs validation
		// Optional tags should not be validated
		if (entry instanceof ITag.TagEntry) {
			return existingFileHelper == null || !existingFileHelper.exists(((ITag.TagEntry)entry).getId(), resourceType);
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected String getTagFolder() {
		return ((Registry)Registry.REGISTRY).getKey(registry).getPath() + "s";
	}

	/**
	 * Resolves a Path for the location to save the given tag.
	 */
	protected abstract Path getPath(ResourceLocation pId);

	protected ExtendedTagsProvider.Builder<T> tag(ITag.INamedTag<T> pTag) {
		ITag.Builder itag$builder = this.getOrCreateRawBuilder(pTag);
		return new ExtendedTagsProvider.Builder<>(itag$builder, this.registry, modId);
	}

	protected ITag.Builder getOrCreateRawBuilder(ITag.INamedTag<T> pTag) {
		return this.builders.computeIfAbsent(pTag.getName(), (p_240526_0_) -> {
			existingFileHelper.trackGenerated(p_240526_0_, resourceType);
			return new ITag.Builder();
		});
	}

	public static class Builder<T> implements net.minecraftforge.common.extensions.IForgeTagBuilder<T> {
		private final ITag.Builder builder;
		private final Registry<T> registry;
		private final String source;

		private Builder(ITag.Builder pBuilder, Registry<T> pRegistry, String pSource) {
			this.builder = pBuilder;
			this.registry = pRegistry;
			this.source = pSource;
		}

		public ExtendedTagsProvider.Builder<T> add(T pItem) {
			this.builder.addElement(this.registry.getKey(pItem), this.source);
			return this;
		}

		public ExtendedTagsProvider.Builder<T> addTag(ITag.INamedTag<T> pTag) {
			this.builder.addTag(pTag.getName(), this.source);
			return this;
		}

		@SafeVarargs
		public final ExtendedTagsProvider.Builder<T> add(T... pToAdd) {
			Stream.<T>of(pToAdd).map(this.registry::getKey).forEach((p_240533_1_) -> {
				this.builder.addElement(p_240533_1_, this.source);
			});
			return this;
		}

		public ExtendedTagsProvider.Builder<T> add(ITag.ITagEntry tag) {
			builder.add(tag, source);
			return this;
		}

		public ITag.Builder getInternalBuilder() {
			return builder;
		}

		public String getModID() {
			return source;
		}
	}
}