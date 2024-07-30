package com.github.maximuslotro.lotrrextended.common.entity.special;

import lotr.common.init.ExtendedEntities;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedStructureSpawnerEntity extends Entity{
	private ResourceLocation spawnerType;
	public ExtendedStructureSpawnerEntity(EntityType<? extends ExtendedStructureSpawnerEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedStructureSpawnerEntity(World pLevel) {
		this(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), pLevel);
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
			if(pPlayer.isCrouching() && pPlayer.getMainHandItem().isEmpty()) {
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
				pPlayer.setItemInHand(Hand.MAIN_HAND, stack);
			}
			pPlayer.sendMessage(new StringTextComponent("Entity Clicked"), Util.NIL_UUID);
			this.remove();
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	@Override
	public ITextComponent getDisplayName() {
		if(spawnerType!=null) {
			return new StringTextComponent("Spawner type: "+spawnerType.toString());
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
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		if(pCompound.contains("setSpawnerType")) {
			spawnerType = new ResourceLocation(pCompound.getString("setSpawnerType"));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		if(spawnerType!=null) {
			pCompound.putString("setSpawnerType", spawnerType.toString());
		}
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
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
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		this.yRotO = this.yRot;
		this.yRot += 0.8F;
		this.yRotO = MathHelper.wrapDegrees(this.yRotO);
		this.yRot = MathHelper.wrapDegrees(this.yRot);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isInvisibleTo(PlayerEntity pPlayer) {
		if (pPlayer.isSpectator() || pPlayer.isCreative()) {
			return false;
		}
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

//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public boolean shouldRender(double pX, double pY, double pZ) {
//		if(this.level.isClientSide()) {
//			Minecraft mc = Minecraft.getInstance();
//			if(mc.player != null && mc.player.isCreative()) {
//				super.shouldRender(pX, pY, pZ);
//			}
//		}
//		return false;
//	}

//	@Override
//	public boolean isInvisible() {
//		if(this.level.isClientSide()) {
//			Minecraft mc = Minecraft.getInstance();
//			if(mc.player.isCreative()) {
//				super.isInvisible();
//			}
//		}
//		return false;
//	}

	@Override
	public boolean isInvulnerable() {
		return true;
	}

	@Override
	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}
}
