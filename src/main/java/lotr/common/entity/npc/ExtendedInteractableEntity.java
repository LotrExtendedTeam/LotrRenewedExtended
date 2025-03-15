package lotr.common.entity.npc;

import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicates;
import net.minecraft.entity.player.PlayerEntity;

public interface ExtendedInteractableEntity {

    default boolean isFriendlyAndAlignedWithLevel(PlayerEntity player, NPCEntity entity, float level) {
        return entity.isNotFighting(player) && LOTRLevelData.getSidedData(player).getAlignmentData()
                .hasAlignment(entity.getFaction(), AlignmentPredicates.greaterThanOrEqual(level));
    }
}