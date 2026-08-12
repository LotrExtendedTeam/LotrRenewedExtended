package com.github.maximuslotro.lotrrextended.common.init;

import java.util.function.Supplier;


import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedChimneyTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedCrucibleTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedGrassVoidTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedHoistTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedLargePotTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneGrinderTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEHangingBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedNoRenderTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedDwarvenBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedGrayFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedHighElvenBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedOrcBarrelTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedReedBasketTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;
import com.mojang.datafixers.types.Type;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedTileEntities {
	public static DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LotrExtendedMod.MODID);

	public static RegistryObject<TileEntityType<ExtendedStoneChestTileEntity>> STONE_CHEST = register("stone_chest", () -> Builder.of(ExtendedStoneChestTileEntity::new, ExtendedBlocks.STONE_CHEST.get()));
	public static RegistryObject<TileEntityType<ExtendedStrawBedTileEntity>> STRAW_BED = register("straw_bed", () -> Builder.of(ExtendedStrawBedTileEntity::new, ExtendedBlocks.STRAW_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedOrcBedTileEntity>> ORC_BED = register("orc_bed", () -> Builder.of(ExtendedOrcBedTileEntity::new, ExtendedBlocks.ORC_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedBlackFurBedTileEntity>> BLACK_FUR_BED = register("black_fur_bed", () -> Builder.of(ExtendedBlackFurBedTileEntity::new, ExtendedBlocks.BLACK_FUR_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedFurBedTileEntity>> FUR_BED = register("fur_bed", () -> Builder.of(ExtendedFurBedTileEntity::new, ExtendedBlocks.FUR_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedGrayFurBedTileEntity>> GRAY_FUR_BED = register("gray_fur_bed", () -> Builder.of(ExtendedGrayFurBedTileEntity::new, ExtendedBlocks.GRAY_FUR_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedSilverFurBedTileEntity>> SILVER_FUR_BED = register("silver_fur_bed", () -> Builder.of(ExtendedSilverFurBedTileEntity::new, ExtendedBlocks.SILVER_FUR_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedWhiteFurBedTileEntity>> WHITE_FUR_BED = register("white_fur_bed", () -> Builder.of(ExtendedWhiteFurBedTileEntity::new, ExtendedBlocks.WHITE_FUR_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedNoRenderTileEntity>> NORENDER = register("no_render_tileentity", () -> Builder.of(ExtendedNoRenderTileEntity::new, ExtendedBlocks.BEDROLL.get()));
	public static RegistryObject<TileEntityType<ExtendedBookshelfTileEntity>> BOOKSHELF = register("bookshelf", () -> Builder.of(ExtendedBookshelfTileEntity::new, ExtendedBlocks.EXTENDED_BOOKSHELF.get()));
	public static RegistryObject<TileEntityType<ExtendedWeaponRackTileEntity>> WEAPON_RACK = register("weapon_rack", () -> Builder.of(ExtendedWeaponRackTileEntity::new, ExtendedBlocks.WEAPON_RACK.get()));
	public static RegistryObject<TileEntityType<ExtendedReedBasketTileEntity>> REED_BASKET = register("reed_basket", () -> Builder.of(ExtendedReedBasketTileEntity::new, ExtendedBlocks.REED_BASKET.get()));
	public static RegistryObject<TileEntityType<ExtendedOrcBarrelTileEntity>> ORC_BARREL = register("orc_barrel", () -> Builder.of(ExtendedOrcBarrelTileEntity::new, ExtendedBlocks.ORC_BARREL.get()));
	public static RegistryObject<TileEntityType<ExtendedStoneGrinderTileEntity>> STONE_GRINDER = register("stone_grinder", () -> Builder.of(ExtendedStoneGrinderTileEntity::new, ExtendedBlocks.STONE_GRINDER.get()));
	public static RegistryObject<TileEntityType<ExtendedMEBannerTileEntity>> BANNER_STANDING = register("banner_stand", () -> Builder.of(ExtendedMEBannerTileEntity::new, ExtendedBannerTypes.getContainedStandingBlocks()));
	public static RegistryObject<TileEntityType<ExtendedMEHangingBannerTileEntity>> BANNER_HANGING = register("banner_hang", () -> Builder.of(ExtendedMEHangingBannerTileEntity::new, ExtendedBannerTypes.getContainedHangingBlocks()));
	public static RegistryObject<TileEntityType<ExtendedGrassVoidTileEntity>> GRASS_VOID = register("grass_void", () -> Builder.of(ExtendedGrassVoidTileEntity::new, ExtendedBlocks.GRASS_VOID.get()));
	public static RegistryObject<TileEntityType<ExtendedStoneTextSignTileEntity>> STONE_SIGN = register("stone_engraving", () -> Builder.of(ExtendedStoneTextSignTileEntity::new, ExtendedBlocks.STONE_SIGN.get()));
	public static RegistryObject<TileEntityType<ExtendedDwarvenBedTileEntity>> DWARVEN_BED = register("dwarven_bed", () -> Builder.of(ExtendedDwarvenBedTileEntity::new, ExtendedBlocks.DWARVEN_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedHoistTileEntity>> HOIST = register("mine_hoist", () -> Builder.of(ExtendedHoistTileEntity::new, ExtendedBlocks.HOIST.get()));
	public static RegistryObject<TileEntityType<ExtendedLargePotTileEntity>> LARGE_POT = register("large_pot", () -> Builder.of(ExtendedLargePotTileEntity::new, ExtendedBlocks.LARGE_POT.get()));
	public static RegistryObject<TileEntityType<ExtendedChimneyTileEntity>> CHIMNEY_POT = register("chimney_pot", () -> Builder.of(ExtendedChimneyTileEntity::new, ExtendedBlocks.CHIMNEYPOT.get()));
	public static RegistryObject<TileEntityType<ExtendedHighElvenBedTileEntity>> HIGH_ELVEN_BED = register("high_elven_bed", () -> Builder.of(ExtendedHighElvenBedTileEntity::new, ExtendedBlocks.HIGH_ELVEN_BED.get()));
	public static RegistryObject<TileEntityType<ExtendedCrucibleTileEntity>> CRUCIBLE = register("crucible", () -> Builder.of(ExtendedCrucibleTileEntity::new, ExtendedBlocks.CRUCIBLE.get()));

	public static void register(IEventBus bus) {
		BLOCK_ENTITIES.register(bus);
	}

	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(final String key, final Supplier<TileEntityType.Builder<T>> builderSup) {
		Type<?> type = Util.fetchChoiceType(TypeReferences.BLOCK_ENTITY, key);
		return BLOCK_ENTITIES.register(key, () -> builderSup.get().build(type));
	}
}
