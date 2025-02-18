package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class ExtendedVanillaRegistryBasedCodexEntry<V> extends ExtendedRegistryBasedCodexEntry<V>{

	public ExtendedVanillaRegistryBasedCodexEntry(ResourceLocation locationID, ResourceLocation location) {
		super(locationID, location);
	}

	/**
	 * Used to retrieve an appropriate registry for the Codex entry
	 * @return {@Link Registry} to be used for looking up this codex data's type
	 * */
	public abstract Registry<V> getAsociatedVanillaRegistry();

	/**
	 * Gets an instance of this codex's entry, using the Vanilla registry
	 * @return The registry object defined by this codex entry
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
