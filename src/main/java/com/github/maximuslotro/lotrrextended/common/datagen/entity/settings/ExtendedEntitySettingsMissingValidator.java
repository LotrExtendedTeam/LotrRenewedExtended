package com.github.maximuslotro.lotrrextended.common.datagen.entity.settings;

import lotr.common.init.ExtendedEntities;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedEntitySettingsMissingValidator implements IDataProvider {

    private final ExistingFileHelper fileHelper;

    public ExtendedEntitySettingsMissingValidator(ExistingFileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @Override
    public void run(DirectoryCache cache) {
        for(String entity : ExtendedEntities.REGISTEREDNPCENTITIES) {
        	if (!fileHelper.exists(new ResourceLocation("lotr", "npcs/entity_settings/"+entity+".json"), ResourcePackType.SERVER_DATA)) {
                System.out.println("Missing entity_settings files for: " + entity);
            }
        }
    }

    @Override
    public String getName() {
        return "Extended Entity Settings Validator";
    }
}
