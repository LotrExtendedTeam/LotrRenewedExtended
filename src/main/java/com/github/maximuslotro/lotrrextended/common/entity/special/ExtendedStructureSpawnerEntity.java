package com.github.maximuslotro.lotrrextended.common.entity.special;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnManager;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedStructureSpawnerEntity extends Entity{
	private static final DataParameter<String> DATA_SPAWNER_TYPE = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.STRING);
	private ExtendedStructureEntitySpawner spawnerInstance = ExtendedStructureEntitySpawner.EMPTY;
	private ResourceLocation spawnerType;
	private static boolean isVisible = true; // Always true on Servers

	public ExtendedStructureSpawnerEntity(EntityType<? extends ExtendedStructureSpawnerEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedStructureSpawnerEntity(World pLevel) {
		this(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), pLevel);
	}

	private boolean isEmptySpawner() {
		return !spawnerInstance.equals(ExtendedStructureEntitySpawner.EMPTY);
	}

	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		this.yRotO = this.yRot;
		this.yRot += 0.8F;
		this.yRotO = MathHelper.wrapDegrees(this.yRotO);
		this.yRot = MathHelper.wrapDegrees(this.yRot);
		this.setDeltaMovement(Vector3d.ZERO);
		if(!level.isClientSide()) {
			if (isEmptySpawner()) {
				if (spawnerType!=null && !spawnerType.equals(ExtendedSpawners.EMPTY)) {
					setSpawnerFromResourceLocation();
				}
			}
			if(!isEmptySpawner() && spawnerInstance.shouldTick()) {
				if(this.tickCount % spawnerInstance.getEntitySpawnTickRate() == 0) {
					trySpawningEntities();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void trySpawningEntities() {
		// Check if spawner bounds exist
		BlockPos minPos = spawnerInstance.getCheckBoundaries().getMinPos(getX(), this.getY(), getZ());
		BlockPos maxPos = spawnerInstance.getCheckBoundaries().getMaxPos(getX(), getY(), getZ());
		if(spawnerInstance.getSpawnType().isShouldCheckBoundries()) {
			if(!this.level.hasChunksAt(minPos, maxPos)) {
				return;
			}
		}
		// Check if player has a proper distance from the spawner
		if(this.level.hasNearbyAlivePlayer(getX(), getY(), getZ(), spawnerInstance.getPlayerMinSpawnDistance())) {
			return;
		} else if (!this.level.hasNearbyAlivePlayer(getX(), getY(), getZ(), spawnerInstance.getPlayerMaxSpawnDistance())) {
			return;
		}
		AxisAlignedBB checkBox = new AxisAlignedBB(minPos, maxPos);
		Predicate<LivingEntity> pred = (entity) ->{
			// Check from new list in spawner of all available spawnable entities
			return true;
		};
		List<LivingEntity> entityTypes = this.level.getEntitiesOfClass(LivingEntity.class, getBoundingBox(), pred);
		// Add new parameter to spawner to set local spawn cap for entities
		if(entityTypes.size() < spawnerInstance.getLocalSpawnCap()) {
			
		}
	}

	private void setSpawnerFromResourceLocation() {
		spawnerInstance = ExtendedStructureEntitySpawnManager.INSTANCE.get(spawnerType);
		if(spawnerInstance.equals(ExtendedStructureEntitySpawner.EMPTY) || spawnerInstance.getId().equals(ExtendedSpawners.EMPTY)) {
			ExtendedLog.warn("Structure Spawner loaded and invalid SpawnerType %s, spawner has been set to default %s preset!", spawnerType.toString(), ExtendedSpawners.EMPTY.toString());
		}
		this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerType.toString());
	}

	@Nullable
	public ExtendedStructureEntitySpawner getSpawnerInstance() {
		return spawnerInstance;
	}

	public void setSpawnerToDefaultPreset(ResourceLocation preset, boolean isCreation) {
		spawnerType = preset;
		if(!isCreation) {
			setSpawnerFromResourceLocation();
		}else {
			spawnerInstance = ExtendedStructureEntitySpawner.EMPTY;
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		if(pCompound.contains("spawnerData")) {
			spawnerInstance = ExtendedStructureEntitySpawnManager.deserialize(ExtendedStructureEntitySpawnManager.GSON, pCompound.getString("spawnerData"), ExtendedStructureEntitySpawner.class);
			this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerInstance.getId().toString());
		} else if(pCompound.contains("setSpawnerType")) {
			spawnerType = new ResourceLocation(pCompound.getString("setSpawnerType"));
			this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerType.toString());
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		if(!isEmptySpawner() && spawnerInstance.isCustom()) {
			pCompound.putString("spawnerData", ExtendedStructureEntitySpawnManager.serialize(ExtendedStructureEntitySpawnManager.GSON, spawnerInstance));
		} else if(!isEmptySpawner() && !spawnerInstance.isCustom()) {
			pCompound.putString("setSpawnerType", spawnerInstance.getId().toString());
		} else if(isEmptySpawner() && spawnerType !=null){
			pCompound.putString("setSpawnerType", spawnerType.toString());
		}else {
			pCompound.putString("setSpawnerType", ExtendedSpawners.EMPTY.toString());
		}
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
			pPlayer.sendMessage(new StringTextComponent("Spawner Type: " +(spawnerType!=null ? spawnerType.toString() : "Unset")), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("In future will open GUI"), Util.NIL_UUID);
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		ItemStack stack = new ItemStack(ExtendedItems.ENTITY_SPAWNER.get());
		if(spawnerType!=null) {
			if(stack.hasTag()) {
				CompoundNBT nbt = stack.getTag();
				nbt.putString("setSpawnerType", spawnerType.toString());
				stack.setTag(nbt);
			} else {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putString("setSpawnerType", spawnerType.toString());
				stack.setTag(nbt);
			}
		}
		return stack;
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
		return isVisible;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isInvisibleTo(PlayerEntity pPlayer) {
		if (pPlayer.isSpectator() || pPlayer.isCreative()) {
			isVisible = false;
			return false;
		}
		isVisible = true;
		return true;
	}

	@Override
	public ITextComponent getDisplayName() {
		if(!this.getEntityData().get(DATA_SPAWNER_TYPE).equals(ExtendedSpawners.EMPTY.toString())) {
			return new StringTextComponent("Spawner type: "+this.getEntityData().get(DATA_SPAWNER_TYPE));
		}
		return this.getName();
	}

	@Override
	public void remove() {
		this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		super.remove();
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_SPAWNER_TYPE, ExtendedSpawners.EMPTY.toString());
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
		if (this.isInvulnerableTo(pSource)) {
			return false;
		} else {
			this.remove();
			return false;
		}
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
