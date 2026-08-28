package com.github.maximuslotro.lotrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.datafixers.ExtendedMissingMappings;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

@Mixin(TileEntity.class)
public class MixinTileEntity {
	@Redirect(
        method = "loadStatic(Lnet/minecraft/block/BlockState;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/tileentity/TileEntity;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundNBT;getString(Ljava/lang/String;)Ljava/lang/String;"
        )
    )
    private static String remapTileEntityId(CompoundNBT compound, String key) {
        String originalId = compound.getString(key);
        return ExtendedMissingMappings.BLOCK_ENTITIES.getOrDefault(originalId, originalId);
    }
}
