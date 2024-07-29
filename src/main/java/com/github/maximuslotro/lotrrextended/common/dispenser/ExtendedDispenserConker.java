package com.github.maximuslotro.lotrrextended.common.dispenser;

import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedConkerEntity;

import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExtendedDispenserConker extends ProjectileDispenseBehavior {
	@Override
	protected ProjectileEntity getProjectile(World world, IPosition pos, ItemStack stack) {
		ExtendedConkerEntity conker = new ExtendedConkerEntity(world, pos.x(), pos.y(), pos.z());
		conker.setItem(stack);
		return conker;
	}
}
