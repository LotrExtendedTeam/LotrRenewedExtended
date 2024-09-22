package com.github.maximuslotro.lotrrextended.common.datagen.models;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedVesselModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T> {
    protected ExtendedVesselModelBuilder(T parent, ExistingFileHelper existingFileHelper)
    {
        super(new ResourceLocation("lotr", "vessel_drink"), parent, existingFileHelper);
    }

    @Override
    public JsonObject toJson(JsonObject json)
    {
        return super.toJson(json);
    }
}