package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.init.LOTRDimensions;

@Mixin(LOTRDimensions.class)
public class MixinLOTRDimensionsJavaPatch {

	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 */
	@Overwrite(remap=false)
	public static void replaceDimensionCodecToForceStability() {
		try {
			//TODO fix later
//			final Codec<DimensionGeneratorSettings> codec = DimensionGeneratorSettings.CODEC;
//			Codec<DimensionGeneratorSettings> stableCodec = new Codec<DimensionGeneratorSettings>() {
//		          public <T> DataResult<T> encode(DimensionGeneratorSettings input, DynamicOps<T> ops, T prefix) {
//		            return codec.encode(input, ops, prefix);
//		          }
//		          
//		          public <T> DataResult<Pair<DimensionGeneratorSettings, T>> decode(DynamicOps<T> ops, T input) {
//		            DataResult<Pair<DimensionGeneratorSettings, T>> result = codec.decode(ops, input);
//		            return DataResult.success(result.result().orElseThrow(() -> new IllegalStateException("Failed to change lifecycle to stable")), Lifecycle.stable());
//		          }
//		    };
//		    DimensionGeneratorSettings.CODEC = stableCodec;
		} catch (Exception var3) {
			var3.printStackTrace();
			ExtendedLog.error("Java9+ Failed to set dimension generator settings codec to stable");
		}
	}
}
