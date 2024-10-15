package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.ArrayList;
import java.util.List;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public enum BannerTypes {
	DUNEDAIN(ExtendedBlocks.DUNEDAIN_BANNER), 
	SHIRE(ExtendedBlocks.HOBBIT_BANNER),
	BLUEMOUNTIANS(ExtendedBlocks.BLUE_MOUNTAINS_BANNER), 
	DURINSFOLK(ExtendedBlocks.DURINS_FOLK_BANNER),
	GUNDABAD_ORC(ExtendedBlocks.GUNDABAD_ORC_BANNER);
	
	private final RegistryObject<Block> containedBlock;

	private BannerTypes(RegistryObject<Block> containedBlock) {
		this.containedBlock = containedBlock;
	}

	public RegistryObject<Block> getContainedBlock() {
		return containedBlock;
	}

	public static Block[] getContainedBlocks(){
		List<Block> containedBanners = new ArrayList<>();
		for(BannerTypes banner : values()) {
			containedBanners.add(banner.getContainedBlock().get());
		}
		return containedBanners.toArray(new Block[0]);
	}
}
