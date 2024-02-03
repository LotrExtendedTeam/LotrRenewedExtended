package lotr.common.structure.StructureV4.abstractPieces;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.enums.StructureType;
import com.github.maximuslotro.lotroExtended.utils.DirectionTools;

import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.structure.util.StructureSize;
import lotr.common.structure.util.StructureSize.StructureBoxValues;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class ExtendedFileBasedStructurePiece extends ExtendedFunctionalStructurePiece{
	private static StructureFile structureFile;
	public final Direction nativeDirection;
	protected ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, StructureType structureType) {
		this(pieceType, random, position, Direction.Plane.HORIZONTAL.getRandomDirection(random), structureType);
	}

	protected ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(pieceType, random, position, direction, structureType);
		if(getStructure()!=null) {
			nativeDirection = structureFile.getNaturalFacingDirection();
			updateStructureParamiters(structureFile);
		}else {
			ExtendedLog.error("FAILED LOADING STRUCTURE FILE!: " + fileName());
			nativeDirection=null;
		}
	}

	protected ExtendedFileBasedStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
		int i = nbt.getInt("NativeDirection");
	    this.nativeDirection = (i == -1) ? null : Direction.from2DDataValue(i);
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("NativeDirection", nativeDirection == null ? -1 : nativeDirection.get2DDataValue());
	}

	protected void updateStructureParamiters(StructureFile file) {
		StructureSize structureSize = file.readGenv4StructureSize();
		this.height = structureSize.getHeight();
		this.width = structureSize.getWidth();
		this.depth = structureSize.getDepth();
		StructureBoxValues boxModifer = structureSize.makeStructureBox(file.getLayers());
		this.boundingBox = MutableBoundingBox.createProper(getWorldX(boxModifer.widthLowX,boxModifer.depthLowZ), 
										getWorldY(boxModifer.heightLowY), 
										getWorldZ(boxModifer.widthLowX,boxModifer.depthLowZ), 
										getWorldX(boxModifer.widthHighX,boxModifer.depthHighZ), 
										getWorldY(boxModifer.heightHighY), 
										getWorldZ(boxModifer.widthHighX,boxModifer.depthHighZ));
	}

	public StructureFile getStructure() {
		if(isCodeDefined()) {
			throw new IllegalStateException("Tried to load a structure file for a code-defined structure! Fix your code! Offendinding class: "+this.getClass().getSimpleName());
		}
		if(structureFile==null) {
			structureFile = getStructure(fileName());
		}
		return structureFile;
	}

	@Override
	protected int getWorldX(int xCord, int zCord) {
		if (getSsDirection() == null || nativeDirection == null) {
			return 0;
		} else {
			if(getSsDirection().equals(nativeDirection)||nativeDirection.equals(Direction.UP)) {
				return initialX + xCord;
			}
			if(getSsDirection().getOpposite().equals(nativeDirection)) {
				return initialX - xCord;
			}
			if(getSsDirection().getClockWise().equals(nativeDirection)) {
				return initialX + zCord;
			}
			if(getSsDirection().getCounterClockWise().equals(nativeDirection)) {
				return initialX - zCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type combination: "+getSsDirection().toString()+" + "+nativeDirection.toString());
	}

	@Override
	protected int getWorldZ(int xCord, int zCord) {
		if (getSsDirection() == null || nativeDirection == null) {
			return 0;
		} else {
			if(getSsDirection().equals(nativeDirection)||nativeDirection.equals(Direction.UP)) {
				return initialZ + zCord;
			}
			if(getSsDirection().getOpposite().equals(nativeDirection)) {
				return initialZ - zCord;
			}
			if(getSsDirection().getClockWise().equals(nativeDirection)) {
				return initialZ - xCord;
			}
			if(getSsDirection().getCounterClockWise().equals(nativeDirection)) {
				return initialZ + xCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type combination: "+getSsDirection().toString()+" + "+nativeDirection.toString());
	}
	@Override
	protected void placeBlockInternal(ISeedReader world, BlockState blockState, BlockPos blockpos, MutableBoundingBox box) {
		if (box.isInside(blockpos)&& nativeDirection!=null) {
			Rotation rot = DirectionTools.getRotationDirection(getSsDirection(), nativeDirection);
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
	public StructureFile getStructure(String name) {
		StructureFile file = StructureFileHandler.getStructureFile(name, ServerLifecycleHooks.getCurrentServer());
		if (file == null) {
			ExtendedLog.error("LOADED NULL STRUCTURE FILE!: " + name);
		}
		return file;
	}

	public abstract String fileName();
}
