package lotr.common.entity.projectile;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedConkerEntity extends ProjectileItemEntity{
	

	public ExtendedConkerEntity(World p_i48541_8_, double p_i48541_2_, double p_i48541_4_, double p_i48541_6_) {
		super(ExtendedEntities.THROWN_CONKER.get(), p_i48541_2_, p_i48541_4_, p_i48541_6_, p_i48541_8_);
	}

	public ExtendedConkerEntity(LivingEntity p_i48542_2_, World p_i48542_3_) {
		super(ExtendedEntities.THROWN_CONKER.get(), p_i48542_2_, p_i48542_3_);
	}

	public ExtendedConkerEntity(EntityType<? extends ProjectileItemEntity> entityType, World p_i48540_2_) {
		super(entityType, p_i48540_2_);
	}

	@Override
	public void defineSynchedData() {
		super.defineSynchedData();
	}

	@Override
	protected float getGravity() {
		return 0.04F;
	}
	  
	@Override
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		p_213868_1_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 1.0F);
	}

	@Override
	protected void onHit(RayTraceResult target) {
		super.onHit(target);
		if(!level.isClientSide) {
			spawnAtLocation(getDefaultItem());
			remove();
		}
	}

	@Override
	protected Item getDefaultItem() {
		return ExtendedItems.CONKER.get();
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
