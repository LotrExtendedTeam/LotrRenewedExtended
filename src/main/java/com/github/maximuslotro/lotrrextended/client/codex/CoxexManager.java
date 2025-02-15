package com.github.maximuslotro.lotrrextended.client.codex;

import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class CoxexManager implements ISelectiveResourceReloadListener {
	public CoxexManager() {}

	public void onResourceManagerReload(IResourceManager pResourceManager, Predicate<IResourceType> resourcePredicate) {
		if(!resourcePredicate.test(getResourceType())) {
			return;
		}
		List<String> list = Lists.newArrayList("en_us");
		Minecraft mc = Minecraft.getInstance();
		if (mc.options.languageCode != "en_us") {
			list.add(mc.options.languageCode);
		}

		ExtendedCodexMap clientlanguagemap = ExtendedCodexMap.loadFrom(pResourceManager, list);
		ExtendedCodexMap.inject(clientlanguagemap);
	}

	@Override
	public IResourceType getResourceType() {
		return VanillaResourceType.LANGUAGES;
	}
}