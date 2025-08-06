package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.ArrayList;
import java.util.List;

import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public enum ExtendedBannerTypes {
	DUNEDAIN(ExtendedBlocks.DUNEDAIN_BANNER, ExtendedBlocks.DUNEDAIN_WALL_BANNER, ExtendedItems.DUNEDAIN_BANNER, FactionPointers.DUNEDAIN_NORTH),
	SHIRE(ExtendedBlocks.HOBBIT_BANNER, ExtendedBlocks.HOBBIT_WALL_BANNER, ExtendedItems.HOBBIT_BANNER, FactionPointers.HOBBITS),
	BLUEMOUNTIANS(ExtendedBlocks.BLUE_MOUNTAINS_BANNER, ExtendedBlocks.BLUE_MOUNTAINS_WALL_BANNER, ExtendedItems.BLUE_MOUNTAINS_BANNER, FactionPointers.BLUE_MOUNTAINS),
	DURINSFOLK(ExtendedBlocks.DURINS_FOLK_BANNER, ExtendedBlocks.DURINS_FOLK_WALL_BANNER, ExtendedItems.DURINS_FOLK_BANNER, FactionPointers.DURINS_FOLK),
	GUNDABAD_ORC(ExtendedBlocks.GUNDABAD_ORC_BANNER,ExtendedBlocks.GUNDABAD_ORC_WALL_BANNER, ExtendedItems.GUNDABAD_ORC_BANNER, FactionPointers.NORTHERN_ORCS),
	BREE(ExtendedBlocks.BREE_BANNER,ExtendedBlocks.BREE_WALL_BANNER, ExtendedItems.BREE_BANNER, FactionPointers.BREE);

	private final RegistryObject<Block> standingBlock;
	private final RegistryObject<Block> hangingBlock;
	private final RegistryObject<Item> bannerItem;
	private final FactionPointer faction;

	private ExtendedBannerTypes(RegistryObject<Block> standingBlock, RegistryObject<Block> hangingBlock, RegistryObject<Item> item, FactionPointer faction) {
		this.standingBlock = standingBlock;
		this.hangingBlock = hangingBlock;
		this.bannerItem = item;
		this.faction = faction;
	}

	public RegistryObject<Block> getStandingBlock() {
		return standingBlock;
	}

	public RegistryObject<Block> getHangingBlock() {
		return hangingBlock;
	}

	public RegistryObject<Item> getBannerItem() {
		return bannerItem;
	}

	public FactionPointer getFaction() {
		return faction;
	}

	public static Block[] getContainedStandingBlocks(){
		List<Block> containedBanners = new ArrayList<>();
		for(ExtendedBannerTypes banner : values()) {
			containedBanners.add(banner.getStandingBlock().get());
		}
		return containedBanners.toArray(new Block[0]);
	}

	public static Block[] getContainedHangingBlocks(){
		List<Block> containedBanners = new ArrayList<>();
		for(ExtendedBannerTypes banner : values()) {
			containedBanners.add(banner.getHangingBlock().get());
		}
		return containedBanners.toArray(new Block[0]);
	}

}
