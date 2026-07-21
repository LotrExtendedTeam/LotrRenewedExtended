package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicates;
import net.minecraft.entity.player.PlayerEntity;

public interface ExtendedIInteractableEntity{
	/**
	 * Hacky getter for getting entity without requiring getters in all implementing
	 * Replace with ExtendedNPCEntity implementation when migrating to new NPC class
	 * @return NPCEntity instance for this entity
	 * 
	 * */
	default NPCEntity getNPC() {
		return (NPCEntity) this;
	}
	/**
     * Checks if the player is friendly with the trader.
     *
     * @param player The player to check.
     * @return True if the player is friendly, false otherwise.
     */
    default boolean isFriendly(PlayerEntity player) {
        return !LOTRMod.PROXY.getSidedAttackTarget(getNPC()).isPresent();
    }
    default boolean isFriendly(PlayerEntity player, NPCEntity entity) {
        return !LOTRMod.PROXY.getSidedAttackTarget(entity).isPresent();
    }
    
    default boolean isAlignedWithLevel(PlayerEntity player, float level) {
        return LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(getNPC().getFaction(), AlignmentPredicates.greaterThanOrEqual(level));
    }
	default boolean isFriendlyAndAlignedWithLevel(PlayerEntity player, float level) {
        return isFriendly(player, getNPC()) && isAlignedWithLevel(player, level);
    }
}