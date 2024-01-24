package lotr.common.structure;

import lotr.common.structure.util.RelativePosition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;

public class StructureFile {

	private final ListNBT layers;
	private final ListNBT signs;
	private final ListNBT entities;
	public final String name;
	private final CompoundNBT structureSize;
	private final Direction NaturalFacingDirection;

	public StructureFile(String name, ListNBT layers, CompoundNBT structureSize, ListNBT signs, ListNBT entities, Direction dir) {
		this.layers = layers;
		this.signs = signs;
		this.entities = entities;
		this.name = name;
		this.structureSize = structureSize;
		this.NaturalFacingDirection = dir;
	}

	public CompoundNBT getStructureSize() {
		return structureSize;
	}

	public ListNBT getLayers() {
		return layers;
	}

	public static RelativePosition readBlockPos(CompoundNBT compoundnbt) {
	    return new RelativePosition(compoundnbt.getInt("X"), compoundnbt.getInt("Y"), compoundnbt.getInt("Z"));
	}
	
	public RelativePosition readStructureSize() {
	    return new RelativePosition(structureSize.getInt("X"), structureSize.getInt("Y"), structureSize.getInt("Z"));
	}

	public ListNBT getSigns() {
		return signs;
	}

	public ListNBT getEntities() {
		return entities;
	}

	public Direction getNaturalFacingDirection() {
		return NaturalFacingDirection;
	}
}
