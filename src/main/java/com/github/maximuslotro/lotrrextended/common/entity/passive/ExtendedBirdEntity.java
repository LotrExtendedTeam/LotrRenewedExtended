package com.github.maximuslotro.lotrrextended.common.entity.passive;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.BirdEntityRandomFlyGoal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;

public class ExtendedBirdEntity extends CreatureEntity {

    private boolean isFlying = false;

    private final FlyingPathNavigator flyingNavigator;
    private final FlyingMovementController flyingController;
    private final MovementController walkingController;
    private final GroundPathNavigator groundNavigator;

    public ExtendedBirdEntity(EntityType<? extends ExtendedCrabanEntity> type, World world) {
        super(type, world);

        this.flyingNavigator = new FlyingPathNavigator(this, world);
        this.groundNavigator = new GroundPathNavigator(this, world);
        this.flyingController = new FlyingMovementController(this, 20, true);
        this.walkingController = new  MovementController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new BirdEntityRandomFlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createBirdAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.FLYING_SPEED, 0.25F);
    }

    public void startFlying() {
        this.navigation.stop();
        this.isFlying = true;

        this.moveControl = flyingController;
        this.navigation = flyingNavigator;

        this.setNoGravity(true);
    }

    public void stopFlying() {
        this.navigation.stop();
        this.isFlying = false;

        this.moveControl = walkingController;
        this.navigation = groundNavigator;

        this.setNoGravity(false);
    }

    public void aiStep() {
        super.aiStep();
    }

    public boolean isFlying() {
        return this.isFlying;
    }
}
