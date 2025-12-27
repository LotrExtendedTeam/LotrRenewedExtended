package com.github.maximuslotro.lotrextended.mixins.lotr.common.item;

import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;

import lotr.common.block.VesselDrinkBlock;
import lotr.common.data.MiscDataModule;
import lotr.common.init.ExtendedBlocks;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

@Mixin(VesselDrinkItem.class)
public class MixinVesselDrinkItem {
	
	@Inject(method = "useOn", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	public void useOn$Extended(ItemUseContext context, CallbackInfoReturnable<ActionResultType> ci, ItemStack drinkItem, VesselType vessel) {
		if(vessel.getCodeName().equals("glass_bottle")) {
			ActionResultType returnType = place$LotrExtended(new BlockItemUseContext(context));
			if(returnType.consumesAction()) {
				ci.setReturnValue(returnType);
			}
		}
	}

	@Unique
	private static ActionResultType place$LotrExtended(BlockItemUseContext blockitemusecontext) {
		if (!blockitemusecontext.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockState blockstate = getPlacementState$LotrExtended(blockitemusecontext);
			if (blockstate == null) {
				return ActionResultType.FAIL;
			} else if (!placeBlock$LotrExtended(blockitemusecontext, blockstate)) {
				return ActionResultType.FAIL;
			} else {
				BlockPos blockpos = blockitemusecontext.getClickedPos();
				World world = blockitemusecontext.getLevel();
				PlayerEntity playerentity = blockitemusecontext.getPlayer();
				ItemStack itemstack = blockitemusecontext.getItemInHand();
				BlockState blockstate1 = world.getBlockState(blockpos);
				Block block = blockstate1.getBlock();
				if (block == blockstate.getBlock()) {
					//blockstate1 = this.updateBlockStateFromTag(blockpos, world, itemstack, blockstate1);
					//this.updateCustomBlockEntityTag(blockpos, world, playerentity, itemstack, blockstate1);
					block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
					if (playerentity instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
					}
				}

				SoundType soundtype = blockstate1.getSoundType(world, blockpos, blockitemusecontext.getPlayer());
				world.playSound(playerentity, blockpos, SoundType.GLASS.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if (playerentity == null || !playerentity.abilities.instabuild) {
					itemstack.shrink(1);
				}

				return ActionResultType.sidedSuccess(world.isClientSide);
			}
		}
	}

	@Nullable
	@Unique
	private static BlockState getPlacementState$LotrExtended(BlockItemUseContext blockitemusecontext) {
		BlockState blockstate = ExtendedBlocks.GLASS_BOTTLE.get().getStateForPlacement(blockitemusecontext);
		return blockstate != null && canPlace$LotrExtended(blockitemusecontext, blockstate) ? blockstate : null;
	}

	@Unique
	private static boolean canPlace$LotrExtended(BlockItemUseContext blockitemusecontext, BlockState placeState) {
		PlayerEntity playerentity = blockitemusecontext.getPlayer();
		ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.empty() : ISelectionContext.of(playerentity);
		return placeState.canSurvive(blockitemusecontext.getLevel(), blockitemusecontext.getClickedPos()) && blockitemusecontext.getLevel().isUnobstructed(placeState, blockitemusecontext.getClickedPos(), iselectioncontext);
	}

	@Unique
	private static boolean placeBlock$LotrExtended(BlockItemUseContext context, BlockState state) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		if (world.getFluidState(pos).is(FluidTags.WATER) && !context.getPlayer().isSecondaryUseActive()) {
			return false;
		}
		if (context.getLevel().setBlock(context.getClickedPos(), state, 11)) {
			VesselDrinkBlock.setVesselDrinkItem(world, pos, context.getItemInHand(), VesselType.valueOf("GLASS_BOTTLE"));
			return true;
		}
		return false;
	}

	@Inject(method = "finishUsingItem", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;round(F)I", ordinal = 1, shift = Shift.AFTER), 
			locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	public void finishUsingItem$Extended(ItemStack stack, World world, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir, 
			PlayerEntity asPlayer, VesselType vessel, ItemStack emptyVessel, float benefitEffectiveness, float healF, float sat, int heal, 
			float alcStrength, Optional<MiscDataModule> playerMiscData, int tolerance, int duration, int durationTicks, int toleranceAdd) {
		if (asPlayer instanceof ServerPlayerEntity) {
			ExtendedCriteriaTriggers.PLAYER_DRINK_TRIGGER.trigger((ServerPlayerEntity) asPlayer, stack, duration, tolerance+toleranceAdd);
		}
	}
}
