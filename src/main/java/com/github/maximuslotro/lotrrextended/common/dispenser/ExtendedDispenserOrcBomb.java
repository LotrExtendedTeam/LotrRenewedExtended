package com.github.maximuslotro.lotrrextended.common.dispenser;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedOrcBombBlock;
import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedOrcBombEntity;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedOrcBombItem;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtendedDispenserOrcBomb extends OptionalDispenseBehavior {
	@Override
	protected ItemStack execute(IBlockSource pSource, ItemStack pStack) {
		World world = pSource.getLevel();
		BlockPos blockpos = pSource.getPos().relative(pSource.getBlockState().getValue(DispenserBlock.FACING));
		int strength = 0;
		boolean fire = false;
		if (pStack.getItem() instanceof ExtendedOrcBombItem) {
			ExtendedOrcBombBlock contained =((ExtendedOrcBombItem)pStack.getItem()).getBombBlock();
			strength = contained.getBombStrength();
			fire = contained.isBombFire();
		}
		ExtendedOrcBombEntity tntentity = new ExtendedOrcBombEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, (LivingEntity)null);
		tntentity.setFuse((short)(80 + 20 * (strength + 1)));
		tntentity.setStrength(strength);
		tntentity.setFire(fire);
		world.addFreshEntity(tntentity);
		world.playSound((PlayerEntity)null, tntentity.getX(), tntentity.getY(), tntentity.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
		pStack.shrink(1);
		return pStack;
	}
}
