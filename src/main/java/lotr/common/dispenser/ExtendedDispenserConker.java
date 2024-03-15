package lotr.common.dispenser;

import lotr.common.entity.projectile.ExtendedConkerEntity;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExtendedDispenserConker extends ProjectileDispenseBehavior {
	@Override
	protected ProjectileEntity getProjectile(World world, IPosition pos, ItemStack stack) {
		return new ExtendedConkerEntity(pos.x(), pos.y(), pos.z(), world, stack);
	}
}
