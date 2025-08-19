package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedMCBannerItem extends WallOrFloorItem {
	public ExtendedMCBannerItem(Block p_i48529_1_, Block p_i48529_2_, Item.Properties p_i48529_3_) {
		super(p_i48529_1_, p_i48529_2_, p_i48529_3_);
		Validate.isInstanceOf(ExtendedMEBannerBlock.class, p_i48529_1_);
		Validate.isInstanceOf(ExtendedMEBannerWallBlock.class, p_i48529_2_);
	}

	public static void appendHoverTextFromBannerBlockEntityTag(ItemStack pItemStack, List<ITextComponent> textLines) {
		CompoundNBT compoundnbt = pItemStack.getTagElement("ProtectionData");
		if (compoundnbt != null ) {
			textLines.add((new TranslationTextComponent("gui.lotrextended.banner_item.info")).withStyle(TextFormatting.GRAY));
		}
	}



	public ExtendedBannerTypes getBanner() {
		return ((ExtendedMEBannerBlock)this.getBlock()).getBanner();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pItemStack, @Nullable World world, List<ITextComponent> textLines, ITooltipFlag p_77624_4_) {
		appendHoverTextFromBannerBlockEntityTag(pItemStack, textLines);
	}
}