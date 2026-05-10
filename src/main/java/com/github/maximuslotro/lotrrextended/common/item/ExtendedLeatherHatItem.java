package com.github.maximuslotro.lotrrextended.common.item;

import lotr.common.init.LOTRMaterial;
import lotr.common.item.LOTRArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;

public class ExtendedLeatherHatItem extends LOTRArmorItem implements IDyeableArmorItem  {

    public ExtendedLeatherHatItem() {
        super(LOTRMaterial.valueOf("COSMETIC_HAT"), EquipmentSlotType.HEAD, "leather_hat");
    }
}
