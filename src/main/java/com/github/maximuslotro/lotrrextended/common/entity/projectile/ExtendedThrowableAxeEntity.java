package com.github.maximuslotro.lotrrextended.common.entity.projectile;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;

import lotr.common.init.ExtendedItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedThrowableAxeEntity extends ProjectileItemEntity {

    private int axeRotation;
    private ItemStack projectileItem;

    // Required constructor for entity registration
    public ExtendedThrowableAxeEntity(EntityType<? extends ExtendedThrowableAxeEntity> entityType, World world) {
        super(entityType, world);
    }
    
    // Constructor for when thrown by a player
    public ExtendedThrowableAxeEntity(EntityType<? extends ExtendedThrowableAxeEntity> entityType, World world, LivingEntity thrower) {
        super(entityType, world);
        this.setOwner(thrower);
    }

    // Constructor with position - use the correct ProjectileItemEntity constructor
    public ExtendedThrowableAxeEntity(EntityType<? extends ExtendedThrowableAxeEntity> entityType, World world, double x, double y, double z, ItemStack item) {
        super(entityType, world);
        this.setPos(x, y, z);
        this.projectileItem = item;
    }

    // Constructor with shooter
    public ExtendedThrowableAxeEntity(EntityType<? extends ExtendedThrowableAxeEntity> entityType, LivingEntity shooter, World world, ItemStack item) {
        super(entityType, world);
        this.setOwner(shooter);
        this.projectileItem = item;
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
    }

    private boolean isThrowingAxe() {
        Item item = getProjectileItem().getItem();
        return item instanceof ExtendedThrowableAxeItem;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isOnGround()) {
            this.axeRotation++;
            if (this.axeRotation > 9) {
                this.axeRotation = 0;
            }
            this.xRot = this.axeRotation / 9.0F * 360.0F;
        }
        if (!isThrowingAxe()) {
            remove();
        }
    }

    @Override
    protected float getGravity() {
        return 0.05F; // Slightly heavier than conker due to metal weight
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult rayTraceResult) {
        super.onHitEntity(rayTraceResult);
        
        // Calculate damage based on axe material and speed
        float damage = getBaseImpactDamage(rayTraceResult.getEntity(), getProjectileItem());
        
        // Play hit sound
        level.playSound(null, getX(), getY(), getZ(), 
            SoundEvents.PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 
            1.0F, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
        
        // Deal damage
        rayTraceResult.getEntity().hurt(
            DamageSource.thrown(this, this.getOwner()), 
            damage
        );
        
        // Damage the axe item when it hits an entity
        damageAxe();
    }

    @Override
    protected void onHit(RayTraceResult target) {
        super.onHit(target);
        
        if (!level.isClientSide) {
            // Play landing sound
            level.playSound(null, getX(), getY(), getZ(), 
                SoundEvents.ITEM_BREAK, SoundCategory.PLAYERS, 
                0.8F, 0.8F + (random.nextFloat() - random.nextFloat()) * 0.4F);
            
            // Drop the axe item (potentially damaged)
            if (projectileItem != null) {
                spawnAtLocation(projectileItem);
            }
            remove();
        }
    }

    public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
        if (!isThrowingAxe()) {
            return 0.0F;
        }
        float speed = MathHelper.sqrt(this.getDeltaMovement().x * this.getDeltaMovement().x + 
                                     this.getDeltaMovement().y * this.getDeltaMovement().y + 
                                     this.getDeltaMovement().z * this.getDeltaMovement().z);
        
        // Get damage multiplier from the axe item
        float damage = 1.0F;
        if (itemstack.getItem() instanceof ExtendedThrowableAxeItem) {
            ExtendedThrowableAxeItem axe = (ExtendedThrowableAxeItem) itemstack.getItem();
            damage = axe.getRangedDamageMultiplier(itemstack, (LivingEntity) this.getOwner(), entity);
        }
        
        return speed * damage;
    }

    public ItemStack getProjectileItem() {
        return projectileItem != null ? projectileItem : new ItemStack(getDefaultItem());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Damage the axe item when it hits something hard
     */
    private void damageAxe() {
        // In a real implementation, you'd want to track the axe's durability
        // and potentially break it or reduce its effectiveness
        // This is a simplified version
        
        if (random.nextFloat() < 0.1F) { // 10% chance to damage significantly
            // Could potentially break the axe or make it less effective
            // For now, we'll just keep the original axe
        }
    }

    /**
     * Get the axe item this entity represents
     */
    public ItemStack getAxeItemStack() {
        return this.projectileItem;
    }

    @Override
    protected Item getDefaultItem() {
        return ExtendedItems.IRON_THROWABLE_AXE.get();
    }
}