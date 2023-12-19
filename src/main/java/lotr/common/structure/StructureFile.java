package lotr.common.structure;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class StructureFile {

	private final ListNBT layers;
	public final String name;
	private final CompoundNBT structureSize;

	public StructureFile(String name, ListNBT layers, CompoundNBT structureSize) {
		this.layers = layers;
		this.name = name;
		this.structureSize = structureSize;
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
}
