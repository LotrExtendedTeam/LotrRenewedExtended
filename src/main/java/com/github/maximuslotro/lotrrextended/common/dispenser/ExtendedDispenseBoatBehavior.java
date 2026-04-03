package com.github.maximuslotro.lotrrextended.common.dispenser;

import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedBoatEntity;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtendedDispenseBoatBehavior extends DefaultDispenseItemBehavior {
	private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
	//private final BoatEntity.Type type;
	private final ExtendedBoatEntity.Type type;

	public ExtendedDispenseBoatBehavior(ExtendedBoatEntity.Type pType) {
		this.type = pType;
	}

	@Override
	public ItemStack execute(IBlockSource pSource, ItemStack pStack) {
		Direction direction = pSource.getBlockState().getValue(DispenserBlock.FACING);
		World world = pSource.getLevel();
		double x = pSource.x() + (double)((float)direction.getStepX() * 1.125F);
		double y = pSource.y() + (double)((float)direction.getStepY() * 1.125F);
		double z = pSource.z() + (double)((float)direction.getStepZ() * 1.125F);
		BlockPos blockpos = pSource.getPos().relative(direction);
		double yOffset;
		if (world.getFluidState(blockpos).is(FluidTags.WATER)) {
			yOffset = 1.0D;
		} else {
			if (!world.getBlockState(blockpos).isAir() || !world.getFluidState(blockpos.below()).is(FluidTags.WATER)) {
				return this.defaultDispenseItemBehavior.dispense(pSource, pStack);
			}
			yOffset = 0.0D;
		}

		//BoatEntity boat = new BoatEntity(world, d0, d1 + d3, d2);
		//boatentity.setType(this.type);
		ExtendedBoatEntity boat = new ExtendedBoatEntity(world, x, y + yOffset, z);
		boat.setTypeExtended(this.type);
		boat.yRot = direction.toYRot();
		world.addFreshEntity(boat);
		pStack.shrink(1);
		return pStack;
	}

	/**
	 * Play the dispense sound from the specified block.
	 */
	@Override
	protected void playSound(IBlockSource pSource) {
		pSource.getLevel().levelEvent(1000, pSource.getPos(), 0);
	}
}
