package lotr.common.entity.npc;

import lotr.common.entity.npc.data.ExtendedNpcCaptainInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcFarmhandInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import net.minecraft.item.ItemStack;

/**
 * Interface providing extended functionality and data access for NPC entities.
 */
public interface ExtendedINPCEntity {

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
	 * Retrieves the NPC's farmhand information.
	 *
	 * @return an {@link ExtendedNpcFarmhandInfoHolder} containing farmhand data
	 */
	ExtendedNpcFarmhandInfoHolder getFarmhandInfo();

	/**
	 * Retrieves the NPC's captain information.
	 *
	 * @return an {@link ExtendedNpcCaptainInfoHolder} containing captain data
	 */
	ExtendedNpcCaptainInfoHolder getCaptainInfo();

	/**
	 * Returns a valid non-standard food item for the NPC to consume.
	 *
	 * @return an {@link ItemStack} representing a valid other food item
	 */
	ItemStack getValidOtherFood();

	/**
	 * Returns itself, used for implementing interfaces
	 *
	 * @return an {@link NPCEntity} representing itself
	 */
	NPCEntity getNPC();
	/**
	 * Should NPC be able to pickup loot?
	 *
	 * @return True if should pickup
	 */
	boolean canPickUpLootExtended();
}