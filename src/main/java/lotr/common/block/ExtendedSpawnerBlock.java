package lotr.common.block;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrExtended.common.enums.ExtendedEntitySpawnTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ExtendedSpawnerBlock extends Block {

	private final ExtendedEntitySpawnTypes entitySpawn;

	public ExtendedSpawnerBlock(Properties p_i48440_1_, ExtendedEntitySpawnTypes entitySpawn) {
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
}
