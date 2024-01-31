package lotr.common.structure.StructureV4.abstractPieces;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.enums.StructureType;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
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
 * Copied and modified from {@Link ScatteredStructurePiece}
 * */
public abstract class ExtendedBaseStructurePiece extends StructurePiece {
	protected int width;
	protected int height;
	protected int depth;
	protected int initialX;
	protected int initialZ;
	protected int heightPosition = -1;
	protected final StructureType structureType;

	public ExtendedBaseStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(pieceType, 0);
		this.structureType = structureType;
		this.width = defaultStructureWidth();
		this.height = defaultStructureHeight();
		this.depth = defaultStructureDepth();
		this.initialX = position.getX();
		this.initialZ = position.getZ();
		if(direction==null || !Direction.Plane.HORIZONTAL.test(direction)) {
			direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		}
		this.setOrientation(direction);
	      if (this.getOrientation().getAxis() == Direction.Axis.Z) {
	          this.boundingBox = new MutableBoundingBox(position.getX(), position.getY(), position.getZ(), position.getX() + width - 1, position.getY() + height - 1, position.getZ() + depth - 1);
	       } else {
	          this.boundingBox = new MutableBoundingBox(position.getX(), position.getY(), position.getZ(), position.getX() + depth - 1, position.getY() + height - 1, position.getZ() + width - 1);
	       }
	}

	public abstract int defaultStructureWidth();
	public abstract int defaultStructureHeight();
	public abstract int defaultStructureDepth();

	protected ExtendedBaseStructurePiece(IStructurePieceType type, CompoundNBT nbt) {
		super(type, nbt);
		this.width = nbt.getInt("Width");
		this.height = nbt.getInt("Height");
		this.depth = nbt.getInt("Depth");
		this.initialX = nbt.getInt("initialX");
		this.initialZ = nbt.getInt("initialZ");
		this.heightPosition = nbt.getInt("HPos");
		this.structureType = StructureType.valueOf(nbt.getString("SType"));
	}

	protected void addAdditionalSaveData(CompoundNBT nbt) {
		nbt.putInt("Width", this.width);
		nbt.putInt("Height", this.height);
		nbt.putInt("Depth", this.depth);
		nbt.putInt("initialX", this.initialX);
		nbt.putInt("initialZ", this.initialZ);
		nbt.putInt("HPos", this.heightPosition);
		nbt.putString("SType", this.structureType.toString());
	}

	@Override
	public boolean postProcess(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, MutableBoundingBox box, ChunkPos cPos, BlockPos bPos) {
		return this.preProcessInternal(world, structureManager, chunkGen, random, cPos);
	}

	private boolean preProcessInternal(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, ChunkPos cPos) {
		if(isSetToGroundHeight()&&structureType!=null&&structureType.equals(StructureType.NATURAL_SINGLE)) {
			updateGroundHeight(world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, initialX, initialZ));
		}
		return this.placeBlocks(world, structureManager, chunkGen, random, cPos, structureType);
	}

	public abstract boolean placeBlocks(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, ChunkPos cPos, StructureType structureType);

	protected void updateGroundHeight(int newHeight) {
		if(newHeight>=0) {
			boundingBox = new MutableBoundingBox(boundingBox.x0, newHeight, boundingBox.z0, boundingBox.x1, newHeight + height - 1, boundingBox.z1);
		}
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

	public boolean isSetToGroundHeight() {
		return true;
	}

	@Override
	protected void placeBlock(ISeedReader p_175811_1_, BlockState p_175811_2_, int p_175811_3_, int p_175811_4_, int p_175811_5_, MutableBoundingBox p_175811_6_) {
		BlockPos blockpos = new BlockPos(this.getWorldX(p_175811_3_, p_175811_5_), this.getWorldY(p_175811_4_), this.getWorldZ(p_175811_3_, p_175811_5_));
		if (p_175811_6_.isInside(blockpos)) {
			// if (this.mirror != Mirror.NONE) {
			// p_175811_2_ = p_175811_2_.mirror(this.mirror);
			// }

			// if (this.rotation != Rotation.NONE) {
			// p_175811_2_ = p_175811_2_.rotate(this.rotation);
			// }

			p_175811_1_.setBlock(blockpos, p_175811_2_, 2);
			FluidState fluidstate = p_175811_1_.getFluidState(blockpos);
			if (!fluidstate.isEmpty()) {
				p_175811_1_.getLiquidTicks().scheduleTick(blockpos, fluidstate.getType(), 0);
			}
		}
		System.out.println("Block not in bounds: "+blockpos.toString());
	}
}