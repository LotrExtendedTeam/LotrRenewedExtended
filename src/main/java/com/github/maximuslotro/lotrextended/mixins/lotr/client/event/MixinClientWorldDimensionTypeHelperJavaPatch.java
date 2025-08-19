package com.github.maximuslotro.lotrextended.mixins.lotr.client.event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.client.event.ClientWorldDimensionTypeHelper;
import lotr.common.dim.LOTRDimensionType;
import lotr.common.init.LOTRDimensions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;

/**
 * Move code to Access Transformers for Java 9+ systems
 * @Mixin (ClientWorldDimensionTypeHelper.class)
 */
@Mixin(ClientWorldDimensionTypeHelper.class)
public class MixinClientWorldDimensionTypeHelperJavaPatch {
	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 * @Overwrite (remap=false)
	 * @param world The passed {@link ClientWorld} value from ClientWorldDimensionTypeHelper
	 */
	@Overwrite(remap=false)
	public static void fixDimensionType(ClientWorld world) {
		DimensionType type = world.dimensionType();
		if ("lotr".equals(type.effectsLocation().getNamespace()) && !(type instanceof LOTRDimensionType)) {
			ResourceLocation dimensionId = world.dimension().location();
			LOTRDimensionType newType = LOTRDimensions.dispatchModDimensionType(dimensionId);

			try {
				world.dimensionType = newType;
				ExtendedLog.debug("Java9+ Corrected the DimensionType of client world %s to %s", dimensionId, newType);
			} catch (Exception var5) {
				ExtendedLog.error("Java9+ Failed to correct the DimensionType of client world %s", dimensionId);
				var5.printStackTrace();
			}
		}

	}
}
