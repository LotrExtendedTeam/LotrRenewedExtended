package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.enums.BannerTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnGroupTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedGrassVoidTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedHoistTileEntity;
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
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedOrcBarrelTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedReedBasketTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedTileEntities;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRTileEntities;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Used to register new recipe handlers added by Extended
 * @Mixin (LOTRTileEntities.class)
 * */
@SuppressWarnings("deprecation")
@Mixin(LOTRTileEntities.class)
public class MixinLOTRTileEntities {
	/**
	 * Get the tileEntity DeferredRegister for Renewed's tileEntities
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES;

	/**
	 * @author maximuslotro
	 * @reason Need to change VesselTileEntityAppliedBlocks to add more drink container types
	 * @Overwrite (remap=false)
	 * @param <T> the type of module extending TileEntity
	 * @param key The id of the tileEntity
	 * @param builderSup A list of blocks to bind the tileEntity to
	 * @return A RegistryObject instance for the tileEntity
	 */
	@SuppressWarnings("unchecked")
	@Overwrite(remap=false)
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String key, Supplier<TileEntityType.Builder<T>> builderSup) {
		Supplier<TileEntityType.Builder<T>> builderSupEXTENDED;
		if("vessel_drink".equals(key)) {
			builderSupEXTENDED = () -> (Builder<T>) Builder.of(VesselDrinkTileEntity::new, LOTRBlocks.WOODEN_MUG.get(), LOTRBlocks.CERAMIC_MUG.get(),
					LOTRBlocks.GOLDEN_GOBLET.get(), LOTRBlocks.SILVER_GOBLET.get(), LOTRBlocks.COPPER_GOBLET.get(), LOTRBlocks.WOODEN_CUP.get(),
					LOTRBlocks.ALE_HORN.get(), LOTRBlocks.GOLDEN_ALE_HORN.get(), ExtendedBlocks.SKULL_GOBLET.get());
		}else {
			builderSupEXTENDED = builderSup;
		}

		Type<?> datafixType = null;
		try {
			datafixType = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, key);
		} catch (IllegalArgumentException var4) {
			if (SharedConstants.IS_RUNNING_IN_IDE) {
				throw var4;
			}
		}
		final Type<?> datafixType2 = datafixType;
		return TILE_ENTITIES.register(key, () -> builderSupEXTENDED.get().build(datafixType2));
	}
	/**
	 * @reason Register Extended's tileEntities after Renewed's static registering is done, but before the differed register is registered
	 * @Inject (method = "register()V", at = @At("HEAD"), remap=false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
    private static void bootstrapExtendedItems(CallbackInfo ci) {
    	for(ExtendedEntitySpawnTypes type : ExtendedEntitySpawnTypes.values()) {
    		ExtendedTileEntities.SPAWNER_TILEENTITYS.put(type.getSerializedName(),
    				register(type.getSerializedName()+"_spawner_tileentity",
    						() -> Builder.of(type.getSupplier(), ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(type.getSerializedName()).get())));
    	}
    	for(ExtendedEntitySpawnGroupTypes type : ExtendedEntitySpawnGroupTypes.values()) {
    		ExtendedTileEntities.SPAWNER_GROUP_TILEENTITYS.put(type.getSerializedName(),
    				register(type.getSerializedName()+"_group_spawner_tileentity",
    						() -> Builder.of(type.getSupplier(), ExtendedBlocks.ENTITYGROUPSPAWNERBLOCKS.get(type.getSerializedName()).get())));
    	}
    	ExtendedTileEntities.STONE_CHEST = register("stone_chest_tileentity", () -> Builder.of(ExtendedStoneChestTileEntity::new, ExtendedBlocks.STONE_CHEST.get()));
    	ExtendedTileEntities.STRAW_BED = register("straw_bed_tileentity", () -> Builder.of(ExtendedStrawBedTileEntity::new, ExtendedBlocks.STRAW_BED.get()));
    	ExtendedTileEntities.ORC_BED = register("orc_bed_tileentity", () -> Builder.of(ExtendedOrcBedTileEntity::new, ExtendedBlocks.ORC_BED.get()));
    	ExtendedTileEntities.BLACK_FUR_BED = register("black_fur_bed_tileentity", () -> Builder.of(ExtendedBlackFurBedTileEntity::new, ExtendedBlocks.BLACK_FUR_BED.get()));
    	ExtendedTileEntities.FUR_BED = register("fur_bed_tileentity", () -> Builder.of(ExtendedFurBedTileEntity::new, ExtendedBlocks.FUR_BED.get()));
    	ExtendedTileEntities.GRAY_FUR_BED = register("gray_fur_bed_tileentity", () -> Builder.of(ExtendedGrayFurBedTileEntity::new, ExtendedBlocks.GRAY_FUR_BED.get()));
    	ExtendedTileEntities.SILVER_FUR_BED = register("silver_fur_bed_tileentity", () -> Builder.of(ExtendedSilverFurBedTileEntity::new, ExtendedBlocks.SILVER_FUR_BED.get()));
    	ExtendedTileEntities.WHITE_FUR_BED = register("white_fur_bed_tileentity", () -> Builder.of(ExtendedWhiteFurBedTileEntity::new, ExtendedBlocks.WHITE_FUR_BED.get()));
    	ExtendedTileEntities.NORENDER = register("no_render_tileentity", () -> Builder.of(ExtendedNoRenderTileEntity::new, ExtendedBlocks.BEDROLL.get()));
    	ExtendedTileEntities.BOOKSHELF = register("bookshelf_tileentity", () -> Builder.of(ExtendedBookshelfTileEntity::new, Blocks.BOOKSHELF));
    	ExtendedTileEntities.WEAPON_RACK = register("weapon_rack_tileentity", () -> Builder.of(ExtendedWeaponRackTileEntity::new, ExtendedBlocks.WEAPON_RACK.get()));
    	ExtendedTileEntities.REED_BASKET = register("reed_basket_tileentity", () -> Builder.of(ExtendedReedBasketTileEntity::new, ExtendedBlocks.REED_BASKET.get()));
    	ExtendedTileEntities.ORC_BARREL = register("orc_barrel_tileentity", () -> Builder.of(ExtendedOrcBarrelTileEntity::new, ExtendedBlocks.ORC_BARREL.get()));
    	ExtendedTileEntities.MILLSTONE = register("stone_grinder_tileentity", () -> Builder.of(ExtendedStoneGrinderTileEntity::new, ExtendedBlocks.STONE_GRINDER.get()));
    	ExtendedTileEntities.BANNER_STANDING = register("banner_s_tileentity", () -> Builder.of(ExtendedMEBannerTileEntity::new, BannerTypes.getContainedStandingBlocks()));
    	ExtendedTileEntities.BANNER_HANGING = register("banner_h_tileentity", () -> Builder.of(ExtendedMEHangingBannerTileEntity::new, BannerTypes.getContainedHangingBlocks()));
    	ExtendedTileEntities.GRASS_VOID = register("grass_void_tileentity", () -> Builder.of(ExtendedGrassVoidTileEntity::new, ExtendedBlocks.GRASS_VOID.get()));
    	ExtendedTileEntities.STONE_SIGN = register("stone_engraving", () -> Builder.of(ExtendedStoneTextSignTileEntity::new, ExtendedBlocks.STONE_SIGN.get()));
    	ExtendedTileEntities.DWARVEN_BED = register("dwarven_bed_tileentity", () -> Builder.of(ExtendedDwarvenBedTileEntity::new, ExtendedBlocks.DWARVEN_BED.get()));
    	ExtendedTileEntities.HOIST = register("hoist_tileentity", () -> Builder.of(ExtendedHoistTileEntity::new, ExtendedBlocks.HOIST.get()));
    }
}
