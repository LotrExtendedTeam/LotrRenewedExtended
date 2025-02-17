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

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class ExtendedCodexManager implements ISelectiveResourceReloadListener {
    public static final Map<String, ExtendedBaseSerializer<?>> codexEntryTypes = new HashMap<>();

    static {
		ExtendedCodexManager.register(ExtendedFoodCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedLoreCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedMechanicsCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedBiomeCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedBlockCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedDimensionCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedEffectCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedEntityTypeCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedItemCodexEntry.Serializer.INSTANCE);
		ExtendedCodexManager.register(ExtendedStructureCodexEntry.Serializer.INSTANCE);
    }

	public ExtendedCodexManager() {}

    public static ExtendedBaseSerializer<?> register(ExtendedBaseSerializer<?> serializer)
    {
        String key = serializer.getID();
        if (codexEntryTypes.containsKey(key))
            throw new IllegalStateException("Duplicate codex serializer: " + key);
        codexEntryTypes.put(key, serializer);
        return serializer;
    }

	public void onResourceManagerReload(IResourceManager pResourceManager, Predicate<IResourceType> resourcePredicate) {
		if(!resourcePredicate.test(getResourceType())) {
			return;
		}
		Minecraft mc = Minecraft.getInstance();
		ExtendedCodexMap clientlanguagemap = ExtendedCodexMap.loadFrom(pResourceManager, mc.getLanguageManager().getSelected().getCode());
		ExtendedCodexMap.inject(clientlanguagemap);
	}

	@Override
	public IResourceType getResourceType() {
		return VanillaResourceType.LANGUAGES;
	}
}