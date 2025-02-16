package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class ExtendedVanillaRegistryBasedCodexEntry<V> extends ExtendedRegistryBasedCodexEntry<V>{

	public ExtendedVanillaRegistryBasedCodexEntry(ResourceLocation location) {
		super(location);
	}

	/*
	 * Used to retrieve an approprate registry for the Codex entry
	 * Avoid using this method, by defaulting to getAsociatedVanillaRegistry
	 * */
	public abstract Registry<V> getAsociatedVanillaRegistry();

	/*
	 * Gets an instance of this codex's entry, using the Vanilla registry
	 * */
	@Override
	public V getInstanceObject() {
		if(object.get()!=null) {
			return object.get();
		}
		V entry = getAsociatedVanillaRegistry().get(location);
		if(entry==null) {
			ExtendedLog.error("Codex tried loading invalid Vanilla Registry entry %s", location.toString());
		}
		object = new WeakReference<V>(entry);
		return entry;
	}
}
