package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * A dyeable variant of {@link ExtendedMountedArmorItem}.
 * <p>
 * This class allows mount armor to be dyed using standard dye mechanics
 * by implementing {@link IDyeableArmorItem}.
 */
public class ExtendedMountedDyeableArmorItem extends ExtendedMountedArmorItem implements IDyeableArmorItem {

	/**
	 * Constructs a new dyeable mounted armor item using a texture identifier string.
	 *
	 * @param pProtection the protection value provided by this armor
	 * @param pIdentifier the texture identifier (file name without extension)
	 * @param pType the mount type this armor is valid for
	 * @param pProperties the item properties
	 */
	public ExtendedMountedDyeableArmorItem(int pProtection, String pIdentifier, MountType pType, Item.Properties pProperties) {
		super(pProtection, pIdentifier, pType, pProperties);
	}

	/**
	 * Constructs a new dyeable mounted armor item with a pre-defined texture resource.
	 *
	 * @param pProtection the protection value provided by this armor
	 * @param texture the texture resource location
	 * @param pType the mount type this armor is valid for
	 * @param pProperties the item properties
	 */
	public ExtendedMountedDyeableArmorItem(int pProtection, ResourceLocation texture, MountType pType, Item.Properties pProperties) {
		super(pProtection, texture, pType, pProperties);
	}
}