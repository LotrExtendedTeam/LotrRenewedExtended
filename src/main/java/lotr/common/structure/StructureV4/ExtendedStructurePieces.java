package lotr.common.structure.StructureV4;

import lotr.common.structure.StructureV4.standalone.pieces.ExtendedHobbitBarnPiece;
import lotr.common.structure.StructureV4.standalone.pieces.ExtendedHobbitHoleSmallPiece;
import lotr.common.structure.StructureV4.standalone.pieces.ExtendedHobbitHousePiece;
import lotr.common.structure.StructureV4.standalone.pieces.ExtendedHobbitTavernPiece;
import lotr.common.structure.StructureV4.standalone.pieces.ExtendedHobbitWindmillPiece;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ExtendedStructurePieces {
	public static final IStructurePieceType HOBBIT_TAVERN_SMALL_START = registerPiece("lotrextended_hobbit_tavern_small", ExtendedHobbitTavernPiece::new);
	public static final IStructurePieceType HOBBIT_BARN_START = registerPiece("lotrextended_hobbit_barn", ExtendedHobbitBarnPiece::new);
	public static final IStructurePieceType HOBBIT_HOLE_SMALL_START = registerPiece("lotrextended_hobbit_hole_small", ExtendedHobbitHoleSmallPiece::new);
	public static final IStructurePieceType HOBBIT_HOUSE_START = registerPiece("lotrextended_hobbit_house", ExtendedHobbitHousePiece::new);
	public static final IStructurePieceType HOBBIT_WINDMILL_START = registerPiece("lotrextended_hobbit_windmill", ExtendedHobbitWindmillPiece::new);

	public static IStructurePieceType registerPiece(String name, IStructurePieceType piece) {
		return Registry.register(Registry.STRUCTURE_PIECE, ExtendedStructures.extendedLoc(name), piece);
	}

	public static void init() {}
}
