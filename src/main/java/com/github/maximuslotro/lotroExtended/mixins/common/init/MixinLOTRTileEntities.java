package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedTileEntities;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRTileEntities;
import lotr.common.tileentitie.spawners.HobbitBartenderSpawnerTileEntity;
import net.minecraft.block.Block;
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
    	//ExtendedTileEntities.HOBBIT_BARTENDER_SPAWNER_TILEENTITY = register("alloy_forge", () -> Builder.of(HobbitBartenderSpawnerTileEntity::new, new Block[] { LOTRBlocks.ALLOY_FORGE.get() }));
    }
}
