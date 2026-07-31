package com.github.maximuslotro.lotrrextended.common.entity.passive;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.World;

public class ExtendedCrabanEntity extends CreatureEntity {

    public ExtendedCrabanEntity(EntityType<? extends ExtendedCrabanEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        // TODO: Add a flying goal
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute createCrabanAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.25F);
    }
}
