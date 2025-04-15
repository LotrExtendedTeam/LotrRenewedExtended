package com.github.maximuslotro.lotrrextended.common.entity.special;

import com.github.maximuslotro.lotrrextended.ExtendedServerProxy;

import lotr.common.init.ExtendedEntities;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedChairEntity extends Entity {

	public ExtendedChairEntity(EntityType<ExtendedChairEntity> pType, World pLevel) {
		super(pType, pLevel);
		this.noPhysics = true;
	}

	public ExtendedChairEntity(World pLevel) {
		this(ExtendedEntities.CHAIR.get(), pLevel);
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {}

	@Override
	public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
		remove();
		return new Vector3d(this.getX(), this.getBoundingBox().maxY+0.5D, this.getZ());
	}

	@Override
	public void remove() {
		ExtendedServerProxy.hackyChairManager.removeChairFromDatabase(level, blockPosition());
		super.remove();
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		return false;
	}

	/**
	 * Return true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean isPickable() {
		return false;
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
	public boolean isInvulnerable() {
		return true;
	}

	@Override
	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void defineSynchedData() {}
}
