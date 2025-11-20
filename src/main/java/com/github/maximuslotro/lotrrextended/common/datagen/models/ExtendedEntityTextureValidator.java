package com.github.maximuslotro.lotrrextended.common.datagen.models;

import java.io.IOException;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedTapestryType;
import com.google.common.base.Preconditions;

import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ExistingFileHelper.ResourceType;

public class ExtendedEntityTextureValidator implements IDataProvider {
	protected static final ResourceType TEXTURE = new ResourceType(ResourcePackType.CLIENT_RESOURCES, ".png", "textures");

	public final ExistingFileHelper existingFileHelper;
	protected final String modid;

	public ExtendedEntityTextureValidator(String modid, ExistingFileHelper existingFileHelper) {
		Preconditions.checkNotNull(existingFileHelper);
		this.existingFileHelper = existingFileHelper;
		this.modid = modid;
	}

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		for(ExtendedTapestryType type : ExtendedTapestryType.TAPESTRIE_REGISTRY.get().getValues()) {
			check(new ResourceLocation(type.getRegistryName().getNamespace(), "entity/tapestry/"+type.getRegistryName().getPath()));
		}
	}

	private void check(ResourceLocation layer) {
		try {
			getExistingFile(layer);
		}catch(IllegalArgumentException e) {
			System.out.println("Missing texture for: "+layer);
		}
	}

	public void getExistingFile(ResourceLocation path) throws IllegalArgumentException{
		Preconditions.checkArgument(existingFileHelper.exists(path, TEXTURE), "Texture %s does not exist in any known resource pack", path);
	}

	@Override
	public String getName() {
		return "Entity Texture Validator: " + modid;
	}
}
