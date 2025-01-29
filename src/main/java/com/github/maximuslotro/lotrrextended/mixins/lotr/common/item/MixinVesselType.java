package com.github.maximuslotro.lotrrextended.mixins.lotr.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.init.ExtendedItems;
import lotr.common.item.VesselType;
import lotr.common.util.LOTRUtil;
import net.minecraft.util.IItemProvider;

@Mixin(VesselType.class)
public class MixinVesselType {
	/**
	 * The field exists, just not until runtime
	 */
	@Shadow(remap = false)
	@Final
	@Mutable
	@Dynamic
	private static VesselType[] $VALUES;

	@Shadow(remap = false)
	@Final
	@Mutable
	private static final Map<String, VesselType> NAME_LOOKUP;

	private static final VesselType SKULL_GOBLET;

	static {
		SKULL_GOBLET = lotrmaterialExpansion$addVariant("SKULL_GOBLET", () -> ExtendedItems.SKULL_GOBLET.get(),
				"skull_goblet", "skull_goblet_drink", true);
		NAME_LOOKUP = LOTRUtil.createKeyedEnumMap(MixinVesselType.$VALUES,
				VesselType::getCodeName);
	}

	@Invoker("<init>")
	public static VesselType lotrVesselType$invokeInit(String internalName, int internalId, Supplier<IItemProvider> item,
			String name, String iconName, boolean place) {
		throw new AssertionError();
	}

	private static VesselType lotrmaterialExpansion$addVariant(String internalName, Supplier<IItemProvider> item, String name, String iconName, boolean place) {
		ArrayList<VesselType> variants = new ArrayList<>(Arrays.asList(MixinVesselType.$VALUES));
		VesselType instrument = lotrVesselType$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, item, name, iconName, place);
		variants.add(instrument);
		MixinVesselType.$VALUES = variants.toArray(new VesselType[0]);
		return instrument;
	}
}
