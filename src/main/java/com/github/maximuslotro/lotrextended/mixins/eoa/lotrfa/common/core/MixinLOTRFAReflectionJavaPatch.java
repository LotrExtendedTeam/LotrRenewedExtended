package com.github.maximuslotro.lotrextended.mixins.eoa.lotrfa.common.core;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrextended.mixins.lotr.common.init.MixinLOTRMaterialAccessor;

import eoa.lotrfa.common.core.LOTRFAReflection;
import lotr.common.init.LOTRMaterial;

/**
 * Mixin that replaces reflective material name modification in {@link LOTRFAReflection}.
 *
 * <p>This removes Java reflection usage and replaces it with a direct Mixin accessor,
 * improving compatibility with Java 9+ module restrictions and future JVM versions.</p>
 */
@Mixin(LOTRFAReflection.class)
public class MixinLOTRFAReflectionJavaPatch {
	/**
     * Replaces the reflective setter for {@code LOTRMaterial#materialName}.
     *
     * @author Maximuslotro
     * @reason Move code to Mixins for Java 9+ systems
     * @param material the material instance whose name will be modified
     * @param name the new material name to assign
     * 
     * @Overwrite (remap = false)
     */
    @Overwrite(remap = false)
    public static void setLOTRMaterialNameField(LOTRMaterial material, String name) {
        ((MixinLOTRMaterialAccessor)(Object) material).setMaterialName(name);
    }
}
