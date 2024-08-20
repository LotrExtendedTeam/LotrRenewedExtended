package com.github.maximuslotro.lotrrextended.common.block.spawners;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnGroupTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

@SuppressWarnings("deprecation")
public class ExtendedGroupSpawnerBlock extends Block {

	private final ExtendedEntitySpawnGroupTypes entitySpawn;

	public ExtendedGroupSpawnerBlock(Properties p_i48440_1_, ExtendedEntitySpawnGroupTypes entitySpawn) {
		super(p_i48440_1_);
		this.entitySpawn = entitySpawn;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return entitySpawn.hasSupplier();
	}

	@Override
	@Nullable
	public TileEntity createTileEntity(BlockState state, IBlockReader reader) {
		return entitySpawn.getSupplierObject();
	}

	public ExtendedEntitySpawnGroupTypes getEntitySpawn() {
		return entitySpawn;
	}
}
