package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnGroupTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedNoRenderTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedGrayFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedReedBasketTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedTileEntities;
import lotr.common.init.LOTRTileEntities;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraftforge.fml.RegistryObject;

@Mixin(LOTRTileEntities.class)
public class MixinLOTRTileEntities {

	@Shadow(remap=false)
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String key, Supplier<Builder<T>> builderSup) {
		return null;
    }

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

    }
}
