package lotr.common.structureV4.structure.abstractPieces;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.enums.StructureType;
import com.github.maximuslotro.lotroExtended.utils.DirectionTools;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
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
			setOrientation(Direction.Plane.HORIZONTAL.getRandomDirection(random));
		}else {
			setOrientation(direction);
		}
		if (getOrientation().getAxis() == Direction.Axis.Z) {
			this.boundingBox = MutableBoundingBox.createProper(initialX, initialY, initialZ, initialX + width - 1, initialY + height - 1, initialZ + depth - 1);
		} else {
			this.boundingBox = MutableBoundingBox.createProper(initialX, initialY, initialZ, initialX + depth - 1, initialY + height - 1, initialZ + width - 1);
		}
	}

	public int defaultStructureWidth() {
		return 0;
	};
	public int defaultStructureHeight() {
		return 0;
	};
	public int defaultStructureDepth() {
		return 0;
	};

	public ExtendedBaseStructurePiece(IStructurePieceType type, CompoundNBT nbt) {
		super(type, nbt);
		this.width = defaultStructureWidth();
		this.height = defaultStructureHeight();
		this.depth = defaultStructureDepth();
		this.initialX = nbt.getInt("initialX");
		this.initialY = nbt.getInt("initialY");
		this.initialZ = nbt.getInt("initialZ");
		this.structureType = StructureType.valueOf(nbt.getString("SType"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		nbt.putInt("initialX", this.initialX);
		nbt.putInt("initialY", this.initialY);
		nbt.putInt("initialZ", this.initialZ);
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

	public boolean isSetToGroundHeight() {
		return true;
	}

	@Override
	protected void placeBlock(ISeedReader world, BlockState blockState, int xPos, int yPos, int zPos, MutableBoundingBox box) {
		this.placeBlockInternal(world, blockState, new BlockPos(this.getWorldX(xPos, zPos), this.getWorldY(yPos), this.getWorldZ(xPos, zPos)), box);
	}

	protected void placeBlockInternal(ISeedReader world, BlockState blockState, BlockPos blockpos, MutableBoundingBox box) {
		if (box.isInside(blockpos)) {
			Rotation rot = DirectionTools.getRotationDirection(getSsDirection(), Direction.NORTH);
			if(rot!=Rotation.NONE) {
				blockState = blockState.rotate(world, blockpos, rot);
			}

			world.setBlock(blockpos, blockState, 2);
			FluidState fluidstate = world.getFluidState(blockpos);
			if (!fluidstate.isEmpty()) {
				world.getLiquidTicks().scheduleTick(blockpos, fluidstate.getType(), 0);
			}
		}
	}

	@Override
	protected void generateBox(ISeedReader world, MutableBoundingBox box, int smallX, int smallY, int smallZ, int bigX, int bigY, int bigZ, BlockState blockStateOutside, BlockState blockStateInside, boolean truePos) {
		for (int i = smallX; i <= bigX; ++i) {
			for (int j = smallY; j <= bigY; ++j) {
				for (int k = smallZ; k <= bigZ; ++k) {
					if(truePos) {
						if (i != smallX && i != bigX && j != smallY && j != bigY && k != smallZ && k != bigZ) {
							this.placeBlockInternal(world, blockStateInside,  new BlockPos(i, j, k), box);
						} else {
							this.placeBlockInternal(world, blockStateOutside,  new BlockPos(i, j, k), box);
						}
					}else {
						if (i != smallX && i != bigX && j != smallY && j != bigY && k != smallZ && k != bigZ) {
							this.placeBlock(world, blockStateInside, i, j, k, box);
						} else {
							this.placeBlock(world, blockStateOutside, i, j, k, box);
						}
					}
				}
			}
		}
	}

	@Override
	protected int getWorldX(int xCord, int zCord) {
		if (getSsDirection() == null) {
			return 0;
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
			return 0;
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
		if(getOrientation()==null) {
			setOrientation(Direction.Plane.HORIZONTAL.getRandomDirection((new Random())));
		}
		return getOrientation();
	}

	public StructureType getStructureType() {
		return structureType;
	}
}