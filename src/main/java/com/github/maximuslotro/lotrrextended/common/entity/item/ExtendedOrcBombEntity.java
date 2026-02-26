package com.github.maximuslotro.lotrrextended.common.entity.item;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.item.MixinTNTEntityAccessor;

import lotr.common.init.ExtendedEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedOrcBombEntity extends TNTEntity {
	private static final DataParameter<Integer> DATA_STRENGTH = EntityDataManager.defineId(ExtendedOrcBombEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> DATA_FIRE = EntityDataManager.defineId(ExtendedOrcBombEntity.class, DataSerializers.BOOLEAN);
	private int strength = 0;
	private boolean fire = false;
	public ExtendedOrcBombEntity(EntityType<? extends ExtendedOrcBombEntity> pBlockEntity, World pWorld) {
		super(pBlockEntity, pWorld);
	}

	public ExtendedOrcBombEntity(World pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
		this(ExtendedEntities.ORC_BOMB.get(), pLevel);
		this.setPos(pX, pY, pZ);
		double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
		this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
		this.setFuse(80);
		this.xo = pX;
		this.yo = pY;
		this.zo = pZ;
		((MixinTNTEntityAccessor)this).setOwner(pOwner);
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("strength", this.getBombStrength());
		pCompound.putBoolean("fire", this.getBombFire());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);
		this.setStrength(pCompound.getInt("strength"));
		this.setFire(pCompound.getBoolean("fire"));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_STRENGTH, 0);
		this.entityData.define(DATA_FIRE, false);
	}

	public void setFire(boolean pFire) {
		this.entityData.set(DATA_FIRE, pFire);
		this.fire = pFire;
	}

	public void setStrength(int pStrength) {
		this.entityData.set(DATA_STRENGTH, pStrength);
		this.strength = pStrength;
	}

	@Override
	public void onSyncedDataUpdated(DataParameter<?> pKey) {
		super.onSyncedDataUpdated(pKey);
		if (DATA_STRENGTH.equals(pKey)) {
			this.strength = this.getBombStrength();
		}
		if (DATA_FIRE.equals(pKey)) {
			this.fire = this.getBombFire();
		}
	}
	public boolean getBombFire() {
		return this.entityData.get(DATA_FIRE);
	}

	public int getBombStrength() {
		return this.entityData.get(DATA_STRENGTH);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void explode() {
		float strength = 4.0F * (getBombStrength() + 1);// Add +1 to handle zero strength bombs (tier one)
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), strength, getBombFire(), Explosion.Mode.BREAK);
	}
}
