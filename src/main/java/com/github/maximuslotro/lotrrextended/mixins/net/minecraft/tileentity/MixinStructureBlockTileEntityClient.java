package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.maximuslotro.lotrrextended.mixinhooks.MixinHooks;

import net.minecraft.tileentity.StructureBlockTileEntity;

@Mixin(StructureBlockTileEntity.class)
public class MixinStructureBlockTileEntityClient {
	
	@ModifyConstant(method = "load", constant = {@Constant(intValue = 48), @Constant(intValue = -48)})
	private int getMaxSizePos(int value) {
		return value > 0 ? MixinHooks.StructureLimitPosBounds : MixinHooks.StructureLimitNegBounds;
	}
}
