package lotr.common.structure.StructureV4.abstractPieces;

import java.util.Random;

import javax.annotation.Nullable;

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
	protected int initialY;
	protected int initialZ;
	protected int heightPosition = -1;
	@Nullable
	private final Direction ssDirection;
	private StructureType structureType;

	public ExtendedBaseStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(pieceType, 0);
		this.structureType = structureType;
		this.width = defaultStructureWidth();
		this.height = defaultStructureHeight();
		this.depth = defaultStructureDepth();
		this.initialX = position.getX();
		this.initialY = position.getY();
		this.initialZ = position.getZ();
		if(direction==null || !Direction.Plane.HORIZONTAL.test(direction)) {
			ssDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		}else {
			ssDirection = direction;
		}
		if (ssDirection.getAxis() == Direction.Axis.Z) {
			this.boundingBox = new MutableBoundingBox(initialX, initialY, initialZ, initialX + width - 1, initialY + height - 1, initialZ + depth - 1);
		} else {
			this.boundingBox = new MutableBoundingBox(initialX, initialY, initialZ, initialX + depth - 1, initialY + height - 1, initialZ + width - 1);
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
		this.initialY = nbt.getInt("initialY");
		this.initialZ = nbt.getInt("initialZ");
		int i = nbt.getInt("Direction");
	    this.ssDirection = (i == -1) ? null : Direction.from2DDataValue(i);
		this.heightPosition = nbt.getInt("HPos");
		this.structureType = StructureType.valueOf(nbt.getString("SType"));
	}

	protected void addAdditionalSaveData(CompoundNBT nbt) {
		nbt.putInt("Width", this.width);
		nbt.putInt("Height", this.height);
		nbt.putInt("Depth", this.depth);
		nbt.putInt("initialX", this.initialX);
		nbt.putInt("initiaY", this.initialY);
		nbt.putInt("initialZ", this.initialZ);
		nbt.putInt("HPos", this.heightPosition);
		nbt.putInt("Direction", ssDirection == null ? -1 : ssDirection.get2DDataValue());
		nbt.putString("SType", this.structureType.toString());
	}

	@Override
	public boolean postProcess(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, MutableBoundingBox box, ChunkPos cPos, BlockPos bPos) {
		return this.preProcessInternal(world, structureManager, chunkGen, random, cPos);
	}

	private boolean preProcessInternal(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, ChunkPos cPos) {
		if(isSetToGroundHeight()&&structureType!=null&&structureType.equals(StructureType.NATURAL_SINGLE)) {
			updateGroundHeightValue(world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, initialX, initialZ));
		}
		return this.placeBlocks(world, structureManager, chunkGen, random, cPos, structureType);
	}

	public abstract boolean placeBlocks(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, ChunkPos cPos, StructureType structureType);

	protected void updateGroundHeightValue(int newHeight) {
		if(newHeight>=0) {
			initialY = newHeight;
			boundingBox = new MutableBoundingBox(boundingBox.x0, initialY, boundingBox.z0, boundingBox.x1, initialY + height - 1, boundingBox.z1);
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
	protected void placeBlock(ISeedReader world, BlockState blockState, int xPos, int yPos, int zPos, MutableBoundingBox box) {
		BlockPos blockpos = new BlockPos(this.getWorldX(xPos, zPos), this.getWorldY(yPos), this.getWorldZ(xPos, zPos));
		if (box.isInside(blockpos)) {
			// if (this.mirror != Mirror.NONE) {
			// p_175811_2_ = p_175811_2_.mirror(this.mirror);
			// }

			// if (this.rotation != Rotation.NONE) {
			// p_175811_2_ = p_175811_2_.rotate(this.rotation);
			// }

			world.setBlock(blockpos, blockState, 2);
			FluidState fluidstate = world.getFluidState(blockpos);
			if (!fluidstate.isEmpty()) {
				world.getLiquidTicks().scheduleTick(blockpos, fluidstate.getType(), 0);
			}
		}
		else {
			System.out.println("Block not in bounds: "+blockpos.toString());
		}
	}
	
	@Override
	protected int getWorldX(int xCord, int zCord) {
		if (getSsDirection() == null) {
			return xCord;
		} else {
			if(getSsDirection().equals(Direction.NORTH)) {
				return initialX + xCord;
			}
			if(getSsDirection().getOpposite().equals(Direction.NORTH)) {
				return initialX - xCord;
			}
			if(getSsDirection().getClockWise().equals(Direction.NORTH)) {
				return initialX + zCord;
			}
			if(getSsDirection().getCounterClockWise().equals(Direction.NORTH)) {
				return initialX - zCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type: "+getSsDirection().toString());
	}

	@Override
	protected int getWorldY(int yCord) {
		return initialY + yCord;
	}

	@Override
	protected int getWorldZ(int xCord, int zCord) {
		if (getSsDirection() == null) {
			return zCord;
		} else {
			if(getSsDirection().equals(Direction.NORTH)) {
				return initialZ + zCord;
			}
			if(getSsDirection().getOpposite().equals(Direction.NORTH)) {
				return initialZ - zCord;
			}
			if(getSsDirection().getClockWise().equals(Direction.NORTH)) {
				return initialZ - xCord;
			}
			if(getSsDirection().getCounterClockWise().equals(Direction.NORTH)) {
				return initialZ + xCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type: "+getSsDirection().toString());

	}

	public Direction getSsDirection() {
		if(ssDirection==null) {
			throw new IllegalArgumentException("Was asked to fetch an invalid structure direction");
		}
		return ssDirection;
	}
}