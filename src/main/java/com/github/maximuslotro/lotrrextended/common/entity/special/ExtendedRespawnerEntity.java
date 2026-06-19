package com.github.maximuslotro.lotrrextended.common.entity.special;

import java.util.Locale;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnerEntry;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedRespawnerEntity extends Entity{
	private static final DataParameter<String> DATA_SPAWNER_TYPE = EntityDataManager.defineId(ExtendedRespawnerEntity.class, DataSerializers.STRING);
	private static final DataParameter<Integer> TICKPERCENTAGE = EntityDataManager.defineId(ExtendedRespawnerEntity.class, DataSerializers.INT);

	private boolean queueToRemove = false;
	private int setEntityTickBackRate = 600;
	private int entityTickBacksRemaining = setEntityTickBackRate;
	private int loadTicks = 0; // if loaded when entityTickBacksRemaining==0, prevent instant trader spawning if banner suppressed, 5 second delay 
	public double spin = 0;
	public double spinO = 0;
	// TRADER settings;
	private CompoundNBT nbt = new CompoundNBT();
	private String entityType;
	private BlockPos homePos = BlockPos.ZERO;
	// if -1 assume no homeIsSet
	private int homeRadius = -1;

	public ExtendedRespawnerEntity(EntityType<? extends ExtendedRespawnerEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedRespawnerEntity(World pLevel) {
		this(ExtendedEntities.RESPAWNER_ENTITY.get(), pLevel);
	}

	public void setTickValues(int tickBackRate) {
		setEntityTickBackRate = tickBackRate;
		entityTickBacksRemaining=setEntityTickBackRate;
		this.getEntityData().set(TICKPERCENTAGE, entityTickBacksRemaining);
	}

	public void setEntityType(EntityType<?> type) {
		entityType = type.getRegistryName().toString();
		this.getEntityData().set(DATA_SPAWNER_TYPE, entityType);
	}

	public void setEntityData(CompoundNBT compund) {
		nbt = compund;
	}

	public void setHomeInfo(BlockPos homePos, int homeRadius) {
		this.homeRadius = homeRadius;
		this.homePos = homePos;
	}

	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		if(this.level.isClientSide()) {
			this.spinO = this.spin;
			// Monster Spawner Logic
			this.spin = (this.spin + (isRespawnerTicking() ? 1000.0F / getTickpercentage() : 60)) % 360.0D;
		}
		this.setDeltaMovement(Vector3d.ZERO);
		setInvulnerable(true);
		if(this.tickCount % 20 == 0 && !level.isClientSide()) {
			this.getEntityData().set(TICKPERCENTAGE, entityTickBacksRemaining);
		}
		if(!level.isClientSide()) {
			if(queueToRemove) {
				this.remove();
			}else if(entityTickBacksRemaining==0) {
				loadTicks+=1;
				if(loadTicks == 100){
					queueToRemove = attemptEntityRespawn((ServerWorld) this.level);
					loadTicks=0;
				}
			}else {
				entityTickBacksRemaining -=1;
			}
		}
	}

	private boolean attemptEntityRespawn(ServerWorld world) {
		EntityType<?> traderEntityType =ExtendedStructureEntitySpawnerEntry.Serializer.getEntityTypeFromString(entityType);
		if(traderEntityType!=null) {
			Entity spawnedEntity = traderEntityType.create(world);
			spawnedEntity.moveTo(getX(), getY(), getZ());
			// check spawn rules (banner protection for SPAWNER types)
			if (spawnedEntity instanceof MobEntity && !((MobEntity)spawnedEntity).checkSpawnRules(world, SpawnReason.SPAWNER)) {
				// Disable respawn under opposing banner protection
            	return false;
            }
			if(spawnedEntity instanceof NPCEntity) {
				NPCEntity npcEntity = (NPCEntity) spawnedEntity;
				npcEntity.finalizeSpawn(world, world.getCurrentDifficultyAt(this.getOnPos()), SpawnReason.SPAWNER, null, null);
				npcEntity.setPersistenceRequired(); // Only persisted npcs can even have a chance to respawn. If it made its way here, it should be able to be persisted
				if(homeRadius > 0) {
					npcEntity.restrictTo(homePos, homeRadius);
				}
			}
			if(spawnedEntity instanceof ExtendedTraderEntity) {
				((ExtendedTraderEntity)spawnedEntity).getTraderData().loadFromNbt(nbt);
			}
			if(spawnedEntity instanceof ExtendedCaptainEntity) {
				// IDK maybe captains have data to save in the future
			}
			world.addFreshEntityWithPassengers(spawnedEntity);
			return true;
		}else {
			ExtendedLog.error("Entity Respawner tried spawning unknown entity {}! ", entityType);
			ExtendedLog.error("Dumping data for staff reconstruction: "+nbt.toString());
			return true;
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		if(pCompound.contains("entityType")) {
			entityType = pCompound.getString("entityType");
			this.getEntityData().set(DATA_SPAWNER_TYPE, entityType);
		}else {
			queueToRemove=true;
		}
		if(pCompound.contains("ticksRemaining")) {
			entityTickBacksRemaining = pCompound.getInt("ticksRemaining");
		}else {
			entityTickBacksRemaining=setEntityTickBackRate;
		}
		this.getEntityData().set(TICKPERCENTAGE, entityTickBacksRemaining);
		if(pCompound.contains("ticksSet")) {
			setEntityTickBackRate = pCompound.getInt("ticksSet");
		}
		if(pCompound.contains("traderTrades")) {
			nbt = pCompound.getCompound("traderTrades");
		}else {
			nbt = new CompoundNBT();
		}
		if(pCompound.contains("homeRadius")) {
			homeRadius = pCompound.getInt("homeRadius");
		}
		if(pCompound.contains("homePos")) {
			homePos = NBTUtil.readBlockPos(pCompound.getCompound("homePos"));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		if(entityType !=null) {
			pCompound.putString("entityType", entityType);
		}else {
			queueToRemove=true;
		}
		pCompound.putInt("ticksRemaining", entityTickBacksRemaining);
		pCompound.putInt("ticksSet", setEntityTickBackRate);
		if(!nbt.isEmpty()) {
			pCompound.put("traderTrades", nbt);
		}
		if(homeRadius!= -1.0F) {
			pCompound.putInt("homeRadius", homeRadius);
			pCompound.put("homePos", NBTUtil.writeBlockPos(homePos));
		}
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
			pPlayer.sendMessage(new StringTextComponent("Spawner Entity: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(entityType!=null ? entityType : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			float percent = 1.0F -((float)entityTickBacksRemaining / (float)setEntityTickBackRate);
			pPlayer.sendMessage(new StringTextComponent("Current spawn percentage: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.format(Locale.US,"%.2f", percent*100.0F)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Current spawn ticks till: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(Integer.toString(entityTickBacksRemaining)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Current spawns sec till: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.format(Locale.US,"%.2f", entityTickBacksRemaining/20.0F)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Current spawns min till: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.format(Locale.US,"%.2f", (entityTickBacksRemaining/20.0F)/60.0F)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("In future will open GUI"), Util.NIL_UUID);
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		if(getSpawnerName()!=null) {
			SpawnEggItem egg = ForgeSpawnEggItem.fromEntityType(ExtendedStructureEntitySpawnerEntry.Serializer.getEntityTypeFromString(getSpawnerName()));
			if(egg==null) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(egg);
		}
		return ItemStack.EMPTY;
	}
	@Override
	public void setInvisible(boolean pInvisible) {
		super.setInvisible(false);
	}

	@Override
	public boolean isInvisible() {
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isInvisibleTo(PlayerEntity pPlayer) {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Respawner: "+getSpawnerName());
	}

	@Override
	public void remove() {
		if(!queueToRemove) {
			this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		}
		super.remove();
	}

	public String getSpawnerName() {
		return this.getEntityData().get(DATA_SPAWNER_TYPE);
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_SPAWNER_TYPE, "empty");
		this.getEntityData().define(TICKPERCENTAGE, 3600);
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
			if(pSource.getEntity()!=null) {
				if(pSource.isCreativePlayer()) {
					this.remove();
				}
			}else {
				this.remove();
			}
			return false;
		}
	}

	/**
	 * Return true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean isPickable() {
		if (!this.level.isClientSide()) {
			return false;
		}
		PlayerEntity entityplayer = ExtendedClientProxy.INSTANCE.getClientPlayerEntity();
		return (entityplayer != null && entityplayer.isCreative());
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

	public int getTickpercentage() {
		return this.getEntityData().get(TICKPERCENTAGE);
	}

	public boolean isRespawnerTicking() {
		return this.getEntityData().get(TICKPERCENTAGE)>0;
	}
}
