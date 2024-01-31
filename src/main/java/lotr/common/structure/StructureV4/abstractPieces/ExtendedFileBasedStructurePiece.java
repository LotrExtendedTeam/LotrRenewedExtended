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
			updateStructureParamiters(structureFile);
			nativeDirection = structureFile.getNaturalFacingDirection();
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
		System.out.println("Structure Spawn Dir: "+ssDirection.toString()+"-"+nativeDirection);
		System.out.println("Structure Size: "+structureSize.toString());
		if (ssDirection == nativeDirection || ssDirection.getOpposite() == nativeDirection || nativeDirection.equals(Direction.UP)) {
			this.width = structureSize.getWidth();
			this.depth = structureSize.getDepth();
			this.boundingBox = new MutableBoundingBox(initialX+boxModifer.widthLowX, initialY+boxModifer.heightLowY, initialZ+boxModifer.depthLowZ, initialX+boxModifer.widthHighX, initialY+boxModifer.heightHighY, initialZ+boxModifer.depthHighZ);
		} else if (ssDirection.getClockWise() == nativeDirection || ssDirection.getCounterClockWise() == nativeDirection){
			this.width = structureSize.getDepth();
			this.depth = structureSize.getWidth();
			this.boundingBox = new MutableBoundingBox(initialX+boxModifer.depthLowZ, initialY+boxModifer.heightLowY, initialZ+boxModifer.widthLowX, initialX+boxModifer.depthHighZ, initialY+boxModifer.heightHighY, initialZ+boxModifer.widthHighX);
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
		if (ssDirection == null) {
			return xCord;
		} else {
			if(ssDirection.equals(nativeDirection)||nativeDirection.equals(Direction.UP)) {
				return initialX + xCord;
			}
			if(ssDirection.getOpposite().equals(nativeDirection)) {
				return initialX - xCord;
			}
			if(ssDirection.getClockWise().equals(nativeDirection)) {
				return initialX + zCord;
			}
			if(ssDirection.getCounterClockWise().equals(nativeDirection)) {
				return initialX - zCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type combination: "+ssDirection.toString()+" + "+nativeDirection.toString());
	}

	@Override
	protected int getWorldZ(int xCord, int zCord) {
		if (ssDirection == null) {
			return zCord;
		} else {
			if(ssDirection.equals(nativeDirection)||nativeDirection.equals(Direction.UP)) {
				return initialZ + zCord;
			}
			if(ssDirection.getOpposite().equals(nativeDirection)) {
				return initialZ - zCord;
			}
			if(ssDirection.getClockWise().equals(nativeDirection)) {
				return initialZ - xCord;
			}
			if(ssDirection.getCounterClockWise().equals(nativeDirection)) {
				return initialZ + xCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type combination: "+ssDirection.toString()+" + "+nativeDirection.toString());
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
