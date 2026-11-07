package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedBirdcageItem extends BlockItem{

	public ExtendedBirdcageItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);
		CompoundNBT blockEntityTag = stack.getTagElement("BlockEntityTag");

		if (blockEntityTag == null) {
			return;
		}
		String birdType = blockEntityTag.getString("BirdType");
		if (!birdType.isEmpty()) {
			ITextComponent birdName = new TranslationTextComponent("entity.lotr." + birdType);
			tooltip.add(new TranslationTextComponent("tooltip.lotrextended.birdcage.held_bird", birdName).withStyle(TextFormatting.GRAY));
		}
	}
}
