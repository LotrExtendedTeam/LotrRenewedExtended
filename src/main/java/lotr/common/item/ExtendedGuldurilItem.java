package lotr.common.item;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ExtendedGuldurilItem extends Item {
	public ExtendedGuldurilItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		World world = entity.level;
		if (!world.isClientSide && entity.isOnFire()) {
			BlockPos pos = entity.blockPosition();
			BlockPos belowPos = pos.below();
			BlockState state = world.getBlockState(pos);
			BlockState belowState = world.getBlockState(belowPos);
			if (state.getBlock() == Blocks.FIRE && belowState.getBlock() == LOTRBlocks.HEARTH_BLOCK.get()) {
				world.setBlockAndUpdate(belowPos, ExtendedBlocks.GREEN_FLAME_HEARTH_BLOCK.get().defaultBlockState());
				world.setBlockAndUpdate(pos, ExtendedBlocks.GREEN_FIRE_BLOCK.get().defaultBlockState());
				world.playSound((PlayerEntity) null, pos, SoundEvents.BLAZE_SHOOT, SoundCategory.BLOCKS, 0.5F, MathHelper.nextFloat(world.random, 0.75F, 0.95F));
				entity.remove();
			}
		}

		return false;
	}
}