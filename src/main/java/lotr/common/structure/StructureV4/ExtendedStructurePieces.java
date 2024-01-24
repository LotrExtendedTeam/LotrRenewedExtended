package lotr.common.structure.StructureV4;

import lotr.common.structure.StructureV4.standalone.ExtendedHobbitTavernPiece;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ExtendedStructurePieces {
	public static final IStructurePieceType HOBBIT_TAVERN_SMALL_START = registerPiece("lotrextended_hobbit_tavern_small", ExtendedHobbitTavernPiece::new);
	
	public static IStructurePieceType registerPiece(String name, IStructurePieceType piece) {
		return Registry.register(Registry.STRUCTURE_PIECE, ExtendedStructures.extendedLoc(name), piece);
	}
}
