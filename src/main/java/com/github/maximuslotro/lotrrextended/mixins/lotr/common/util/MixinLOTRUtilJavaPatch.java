package com.github.maximuslotro.lotrrextended.mixins.lotr.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.util.LOTRUtil;

@Mixin(LOTRUtil.class)
public class MixinLOTRUtilJavaPatch {

	/**
	 * @author Maximuslotro
	 * @reason This code should not be called if this patch is applied properly
	 */
	@Overwrite(remap = false)
	public static void unlockConstructor(Constructor<?> constr) {
		ExtendedLog.error("unlockConstructor called while Java 9+ compatability patch! This should not happen, someone is touching things they should not and are creating compatibality issues!");
		throw new RuntimeException();
	}

	/**
	 * @author Maximuslotro
	 * @reason This code should not be called if this patch is applied properly
	 */
	@Overwrite(remap = false)
	public static void unlockFinalField(Field f) {
		ExtendedLog.error("unlockFinalField called while Java 9+ compatability patch! This should not happen, someone is touching things they should not and are creating compatibality issues!");
		throw new RuntimeException();
	}

	/**
	 * @author Maximuslotro
	 * @reason This code should not be called if this patch is applied properly
	 */
	@Overwrite(remap = false)
	public static void unlockMethod(Method m) {
		ExtendedLog.error("unlockMethod called while Java 9+ compatability patch! This should not happen, someone is touching things they should not and are creating compatibality issues!");
		throw new RuntimeException();
	}
}
