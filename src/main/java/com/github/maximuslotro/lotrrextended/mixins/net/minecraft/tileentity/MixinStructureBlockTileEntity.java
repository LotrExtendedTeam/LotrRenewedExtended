package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.maximuslotro.lotrrextended.mixinhelpers.MixinConstants;

import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(StructureBlockTileEntity.class)
public abstract class MixinStructureBlockTileEntity extends TileEntity {
	
	public MixinStructureBlockTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	@Override
	public double getViewDistance() {
		return MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance;
	}

	@ModifyConstant(method = "load", constant = {@Constant(intValue = 48), @Constant(intValue = -48)})
	private int getMaxSizePos(int value) {
		return value > 0 ? MixinConstants.StructureLimitPosBounds : MixinConstants.StructureLimitNegBounds;
	}
}
