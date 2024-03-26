package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.tileentity;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;

@Mixin(StructureBlockTileEntity.class)
public class MixinStructureBlockTileEntity {
	
	@Shadow
	private BlockPos structurePos;

	@Shadow
	private BlockPos structureSize;

	@Shadow
	private StructureMode mode;

	@Shadow
	private List<StructureBlockTileEntity> getNearbyCornerBlocks(BlockPos p_184418_1_, BlockPos p_184418_2_) {return null;}

	@Shadow
	private List<StructureBlockTileEntity> filterRelatedCornerBlocks(List<StructureBlockTileEntity> p_184415_1_) {return null;}

	@Shadow
	private MutableBoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<StructureBlockTileEntity> p_184416_2_) {return null;}

	@Inject(method = "detectSize()Z", at = @At("HEAD"), cancellable = true)
	public void detectSize(CallbackInfoReturnable<Boolean> cl) {
		if (!ExtendedServerConfig.enableDevMode.get()) {
			return;
		}
		if (this.mode != StructureMode.SAVE) {
			return;
		} else {
			BlockPos blockpos = ((StructureBlockTileEntity)(Object)this).getBlockPos();
			int i = 100;
			BlockPos blockpos1 = new BlockPos(blockpos.getX() - i, 0, blockpos.getZ() - i);
			BlockPos blockpos2 = new BlockPos(blockpos.getX() + i, 255, blockpos.getZ() + i);
			List<StructureBlockTileEntity> list = this.getNearbyCornerBlocks(blockpos1, blockpos2);
			List<StructureBlockTileEntity> list1 = this.filterRelatedCornerBlocks(list);
			if (list1.size() < 1) {
				return;
			} else {
				MutableBoundingBox mutableboundingbox = this.calculateEnclosingBoundingBox(blockpos, list1);
				if (mutableboundingbox.x1 - mutableboundingbox.x0 > 1 && mutableboundingbox.y1 - mutableboundingbox.y0 > 1 && mutableboundingbox.z1 - mutableboundingbox.z0 > 1) {
					this.structurePos = new BlockPos(mutableboundingbox.x0 - blockpos.getX() + 1, mutableboundingbox.y0 - blockpos.getY() + 1, mutableboundingbox.z0 - blockpos.getZ() + 1);
					this.structureSize = new BlockPos(mutableboundingbox.x1 - mutableboundingbox.x0 - 1, mutableboundingbox.y1 - mutableboundingbox.y0 - 1, mutableboundingbox.z1 - mutableboundingbox.z0 - 1);
					((StructureBlockTileEntity)(Object)this).setChanged();
					BlockState blockstate = ((StructureBlockTileEntity)(Object)this).getLevel().getBlockState(blockpos);
					((StructureBlockTileEntity)(Object)this).getLevel().sendBlockUpdated(blockpos, blockstate, blockstate, 3);
					cl.setReturnValue(true);
				} else {
					return;
				}
			}
		}
	}
}
