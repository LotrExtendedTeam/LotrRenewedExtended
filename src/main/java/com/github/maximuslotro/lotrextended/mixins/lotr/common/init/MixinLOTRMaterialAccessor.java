package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import lotr.common.init.LOTRMaterial;

@Mixin(LOTRMaterial.class)
public interface MixinLOTRMaterialAccessor {

    @Accessor(value = "materialName", remap = false)
    void setMaterialName(String name);
}