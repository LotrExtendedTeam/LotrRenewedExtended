package com.github.maximuslotro.lotrrextended.common.entity.special;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(
		value = Dist.CLIENT,
		_interface = IRendersAsItem.class
)
public class ExtendedStructureSpawnerEntity extends Entity implements IRendersAsItem{

	public ExtendedStructureSpawnerEntity(EntityType<? extends ExtendedStructureSpawnerEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedStructureSpawnerEntity(World pLevel) {
		this(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), pLevel);
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!this.level.isClientSide()) {
			pPlayer.sendMessage(new StringTextComponent("Entity Clicked"), Util.NIL_UUID);
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack getItem() {
		return new ItemStack(ExtendedItems.HOBBIT_BANNER.get());
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		// TODO Auto-generated method stub
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected boolean isMovementNoisy() {
		return false;
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
	}

	@Override
	public boolean fireImmune() {
		return true;
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
	protected boolean canRide(Entity pEntity) {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldRender(double pX, double pY, double pZ) {
		if(this.level.isClientSide()) {
			Minecraft mc = Minecraft.getInstance();
			if(mc.player.isCreative()) {
				super.shouldRender(pX, pY, pZ);
			}
		}
		return false;
	}

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
