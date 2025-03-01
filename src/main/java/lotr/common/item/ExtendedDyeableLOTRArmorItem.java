package lotr.common.item;

import lotr.common.init.LOTRMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;

public class ExtendedDyeableLOTRArmorItem extends LOTRArmorItem implements IDyeableArmorItem {

	public ExtendedDyeableLOTRArmorItem(LOTRMaterial material, EquipmentSlotType slot, String specialTex) {
		super(material, slot, specialTex);
	}
}
