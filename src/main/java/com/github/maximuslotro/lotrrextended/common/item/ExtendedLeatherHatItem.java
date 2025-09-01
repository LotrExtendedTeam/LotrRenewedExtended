package com.github.maximuslotro.lotrrextended.common.item;

import lotr.common.fac.Faction;
import lotr.common.init.LOTRMaterial;
import lotr.common.item.ExtendedDyeableLOTRArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedLeatherHatItem extends ExtendedDyeableLOTRArmorItem  {

    public ExtendedLeatherHatItem() {
        super(LOTRMaterial.COSMETIC, EquipmentSlotType.HEAD, "leather_hat");
    }

    public static boolean isHatDyed(ItemStack pStack) {
        CompoundNBT compoundnbt = pStack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("color", 99);
    }

    public static int getHatColor(ItemStack pStack) {
        CompoundNBT compoundnbt = pStack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
    }

    public static void setHatDyedByFaction(ItemStack stack, Faction faction) {
        stack.getOrCreateTagElement("display").putInt("color", faction.getColor());
        //nbt.putInt("Color", faction.getColor());
        // TODO: Use this to add the lore text where it says "Dyed by High Elves" for example
        //  for hats like it does for pouches
        //nbt.putString("ColorFaction", faction.getName().toString());
    }

    public static void setHatDyedColor(ItemStack pStack, int pColor) {
        pStack.getOrCreateTagElement("display").putInt("color", pColor);
    }
}
