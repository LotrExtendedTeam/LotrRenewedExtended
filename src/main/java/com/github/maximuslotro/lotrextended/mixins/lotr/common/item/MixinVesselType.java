package com.github.maximuslotro.lotrextended.mixins.lotr.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.init.ExtendedItems;
import lotr.common.item.VesselType;
import lotr.common.util.LOTRUtil;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

/**
 * Used to register new VesselType instances, as Renewed requires this enum type to be used for vessel logic
 * @Mixin (VesselType.class)
 * */
@Mixin(VesselType.class)
public class MixinVesselType {
	@SuppressWarnings("target")
	/*
	 * @reason This value is the runtime array variable for the VesselType
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * @Dynamic This field is a runtime fabricated field
	 * */
	@Shadow(remap = false)
	@Final
	@Mutable
	@Dynamic
	private static VesselType[] $VALUES;

	/**
	 * Need to regenerate the lookup index for vessels to add extended's vessels
	 * */
	@Unique
	private static final Map<String, VesselType> EXTENDED_NAME_LOOKUP;

	/**
	 * Holder for extended's skull goblet vessel type
	 * */
	@Unique
	private static final VesselType SKULL_GOBLET;

	/**
	 * Holder for extended's glass bottle vessel type
	 * */
	@Unique
	private static final VesselType GLASS_BOTTLE;

	/*
	 * @reason Register Extended's vessel types and regenerate the vessel map
	 * */
	static {
		SKULL_GOBLET = lotrmaterialExpansion$addVariant("SKULL_GOBLET", () -> ExtendedItems.SKULL_GOBLET.get(), "skull_goblet", "skull_goblet_drink", true);
		GLASS_BOTTLE = lotrmaterialExpansion$addVariant("GLASS_BOTTLE", () -> Items.GLASS_BOTTLE, "glass_bottle", "glass_bottle_drink", true);
		EXTENDED_NAME_LOOKUP = LOTRUtil.createKeyedEnumMap(MixinVesselType.$VALUES, VesselType::getCodeName);
	}

	/**
	 * Accessing the Enum creation method, DO NOT CALL THIS METHOD DIRECTLY, CALL THE HELPER METHODS
	 * @Invoker ("&lt;init&gt;")
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param internalId The internal id (ordinal) of this Enum entry
	 * @param item The item for this drink vessel to handle
	 * @param name The localized name of this Enum
	 * @param iconName The item texture location
	 * @param place Should this vessel be placed down
	 * @return A built VesselType instance
	 * */
	@Invoker("<init>")
	public static VesselType lotrVesselType$invokeInit(String internalName, int internalId, Supplier<IItemProvider> item,
			String name, String iconName, boolean place) {
		throw new AssertionError();
	}
	/**
	 * Accessing the Enum creation method
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param item The item for this drink vessel to handle
	 * @param name The localized name of this Enum
	 * @param iconName The item texture location
	 * @param place Should this vessel be placed down
	 * @return A built VesselType instance
	 * */
	@Unique
	private static VesselType lotrmaterialExpansion$addVariant(String internalName, Supplier<IItemProvider> item, String name, String iconName, boolean place) {
		ArrayList<VesselType> variants = new ArrayList<>(Arrays.asList(MixinVesselType.$VALUES));
		VesselType instrument = lotrVesselType$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, item, name, iconName, place);
		variants.add(instrument);
		MixinVesselType.$VALUES = variants.toArray(new VesselType[0]);
		return instrument;
	}

	/**
	 * @author maximuslotro
	 * @reason Replace Renewed's vessel name lookup with our own that adds our new vessel types
	 * @Overwrite (remap=false)
	 * @param name The name of the vessel to lookup
	 * @return A VesselType for the searched name, otherwise return VesselType.WOODEN_MUG
	 * */
	@Overwrite(remap=false)
	public static VesselType forName(String name) {
		return EXTENDED_NAME_LOOKUP.getOrDefault(name, VesselType.WOODEN_MUG);
	}
}
