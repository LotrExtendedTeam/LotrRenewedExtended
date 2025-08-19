package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;

public class ExtendedDyeableBoarArmorItem extends ExtendedBoarArmorItem implements IDyeableArmorItem {
	   /**
	    * 
	    * @param pProtection the given protection level of the {@code HorseArmorItem}
	    * @param pIdentifier the texture path identifier for the {@code DyeableHorseArmorItem}, {@link ExtendedDyeableBoarArmorItem}
	    * @param pProperties the item properties
	    */
	   public ExtendedDyeableBoarArmorItem(int pProtection, String pIdentifier, Item.Properties pProperties) {
	      super(pProtection, pIdentifier, pProperties);
	   }
	   /**
	    * 
	    * @param pProtection the given protection level of the {@code HorseArmorItem}
	    * @param texture the texture path identifier for the {@code DyeableHorseArmorItem}, {@link ExtendedDyeableBoarArmorItem}
	    * @param pProperties the item properties
	    */
	   public ExtendedDyeableBoarArmorItem(int pProtection, net.minecraft.util.ResourceLocation texture, Item.Properties pProperties) {
	      super(pProtection, texture, pProperties);
	   }
	}