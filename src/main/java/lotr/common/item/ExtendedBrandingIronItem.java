package lotr.common.item;

import lotr.common.init.LOTRItemGroups;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedBrandingIronItem extends Item{
	public static final String NBTvalue = "heated";

	public ExtendedBrandingIronItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	public ExtendedBrandingIronItem() {
		super(new Properties().stacksTo(1).tab(LOTRItemGroups.TOOLS).durability(100));
	}

	public static boolean isNotEmpty(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(NBTvalue);
	}

	public static boolean isTempered(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return isNotEmpty(itemStack) && compoundnbt.getBoolean(NBTvalue);
	}

	public static void setType(ItemStack itemStack, boolean type) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putBoolean(NBTvalue, type);
	}
}
