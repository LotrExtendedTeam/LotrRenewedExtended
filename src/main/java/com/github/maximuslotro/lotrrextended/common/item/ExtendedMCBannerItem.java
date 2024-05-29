package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.enums.BannerTypes;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedMCBannerItem extends WallOrFloorItem {
	public ExtendedMCBannerItem(Block p_i48529_1_, Block p_i48529_2_, Item.Properties p_i48529_3_) {
		super(p_i48529_1_, p_i48529_2_, p_i48529_3_);
		Validate.isInstanceOf(ExtendedMEBannerBlock.class, p_i48529_1_);
		Validate.isInstanceOf(ExtendedMEBannerWallBlock.class, p_i48529_2_);
	}

	//Add data about protection to item
//	@OnlyIn(Dist.CLIENT)
//	public static void appendHoverTextFromBannerBlockEntityTag(ItemStack p_185054_0_, List<ITextComponent> p_185054_1_) {
//		CompoundNBT compoundnbt = p_185054_0_.getTagElement("BlockEntityTag");
//		if (compoundnbt != null && compoundnbt.contains("Patterns")) {
//			ListNBT listnbt = compoundnbt.getList("Patterns", 10);
//
//	         for(int i = 0; i < listnbt.size() && i < 6; ++i) {
//	            CompoundNBT compoundnbt1 = listnbt.getCompound(i);
//	            DyeColor dyecolor = DyeColor.byId(compoundnbt1.getInt("Color"));
//	            BannerPattern bannerpattern = BannerPattern.byHash(compoundnbt1.getString("Pattern"));
//	            if (bannerpattern != null) {
//	               p_185054_1_.add((new TranslationTextComponent("block.minecraft.banner." + bannerpattern.getFilename() + '.' + dyecolor.getName())).withStyle(TextFormatting.GRAY));
//	            }
//	         }
//
//	      }
//	   }

	public BannerTypes getBanner() {
		return ((ExtendedMEBannerBlock)this.getBlock()).getBanner();
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
		//appendHoverTextFromBannerBlockEntityTag(p_77624_1_, p_77624_3_);
	}
}