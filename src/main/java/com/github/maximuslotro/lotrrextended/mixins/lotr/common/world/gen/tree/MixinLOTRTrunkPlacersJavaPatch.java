package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.gen.tree;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.serialization.Codec;

import lotr.common.world.gen.tree.LOTRTrunkPlacers;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
/**
 * Move code to Access Transformers for Java 9+ systems
 * Mixin patch for {@link LOTRTrunkPlacers} to modify trunk placer registration.
 * 
 * <p>This mixin overwrites the private {@code registerModded} method to move the registration logic
 * to Access Transformers for compatibility with Java 9 and above.</p>
 * 
 * <p>The method registers a modded {@link TrunkPlacerType} with a namespaced key prefixed by "lotr:".</p>
 * 
 * @author Maximuslotro
 */
@Mixin(LOTRTrunkPlacers.class)
public class MixinLOTRTrunkPlacersJavaPatch {

	/**
     * Registers a modded trunk placer type with the "lotr:" namespace prefix.
     * 
     * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
     * @param <P> The type of {@link AbstractTrunkPlacer}.
     * @param name The registry name for the trunk placer.
     * @param codec The codec used for serialization of the trunk placer.
     * @return The registered {@link TrunkPlacerType} instance.
     * @Overwrite (remap = false)
     */
	@Overwrite(remap = false)
	private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> registerModded(String name, Codec<P> codec) {
		return TrunkPlacerType.register("lotr:"+name, codec);
	}
}
