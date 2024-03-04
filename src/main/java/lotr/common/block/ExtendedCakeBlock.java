package lotr.common.block;

import com.github.maximuslotro.lotrrextended.stats.ExtendedStats;

import lotr.common.event.CompostingHelper;
import lotr.common.item.DaggerItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCakeBlock extends CakeBlock {
	private final RegistryObject<Item> sliceitem;
	public ExtendedCakeBlock(Properties p_i48434_1_, float compostValue, RegistryObject<Item> sliceitem) {
		super(p_i48434_1_);
		if(compostValue>0) {
			CompostingHelper.prepareCompostable(this, compostValue);
		}
		this.sliceitem = sliceitem;
	}

	@Override
	public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (p_225533_2_.isClientSide) {
			ItemStack itemstack = p_225533_4_.getItemInHand(p_225533_5_);
			if (this.eatMouse(p_225533_2_, p_225533_3_, p_225533_1_, p_225533_4_, p_225533_5_).consumesAction()) {
				return ActionResultType.SUCCESS;
			}
			if (itemstack.isEmpty()) {
				return ActionResultType.CONSUME;
			}
		}
		return this.eatMouse(p_225533_2_, p_225533_3_, p_225533_1_, p_225533_4_, p_225533_5_);
	}

	private ActionResultType eatMouse(IWorld world, BlockPos pos, BlockState state, PlayerEntity entity, Hand hand) {
		if (!entity.canEat(false)) {
			return ActionResultType.PASS;
		} else {
			ItemStack itemstack = entity.getItemInHand(hand);
			if(itemstack.getItem() instanceof DaggerItem) {
				popResource(entity.level, pos, new ItemStack(sliceitem.get(), 1));
				entity.awardStat(ExtendedStats.SLICE_CHEESE_SLICE);
			}
			else {
				entity.getFoodData().eat(2, 0.1F);
				entity.awardStat(ExtendedStats.EAT_CHEESE_SLICE);
			}
			world.playSound((PlayerEntity)null, pos, SoundEvents.PLAYER_BURP, SoundCategory.BLOCKS, 1.0F, 0.8F + entity.level.random.nextFloat() * 0.4F);
			int i = state.getValue(BITES);
			if (i < 6) {
				world.setBlock(pos, state.setValue(BITES, Integer.valueOf(i + 1)), 3);
			} else {
				world.removeBlock(pos, false);
			}
			return ActionResultType.SUCCESS;
		}
	}
}
