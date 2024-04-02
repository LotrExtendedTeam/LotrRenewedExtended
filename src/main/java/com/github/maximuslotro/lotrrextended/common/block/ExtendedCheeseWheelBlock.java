package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.stats.ExtendedStats;

import lotr.common.item.DaggerItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCheeseWheelBlock extends ExtendedCakeBlock {
	private final RegistryObject<Item> sliceitem;
	public ExtendedCheeseWheelBlock(Properties p_i48434_1_, float compostValue, RegistryObject<Item> sliceitem) {
		super(p_i48434_1_, compostValue);
		this.sliceitem = sliceitem;
	}

	@Override
	protected ActionResultType eat(IWorld world, BlockPos pos, BlockState state, PlayerEntity entity, Hand hand) {
		ItemStack itemstack = entity.getItemInHand(hand);
		if(itemstack.getItem() instanceof DaggerItem) {
			popResource(entity.level, pos, new ItemStack(sliceitem.get(), 1));
			entity.awardStat(ExtendedStats.SLICE_CHEESE_SLICE);
			world.playSound((PlayerEntity)null, pos, SoundEvents.PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 0.8F + entity.level.random.nextFloat() * 0.4F);
		}
		else {
			if (!entity.canEat(false)) {
				return ActionResultType.PASS;
			}
			entity.getFoodData().eat(2, 0.1F);
			entity.awardStat(ExtendedStats.EAT_CHEESE_SLICE);
			world.playSound((PlayerEntity)null, pos, SoundEvents.PLAYER_BURP, SoundCategory.BLOCKS, 1.0F, 0.8F + entity.level.random.nextFloat() * 0.4F);
		}
		int i = state.getValue(BITES);
		if (i < 6) {
			world.setBlock(pos, state.setValue(BITES, Integer.valueOf(i + 1)), 3);
		} else {
			world.removeBlock(pos, false);
		}
		return ActionResultType.SUCCESS;
	}
}
