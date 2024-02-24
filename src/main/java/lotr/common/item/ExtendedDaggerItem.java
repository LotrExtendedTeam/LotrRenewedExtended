package lotr.common.item;

import lotr.common.init.LOTRMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedDaggerItem extends DaggerItem {
	public static final String poisoned = "poisoned";

	public ExtendedDaggerItem(LOTRMaterial material) {
		super(material);
	}

	public static boolean isPoisoned(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt != null && compoundnbt.contains(poisoned)) {
			return compoundnbt.getBoolean(poisoned);
		}
		return false;
	}

	public static void setPoisoned(ItemStack itemStack, boolean setpoisioned) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putBoolean(poisoned, setpoisioned);
	}
}
