package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class ExtendedVanillaRegistryKeyBasedCodexEntry<V, T extends RegistryKey<V>> extends ExtendedRegistryBasedCodexEntry<T>{

	public ExtendedVanillaRegistryKeyBasedCodexEntry(ResourceLocation location) {
		super(location);
	}

	/*
	 * Used to retrieve an approprate registry for the Codex entry
	 * Avoid using this method, by defaulting to getAsociatedVanillaRegistry
	 * */
	public abstract RegistryKey<Registry<V>> getAsociatedVanillaRegistry();

	/*
	 * Gets an instance of this codex's entry, using the Vanilla registry
	 * */
	@Override
	public T getInstanceObject() {
		if(object.get()!=null) {
			return object.get();
		}
		try {
			@SuppressWarnings("unchecked")
			T entry = (T) RegistryKey.create(getAsociatedVanillaRegistry(), location);
			if(entry==null) {
				ExtendedLog.error("Codex tried loading invalid Vanilla Registry entry %s", location.toString());
			}
			object = new WeakReference<T>(entry);
			return entry;
		}catch(ClassCastException e) {
			ExtendedLog.error("Failed Getting Vanilla RegistryKey entry!");
			e.printStackTrace();
		}
		return null;
	}
}
