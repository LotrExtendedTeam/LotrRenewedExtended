package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Represents a custom mount armor item that can be equipped on specific mount types
 * (e.g., boars, wargs). This class extends {@link Item} and provides additional
 * properties such as protection value and a custom texture.
 */
public class ExtendedMountedArmorItem extends Item {

	/** The protection value provided by this armor. */
	private final int protection;

	/** The texture resource used to render this armor on the mount. */
	private final ResourceLocation texture;

	/** The mount type this armor can be applied to. */
	private final MountType mountType;

	/**
	 * Constructs a new {@code ExtendedMountedArmorItem} using a texture identifier string.
	 * The texture path is automatically built based on the mount type.
	 *
	 * @param pProtection the protection value provided by this armor
	 * @param pIdentifier the texture identifier (file name without extension)
	 * @param pType the mount type this armor is valid for
	 * @param pProperties the item properties
	 */
	public ExtendedMountedArmorItem(int pProtection, String pIdentifier, MountType pType, Item.Properties pProperties) {
		this(pProtection, new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/" + pType.relativePath + "/armor/" + pIdentifier + ".png"), pType, pProperties);
	}

	/**
	 * Constructs a new {@code ExtendedMountedArmorItem} with a pre-defined texture resource.
	 *
	 * @param pProtection the protection value provided by this armor
	 * @param texture the texture resource location
	 * @param pType the mount type this armor is valid for
	 * @param pProperties the item properties
	 */
	public ExtendedMountedArmorItem(int pProtection, ResourceLocation texture, MountType pType, Item.Properties pProperties) {
		super(pProperties);
		this.protection = pProtection;
		this.texture = texture;
		this.mountType = pType;
	}

	/**
	 * Gets the texture used to render this armor.
	 *
	 * @return the texture resource location
	 */
	public ResourceLocation getTexture() {
		return texture;
	}

	/**
	 * Gets the protection value of this armor.
	 *
	 * @return the protection value
	 */
	public int getProtection() {
		return this.protection;
	}

	/**
	 * Gets the mount type that this armor is designed for.
	 *
	 * @return the {@link MountType} associated with this armor
	 */
	public MountType getMountType() {
		return mountType;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		pTooltip.add(StringTextComponent.EMPTY);
		pTooltip.add((new TranslationTextComponent("item.lotrextended.modifiers." + mountType.langKey)).withStyle(TextFormatting.GRAY));
		pTooltip.add((new TranslationTextComponent("attribute.modifier.plus.0", protection, new TranslationTextComponent("attribute.name.generic.armor"))).withStyle(TextFormatting.BLUE));
	}

	/**
	 * Defines the types of mounts that can use this armor, along with their
	 * corresponding texture directory paths.
	 */
	public enum MountType {

		/** Boar mount type. */
		BOAR("animals/boar", "boar"),

		/** Warg mount type. */
		WARG("warg", "warg");

		/** Relative texture path used for this mount type. */
		public final String relativePath;
		/** Localization key used for this mount type. */
		public final String langKey;

		/**
		 * Constructs a mount type with its associated texture path.
		 *
		 * @param pPath The relative texture directory path
		 * @param pKey The lang key for this type
		 */
		private MountType(String pPath, String pKey) {
			this.relativePath = pPath;
			this.langKey = pKey;
		}
	}
}