package lotr.common.entity.npc;

import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import net.minecraft.item.ItemStack;

public interface ExtendedNPCEntity {
	public ExtendedNpcMarriageHolder getMarriageInfo();

	public ExtendedNpcTraderInfoHolder getTraderInfo();

	public ExtendedNpcHiredUnitInfoHolder getUnitInfo();

	public ItemStack getValidOtherFood();
}
