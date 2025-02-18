package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class ExtendedForgeRegistryBasedCodexEntry<V extends IForgeRegistryEntry<V>> extends ExtendedRegistryBasedCodexEntry<V>{

	public ExtendedForgeRegistryBasedCodexEntry(ResourceLocation locationID, ResourceLocation location) {
		super(locationID, location);
	}

	/**
	 * Used to retrieve an appropriate registry for the Codex entry
	 * @return {@Link IForgeRegistry} to be used for looking up this codex data's type
	 * */
	public abstract IForgeRegistry<V> getAsociatedForgeRegistry();

	/**
	 * Gets an instance of this codex's entry, using the Forge registry
	 * @return The registry object defined by this codex entry
	 * */
	public V getInstanceObject() {
		if(object.get()!=null) {
			return object.get();
		}
		V entry = getAsociatedForgeRegistry().getValue(location);
		if(entry==null) {
			ExtendedLog.error("Codex tried loading invalid Registry entry %s", location.toString());
		}
		object = new WeakReference<V>(entry);
		return entry;
	}
}
