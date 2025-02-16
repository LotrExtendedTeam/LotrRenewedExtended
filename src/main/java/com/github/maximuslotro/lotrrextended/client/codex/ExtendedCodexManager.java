package com.github.maximuslotro.lotrrextended.client.codex;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class ExtendedCodexManager implements ISelectiveResourceReloadListener {
	public ExtendedCodexManager() {}

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