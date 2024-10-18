package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.ArrayList;
import java.util.List;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public enum BannerTypes {
	DUNEDAIN(ExtendedBlocks.DUNEDAIN_BANNER, ExtendedBlocks.DUNEDAIN_WALL_BANNER, ExtendedItems.DUNEDAIN_BANNER), 
	SHIRE(ExtendedBlocks.HOBBIT_BANNER, ExtendedBlocks.HOBBIT_WALL_BANNER, ExtendedItems.HOBBIT_BANNER),
	BLUEMOUNTIANS(ExtendedBlocks.BLUE_MOUNTAINS_BANNER, ExtendedBlocks.BLUE_MOUNTAINS_WALL_BANNER, ExtendedItems.BLUE_MOUNTAINS_BANNER), 
	DURINSFOLK(ExtendedBlocks.DURINS_FOLK_BANNER, ExtendedBlocks.DURINS_FOLK_WALL_BANNER, ExtendedItems.DURINS_FOLK_BANNER),
	GUNDABAD_ORC(ExtendedBlocks.GUNDABAD_ORC_BANNER,ExtendedBlocks.GUNDABAD_ORC_WALL_BANNER, ExtendedItems.GUNDABAD_ORC_BANNER);
	
	private final RegistryObject<Block> standingBlock;
	private final RegistryObject<Block> hangingBlock;
	private final RegistryObject<Item> item;

	private BannerTypes(RegistryObject<Block> standingBlock, RegistryObject<Block> hangingBlock, RegistryObject<Item> item) {
		this.standingBlock = standingBlock;
		this.hangingBlock = hangingBlock;
		this.item = item;
	}

	public RegistryObject<Block> getStandingBlock() {
		return standingBlock;
	}

	public RegistryObject<Block> getHangingBlock() {
		return hangingBlock;
	}

	public RegistryObject<Item> getBannerItem() {
		return item;
	}

	public static Block[] getContainedStandingBlocks(){
		List<Block> containedBanners = new ArrayList<>();
		for(BannerTypes banner : values()) {
			containedBanners.add(banner.getStandingBlock().get());
		}
		return containedBanners.toArray(new Block[0]);
	}

	public static Block[] getContainedHangingBlocks(){
		List<Block> containedBanners = new ArrayList<>();
		for(BannerTypes banner : values()) {
			containedBanners.add(banner.getHangingBlock().get());
		}
		return containedBanners.toArray(new Block[0]);
	}
}
