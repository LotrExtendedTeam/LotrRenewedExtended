package com.github.maximuslotro.lotrrextended.common.entity.special;

import lotr.common.fac.FactionType;
import lotr.common.init.ExtendedEntities;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedInvasionSpawnerEntity extends Entity {

    private static final DataParameter<String> DATA_FACTION_KEY = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_ENEMIES_LEFT = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> DATA_INVASION_SIZE = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.INT);

    private static final float ROTATION_SPEED = 1.0F;

    public ExtendedInvasionSpawnerEntity(EntityType<? extends ExtendedInvasionSpawnerEntity> pType, World pLevel) {
        super(pType, pLevel);
    }

    public ExtendedInvasionSpawnerEntity(World pLevel) {
        this(ExtendedEntities.INVASION_SPAWNER.get(), pLevel);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_FACTION_KEY, "empty");
        this.getEntityData().define(DATA_ENEMIES_LEFT, 0);
        this.getEntityData().define(DATA_INVASION_SIZE, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        if(compoundNBT.contains("faction_key")) {
            this.getEntityData().set(DATA_FACTION_KEY, compoundNBT.getString("faction_key"));
        }
        if(compoundNBT.contains("enemies_left")) {
            this.getEntityData().set(DATA_ENEMIES_LEFT, compoundNBT.getInt("enemies_left"));
        }
        if(compoundNBT.contains("invasion_size")) {
            this.getEntityData().set(DATA_INVASION_SIZE, compoundNBT.getInt("invasion_size"));
        }
    }

    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);

        if(!this.level.isClientSide()) {
            this.yRot += ROTATION_SPEED;

            if (this.yRot >= 360.0F) {
                this.yRot -= 360.0F;
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putString("faction_key", getFactionKey());
        compoundNBT.putInt("invasion_size", getInvasionSize());
        compoundNBT.putInt("enemies_left", getEnemiesLeft());
    }

    public void setFaction(String factionKey) {
        this.getEntityData().set(DATA_FACTION_KEY, factionKey);
    }

    public String getFactionKey() {
        return this.getEntityData().get(DATA_FACTION_KEY);
    }

    public int getEnemiesLeft() {
        return this.getEntityData().get(DATA_ENEMIES_LEFT);
    }

    public int getInvasionSize() {
        return this.getEntityData().get(DATA_INVASION_SIZE);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Invasion Spawner: " + getFactionKey());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            Entity entity = pSource.getEntity();
            if(entity !=null && entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.instabuild) {
                if(entity.isCrouching()) {
                    this.remove();
                }
            }else {
                this.remove();
            }
            return false;
        }
    }

    @Override
    public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
        if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
            pPlayer.sendMessage(new StringTextComponent("Faction Key: ").withStyle(TextFormatting.GREEN)
                    .append(new StringTextComponent(getFactionKey() !=null ? getFactionKey() : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
            pPlayer.sendMessage(new StringTextComponent("UUID: ").withStyle(TextFormatting.GREEN)
                    .append(new StringTextComponent(getStringUUID())).withStyle(TextFormatting.WHITE), Util.NIL_UUID);
        }
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }

    /**
     * Return true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected boolean canRide(Entity pEntity) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

}
