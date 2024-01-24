package lotr.common.structure.StructureV4.standalone;

import java.util.Random;

import lotr.common.structure.StructureV4.ExtendedStructurePiece;
import lotr.common.structure.StructureV4.ExtendedStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
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
		super(ExtendedStructurePieces.HOBBIT_TAVERN_SMALL_START, p_i48656_1_, p_i48656_2_, 64, p_i48656_3_, 1, 20, 1);
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
	public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_,
			Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
		if (!this.updateAverageGroundHeight(p_230383_1_, p_230383_5_, 0)) {
			return false;
		} else {
			System.out.println("Placing hobbit tavern blocks!");
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 10, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 11, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 12, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 13, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 14, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 15, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 16, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 17, 0, p_230383_5_);
			this.placeBlock(p_230383_1_, Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState(), 0, 18, 0, p_230383_5_);

			return true;
		}
	}
}