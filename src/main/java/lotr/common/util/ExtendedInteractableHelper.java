package lotr.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.Optional;
import java.util.UUID;

@Deprecated
public class ExtendedInteractableHelper {

    public static void removePlayerFromInteracting(PlayerEntity player, EntityDataManager eData, DataParameter<Optional<UUID>> pKey) {
        if (eData.get(pKey).isPresent() && eData.get(pKey).get().equals(player.getUUID())) {
            eData.set(pKey, Optional.empty());
        }
    }

    public static boolean isInteractingWithPlayer(Optional<UUID> uuid) {
        return uuid.isPresent();
    }
}
