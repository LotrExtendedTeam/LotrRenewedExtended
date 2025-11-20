package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtendedBottleVesselDrinkBlock extends VesselDrinkBlock {

	public ExtendedBottleVesselDrinkBlock(Properties properties, VoxelShape shape) {
		super(properties, shape);
	}

	@Override
	public ItemStack getPickBlock(BlockState pState, RayTraceResult pTarget, IBlockReader pLevel, BlockPos pBlockPos, PlayerEntity pPlayer) {
		ItemStack stack = super.getPickBlock(pState, pTarget, pLevel, pBlockPos, pPlayer);
		if(stack.isEmpty()) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		return stack;
	}
}
