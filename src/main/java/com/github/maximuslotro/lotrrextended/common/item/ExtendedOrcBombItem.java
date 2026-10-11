package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;
import java.util.function.Supplier;

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
	public ExtendedOrcBombItem(Supplier<Block> pBlock, Properties pProperties) {
		this(pBlock.get(), pProperties);
	}
	public ExtendedOrcBombItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
		if(!(pBlock instanceof ExtendedOrcBombBlock)){
			throw new IllegalArgumentException();
		}
		DispenserBlock.registerBehavior(this, new ExtendedDispenserOrcBomb());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		int strength = ((ExtendedOrcBombBlock)this.getBlock()).getBombStrength();
		pTooltip.add(1, (new TranslationTextComponent("gui.lotrextended.orc_bomb.strength_"+strength)).withStyle(TextFormatting.GRAY));
		if(((ExtendedOrcBombBlock)this.getBlock()).isBombFire()) {
			pTooltip.add(2, (new TranslationTextComponent("gui.lotrextended.orc_bomb.fire_message")).withStyle(TextFormatting.RED));
		}
	}

	public ExtendedOrcBombBlock getBombBlock() {
		return (ExtendedOrcBombBlock)getBlock();
	}
}