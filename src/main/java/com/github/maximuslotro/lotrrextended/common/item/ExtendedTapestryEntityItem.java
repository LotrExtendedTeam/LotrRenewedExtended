package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedTapestryEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtendedTapestryEntityItem extends Item {

	public ExtendedTapestryEntityItem(Item.Properties pProperties) {
		super(pProperties);
	}

	@Override
	public ActionResultType useOn(ItemUseContext pContext) {
		BlockPos blockpos = pContext.getClickedPos();
		Direction direction = pContext.getClickedFace();
		BlockPos blockpos1 = blockpos.relative(direction);
		PlayerEntity playerentity = pContext.getPlayer();
		ItemStack itemstack = pContext.getItemInHand();
		if (playerentity != null && !this.mayPlace(playerentity, direction, itemstack, blockpos1)) {
			return ActionResultType.FAIL;
		} else {
			World world = pContext.getLevel();
			HangingEntity hangingentity = new ExtendedTapestryEntity(world, blockpos1, direction);

			CompoundNBT compoundnbt = itemstack.getTag();
			if (compoundnbt != null) {
				EntityType.updateCustomEntityTag(world, playerentity, hangingentity, compoundnbt);
			}

			if (hangingentity.survives()) {
				if (!world.isClientSide) {
					hangingentity.playPlacementSound();
					world.addFreshEntity(hangingentity);
				}

				itemstack.shrink(1);
				return ActionResultType.sidedSuccess(world.isClientSide);
			} else {
				return ActionResultType.CONSUME;
			}
		}
	}

	protected boolean mayPlace(PlayerEntity pPlayer, Direction pDirection, ItemStack pHangingEntityStack, BlockPos pPos) {
		return !pDirection.getAxis().isVertical() && pPlayer.mayUseItemAt(pPos, pDirection, pHangingEntityStack);
	}
}