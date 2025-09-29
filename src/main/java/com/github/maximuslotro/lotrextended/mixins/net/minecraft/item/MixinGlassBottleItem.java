package com.github.maximuslotro.lotrextended.mixins.net.minecraft.item;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import lotr.common.block.VesselDrinkBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.item.VesselType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

/**
 * A mixin for {@link GlassBottleItem} that extends its behavior to allow placement
 * of a glass bottle block in the world. 
 * <p>
 * This modification enables bottles to function like placeable blocks, interacting
 * with the environment, triggering block placement logic, and integrating with custom
 * systems such as {@code VesselDrinkBlock}.
 */
@Mixin(GlassBottleItem.class)
public abstract class MixinGlassBottleItem extends Item {
	/**
     * Constructor required for mixins that extend a superclass with non-default constructors.
     * This constructor is only used at compile-time and stripped at runtime.
     *
     * @param pProperties the properties for the item
     */
	public MixinGlassBottleItem(Properties pProperties) {
		super(pProperties);
	}
	/**
     * Overrides {@link Item#useOn(ItemUseContext)} to provide custom placement logic
     * for glass bottles. 
     * <p>
     * Attempts to place a glass bottle block using {@link #place$LotrExtended(BlockItemUseContext)}.
     * If placement fails, falls back to the vanilla behavior.
     *
     * @param itemUseContext the context of the item use, including world, position, and player
     * @return the result of the action (success, fail, or pass-through)
     */
	@Override
	public ActionResultType useOn(ItemUseContext itemUseContext) {
		ActionResultType actionresulttype = place$LotrExtended(new BlockItemUseContext(itemUseContext));
		return !actionresulttype.consumesAction() ? super.useOn(itemUseContext) : actionresulttype;
	}
	/**
     * Handles the placement process for a glass bottle block in the world.
     * <p>
     * Validates placement conditions, determines the appropriate {@link BlockState},
     * places the block, triggers advancements, plays sounds, and handles item
     * consumption.
     *
     * @param blockItemUseContext the context of the block placement
     * @return the result of the placement attempt
     */
	@Unique
	private static ActionResultType place$LotrExtended(BlockItemUseContext blockItemUseContext) {
		if (!blockItemUseContext.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockState blockstate = getPlacementState$LotrExtended(blockItemUseContext);
			if (blockstate == null) {
				return ActionResultType.FAIL;
			} else if (!placeBlock$LotrExtended(blockItemUseContext, blockstate)) {
				return ActionResultType.FAIL;
			} else {
				BlockPos blockpos = blockItemUseContext.getClickedPos();
				World world = blockItemUseContext.getLevel();
				PlayerEntity playerentity = blockItemUseContext.getPlayer();
				ItemStack itemstack = blockItemUseContext.getItemInHand();
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

				SoundType soundtype = blockstate1.getSoundType(world, blockpos, blockItemUseContext.getPlayer());
				world.playSound(playerentity, blockpos, SoundType.GLASS.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if (playerentity == null || !playerentity.abilities.instabuild) {
					itemstack.shrink(1);
				}

				return ActionResultType.sidedSuccess(world.isClientSide);
			}
		}
	}
	/**
     * Determines the block state for a glass bottle placement attempt.
     *
     * @param blockItemUseContext the context of the block placement
     * @return a valid block state if placement is possible, otherwise {@code null}
     */
	@Nullable
	@Unique
	private static BlockState getPlacementState$LotrExtended(BlockItemUseContext blockItemUseContext) {
		BlockState blockstate = ExtendedBlocks.GLASS_BOTTLE.get().getStateForPlacement(blockItemUseContext);
		return blockstate != null && canPlace$LotrExtended(blockItemUseContext, blockstate) ? blockstate : null;
	}
	/**
     * Validates whether the given block state can be placed at the target position.
     *
     * @param blockItemUseContext the placement context
     * @param placeState          the block state to validate
     * @return {@code true} if placement is valid, otherwise {@code false}
     */
	@Unique
	private static boolean canPlace$LotrExtended(BlockItemUseContext blockItemUseContext, BlockState placeState) {
		PlayerEntity playerentity = blockItemUseContext.getPlayer();
		ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.empty() : ISelectionContext.of(playerentity);
		return placeState.canSurvive(blockItemUseContext.getLevel(), blockItemUseContext.getClickedPos()) && blockItemUseContext.getLevel().isUnobstructed(placeState, blockItemUseContext.getClickedPos(), iselectioncontext);
	}
	/**
     * Attempts to place the glass bottle block in the world.
     * <p>
     * Ensures placement rules are respected (e.g., preventing placement in water unless
     * conditions allow). If successful, sets up vessel data via {@link VesselDrinkBlock}.
     *
     * @param context the placement context
     * @param state   the block state to place
     * @return {@code true} if the block was successfully placed, otherwise {@code false}
     */
	@Unique
	private static boolean placeBlock$LotrExtended(BlockItemUseContext context, BlockState state) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		if (world.getFluidState(pos).is(FluidTags.WATER) && !context.getPlayer().isSecondaryUseActive()) {
			return false;
		}
		if (context.getLevel().setBlock(context.getClickedPos(), state, 11)) {
			VesselDrinkBlock.setVesselDrinkItem(world, pos, ItemStack.EMPTY, VesselType.valueOf("GLASS_BOTTLE"));
			return true;
		}
		return false;
	}
}
