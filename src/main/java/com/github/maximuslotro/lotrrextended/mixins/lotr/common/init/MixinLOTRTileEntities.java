package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneChestTileEntity;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedTileEntities;
import lotr.common.init.LOTRTileEntities;
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
    	ExtendedTileEntities.STONE_CHEST = register("stone_chest_tileentity", () -> Builder.of(ExtendedStoneChestTileEntity::new, ExtendedBlocks.STONE_CHEST.get()));
    	//register("weapon_rack",() -> Builder.of(ExtendedWeaponRackTileEntity::new, ExtendedBlocks.WEAPON_RACK));	
    }
}
