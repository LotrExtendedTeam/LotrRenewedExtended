package com.github.maximuslotro.lotrrextended.common.entity.special;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnManager;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.SpawnerType;
import com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc.MixinNPCEntityInvoker;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedBannerEntity extends Entity{
	private static final DataParameter<Integer> SPAWNER_BOUNDS = EntityDataManager.defineId(ExtendedBannerEntity.class, DataSerializers.INT);

	private static boolean isInvisible = true; // Always true on Servers
	private boolean queueToRemove = false;
	private boolean firstTick = true;

	public ExtendedBannerEntity(EntityType<? extends ExtendedBannerEntity> pType, World pLevel) {
		super(pType, pLevel);
	}

	public ExtendedBannerEntity(World pLevel) {
		this(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), pLevel);
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
				firstTick = false;
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

	public int getSpawnerOutline() {
		return this.getEntityData().get(SPAWNER_BOUNDS);
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
