package com.github.maximuslotro.lotrrextended.mixins.lotr.client.event;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.common.base.Function;

import lotr.client.event.LOTRTickHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;

/**
 * Move code to Access Transformers for Java 9+ systems
 * @Mixin (LOTRTickHandlerClient.class)
 */
@SuppressWarnings("deprecation")
@Mixin(LOTRTickHandlerClient.class)
public class MixinLOTRTickHandlerClientJavaPatch {

	/**
	 * This method overwrites Renewed's Dangerous code for replacing final Rendering Classes in Vanilla
	 * Code has been written to take advantage of AccessTransfromers and remove Reflection
	 * @author maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 * @Overwrite (remap=false)
	 * @param <T> The base type of the field being replaced
	 * @param <U> The subclass type that extends T and replaces the base class
	 * @param minecraftInstance The local Minecraft here
	 * @param baseClass The class to override
	 * @param subClass The class to replace with
	 * @param getter A function to retrieve the current {@link Minecraft} instance
	 * @param newSubClassSupplier A function to return an instance of the overriding class
	 * @param loggingNameOfField A String that is used in debug logging
	 */
	@Overwrite(remap=false)
	private static <T, U extends T> void replaceFinalMinecraftFieldIfNotReplaced(Minecraft minecraftInstance, Class<T> baseClass, Class<U> subClass, Function<Minecraft, T> getter, Supplier<U> newSubClassSupplier, String loggingNameOfField) {
		if("GameRenderer".equals(loggingNameOfField)) {
			T object = getter.apply(minecraftInstance);
			if (!subClass.isAssignableFrom(object.getClass())) {
				U newSubClassObject = newSubClassSupplier.get();
				minecraftInstance.gameRenderer = (GameRenderer) newSubClassObject;
				if (newSubClassObject instanceof IFutureReloadListener) {
					IReloadableResourceManager resMgr = (IReloadableResourceManager) minecraftInstance.getResourceManager();
					IFutureReloadListener objectAsListener = (IFutureReloadListener) newSubClassObject;
					resMgr.registerReloadListener(objectAsListener);
					if (objectAsListener instanceof IResourceManagerReloadListener)
						((IResourceManagerReloadListener) objectAsListener).onResourceManagerReload(resMgr);
				}
				ExtendedLog.infoParms("Java9+ Successfully replaced %s instance with subclass instance", loggingNameOfField );
			}
		}
		else if("WorldRenderer".equals(loggingNameOfField)) {
			T object = getter.apply(minecraftInstance);
			if (!subClass.isAssignableFrom(object.getClass())) {
				U newSubClassObject = newSubClassSupplier.get();
				minecraftInstance.levelRenderer = (WorldRenderer) newSubClassObject;
				if (newSubClassObject instanceof IFutureReloadListener) {
					IReloadableResourceManager resMgr = (IReloadableResourceManager) minecraftInstance.getResourceManager();
					IFutureReloadListener objectAsListener = (IFutureReloadListener) newSubClassObject;
					resMgr.registerReloadListener(objectAsListener);
					if (objectAsListener instanceof IResourceManagerReloadListener)
						((IResourceManagerReloadListener) objectAsListener).onResourceManagerReload(resMgr);
				}
				ExtendedLog.infoParms("Java9+ Successfully replaced %s instance with subclass instance", loggingNameOfField );
			}
		}
		else {
			ExtendedLog.error("Java9+ Failed to set new %s", loggingNameOfField );
			throw new RuntimeException("Recieved a type :"+loggingNameOfField+" not handlable by MixinLOTRTickHandlerClientJavaPatch, PLEASE REPORT THIS TO THE DEVS!");
		}
	}
}
