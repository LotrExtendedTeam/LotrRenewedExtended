package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

/**
 * This custom AI class is used for entities who can switch between shields on their back and in their hands when they
 * fight. The main entity that can do this so far is the Bree-land Sheriff.
 */
public class ExtendedShieldSwapMeleeAttackGoal extends NPCMeleeAttackGoal {

    private final ItemStack shield;
    private final ItemStack idleOffhand;
    private final NPCEntity swappableShieldEntity;

    public ExtendedShieldSwapMeleeAttackGoal(NPCEntity entity, double speed, ItemStack shield, ItemStack idleOffhand) {
        super(entity, speed);
        swappableShieldEntity = entity;

        this.shield = shield;
        this.idleOffhand = idleOffhand;
    }

    @Override
    public void start() {
        super.start();
        this.swappableShieldEntity.setItemSlot(EquipmentSlotType.OFFHAND, shield);
    }

    @Override
    public void stop() {
        super.stop();
        this.swappableShieldEntity.setItemSlot(EquipmentSlotType.OFFHAND, idleOffhand);
    }

}
