package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedMCBannerItem extends WallOrFloorItem {
	public ExtendedMCBannerItem(Block bannerBlock, Block wallBanner, Item.Properties pProperties) {
		super(bannerBlock, wallBanner, pProperties);
		Validate.isInstanceOf(ExtendedMEBannerBlock.class, bannerBlock);
		Validate.isInstanceOf(ExtendedMEBannerWallBlock.class, wallBanner);
	}

	@Override
	@Nullable
	protected BlockState getPlacementState(BlockItemUseContext pContext) {
		World level = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		BlockState wallState = this.wallBlock.getStateForPlacement(pContext);

		for(Direction direction : pContext.getNearestLookingDirections()) {
			if(direction != Direction.UP) {
				if(direction == Direction.DOWN && !ExtendedMEBannerBlock.canPlaceBanner(pContext, this.getBanner())) {
					if (!level.isClientSide()) {
						BlockState currentBottom = level.getBlockState(blockpos);
						BlockState currentTop = level.getBlockState(blockpos.above());
						level.sendBlockUpdated(blockpos, currentBottom, currentBottom, 10);
						level.sendBlockUpdated(blockpos.above(), currentTop, currentTop, 10);
						if (pContext.getPlayer() instanceof ServerPlayerEntity) {
							((ServerPlayerEntity)pContext.getPlayer()).refreshContainer(pContext.getPlayer().inventoryMenu);
						}
						return null;
					}
				}
				BlockState stateToTry = (direction == Direction.DOWN) ? this.getBlock().getStateForPlacement(pContext) : wallState;
				if(stateToTry != null && stateToTry.canSurvive(level, blockpos)) {
					if(level.isUnobstructed(stateToTry, blockpos, ISelectionContext.empty())) {
						return stateToTry;
					}
					break;
				}
			}
		}

		return null;
	}

	public ExtendedBannerTypes getBanner() {
		return ((ExtendedMEBannerBlock)this.getBlock()).getBanner();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		CompoundNBT compoundnbt = pStack.getTagElement("ProtectionData");
		if(compoundnbt != null ) {
			pTooltip.add((new TranslationTextComponent("gui.lotrextended.banner_item.info")).withStyle(TextFormatting.GRAY));
		}
	}
}