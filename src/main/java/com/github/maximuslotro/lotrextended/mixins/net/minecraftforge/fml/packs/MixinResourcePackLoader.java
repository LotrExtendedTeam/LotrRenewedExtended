package com.github.maximuslotro.lotrextended.mixins.net.minecraftforge.fml.packs;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;

import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.fml.packs.ResourcePackLoader;
import net.minecraftforge.fml.packs.ResourcePackLoader.IPackInfoFinder;

/**
 * A Mixin targeting {@link ResourcePackLoader} to inject additional custom resource packs
 * into the game’s pack list during initialization.
 * <p>
 * This mixin runs at the end of 
 * {@link ResourcePackLoader#loadResourcePacks(ResourcePackList, BiFunction)} 
 * to ensure that vanilla and modded resource packs are already loaded, then appends
 * custom-defined packs provided by {@link ExtendedClientProxy}.
 * </p>
 * @Mixin (ResourcePackLoader.class)
 */
@Mixin(ResourcePackLoader.class)
public class MixinResourcePackLoader {

	/**
	 * Injects into {@link ResourcePackLoader#loadResourcePacks(ResourcePackList, BiFunction)}
	 * at the {@code TAIL}, adding additional resource packs after vanilla packs are registered.
	 *
	 * <p>This ensures that extended packs are available alongside vanilla and mod resource packs,
	 * allowing custom assets (textures, sounds, models) to be distributed seamlessly.</p>
	 *
	 * @param resourcePacks the game’s active {@link ResourcePackList}
	 * @param packFinder    the pack discovery function used to locate resource packs
	 * @param ci            callback info (unused here, injection only)
	 * @Inject (method = "loadResourcePacks(Lnet/minecraft/resources/ResourcePackList;Ljava/util/function/BiFunction;)V", at = @At("TAIL"), remap = false)
	 */
	@Inject(method = "loadResourcePacks(Lnet/minecraft/resources/ResourcePackList;Ljava/util/function/BiFunction;)V", at = @At("TAIL"), remap = false)
	private static void loadExtendedResourcePacks(ResourcePackList resourcePacks, BiFunction<Map<ModFile, ? extends ModFileResourcePack>, BiConsumer<? super ModFileResourcePack, ResourcePackInfo>, IPackInfoFinder> packFinder, CallbackInfo ci) {
		ExtendedClientProxy.createAdditionalPack(resourcePacks, "extended_textures", "extended", "Forced Extended Textures", true);
		ExtendedClientProxy.createAdditionalPack(resourcePacks, "renewed_textures", "renewed", "Redone Renewed Textures", false);
		ExtendedClientProxy.createAdditionalPack(resourcePacks, "vanilla_textures", "vanilla", "Redone Vanilla Textures", false);
	}
}
