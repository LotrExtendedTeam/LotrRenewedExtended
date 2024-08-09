package com.github.maximuslotro.lotrrextended.common.entity.special;

import java.util.Locale;

import lotr.common.init.ExtendedItems;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
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

public class ExtendedTraderRespawnEntity extends Entity{
	private String traderType;
	private boolean queueToRemove = false;
	private int entityTickBackRate = 3600;
	public ExtendedTraderRespawnEntity(EntityType<? extends ExtendedTraderRespawnEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

//	public ExtendedTraderRespawnEntity(World pLevel) {
//		this(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), pLevel);
//	}

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
		setInvulnerable(true);
		if(!level.isClientSide()) {
			if(queueToRemove) {
				this.remove();
			}
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		if(pCompound.contains("firstTick")) {
			this.firstTick = pCompound.getBoolean("firstTick");
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		if(!this.firstTick) {
			pCompound.putBoolean("firstTick", false);
		}
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
			pPlayer.sendMessage(new StringTextComponent("Spawner Entity: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(traderType!=null ? traderType : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			int tickModulus = this.tickCount % entityTickBackRate;
			double percent = tickModulus/(float)entityTickBackRate ;
			pPlayer.sendMessage(new StringTextComponent("Current tick times: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(Integer.toString(tickModulus)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Current spawns percents: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.format(Locale.US,"%.2f", percent*100)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Current spawns sec till: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.format(Locale.US,"%.2f", (1-percent) * (entityTickBackRate/20.0F))).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Current spawns min till: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.format(Locale.US,"%.2f", (1-percent) * ((entityTickBackRate/20.0F)/60.0F))).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("In future will open GUI"), Util.NIL_UUID);
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		ItemStack stack = new ItemStack(ExtendedItems.ENTITY_SPAWNER.get());
//		if(!getSpawnerName().equals(ExtendedSpawners.EMPTY.toString())) {
//			if(stack.hasTag()) {
//				CompoundNBT nbt = stack.getTag();
//				nbt.putString("setSpawnerType", getSpawnerName());
//				stack.setTag(nbt);
//			} else {
//				CompoundNBT nbt = new CompoundNBT();
//				nbt.putString("setSpawnerType", getSpawnerName());
//				stack.setTag(nbt);
//			}
//		}
		return stack;
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
