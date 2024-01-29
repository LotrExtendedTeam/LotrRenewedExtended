package lotr.common.structure.StructureV4;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.structure.util.StructureSize;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class ExtendedFileBasedStructurePiece extends ExtendedStructurePiece{
	private static StructureFile structureFile;

	protected ExtendedFileBasedStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	protected ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, int posX, int posY, int posZ, int width, int height, int depth) {
		this(pieceType, random, posX, posY, posZ, width, height, depth, Direction.Plane.HORIZONTAL.getRandomDirection(random));
	}

	protected ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, int posX, int posY, int posZ, int width, int height, int depth, Direction direction) {
		super(pieceType, random, posX, posY, posZ, width, height, depth, direction);
		if(getStructure()!=null) {
			updateStructureParamiters(structureFile.getNaturalFacingDirection(), structureFile.readGenv4StructureSize());
		}else {
			ExtendedLog.error("FAILED LOADING STRUCTURE FILE!: " + fileName());
		}
	}

	protected void updateStructureParamiters(Direction nativeDir, StructureSize structureSize) {
		this.height = structureSize.getHeight();
		int posX = boundingBox.x0;
		int posY = boundingBox.y0;
		int posZ = boundingBox.z0;
		System.out.println("Structure Spawn Dir: "+this.getOrientation().toString()+"-"+nativeDir);
		System.out.println("Structure Size: "+structureSize.toString());
		if (this.getOrientation().getAxis() == nativeDir.getAxis() || nativeDir.equals(Direction.UP)) {
			this.width = structureSize.getWidth();
			this.depth = structureSize.getDepth();
			this.boundingBox = new MutableBoundingBox(posX, posY, posZ, posX + structureSize.getWidth() - 1, posY + structureSize.getHeight() - 1, posZ + structureSize.getDepth() - 1);
		} else if (this.getOrientation().getOpposite().getAxis() == nativeDir.getAxis()){
			this.width = structureSize.getDepth();
			this.depth = structureSize.getWidth();
			this.boundingBox = new MutableBoundingBox(posX, posY, posZ, posX + structureSize.getDepth() - 1, posY + structureSize.getHeight() - 1, posZ + structureSize.getWidth() - 1);
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
