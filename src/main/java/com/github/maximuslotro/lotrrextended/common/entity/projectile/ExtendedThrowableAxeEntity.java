package com.github.maximuslotro.lotrrextended.common.entity.projectile;

import lotr.common.entity.projectile.LOTRAbstractArrowEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Collection;
import java.util.Optional;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedDamageSource;

public class ExtendedThrowableAxeEntity extends LOTRAbstractArrowEntity {

    private static final DataParameter<ItemStack> THROWING_AXE_ITEM = EntityDataManager.defineId(ExtendedThrowableAxeEntity.class, DataSerializers.ITEM_STACK);

    public ExtendedThrowableAxeEntity(EntityType<? extends ExtendedThrowableAxeEntity> type, World w) {
        super(type, w);
        // Set smaller collision box - width and height in blocks
        this.setBoundingBox(this.getBoundingBox().inflate(-0.3, -0.3, -0.3));
    }

    public ExtendedThrowableAxeEntity(World w, LivingEntity thrower, ItemStack stack) {
        super((EntityType<? extends LOTRAbstractArrowEntity>) ExtendedEntities.THROWN_AXE.get(), thrower, w);
        ItemStack item = stack.copy();
        item.setCount(1);
        this.setThrowingAxeItem(item);
        // Set smaller collision box
        this.setBoundingBox(this.getBoundingBox().inflate(-0.3, -0.3, -0.3));
    }

    public ExtendedThrowableAxeEntity(World w, double x, double y, double z, ItemStack stack) {
        super((EntityType<? extends LOTRAbstractArrowEntity>) ExtendedEntities.THROWN_AXE.get(), x, y, z, w);
        ItemStack item = stack.copy();
        item.setCount(1);
        this.setThrowingAxeItem(item);
        // Set smaller collision box
        this.setBoundingBox(this.getBoundingBox().inflate(-0.3, -0.3, -0.3));
    }

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

    @Override
	protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(THROWING_AXE_ITEM, ItemStack.EMPTY);
    }

    public ItemStack getThrowingAxeItem() {
        return (ItemStack)this.entityData.get(THROWING_AXE_ITEM);
    }

    public void setThrowingAxeItem(ItemStack stack) {
        this.entityData.set(THROWING_AXE_ITEM, stack);
    }

    @Override
	protected ItemStack getPickupItem() {
        return this.getThrowingAxeItem().copy();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.inGround) {
            this.xRotO = this.xRot;
            this.xRot = this.xRot + 90.0F % 360.0F;
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity hitEntity = result.getEntity();
        int damage = this.calculateImpactDamageIncludingCritical(this.getBaseImpactDamage(hitEntity));
        Entity shooter = this.getOwner();
        DamageSource damageSource = ExtendedDamageSource.causeThrownAxeDamage(this, Optional.ofNullable(shooter).orElse(this));

        if (hitEntity.hurt(damageSource, (float) damage)) {
            if (this.isOnFire()) {
                hitEntity.setSecondsOnFire(5);
            }

            if (hitEntity instanceof LivingEntity) {
                LivingEntity hitLivingEntity = (LivingEntity) hitEntity;
                if (shooter instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(hitLivingEntity, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, hitLivingEntity);
                }
                this.doPostHurtEffects(hitLivingEntity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    private float getBaseImpactDamage(Entity hitEntity) {
        double speed = this.getDeltaMovement().length();
        ItemStack axeItem = this.getThrowingAxeItem();
        double damageMultiplier = this.getAxeItemAttackDamage(axeItem);
        damageMultiplier += (double)EnchantmentHelper.getDamageBonus(axeItem, hitEntity instanceof LivingEntity ? ((LivingEntity)hitEntity).getMobType() : CreatureAttribute.UNDEFINED);
        damageMultiplier *= 0.9;
        return (float)(speed * damageMultiplier);
    }

    private double getAxeItemAttackDamage(ItemStack stack) {
        Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE);
        double base = 1.0;
        if (modifiers != null) {
            for (AttributeModifier mod : modifiers) {
                base += mod.getAmount();
            }
        }
        return base;
    }

    @Override
	protected int getLifespanTicksInGround() {
        return this.pickup == PickupStatus.DISALLOWED ? 1200 : 6000;
    }

    @Override
	public void playerTouch(PlayerEntity player) {
        if (!this.level.isClientSide && (this.inGround || this.isNoPhysics()) && this.shakeTime <= 0) {
            // Don't allow pickup if owner is an mob
            Entity owner = this.getOwner();
            
            boolean canPickUp = (this.pickup == PickupStatus.ALLOWED
                    || this.pickup == PickupStatus.CREATIVE_ONLY && player.abilities.instabuild
                    || this.isNoPhysics()) && !(owner instanceof CreatureEntity);

            if (canPickUp) {
                if (this.pickup == PickupStatus.CREATIVE_ONLY) {
                    this.doPickUpAnimationAndRemove(player);
                } else {
                    ItemStack pickupStack = this.getPickupItem().copy();
                    pickupStack.hurtAndBreak(1, player, (p) -> {});
                    if (pickupStack.isEmpty()) {
                        this.playBreakSoundAndRemove();
                    } else if (player.inventory.add(pickupStack)) {
                        this.doPickUpAnimationAndRemove(player);
                    }
                }
            }
        }
    }

    private void doPickUpAnimationAndRemove(PlayerEntity player) {
        player.take(this, 1);
        this.remove();
    }

    private void playBreakSoundAndRemove() {
        this.playSound(SoundEvents.ITEM_BREAK, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
        this.remove();
    }

    @Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("ThrowingAxeItem", 10)) {
            this.setThrowingAxeItem(ItemStack.of(nbt.getCompound("ThrowingAxeItem")));
        }
    }

    @Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("ThrowingAxeItem", this.getThrowingAxeItem().save(new CompoundNBT()));
    }

    @Override
	protected float getWaterInertia() {
        return 0.99F;
    }
}