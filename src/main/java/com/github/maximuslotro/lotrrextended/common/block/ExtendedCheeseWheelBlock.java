package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCheeseState;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;

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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCheeseWheelBlock extends ExtendedCakeBlock implements ExtendedAgeingCheese {
	private final RegistryObject<Item> sliceitem;
	private final ExtendedCheeseState age;
	public ExtendedCheeseWheelBlock(Properties p_i48434_1_, float compostValue, RegistryObject<Item> sliceitem, ExtendedCheeseState age) {
		super(p_i48434_1_, compostValue);
		this.sliceitem = sliceitem;
		this.age = age;
	}

	@Override
	protected ActionResultType eat(IWorld world, BlockPos pos, BlockState state, PlayerEntity entity, Hand hand) {
		ItemStack itemstack = entity.getItemInHand(hand);
		if(itemstack.getItem() instanceof DaggerItem) {
			popResource(entity.level, pos, new ItemStack(sliceitem.get(), 1));
			entity.awardStat(ExtendedStats.SLICE_CHEESE_SLICE);
			world.playSound((PlayerEntity)null, pos, ExtendedSounds.CHEESE_SLICE.get(), SoundCategory.PLAYERS, 1.0F, 0.8F + entity.level.random.nextFloat() * 0.4F);
		}
		else {
			if (!entity.canEat(false)) {
				return ActionResultType.PASS;
			}
			if(getAge()==ExtendedCheeseState.AGED) {
				entity.getFoodData().eat(3, 0.1F);
			}
			else {
				entity.getFoodData().eat(2, 0.1F);
			}
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

	@Override
	public void randomTick(BlockState p_154929_, ServerWorld p_154930_, BlockPos p_154931_, Random p_154932_) {
		this.onRandomTick(p_154929_, p_154930_, p_154931_, p_154932_);
	}

	@Override
	public boolean isRandomlyTicking(BlockState p_154935_) {
		return ExtendedAgeingCheese.getNext(p_154935_.getBlock()).isPresent();
	}
	  
	@Override
	public ExtendedCheeseState getAge() {
		return age;
	}
}
