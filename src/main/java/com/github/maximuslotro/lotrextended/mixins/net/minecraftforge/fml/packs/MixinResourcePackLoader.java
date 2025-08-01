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

@Mixin(ResourcePackLoader.class)
public class MixinResourcePackLoader {

	@Inject(method = "loadResourcePacks(Lnet/minecraft/resources/ResourcePackList;Ljava/util/function/BiFunction;)V", at = @At("TAIL"), remap = false)
    private static void loadExtendedResourcePacks(ResourcePackList resourcePacks, BiFunction<Map<ModFile, ? extends ModFileResourcePack>, BiConsumer<? super ModFileResourcePack, ResourcePackInfo>, IPackInfoFinder> packFinder, CallbackInfo cl) {
		//ExtendedClientProxy.createLecacyPack(resourcePacks);
		ExtendedClientProxy.createRenewedPack(resourcePacks);
		ExtendedClientProxy.createForcedPack(resourcePacks);
    }
}
