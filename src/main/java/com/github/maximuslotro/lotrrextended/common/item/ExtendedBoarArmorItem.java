package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ExtendedBoarArmorItem extends Item {
	   private final int protection;
	   private final ResourceLocation texture;

	   /**
	    * @param pProtection the given protection level of the {@code HorseArmorItem}
	    * @param pIdentifier the texture path identifier for the {@code DyeableHorseArmorItem}, {@link ExtendedBoarArmorItem}
	    * @param pProperties the item properties
	    */
	   public ExtendedBoarArmorItem(int pProtection, String pIdentifier, Item.Properties pProperties) {
	      this(pProtection, new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/armor/boar_armor_" + pIdentifier + ".png"), pProperties);
	   }

	   /**
	    * @param pProtection the given protection level of the {@code HorseArmorItem}
	    * @param texture the texture path identifier for the {@code DyeableHorseArmorItem}, {@link ExtendedBoarArmorItem}
	    * @param pProperties the item properties
	    */
	   public ExtendedBoarArmorItem(int pProtection, ResourceLocation texture, Item.Properties pProperties) {
	      super(pProperties);
	      this.protection = pProtection;
	      this.texture = texture;
	   }

	   public ResourceLocation getTexture() {
	      return texture;
	   }

	   public int getProtection() {
	      return this.protection;
	   }
	}