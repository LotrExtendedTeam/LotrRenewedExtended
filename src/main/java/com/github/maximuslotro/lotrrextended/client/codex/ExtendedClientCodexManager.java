package com.github.maximuslotro.lotrrextended.client.codex;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedFoodCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedLoreCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedMechanicsCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry.ExtendedBiomeCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry.ExtendedBlockCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry.ExtendedDimensionCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry.ExtendedEffectCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry.ExtendedEntityTypeCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry.ExtendedItemCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry.ExtendedStructureCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class ExtendedClientCodexManager implements ISelectiveResourceReloadListener {
	/**
	 * A list of Codex entry serializers/deserializers
	 * */
    public static final Map<ExtendedCodexEntryType, ExtendedBaseSerializer<?>> codexEntryTypes = new HashMap<>();

    static {
		ExtendedClientCodexManager.register(ExtendedFoodCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedLoreCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedMechanicsCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedBiomeCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedBlockCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedDimensionCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedEffectCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedEntityTypeCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedItemCodexEntry.Serializer.INSTANCE);
		ExtendedClientCodexManager.register(ExtendedStructureCodexEntry.Serializer.INSTANCE);
    }

	public ExtendedClientCodexManager() {}

	/**
	 * Proper place to register new codex entry type serializers/deserializers
	 * @param serializer The {@Link ExtendedBaseSerializer} instance to register
	 * */
    public static void register(ExtendedBaseSerializer<?> serializer)
    {
    	ExtendedCodexEntryType key = serializer.getID();
        if (codexEntryTypes.containsKey(key))
            throw new IllegalStateException("Duplicate codex serializer: " + key);
        codexEntryTypes.put(key, serializer);
    }

    @Override
	public void onResourceManagerReload(IResourceManager pResourceManager, Predicate<IResourceType> resourcePredicate) {
		if(!resourcePredicate.test(getResourceType())) {
			return;
		}
		Minecraft mc = Minecraft.getInstance();
		ExtendedClientCodexMap clientlanguagemap = ExtendedClientCodexMap.loadFrom(pResourceManager, mc.getLanguageManager().getSelected().getCode());
		ExtendedClientCodexMap.inject(clientlanguagemap);
	}

	@Override
	public IResourceType getResourceType() {
		return VanillaResourceType.LANGUAGES;
	}
}