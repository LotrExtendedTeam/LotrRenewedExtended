package com.github.maximuslotro.lotrrextended.common.tileentity.chests;

import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public abstract class ExtendedChestTileEntity<T extends ChestTileEntity> extends ChestTileEntity {
	private int tickInterval;

	public ExtendedChestTileEntity(TileEntityType<T> tileEntityType) {
		super(tileEntityType);
	}

	public abstract SoundEvent openSound();

	public abstract SoundEvent closeCound();

	@Override
	public void tick() {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		++this.tickInterval;
		this.openCount = getOpenCount(this.level, this, this.tickInterval, i, j, k, this.openCount);
		this.oOpenness = this.openness;
		float f = 0.1F;
		if (this.openCount > 0 && this.openness == 0.0F) {
			this.playSound(openSound());
		}

		if (this.openCount == 0 && this.openness > 0.0F || this.openCount > 0 && this.openness < 1.0F) {
			float f1 = this.openness;
			if (this.openCount > 0) {
				this.openness += 0.1F;
			} else {
				this.openness -= 0.1F;
			}

			if (this.openness > 1.0F) {
				this.openness = 1.0F;
			}

			float f2 = 0.5F;
			if (this.openness < 0.5F && f1 >= 0.5F) {
				this.playSound(closeCound());
			}

			if (this.openness < 0.0F) {
				this.openness = 0.0F;
			}
		}

	}

	private void playSound(SoundEvent p_195483_1_) {
		ChestType chesttype = this.getBlockState().getValue(ChestBlock.TYPE);
		if (chesttype != ChestType.LEFT) {
			double d0 = (double)this.worldPosition.getX() + 0.5D;
			double d1 = (double)this.worldPosition.getY() + 0.5D;
			double d2 = (double)this.worldPosition.getZ() + 0.5D;
			if (chesttype == ChestType.RIGHT) {
				Direction direction = ChestBlock.getConnectedDirection(this.getBlockState());
				d0 += (double)direction.getStepX() * 0.5D;
				d2 += (double)direction.getStepZ() * 0.5D;
			}

			this.level.playSound((PlayerEntity)null, d0, d1, d2, p_195483_1_, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
		}
	}
}