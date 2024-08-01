package com.github.maximuslotro.lotrrextended.common.entity.special;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedStructureSpawnerEntity extends Entity{
	private static final DataParameter<String> DATA_SPAWNER_TYPE = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.STRING);
	private static final DataParameter<BlockPos> DATA_SPAWNER_CBoundMin = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<BlockPos> DATA_SPAWNER_CBoundMax = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);

	private static final DataParameter<BlockPos> DATA_SPAWNER_SBoundMin = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<BlockPos> DATA_SPAWNER_SBoundMax = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);

	private ExtendedStructureEntitySpawner spawnerInstance = ExtendedStructureEntitySpawner.EMPTY;
	private ResourceLocation spawnerType;
	private static boolean isVisible = true; // Always true on Servers

	public ExtendedStructureSpawnerEntity(EntityType<? extends ExtendedStructureSpawnerEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedStructureSpawnerEntity(World pLevel) {
		this(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), pLevel);
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
			if (spawnerInstance.isEmptySpawner()) {
				if (spawnerType!=null && !spawnerType.equals(ExtendedSpawners.EMPTY)) {
					setSpawnerFromResourceLocation();
				}
			}
			if(!spawnerInstance.isEmptySpawner() && spawnerInstance.shouldTick()) {
				if(this.tickCount % spawnerInstance.getEntitySpawnTickRate() == 0) {
					trySpawningEntities();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void trySpawningEntities() {
		// Check if spawner bounds exist
		if(spawnerInstance.getSpawnType().isShouldCheckBoundries()) {
			if(!this.level.hasChunksAt(getCMinBlockPos(), getCMaxBlockPos())) {
				return;
			}
		}
		// Check if player has a proper distance from the spawner
		if(this.level.hasNearbyAlivePlayer(getX(), getY(), getZ(), spawnerInstance.getPlayerMinSpawnDistance())) {
			return;
		} else if (!this.level.hasNearbyAlivePlayer(getX(), getY(), getZ(), spawnerInstance.getPlayerMaxSpawnDistance())) {
			return;
		}
		AxisAlignedBB checkBox = new AxisAlignedBB(getCMinBlockPos(), getCMaxBlockPos());
		Predicate<LivingEntity> pred = (entity) ->{
			return ExtendedStructureEntitySpawnManager.INSTANCE.getEntities(spawnerInstance.getId().toString(), spawnerInstance.getEntries())
					.contains(entity.getType().getRegistryName().toString());
		};
		List<LivingEntity> entityTypes = this.level.getEntitiesOfClass(LivingEntity.class, checkBox, pred);
		// Add new parameter to spawner to set local spawn cap for entities
		if(entityTypes.size() < spawnerInstance.getLocalSpawnCap()) {
			
		}
	}

	private void setSpawnerFromResourceLocation() {
		spawnerInstance = ExtendedStructureEntitySpawnManager.INSTANCE.get(spawnerType);
		if(spawnerInstance.isEmptySpawner()) {
			ExtendedLog.warn("Structure Spawner loaded and invalid SpawnerType %s, spawner has been set to default %s preset!", spawnerType.toString(), ExtendedSpawners.EMPTY.toString());
		}
		this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerType.toString());
		reCalculateBounds();
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
			reCalculateBounds();
		} else if(pCompound.contains("setSpawnerType")) {
			spawnerType = new ResourceLocation(pCompound.getString("setSpawnerType"));
			this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerType.toString());
			reCalculateBounds();
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		if(!spawnerInstance.isEmptySpawner() && spawnerInstance.isCustom()) {
			pCompound.putString("spawnerData", ExtendedStructureEntitySpawnManager.serialize(ExtendedStructureEntitySpawnManager.GSON, spawnerInstance));
		} else if(!spawnerInstance.isEmptySpawner() && !spawnerInstance.isCustom()) {
			pCompound.putString("setSpawnerType", spawnerInstance.getId().toString());
		} else if(spawnerInstance.isEmptySpawner() && spawnerType !=null){
			pCompound.putString("setSpawnerType", spawnerType.toString());
		}else {
			pCompound.putString("setSpawnerType", ExtendedSpawners.EMPTY.toString());
		}
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
			pPlayer.sendMessage(new StringTextComponent("Spawner Raw Type: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(spawnerType!=null ? spawnerType.getPath() : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Spawner Instanced: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(spawnerInstance.getId().getPath()).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Is Empty Spawner: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(Boolean.toString(spawnerInstance.isEmptySpawner())).withStyle(TextFormatting.WHITE))
					.append(new StringTextComponent(", Is Ticking: ").withStyle(TextFormatting.GREEN))
					.append(new StringTextComponent(Boolean.toString(spawnerInstance.shouldTick())).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Local Spawn Cap: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(Integer.toString(spawnerInstance.getLocalSpawnCap())).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			String str = "";
			for(String entity : ExtendedStructureEntitySpawnManager.INSTANCE.getEntities(spawnerInstance.getId().toString(), spawnerInstance.getEntries())) {
				if(str.equals("")) {
					str = entity;
				}else {
					str = str+ ", "+entity;
				}
			}
			pPlayer.sendMessage(new StringTextComponent("Pool Entities: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(str).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			if(spawnerInstance.shouldTick()) {
				int tickModulus = this.tickCount % spawnerInstance.getEntitySpawnTickRate();
				double percent = tickModulus/(float)spawnerInstance.getEntitySpawnTickRate() ;
				pPlayer.sendMessage(new StringTextComponent("Current tick times: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(Integer.toString(tickModulus)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
				pPlayer.sendMessage(new StringTextComponent("Current spawns percents: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(String.format(Locale.US,"%.2f", percent*100)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
				pPlayer.sendMessage(new StringTextComponent("Current spawns sec till: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(String.format(Locale.US,"%.2f", (1-percent) * (spawnerInstance.getEntitySpawnTickRate()/20.0F))).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
				pPlayer.sendMessage(new StringTextComponent("Current spawns min till: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(String.format(Locale.US,"%.2f", (1-percent) * ((spawnerInstance.getEntitySpawnTickRate()/20.0F)/60.0F))).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			}
			pPlayer.sendMessage(new StringTextComponent("In future will open GUI"), Util.NIL_UUID);
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		ItemStack stack = new ItemStack(ExtendedItems.ENTITY_SPAWNER.get());
		if(!getSpawnerName().equals(ExtendedSpawners.EMPTY.toString())) {
			if(stack.hasTag()) {
				CompoundNBT nbt = stack.getTag();
				nbt.putString("setSpawnerType", getSpawnerName());
				stack.setTag(nbt);
			} else {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putString("setSpawnerType", getSpawnerName());
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
		if (pPlayer.isSpectator() || pPlayer.isCreative() || ExtendedClientConfig.enableDevMode.get()) {
			isVisible = false;
			return false;
		}
		isVisible = true;
		return true;
	}

	public BlockPos getCMinBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_CBoundMin);
	}
	public BlockPos getCMaxBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_CBoundMax);
	}
	public BlockPos getSMinBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_SBoundMin);
	}
	public BlockPos getSMaxBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_SBoundMax);
	}
	public String getSpawnerName() {
		return this.getEntityData().get(DATA_SPAWNER_TYPE);
	}
	public void reCalculateBounds() {
		this.getEntityData().set(DATA_SPAWNER_CBoundMin, spawnerInstance.getCheckBoundaries().getMinPos(getX(), getY(), getZ()));
		this.getEntityData().set(DATA_SPAWNER_CBoundMax, spawnerInstance.getCheckBoundaries().getMaxPos(getX(), getY(), getZ()));
		this.getEntityData().set(DATA_SPAWNER_SBoundMin, spawnerInstance.getSpawnBoundaries().getMinPos(getX(), getY(), getZ()));
		this.getEntityData().set(DATA_SPAWNER_SBoundMax, spawnerInstance.getSpawnBoundaries().getMaxPos(getX(), getY(), getZ()));
	}

	@Override
	public ITextComponent getDisplayName() {
		if(!getSpawnerName().equals(ExtendedSpawners.EMPTY.toString())) {
			return new StringTextComponent("Spawner type: "+getSpawnerName());
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
		this.getEntityData().define(DATA_SPAWNER_CBoundMin, BlockPos.ZERO);
		this.getEntityData().define(DATA_SPAWNER_CBoundMax, BlockPos.ZERO);
		this.getEntityData().define(DATA_SPAWNER_SBoundMin, BlockPos.ZERO);
		this.getEntityData().define(DATA_SPAWNER_SBoundMax, BlockPos.ZERO);
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
