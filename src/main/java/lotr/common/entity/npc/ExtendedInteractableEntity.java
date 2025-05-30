package lotr.common.entity.npc;

import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicates;
import net.minecraft.entity.player.PlayerEntity;

public interface ExtendedInteractableEntity {

	default boolean isFriendlyAndAlignedWithLevel(PlayerEntity player, NPCEntity entity, float level) {
        return isFriendly(player, entity) && isAlignedWithLevel(player, entity, level);
    }
	
    default boolean isFriendly(PlayerEntity player, NPCEntity entity) {
        return entity.isNotFighting(player);
    }
    
    default boolean isAlignedWithLevel(PlayerEntity player, NPCEntity entity, float level) {
        return LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(entity.getFaction(), AlignmentPredicates.greaterThanOrEqual(level));
    }
}