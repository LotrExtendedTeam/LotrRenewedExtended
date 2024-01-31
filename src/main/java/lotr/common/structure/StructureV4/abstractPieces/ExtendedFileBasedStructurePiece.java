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

	protected ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, StructureType structureType) {
		this(pieceType, random, position, Direction.Plane.HORIZONTAL.getRandomDirection(random), structureType);
	}

	protected ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(pieceType, random, position, direction, structureType);
		if(getStructure()!=null) {
			updateStructureParamiters(structureFile);
		}else {
			ExtendedLog.error("FAILED LOADING STRUCTURE FILE!: " + fileName());
		}
	}

	protected ExtendedFileBasedStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	protected void updateStructureParamiters(StructureFile file) {
		Direction nativeDir = file.getNaturalFacingDirection();
		StructureSize structureSize = file.readGenv4StructureSize();
		this.height = structureSize.getHeight();
		int posX = boundingBox.x0;
		int posY = boundingBox.y0;
		int posZ = boundingBox.z0;
		StructureBoxValues boxModifer = structureSize.makeStructureBox(file.getLayers());
		System.out.println("Structure Spawn Dir: "+this.getOrientation().toString()+"-"+nativeDir);
		System.out.println("Structure Size: "+structureSize.toString());
		if (this.getOrientation() == nativeDir || this.getOrientation().getOpposite() == nativeDir || nativeDir.equals(Direction.UP)) {
			this.width = structureSize.getWidth();
			this.depth = structureSize.getDepth();
			this.boundingBox = new MutableBoundingBox(posX+boxModifer.widthLowX, posY+boxModifer.heightLowY, posZ+boxModifer.depthLowZ, posX+boxModifer.widthHighX, posY+boxModifer.heightHighY, posZ+boxModifer.depthHighZ);
		} else if (this.getOrientation().getClockWise() == nativeDir||this.getOrientation().getCounterClockWise() == nativeDir){
			this.width = structureSize.getDepth();
			this.depth = structureSize.getWidth();
			new MutableBoundingBox(posX+boxModifer.depthLowZ, posY+boxModifer.heightLowY, posZ+boxModifer.widthLowX, posX+boxModifer.depthHighZ, posY+boxModifer.heightHighY, posZ+boxModifer.widthHighX);
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

	public StructureFile getStructure(String name) {
		StructureFile file = StructureFileHandler.getStructureFile(name, ServerLifecycleHooks.getCurrentServer());
		if (file == null) {
			ExtendedLog.error("LOADED NULL STRUCTURE FILE!: " + name);
		}
		return file;
	}

	public abstract String fileName();
}
