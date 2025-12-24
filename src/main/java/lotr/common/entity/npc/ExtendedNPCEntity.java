package lotr.common.entity.npc;

import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import net.minecraft.item.ItemStack;

/**
 * Interface providing extended functionality and data access for NPC entities.
 */
public interface ExtendedNPCEntity {

    /**
     * Retrieves the NPC's marriage information.
     *
     * @return an {@link ExtendedNpcMarriageHolder} containing marriage-related data
     */
    ExtendedNpcMarriageHolder getMarriageInfo();

    /**
     * Retrieves the NPC's trading information.
     *
     * @return an {@link ExtendedNpcTraderInfoHolder} containing trade-related data
     */
    ExtendedNpcTraderInfoHolder getTraderInfo();

    /**
     * Retrieves the NPC's hired unit information.
     *
     * @return an {@link ExtendedNpcHiredUnitInfoHolder} containing unit-related data
     */
    ExtendedNpcHiredUnitInfoHolder getUnitInfo();

    /**
     * Returns a valid non-standard food item for the NPC to consume.
     *
     * @return an {@link ItemStack} representing a valid other food item
     */
    ItemStack getValidOtherFood();
}