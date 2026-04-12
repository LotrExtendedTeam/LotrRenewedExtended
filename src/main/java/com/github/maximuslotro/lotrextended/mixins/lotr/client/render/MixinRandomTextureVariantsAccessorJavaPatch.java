package com.github.maximuslotro.lotrextended.mixins.lotr.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.client.render.RandomTextureVariants;
import net.minecraft.util.ResourceLocation;

@Mixin(RandomTextureVariants.class)
public interface MixinRandomTextureVariantsAccessorJavaPatch {

    @Invoker("<init>")
    static RandomTextureVariants create(ResourceLocation path) {
        throw new AssertionError();
    }
}