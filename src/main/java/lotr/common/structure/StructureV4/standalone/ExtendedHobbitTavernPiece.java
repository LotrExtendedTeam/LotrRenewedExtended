package lotr.common.structure.StructureV4.standalone;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.enums.StructureType;

import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureV4.ExtendedStructurePieces;
import lotr.common.structure.StructureV4.abstractPieces.ExtendedFileBasedStructurePiece;
import lotr.common.structure.StructureV4.parts.ExtendedKegDrinks;
import lotr.common.structure.util.RelativePosition;
import lotr.common.structure.util.StructureStrings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ExtendedHobbitTavernPiece extends ExtendedFileBasedStructurePiece {
	//private boolean placedMainChest;

	protected ExtendedHobbitTavernPiece(Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(ExtendedStructurePieces.HOBBIT_TAVERN_SMALL_START, random, position, direction, structureType);
	}

	public ExtendedHobbitTavernPiece(TemplateManager p_i51350_1_, CompoundNBT p_i51350_2_) {
		super(ExtendedStructurePieces.HOBBIT_TAVERN_SMALL_START, p_i51350_2_);
		//this.placedMainChest = p_i51350_2_.getBoolean("placedMainChest");
	}

	@Override
	protected void addMoreSaveData(CompoundNBT p_143011_1_) {
		//p_143011_1_.putBoolean("placedMainChest", this.placedMainChest);

	}

	@Override
	public boolean placeBlocks(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, ChunkPos cPos, StructureType structureType) {
//		if (!this.updateAverageGroundHeight(world, boundingBox, 0)) {
//			System.out.println("Failed to update ground height");
//			return false;
//		} else {
			StructureFile file = getStructure();
			System.out.println(boundingBox.toString());
			if (file != null) {
				generateBox(world, boundingBox, 0, 0, 0, boundingBox.x1-boundingBox.x0, boundingBox.y1-boundingBox.y0, boundingBox.z1-boundingBox.z0, Blocks.BROWN_STAINED_GLASS.defaultBlockState(), Blocks.WHITE_STAINED_GLASS.defaultBlockState(), false);
				for (int i = 0; i < file.getLayers().size(); i++) {
					CompoundNBT nbt = file.getLayers().getCompound(i);
					RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
					CompoundNBT blockData = nbt.getCompound(StructureStrings.blockMapHolder);
					BlockState blockstate = NBTUtil.readBlockState(blockData);

					setBlockAndMetadata(world, blockstate, rpos.getX(), rpos.getY(), rpos.getZ(), boundingBox, nbt, random);
					genKegForCurrentBlock(world, random, blockstate, rpos.getX(), rpos.getY(), rpos.getZ(), boundingBox, ExtendedKegDrinks.shireDrinksPool);
				}
				//doFileGen(world, rand, file);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 0, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 1, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 2, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 3, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 4, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 5, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 6, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 7, 0, boundingBox);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 8, 0, boundingBox);
				System.out.println("Structure generated");
				return true;
			}
			System.out.println("Failed to generate structure");
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

}