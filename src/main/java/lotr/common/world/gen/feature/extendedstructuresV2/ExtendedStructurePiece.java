package lotr.common.world.gen.feature.extendedstructuresV2;

import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;

public class ExtendedStructurePiece extends StructurePiece {

	public ExtendedStructurePiece(IStructurePieceType p_i51343_1_, CompoundNBT p_i51343_2_) {
		super(p_i51343_1_, p_i51343_2_);
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT p_143011_1_) {
	}

	@Override
	public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_,
			Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
		return false;
	}

}
