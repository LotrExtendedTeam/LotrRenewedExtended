package com.github.maximuslotro.lotrrextended.common.tileentity;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class ExtendedMEHangingBannerTileEntity extends TileEntity {
	private CompoundNBT itemData;

	public ExtendedMEHangingBannerTileEntity() {
		super(ExtendedTileEntities.BANNER_HANGING.get());
	}

	@Override
	public void load(BlockState blockState, CompoundNBT pCompound) {
		super.load(blockState, pCompound);
		loadProtectionData(pCompound);
	}

	public void loadProtectionData(CompoundNBT tag) {
		if(tag.contains("ProtectionData", 10)) {
			itemData = tag.getCompound("ProtectionData");
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		if(itemData!=null) {
			pCompound.put("ProtectionData", itemData);
		}
		return pCompound;
	}

	/**
	 * Gets the copied item, handling if the banner has protection data
	 * @param bannerType The banner type instance to get item from
	 * @return ItemStack
	 * */
	public ItemStack getItem(ExtendedBannerTypes bannerType) {
		ItemStack itemstack = new ItemStack(bannerType.getBannerItem().get());
		if(itemData!=null) {
			itemstack.getOrCreateTag().put("ProtectionData", itemData);
		}
		return itemstack;
	}
}
