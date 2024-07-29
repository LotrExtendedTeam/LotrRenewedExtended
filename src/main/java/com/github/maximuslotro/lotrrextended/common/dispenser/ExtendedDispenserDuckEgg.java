package com.github.maximuslotro.lotrrextended.common.dispenser;

import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedDuckEggEntity;

import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExtendedDispenserDuckEgg extends ProjectileDispenseBehavior {
	@Override
	protected ExtendedDuckEggEntity getProjectile(World world, IPosition pos, ItemStack stack) {
		ExtendedDuckEggEntity conker = new ExtendedDuckEggEntity(world, pos.x(), pos.y(), pos.z());
		conker.setItem(stack);
		return conker;
	}
}
