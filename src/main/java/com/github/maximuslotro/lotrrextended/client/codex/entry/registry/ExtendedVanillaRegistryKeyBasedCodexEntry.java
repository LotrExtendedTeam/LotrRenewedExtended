package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class ExtendedVanillaRegistryKeyBasedCodexEntry<V, T extends RegistryKey<V>> extends ExtendedRegistryBasedCodexEntry<T>{

	public ExtendedVanillaRegistryKeyBasedCodexEntry(ResourceLocation locationID, ResourceLocation location) {
		super(locationID, location);
	}

	/**
	 * Used to retrieve an appropriate registry for the Codex entry
	 * @return {@Link RegistryKey}<{@Link Registry}> to be used for looking up this codex data's type
	 * */
	public abstract RegistryKey<Registry<V>> getAsociatedVanillaRegistry();

	/**
	 * Gets an instance of this codex's entry, using the Vanilla RegistryKey Registry
	 * @return The registry object defined by this codex entry
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
