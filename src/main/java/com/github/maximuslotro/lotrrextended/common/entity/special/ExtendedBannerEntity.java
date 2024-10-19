package com.github.maximuslotro.lotrrextended.common.entity.special;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.tileentity.banner.ExtendedMEBannerTileEntity;

import lotr.common.init.ExtendedEntities;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedBannerEntity extends Entity {
	private static final DataParameter<Integer> SPAWNER_BOUNDS = EntityDataManager.defineId(ExtendedBannerEntity.class, DataSerializers.INT);
	private static boolean isInvisible = true; // Always true on Servers
	private boolean queueToRemove = false;

	public ExtendedBannerEntity(EntityType<? extends ExtendedBannerEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedBannerEntity(World pLevel) {
		this(ExtendedEntities.BANNER_HOLDER.get(), pLevel);
	}

	@Override
	public void tick() {
		this.noPhysics = true;
		//super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		this.setDeltaMovement(Vector3d.ZERO);
		setInvulnerable(true);
		if(!level.isClientSide()) {
			if(queueToRemove) {
				this.remove();
			}else {
				TileEntity tileentity = level.getBlockEntity(blockPosition());
		        if (tileentity instanceof ExtendedMEBannerTileEntity) {
		        	ExtendedMEBannerTileEntity banner = ((ExtendedMEBannerTileEntity)tileentity);
		        	if(banner.isActiveHalf()) {
		        		if(!this.getUUID().equals(banner.getEntityID())) {
			        		queueToRemove = true;
			        	}else {
			        		this.setSpawnerRange(banner.getProtectionRadius());
			        	}
		        	}else {
		        		queueToRemove = true;
		        	}
		        }else {
		        	queueToRemove = true;
		        }
			}
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
//		if(pCompound.contains("firstTick")) {
//			this.firstTick = pCompound.getBoolean("firstTick");
//		}
		if(pCompound.contains("protectionRange")) {
			setSpawnerRange(pCompound.getInt("protectionRange"));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
//		if(!this.firstTick) {
//			pCompound.putBoolean("firstTick", false);
//		}
		if(getSpawnerRanger()>0) {
			pCompound.putInt("protectionRange", getSpawnerRanger());
		}
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setInvisible(boolean pInvisible) {
		if(!this.level.isClientSide()) {
			super.setInvisible(pInvisible);
		}
	}

	@Override
	public boolean isInvisible() {
		if(!this.level.isClientSide()) {
			return super.isInvisible();
		}
		return isInvisible;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isInvisibleTo(PlayerEntity pPlayer) {
		if (pPlayer.isSpectator() || pPlayer.isCreative() || ExtendedClientConfig.enableDevMode.get()) {
			isInvisible = false;
			return false;
		}
		isInvisible = true;
		return true;
	}

	public int getSpawnerRanger() {
		return this.getEntityData().get(SPAWNER_BOUNDS);
	}

	public void setSpawnerRange(int range) {
		this.getEntityData().set(SPAWNER_BOUNDS, range);
	}

	/**
	 * Disable killing accidently with kill command
	 * */
	@Override
	public void kill() {}

	@Override
	public void remove() {
		if(!queueToRemove) {
			this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		}
		super.remove();
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(SPAWNER_BOUNDS, 0);
	}
	  
	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	public boolean ignoreExplosion() {
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
		return true;
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

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
