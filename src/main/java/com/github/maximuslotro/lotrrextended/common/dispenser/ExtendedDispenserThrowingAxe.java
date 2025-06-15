package com.github.maximuslotro.lotrrextended.common.dispenser;

import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedThrowableAxeEntity;

import lotr.common.entity.projectile.LOTRAbstractArrowEntity;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExtendedDispenserThrowingAxe extends ProjectileDispenseBehavior {

    @Override
    protected ProjectileEntity getProjectile(World world, IPosition pos, ItemStack stack) {
        ExtendedThrowableAxeEntity axe = new ExtendedThrowableAxeEntity(world, pos.x(), pos.y(), pos.z(), stack);
        axe.pickup = LOTRAbstractArrowEntity.PickupStatus.ALLOWED;
        return (ProjectileEntity)axe;
    }
    
}