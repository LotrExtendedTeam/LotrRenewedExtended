package com.github.maximuslotro.lotrrextended.common.entity.projectile;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedDuckEggEntity extends ProjectileItemEntity {
	public ExtendedDuckEggEntity(EntityType<? extends ExtendedDuckEggEntity> p_i50154_1_, World p_i50154_2_) {
		super(p_i50154_1_, p_i50154_2_);
	}

	public ExtendedDuckEggEntity(World pLevel, LivingEntity pShooter) {
		super(ExtendedEntities.DUCK_EGG_ENTITY.get(), pShooter, pLevel);
	}

	public ExtendedDuckEggEntity(World pLevel, double pX, double pY,
			double pZ) {
		super(ExtendedEntities.DUCK_EGG_ENTITY.get(), pX, pY, pZ, pLevel);
	}

	/**
	 * Handles an entity event fired from
	 * net.minecraft.world.level.Level#broadcastEntityEvent
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte pId) {
		if (pId == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(
						new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(),
						(this.random.nextFloat() - 0.5D) * 0.08D,
						(this.random.nextFloat() - 0.5D) * 0.08D,
						(this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}
	}

	/**
	 * Called when the arrow hits an entity
	 */
	@Override
	protected void onHitEntity(EntityRayTraceResult pResult) {
		super.onHitEntity(pResult);
		pResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	@Override
	protected void onHit(RayTraceResult pResult) {
		super.onHit(pResult);
		if (!this.level.isClientSide) {
			if (this.random.nextInt(8) == 0) {
				int i = 1;
				if (this.random.nextInt(32) == 0) {
					i = 4;
				}

				for (int j = 0; j < i; ++j) {
					ExtendedDuckEntity duckentity = ExtendedEntities.DUCK.get().create(this.level);
					duckentity.setAge(-24000);
					duckentity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
					duckentity.setGenderRandomly(true);
					this.level.addFreshEntity(duckentity);
				}
			}
			this.level.broadcastEntityEvent(this, (byte) 3);
			this.remove();
		}

	}

	@Override
	protected Item getDefaultItem() {
		return ExtendedItems.DUCK_EGG.get();
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}