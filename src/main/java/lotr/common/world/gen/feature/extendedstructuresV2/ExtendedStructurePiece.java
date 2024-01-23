package lotr.common.world.gen.feature.extendedstructuresV2;

import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;

/*
 * Code copied and modified from {@Link ScatteredStructurePiece}
 * */
public abstract class ExtendedStructurePiece extends StructurePiece {
	protected final int width;
	protected final int height;
	protected final int depth;
	protected int heightPosition = -1;

	protected ExtendedStructurePiece(IStructurePieceType piece, Random random, int x0, int y0, int z0, int width,
			int height, int depth) {
		super(piece, 0);
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.setOrientation(Direction.Plane.HORIZONTAL.getRandomDirection(random));
		if (this.getOrientation().getAxis() == Direction.Axis.Z) {
			this.boundingBox = new MutableBoundingBox(x0, y0, z0, x0 + width - 1, y0 + height - 1, z0 + depth - 1);
		} else {
			this.boundingBox = new MutableBoundingBox(x0, y0, z0, x0 + depth - 1, y0 + height - 1, z0 + width - 1);
		}

	}

	protected ExtendedStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
		this.width = nbt.getInt("Width");
		this.height = nbt.getInt("Height");
		this.depth = nbt.getInt("Depth");
		this.heightPosition = nbt.getInt("HPos");
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		nbt.putInt("Width", this.width);
		nbt.putInt("Height", this.height);
		nbt.putInt("Depth", this.depth);
		nbt.putInt("HPos", this.heightPosition);
	}

	protected boolean updateAverageGroundHeight(IWorld world, MutableBoundingBox boundingBox, int p_202580_3_) {
		if (this.heightPosition >= 0) {
			return true;
		} else {
			int i = 0;
			int j = 0;
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			for (int k = this.boundingBox.z0; k <= this.boundingBox.z1; ++k) {
				for (int l = this.boundingBox.x0; l <= this.boundingBox.x1; ++l) {
					blockpos$mutable.set(l, 64, k);
					if (boundingBox.isInside(blockpos$mutable)) {
						i += world.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable).getY();
						++j;
					}
				}
			}

			if (j == 0) {
				return false;
			} else {
				this.heightPosition = i / j;
				this.boundingBox.move(0, this.heightPosition - this.boundingBox.y0 + p_202580_3_, 0);
				return true;
			}
		}
	}

	@Override
	public abstract boolean postProcess(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen,
			Random random, MutableBoundingBox b, ChunkPos cPos, BlockPos bPos);
}
