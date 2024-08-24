package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;

public interface ExtendedRidableWargEntity {
	public boolean canWearArmor();

	public boolean isWearingArmor();

	public boolean isArmor(ItemStack pStack);
}
