package lotr.common.entity.npc;

import net.minecraft.entity.player.PlayerEntity;

public interface ExtendedCaptainEntity extends ExtendedInteractableEntity {

    boolean canHire(PlayerEntity player);

}