package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedModifierRepairTomeItem extends Item {
	private String functionID;
	private String typeID;

	public ExtendedModifierRepairTomeItem(Properties pProperties, String pFunctionID, String pTypeID) {
		super(pProperties);
		functionID = pFunctionID;
		typeID = pTypeID;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		pTooltip.add(1, (new TranslationTextComponent("gui.lotrextended.repair_books.description",
			new TranslationTextComponent("gui.lotrextended.repair_books.description."+functionID).withStyle(TextFormatting.GRAY),
			new TranslationTextComponent("gui.lotrextended.repair_books."+typeID).withStyle(TextFormatting.AQUA))).withStyle(TextFormatting.GRAY));
	}

}
