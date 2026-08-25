package com.github.maximuslotro.lotrrextended.common.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.world.World;

public class ExtendedCrabanEntity extends ExtendedBirdEntity {

    public ExtendedCrabanEntity(EntityType<? extends ExtendedCrabanEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute createCrabanAttributes() {
        return ExtendedBirdEntity.createBirdAttributes();
    }
}
