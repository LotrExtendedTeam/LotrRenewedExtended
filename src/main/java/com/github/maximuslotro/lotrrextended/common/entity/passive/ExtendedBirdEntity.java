package com.github.maximuslotro.lotrrextended.common.entity.passive;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.BirdEntityRandomFlyGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtendedBirdEntity extends CreatureEntity {

    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.defineId(ExtendedBirdEntity.class, DataSerializers.BOOLEAN);

    private final FlyingPathNavigator flyingNavigator;
    private final FlyingMovementController flyingController;
    private final MovementController walkingController;
    private final GroundPathNavigator groundNavigator;

    public float wingRotation = 0.0F;
    public float prevWingRotation = 0.0F;

    public float wingSpeed = 0.0F;
    public float prevWingSpeed = 0.0F;

    public ExtendedBirdEntity(EntityType<? extends ExtendedBirdEntity> type, World world) {
        super(type, world);

        this.flyingNavigator = new FlyingPathNavigator(this, world);
        this.groundNavigator = new GroundPathNavigator(this, world);
        this.flyingController = new FlyingMovementController(this, 20, true);
        this.walkingController = new  MovementController(this);

        // start as walking
        this.navigation = groundNavigator;
        this.moveControl = walkingController;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLYING, false);
    }

    public float getWingRotation() {
        return wingRotation;
    }

    public float getPrevWingRotation() {
        return prevWingRotation;
    }

    @Override
    public void tick() {
        super.tick();

        prevWingRotation = wingRotation;
        prevWingSpeed = wingSpeed;

        if (isFlying()) {
            wingSpeed = Math.min(wingSpeed + 0.15F, 1.0F);
        } else {
            wingSpeed = Math.max(wingSpeed - 0.15F, 0.0F);
        }

        wingRotation += wingSpeed * 0.8F;

        if (!level.isClientSide) {
            if (!this.onGround && !isFlying()) {
                startFlying();
            } else if (this.onGround && isFlying()) {
                stopFlying();
            }
        }
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
                .add(Attributes.FLYING_SPEED, 0.55F);
    }

    public void startFlying() {
        if (!isFlying()) {
            this.entityData.set(DATA_FLYING, true);
            applyFlyingState(true);
        }
    }

    public void stopFlying() {
        if (isFlying()) {
            this.entityData.set(DATA_FLYING, false);
            applyFlyingState(false);
        }
    }

    private void applyFlyingState(boolean flying) {
        this.navigation.stop();
        this.moveControl = flying ? flyingController : walkingController;
        this.navigation = flying ? flyingNavigator : groundNavigator;
        this.setNoGravity(flying);
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        if (DATA_FLYING.equals(pKey)) {
            applyFlyingState(this.entityData.get(DATA_FLYING));
        }
    }

    // birds are immune to fall damage
    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    public boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

    public boolean isOnGround() {
        return onGround;
    }
}
