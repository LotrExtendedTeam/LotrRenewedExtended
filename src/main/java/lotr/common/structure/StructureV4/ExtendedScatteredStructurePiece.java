package lotr.common.structure.StructureV4;

import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;

/*
 * Copied and modified from {@Link ScatteredStructurePiece}
 * */
public abstract class ExtendedScatteredStructurePiece extends StructurePiece {
	protected int width;
	protected int height;
	protected int depth;
	protected int heightPosition = -1;

	public ExtendedScatteredStructurePiece(IStructurePieceType pieceType, Random random, int posX, int posY, int posZ, int width, int height, int depth, Direction direction) {
		super(pieceType, 0);
		this.width = width;
		this.height = height;
		this.depth = depth;
		if(direction==null || !Direction.Plane.HORIZONTAL.test(direction)) {
			direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		}
		this.setOrientation(direction);
	      if (this.getOrientation().getAxis() == Direction.Axis.Z) {
	          this.boundingBox = new MutableBoundingBox(posX, posY, posZ, posX + width - 1, posY + height - 1, posZ + depth - 1);
	       } else {
	          this.boundingBox = new MutableBoundingBox(posX, posY, posZ, posX + depth - 1, posY + height - 1, posZ + width - 1);
	       }
	}

	protected ExtendedScatteredStructurePiece(IStructurePieceType type, CompoundNBT nbt) {
		super(type, nbt);
		this.width = nbt.getInt("Width");
		this.height = nbt.getInt("Height");
		this.depth = nbt.getInt("Depth");
		this.heightPosition = nbt.getInt("HPos");
	}

	protected void addAdditionalSaveData(CompoundNBT nbt) {
		nbt.putInt("Width", this.width);
		nbt.putInt("Height", this.height);
		nbt.putInt("Depth", this.depth);
		nbt.putInt("HPos", this.heightPosition);
	}

	protected boolean updateAverageGroundHeight(IWorld world, MutableBoundingBox boundingBox, int yOffset) {
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
						i += world.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable)
														.getY();
						++j;
					}
				}
			}

			if (j == 0) {
				return false;
			} else {
				this.heightPosition = i / j;
				this.boundingBox.move(0, this.heightPosition - this.boundingBox.y0 + yOffset, 0);
				return true;
			}
		}
	}
}