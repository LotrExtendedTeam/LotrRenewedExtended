package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;

import net.minecraft.util.ResourceLocation;

public abstract class ExtendedRegistryBasedCodexEntry<V> extends ExtendedBaseCodexEntry{
	protected WeakReference<V> object = new WeakReference<>(null);

	public ExtendedRegistryBasedCodexEntry(ResourceLocation location) {
		super(location);
	}

	/*
	 * Gets an instance of this codex's entry, using either Forge or Vanilla registies as needed
	 * */
	public abstract V getInstanceObject();
}
