package lotr.common.entity.npc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public interface ExtendedCaptainEntity extends ExtendedInteractableEntity {

    boolean canHire(PlayerEntity player);

    int getCost(ResourceLocation breeGuard, PlayerEntity player);

}