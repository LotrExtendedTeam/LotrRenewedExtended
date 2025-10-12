package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedChimneyPotBlock;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtendedChimneyTileEntity extends TileEntity implements ITickableTileEntity {

	public ExtendedChimneyTileEntity() {
		super(ExtendedTileEntities.CHIMNEY_POT.get());
	}

	@Override
	public void tick() {
		if (this.level.isClientSide && !this.getBlockState().getValue(ExtendedChimneyPotBlock.WATERLOGGED)) {
			this.makeParticles();
		}
	}

	private void makeParticles() {
		World world = this.getLevel();
		if (world != null) {
			BlockPos blockpos = this.getBlockPos();
			Random random = world.random;
			if (random.nextFloat() < 0.11F) {
				for(int i = 0; i < random.nextInt(2) + 2; ++i) {
					ExtendedChimneyPotBlock.makeParticles(world, blockpos, false);
				}
			}
		}
	}
}