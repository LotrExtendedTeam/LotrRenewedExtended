package com.github.maximuslotro.lotrrextended.client.render.model;

import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import com.mojang.datafixers.util.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class ExtendedTransformWrapperUnbakedModel implements IModelGeometry<ExtendedTransformWrapperUnbakedModel> {
    private final ResourceLocation guiModelLocation;
    private final ResourceLocation heldModelLocation;

    public ExtendedTransformWrapperUnbakedModel(ResourceLocation inventoryLocation, ResourceLocation handheldLocation) {
        this.guiModelLocation = inventoryLocation;
        this.heldModelLocation = handheldLocation;
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        IUnbakedModel unbakedGuiModel = bakery.getModel(this.guiModelLocation);
        IUnbakedModel unbakedHeldModel = bakery.getModel(this.heldModelLocation);
        if(unbakedGuiModel == null) {
            throw new IllegalStateException("Could not find inventory model: " + this.guiModelLocation);
        }
        if(unbakedHeldModel == null) {
            throw new IllegalStateException("Could not find handheld model: " + this.heldModelLocation);
        }
        IBakedModel bakedGuiModel = unbakedGuiModel.bake(bakery, spriteGetter, modelTransform, this.guiModelLocation);
        IBakedModel bakedHeldModel = unbakedHeldModel.bake(bakery, spriteGetter, modelTransform, this.heldModelLocation);
        return new ExtendedTransformWrapperBakedModel(bakedGuiModel, bakedHeldModel);
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<RenderMaterial> materials = new HashSet<>();
        IUnbakedModel invModel = modelGetter.apply(this.guiModelLocation);
        if (invModel != null) {
            materials.addAll(invModel.getMaterials(modelGetter, missingTextureErrors));
        }
        IUnbakedModel handModel = modelGetter.apply(this.heldModelLocation);
        if (handModel != null) {
            materials.addAll(handModel.getMaterials(modelGetter, missingTextureErrors));
        }
        return materials;
    }
}