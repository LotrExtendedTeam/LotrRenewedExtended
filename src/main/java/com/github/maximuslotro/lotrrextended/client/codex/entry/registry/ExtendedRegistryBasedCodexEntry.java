package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;

import net.minecraft.util.ResourceLocation;

public abstract class ExtendedRegistryBasedCodexEntry<V> extends ExtendedBaseCodexEntry{
	protected WeakReference<V> object = new WeakReference<>(null);

	/*
	 * Registry location of the codex entry
	 * */
	protected ResourceLocation location;

	public ExtendedRegistryBasedCodexEntry(ResourceLocation locationID, ResourceLocation location) {
		super(locationID);
		this.location = location;
	}

	/*
	 * Get the registry location of this codex entry
	 * @return A {@Link ResourceLocation} address of the entry
	 * */
	public ResourceLocation getLocation() {
		return location;
	}

	/**
	 * Gets an instance of this codex's entry, using either Forge or Vanilla registries as needed
	 * @return The registry object defined by this codex entry
	 * */
	public abstract V getInstanceObject();

	@Override
	public String toString() {
		return "[regLoc:"+location.toString()+","+super.toString()+"]";
	}
}
