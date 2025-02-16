package com.github.maximuslotro.lotrrextended.client.codex.entry.registry;

import java.lang.ref.WeakReference;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class ExtendedForgeRegistryBasedCodexEntry<V extends IForgeRegistryEntry<V>> extends ExtendedRegistryBasedCodexEntry<V>{

	public ExtendedForgeRegistryBasedCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent(getInstanceObject().getRegistryName().toString());
	}

	/*
	 * Used to retrieve an approprate registry for the Codex entry
	 * Use this method as much as possable, only using getAsociatedVanillaRegistry
	 * when there is no appropriate {@Link ForgeRegistries} available
	 * */
	public abstract IForgeRegistry<V> getAsociatedForgeRegistry();

	/*
	 * Gets an instance of this codex's entry, using the Forge registry
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
