package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;

import com.github.maximuslotro.lotrrextended.mixinhelpers.MixinConstants;

import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

@Mixin(StructureBlockTileEntity.class)
public abstract class MixinStructureBlockTileEntity extends TileEntity {
	
	public MixinStructureBlockTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	@Override
	public double getViewDistance() {
		return MixinConstants.StructureLimit;
	   }
}
