package com.github.maximuslotro.lotrextended.mixins.lotr.client.render;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.client.render.RandomTextureVariants;
import net.minecraft.util.ResourceLocation;

/**
 * Fix using unsafe Java 9+ methods
 * @Mixin (RandomTextureVariants.class)
 */
@Mixin(RandomTextureVariants.class)
public class MixinRandomTextureVariantsJavaPatch {

	/**
	 * @Shadow (remap = false)
	 * @Final Index of all random skins
	 * */
	@Shadow(remap=false)
	@Final
	private static Map<ResourceLocation, RandomTextureVariants> ALL_RANDOM_SKINS;

	@Invoker("<init>")
    static RandomTextureVariants create(ResourceLocation path) {
        throw new AssertionError();
    }

	/**
	 * @author maximuslotro
	 * @reason Java 9+ changes how computeIfAbsent works. If any map changes, not just on current key happens, a ConcurrentModificationException is thrown
	 * This code changes the way the BlockModelQuadsHolder is generated to ensure non-concurrent changes
	 * <p>
	 * A ConcurrentModificationException in HashMap.computeIfAbsent can occur when the map is structurally modified by another thread while computeIfAbsent is in progress.
	 * This behavior changed between Java 8 and Java 9 due to a fix for JDK-8071667.
	 * In Java 8, computeIfAbsent might not throw a ConcurrentModificationException if the modification happens during the computation but doesn't interfere with the specific key being computed.
	 * However, in Java 9 and later, the implementation is stricter.
	 * If any modification to the HashMap occurs during the execution of the computeIfAbsent method, a ConcurrentModificationException is thrown.
	 * <p>
	 * This blog explains the issue: <a href="http://thisthread.blogspot.com/2017/12/hashmapcomputeifabsent-behavior-in-java.html">...</a>
	 * @Overwrite (remap=false)
	 * @param path The skin list path
	 * @return A RandomTextureVariants holder
	 */
	@Overwrite(remap=false)
	public static RandomTextureVariants loadSkinsList(ResourceLocation path) {
		synchronized (ALL_RANDOM_SKINS) {
	        return ALL_RANDOM_SKINS.computeIfAbsent(path, MixinRandomTextureVariantsAccessorJavaPatch::create);
	    }
	}
}
