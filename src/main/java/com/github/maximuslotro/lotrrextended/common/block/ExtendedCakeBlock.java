package com.github.maximuslotro.lotrrextended.common.block;

import com.mojang.datafixers.util.Pair;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ExtendedCakeBlock extends CakeBlock {
	protected final Food foodProperties;
	protected final ResourceLocation eatStat;

	public ExtendedCakeBlock(Properties p_i48434_1_, float compostValue, Food food, ResourceLocation pEatStat) {
		super(p_i48434_1_);
		this.foodProperties = food;
		this.eatStat = pEatStat;
		if(compostValue>0) {
			CompostingHelper.prepareCompostable(this, compostValue);
		}
	}

	@Override
	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult p_225533_6_) {
		if (pLevel.isClientSide) {
			ItemStack itemstack = pPlayer.getItemInHand(pHand);
			if (this.eat(pLevel, pPos, pState, pPlayer, pHand).consumesAction()) {
				return ActionResultType.SUCCESS;
			}
			if (itemstack.isEmpty()) {
				return ActionResultType.CONSUME;
			}
		}
		return this.eat(pLevel, pPos, pState, pPlayer, pHand);
	}

	protected ActionResultType eat(World pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer, Hand pHand) {
		if (!pPlayer.canEat(false)) {
			return ActionResultType.PASS;
		} else {
			pPlayer.awardStat(eatStat);
			pPlayer.getFoodData().eat(foodProperties.getNutrition(), foodProperties.getSaturationModifier());
			for(Pair<EffectInstance, Float> pair : foodProperties.getEffects()) {
				if (!pLevel.isClientSide && pair.getFirst() != null && pLevel.random.nextFloat() < pair.getSecond()) {
					pPlayer.addEffect(new EffectInstance(pair.getFirst()));
				}
			}
			pLevel.playSound(null, pPos, SoundEvents.PLAYER_BURP, SoundCategory.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
			int i = pState.getValue(BITES);
			if (i < 6) {
				pLevel.setBlock(pPos, pState.setValue(BITES, i + 1), 3);
			} else {
				pLevel.removeBlock(pPos, false);
			}
			return ActionResultType.SUCCESS;
		}
	}
}
