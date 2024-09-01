package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.mixins.MIXINConfig;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;

import lotr.common.init.LOTRDimensions;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;

@Mixin(LOTRDimensions.class)
public class MixinLOTRDimensionsJavaPatch {

	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 */
	@Overwrite(remap=false)
	public static void replaceDimensionCodecToForceStability() {
		try {
			if(MIXINConfig.isBrokenJavaFutureVersions()) {
				ExtendedLog.info("Java9+ bypassing replaceDimensionCodecToForceStability patch due to inability to unlock final fields.");
				return;
			}
			//TODO replace with AT later if possable
			final Codec<DimensionGeneratorSettings> codec = DimensionGeneratorSettings.CODEC;
			Codec<DimensionGeneratorSettings> stableCodec = new Codec<DimensionGeneratorSettings>() {
				public <T> DataResult<T> encode(DimensionGeneratorSettings input, DynamicOps<T> ops, T prefix) {
					return codec.encode(input, ops, prefix);
				}

				public <T> DataResult<Pair<DimensionGeneratorSettings, T>> decode(DynamicOps<T> ops, T input) {
					DataResult<Pair<DimensionGeneratorSettings, T>> result = codec.decode(ops, input);
					return DataResult.success(result.result().orElseThrow(() -> new IllegalStateException("Failed to change lifecycle to stable")), Lifecycle.stable());
				}
			};
			Field dim_CODEC = Stream.of(DimensionGeneratorSettings.class.getDeclaredFields())
									.filter(field -> (field.getModifiers() & 8) != 0)
									.filter(field -> field.getType() == Codec.class)
									.findFirst()
									.orElseThrow(() -> new IllegalStateException("Failed to find codec field in DimensionGeneratorSettings"));
			try {
				dim_CODEC.setAccessible(true);
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(dim_CODEC, (dim_CODEC.getModifiers() & -3 & -5 | 1) & -17);
			} catch (SecurityException | IllegalAccessException | NoSuchFieldException exception) {
				ExtendedLog.error("Error unlocking final field " + dim_CODEC.toString());
				exception.printStackTrace();
			}
			dim_CODEC.set((Object) null, stableCodec);
		} catch (Exception exception) {
			exception.printStackTrace();
			ExtendedLog.error("Java9+ Failed to set dimension generator settings codec to stable");
		}
	}
}