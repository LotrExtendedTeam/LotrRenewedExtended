package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedOrcBombBlock;
import com.github.maximuslotro.lotrrextended.common.dispenser.ExtendedDispenserOrcBomb;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedOrcBombItem extends BlockItem {
	public ExtendedOrcBombItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
		if(!(pBlock instanceof ExtendedOrcBombBlock)){
			throw new IllegalArgumentException();
		}
		DispenserBlock.registerBehavior(this, new ExtendedDispenserOrcBomb());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pItemStack, @Nullable World world, List<ITextComponent> textLines, ITooltipFlag p_77624_4_) {
		int strength = ((ExtendedOrcBombBlock)this.getBlock()).getBombStrength();
		textLines.add(1, (new TranslationTextComponent("item.lotrextended.orc_bomb.strength_"+strength)).withStyle(TextFormatting.GRAY));
		if(((ExtendedOrcBombBlock)this.getBlock()).isBombFire()) {
			textLines.add(2, (new TranslationTextComponent("item.lotrextended.orc_bomb.fire_message")).withStyle(TextFormatting.RED));
		}
	}

	public ExtendedOrcBombBlock getBombBlock() {
		return (ExtendedOrcBombBlock)getBlock();
	}
}