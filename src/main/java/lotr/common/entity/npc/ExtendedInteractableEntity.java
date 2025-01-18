package lotr.common.entity.npc;

import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicates;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;
import java.util.UUID;

public interface ExtendedInteractableEntity {

    boolean isInteractingWithPlayer();

    default boolean isFriendlyAndAlignedWithLevel(PlayerEntity player, NPCEntity entity, float level) {
        return entity.isNotFighting(player) && LOTRLevelData.getSidedData(player).getAlignmentData()
                .hasAlignment(entity.getFaction(), AlignmentPredicates.greaterThanOrEqual(level));
    }

    Optional<UUID> getPlayerInteractingWith();

    void updatePlayerInteractingWith(UUID uuid);

    void removePlayerFromInteracting(PlayerEntity player);
}
