package lotr.common.entity.projectile;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ExtendedConkerEntity extends ProjectileItemEntity{
	

	public ExtendedConkerEntity(double p_i48541_2_, double p_i48541_4_, double p_i48541_6_, World p_i48541_8_, ItemStack stack) {
		super(ExtendedEntities.THROWN_CONKER.get(), p_i48541_2_, p_i48541_4_, p_i48541_6_, p_i48541_8_);
		setItem(stack);
	}

	public ExtendedConkerEntity(LivingEntity p_i48542_2_, World p_i48542_3_, ItemStack stack) {
		super(ExtendedEntities.THROWN_CONKER.get(), p_i48542_2_, p_i48542_3_);
		setItem(stack);
	}

	public ExtendedConkerEntity(EntityType<? extends ProjectileItemEntity> entityType, World p_i48540_2_) {
		super(entityType, p_i48540_2_);
	}

	public ExtendedConkerEntity(World p_i48540_2_) {
		this(ExtendedEntities.THROWN_CONKER.get(), p_i48540_2_);
	}

	@Override
	public void defineSynchedData() {
	}

	@Override
	protected float getGravity() {
		return 0.04F;
	}

	@Override
	protected void onHit(RayTraceResult target) {
		if (target.getType() == Type.ENTITY) {
			((EntityRayTraceResult) target).getEntity().hurt(DamageSource.thrown(this, getOwner()), 1.0F);

		}
		if(!level.isClientSide) {
			spawnAtLocation(getDefaultItem());
			remove();
		}
	}

	@Override
	protected Item getDefaultItem() {
		return ExtendedItems.CONKER.get();
	}
}
