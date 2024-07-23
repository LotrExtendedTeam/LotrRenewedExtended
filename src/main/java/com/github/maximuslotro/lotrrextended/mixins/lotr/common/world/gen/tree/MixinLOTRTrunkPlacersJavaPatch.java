package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.gen.tree;

import java.lang.reflect.Constructor;

import org.spongepowered.asm.mixin.Mixin;

import com.mojang.serialization.Codec;

import lotr.common.util.LOTRUtil;
import lotr.common.world.gen.tree.LOTRTrunkPlacers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mixin(LOTRTrunkPlacers.class)
public class MixinLOTRTrunkPlacersJavaPatch {

	@SuppressWarnings("unchecked")
	private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> registerModded(String name, Codec<P> codec) {
		return TrunkPlacerType.register("lotr:"+name, codec);
	}
}
