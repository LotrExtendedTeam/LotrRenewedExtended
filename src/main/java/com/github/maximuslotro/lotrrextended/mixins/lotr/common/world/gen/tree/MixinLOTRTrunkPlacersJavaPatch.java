package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.gen.tree;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.serialization.Codec;

import lotr.common.world.gen.tree.LOTRTrunkPlacers;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

@Mixin(LOTRTrunkPlacers.class)
public class MixinLOTRTrunkPlacersJavaPatch {

	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 */
	@Overwrite(remap = false)
	private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> registerModded(String name, Codec<P> codec) {
		return TrunkPlacerType.register("lotr:"+name, codec);
	}
}
