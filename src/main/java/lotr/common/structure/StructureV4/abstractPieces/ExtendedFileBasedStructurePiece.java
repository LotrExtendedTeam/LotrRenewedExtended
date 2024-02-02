package lotr.common.structure.StructureV4.abstractPieces;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.enums.StructureType;

import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.structure.util.StructureSize;
import lotr.common.structure.util.StructureSize.StructureBoxValues;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
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
		StructureBoxValues boxModifer = structureSize.makeStructureBox(file.getLayers());
		System.out.println("Structure Spawn Dir: "+getSsDirection().toString()+"-"+nativeDirection);
		System.out.println("Structure Size: "+structureSize.toString());
		if (getSsDirection().equals(nativeDirection) || nativeDirection.equals(Direction.UP)) {
			if(getStructureType().equals(StructureType.SPAWNED_SINGLE)) {
				System.out.println("Same direction");
			}
			this.width = structureSize.getWidth();
			this.depth = structureSize.getDepth();
			this.boundingBox = MutableBoundingBox.createProper(initialX+boxModifer.widthLowX, initialY+boxModifer.heightLowY, initialZ+boxModifer.depthLowZ, initialX+boxModifer.widthHighX, initialY+boxModifer.heightHighY, initialZ+boxModifer.depthHighZ);
		} else if (getSsDirection().getOpposite().equals(nativeDirection)) {
			if(getStructureType().equals(StructureType.SPAWNED_SINGLE)) {
				System.out.println("Opposite direction");
			}
			this.width = structureSize.getWidth();
			this.depth = structureSize.getDepth();
			this.boundingBox = MutableBoundingBox.createProper(initialX+boxModifer.widthLowX, initialY+boxModifer.heightLowY, initialZ+boxModifer.depthLowZ, initialX+boxModifer.widthHighX, initialY+boxModifer.heightHighY, initialZ+boxModifer.depthHighZ);
		}else if (getSsDirection().getClockWise().equals(nativeDirection)){
			if(getStructureType().equals(StructureType.SPAWNED_SINGLE)) {
				System.out.println("ClockWise direction");
			}
			this.width = structureSize.getDepth();
			this.depth = structureSize.getWidth();
			this.boundingBox = MutableBoundingBox.createProper(initialX+boxModifer.depthLowZ, initialY+boxModifer.heightLowY, initialZ+boxModifer.widthLowX, initialX+boxModifer.depthHighZ, initialY+boxModifer.heightHighY, initialZ+boxModifer.widthHighX);
		}else if (getSsDirection().getCounterClockWise().equals(nativeDirection)){
			if(getStructureType().equals(StructureType.SPAWNED_SINGLE)) {
				System.out.println("CounterClockWise direction");
			}
			this.width = structureSize.getDepth();
			this.depth = structureSize.getWidth();
			this.boundingBox = MutableBoundingBox.createProper(initialX+boxModifer.depthLowZ, initialY+boxModifer.heightLowY, initialZ+boxModifer.widthLowX, initialX+boxModifer.depthHighZ, initialY+boxModifer.heightHighY, initialZ+boxModifer.widthHighX);
		}
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
			return xCord;
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
			return zCord;
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

	public StructureFile getStructure(String name) {
		StructureFile file = StructureFileHandler.getStructureFile(name, ServerLifecycleHooks.getCurrentServer());
		if (file == null) {
			ExtendedLog.error("LOADED NULL STRUCTURE FILE!: " + name);
		}
		return file;
	}

	public abstract String fileName();
}
