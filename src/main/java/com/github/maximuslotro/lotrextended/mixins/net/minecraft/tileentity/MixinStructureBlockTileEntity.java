package com.github.maximuslotro.lotrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;

import com.github.maximuslotro.lotrextended.mixinhelpers.MixinConstants;

import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(StructureBlockTileEntity.class)
public abstract class MixinStructureBlockTileEntity extends TileEntity {

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param tileEntity A placeholder variable instance of TileEntityType
	 * */
	public MixinStructureBlockTileEntity(TileEntityType<?> tileEntity) {
		super(tileEntity);
	}

	@Override
	public double getViewDistance() {
		return MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance;
	}
}
