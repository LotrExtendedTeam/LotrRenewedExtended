package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public enum ExtendedBannerTypes {
    DUNEDAIN(ExtendedBlocks.DUNEDAIN_BANNER, ExtendedBlocks.DUNEDAIN_WALL_BANNER, ExtendedItems.DUNEDAIN_BANNER, FactionPointers.DUNEDAIN_NORTH),
    SHIRE(ExtendedBlocks.HOBBIT_BANNER, ExtendedBlocks.HOBBIT_WALL_BANNER, ExtendedItems.HOBBIT_BANNER, FactionPointers.HOBBITS),
    BLUEMOUNTIANS(ExtendedBlocks.BLUE_MOUNTAINS_BANNER, ExtendedBlocks.BLUE_MOUNTAINS_WALL_BANNER, ExtendedItems.BLUE_MOUNTAINS_BANNER, FactionPointers.BLUE_MOUNTAINS),
    DURINSFOLK(ExtendedBlocks.DURINS_FOLK_BANNER, ExtendedBlocks.DURINS_FOLK_WALL_BANNER, ExtendedItems.DURINS_FOLK_BANNER, FactionPointers.DURINS_FOLK),
    GUNDABAD_ORC(ExtendedBlocks.GUNDABAD_ORC_BANNER, ExtendedBlocks.GUNDABAD_ORC_WALL_BANNER, ExtendedItems.GUNDABAD_ORC_BANNER, FactionPointers.NORTHERN_ORCS),
    BREE(ExtendedBlocks.BREE_BANNER, ExtendedBlocks.BREE_WALL_BANNER, ExtendedItems.BREE_BANNER, FactionPointers.BREE),

    ANGMAR(ExtendedBlocks.ANGMAR_BANNER, ExtendedBlocks.ANGMAR_WALL_BANNER, ExtendedItems.ANGMAR_BANNER, FactionPointers.ANGMAR),
    DALE(ExtendedBlocks.DALE_BANNER, ExtendedBlocks.DALE_WALL_BANNER, ExtendedItems.DALE_BANNER, FactionPointers.DALE),
    DOL_AMROTH(ExtendedBlocks.DOL_AMROTH_BANNER, ExtendedBlocks.DOL_AMROTH_WALL_BANNER, ExtendedItems.DOL_AMROTH_BANNER, FactionPointers.GONDOR),
    DOL_GULDUR(ExtendedBlocks.DOL_GULDUR_BANNER, ExtendedBlocks.DOL_GULDUR_WALL_BANNER, ExtendedItems.DOL_GULDUR_BANNER, FactionPointers.DOL_GULDUR),
    DORWINION(ExtendedBlocks.DORWINION_BANNER, ExtendedBlocks.DORWINION_WALL_BANNER, ExtendedItems.DORWINION_BANNER, FactionPointers.DORWINION),
    DUNLAND(ExtendedBlocks.DUNLAND_BANNER, ExtendedBlocks.DUNLAND_WALL_BANNER, ExtendedItems.DUNLAND_BANNER, FactionPointers.DUNLAND),
    GONDOR(ExtendedBlocks.GONDOR_BANNER, ExtendedBlocks.GONDOR_WALL_BANNER, ExtendedItems.GONDOR_BANNER, FactionPointers.GONDOR),
    ISENGARD(ExtendedBlocks.ISENGARD_BANNER, ExtendedBlocks.ISENGARD_WALL_BANNER, ExtendedItems.ISENGARD_BANNER, FactionPointers.ISENGARD),
    LINDON(ExtendedBlocks.LINDON_BANNER, ExtendedBlocks.LINDON_WALL_BANNER, ExtendedItems.LINDON_BANNER, FactionPointers.HIGH_ELVES),
    LOTHLORIEN(ExtendedBlocks.LOTHLORIEN_BANNER, ExtendedBlocks.LOTHLORIEN_WALL_BANNER, ExtendedItems.LOTHLORIEN_BANNER, FactionPointers.LOTHLORIEN),
    MORDOR(ExtendedBlocks.MORDOR_BANNER, ExtendedBlocks.MORDOR_WALL_BANNER, ExtendedItems.MORDOR_BANNER, FactionPointers.MORDOR),
    RIVENDELL(ExtendedBlocks.RIVENDELL_BANNER, ExtendedBlocks.RIVENDELL_WALL_BANNER, ExtendedItems.RIVENDELL_BANNER, FactionPointers.HIGH_ELVES),
    ROHAN(ExtendedBlocks.ROHAN_BANNER, ExtendedBlocks.ROHAN_WALL_BANNER, ExtendedItems.ROHAN_BANNER, FactionPointers.ROHAN),
    WOODLAND_REALM(ExtendedBlocks.WOODLAND_REALM_BANNER, ExtendedBlocks.WOODLAND_REALM_WALL_BANNER, ExtendedItems.WOODLAND_REALM_BANNER, FactionPointers.WOODLAND_REALM);


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
