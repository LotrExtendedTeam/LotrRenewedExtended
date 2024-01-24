package lotr.common.structure.StructureV4;

import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;

public abstract class ExtendedStructurePiece extends ScatteredStructurePiece {

	protected ExtendedStructurePiece(IStructurePieceType piece, Random random, int x0, int y0, int z0, int width, int height, int depth) {
		super(piece, random, x0, y0, z0, width, height, depth);

	}

	protected ExtendedStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		this.addMoreSaveData(nbt);
	}

	protected abstract void addMoreSaveData(CompoundNBT nbt);

	@Override
	public abstract boolean postProcess(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen,
			Random random, MutableBoundingBox b, ChunkPos cPos, BlockPos bPos);
}
