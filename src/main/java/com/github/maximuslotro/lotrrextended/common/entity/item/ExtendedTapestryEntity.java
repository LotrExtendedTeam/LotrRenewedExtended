package com.github.maximuslotro.lotrrextended.common.entity.item;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedTapestryType;
import com.google.common.collect.Lists;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedTapestryEntity extends HangingEntity implements IEntityAdditionalSpawnData {
	public ExtendedTapestryType type;

	public ExtendedTapestryEntity(EntityType<? extends ExtendedTapestryEntity> pEntityType, World pLevel) {
		super(pEntityType, pLevel);
	}

	public ExtendedTapestryEntity(World pLevel, BlockPos pPos, Direction pFacingDirection) {
		super(ExtendedEntities.TAPESTRY.get(), pLevel, pPos);
		List<ExtendedTapestryType> list = Lists.newArrayList();
		int i = 0;

		for(ExtendedTapestryType paintingtype : ExtendedTapestryType.TAPESTRIE_REGISTRY.get().getValues()) {
			this.type = paintingtype;
			this.setDirection(pFacingDirection);
			if (this.survives()) {
				list.add(paintingtype);
				int j = paintingtype.getWidth() * paintingtype.getHeight();
				if (j > i) {
					i = j;
				}
			}
		}

		if (!list.isEmpty()) {
			Iterator<ExtendedTapestryType> iterator = list.iterator();

			while(iterator.hasNext()) {
				ExtendedTapestryType paintingtype1 = iterator.next();
				if (paintingtype1.getWidth() * paintingtype1.getHeight() < i) {
					iterator.remove();
				}
			}

			this.type = list.get(this.random.nextInt(list.size()));
		}

		this.setDirection(pFacingDirection);
	}

	@OnlyIn(Dist.CLIENT)
	public ExtendedTapestryEntity(World pLevel, BlockPos pPos, Direction pFacingDirection, ExtendedTapestryType pMotive) {
		this(pLevel, pPos, pFacingDirection);
		this.type = pMotive;
		this.setDirection(pFacingDirection);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		pCompound.putString("Type", ExtendedTapestryType.TAPESTRIE_REGISTRY.get().getKey(this.type).toString());
		pCompound.putByte("Facing", (byte)this.direction.get2DDataValue());
		super.addAdditionalSaveData(pCompound);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		this.type = ExtendedTapestryType.TAPESTRIE_REGISTRY.get().getValue(ResourceLocation.tryParse(pCompound.getString("Type")));
		this.direction = Direction.from2DDataValue(pCompound.getByte("Facing"));
		super.readAdditionalSaveData(pCompound);
		this.setDirection(this.direction);
	}

	@Override
	public int getWidth() {
		return this.type == null ? 1 : this.type.getWidth();
	}

	@Override
	public int getHeight() {
		return this.type == null ? 1 : this.type.getHeight();
	}

	@Override
	public void dropItem(@Nullable Entity pBrokenEntity) {
		if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
			this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
			if (pBrokenEntity instanceof PlayerEntity) {
				PlayerEntity playerentity = (PlayerEntity)pBrokenEntity;
				if (playerentity.abilities.instabuild) {
					return;
				}
			}

			this.spawnAtLocation(ExtendedItems.TAPESTRY.get());
		}
	}

	@Override
	public void playPlacementSound() {
		this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
	}

	@Override
	public void moveTo(double pX, double pY, double pZ, float pYRot, float pXRot) {
		this.setPos(pX, pY, pZ);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps, boolean pTeleport) {
		BlockPos blockpos = this.pos.offset(pX - this.getX(), pY - this.getY(), pZ - this.getZ());
		this.setPos((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ExtendedItems.TAPESTRY.get());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(PacketBuffer pBuffer) {
		pBuffer.writeByte(this.direction.get2DDataValue());
		pBuffer.writeResourceLocation(type.getRegistryName());
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.direction = Direction.from2DDataValue(additionalData.readUnsignedByte());
		this.type = ExtendedTapestryType.TAPESTRIE_REGISTRY.get().getValue(additionalData.readResourceLocation());
	}
}
