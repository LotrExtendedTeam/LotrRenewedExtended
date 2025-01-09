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

@SuppressWarnings("deprecation")
@Mixin(LOTRTickHandlerClient.class)
public class MixinLOTRTickHandlerClientJavaPatch {

	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 */
	@Overwrite(remap=false)
	private static <T, U extends T> void replaceFinalMinecraftFieldIfNotReplaced(Minecraft mc, Class<T> baseClass, Class<U> subClass, Function<Minecraft, T> getter, Supplier<U> newSubClassSupplier, String loggingNameOfField) {
		if("GameRenderer".equals(loggingNameOfField)) {
			T object = getter.apply(mc);
			if (!subClass.isAssignableFrom(object.getClass())) {
				U newSubClassObject = newSubClassSupplier.get();
				mc.gameRenderer = (GameRenderer) newSubClassObject;
				if (newSubClassObject instanceof IFutureReloadListener) {
					IReloadableResourceManager resMgr = (IReloadableResourceManager) mc.getResourceManager();
					IFutureReloadListener objectAsListener = (IFutureReloadListener) newSubClassObject;
					resMgr.registerReloadListener(objectAsListener);
					if (objectAsListener instanceof IResourceManagerReloadListener)
						((IResourceManagerReloadListener) objectAsListener).onResourceManagerReload(resMgr);
				}
				ExtendedLog.info("Java9+ Successfully replaced %s instance with subclass instance", loggingNameOfField );
			}
		}
		else if("WorldRenderer".equals(loggingNameOfField)) {
			T object = getter.apply(mc);
			if (!subClass.isAssignableFrom(object.getClass())) {
				U newSubClassObject = newSubClassSupplier.get();
				mc.levelRenderer = (WorldRenderer) newSubClassObject;
				if (newSubClassObject instanceof IFutureReloadListener) {
					IReloadableResourceManager resMgr = (IReloadableResourceManager) mc.getResourceManager();
					IFutureReloadListener objectAsListener = (IFutureReloadListener) newSubClassObject;
					resMgr.registerReloadListener(objectAsListener);
					if (objectAsListener instanceof IResourceManagerReloadListener)
						((IResourceManagerReloadListener) objectAsListener).onResourceManagerReload(resMgr);
				}
				ExtendedLog.info("Java9+ Successfully replaced %s instance with subclass instance", loggingNameOfField );
			}
		}
		else {
			ExtendedLog.error("Java9+ Failed to set new %s", loggingNameOfField );
			throw new RuntimeException("Recieved a type :"+loggingNameOfField+" not handlable by MixinLOTRTickHandlerClientJavaPatch, PLEASE REPORT THIS TO THE DEVS!");
		}
	}
}
