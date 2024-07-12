package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.maximuslotro.lotrrextended.mixinhelpers.MixinConstants;

import net.minecraft.tileentity.StructureBlockTileEntity;

@Mixin(StructureBlockTileEntity.class)
public abstract class MixinStructureBlockTileEntity {// extends TileEntity {
	
//	public MixinStructureBlockTileEntity(TileEntityType<?> p_i48289_1_) {
//		super(p_i48289_1_);
//	}
//
//	@Override
//	public double getViewDistance() {
//		return MixinConstants.StructureLimit;
//	}

	@ModifyConstant(method = "load", constant = {@Constant(intValue = 48), @Constant(intValue = -48)})
	private int getMaxSizePos(int value) {
		return value > 0 ? MixinConstants.StructureLimitPosBounds : MixinConstants.StructureLimitNegBounds;
	}
}
