package lotr.common.structure.StructureV4.standalone;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.enums.StructureType;

import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureV4.ExtendedStructurePieces;
import lotr.common.structure.StructureV4.abstractPieces.ExtendedFileBasedStructurePiece;
import lotr.common.structure.StructureV4.parts.ExtendedKegDrinksv4;
import lotr.common.structure.StructureV4.parts.ExtendedSignTextsv4;
import lotr.common.structure.util.RelativePosition;
import lotr.common.structure.util.StructureStrings;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ExtendedHobbitTavernPiece extends ExtendedFileBasedStructurePiece {

	protected ExtendedHobbitTavernPiece(Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(ExtendedStructurePieces.HOBBIT_TAVERN_SMALL_START, random, position, direction, structureType);
	}

	public ExtendedHobbitTavernPiece(TemplateManager p_i51350_1_, CompoundNBT p_i51350_2_) {
		super(ExtendedStructurePieces.HOBBIT_TAVERN_SMALL_START, p_i51350_2_);
	}

	@Override
	protected void addMoreSaveData(CompoundNBT p_143011_1_) {}

	@Override
	public boolean placeBlocks(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, ChunkPos cPos, StructureType structureType) {
//		if (!this.updateAverageGroundHeight(world, boundingBox, 0)) {
//			System.out.println("Failed to update ground height");
//			return false;
//		} else {
			StructureFile file = getStructure();
			if (file != null) {
				for (int i = 0; i < file.getLayers().size(); i++) {
					CompoundNBT nbt = file.getLayers().getCompound(i);
					RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
					CompoundNBT blockData = nbt.getCompound(StructureStrings.blockMapHolder);
					BlockState blockstate = NBTUtil.readBlockState(blockData);

					setBlockAndMetadata(world, blockstate, rpos.getX(), rpos.getY(), rpos.getZ(), boundingBox, nbt, random);
					genKegForCurrentBlock(world, random, blockstate, rpos.getX(), rpos.getY(), rpos.getZ(), boundingBox, ExtendedKegDrinksv4.shireDrinksPool);
				}
				doFileGen(world, random, file, boundingBox);
				return true;
			}
			return false;
		}
//	}

	@Override
	public String fileName() {
		return "hobbit_tavern_small";
	}

	@Override
	public boolean isCodeDefined() {
		return false;
	}

	@Override
	public int defaultStructureWidth() {
		return 0;
	}

	@Override
	public int defaultStructureHeight() {
		return 0;
	}

	@Override
	public int defaultStructureDepth() {
		return 0;
	}

	@Override
	public void setSignTextFromFileData(ISeedReader world, Random random, BlockPos blockPos, int num, MutableBoundingBox box) {
		setSignTileEntity(world, blockPos, ExtendedSignTextsv4.ShireTavernTexts(random), box);
	}
}