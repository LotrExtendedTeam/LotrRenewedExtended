package com.github.maximuslotro.lotrrextended.common.entity.special;

import java.util.Locale;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnerEntry;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class ExtendedTraderRespawnEntity extends Entity{
	private boolean queueToRemove = false;
	private int setEntityTickBackRate = 3600;
	private int entityTickBacksRemaining = setEntityTickBackRate;
	public float zRot = 0;
	public float zRotO = 0;

	// TRADER settings;
	private CompoundNBT nbt = new CompoundNBT();
	private String traderType;
	private BlockPos homePos = BlockPos.ZERO;
	// if -1 assume no homeIsSet
	private int homeRadius = -1;

	public ExtendedTraderRespawnEntity(EntityType<? extends ExtendedTraderRespawnEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedTraderRespawnEntity(World pLevel) {
		this(ExtendedEntities.TRADER_RESPAWNER_ENTITY.get(), pLevel);
	}

	public void setTickValues(int tickBackRate) {
		setEntityTickBackRate = tickBackRate;
		entityTickBacksRemaining=setEntityTickBackRate;
	}

	public void setTraderType(EntityType<?> type) {
		traderType = type.getRegistryName().toString();
	}

	public void setTraderType(CompoundNBT compund) {
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
		this.zRotO = this.zRot;
		this.zRot += 0.8F;
		this.zRotO = MathHelper.wrapDegrees(this.zRotO);
		this.zRot = MathHelper.wrapDegrees(this.zRot);
		this.setDeltaMovement(Vector3d.ZERO);
		setInvulnerable(true);
		if(!level.isClientSide()) {
			if(queueToRemove) {
				this.remove();
			}else if(entityTickBacksRemaining==0) {
				spawnTrader((ServerWorld) this.level);
			}else {
				entityTickBacksRemaining -=1;
			}
		}
	}

	private void spawnTrader(ServerWorld world) {
		EntityType<?> traderEntityType =ExtendedStructureEntitySpawnerEntry.Serializer.getEntityTypeFromString(traderType);
		if(traderEntityType!=null) {
			Entity traderEntity = traderEntityType.create(world);
			traderEntity.moveTo(getX(), getY(), getZ());
			if(!(traderEntity instanceof ExtendedTraderEntity)) {
				queueToRemove = false;
				ExtendedLog.error("Trader Respawner tried spawning entity not of ExtendedTraderEntity! "+traderType +", with data: "+nbt.toString());
				return;
			}
			NPCEntity npcTrader = (NPCEntity) traderEntity;
			npcTrader.setPersistenceRequired();
			if(homeRadius != -1.0F) {
				npcTrader.restrictTo(homePos, homeRadius);
			}
			((ExtendedTraderEntity)npcTrader).loadAllTrades(nbt);
			queueToRemove = true;
		}else {
			ExtendedLog.error("Trader Respawner tried spawning unknown entity! "+traderType +", with data: "+nbt.toString());
			queueToRemove = false;
			return;
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		if(pCompound.contains("entityType")) {
			traderType = pCompound.getString("entityType");
		}else {
			queueToRemove=true;
		}
		if(pCompound.contains("ticksRemaining")) {
			entityTickBacksRemaining = pCompound.getInt("ticksRemaining");
		}else {
			entityTickBacksRemaining=setEntityTickBackRate;
		}
		if(pCompound.contains("ticksSet")) {
			setEntityTickBackRate = pCompound.getInt("ticksSet");
		}
		if(pCompound.contains("traderTrades")) {
			nbt = pCompound.getCompound("traderTrades");
		}else {
			new CompoundNBT();
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
		if(traderType !=null) {
			pCompound.putString("entityType", traderType);
		}else {
			queueToRemove=true;
		}
		pCompound.putInt("ticksRemaining", entityTickBacksRemaining);
		pCompound.putInt("ticksSet", setEntityTickBackRate);
		pCompound.put("traderTrades", nbt);
		if(homeRadius!= -1.0F) {
			pCompound.putInt("homeRadius", homeRadius);
			pCompound.put("homePos", NBTUtil.writeBlockPos(homePos));
		}
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
			pPlayer.sendMessage(new StringTextComponent("Spawner Entity: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(traderType!=null ? traderType : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			int tickPercentageInverse = entityTickBacksRemaining / setEntityTickBackRate;
			double percent = (1-tickPercentageInverse);
			pPlayer.sendMessage(new StringTextComponent("Current spawn percentage: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.format(Locale.US,"%.2f", percent*100)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
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
		SpawnEggItem egg = ForgeSpawnEggItem.fromEntityType(ExtendedStructureEntitySpawnerEntry.Serializer.getEntityTypeFromString(traderType));
		if(egg== null) {
			return ItemStack.EMPTY;
		}
		return new ItemStack(egg);
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
		return this.getName();
	}

	@Override
	public void remove() {
		if(!queueToRemove) {
			this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		}
		super.remove();
	}

	@Override
	protected void defineSynchedData() {
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
			Entity entity = pSource.getEntity();
			if(entity !=null && entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.instabuild) {
				if(((PlayerEntity)entity).isCrouching()) {
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
