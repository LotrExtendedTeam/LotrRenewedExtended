package lotr.common.structure.StructureV4.standalone;

import java.util.Random;

import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureV4.ExtendedStructurePiece;
import lotr.common.structure.StructureV4.ExtendedStructurePieces;
import lotr.common.structure.util.RelativePosition;
import lotr.common.structure.util.StructureStrings;
import lotr.common.world.gen.feature.structuresV3.parts.ExtendedKegDrinks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ExtendedHobbitTavernPiece extends ExtendedStructurePiece {
	//private boolean placedMainChest;

	public ExtendedHobbitTavernPiece(Random p_i48656_1_, int p_i48656_2_, int p_i48656_3_) {
		super(ExtendedStructurePieces.HOBBIT_TAVERN_SMALL_START, p_i48656_1_, p_i48656_2_, 64, p_i48656_3_, 1, 9, 1, null);
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
	public boolean postProcess(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, MutableBoundingBox box, ChunkPos cPos, BlockPos bPos) {
		if (!this.updateAverageGroundHeight(world, box, 0)) {
			return false;
		} else {
			StructureFile file = getStructure();
			if (file != null) {
				generateOffsets(world, random, file.getNaturalFacingDirection(), file.readGenv4StructureSize(), box);
				for (int i = 0; i < file.getLayers().size(); i++) {
					CompoundNBT nbt = file.getLayers().getCompound(i);
					RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
					CompoundNBT blockData = nbt.getCompound(StructureStrings.blockMapHolder);
					BlockState blockstate = NBTUtil.readBlockState(blockData);

					setBlockAndMetadata(world, blockstate, rpos.getX(), rpos.getY(), rpos.getZ(), box, nbt, random);
					genKegForCurrentBlock(world, random, blockstate, rpos.getX(), rpos.getY(), rpos.getZ(), box, ExtendedKegDrinks.shireDrinksPool);
				}
				//doFileGen(world, rand, file);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 0, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 1, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 2, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 3, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 4, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 5, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 6, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 7, 0, box);
				this.placeBlock(world, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 8, 0, box);
				return true;
			}
			return false;
		}
	}

	@Override
	public String fileName() {
		return "hobbit_tavern_small";
	}

}